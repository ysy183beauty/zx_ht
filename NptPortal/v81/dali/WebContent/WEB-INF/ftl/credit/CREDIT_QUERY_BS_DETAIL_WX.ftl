<#include "CommonUtil.ftl"/>
<#assign data=NPT_ACTION_RETURNED_JSON>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/js/pager/css/kkpager.css" />
<style type="text/css">
    table{
        margin-bottom:10px;
    }
    .table-responsive{
        background-color:#fff;
    }
    .table-responsive th,.table-responsive td{
        font-size:12px;
        padding:1em 0;
        border-bottom:1px solid #dedede;
    }
    .bs-title{
        font-size:18px;
        text-align: center;
        width:100%;
        padding:1em 0;
        color:#fff;
        background: #199ED8;
    }
    .td_right{
        text-align: right;
    }
    .td_left{
        text-align: left;
    }
    thead th{
        background: #999!important;
        color:#fff!important;
    }
</style>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>企业信用查询</title>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/qyxycx.css"/>
</head>

<body>

<div>
<#--<#if >-->
    <div class="bs-title">
        基本信息
    </div>
    <#if data.dataList ??>
        <#list data.dataList as list>
        <#--<div class="center_title">${list.groupTitle}</div>-->
            <div>
                <#list list.blockList as blockList>
                <#--<h3>${blockList.blockInfo.poolTitle}</h3>-->
                    <#if blockList.dataArray??>
                        <#if blockList.dataArray?size == 1>
                            <#list blockList.dataArray as dataArray>
                                <table class="table-responsive" border="0" fixed width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;table-layout:fixed;">
                                <tr>
                                    <#list dataArray.dataArray as array>

                                        <td class="col-xs-3 td_right">${array.title}：</td>
                                        <td class="col-xs-9 td_left" style="word-wrap:break-word;">${array.value} </td>
                                    <#--<#if array?index%2 ==1>-->
                                    </tr>
                                    <tr>
                                    <#--</#if>-->

                                                </#list>
                                </tr>
                                </table>
                            </#list>
                        <#else>

                            <table border="0" width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed;word-break:break-all;border-collapse:collapse;">
                                <thead>
                                    <#list blockList.dataArray[0].dataArray as array>
                                    <th>${array.title}</th>
                                    </#list>
                                </thead>
                                <#list blockList.dataArray as dataArray>
                                    <tr>
                                        <#list dataArray.dataArray as array>
                                            <td >${array.value}</td>
                                        </#list>
                                    </tr>
                                </#list>
                            </table>

                        </#if>
                    <#else>
                    <#--<div class="null">-->
                    <#--本平台暂未收录！-->
                    <#--</div>-->

                    </#if>
                </#list>
            </div>
        </#list>
    <#else>
        <div class="text-center">暂无数据</div>
    </#if>


</div>


</body>
</html>