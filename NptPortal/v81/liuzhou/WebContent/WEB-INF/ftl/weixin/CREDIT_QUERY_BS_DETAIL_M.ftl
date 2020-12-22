<#include "CommonUtil.ftl"/>
<#assign data=NPT_ACTION_RETURNED_JSON>
<meta name="viewport" content="width=device-width, initial-scale=1" />
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
        text-align: center;
        padding:1em 0;
        border-bottom:1px solid #dedede;
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

<#--<div class="wrap">-->

    <#--<div id="container">-->
        <#--<div class="title">-->
            <#--<span><a href="">当前位置</a> ：<a href="">首页</a> > <a href="">信用公示 </a> > <a href=""> 企业信用查询</a></span>-->
        <#--</div>-->
        <#--<div class="content">-->
            <#--<div class="con_top wdl">-->
                <#--<div class="con_top_shu">-->
                    <#--<h2>${data.primaryKeyValue}</h2>-->
                    <#--<div class="error" >-->
                        <#--<img src="${ctx}/r/cms/www/red/img/sjxq/u87.png" alt=""/>信息纠错-->
                    <#--</div>-->
                    <#--<ul>-->
                        <#--<li>-->
                            <#--<span>基础信息：</span>-->
                            <#--<span>基本信息</span>-->
                            <#--<span>主要人员信息</span>-->
                            <#--<span>出资人信息</span>-->
                            <#--<span>行政许可信息</span>-->
                            <#--<span>变更信息</span>-->
                        <#--</li>-->
                        <#--<li>-->
                            <#--<span>资质认证信息：</span>-->
                            <#--<span>信息概要</span>-->
                            <#--<span> 营业执照</span>-->
                            <#--<span> 税务登记证</span>-->
                            <#--<span>卫生许可证</span>-->
                        <#--</li>-->
                        <#--<li>-->
                            <#--<span> 荣誉信息：</span>-->
                            <#--<span>红榜信息</span>-->
                        <#--</li>-->
                        <#--<li>-->
                            <#--<span> 失信信息：</span>-->
                            <#--<span> 黑榜信息</span>-->
                            <#--<span>行政处罚信息</span>-->
                        <#--</li>-->
                    <#--</ul>-->
                    <#--<div class="con_center">-->
                        <div class="table-responsive">
                        <#--<#if >-->
                    <#list data.dataList as list>
                        <#--<div class="center_title">${list.groupTitle}</div>-->
                        <div class="bg bg_1">
                            <#list list.blockList as blockList>
                                <#--<h3>${blockList.blockInfo.poolTitle}</h3>-->
                                <#if blockList.dataArray??>
                                    <#if blockList.dataArray?size == 1>
                                        <#list blockList.dataArray as dataArray>
                                            <table border="0" fixed width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;table-layout:fixed;">
                                            <tr>
                                                <#list dataArray.dataArray as array>

                                                    <td class="col-md-3 td_left">${array.title}：</td>
                                                    <td class="col-md-9 td_right" style="word-wrap:break-word;">${array.value} </td>
                                                    <#--<#if array?index%2 ==1>-->
                                                    </tr>
                                                    <tr>
                                                    <#--</#if>-->

                                                </#list>
                                            </tr>
                                            </table>
                                        </#list>
                                    <#else>
                                        <#list blockList.dataArray as dataArray>
                                            <table border="0" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
                                                <tr>
                                                    <#list dataArray.dataArray as array>
                                                        <th>${array.title}</th>
                                                    </#list>
                                                </tr>
                                                <tr>
                                                    <#list dataArray.dataArray as array>
                                                        <td >${array.value}</td>
                                                    </#list>
                                                </tr>
                                            </table>
                                        </#list>
                                    </#if>
                                <#else>
                                    <#--<div class="null">-->
                                        <#--本平台暂未收录！-->
                                    <#--</div>-->

                                </#if>
                            </#list>
                        </div>
                    </#list>
                        <#--</#if>-->
                    <#--</div>-->
                <#--</div>-->
            <#--</div>-->
            <#--</div>-->
        <#--</div>-->
    <#--</div>-->
</div>


</body>
</html>