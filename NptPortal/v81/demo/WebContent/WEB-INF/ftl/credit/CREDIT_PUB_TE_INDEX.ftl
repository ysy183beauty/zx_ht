<#include "CommonUtil.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/te.css"/>
<#--</head>-->
<#--<body>-->
<#assign model=_MODEL>
<#if model?size gt 0>
<div class="row top_nav">

    <#list model as pool>
        <div class="col-md-4 col-sm-6">
            <div class="box-nav" onclick="show_info(${pool.id})">
                <div class="box-text">
                ${pool.poolTitle}
                </div>
                <div class="box-content">
                    <p class="description">${pool.providerTitle}</p>
                </div>
            </div>
        </div>
    </#list>
</div>
<div class="md_content">
</div>
<#else>
<div style="font-size:24px;text-align:center;">
    暂无数据
</div>
</#if>

<#--</body>-->
<script>
    $(".box-nav").bind("click",function () {
        $(".top_nav").slideUp(1000);
//        $(".top_title span b").text( $(this).find(".box-text").text());
    });

    function show_info(id) {
        $.ajax({
            url:"/credit/pub/2pub/list.do",
            data:{
                poolId : id,
                pageSize : 10,
                currPage : 1
            },
            success:function (html) {
                $(".md_content").html(html);
            }
        })
    }

</script>
<#--</html>-->