<#if _PERMISSION_LIST??>
    <#assign dataList = _PERMISSION_LIST>
    <#list dataList as c>
    <tr>
        <td align="center">${c_index+1}</td>
        <td align="left">
            <#list _ACTION_LIST as a>
                <#if a.code == c.action>
                ${a.title!}
                </#if>
            </#list>
        </td>
        <td align="left">
            <#list _ORG_LIST as b>
                <#if b.id == c.orgId>
                ${b.orgName!}
                </#if>
            </#list>
        </td>
        <td align="center">${(c.status==1)?string('启用','禁用')}</td>
        <td>
            <div class="controls">
                <#if c.status==1>
                    <a title="禁用" class="btn mini yellow" href="javascript:void(0);"
                       onclick="setPermissionStatus('${c.id?string("#")}',0)"><i class="icon-stop"></i> 禁用</a>
                <#else>
                    <a title="启用" class="btn mini blue" href="javascript:void(0);"
                       onclick="setPermissionStatus('${c.id?string("#")}',1)"><i class="icon-play"></i> 启用</a>
                </#if>
            </div>
        </td>
    </tr>
    </#list>
<#else >
<tr>
    <td colspan="5">暂无数据</td>
</tr>
</#if>




