<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>职权详情</title>
    <#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/zqsjxq.css"/>
    <style type="text/css">
        .con_top_table{
            background-color:#fff;
            margin-top:30px;
            padding:30px 0;
        }
        table tr td{
            padding:10px;
        }
        table tr td:first-child{
            width:13%;
            text-align:right;
        }
        table tr td:first-child span{
            display: block;
            padding:10px 5px;
            color:#fff;
            background-color:#fb6415;
        }
        table tr td:last-child{
            text-indent: 2em;
        }
        .data-null{
            text-align: center;
        }
    </style>
</head>
<body>
<div class="wrap">
<#include "include/header.ftl"/>
    <div id="container">
    <#--<div class="title">-->
    <#--<span><a href="">当前位置</a> ：<a href="">首页</a> > <a href="">信用公示 </a> > <a href=""> 双公示</a> > <a href="">行政许可公示</a></span>-->
    <#--</div>-->
        <div class="sit height_auto m-t-md">
            <ul>
                <li>
                    <a id="sy" href="/">首页</a>
                </li>
                <li>&gt;</li>
                <li>
                    <a href="/credit/pub/index.do">信用公示</a>
                </li>
                <li class="last-tit">
                <li>&gt;</li>
                <li>
                    双公示
                </li>
                </li>
            </ul>
        </div>
        <div class="content">
            <div class="con_top">
                <div class="con_top_table">
                    <#if _QZQD??>
                        <#assign data = _QZQD>
                        <table border="0" width="100%" cellpadding="0" cellspacing="0">
                        <#--<colgroup>-->
                        <#--<col class="qz-title">-->
                        <#--<col>-->
                        <#--</colgroup>-->
                            <tbody>
                            <tr><td><span>信息类:</span></td><td>${data.xxl!}</td></tr>
                            <tr><td><span>形式主体:</span></td><td>${data.xszt!}</td></tr>
                            <tr><td><span>设定依据:</span></td><td>${data.zxmc!}</td></tr>
                            <tr><td><span>设定依据:</span></td><td>${data.sdyj!}</td></tr>
                            <tr><td><span>承诺时限:</span></td><td>${data.cnsx!}</td></tr>
                            <tr><td><span>责任事项:</span></td><td>${data.zrsx!}</td></tr>
                            <tr><td><span>救济途径:</span></td><td>${data.zzqx!}</td></tr>
                            <tr><td><span>救济途径:</span></td><td>${data.zzyj!}</td></tr>
                            <tr><td><span>救济途径:</span></td><td>${data.jdfs!}</td></tr>
                            <tr><td><span>救济途径:</span></td><td>${data.jjtj!}</td></tr>
                            <tr><td><span>备注:</span></td><td>${data.bz!}</td></tr>
                            <tr><td><span>委办局:</span></td><td>${data.wbj!}</td></tr>
                            <tr><td><span>权责清单类型:</span></td><td>${data.qzqdlx!}</td></tr>
                            </tbody>
                        </table>
                    <#else>
                       <div class="data-null">暂无数据</div>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "include/footer.ftl"/>
</body>
</html>