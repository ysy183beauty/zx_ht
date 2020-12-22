<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
    <#assign reportDisplay=findReportDisplay>
</head>
<body class="page-header-fixed">
<div id="reportDiv">
<#if REPORT??>
     <#-- 判断是跳转到报表，还是跳转到action上 -->
    <iframe id="reportiframe" width='100%' height='1150' class='border bgWhite'></iframe>
<#else>
    该菜单没有绑定报表
</#if>
</div>
<script type="text/javascript">
   //页面加载完成的事件
    $(function () {
        var url='${REPORT.rptPath}';
        var findReportDisplay='${reportDisplay}';
        var windowUrl="";
        if(1==parseInt(findReportDisplay)){
            if(url!=null&&url.indexOf(".cpt")>0){
                windowUrl="${ctx}/ReportServer?id=${REPORT.id!}&reportlet=${REPORT.rptPath!}";
                $("#reportiframe").attr("src",windowUrl);
            }else if (url!=null&&url.indexOf(".frm")>0){
                windowUrl="${ctx}/ReportServer?id=${REPORT.id!}&formlet=${REPORT.rptPath!}";
                $("#reportiframe").attr("src",windowUrl);
            }
        }else{
            window.location.href="${ctx}/findreport/${REPORT.reservedField06}.action";
        }
    });
</script>
</body>
</html>
