<#assign data = dataPagination>
<#if data??>
    <#if data.list?size==0>
    <tr>
        <td colspan="8">暂无数据</td>
    </tr>
    <#else >
        <#list data.list as list>
        <tr>
            <td>${list_index+1}</td>
            <td title="${list.appealUser!}">${list.appealUser!}</td>
            <td title="${list.appealUserTel!}">${list.appealUserTel!}</td>
            <td title="${list.appealDTTitle!}">${list.appealDTTitle!}</td>
            <td title="${list.appealProviderTitle!}">${list.appealProviderTitle!}</td>
            <td title="${list.createTime!}">${list.createTime!}</td>
            <td title="${list.appealStatusTitle!}">${list.appealStatusTitle!}</td>
            <td>
                <a href="javascript:void(0)" onclick="showAppeal('${list.appealNo!}')">查看审核</a>
            </td>
        </tr>
        </#list>
    </#if>
<#else >
<tr>
    <td colspan="8">暂无数据</td>
</tr>
</#if>
