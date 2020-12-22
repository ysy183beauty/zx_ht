package com.credit.SystemManager.dao;

import com.credit.SystemManager.entity.CreditUser;
import com.jeecms.common.page.Pagination;

import java.util.List;
/**
 * @ClassName: UserDao
 * @Description: TODO
 * @author jiaoss
 * @date 2017年3月2日16:330:38
 *
 */
public interface UserDao {
    //用户注册
    public Boolean userRegister(CreditUser user);

    //用户认证
    public int userAuthentification(CreditUser user);

    //用户登录
    public CreditUser getUserByLoginName(String loginName);

    //发送短信验证码
    public String sendMessage(String message,String phone);

    // 校验短信验证码
    public String checkMessage(String jym,String phone) ;


    //修改密码
    public int changePassword(String phone, String password) ;

    public int changePasswordByPwd(String oldPassWord, String passWord);

    public int changePhoneByPhone(String oldPhone, String phone);

    //用户登录
    public List<CreditUser> userListByCondition(String condition);

    //用户登录
    public Pagination getUserList(int current, int preNum,CreditUser user);

    //禁用用户
    public int  disableUser(Long id);

    //禁用用户
    public int  enableUser(Long id);

    public CreditUser getUserById(Long id);

}
