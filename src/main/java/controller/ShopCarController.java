package controller;

import entity.Productinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import serviceimpl.ProductinfoServiceImpl;
import serviceimpl.UserServiceImpl;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
public class ShopCarController {

    @Autowired
    JedisPool jedisPool;

    @Autowired
    ProductinfoServiceImpl psi;

    @Autowired
    UserServiceImpl usi;

    @RequestMapping("/addCar")
    public String addcar(@RequestParam String pid,@RequestParam String username){

        //购物车存储结构
        //redis的hmset存储：hmset是一个map集合，该集合的key存储username(用户名)
        //value存储一个map集合，其中这个map集合的key存储pid(商品唯一标识)，value存储
        //的是用户购物车每个商品的数量

        Jedis jedis=jedisPool.getResource();//获取Jedis对象
        //1.判断购物车里有没有商品:hgetAll通过用户名得到value
        //1.1如果value不为空，说明有商品，直接添加
        //1.2如果value为空，那么需要创建一个map集合
        Map<String,String> map=jedis.hgetAll(username);
        if(map.isEmpty()){//map一定要先进行非空判断，不然为空就是空指针
            map=new HashMap<String,String>();
            map.put(pid,"1");
            jedis.hmset(username,map);//存入redis中
        }else{
            if(map.containsKey(pid)){
                map.put(pid,String.valueOf(Integer.parseInt(map.get(pid))+1));
                jedis.hmset(username,map);//存入redis中
            }else{
                map.put(pid,"1");
                jedis.hmset(username,map);//存入redis中
            }
        }
        return "";
    }

    @RequestMapping("/getCar")
    public List<Productinfo> getCar(@RequestParam String username){
        Jedis jedis=jedisPool.getResource();//获取Jedis对象
        Map<String, String> map = jedis.hgetAll(username);
        List<Productinfo> list=new ArrayList<>();
        System.out.println(map.size());
        if(!map.isEmpty()){
            for(Map.Entry<String,String>entry:map.entrySet()){
                Productinfo pi = psi.selectByPrimaryKey(Integer.parseInt(entry.getKey()));
                pi.setpNum(Integer.parseInt(entry.getValue()));
                list.add(pi);
            }
        }
        return list;
    }

    @RequestMapping("/removeItems")
    public void removeItems(@RequestParam String pid,@RequestParam String username){
        Jedis jedis=jedisPool.getResource();
        Map<String,String> map=jedis.hgetAll(username);
        map.remove(pid);
        jedis.del(username);
        jedis.hmset(username,map);
    }

    @RequestMapping("/reduceItemsNum")
    public void reduceItemsNum(@RequestParam String pnum,@RequestParam String username,@RequestParam String pid){
        Jedis jedis=jedisPool.getResource();//获取对象
        Map<String, String> map = jedis.hgetAll(username);
        if(Integer.parseInt(map.get(pid))>=1){
            map.put(pid,String.valueOf(Integer.parseInt(map.get(pid))-1));
            jedis.hmset(username,map);
        }else{
            removeItems(pid, username);
            jedis.hdel(pid,username);
        }
    }

    @RequestMapping("/plusItemsNum")
    public void plusItemsNum(@RequestParam String pnum,@RequestParam String username,@RequestParam String pid){
        Jedis jedis=jedisPool.getResource();
        Map<String, String> map = jedis.hgetAll(username);
        map.put(pid,String.valueOf(Integer.parseInt(map.get(pid))+1));
        jedis.hmset(username,map);
    }

    @RequestMapping("/sendEmail")
    public  String getEmailCount(@RequestParam String username,HttpServletRequest req,HttpServletResponse resp) throws IOException {
        String email=usi.selectEmailByUsername(username);
        int randomNum=(int)((Math.random()*9+1)*100000);

        sendEmail(email,randomNum,req,resp);
        return String.valueOf(randomNum);
    }


    public  void sendEmail(String emailCount, int randomNum, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        String from="1171707621@qq.com";//你自己的邮箱
        String host="smtp.qq.com";//本机地址
        //Properties可以确定系统的属性,就是为了寻找我们的host
        Properties properties=System.getProperties();
        properties.setProperty("mail.smtp.host", host);
        properties.setProperty("mail.smtp.port", "25");
        properties.put("mail.smtp.auth","true");//开启代理

        Authenticator aut=new Authenticator() {//创建Authenticator内部类来填入代理的用户名前缀和密码

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("1171707621","dktjvukotejohjjc");//填用户名和代理密码

            }

        };

        //创建Session会话,Session是java.mail API最高入口
        Session session= Session.getDefaultInstance(properties,aut);
        //MimeMessage获取session对象,就可以创建要发送的邮箱内容和标题
        MimeMessage message=new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(from));//设置你自己的邮箱
            message.addRecipients(Message.RecipientType.TO, emailCount);//设置接受邮箱
            message.setSubject("验证码");//设置邮箱标题
            message.setText("您本次的验证码是:"+randomNum);//邮箱内容
            Transport.send(message);//发送邮箱

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/deleteProductNum")
    public synchronized String deleteProduction(@RequestParam String username,@RequestParam Integer pid,@RequestParam Integer pnum){
        Productinfo pi=psi.selectByPrimaryKey(pid);
        if(pi.getpNum()>=pnum){
            pi.setpNum(pi.getpNum()-pnum);
            psi.updateByPrimaryKey(pi);
            return "yes";
        }

        return "no";
    }

}
