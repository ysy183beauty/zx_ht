<#if _DATATYPE_FIELD_LIST?size gt 0>
    <#list _DATATYPE_FIELD_LIST as c>
    <tr>
        <td>${c_index+1}</td>
        <td align="left">${c.fieldName!}</td>
        <td>
            <#if c.pubLevel == 0>
                授权访问
            <#elseif c.pubLevel == 1 >
                政务公开
            <#else >
                社会公开
            </#if>
        </td>
        <td>
            <#if c.showStyle == 'FSS_COMMON_TEXT'>
                普通文本
                <#elseif c.showStyle == 'FSS_PARTHIDE_TEXT'>
                半隐藏文本
                <#elseif c.showStyle == 'FSS_DATE'>
                日期时间
                <#elseif c.showStyle == 'FSS_CODE'>
                码表转换
                <#elseif c.showStyle == 'FSS_IMG_DATE'>
                图片
                <#elseif c.showStyle == 'FSS_IMG_PATH'>
                图片路径
                <#else>
                未知类型
            </#if>
        </td>
        <td>
            <#if c.status == 1>
                启用
                <#else >
                禁用
            </#if>
        </td>
        <td>
            <input type="radio" name="pFieldId" value="${c.id}" required>
        </td>
        <td>
            <input type="checkbox" name="pConFieldIds" value="${c.id}">
        </td>
        <td>
            <input  type="checkbox" name="pIdxFieldIds"  value="${c.id}">
        </td>
        <td>
            <input type="radio" name="pool.titleFieldId" value="${c.id}" required>
        </td>
        <td>
            <input type="radio" name="pool.orderFieldId" value="${c.id}">
            <i class="icon-arrow-up"></i>
            <input type="radio" name="pool.orderFieldId" value= ${"-${c.id}"}>
            <i class="icon-arrow-down"></i>
        </td>
    </tr>
    </#list>
<#else >

</#if>

