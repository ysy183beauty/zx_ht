/**
* @Title: UserServiceImpl.java
* @Package com.szbase.credit.service.impl
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午2:58:05
* @version V1.0
*/
package com.szbase.credit.service.impl;

import com.szbase.credit.dao.IUserDao;
import com.szbase.credit.service.IUserService;
import com.szbase.credit.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午2:58:05
 *
 */
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;

    @Override
    public String userRegister(String userName,String loginName,String password,String email,String phone,String type) {

        return userDao.userRegister(userName,loginName,password,email,phone,type);
    }

    @Override
    public String userLogin(String loginName,
                                         String password) {
        List<Map<String, Object>> list=userDao.userListByLoginName(loginName);
        if(list.size()>0){
            String password1= StringUtils.getStr(list.get(0).get("PASSWORD"));
            if(password1.equals(password)){
                return "ok";
            }
        }
        return "false";
    }

    @Override
    public List<Map<String, Object>> queryUserList(String loginName) {

        return userDao.userListByLoginName(loginName);
    }

    @Override
    public List<Map<String, Object>> getUserList(String field,String value) {

        return userDao.userListByCondition(field+"='"+value+"'");
    }

    @Override
    public String sendMessage(String message) {
        return null;
    }

    @Override
    public String checkMessage(String jym,String phone) {
        return "ok";
    }

    @Override
    public String changePassword(String phone,String password) {
        return userDao.changePassword(phone,password);
    }

    @Override
    public String changePasswordByPwd(String oldpassword,String password){
        return userDao.changePasswordByPwd(oldpassword,password);
    }

    @Override
    public String changePhoneByPhone(String oldphone,String phone){
        return userDao.changePhoneByPhone(oldphone,phone);
    }
}
