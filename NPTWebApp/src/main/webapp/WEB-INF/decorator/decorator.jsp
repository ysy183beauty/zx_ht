<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="wctx" value="${ctx}${applicationScope.webSourcePathKey}"/>
<c:set var="themeName" value="${sessionScope.userViewStyleKey}"/>
<c:set var="sctx" value="${wctx}/pub/CreditStyle/resource/theme/${themeName}"/>
<c:set var="platformName" value="${_styleParam.platformName }"/>
<c:set var="platformLogo" value="${wctx}/${_styleParam.styleLogo }"/>
<c:set var="platformCopyright" value="${_styleParam.copyright }"/>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>
    <title>${platformName}</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script type="text/javascript" src="${wctx}/pub/matrix/js/jquery.min.js"></script>

    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>

    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/bootstrap-responsive.min.css" rel="stylesheet"
          type="text/css"/>

    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/font-awesome.min.css" rel="stylesheet"
          type="text/css"/>

    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/style-metro.css" rel="stylesheet" type="text/css"/>
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/style-responsive.css" rel="stylesheet"
          type="text/css"/>
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/uniform.default.css" rel="stylesheet"
          type="text/css"/>
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/jquery.gritter.css" rel="stylesheet" type="text/css"/>
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/daterangepicker.css" rel="stylesheet"
          type="text/css"/>
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/fullcalendar.css" rel="stylesheet" type="text/css"/>
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/jqvmap.css" rel="stylesheet" type="text/css"
          media="screen"/>
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/jquery.easy-pie-chart.css" rel="stylesheet"
          type="text/css" media="screen"/>
    <link rel="shortcut icon" href="${wctx}/pub/index/xin.ico"/>
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/bootstrap-modal.css" rel="stylesheet"
          type="text/css"/>

    <link rel="stylesheet" type="text/css"
          href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/multi-select-metro.css"/>
    <link rel="stylesheet" href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/DT_bootstrap.css"/>
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${sctx}.css" rel="stylesheet" type="text/css"/>

</head>
<style type="text/css">

</style>

<sitemesh:write property='head'/>
</head>
<body class="page-header-fixed">
<div class="header navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="row-fluid">
            <a class="brand" href="${ctx}/index.html">
                <%--${platformName}--%>
                信用信息管理平台
            </a>
            <a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse">
                <img src="${wctx}/pub/CreditStyle/bootstrap/frame/media/image/menu-toggler.png" alt="">
            </a>
            <ul class="nav pull-right">
                <li class="dropdown user">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown"><span
                            class="username">${currUserName}</span> <i
                            class="icon-angle-down"></i> </a>
                    <ul class="dropdown-menu">
                        <li><a href="${ctx}/security/user/enterEditPassword.action"><i class="icon-user"></i> 修改密码</a>
                        </li>
                        <c:if test="${isAdmin}">
                            <li class="divider"></li>
                            <li><a href="${ctx}/security/menuManager.action"><i class="icon-tasks"></i> 调整菜单</a></li>
                        </c:if>
                        <li><a href="${ctx}/j_spring_security_logout"><i class="icon-key"></i> 退出</a></li>
                    </ul>
                </li>

            </ul>
        </div>
    </div>
</div>

<div class="page-container">
    <div class="page-sidebar nav-collapse collapse">
        <ul class="page-sidebar-menu">
            <c:forEach var="m" items="${firstLevelMenu}">
                <li id="${m.id}">
                    <a href="javascript:void(0);" onclick="toMenu('${m.url}' )">
                        <i class="icon-folder-open"></i>
                        <span class="title">${m.title}</span>
                        <span class="arrow"></span>
                        <span class="selected"></span>
                    </a>
                    <ul class="sub-menu">
                        <c:forEach var="m2" items="${secondLevelMenu}">
                            <c:if test="${m2.parent==m.id}">
                                <li id="${m2.id}">
                                    <a href="javascript:void(0);" onclick="toMenu('${m2.url}' )">
                                        <i class="icon-bookmark-empty"></i>
                                        <span>${m2.title}</span>
                                    </a>
                                </li>
                            </c:if>
                        </c:forEach>
                    </ul>
                    </a>
                </li>
            </c:forEach>
            <li>
                <div class="sidebar-toggler hidden-phone"></div>
            </li>
        </ul>
    </div>

    <div class="page-content">
        <%--<div class="color-panel hidden-phone">--%>
        <%--<div class="color-mode-icons icon-color" ></div>--%>
        <%--<div class="color-mode-icons icon-color-close"></div>--%>
        <%--<div class="color-mode">--%>
        <%--<p>更换主题</p>--%>
        <%--<ul class="inline" id="themeList">--%>
        <%--<li class="color-black  color-default"--%>
        <%--data-style="default"></li>--%>
        <%--<li class="color-blue" data-style="bright"></li>--%>
        <%--</ul>--%>
        <%--</div>--%>
        <%--</div>--%>
        <ul class="breadcrumb">
            <li><i class="icon-home"></i> <a href="${ctx}/index.action" id="parentTitle">主页</a></li>
            <li id="childTitle">>> ${positionTitle}</li>
            <%--<li class="pull-right mr50">--%>
            <%--本页检索：<input type="text" id="searchText" class="space12 no-margin " placeholder="请输入关键字">--%>
            <%--<button type="button" class="btn mini green" onclick="searchAll()">搜索</button>--%>
            <%--</li>--%>
        </ul>
        <div class="container-fluid">
           <sitemesh:write property="body"/>
        </div>
    </div>
</div>

<div class="footer">
    <div class="footer-inner">${platformCopyright}</div>
    <div class="footer-tools"> <span class="go-top">
			<i class="icon-angle-up"></i>
			</span></div>
</div>
<div class="loadDiv">
    <div class="loadBg"></div>
    <img src="${wctx}/pub/CreditStyle/bootstrap/frame/media/image/fancybox_loading.gif" alt="">
</div>
<div id="successInfo" class="alert alert-success fade"></div>
<div id="warningInfo" class="alert alert-error fade"></div>

<%--<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/colResizable-1.5.min.js" type="text/javascript"></script>--%>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery-migrate-1.2.1.min.js"
        type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery-ui-1.10.1.custom.min.js"
        type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/bootstrap.min.js" type="text/javascript"></script>
<!--[if lt IE 9]>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/excanvas.min.js"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/respond.min.js"></script>
<![endif]-->
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.slimscroll.min.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.cookie.min.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.uniform.min.js" type="text/javascript"></script>

<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.vmap.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.vmap.russia.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.vmap.world.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.vmap.europe.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.vmap.germany.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.vmap.usa.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.vmap.sampledata.js"
        type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.flot.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.flot.resize.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.pulsate.min.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/date.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/daterangepicker.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.gritter.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/fullcalendar.min.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.easy-pie-chart.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.sparkline.min.js" type="text/javascript"></script>

<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.dataTables.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/DT_bootstrap.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/table-managed.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/select2.min.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/bootstrap-modal.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/bootstrap-modalmanager.js"
        type="text/javascript"></script>

<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/app.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/index.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/form-components.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/ui-modals.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/table-editable.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/moment.min.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/daterangepicker.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/date.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/form-components.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/echarts/echarts.min.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/index.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/charts.js"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.multi-select.js" type="text/javascript"></script>
<script src="${wctx}/pub/CreditStyle/jquery/Sortable.min.js" type="text/javascript"></script>

<script type="text/javascript" src="${wctx}/pub/CreditStyle/init.js"></script>
<script>
//    主页判断
   $(function () {
       var url=window.location.href;
       var arrUrl=url.split("//");
       var arrSceurl=arrUrl[1].split("/");
       arrSceurl.shift();
       var str=arrSceurl.join("/");
//       console.log(str);
       if(str == "index.action"){
           $("#childTitle").html(">>");
       }
   });

    /**
     * 显示操作返回信息
     * **/
    function returnInfo(flag, data) {
        if (flag) {
            $("#successInfo").addClass("in").html(data);
        }
        else {
            $("#warningInfo").addClass("in").html(data);
        }
        setTimeout(function () {
            $("#successInfo").removeClass("in");
            $("#warningInfo").removeClass("in");
        }, 3000);
    }
    /**
     * 更换主题
     * */
    $("#themeList>li").click(function () {
        $.ajax({
            type: 'POST',
            url: '${ctx}/changeUserStyle.action?userStyle=' + $(this).attr("data-style"),
            success: function (msg) {
                if (msg == "success") {
//                    returnInfo(true, "样式修改提交成功!");
                    window.top.location.reload();
                } else {
                    alert(msg, "密码修改失败！");
                }
            },
            error: function (msg) {
                alert(msg, "提交失败,请重试!多次失败请与管理员联系!");
            }
        });
    });


    function searchAll() {
        clearSelection();
        var searchText = $('#searchText').val();
        if ("" != searchText) {
            var html = $("#filterInfo").html();
            var regExp = new RegExp(searchText, 'g');
            var newHtml = html.replace(regExp, '<span class="highlight">' + searchText + '</span>');
            $("#filterInfo").html(newHtml);
        }

    }
    function clearSelection() {
        $("#filterInfo").find('.highlight').each(function () {
            $(this).replaceWith($(this).html());
        });

    }
    jQuery(document).ready(function () {

        App.handleTheme();
        Index.initCharts();
        Index.initMiniCharts();
        UIModals.init();
        App.handlePortletTools();
        App.handleSidebarToggler();
        App.handleGoTop();

    });
</script>

<!----------------------------------------------------------------------------------------------------------->
<script type="text/javascript">
    $.ajaxSetup({
        timeout: 3000,
        success: function (data) {
            //alert("success");
        },
        error: function (xhr, status, e) {
            //alert("error");
        },
        complete: function (xhr, status) {
            var sessionstatus = (xhr.getResponseHeader('sessionstatus'));
            if (sessionstatus == "timeout" || sessionstatus == "expired") {
                try {
                    var json = $.parseJSON(xhr.responseText);
                    alert("登录超时, 请重新登录");
                } catch (err) {
                }
                window.top.location = "${ctx}/login.action";
            }
        },
        //发送请求前触发
        beforeSend: function (xhr) {
        }
    });

</script>
<script type="text/javascript">
    // This function is called from the pop-up menus to transfer to
    // a different page. Ignore if the value returned is a null string:
    function goPage(newURL) {
        // if url is empty, skip the menu dividers and reset the menu selection to default
        if (newURL != "") {
            // if url is "-", it is this page -- reset the menu:
            if (newURL == "-") {
                resetMenu();
            }
            // else, send page to designated URL
            else {
                document.location.href = newURL;
            }
        }
    }

    // resets the menu selection upon entry to this page:
    function resetMenu() {
        document.gomenu.selector.selectedIndex = 1;
    }
    function toMenu(url) {
        document.location.href = "${ctx}" + url;
    }
    var curentMenuId = '${curentMenuId}';
    var directoryMenuId = '${directoryMenuId}';
    if (curentMenuId) {

        $("#" + curentMenuId).addClass('active');
        $("#" + curentMenuId).parent().parent().addClass('active');
        $("#" + curentMenuId).parent().show();
        if (curentMenuId != directoryMenuId) {

            $("#" + directoryMenuId).addClass('active');
            $("#" + directoryMenuId).children('ul').show();
        }
    }

</script>
</body>

</html>
