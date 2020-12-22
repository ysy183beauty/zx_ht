

<table class="table table-bordered table-hover tbList" >
<#if NPT_ACTION_RETURNED_JSON??>
<#assign data =  NPT_ACTION_RETURNED_JSON>
<#if data.dataList.dataCount gt 0>
    <tbody>

        <#list data.dataList.dataArray as firstElement>
            <#if firstElement_index == 0>
                <#list firstElement.dataArray as fieldData>
                <tr>
                    <td>${fieldData.title!}</td>
                    <td title="${fieldData.value!'暂无数据'}">
                        <#if fieldData.linked == 1 && fieldData.value??>
                            <a href="javascript:linkedPoolData('${block.blockInfo.id?string("#")}','${firstElement.uFieldValue}','${fieldData.fieldId}','');">${fieldData.value}</a>
                        <#elseif fieldData.valueNote?? && fieldData.value??>
                        ${fieldData.value}&nbsp;&nbsp;&nbsp;&nbsp;
                            <i class="icon-flag">(${fieldData.valueNote})</i>
                        <#else >
                        ${fieldData.value!'暂无数据'}
                        </#if>
                    </td>
                </tr>
                    <#if (firstElement.dataArray?size%2==1)&&(fieldData_index==firstElement.dataArray?size-1)>
                    <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                    </tr>
                    </#if>
                </#list>

            </#if>
        </#list>
    </tbody>
<#else >
    <thead>
    <tr>
        <th>暂无数据</th>
    </tr>
    </thead>
</#if>
<#else >
    <thead>
    <tr>
        <th>暂无数据</th>
    </tr>
    </thead>
</#if>
</table>


