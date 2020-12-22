<#assign data = dataPagination>

<#if data.list?size==0>
<tr>
    <td colspan="8">暂无数据</td>
</tr>
<#else >
    <#list data.list as list>
    <tr>
        <td>${(data.beginIndex + list_index + 1)?string("#")}</td>
        <td title="${list.applyBusinessKey!}">${list.applyBusinessKey!}</td>
        <td title="${list.applyGrouPoolTitle!}" align="left">${list.applyGrouPoolTitle!}</td>
        <td title="${list.applyProviderTitle!}">${list.applyProviderTitle!}</td>
        <td title="${list.createTime!}">${list.createTime!}</td>
        <td title="${list.applyStatusTitle!}">${list.applyStatusTitle!}</td>
        <td title="${list.expiredTitle!}">${list.expiredTitle!}</td>
        <td><a href="javascript:void(0)" onclick="showDetail('${list.applyNo}')">查看详情</a></td>
    </tr>
    </#list>
</#if>
