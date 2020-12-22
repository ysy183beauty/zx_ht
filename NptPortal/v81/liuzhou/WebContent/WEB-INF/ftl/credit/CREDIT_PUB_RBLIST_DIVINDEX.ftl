<#include "CommonUtil.ftl"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/lead.css">
<#assign data=NPT_ACTION_RETURNED_JSON>


    <#if data?size gt 0>

        <#list data?keys as key>
            <div id="${key}">
                <#if data[key]?size gt 0>
                    <#list data[key] as value>
                    <li>
                        <a onclick="show_info(${value.id})" title="${value.poolTitle}" target="_blank" class="nav-text">
                            <div class="fang"></div>
                            ${value.poolTitle}
                        </a>
                    </li>
                    </#list>
                <#else>
                    暂无数据
                </#if>
            </div>
        </#list>

    <#else>
        数据加载失败！
    </#if>

<script>

    function show_info(id) {
        window.open("/credit/pub/rbl/list.do?poolId=" + id + "&fromIndex=1");
    }

</script>
