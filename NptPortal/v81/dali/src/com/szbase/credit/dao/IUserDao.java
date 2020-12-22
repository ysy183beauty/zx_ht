package com.szbase.credit.dao;

import java.util.List;
import java.util.Map;

public interface IUserDao {
    //用户注册
    public String userRegister(String userName,String loginName,String password,String email,String phone,String type);

    //用户登录
    public List<Map<String,Object>> userListByLoginName(String userName);

    //发送短信验证码
    public String sendMessage(String message);

    // 校验短信验证码
    public String checkMessage(String jym) ;


    //修改密码
    public String changePassword(String phone,String password) ;

    public String changePasswordByPwd(String oldpassword,String password);

    public String changePhoneByPhone(String oldphone,String phone);

    //用户登录
    public List<Map<String,Object>> userListByCondition(String condition);
}
