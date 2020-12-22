<#if NPT_ACTION_RETURNED_JSON??>
<#assign data = NPT_ACTION_RETURNED_JSON>


        <#if data.totalCount gt 0>
        <thead>
            <th width="10%">序号</th>
            <#list data.columnTitles as title>
                <#if title_index!=0>
                    <th width="${80/(data.columnTitles?size-1)}%">${title!}</th>
                </#if>
            </#list>
            <th width="10%">操作</th>
        </thead>
        </#if>
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
                   <a class="btn mini yellow" onclick="showMap('${dataRow.dataArray[0].value}');">图谱</a>
                    <a class="btn mini green" onclick="showMap('${dataRow.dataArray[0].value}', 'd3');">d3图谱</a>
                </td>
            </tr>
            </#list>
        </#if>
    <#else>
    <tr>
        <td>暂无数据</td>
    </tr>
    </#if>

</#if>