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
							<c:if test="${content.success eq true}">
								<thead>
									<tr>
										<th colspan="2">${content.data.qymc}</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td class="tableTitle">企业名称</td>
										<td>${content.data.qymc}</td>
									</tr>
									<tr>
										<td class="tableTitle">法定代表人</td>
										<td>${content.data.fddbr}</td>
									</tr>
									<tr>
										<td class="tableTitle">工商注册号</td>
										<td>${content.data.gszch}</td>
									</tr>
									<tr>
										<td class="tableTitle">组织机构代码</td>
										<td>${content.data.zzjgdm}</td>
									</tr>
									<tr>
										<td class="tableTitle">经营场所</td>
										<td>${content.data.jycs}</td>
									</tr>
									<tr>
										<td class="tableTitle">注册日期</td>
										<td id="zcrq"></td>
									</tr>
									<tr>
										<td class="tableTitle">经营范围</td>
										<td>${content.data.jyfw}</td>
									</tr>
									<tr>
										<td class="tableTitle">登记机关</td>
										<td>常熟市市场监督管理局</td>
									</tr>
									<!-- <tr>
										<td>数据的更新日期</td>
										<td>${content.data.gxrq}</td>
									</tr>
									 -->
								</tbody>
							</c:if>
						</table>
						<a href="/credit/credit_company/creditQyxyxxDetail?type=2" target="_blank" style="font-weight: bold;text-decoration: underline;font-size: 14px;color: #385DA3">企业信用记录查询</a>

					</div>
				</div>
			</div>
		<%@ include file="include/bottom.jsp"%>
	<script type="text/javascript">
$(function(){
	var djzcrq = new Date(${content.data.djzcrq});
	djzcrq = djzcrq.getFullYear()+"-"+(djzcrq.getMonth()+1)+"-"+djzcrq.getDate();
	$("#zcrq").html(djzcrq);
})
    //生成分页控件
    kkpager.generPageHtml({
        pno: '<fmt:formatNumber type="number" value="${param.start / param.limit + 1 - 0.49}" maxFractionDigits="0" groupingUsed="false"/>',
        mode : 'link', //可选，默认就是link
        //总页码
        total: '<fmt:formatNumber type="number" value="${(content.count+param.limit-1)/param.limit - 0.49}" maxFractionDigits="0" groupingUsed="false"/>',
        //总数据条数
        totalRecords: '${content.count}',
        //链接算法
        getLink: function (n) {
            return '/credit/credit_company/creditCompanyList?start=' + (n - 1) * ${param.limit} +'&limit=${param.limit}&qymc=${param.qymc}&gsczh=${param.gsczh}';
        }
    });

//id；表格的id
function kkpagerinitbyId(id,pno,totalRecords,limit){
    var total=parseInt((totalRecords-1)/limit)+1;
    kkpagerGis.pagetableid = id;
    kkpagerGis.total=total;
    kkpagerGis.totalRecords=totalRecords;
    kkpagerGis.pagelimit = limit;
    //生成分页控件
    kkpagerGis.generPageHtml({
        pno: pno,
        mode : 'link', //可选，默认就是link
        //总页码
        total: total,
        //总数据条数
        totalRecords: totalRecords,
        //链接算法
        click: function (n,sum,pagelimit,tid) {
            //获取第n页数据
            var small = pagelimit*(n-1);
            var large = pagelimit*n-1;

            for(var i=0;i<sum;i++){
                var tr = $("#"+tid+"tr"+i);
                if(i>=small && i<=large){
                    tr.css("display","table-row");
                }else{
                    tr.css("display","none");
                }
            }
            this.selectPage(n);//页码跳转
        }
    },true);
}
//-------------------------------------分页控件初始化结束---------------------------------------
</script>
</body>
</html>