package mapper;

import entity.Userinfo;

public interface UserinfoMapper {

    int selectUidByUsername(String username);

    String selectEmailByUsername(String name);

    Userinfo selectByUsername(String username);

    int deleteByPrimaryKey(Integer uId);

    int insert(Userinfo record);

    int insertSelective(Userinfo record);

    Userinfo selectByPrimaryKey(Integer uId);

    int updateByPrimaryKeySelective(Userinfo record);

    int updateByPrimaryKey(Userinfo record);
}