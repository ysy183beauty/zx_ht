<#if _LOG_Pagination??>

<#assign dataList = _LOG_Pagination.list>
<#assign data = _LOG_Pagination>
<input id="totalCount" value="${data.totalCount!}" type="hidden">
<input id="totalPage" value="${data.totalPage!}" type="hidden">
<input id="pageSize" value="${data.pageSize!}" type="hidden">
<#if dataList?size == 0>
<tr><td colspan="7">暂无信息</td></tr>
<#else>
    <#assign sn = _LOG_Pagination.beginIndex>
<div class="row-fluid">
    <#list dataList as c>
        <tr>
            <td>${(sn+1+c?index)?string("#")}</td>
            <td title="${(_LOG_ORG[c?index].alias)!}">${(_LOG_ORG[c?index].alias)!}</td>
            <td title="${c.userName!}">${c.userName!}</td>
            <td title="${(_LOG_USER[c?index].loginName)!}">${(_LOG_USER[c?index].loginName)!}</td>
            <td title="${c.businessName!}">${c.businessName!}</td>
            <#--<td title="${c.invokeIP!}">${c.invokeIP!}</td>-->
            <td title="${c.createTime!}">${c.createTime!}</td>
            <td>
                <#if c.results?? && c.results=="null">
                <#elseif c.results??>
                    <a data-toggle="modal" data-id="${c.id?string("#")}" data-target="#modalDiv" class="btn mini green"><i class="icon-search"></i> 详情</a>
                <#else>
                    <p>${c.actionName!}</p>
                </#if>
            </td>
        </tr>
    </#list>
</div>
</#if>
<#else>
<tr><td colspan="8">暂无信息</td></tr>
</#if>