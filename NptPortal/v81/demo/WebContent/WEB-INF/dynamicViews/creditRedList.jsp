<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title>红榜信息 - 诚信常熟</title>
<meta http-equiv="X-UA-Compatible" content="IE=8;">
<script type="text/javascript"
	src="../../r/cms/www/red/js/pager/js/jquery-1.10.2.min.js"></script>

<style type="text/css">
.c1-bline {
	border-bottom: #999 1px dashed;
	border-top: 1px;
	margin: auto 20px;
	height: 15px;
	font-size: 12px
}

.f-left {
	float: left;
}

.f-right {
	float: right;
	color: #afafaf
}

.f-left a:hover {
	color: #385DA3;
}

.img-vm {
	vertical-align: middle
}

.pagination {
	height: 30px;
	line-height: 30px;
	text-align: center;
	clear: both;
	margin-top: 10px;
	font-size: 12px;
}

.orange {
	padding-right: 5px;
	color: #2c62b8;
	font-weight: bold;
	margin-left: 5px;
}

.warp_right .tit h3 {
	width: 150px;
	height: 31px;
	line-height: 31px;
	background: url(/${res}/img/newEdition/all.png) repeat-x -122px -77px
		!important;
	color: white;
	text-align: center;
	font-weight: normal;
	font-size: 16px;
}
</style>

</head>
<body>
	<div class="wrap head_bg">
		<div class="index_focus">
			<%@ include file="include/head.jsp"%>
		</div>
		<div id="main">
			<div class="main_left fl">
				<div class="list_menu">
					<h3>信用公示</h3>
					<ul>
						<li><a
							href="/credit/credit_publity/creditPublityList?start=0&limit=10">公示信息</a></li>
						<li><a class="current"
							href="/credit/credit_publity/creditRedList?start=0&limit=10">红榜信息</a></li>
						<li><a
							href="/credit/credit_publity/creditBlackList?start=0&limit=10">黑榜信息</a></li>
					</ul>
				</div>
			</div>
			<div class="main_right fr">
				<div class="list_article">
					<div class="location_box">
						<label class="fl">当前位置</label> <span class="fl"> <a
							href="/">首页</a> <span>></span> 信用公示 <span>></span> 红榜信息
						</span>
					</div>



					<div class="c1-body">
						<jsp:useBean id="dateValue" class="java.util.Date" />
						<c:forEach items="${content.list}" var="domain" varStatus="status">
							<jsp:setProperty name="dateValue" property="time"
								value="${domain.fbsj}" />
							<div class="c1-bline" style="padding: 10px 0;">
								<div class="f-left">
									<img class="img-vm" border="0" align="middle"
										src="/r/cms/head-mark3.gif"></img> <a class="orange"
										target="_blank"
										href="/credit/credit_publity/creditRedList?start=0&limit=20">[红榜信息]</a>
									<a
										href="/credit/credit_publity/creditPublityView?paramId=${domain.id}&start=0&limit=20&type=2&first=true"
										title="" target="_blank"><span style="color: black">${domain.xxmc}</span></a>
								</div>
								<div class="gray f-right">
								<%-- <fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd" /> --%>
									${domain.xxlywbj}
								</div>
								<div class="clear"></div>
							</div>
						</c:forEach>
					</div>
					<div class="pagination" id="fy"></div>

				</div>
			</div>

			<div class="clear"></div>
		</div>

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
			var url = "/credit/credit_publity/creditRedList";
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
								"<a disabled='disabled'>下一页</a> <a disabled='disabled'>尾页</a>");
			}
			if (count > 0 && pageIndex < total) {
				$("#fy").append(
						"<a href='" + url + "?start=" + (start + size)
								+ "&limit=" + size + "'>下一页</a> <a href='"
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
