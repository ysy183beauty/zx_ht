package com.npt.internet.auth;

import com.npt.rms.auth.entity.NptSimpleUser;
import com.npt.rms.auth.service.NptRmsAuthService;
import com.npt.rms.base.action.NptRMSAction;
import com.npt.rms.util.NptRmsUtil;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 项目：NPTWebApp
 * 作者：owen
 * 时间：2016/12/12 21:53
 * 描述:
 */
@Controller("npt.internet.user")
public class NptInternetUserAction extends NptRMSAction<NptSimpleUser>{

    @Resource(name = "rmsAuthService")
    private NptRmsAuthService authService;

    /**
     *作者：owen
     *时间：2016/12/12 21:55
     *描述:
     *      用户注册
     */
    public String register(){
        String loginName = getParameter("loginName");
        String userName = getParameter("userName");
        String password = getParameter("password");
        String repPassword = getParameter("repPassword");
        String mobile = getParameter("mobile");
        String wechar = getParameter("wechat");
        String email = getParameter("email");
        String sex = getParameter("sex");

        boolean existed = authService.checkUserName(loginName);
        if(existed){
            this.outputErrorResult("账号已存在!");
            return ERROR;
        }else if(!password.equals(repPassword)){
            this.outputErrorResult("两次密码不相同!");
            return ERROR;
        }else {
            NptSimpleUser user = new NptSimpleUser();

            user.setPassword(NptRmsUtil.encodePassword(password));
            user.setLoginName(loginName);
            user.setMobile(mobile);
            user.setEmail(email);
            user.setSex(sex);
            user.setWechatNo(wechar);
            user.setUserName(userName);
            authService.add(user);

            return SUCCESS;
        }
    }
}
