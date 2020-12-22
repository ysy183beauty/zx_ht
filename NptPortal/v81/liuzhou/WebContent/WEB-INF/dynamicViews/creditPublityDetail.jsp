<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${content.data.xxmc}- 诚信大理</title>
<meta http-equiv="X-UA-Compatible" content="IE=8;"/>
    <link rel="stylesheet" href="../../r/cms/www/red/css/bootstrap.css" />
    <link rel="stylesheet" href="../../r/cms/www/red/css/comm.css" />
    <link rel="stylesheet" href="../../r/cms/www/red/css/font-awesome.css" />
    <link rel="stylesheet" href="../../r/cms/www/red/css/style.css" />
    <link rel="stylesheet" href="../../r/cms/www/red/css/main.css" />
    <script type="text/javascript" src="../../r/cms/www/red/js/jquery-2.1.0.js" ></script>
    <script type="text/javascript" src="../../r/cms/www/red/js/jquery.SuperSlide.2.1.1.js"></script>
    <script type="text/javascript" src="../../r/cms/www/red/js/style.js" ></script>
</head>
<body>
	<div class="wrap head_bg">
		<%@ include file="include/head.jsp"%>
		<div id="main">
			<div class="main">
				<div class="list_article">
					<div class="location_box">
						<label class="fl">当前位置</label>
						 <span class="fl"> 
							<a href="/">首页</a> <span>></span> 信用公示 <span>></span> ${wz}
						</span>
						<div class="fr mr20">
							<span >本文检索：</span>
							<input class="contentText" name="q" value="${queryword}" type="text" id="queryword"
					   maxlength="24"/>
					   <a href="javascript:queryword()" class="contentSearch ml10" id="contentSearch">搜索</a>
						</div>
					</div>
					<div class="Content">

						<c:choose>
							<c:when test="${(fn:length(content.data.columnMcList))<=2}">
								<c:set var="width" value="300" />
							</c:when>
							<c:when
								test="${(fn:length(content.data.columnMcList))<=4&&(fn:length(content.data.columnMcList))>2}">
								<c:set var="width" value="200" />
							</c:when>
							<c:when
								test="${(fn:length(content.data.columnMcList))<=6&&(fn:length(content.data.columnMcList))>4}">
								<c:set var="width" value="135" />
							</c:when>
							<c:otherwise>
								<c:set var="width"
									value="(890/${fn:length(content.data.columnMcList)})-10 " />
							</c:otherwise>
						</c:choose>

						<div class="tit">
							<h3 id="syc">${content.data.xxmc}</h3>
							<span><%-- 发布时间： ${content.data.fbsj}&nbsp; --%>
								来源：${content.data.lywbj} &nbsp; 浏览次数：${content.data.visits}次 </span>
						</div>
						<div class="content">
							<table class="tb">
								<thead>
									<tr>
										<th width="25">序号</th>
										<c:forEach items="${content.data.columnMcList}" var="domain"
											varStatus="status">
											<th width="${width}">${domain}</th>
										</c:forEach>
									</tr>
								</thead>
								<tbody>
									<%  int a =Integer.parseInt(request.getParameter("start"))+1; %>
									<jsp:useBean id="dateValue" class="java.util.Date" />
											
									<c:forEach items="${content.data.column_data.list}"
										var="domain" varStatus="status">
										<tr>
											<td><%=a%></td>
											<%a++; %>
											<c:forEach items="${content.data.columnList}" var="i">
												<c:choose>
													<c:when
														test="${domain.get(i).getClass().name eq 'java.lang.Long'}">
														<jsp:setProperty name="dateValue" property="time"
															value="${domain.get(i)}" />
														<td><fmt:formatDate value="${dateValue}"
																pattern="yyyy-MM-dd" /></td>
													</c:when>
													<c:otherwise>
														<td>${domain.get(i)}</td>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</tr>
									</c:forEach>
								</tbody>
							</table>
							<c:choose>
								<c:when test="${fn:length(content.data.column_data.list)==0}">
								
									<div style="text-align: center; font-size: 14px;margin-top: 10px;">没有相关信息</div>
								</c:when>
							</c:choose>
							<c:choose>
								<c:when test="${content.data.column_data.count>param.limit}">
									<div id="kkpager"></div>
								</c:when>
							</c:choose>
							<ul class="pre-next-page">
								<c:choose>
									<c:when test="${content.data.lastXxmc eq ''}">
										<li class="f-right"><strong>上一篇：</strong><a>无</a></li>
									</c:when>
									<c:otherwise>
										<li class="f-right"><strong>上一篇：</strong><a
											href="/credit/credit_publity/creditPublityView?paramId=${content.data.lastId}&start=0&limit=20&type=${type}&first=true"
											target='_self'>${content.data.lastXxmc}</a></li>
									</c:otherwise>
								</c:choose>
								<c:choose>
									<c:when test="${content.data.nextXxmc eq ''}">
										<li class="f-right"><strong>下一篇：</strong><a>无</a></li>
									</c:when>
									<c:otherwise>
										<li class="f-right"><strong>下一篇：</strong><a
											href="/credit/credit_publity/creditPublityView?paramId=${content.data.nextId}&start=0&limit=20&type=${type}&first=true"
											target='_self'>${content.data.nextXxmc}</a></li>
									</c:otherwise>
								</c:choose>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>

		<%@ include file="include/bottom.jsp"%>
	</div>


	<script type="text/javascript">
	function queryword() {
		var queryword = $('#queryword').val();
		location.href = "/credit/credit_publity/creditPublityView?paramId=${param.paramId}&start=0&limit=20&type=${type}&first=false"+"&queryword="+queryword;
	}
		//生成分页控件  
		kkpager
				.generPageHtml({
					pno : '<fmt:formatNumber type="number" value="${param.start / param.limit + 1-0.49}" maxFractionDigits="0" groupingUsed="false"/>',
					mode : 'link', //可选，默认就是link
					//总页码
					total : '<fmt:formatNumber type="number" value="${(content.data.column_data.count+param.limit-1)/param.limit-0.49}" maxFractionDigits="0" groupingUsed="false"/>',
					//总数据条数
					totalRecords : '${content.data.column_data.count}',
					//链接算法
					getLink : function(n) {
						return '/credit/credit_publity/creditPublityView?paramId=${param.paramId}&start='+ (n - 1) * ${param.limit}
						+'&limit=${param.limit}&type=${type}&queryword=${queryword}#syc';
					}
				});
	</script>
</body>
</html>