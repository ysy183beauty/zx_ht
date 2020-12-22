<#assign data = dataPagination>
<input id="totalCount" value="${data.totalCount!}" type="hidden">
<input id="totalPage" value="${data.totalPage!}" type="hidden">
<input id="pageSize" value="${data.pageSize!}" type="hidden">

<#if data.list??>
    <#if data.list?size==0>
    <tr>
        <td colspan="9">暂无数据</td>
    </tr>
    <#else >

        <#list data.list as list>
        <tr>
            <td>${(data.beginIndex + list_index + 1)?string("#")}</td>
            <td title="${list.source!}">${list.source!}</td>
            <td title="${list.appealUser!}">${list.appealUser!}</td>
            <td title="${list.appealUserTel!}">${list.appealUserTel!}</td>
            <td title="${list.appealDTTitle!}">${list.appealDTTitle!}</td>
            <td title="${list.appealProviderTitle!}">${list.appealProviderTitle!}</td>
            <td title="${list.createTime!}">${list.createTime!}</td>
            <td title="${list.appealStatusTitle!}">${list.appealStatusTitle!}</td>
            <td>
                <#if list.appealStatus == 0>
                    <a href="javascript:void(0)" onclick="showAppeal('${list.appealNo!}')">审核</a>
                <#else>
                    <a href="javascript:void(0)" onclick="showAppeal('${list.appealNo!}')">查看审核</a>
                </#if>

            </td>
        </tr>
        </#list>
    </#if>
<#else >
<tr>
    <td colspan="8">暂无数据</td>
</tr>
</#if>
