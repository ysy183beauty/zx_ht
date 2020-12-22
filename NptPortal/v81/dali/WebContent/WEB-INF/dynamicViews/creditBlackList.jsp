<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>公示信息 - 信用大理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=8;">

    <script type="text/javascript"
            src="../../r/cms/www/red/js/pager/js/jquery-1.10.2.min.js"></script>

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
                    <a href="">信用查询</a>
                </li>
            </ul>
        </div>
        <div class="search height_auto text-center">
            <div class="m-t-md search_li">
                <ul>
                    <li class="active"><span>企业基础信息查询</span></li>
                    <li><span>企业信用信息查询</span></li>
                    <li><span>重点人群信用查询</span></li>

                </ul>
            </div>
        </div>
        <div class="height_auto">
            <div class="m-t-md search_w">
                <input class="search_input" placeholder="企业信用查询，请输入企业名称" />
                <button class="serch_btn"><i class="fa fa-search"></i></button>
            </div>
        </div>

    </div>
    <div class="w">
        <div class="w740 pull-right">
            <div class="m-t-md">
                <div class="g_tit">
                    <span>企业实名流程</span>
                </div>
                <div class="zx_tab height_auto">
                    <div class="bd m-t-xs">
                        <ul>
                            <jsp:useBean id="dateValue" class="java.util.Date" />
                            <c:forEach items="${content.list}" var="domain" varStatus="status">
                                <jsp:setProperty name="dateValue" property="time"
                                                 value="${domain.fbsj}" />
                                <li>
                                    <dl>
                                        <dt>
                                            <a href="/credit/credit_publity/creditPublityView?paramId=${domain.id}&start=0&limit=20&type=1&first=true" target="_blank">${domain.xxmc}</a>
                                        </dt>
                                        <dd>${domain.xxlywbj}</dd>
                                    </dl>
                                    <div style="clear:both"></div>
                                </li>
                            </c:forEach>
                        </ul>
                        <div class="clear"></div></div>

                    <div style="clear: both"></div>
                    <div class="page text-center m-t-lg" >
                        <ul id="fy">

                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="clear"></div>

        <%@ include file="include/bottom.jsp"%>
    </div>
    <script type="text/javascript">
        //生成分页
        $(function() {
            var size = 10; //每页显示记录条数
            var start = ${param.start}
                    ; //起始记录索引
            var count = ${content.count}
                    ; //记录总条数
            var total = parseInt((count - 1) / size) + 1; //总页数
            var pageIndex = (start / size) + 1; //当前页
            var url = "/credit/credit_publity/creditPublityList";
            var str;
            $("#fy").empty();
            $("#fy").append(
                    "共" + count + "记录 " + pageIndex + "/" + total
                    + "页&nbsp;&nbsp;");
            if (count == 0) {
                $("#fy").append(
                        "<span style='font-size: 16px;'>没有查询到相关信息!</span>");
            }
            if (count > 0 && start == 0) {
                $("#fy")
                        .append(
                                "<a disabled='disabled'>首页</a> <a disabled='disabled'>上一页</a>&nbsp;&nbsp;");
            }
            if (count > 0 && start > 0) {
                $("#fy").append(
                        "<a href='" + url + "?start=0&limit=" + size
                        + "'>首页</a> <a href='" + url + "?start="
                        + (start - size) + "&limit=" + size
                        + "'>上一页</a>");
            }
            if (count > 0 && pageIndex == total) {
                $("#fy")
                        .append(
                                "<a disabled='disabled'> 下一页</a> <a disabled='disabled'>尾页</a>");
            }
            if (count > 0 && pageIndex < total) {
                $("#fy").append(
                        "<a href='" + url + "?start=" + (start + size)
                        + "&limit=" + size + "'> 下一页</a> <a href='"
                        + url + "?start=" + (total - 1) * size
                        + "&limit=" + size + "'>尾页</a>");
            }

            if (count > 0) {
                str = "&nbsp;&nbsp;第<select onchange='selectfy(\"" + url
                        + "\",this.value," + size + ")'>";
                for (var i = 1; i <= total; i++) {
                    var temp = (i - 1) * size;
                    if (i == pageIndex) {
                        str += "<option selected='selected' value =" + temp + ">"
                                + i + "</option>";
                    } else {
                        str += "<option value ="+temp+">" + i + "</option>";
                    }
                }
                str = str + "</select>页";
                $("#fy").append(str);
            }
        });

        function selectfy(url, start, limit) {
            window.location = url + "?start=" + start + "&limit=" + limit;
        }
    </script>

</body>
</html>
