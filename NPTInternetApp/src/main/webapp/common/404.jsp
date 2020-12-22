<%@page contentType="text/html; charset=utf-8"%>
<%@page import="org.summer.security.web.action.CoreMainAction"%>
<%
org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
String uri = (String)request.getAttribute("javax.servlet.forward.request_uri");
String msg = "请求的页面未找到:" + uri;
logger.warn(msg);

Object loginFromJsp = request.getAttribute("loginFromJsp");
Object mainFromJsp = request.getAttribute("mainFromJsp");
if(loginFromJsp != null && (Boolean)loginFromJsp) {
	CoreMainAction.forwardToDefaultLogin(request, response, false, null);
} else if(mainFromJsp != null && (Boolean)mainFromJsp) {
	CoreMainAction.forwardToDefaultMain(request, response);
} else {
//判断是否是Ajax请求
String x_requested_with = request.getHeader("X-Requested-With");
if(x_requested_with != null && x_requested_with.trim().length() >0){
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/plain");
	out.println("ajax:" + msg);
}else{
%>
<html>
<head>
<title>page not found</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body><h1>404 page not found</h1><h1><%= uri %></h1>
<p>请求的页面未找到。您可以选择返回到 <a href="../">主菜单</a>。或者选择在此休息一下，忘掉刚才的沮丧，欣赏一个美丽的图片？</p>
<p style="text-align: center; margin-top: 20px">
    <img style="border: 0" src="<%= request.getContextPath() %>/common/img/404.jpg"/>
</p>
</body></html>
<%}}%>
