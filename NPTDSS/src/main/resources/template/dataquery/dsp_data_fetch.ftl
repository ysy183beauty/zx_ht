<#include "/template/Credit_Common/common.ftl">

<#assign dataList = dataPagination.list>
<input data-role="pageElement" data-totalCount="${dataPagination.totalCount!}" data-totalPage="${dataPagination.totalPage!}" data-pageSize="${dataPagination.pageSize!}" type="hidden">
<#if dataList?size == 0>
<tr><td colspan="9" align="center">暂无信息</td></tr>
<#else>
    <#assign sn = dataPagination.beginIndex>
    <#list dataList as c>

        <#list c?keys as key>
            ${key}
            ${c.get(key)}
        </#list>


    </#list>
</#if>
