<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${content.data.qymc}- 诚信大理</title>
<meta http-equiv="X-UA-Compatible" content="IE=8;"/>
<script type="text/javascript"	src="../../r/cms/www/red/js/pager/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../../r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css"	src="../../r/cms/www/red/js/pager/css/kkpager.css" />
</head>

<body>
<%@ include file="include/head.jsp"%>
<div class="main">
    <div class="w">
        <div class="sit height_auto m-t-md">
            <ul>
                <li>
                    <a href="/">首页</a>
                </li>
                <li>&gt;</li>
                <li>
                    <a href="/credit/credit_company/creditCompanyList?start=0&limit=10">信用查询</a>
                </li>
            </ul>
        </div>
            <div class="w">
                <h2 class="marginTop25 text-center">企业基础信息</h2>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th colspan="2">${content.QYMC}</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td class="tableTitle">企业名称</td>
                            <td>${content.QYMC}</td>
                        </tr>
                        <tr>
                            <td class="tableTitle">法定代表人</td>
                            <td>${content.FDDBR}</td>
                        </tr>
                        <tr>
                            <td class="tableTitle">工商注册号</td>
                            <td>${content.GSZCH}</td>
                        </tr>
                        <tr>
                            <td class="tableTitle">组织机构代码</td>
                            <td>${content.ZZJGDM}</td>
                        </tr>
                        <tr>
                            <td class="tableTitle">经营场所</td>
                            <td>${content.JYCS}</td>
                        </tr>
                        <tr>
                            <td class="tableTitle">开业日期</td>
                            <td id="zcrq">${content.KYRQ}</td>
                        </tr>
                        <tr>
                            <td class="tableTitle">经营范围</td>
                            <td>${content.JYFW}</td>
                        </tr>
                        <tr>
                            <td class="tableTitle">登记机关</td>
                            <td>大理市市场监督管理局</td>
                        </tr>
                    </tbody>
                </table>
                <a href="/credit/credit_company/creditCompanyTypeDetail?mc=${content.QYMC}" target="_blank" style="font-weight: bold;text-decoration: underline;font-size: 14px;color: #385DA3">企业信用记录查询</a>
            </div>
        </div>
    </div>
<%@ include file="include/bottom.jsp"%>
</body>
</html>