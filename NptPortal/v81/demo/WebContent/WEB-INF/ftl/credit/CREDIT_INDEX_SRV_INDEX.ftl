<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>服务机构</title>
    <#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/publicity.css" />
    <style>
        .credit_list a{
            font-size:14px!important;
        }
    </style>
</head>
<body onload="test('/fwjg/index.jhtml')">

<div class="wrap">
<#include "include/header.ftl"/>

    <div class="main">
        <div id="container" class="container">
            <div class="sit height_auto m-t-md">
                <ul>
                    <li>
                        <a id="sy" href="/">首页</a>
                    </li>
                    <li>&gt;</li>
                    <#--<li>-->
                        <#--<a href="/credit/pub/index.do">信用公示</a>-->
                    <#--</li>-->
                    <li class="last-tit">
                    <li>&gt;</li>
                    <li>
                        服务机构
                    </li>
                    </li>
                </ul>
            </div>
            <div class="">

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