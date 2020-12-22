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
        .no-found{
            font-weight:bold;
            text-align: center;
            font-size:20px;
        }
    </style>
</head>
<body >

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
                    <li>
                        <a href="/credit/pub/index.do">信用公示</a>
                    </li>
                    <li class="last-tit">
                    <li>&gt;</li>
                    <li>
                        被执行企业/被执行人
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
                        <div class="no-found">
                            暂无数据
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "include/footer.ftl"/>
</body>
<script type="text/javascript">

</script>
</html>