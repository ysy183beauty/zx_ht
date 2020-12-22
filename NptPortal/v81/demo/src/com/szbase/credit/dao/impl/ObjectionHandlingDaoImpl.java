package com.szbase.credit.dao.impl;

import com.szbase.credit.dao.IObjectionHandlingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ObjectionHandlingDaoImpl implements IObjectionHandlingDao {
    @Autowired
    private JdbcTemplate simpleJdbcTemplate;

    @Override
    public String addObjectionHandling(String realName,String phone,String email,String title,String content,String remark) {

        String sql = "insert into mh_user (PHONE, USERNAME, LOGINNAME, PASSWORD,EMAIL,TYPE)";
                // +" values('"+phone+"','"+userName+"','"+loginName+"','"+password+"','"+email+"','"+type+"')";
       int sum = 1;//simpleJdbcTemplate.update(sql);
        String flag="false";
        if(sum==1){
            flag="ok";
        }

        return flag;
    }

    @Override
    public List<Map<String,Object>> getObjectionHandlingList(String loginName) {
        String sql = "select * from mh_user u  where u.loginname='"+loginName+"'";
        List<Map<String,Object>> list = simpleJdbcTemplate.queryForList(sql);

        return list;
    }
}
