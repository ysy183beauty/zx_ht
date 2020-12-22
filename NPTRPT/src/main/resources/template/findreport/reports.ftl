<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
<#assign reportDisplay=findReportDisplay>
<style>
    .host {
        padding-top: 3px;
        text-align: right;
        font-size: 12px;
        letter-spacing: -1px;
        font-weight: 300;
        color: #fff;
    }
    div.title {
        padding-top: 12px;
        text-align: right;
        text-overflow:ellipsis;
        font-size: 21px;
        letter-spacing: -1px;
        font-weight: 300;
        color: #fff;
        margin-bottom: 10px;
        overflow: hidden;
    }
    .note {
        text-align: right;
        font-size: 12px;
        letter-spacing: 0;
        font-weight: 300;
        color: #fff;
        width:100%;
        min-height:2em;
        -ms-text-overflow: ellipsis;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    .dashboard-stat .details{
        float:right!important;
        margin-right:10px;
        white-space: nowrap;
        text-overflow:ellipsis;
        width:53%;
        overflow:hidden;
    }
    .dashboard-stat .visual{
        width:0px!important;
    }
</style>
</head>
<body class="page-header-fixed">
<div>
    <div class="portlet box boxTheme">
		<div class="portlet-title" >
			<div class="caption">
				<i class="icon-file"></i>
				报表查看
			</div>
            <div class="tools">
                <a href="javascript:;" class="collapse"></a>
            </div>
		</div>
		<div class="portlet-body">
			<div class="row-fluid">
			<#list reports as c>
			<#if c?index%3==0 && c?index!=0>
			</div>
            <div class="row-fluid">
			</#if>
				<#assign urlDefine=''>
				<#if reportDisplay==1>
					<#assign urlDefine=c.rptPath>
				<#else>
					<#assign urlDefine=c.reservedField06>
				</#if>
				<div class="span4 responsive" data-tablet="span6" data-desktop="span4" style="cursor:pointer"
                     data-id='${c.id!}' data-define='${urlDefine!}' tabindex="${c?index}">
					<div class="dashboard-stat yellow" style="border-radius: 3px;">
						<div class="visual"> <i class="icon-bar-chart"></i> </div>
						<div class="details">
                            <div class="host">
								<#list reportsHost as d>
								<#if d.code == c.rptHost!>
								${d.title}
								</#if>
								</#list>
							</div>
							<div class="title" title="${c.rptName!}">${c.rptName!}</div>
                            <div class="note" title="${c.rptNote!}">
								<#if (c.rptNote!)?length gt 20>
								${(c.rptNote!)?substring(0,20)}...
								<#else>
								${(c.rptNote!)}
								</#if>
							</div>
						</div>
					</div>
				</div>
			</#list>
			</div>
                <div id="pagination"></div>
		</div>
	</div>
</div>
<div id="reportDiv" style="display: none">
    <iframe id="reportiframe" width='100%' height='1150' class='border bgWhite'></iframe>
</div>
<script type="text/javascript">
var pageSize = 9;
function selectCallback(pageNumber) {
    $(".responsive").hide();
    for(var i=pageNumber*pageSize;i<(pageNumber+1)*pageSize;i++){
        $(".responsive[tabindex="+i+"]").show();
    }
}

(function(){
	$(".responsive").click(function(){
		//判断url是action地址还是报表地址
		var url=$(this).attr("data-define");
		if(url!=null&&(url.indexOf("cpt")>0||url.indexOf("frm")>0)){
			showReport($(this).attr("data-id"), url);
		}else{
			showReportDetail($(this).attr("data-id"),url);
		}
	});

    selectCallback(0);
    $("#pagination").pagination(${reports?size}, {
        callback: selectCallback,
        prev_text: "← 上一页",
        next_text: "下一页 →",
        items_per_page:pageSize,// 每页数量
        num_edge_entries: 2,//两侧首尾分页条目数
        num_display_entries: 8,//连续分页主体部分分页条目数
        current_page:0//当前页索引
    });
})();

function showReport(id, define){
    var url = "${ctx}/ReportServer?id="+id;
    if(define.lastIndexOf(".cpt") != -1){ // cpt
        url += "&reportlet=" + define;
    }else{ // frm
        url += "&formlet=" + define;
    }
	$("#reportiframe").attr("src", url);
    $(".collapse").trigger('click');
    $("#reportDiv").show();
}

/**
 * 修改后：报表列表跳转的js
 */
function showReportDetail(id, define) {
	var url="${ctx}/findreport/"+define+".action";
	window.location.href=url;
}
</script>
</body>
</html>