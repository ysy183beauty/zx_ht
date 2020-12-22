<#assign data = dataPagination>
<input id="totalCount" value="${data.totalCount!}" type="hidden">
<input id="totalPage" value="${data.totalPage!}" type="hidden">
<input id="pageSize" value="${data.pageSize!}" type="hidden">

<#if data??>
    <#if data.list?size==0>
    <tr>
        <td colspan="9">暂无数据</td>
    </tr>
    <#else >

        <#list data.list as list>
        <tr>
            <td>${(data.beginIndex + list_index + 1)?string("#")}</td>
            <td title="${list.applyUserOrgTitle!}">${list.applyUserOrgTitle!}</td>
            <td title="${list.applyUserName!}">${list.applyUserName!}</td>
            <td title="${list.applyUserTel!}">${list.applyUserTel!}</td>
            <td title="${list.applyBusinessKey!}">${list.applyBusinessKey!}</td>
            <td title="${list.applyGrouPoolTitle!}">${list.applyGrouPoolTitle!!}</td>
            <td title="${list.createTime!}">${list.createTime!}</td>
            <td title="${list.applyStatusTitle!}">${list.applyStatusTitle!}</td>
            <td>
                <#if list.applyStatus == 1>
                    <a href="javascript:void(0)" onclick="showApproval('${list.applyNo}')">审批</a>
                <#else >
                    <a href="javascript:void(0)" onclick="showApproval('${list.applyNo}')">查看审批</a>
                </#if>

            </td>
        </tr>
        </#list>
    </#if>
<#else >
<tr>
    <td colspan="9">暂无数据</td>
</tr>
</#if>
