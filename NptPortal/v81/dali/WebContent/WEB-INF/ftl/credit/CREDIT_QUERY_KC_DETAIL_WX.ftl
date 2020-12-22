<#include "CommonUtil.ftl"/>
<#assign data = NPT_ACTION_RETURNED_JSON>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/js/pager/css/kkpager.css" />
<style type="text/css">
    .table-responsive{
        background-color:#fff;
    }
    .table-responsive th,.table-responsive td{
        font-size:12px;
        padding:1em 0;
    }
    .row{
        border-bottom:1px solid #dedede;
    }
    .row td:first-child{
        float:right;
    }
</style>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>重点人群详情</title>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/zqsjxq.css"/>
</head>
<body>
<#--<div class="wrap">-->
<#if data.dataArray??>
<div class="table-responsive">
<#--<div id="container">-->
<#--<div class="content">-->
<#--<div class="con_top">-->
<#--<div class="con_top_shu">-->
<#--<h2></h2>-->
<#--<div class="error">-->
<#--<img src="${ctx}/r/cms/www/red/img/sjxq/u87.png" alt=""/>信息纠错-->
<#--</div>-->
    <table  border="0" width="100%" cellpadding="0" cellspacing="0" table-layout="fixed">
        <#list data.dataArray as array>

            <tr class="row">
                <td class="">${array.title}：</td>
                <td class="" style="word-wrap:break-word;">${array.value!}</td>
            <tr>
            </tr>
            </tr>

        </#list>
    </table>
<#--</div>-->
<#--</div>-->
<#--</div>-->
<#--</div>-->
</div>
<#else>
暂无数据
</#if>
<#--</div>-->
</body>

</html>