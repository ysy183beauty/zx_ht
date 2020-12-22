package com.szbase.credit.controller;

import com.alibaba.fastjson.JSON;
import com.szbase.credit.service.IObjectionHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 异议管理.
 */
@Controller
@RequestMapping(value = "/objection", method ={RequestMethod.GET, RequestMethod.POST })
public class ObjectionHandlingController {

    @Autowired
    private IObjectionHandlingService objectionHandlingService;

    //异议请提
	@RequestMapping(value = "/addObjection")
	public void addObjectionHandling(HttpServletRequest request,
			HttpServletResponse response) {

        response.setCharacterEncoding("UTF-8");
        String realName = request.getParameter("realname");
        String phone = request.getParameter("phone");
        String email = request.getParameter("email");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String remark = request.getParameter("remark");

        objectionHandlingService.addObjectionHandling(realName,phone,email,title,content,remark);
        String s= JSON.toJSONString("");
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            response.sendRedirect("/yytj/index.jhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
	}

    //获取异议处理的结果
	@RequestMapping(value = "/getObjectionList")
	public void getObjectionHandlingList(HttpServletRequest request,
			HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        String s= JSON.toJSONString("");
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
	}

    //用户登录
    @RequestMapping(value = "/getObjection")
    public void getObjectionHandling(HttpServletRequest request,
                                         HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        String s= JSON.toJSONString("");
        try {
            response.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ;
    }

}
