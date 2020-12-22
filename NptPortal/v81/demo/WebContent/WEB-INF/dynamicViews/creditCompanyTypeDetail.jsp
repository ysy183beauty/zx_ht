<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
	   <title>企业信用查询 - 信用大理</title>
<meta http-equiv="X-UA-Compatible" content="IE=8;"/>
<script type="text/javascript"
	src="../../r/cms/www/red/js/pager/js/jquery-1.10.2.min.js"></script>
    <style type="text/css">
        .c1-bline {
            border-top: 1px;
            margin: auto 20px;
            font-size: 12px;
            text-align: left;
        }
        .c1-bline a{
            color: #0e9aef;
        }

        .c1-bline ul{
            border-top: #999 1px dashed;
        }

        .c1-bline li{
            border-bottom: dashed 1px #d1d1d1;
            height: 36px;
            line-height: 36px;
            padding-left: 40px;
        }

        .none {
            display: none;
        }

        .f-left {
            float: left;
        }
        .f-left a:hover {
            color: #385DA3;
        }
        .Content {
            padding: 10px 10px 0 10px;
        }

        .Content_title {
            font-size: 28px;
            color: #2d72c3;
            text-align: center;
            height: 50px;
            line-height: 50px;
            border-bottom: #CCC 1px dotted;
        }

        .fr {
            float: right;
            margin-right:50px;
        }
        .orange {
            padding-right: 5px;
            height: 40px;
            line-height: 40px;
            color: #2c62b8;
            font-weight: bold;
            margin-left: 5px;
            font-weight: bold;
            font-size: 14px;
        }
    </style>
</head>
<body>
<%@ include file="include/head.jsp"%>
	<div class="wrap head_bg">
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
                            <li class="active"><a href="/credit/credit_company/baseCompanyList?start=0&limit=0"><span>企业信用信息查询</span></a></li>
                            <li ><a href="/credit/credit_company/creditPersonList"><span>重点人群信用查询</span></a></li>
                            <li><a href="/search.jspx"><span>站内查询</span></a></li>
                        </ul>
                    </div>
                </div>
                <div class="height_auto">
                    <div class="m-t-md search_w">
                        <input id="queryCondition"  class="search_input" placeholder="企业信用信息查询，请输入企业名称" />
                        <button class="serch_btn" onclick="searchInfo();"><i class="fa fa-search"></i></button>
                    </div>
                </div>
            </div>
			<div class="w">
				<div class="height_auto text-center">
                    <div class="Content text-center">
                        <div class="Content_title">${param.mc}</div>
                    </div>
                    <div class="w">
                        <div style="font-size: 16px;color: #0e9aef;height: 30px;text-align:left;line-height: 30px;margin-left: 20px;border-bottom: dashed 1px #d1d1d1;">
                            法人基础信息
                        </div>
                        <c:forEach var="d" items="${base}" ><!--map-->
                        <c:if test="${d.key=='data'}">
                            <div style="margin-left: 40px;margin-right: 50px;margin-top: 8px;">
                                <c:forEach var="d2" items="${d.value}" ><!--list-->
                                <table class="table table-bordered">
                                    <thead>
                                    <tr>
                                        <th colspan="2">${param.mc}</th>
                                    </tr>
                                    </thead>
                                    <c:forEach var="d3" items="${d2}" ><!--map-->
                                    <tr>
                                        <th class="tableTitle" >${base['title'][d3.key]}</th>
                                        <th style="font-weight: normal;">${d3.value}</th>
                                    </tr>
                                    </c:forEach>
                                </table>
                                </c:forEach>
                            </div>
                        </c:if>
                        </c:forEach>
                    </div class="w">
                <c:forEach var="map" items="${content}" varStatus="status">
                <div class=" mt10">
                    <div class="c1-bline height_auto" style="padding:10px 0;">
                        <div class="f-left w">
                            <ul id="hidden_ul${status.index}" >
                                <c:forEach var="data" items="${map.value}"><!--list-->
                                    <c:forEach var="d" items="${data}" varStatus="index"><!--map-->
                                            <c:if test="${d.key=='des'}">
                                                <!--map-->
                                                <li style="margin-left: -40px;font-size: 16px;">
                                                    <label>[${d.value.SJLYDW}]</label>
                                                    <a onclick="showHideTable('${d.value.BYWM}')" class="ml10">${d.value.BZWM}</a>
                                                    <a onclick="showHideTable('${d.value.BYWM}')" class="fr" style="font-weight: bold;color:#2d72c3 ">详情</a>
                                                </li>
                                            </c:if>
                                            <c:if test="${d.key=='data'}">
                                                <div id="${data.des.BYWM}" style="margin-left: 20px;margin-right: 70px;margin-top: 8px;display: none;font-size: 14px;">
                                                <c:forEach var="d2" items="${d.value}" varStatus="index2"><!--list-->
                                                    <table class="table table-bordered">
                                                        <thead>
                                                        <tr>
                                                            <th colspan="2">${param.mc}</th>
                                                        </tr>
                                                        </thead>
                                                        <c:forEach var="d3" items="${d2}" varStatus="index3"><!--map-->
                                                                <tr>
                                                                    <th class="tableTitle" >${data['title'][d3.key]}</th>
                                                                    <th style="font-weight: normal;">${d3.value}</th>
                                                                </tr>
                                                        </c:forEach>
                                                    </table>
                                                </c:forEach>
                                                </div>
                                            </c:if>
                                </c:forEach>
                                </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
                </c:forEach>
				<div class="clear"></div>
						<div class="pagination" id="fy"></div>
				</div>
		</div>
	</div>
<%@ include file="include/bottom.jsp"%>
<script type="text/javascript">
    var baseUrl = "/credit/credit_company/baseCompanyList";

    //查询
    function searchCompany(name,code) {
        var url = baseUrl + '?start=0&limit=10&qymc=' + name + '&gszch=' + code;
        location.href = url;
    }

    function searchInfo(){
        var id =  $('#queryType>li[class=active]').attr('id');
        var condition = $('#queryCondition').val();
        if(condition=='' || condition==undefined){
            alert("请输入查询条件！");
            return;
        }
        var patrn=/[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi;
        if(!patrn.exec(condition)){
            searchCompany('',condition);
        }else{
            searchCompany(condition,'');
        }
    }

    $(function(){
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
    function showHideTable(id){
        if($('#'+id).is(":visible")){
            $('#'+id).hide();
        }else{
            $('#'+id).show();
        }
    }
    function openurl(url){
        window.open(encodeURI(encodeURI(url)));
    }
    function creditlogin(){
        $("#modaltrigger_login").trigger("click");
    }

</script>
</body>
</html>