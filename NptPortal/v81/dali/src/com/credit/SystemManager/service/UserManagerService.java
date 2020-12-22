/**
* @Title: IUserService.java
* @Package com.szbase.credit.service
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午2:57:33
* @version V1.0
*/
package com.credit.SystemManager.service;

import com.credit.SystemManager.entity.CreditUser;
import com.jeecms.common.page.Pagination;

import java.util.List;

/**
 * @ClassName: IUserService
 * @Description: TODO
 * @author jiaoss
 * @date 2017年3月2日16:42:38
 *
 */
public interface UserManagerService {
    //用户注册
    public Boolean userRegister(CreditUser user);


    //用户认证
    public Boolean userAuthentification(CreditUser user);


    //用户登录
    public Boolean userLogin(String userName,
                            String password);

    public CreditUser queryUser(String loginName);

    //发送短信验证码
    public String sendMessage(String message);



    // 校验短信验证码
    public Boolean checkMessage(String jym, String phone) ;

    // 修改密码
    public Boolean  changePassword(String phone, String password);

    // 修改密码
    public Boolean  changePasswordByPwd(String oldpassword, String password);

    public Boolean  changePhoneByPhone(String oldphone, String phone);

    public Boolean  disableUser(Long id);

    public Boolean  enableUser(Long id);


    public List<CreditUser> getUserList(String field, String value);

    public Pagination getUserList(int current, int preNum,CreditUser user);

    public CreditUser getUserById(Long id);
}
