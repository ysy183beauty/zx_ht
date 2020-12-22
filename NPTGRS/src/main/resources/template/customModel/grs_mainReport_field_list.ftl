<#assign data = NPT_ACTION_RETURNED_JSON>
<#if data??>
<input id="totalCount" value="${data.totalCount!}" type="hidden">
<input id="totalPage" value="${data.totalPage!}" type="hidden">
<input id="pageSize" value="${data.pageSize!}" type="hidden">
    <#if !data.columnTitles??>
    <tr>
        <td>暂无数据</td>
    </tr>
    <#elseif data.totalCount == 0>
    <tr>
        <td colspan="${data.columnTitles?length+1}">暂无数据</td>
    </tr>
    <#else>
        <#list data.dataList as dataRow>
        <tr>
            <td>${(data.beginIndex + dataRow_index + 1)?string("#")}</td>
            <#list dataRow.dataArray as fieldData>
                <#if fieldData_index!=0>
                    <td align="left" title="${fieldData.value!}">${fieldData.value!}</td>
                </#if>
            </#list>
            <td>
                <div class="controls">
                <a onclick="openModal('${dataRow.dataArray[0].value}')" class="btn mini green"><i class="icon-search"></i> 查看报告</a>
                <a onclick="loadReportHistory('${dataRow.dataArray[0].value}')" class="btn mini yellow"><i class="icon-list"></i> 历史报告</a>
                </div>
            </td>
        </tr>
        </#list>
    </#if>
<#else>
<tr>
    <td>暂无数据</td>
</tr>
</#if>