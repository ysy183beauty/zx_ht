/**
* @Title: IUserService.java
* @Package com.szbase.credit.service
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午2:57:33
* @version V1.0
*/
package com.szbase.credit.service;

/**
 * @ClassName: IUserService
 * @Description: TODO
 * @author Owen Szbase
 * @date 2015年7月15日 下午2:57:33
 *
 */
public interface IObjectionHandlingService {
    //用户注册
    public String addObjectionHandling(String realName,String phone,String email,String title,String content,String remark);

    //用户登录
    public String getObjectionHandlingList(String userName,
                            String password);

}
