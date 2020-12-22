/**
 * @Title: UserServiceImpl.java
 * @Package com.szbase.credit.service.impl
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午2:58:05
 * @version V1.0
 */
package com.credit.SystemManager.service.impl;

import com.credit.SystemManager.dao.UserDao;
import com.credit.SystemManager.entity.CreditUser;
import com.credit.SystemManager.service.UserManagerService;
import com.jeecms.common.page.Pagination;
import com.npt.bridge.util.NptHttpUtil;
import com.npt.bridge.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: UserServiceImpl
 * @Description: 用户管理——字段加解密的类
 *              主要加密: 密码，电话，身份证号
 * @author jiaoss
 * @date 2017年3月2日16:40:38
 *
 */
@Service
@Transactional
public class UserManagerServiceImpl implements UserManagerService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(readOnly = false)
    public Boolean userRegister(CreditUser user) {//加密用户密码

        String passWord = user.getPassWord();

        user.setPassWord(NptHttpUtil.encode(passWord));


        return userDao.userRegister(user);
    }


    //添加用户认证
    @Override
    @Transactional(readOnly = false)
    public Boolean userAuthentification(CreditUser user) {//身份证编码
        String idCard = user.getIdCard();
        String phone = user.getPhone();
        user.setIdCard(NptHttpUtil.encode(idCard));
        user.setPhone(NptHttpUtil.encode(phone));
        int sum = userDao.userAuthentification(user);
        if (sum == 1) {
            return true;
        }
        return false;
    }


    //用户登录
    @Override
    @Transactional(readOnly = true)
    public Boolean userLogin(String loginName, String password) {
        CreditUser user = userDao.getUserByLoginName(loginName);
        if (user != null) {
            try {
                String password1 = StringUtils.getStr(NptHttpUtil.decode(user.getPassWord()));
                if (password1.equals(password)) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    //查询用户
    @Override
    @Transactional(readOnly = true)
    public CreditUser queryUser(String loginName) {
        CreditUser user = userDao.getUserByLoginName(loginName);

        return DecryptUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditUser> getUserList(String field, String value) {

        if (field.equals("idCard") || field.equals("passWord")
                || field.equals("phone")) {
            value = NptHttpUtil.encode(value);
        }

        List<CreditUser> list = userDao.userListByCondition(field + "='" + value + "'");

        return DecryptUserList(list);
    }


    @Override
    @Transactional(readOnly = true)
    public Pagination getUserList(int current, int preNum,CreditUser user) {

        Pagination page = userDao.getUserList(current,preNum,encryptUser(user));
        List<CreditUser> list =(List<CreditUser>) page.getList();
        page.setList(DecryptUserList(list));

        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public String sendMessage(String message) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean checkMessage(String jym, String phone) {
        return true;
    }

    @Override
    public Boolean changePassword(String phone, String password) {
        int sum = userDao.changePassword(NptHttpUtil.encode(phone), NptHttpUtil.encode(password));
        if (sum == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean changePasswordByPwd(String oldpassword, String password) {
        int sum = userDao.changePasswordByPwd(NptHttpUtil.encode(oldpassword), NptHttpUtil.encode(password));
        if (sum == 1) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean disableUser(Long id) {
        int sum = userDao.disableUser(id);
        if (sum == 1) {
            return true;
        }
        return false;
    }

    public Boolean  enableUser(Long id){
        int sum = userDao.enableUser(id);
        if (sum == 1) {
            return true;
        }
    return false;
}


    @Override
    public Boolean changePhoneByPhone(String oldphone, String phone) {
        int sum = userDao.changePhoneByPhone(NptHttpUtil.encode(oldphone), NptHttpUtil.encode(phone));
        if (sum == 1) {
            return true;
        }
        return false;
    }

    private List<CreditUser> DecryptUserList(List<CreditUser> list){
        List<CreditUser> list2 = new ArrayList<CreditUser>();
        int len = list.size();
        for (int i = 0; i < len; i++) {
            CreditUser user = list.get(i);
            list2.add(DecryptUser(user));
        }
        return list2;
    }
    private CreditUser DecryptUser(CreditUser user){

            String idCard = user.getIdCard();
            String passWord = user.getPassWord();
            String phone = user.getPhone();
            try {
                if(!"".equals(idCard)){
                    user.setIdCard(NptHttpUtil.decode(idCard));
                }
                if(!"".equals(phone)){
                    user.setPhone(NptHttpUtil.decode(phone));
                }
                user.setPassWord(NptHttpUtil.decode(passWord));
            } catch (Exception e) {
                e.printStackTrace();
            }

        return user;
    }

    private CreditUser encryptUser(CreditUser user){

        String idCard = user.getIdCard();
        String passWord = user.getPassWord();
        String phone = user.getPhone();
        try {
            if(!"".equals(idCard)){
                user.setIdCard(NptHttpUtil.encode(idCard));
            }
            if(!"".equals(phone)){
                user.setPhone(NptHttpUtil.encode(phone));
            }
            user.setPassWord(NptHttpUtil.encode(passWord));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }

    public CreditUser getUserById(Long id){

        return DecryptUser(userDao.getUserById(id));
    }

}
