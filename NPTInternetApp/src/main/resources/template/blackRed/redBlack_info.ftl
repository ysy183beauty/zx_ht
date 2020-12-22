<#if NPT_ACTION_RETURNED_JSON??>
    <#assign data =  NPT_ACTION_RETURNED_JSON>
<input id="totalCount" value="${data.totalCount!}" type="hidden">
<input id="totalPage" value="${data.totalPage!}" type="hidden">
<input id="pageSize" value="${data.pageSize!}" type="hidden">

    <thead style="background: #ccc; font-size: bold">
    <tr>
        <#if data.columnTitles??>
            <th width="5%">序号</th>
            <#list data.columnTitles as title>
                <#if title_index!=0>
                    <th width="${90/(data.columnTitles?size-1)}%">${title}</th>
                </#if>
            </#list>
        </#if>
    </tr>
    </thead>
    <tbody>
        <#list data.dataList as dataRow>
        <tr>
            <td>${(data.beginIndex + dataRow_index + 1)?string("#")}</td>
            <#list dataRow.dataArray as fieldData>
                <#if fieldData_index!=0>
                    <td align="left" title="${fieldData.value!}">${fieldData.value!}</td>
                </#if>
            </#list>
        </tr>
        </#list>
    </tbody>

</#if>
