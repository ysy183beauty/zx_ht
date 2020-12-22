
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
<style>
    .key_content{
        margin-top:30px;
    }
    .key_title{
        width:100%;
        height:40px;
        line-height: 40px;
        color:#fff;
        background: #00a2d4;
        padding-left:10px;
    }
    .key_box{
        border-bottom: 1px solid #ccc;
        font-size:14px;
        padding:20px 0;
    }
    .key_box:hover{
        background:#ffffcc;
    }
</style>
<#assign pools=_POOLS>
<#if pools?size gt 0>
<div class="row top_nav">
    <div class="key_content">
        <#list pools?keys as key>
            <div class="key_title">
                >>${key}
            <#--<span>[${pools[key]?size}]</span>-->
            </div>
            <div>
                <#list pools[key] as value>

                    <div class="key_box" onclick="show_info(null,${value.id},0)">
                    ${value.poolTitle}
                    </div>
                </#list>
            </div>
        </#list>
    </div>
</div>
</div>
<div id="md_content">
</div>
<#else>
<div style="font-size:24px;text-align:center;">
    暂无数据
</div>
</#if>

<#--</body>-->
<script>
    function show_info(param,id,_index) {
        $(".load_main").show();
        $.ajax({
            url:"/credit/pub/rbl/listMobile.do",
            data:{
                poolId : id,
                pageSize : 10,
                currPage : 1,
                fromIndex: _index,
                bean  : param
            },
            success:function (html) {
                $("body").html(html);
//                document.getElementsByClassName("focus")[0].focus();
//                $.scrollTo('#md_content',500);
//                load();
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

</script>