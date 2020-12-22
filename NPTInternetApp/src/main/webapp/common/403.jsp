<%@page contentType="text/html; charset=utf-8"%>

<%
org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
String uri = (String)request.getAttribute("javax.servlet.forward.request_uri");
String msg = "您当前角色无权限查看此页面。请联系系统管理员，获得相应的权限。";
logger.warn(msg + uri);

// 判断是否是Ajax请求
String x_requested_with = request.getHeader("X-Requested-With");
if(x_requested_with != null && x_requested_with.trim().length() >0){
	response.setCharacterEncoding("UTF-8");
	response.setContentType("text/plain");
	out.println("ajax:" + msg);
}else{
%>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>对不起，您没有权限访问此页面！</title>
<content tag="heading"><%=msg %>此刻，让我们欣赏一个图片，放松一下好吧？</content>
<p style="text-align: center; margin-top: 20px">
    <img style="border: 0" src="<%= request.getContextPath() %>/common/img/403.jpg"/>
</p>
<%}%>