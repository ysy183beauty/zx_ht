<%@ page language="java" isErrorPage="true" %>
<%@page contentType="text/html; charset=utf-8"%>
<%
	org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	Throwable ex = exception;
	try{
	if(ex == null){ex = (Throwable)request.getAttribute("javax.servlet.error.exception");}
	}catch(Exception e){logger.warn(null, e);};
	
	String msg = "未知请求错误！";
	if(ex != null){
		msg = ex.getMessage();
		if(msg == null) msg = ex.toString();
	}
	if(ex != null){logger.warn(msg, ex);}
	else{logger.warn(msg);}
	
	// 判断是否是Ajax请求
	String x_requested_with = request.getHeader("X-Requested-With");
	if(x_requested_with != null && x_requested_with.trim().length() >0){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		out.println("ajax:" + msg);
	}else{
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
    <title>请求错误</title>
</head>
<body>
<div id="screen">
    <div id="content">
    <h1>错误</h1>
    <div><%= msg %></div>
    <pre> <% if(ex != null) ex.printStackTrace(new java.io.PrintWriter(out)); %></pre>
    </div>
</body>
</html>
 <% } %>
