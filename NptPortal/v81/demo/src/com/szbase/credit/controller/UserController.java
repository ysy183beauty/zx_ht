package com.szbase.credit.controller;

import com.alibaba.fastjson.JSON;
import com.szbase.credit.service.IUserService;
import com.szbase.credit.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理.
 */
@Controller
@RequestMapping(value = "/user", method ={RequestMethod.GET, RequestMethod.POST })
public class UserController {

    @Autowired
    private IUserService userService;

    //用户注册
	@RequestMapping(value = "/userRegister")
	public void userRegister(HttpServletRequest request,
			HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        Map<String,Object> map = new HashMap<String, Object>();
        String phoneYZM = request.getParameter("phoneyzm");
        String phone = request.getParameter("phone");
        String loginName = request.getParameter("loginname");
        List<Map<String,Object>> list=userService.getUserList("phone", phone);
        if(list.size()>0){//登录已存在
            map.put("flag","false");
            map.put("msg","电话名已存在");
        }else {
            String flag = userService.checkMessage(phoneYZM,phone);
            String registerFlag = "";
            if ("ok".equals(flag)) {
                String userName = request.getParameter("username");
                list = userService.getUserList("loginname", loginName);
                if (list.size() > 0) {//电话名已存在
                    map.put("flag", "false");
                    map.put("msg", "登录名已存在");
                } else {
                    String password = request.getParameter("password");
                    String email = request.getParameter("email");
                    String type = request.getParameter("type");
                    registerFlag = userService.userRegister(userName, loginName, password, email, phone, type);
                    if ("ok".equals(registerFlag)) {
                        map.put("flag", "ok");
                        map.put("msg", "注册成功！");
                    } else {
                        map.put("flag", "false");
                        map.put("msg", "注册失败！稍后请重新尝试。");
                    }
                }
            } else {
                map.put("flag", "false");
                map.put("msg", "验证码不正确");
            }
        }


        String s= JSON.toJSONString(map);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
		return ;
	}

    //用户登录
	@RequestMapping(value = "/userLogin")
	public void userLogin(HttpServletRequest request,
			HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String password = request.getParameter("password");
        String loginName = request.getParameter("loginname");
        String flag= userService.userLogin(loginName,password);

        Map<String,Object> map = new HashMap<String, Object>();
        if("ok".equals(flag)){
            List<Map<String,Object>> list= userService.queryUserList(loginName);
            map.put("flag","ok");
            map.put("msg","登录成功");
            map.put("list",list.get(0));
            request.getSession().setAttribute("credit_user",list.get(0));
            request.getSession().removeAttribute("changPwd_Phone");
        }else {
            map.put("flag","false");
            map.put("msg","登录失败");
        }
        String s= JSON.toJSONString(map);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
	}


    //退出登录
    @RequestMapping(value = "/userLogout")
    public void userLogout(HttpServletRequest request,
                          HttpServletResponse response) {
     request.getSession().removeAttribute("credit_user");
        String s= JSON.toJSONString("ok");
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
    }

    //修改密码
    @RequestMapping(value = "/changePassword")
    public void changePassword(HttpServletRequest request,
                           HttpServletResponse response) {
        Map<String,Object> map = (Map<String, Object>) request.getSession().getAttribute("credit_user");
        if(map.isEmpty()){
            try {
                response.sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }else {
            String phone = StringUtils.getStr(map.get("PHONE"));
            String pwd = request.getParameter("pwd");
            String flag = userService.changePassword(phone, pwd);
            String s = JSON.toJSONString(flag);
            try {
                response.getWriter().write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ;
    }

    //修改密码
    @RequestMapping(value = "/changePhone")
    public void changePhone(HttpServletRequest request,
                               HttpServletResponse response) {
        Map<String,Object> map = (Map<String, Object>) request.getSession().getAttribute("credit_user");
        String yzm = request.getParameter("yzm");
        String phone = request.getParameter("phone");
        if(map.isEmpty()){
            try {
                response.sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }else {
            String s="";
            String result =  userService.checkMessage(yzm,phone);
            if("ok".equals(result)) {
                String oldphone = StringUtils.getStr(map.get("PHONE"));
                String flag = userService.changePasswordByPwd(oldphone, phone);
                s = JSON.toJSONString(flag);
            }else {
                s = JSON.toJSONString("验证码错误请重试！");
            }
            try {
                response.getWriter().write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
    }


    //发送短信验证码
    @RequestMapping(value = "/sendMessage")
    public void sendMessage(HttpServletRequest request,
                             HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        String phone =  StringUtils.getStr(request.getSession().getAttribute("changPwd_Phone"));
        String s= JSON.toJSONString("ok");
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
    }



    // 校验短信验证码
	@RequestMapping(value = "/checkMessage")
	public void checkMessage(HttpServletRequest request,
			HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String jym = request.getParameter("jym");
        String phone =  StringUtils.getStr(request.getSession().getAttribute("changPwd_Phone"));
        String flag = userService.checkMessage(jym,phone);
        String s= JSON.toJSONString(flag);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
	}

    // 企业信用查询委办局资源信息
    @RequestMapping(value = "/getBackPassword")
    public void getBackPassword(HttpServletRequest request,
                                      HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String password = request.getParameter("pwd");
        String phone =  StringUtils.getStr(request.getSession().getAttribute("changPwd_Phone"));
        String flag="false";
        if(!"".equals(phone)){
           flag=userService.changePassword(phone,password);
        }

        String s= JSON.toJSONString(flag);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
    }

    // 用户名或者电话核对
    @RequestMapping(value = "/checkUser")
    public void checkUser(HttpServletRequest request,
                               HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        String type = request.getParameter("type");
        String value = request.getParameter("value");
        List<Map<String,Object>> list=userService.getUserList(type,value);
        Map<String,Object> map=new HashMap<String, Object>();
        if(list.size()>0){
            map=list.get(0);
            request.getSession().setAttribute("changPwd_Phone", StringUtils.getStr(map.get("PHONE")));
        }
        String s= JSON.toJSONString(map);
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
    }

}
