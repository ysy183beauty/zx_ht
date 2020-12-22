<#include "CommonUtil.ftl"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
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
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/nav.css"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/publicity.css"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/tableDate.css"/>
<#--<!DOCTYPE html>-->
<#--<html>-->
<#--<head lang="en">-->
    <#--<meta charset="UTF-8">-->
    <#--<title>行政许可</title>-->
    <#--<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css"/>-->
<style type="text/css">
    .wrap{
        width: 100px;
        margin: 0 auto;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }
    thead th{
        background: #999!important;
        color:#fff!important;
    }
</style>
<#assign pools=_POOLS>
<#if pools?size gt 0>

    <table class="table table-striped" border="0" width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed;word-break:break-all;border-collapse:collapse;">
    <thead>
    <th>公示信息表</th>
    </thead>
    <#list pools?keys as key>
        <#list pools[key] as value>
            <tr onclick="show_info(${value.id})">
                <td>${value.poolTitle}</td>
            </tr>
        </#list>
    </#list>
    </table>


    <div id="md_content">

    </div>

<#else>
    <div style="font-size:24px;text-align:center;">
        暂无数据
    </div>
</#if>

<#--</body>-->
<script>
    $(".box-nav").bind("click",function () {
        $(".load_main").show()//显示你的DIV
//        $(".top_title span b").text( $(this).find(".box-text").text());
    });

    function show_info(id) {
        $.ajax({
            url:"/credit/pub/2pub/listMoblie.do",
            data:{
                poolId : id,
                pageSize : 10,
                currPage : 1
            },
            success:function (html) {
                $("body").html(html);
//                setTimeout(function () {
//                    $(".top_nav").slideUp(1000);
//                },500);
                load();
            },
            error:function () {
                $("#md_content").html("数据请求失败！");
                load();
          },
            timeout:function(){
                $("#md_content").html("数据请求超时！");
                load();
            }
        })
    }
//
//    function show_nav(){
//        var num=$(".film_focus_nav li").index($(".cur"));
//        $(".film_focus_imgs li").eq(num).show().siblings().hide();
//        console.log($(".film_focus_nav li").index($(".cur")));
//    }
//    $(function () {
//        $(".film_focus_nav li:eq(0)").addClass("cur").siblings().removeClass("cur");
//        show_nav();
//    })
//    $(".film_focus_nav li").each(function(){
//        $(this).bind("click",function(){
//            $(this).stop().addClass("cur").siblings().removeClass("cur");
//            show_nav();
//        })
//    });
</script>
<#--</html>-->