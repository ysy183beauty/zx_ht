<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
 <title>重点人群信用查询 - 信用大理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=8;"/>
    <script type="text/javascript"
            src="../../r/cms/www/red/js/pager/js/jquery-1.10.2.min.js"></script>
</head>
<body>
<div class="wrap head_bg">
    <div class="index_focus">
        <%@ include file="include/head.jsp"%>
    </div>
    <div id="main">
        <div class="w">
            <div class="sit height_auto m-t-md">
                <ul >
                    <li>
                        <a href="">首页</a>
                    </li>
                    <li>&gt;</li>
                    <li>
                        <a href="">信用查询</a>
                    </li>
                </ul>
            </div>
            <div class="search height_auto text-center">
                <div class="m-t-md search_li">
                    <ul id="queryType">
                        <li><a href="/credit/credit_company/baseCompanyList?start=0&limit=10"><span>企业信用信息查询</span></a></li>
                        <li class="active"><a href="/credit/credit_company/creditPersonList"><span>重点人群信用查询</span></a></li>
                        <li><a href="/credit/credit_company/creditPersonList"><span>站内查询</span></a></li>
                    </ul>
                </div>
            </div>
            <div class="height_auto">
                <div class="m-t-md search_w">
                    <input class="search_input" placeholder="企业信用信息查询，请输入企业名称" />
                    <button class="serch_btn"><i class="fa fa-search"></i></button>
                </div>
            </div>
        </div>
        <div class="w">
            <div class="height_auto text-center">
                <c:choose>
                    <c:when test="${content==null}">
                        <div id="contentlist" style="font-size:14px;margin-top: 60px;">
                            ------------------------------------暂无信用记录-----------------------------------
                        </div>
                    </c:when>
                </c:choose>
                <%int i = 0; %>
                <c:forEach items="${content}" var="domain">
                    <div class="c1-body mt10">
                        <div class="c1-bline" style="padding:10px 0;">
                            <div class="f-left w" >
                                <img src="/r/cms/head-mark3.gif" alt="" class="img-vm" border="0" align="middle" />
                                <a class="orange" target="_self" href="javascript:show_hiddendiv(<%=i%>);"  id="lb_<%=i%>" >[${domain.lbmc}]	</a>
                                <ul id="hidden_ul<%=i%>" class="none">
                                    <c:forEach items="${domain.wbjZyXX}" var="wbjZyXX">
                                        <li >
                                            <c:set var="string1" value="${wbjZyXX.resouceXX[0].tablename}"/>
                                            <c:set var="tablename" value='${fn:replace(string1,"\'","\\\\\'")}' />
                                            <label>[${wbjZyXX.wbjmc}]</label>
                                            <a href="javascript:void(0)" onclick="openurl('/credit/credit_company/creditQyZyPageList?param_bs=${param_bs}&param_type=${param_type}&tablename=${tablename}&start=0&limit=10&resoucename=${wbjZyXX.resouceXX[0].resoucename}&wbjmc=${wbjZyXX.wbjmc}')" class="ml10" >${wbjZyXX.resouceXX[0].resoucename}</a>
                                            <a href="javascript:void(0)" onclick="openurl('/credit/credit_company/creditQyZyPageList?param_bs=${param_bs}&param_type=${param_type}&tablename=${tablename}&start=0&limit=10&resoucename=${wbjZyXX.resouceXX[0].resoucename}&wbjmc=${wbjZyXX.wbjmc}')" class="fr"  style="font-weight: bold;color:#2d72c3 ">查看详情</a></li>
                                    </c:forEach>
                                    <%i++; %>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="clear"></div>
                </c:forEach>
                <div class="pagination" id="fy"></div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
    <%@ include file="include/bottom.jsp"%>
</div>
<script type="text/javascript">
    $(function(){

        if(""== "${sessionScope.rdp_user}"){
            $("#contentlist").html("------------------------------------请先<a href=\"javascript:void(0);\" onclick='showLogin();' style='text-decoration: underline;color:#2e71b8;'>登录</a>,然后查看------------------------------------");
        }
        if(2=="${param.type}"&&""!= "${sessionScope.rdp_user}"&&"1"!="${sessionScope.rdp_user.IS_AUTHENTICATE}"&&"01"=="${sessionScope.rdp_user.level}"){
            $("#contentlist").html("------------------------请先<a href=\"/qyrz/index.jhtml\" style='text-decoration: underline;color:#2e71b8;'>企业实名认证</a>,然后查看信用记录------------------------");
        }
        if(2=="${param.type}"&&""!= "${sessionScope.rdp_user}"&&"2"=="${sessionScope.rdp_user.IS_AUTHENTICATE}"&&"01"=="${sessionScope.rdp_user.level}"){
            $("#contentlist").html("------------------------您的实名认证审核中,等认证通过才能查看------------------------");
        }
        if(1=="${param.type}"&&""!= "${sessionScope.rdp_user}"&&"01"=="${sessionScope.rdp_user.level}"&&"000000"!="${sessionScope.rdp_user.exist}"){
            $("#contentlist").html("------------------------请先<a href=\"/smrz/index.jhtml\" style='text-decoration: underline;color:#2e71b8;'>个人实名认证</a>,然后查看信用记录------------------------");
        }
        if(1=="${param.type}"&&""!= "${sessionScope.rdp_user}"&&"0201"=="${sessionScope.rdp_user.level}"&&"000000"!="${sessionScope.rdp_user.exist}"){
            $("#contentlist").html("------------------------您的实名认证审核中,等认证通过才能查看------------------------");
        }
        if(1=="${param.type}"&&""!= "${sessionScope.rdp_user}"&&"000000"=="${sessionScope.rdp_user.exist}"&&"01"=="${sessionScope.rdp_user.level}"){
            $("#contentlist").html("-----------------------您是企业用户，无法查看个人记录-----------------------");
        }
        if(2=="${param.type}"&&""!= "${sessionScope.rdp_user}"&&"01"!="${sessionScope.rdp_user.level}"){
            $("#contentlist").html("--------------------------您是个人用户，无法查看企业记录---------------------------");
        }
        show_hiddendiv(0);
    })
    function show_hiddendiv(n){
        $("#hidden_ul"+n).parent().find("img").attr("src","/r/cms/hm3_1.gif");
        $("#hidden_ul"+n).css("display","block");
        $("#lb_"+n).attr("href","javascript:hidden_showdiv("+n+")");
    }
    function hidden_showdiv(n){
        $("#hidden_ul"+n).parent().find("img").attr("src","/r/cms/head-mark3.gif");
        $("#hidden_ul"+n).css("display","none");
        $("#lb_"+n).attr("href","javascript:show_hiddendiv("+n+")");

    }
    function openurl(url){
        window.open(encodeURI(encodeURI(url)));
    }
</script>
</body>
</html>