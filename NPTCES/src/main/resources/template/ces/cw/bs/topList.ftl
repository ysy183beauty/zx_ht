<#assign dataList = dataPagination.get("list")>
<input id="totalCount" value="${dataPagination.get("totalCount")!}" type="hidden">
<input id="totalPage" value="${dataPagination.get("totalPage")!}" type="hidden">
<#--<input id="pageSize" value="${dataPagination.get("pageSize")!}" type="hidden">-->
<#if dataList?size == 0>
<tr class="td-val">
    <td colspan="5">暂无数据</td>
</tr>
<#else>
    <#assign sn = dataPagination.get("beginIndex")>
    <#list dataList as c>
    <tr class="td-val" onclick="bsDetail('${c.get("creditEntityId")!}')">
        <td>${c.get("creditEntityId")!}</td>
        <td>${c.get("creditEntityTitle")!}</td>
        <td>${c.get("riskScore")!}</td>
        <td class="level">${c.get("riskLevel")!}</td>
        <td>${c.get("createTime")?string("yyyy-MM-dd")!}</td>
    </tr>
    </#list>
</#if>

