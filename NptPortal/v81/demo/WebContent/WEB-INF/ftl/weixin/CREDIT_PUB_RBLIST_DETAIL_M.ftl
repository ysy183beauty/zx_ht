<#include "CommonUtil.ftl"/>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/comm.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/style.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/main.css" />
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/style.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
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
<#assign data = NPT_ACTION_RETURNED_JSON>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>数据详情</title>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/zqsjxq.css"/>
</head>
<body>
<#--<div class="wrap">-->
<#--<#include "include/header.ftl"/>-->
<#if data.dataArray??>
    <#--<div id="container">-->
        <#--<div class="title">-->
            <#--<span><a href="">当前位置</a> ：<a href="">首页</a> > <a href="">信用公示 </a> > <a href=""> 双公示</a> > <a href="">行政许可公示</a></span>-->
        <#--</div>-->
        <#--<div class="sit height_auto m-t-md">-->
            <#--<ul>-->
                <#--<li>-->
                    <#--<a id="sy" href="/">首页</a>-->
                <#--</li>-->
                <#--<li>&gt;</li>-->
                <#--<li>-->
                    <#--<a href="/credit/pub/index.do">信用公示</a>-->
                <#--</li>-->
                <#--<li class="last-tit">-->
                <#--<li>&gt;</li>-->
                <#--<li>-->
                    <#--红黑榜-->
                <#--</li>-->
                <#--</li>-->
            <#--</ul>-->
        <#--</div>-->
        <#--<div class="content">-->
            <#--<div class="con_top">-->
                <#--<div class="con_top_shu">-->
                    <div class="table-responsive">
                    <#--<h2></h2>-->
                    <#--<div class="error">-->
                        <#--<img src="${ctx}/r/cms/www/red/img/sjxq/u87.png" alt=""/>异议-->
                    <#--</div>-->
                    <table  border="0" width="100%" cellpadding="0" cellspacing="0" table-layout="fixed">
                        <#list data.dataArray as array>
                            <#if array_index gt 0>
                                <tr class="row">
                                    <td class="col-md-3 td_left">${array.title}：</td>
                                    <td class="col-md-9 td_right" style="word-wrap:break-word;">${array.value!}</td>
                                </tr>
                            </#if>
                        </#list>
                    </table>
                <#--</div>-->
            <#--</div>-->
        <#--<div class="btn">-->
        <#--<input type="submit" value="返回" onclick="javascript:history.go(-1);"/>-->
        <#--</div>-->
        <#--</div>-->
                    </div>
    <#--</div>-->
<#else>
    暂无数据
</#if>
<#--</div>-->

<#--<#include "include/footer.ftl"/>-->

</body>
</html>