package com.szbase.credit.dao.impl;

import com.szbase.credit.dao.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements IUserDao {
    @Autowired
    private JdbcTemplate simpleJdbcTemplate;

    @Override
    public String userRegister(String userName, String loginName, String password, String email, String phone,String type) {

        String sql = "insert into mh_user (PHONE, USERNAME, LOGINNAME, PASSWORD,EMAIL,TYPE)" +
                " values('"+phone+"','"+userName+"','"+loginName+"','"+password+"','"+email+"','"+type+"')";
       int sum = simpleJdbcTemplate.update(sql);
        String flag="false";
        if(sum==1){
            flag="ok";
        }

        return flag;
    }

    @Override
    public List<Map<String,Object>> userListByLoginName(String loginName) {
        String sql = "select * from mh_user u  where u.loginname='"+loginName+"'";
        List<Map<String,Object>> list = simpleJdbcTemplate.queryForList(sql);

        return list;
    }

    @Override
    public String sendMessage(String message) {
        return null;
    }

    @Override
    public String checkMessage(String jym) {
        return null;
    }

    @Override
    public String changePassword(String phone, String password) {

        String sql = "update mh_user set PASSWORD ='"+password+"' where phone='"+phone+"'";
        int sum = simpleJdbcTemplate.update(sql);
        String flag="false";
        if(sum==1){
            flag="ok";
        }

        return flag;
    }

    @Override
    public String changePasswordByPwd(String oldpassword, String password) {

        String sql = "update mh_user set password ='"+password+"' where password='"+oldpassword+"'";
        int sum = simpleJdbcTemplate.update(sql);
        String flag="false";
        if(sum==1){
            flag="ok";
        }

        return flag;
    }

    @Override
    public String changePhoneByPhone(String oldphone, String phone) {

        String sql = "update mh_user set phone ='"+phone+"' where phone='"+oldphone+"'";
        int sum = simpleJdbcTemplate.update(sql);
        String flag="false";
        if(sum==1){
            flag="ok";
        }

        return flag;
    }


    public List<Map<String,Object>> userListByCondition(String condition){
        String sql = "select * from mh_user u  where "+condition;
        List<Map<String,Object>> list = simpleJdbcTemplate.queryForList(sql);

        return list;
    }
}
