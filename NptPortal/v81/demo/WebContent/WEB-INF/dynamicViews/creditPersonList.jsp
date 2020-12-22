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
                        <li><a href="/credit/credit_company/baseCompanyList?start=0&limit=0"><span>企业信用信息查询</span></a></li>
                        <li class="active"><a href="/credit/credit_company/creditPersonList"><span>重点人群信用查询</span></a></li>
                        <li><a href="/search.jspx"><span>站内查询</span></a></li>
                    </ul>
                </div>
            </div>
            <div class="height_auto">
                <div class="m-t-md search_w">
                    <input class="search_input" placeholder="暂未开放" />
                    <button class="serch_btn"><i class="fa fa-search"></i></button>
                </div>
            </div>
        </div>
        <div class="w">
            <div class="height_auto text-center">

                        <div id="contentlist" style="font-size:14px;height: 300px; line-height: 300px;" class="text-center">
                            ------------------------------------暂未开放-----------------------------------
                        </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
    <%@ include file="include/bottom.jsp"%>
</div>
</body>
</html>