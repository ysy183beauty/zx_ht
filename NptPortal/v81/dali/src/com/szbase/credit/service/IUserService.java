/**
* @Title: IUserService.java
* @Package com.szbase.credit.service
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午2:57:33
* @version V1.0
*/
package com.szbase.credit.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IUserService
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午2:57:33
 *
 */
public interface IUserService {
    //用户注册
    public String userRegister(String userName,String loginName,String password,String email,String phone,String type);

    //用户登录
    public String userLogin(String userName,
                                        String password);

    public List<Map<String, Object>> queryUserList(String loginName);

    //发送短信验证码
    public String sendMessage(String message);



    // 校验短信验证码
    public String checkMessage(String jym,String phone) ;

    // 修改密码
    public String  changePassword(String phone,String password);

    // 修改密码
    public String  changePasswordByPwd(String oldpassword,String password);

    public String  changePhoneByPhone(String oldphone,String phone);


    public List<Map<String, Object>> getUserList(String field,String value);
}
