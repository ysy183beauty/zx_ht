<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
</head>
<body class="page-header-fixed">

<!-------------------------------------历史信用报告-------------------------------------------->
<div id="indexDiv">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>历史信用报告</div>
            <div class="tools">
                <a href="javascript:;" class="collapse"></a>
            </div>
        </div>
        <div class="portlet-body">
            <table class="table table-striped table-bordered table-hover" id="logList">
                <thead>
                <tr>
                    <th width="5%">序号</th>
                    <#--<th width="30%">报告名称</th>-->
                    <th width="25%">报告模板</th>
                    <th width="30%">报告生成时间</th>
                    <th width="10%">操作</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>

    </div>
</div>
<div id="detailDiv"></div>

<script>

    function showReportHistory(fileName) {
        window.location.href = ${ctx}"/npt/web/finereport/log/detail.${urlExt}?fileName=" + fileName;
    }
</script>
</body>
<!-- END BODY -->

</html>