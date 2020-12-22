package com.npt.bridge.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 项目: 柳州征信门户
 * 作者: zhanglei
 * 日期: 2017/6/16
 * 备注:
 */
public class RequestUtil {
    public static String getDomain(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();
        String contextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).append("/").toString();
        return contextUrl;
    }
}
