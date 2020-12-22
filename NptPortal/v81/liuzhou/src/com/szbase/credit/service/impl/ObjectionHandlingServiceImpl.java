/**
* @Title: UserServiceImpl.java
* @Package com.szbase.credit.service.impl
* @Description: TODO
* @author Owen Szbase
* @date 2015年7月15日 下午2:58:05
* @version V1.0
*/
package com.szbase.credit.service.impl;

import com.szbase.credit.dao.IObjectionHandlingDao;
import com.szbase.credit.service.IObjectionHandlingService;
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
public class ObjectionHandlingServiceImpl implements IObjectionHandlingService {
	
	@Autowired
	private IObjectionHandlingDao objectionHandlingDao;

    @Override
    public String addObjectionHandling(String realName,String phone,String email,String title,String content,String remark) {

        return objectionHandlingDao.addObjectionHandling(realName,phone,email,title,content,remark);
    }

    @Override
    public String getObjectionHandlingList(String loginName,
                                         String password) {
        List<Map<String, Object>> list=objectionHandlingDao.getObjectionHandlingList(loginName);
        if(list.size()>0){
            String password1= StringUtils.getStr(list.get(0).get("PASSWORD"));
            if(password1.equals(password)){
                return "ok";
            }
        }
        return "false";
    }
}
