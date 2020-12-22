package com.szbase.credit.dao;

import java.util.List;
import java.util.Map;

public interface IObjectionHandlingDao {
    //用户注册
    public String addObjectionHandling(String realName,String phone,String email,String title,String content,String remark);

    //用户登录
    public List<Map<String,Object>> getObjectionHandlingList(String userName);

}
