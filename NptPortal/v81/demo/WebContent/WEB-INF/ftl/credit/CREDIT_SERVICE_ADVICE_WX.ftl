<#include "CommonUtil.ftl"/>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/js/pager/css/kkpager.css" />
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>信用咨询</title>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xyfw.css"/>
</head>
<body>
<style type="text/css">
    .button{
        margin-bottom:1em;
    }
    .t_area{
        width:100%;
        height:100%;
    }
    .top{position:fixed; top:0; right:0; width:100%; height:20px; background:#FCC;z-index:9999 }
    .top1{position:fixed; top:0; left:0; width:100%; height:30px; background:#009dd9;z-index:9998 }
    /*.top2{position:fixed; top:0; right:0; width:100%; height:20px; background:#009dd9;z-index:9997 }*/
</style>
<#--<#if user??>-->
<div id="show_con">
    <div class ="top">
        <font color="#cc0000" size="3px"> 当前位置-信用咨询 </font>
    </div>
    <#--<div class="con_top_shu">-->
        <div class=" top error ">
            <a onclick="show()"><img src="${ctx}/r/cms/www/red/img/xyfw/u71.png" alt=""/>发出声音</a>
        </div>
        <div id="adviceList" class="con_center"></div>
    <#--</div>-->
</div>
<div id="hide_con">
    <div class="con_top_shu">
        <div class ="top1">
            <font color="#ffffff" size="3px">当前位置-发出声音</font>
        </div>
        <div class="con_center">
            <form>
            <td>
                <textarea class="t_area"  name="text" cols="30" rows="20" placeholder="请耐心些，尽量详细描述你的问题！"></textarea>
            </td>
            <div class="btn ">
            <input class="btn button" type="button" id="btnSubmit" value="发布"/>
            <input class="btn button" type="button" value="取消" onclick="hide()"/>
            </div>
            </form>
        </div>

    </div>
</div>
<#--<#else >-->
<#--<div>-->
    <#--请先<a href="http://npt.s1.natapp.cc/common/getOpenIdUrl.jspx?rediretUrl=http://npt.s1.natapp.cc/login.jspx">登录！</a>登陆后才可以进行信用咨询！-->
<#--</div>-->
<#--</#if>-->
</body>
<script>
    $(function () {
        list(1, 10);
    });
    function list(pageNo, pageSize) {
        $.ajax({
            url: "/credit/srv/advice/list.do",
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            success: function (html) {
                $("#adviceList").html(html);
            }
        })
    }
    function show() {
        $("#show_con").hide();
        $("#hide_con").show();
    }
    function hide() {
        $("#show_con").show();
        $("#hide_con").hide();
    }
    $("#btnSubmit").click(function () {
        $.ajax({
            type: "POST",
            url: "/credit/srv/advice/add.do",
            data: $("form").serializeArray(),
            success: function (msg) {
                if (msg == "success") {
                    creditService($(".search_list .active a").attr("name"));
                    window.location.href="http://npt.s1.natapp.cc/credit/srv/adviceMobile.do";
                }
            }
        })

    });
</script>
</html>