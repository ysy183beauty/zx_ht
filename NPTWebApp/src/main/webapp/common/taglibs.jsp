<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="utf-8" contentType="text/html; charset=utf-8" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%><%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%><%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags" %><% request.setAttribute("randomData", new java.util.Random().nextInt());%><c:set var="random" value="${randomData}"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="wctx" value="${ctx}${applicationScope.webSourcePathKey}" />
<c:set var="themeName" value="${sessionScope.userViewStyleKey}" />
<c:set var="sctx" value="${wctx}/pub/style/${themeName}" />
<%response.setHeader("Pragma","No-cache");response.setHeader("Cache-Control","no-cache");response.setDateHeader("Expires",0);%> 