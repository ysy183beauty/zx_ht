<#include "CommonUtil.ftl"/>
<#assign data=NPT_ACTION_RETURNED_JSON>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>${data.title!}</title>
    <#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/qyxycx.css"/>
    <style>
        .table{
            margin-bottom:0!important;
        }
    </style>
</head>
<body>
<div class="wrap">
    <div class="body">
    <#include "include/header.ftl"/>
        <div id="container">
            <div class="sit height_auto m-t-md">
                <#--<ul>-->
                    <#--<li>-->
                        <#--<a id="sy" href="/">首页</a>-->
                    <#--</li>-->
                    <#--<li>&gt;</li>-->
                    <#--<li>-->
                        <#--<a href="/credit/pub/index.do">信用查询</a>-->
                    <#--</li>-->
                    <#--<li class="last-tit">-->
                    <#--<li>&gt;</li>-->
                    <#--<li>企业基础查询</li>-->
                    <#--</li>-->
                <#--</ul>-->
            </div>
            <div class="">
                <div class="con_top wdl">
                    <div class="con_top_shu">

                        <h2>
                            <img class="detail-title-img" src="${ctx}/r/cms/www/red/img/lz/detail/01icon_2.png" alt="">
                            <span>${data.title!}</span>
                        </h2>
                    <#--<div class="error" >-->
                    <#--<img src="${ctx}/r/cms/www/red/img/sjxq/u87.png" alt=""/>信息纠错-->
                    <#--</div>-->
                    <#--<ul class="alert alert-info">-->
                    <#--<#list data.dataList as list>-->
                    <#--<li>-->
                    <#--<div class="info">${list.groupTitle}：</div>-->
                    <#--<div class="info-con">-->
                    <#--<#list list.blockList as blockList>-->
                    <#--<span class="col-sm-6">${blockList.blockInfo.poolTitle}</span>-->
                    <#--</#list>-->
                    <#--</div>-->
                    <#--</li>-->
                    <#--</#list>-->
                    <#--</ul>-->
                        <div class="con_center">
                        <#list data.dataList as list>
                            <div class="center_title">${list.groupTitle}</div>
                            <div class="bg bg_1">
                                <#if list.blockList?size gt 0>
                                    <#list list.blockList as blockList>
                                        <div class="h3-menu">
                                            <div class="leftBG"></div>
                                            <div class="labelBG">${blockList.blockInfo.poolTitle}</div>
                                            <div class="centerBG"></div>
                                        </div>

                                        <#if blockList.dataArray??>
                                            <#if blockList.dataArray?size == 1>
                                                <#list blockList.dataArray as dataArray>
                                                    <table class="tab-col table table-bordered" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;table-layout: fixed;">
                                                    <#--<tr>-->
                                                        <#--<#list dataArray.dataArray as array>-->
                                                            <#--<th>${array.title}：</th>-->
                                                            <#--<td>${array.value}</td>-->
                                                            <#--<#if array?index%2 ==1>-->
                                                            <#--</tr>-->
                                                            <#--<tr>-->
                                                            <#--</#if>-->
                                                        <#--</#list>-->
                                                        <#--<#if dataArray.dataArray?size%2 ==1>-->
                                                            <#--<th></th>-->
                                                            <#--<td></td>-->
                                                        <#--</#if>-->
                                                    <#--</tr>-->
                                                    <#list dataArray.dataArray as array>
                                                        <tr>
                                                            <th>${array.title}：</th>
                                                            <td>${array.value}</td>
                                                        </tr>
                                                    </#list>
                                                    </table>
                                                </#list>
                                            <#else>
                                                <#list blockList.dataArray as dataArray>
                                                    <table class="tab-row" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
                                                        <tr>
                                                            <#list dataArray.dataArray as array>
                                                                <th>${array.title}</th>
                                                            </#list>
                                                        </tr>
                                                        <tr>
                                                            <#list dataArray.dataArray as array>
                                                                <td>${array.value}</td>
                                                            </#list>
                                                        </tr>
                                                    </table>
                                                </#list>
                                            </#if>
                                        <#else>
                                            <div class="null">
                                                本平台暂未收录！
                                            </div>

                                        </#if>
                                    </#list>
                                <#else>
                                    <div class="no-found">无相关信息</div>
                                </#if>
                            </div>
                        </#list>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<#include "include/footer.ftl"/>
</body>
</html>