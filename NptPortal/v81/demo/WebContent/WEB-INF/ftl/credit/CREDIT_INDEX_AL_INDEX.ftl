<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>行政许可</title>
    <#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/publicity.css" />
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/nav.css"/>
    <#--<link href="${ctx}/r/cms/www/red/css/font-awesome.min.css" rel="stylesheet">-->
    <style type="text/css">

    </style>
</head>
<body onload="test('/credit/pub/2pub/alIndex.do')">

<div class="wrap">
<#include "include/header.ftl"/>

    <div class="main">
        <div id="container" class="container">
            <#--<div class="title">-->
                <#--<span>当前位置：<a href="/">首页</a> >  <span> 行政许可</span></span>-->
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
                            行政许可
                        </li>
                        </li>
                    </ul>
                </div>
            <div class="row">

                <div class="content">
                    <div class="load_main">
                        <img class="load_img" src="/r/cms/www/red/img/load.gif">
                    </div>
                    <div  id="mainContent">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "include/footer.ftl"/>
</body>
<script type="text/javascript">
    function load(){
        window.setTimeout("$('.load_main').hide()",100);//100毫秒后，隐藏你的DIV
    }
    function test(url){
        $.ajax({
            url: url,
            data: {},
            timeout: 30000,
            success: function (data) {
                $("#mainContent").html(data);
            }
        });
    }
</script>
</html>