<#include "CommonUtil.ftl"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/lead.css">
<#assign data=NPT_ACTION_RETURNED_JSON>
<div class="lan">
    <div class="lan1"  id="hd_ul">
        <span class='red'>红名单</span>
        <span class='black'>黑名单</span>
    </div>
    <div class="lan2" style="width:64px;"></div>
    <a id="rb-more" class='lanshang' href="/credit/pub/index.do?word=1">更多+</a>
</div>
<div class="bd">
    <div class="png">
        <img src="${ctx}/r/cms/www/red/img/rbl.jpg" alt="" style="width:121px;height:168px;">
    </div>
    <#if data?size gt 0>
        <ul class="bd_ul">
        <#list data?keys as key>
            <div id="${key}">
                <#list data[key] as value>
                <li>
                    <dl>
                        <dt>
                        <div class="fang"></div>
                            <a onclick="show_info(${value.id})" title="${value.poolTitle}" target="_blank">

                                ${value.poolTitle}
                            </a>
                        </dt>
                    </dl>
                </li>
                </#list>
            </div>
        </#list>
        </ul>
    <#else>
        数据加载失败！
    </#if>
</div>
<script>

    function show_info(id) {
        window.open("/credit/pub/rbl/list.do?poolId=" + id + "&fromIndex=1");
    }
    $(function () {
        $("#红名单").css("display","block");
        $("#红名单").show().siblings().hide();
    });
    $(".black").bind("click",function () {
        $(this).parent().parent().find(".lan2").css("left","60px");
        $("#黑名单").show().siblings().hide();
        $("#rb-more").attr("href","/credit/pub/index.do?word=2");
    });
    $(".red").bind("click",function () {
        $(this).parent().parent().find(".lan2").css("left","0px");
        $("#红名单").show().siblings().hide();
        $("#rb-more").attr("href","/credit/pub/index.do?word=1");
    });
</script>
