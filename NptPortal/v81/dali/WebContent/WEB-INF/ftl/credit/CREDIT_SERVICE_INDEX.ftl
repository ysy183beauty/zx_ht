<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>信用服务</title>
<#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xyfw.css"/>

    <style>
        .credit_list a{
            font-size:14px!important;
        }
        .content{
            margin-top: 20px;
        }
    </style>
</head>
<body>
<#--<div class="load_main">-->
    <#--<img src="/r/cms/www/red/img/loading.gif">-->
<#--</div>-->
<div class="wrap">
<#include "include/header.ftl"/>
<div class="main">
    <div id="container">
        <#--<div class="title">-->
            <#--<span>当前位置：<a href="/">首页</a> > <a href="/credit/srv/index.do">信用服务 </a> > <span> 异议处理</span></span>-->
        <#--</div>-->
        <div class="sit height_auto m-t-md">
            <ul>
                <li>
                    <a id="sy" href="/">首页</a>
                </li>
                <li>&gt;</li>
                <li>
                    <a href="/credit/srv/index.do">信用服务</a>
                </li>
                <li class="last-tit"></li>
            </ul>
        </div>
        <div class="row">
            <div id="nav_search" class="col-sm-3">
            <#include "include/NAV_SERVICE_INDEX.ftl"/>
            </div>
            <div class="content col-sm-9" id="mainContent">
            <#if !user??>
                请登录后再来查询信息！
            </#if>
            </div>
        </div>

    </div>
</div>
</div>
<#include "include/footer.ftl"/>
</body>
<script>
    $(function () {
        //    获取页面参数
        var URLParams = new Array();
        var aParams = document.location.search.substr(1).split('&');
        for (i=0; i < aParams.length ; i++){
            var aParam = aParams[i].split('=');
            URLParams[aParam[0]] = aParam[1];
        }
        var word=URLParams["word"];
        if(word && word == 1){
            $("#nav li").eq(1).click();
        }
        else{
            $("#nav li:first-child").click();
        }
    })
</script>
</html>