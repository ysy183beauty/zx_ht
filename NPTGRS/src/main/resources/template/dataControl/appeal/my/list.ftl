<#assign dataList = dataPagination.list>
<input id="totalCount" value="${dataPagination.totalCount!}" type="hidden">
<input id="totalPage" value="${dataPagination.totalPage!}" type="hidden">
<input id="pageSize" value="${dataPagination.pageSize!}" type="hidden">
<#if dataList?size == 0>
<tr><td colspan="8">暂无信息</td></tr>
<#else>
    <#assign sn = dataPagination.beginIndex>
    <#list dataList as c>
    <tr class="odd gradeX">
        <td>${sn + 1 + c_index}</td>
        <td title="${c.appealBusinessKey!}">${c.appealBusinessKey!}</td>
        <td title="${c.appealDTTitle!}">${c.appealDTTitle!}</td>
        <td title="${c.appealPKTitle!}">${c.appealPKTitle!}</td>
        <td title="${c.appealProviderTitle!}">${c.appealProviderTitle!}</td>
        <td title="${c.createTime!}">${c.createTime?string('yyyy-MM-dd')!}</td>
        <td title="${c.appealStatusTitle!}">${c.appealStatusTitle!}</td>
        <td><a onclick="showAppeal('${c.appealNo!}')">查看申请</a></td>
    </tr>
    </#list>
</#if>
