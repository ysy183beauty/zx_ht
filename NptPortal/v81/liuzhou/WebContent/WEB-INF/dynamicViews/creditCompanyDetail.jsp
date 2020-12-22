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
                <h2 class="marginTop25 text-center">${type}</h2>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th colspan="2">${mc}</th>
                        </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="column" items="${head}" varStatus="status">
                        <tr>
                            <td class="tableTitle">${column.COMMENTS}</td>
                            <td>${data[column.COLUMN_NAME]}</td>
                        </tr>
                      </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
<%@ include file="include/bottom.jsp"%>
</body>
</html>