<#if _POOL_DATA_FIELD_LIST??>
    <#list _POOL_DATA_FIELD_LIST as c>
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
            <input type="checkbox" name="toKeyId[]" value="${c.id}"
                <#assign flag = false>
                <#list _LINK_LIST as link>
                    <#assign index = link_index>
                    <#if link.toKeyId == c.id>
                   checked
                        <#assign flag = true>
                        <#break>
                    </#if>
                </#list>
            >
        </td>
        <td>
            <input type="text" name="linkTitle[]" list="titleList"
                <#if true == flag>
                   value="${_LINK_LIST[index].linkTitle!}"
                <#else>
                   disabled
                </#if>
            >
            <datalist id="titleList">
                <#list _POOL_DATA_FIELD_LIST as tf>
                    <option value="${tf.id}" label="${tf.fieldName!}"/>
                </#list>
            </datalist>
        </td>
        <td>
            <input type="checkbox" name="relLinkFieldId[]" value="${c.id}"
                <#assign flag = false>
                <#list _LINK_LIST as link>
                    <#assign index = link_index>
                    <#if link.toKeyId == c.id>
                   checked
                        <#assign flag = true>
                        <#break>
                    </#if>
                </#list>
            >
        </td>
    </tr>
    </#list>
<#else >

</#if>
<script>
    $("input[name='toKeyId[]']").on("change", function () {
        var input = $(this).parent("td").next().find("input");
        if($(this).prop("checked")){
            input.prop("disabled", false);
            input.prop("required", true);
        }else{
            input.val("");
            input.prop("disabled", true);
            input.prop("required", false);
        }
    });
</script>