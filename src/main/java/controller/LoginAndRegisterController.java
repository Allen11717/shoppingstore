package controller;

import entity.Userinfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import serviceimpl.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class LoginAndRegisterController {

    @Autowired
    UserServiceImpl usi;

    @RequestMapping("/modify")
    public String modify(@RequestParam String username,@RequestParam String password){
        Userinfo ui = usi.selectByUsername(username);
        if(ui==null){
            return "none";
        }else{
            ui.setPassword(DigestUtils.md5Hex(password.getBytes()));
                int i = usi.updateByPrimaryKeySelective(ui);
                if(i>0){
                return "ok";
            }else{
                return "no";
            }
        }
    }
    @RequestMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, @RequestParam String flag, HttpServletRequest req, HttpServletResponse resp){
        Userinfo ui = usi.selectByUsername(username);
        if(ui==null){
            return "none";
        }else{
            if(DigestUtils.md5Hex(password.getBytes()).equals(ui.getPassword())){

                    /*req.getSession().setAttribute("info",ui);*/
                    Cookie cookieu = new Cookie("username",username);
                    Cookie cookiep = new Cookie("password",password);
                    cookieu.setMaxAge(10000);
                    cookiep.setMaxAge(10000);
                    resp.addCookie(cookieu);
                    resp.addCookie(cookiep);

                    if(flag.equals("yes")){
                        Cookie cookie = new Cookie("flag","no");
                        cookie.setMaxAge(50000);
                        resp.addCookie(cookie);
                       /* String value = cookie.getValue();
                        System.out.println(value);*/
                    }else{
                        Cookie cookie = new Cookie("flag","yes");
                        cookie.setMaxAge(50000);
                        resp.addCookie(cookie);
                    }

                    /*Cookie[]cookie=req.getCookies();
                    if(cookie.length>0){
                        for(Cookie co:cookie){
                            if(co.getName().equals(username)){
                                co.setValue(null);
                                co.setMaxAge(0);
                                resp.addCookie(co);
                            }
                        }
                    }*/

                    //req.getSession().removeAttribute(username);


                   // req.getSession().setAttribute("username",username);


                   /* Cookie cookieu2=new Cookie(username,username);
                    cookieu2.setMaxAge(50000);
                    resp.addCookie(cookieu2);*/




                return "true";
            }else{
                return "error";
            }
        }
    }



    @RequestMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, @RequestParam String email){
        /*如果查询结果不为空，说明该用户名已存在，不允许注册*/
        if(usi.selectByUsername(username)!=null){
            return "existed";
        }else{
            Userinfo ui=new Userinfo();
            ui.setUsername(username);
            ui.setPassword(DigestUtils.md5Hex(password.getBytes()));
            ui.setEmail(email);
            Date date=new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ui.setRegisterTime(sdf.format(date));
            int line=usi.insert(ui);
            if(line>0){
                return "yes";
            }else{
                return "no";
            }
        }
    }
}
