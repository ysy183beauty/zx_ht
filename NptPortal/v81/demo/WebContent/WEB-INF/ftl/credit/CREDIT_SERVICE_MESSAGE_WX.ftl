<#include "CommonUtil.ftl"/>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xyfw.css" />
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<head lang="en">
    <meta charset="UTF-8">
    <title>我的消息</title>
</head>
<body>
<style type="text/css">
    .top{position:fixed; top:0; right:0; width:100%; height:40px; background:#009dd9;z-index:9999 }
    .top1{position:fixed; top:50; width:100%; height:40px; }
    </style>
<#--<div class ="top">-->
    <#--<font color="#ffffff" size="4px"> 当前位置-我的消息 </font>-->
<#--</div>-->

<#if user??>
<div id="xy_list">
    <div class="nav-title">
        <a onclick="list('0', 1, 10)" class="on">异议处理</a>
        <a onclick="list('1', 1, 10)" class="off">信用投诉</a>
    </div>
    <div class="con_center"></div>
</div>
<div id="xy_detail"></div>
<#else >
<div class="top1">
    <center>
    请先<a href="http://npt.s1.natapp.cc/common/getOpenIdUrl.jspx?rediretUrl=http://npt.s1.natapp.cc/login.jspx">登录</a>后再来查看！
    </center>
</div>
</#if>
</body>
<script>
    $(".nav a").bind("click",function(){
        $(this).stop().removeClass("off").addClass("on").siblings().removeClass("on").addClass("off");
    })
    function show_info(id) {
        $.ajax({
            url: "/credit/srv/objection/detailMobile.do",
            data: {
                id: id
            },
            success: function (html) {
                $("#xy_detail").html(html);
                $("#xy_list").hide();
                $("#xy_detail").show();
            }
        })
    }
    function hide_info() {
        $("#xy_list").show();
        $("#xy_detail").hide();
    }
    // 分页
    function list(flag, pageNo, pageSize) {
        $.ajax({
            url: "/credit/srv/objection/listMobile.do",
            data: {
                flag: flag,
                pageNo: pageNo,
                pageSize: pageSize
            },
            success: function (html) {
                $("div.con_center").html(html);
            }
        })
    }

    $(function () {
        list("0", 1, 10);
    })
</script>