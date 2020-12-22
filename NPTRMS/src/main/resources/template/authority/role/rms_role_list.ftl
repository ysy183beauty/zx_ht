<#assign dataList = NPT_ACTION_RETURNED_JSON>
<#if dataList?size == 0>

<#else>
	<#list dataList as c>
		<tr>
			<td align="center">${c_index+1}</td>
			<td align="left">${c.roleName!}</td>
			<td align="left">${c.roleNote!}</td>
			<td align="center">${(c.status==1)?string('启用','禁用')}</td>
			<td>
				<div class="controls">
                    <a title="编辑" class="btn mini green" href="javascript:void(0);" onclick="showEditPage('${c.id?string("#")}')"><i class="icon-edit "  ></i> 编辑</a>
					<#if c.status==1>
                        <a title="禁用"  class="btn mini yellow" href="javascript:void(0);"  onclick="disableRole('${c.id?string("#")}')"><i class="icon-stop"  ></i> 禁用</a>
					<#else>
                        <a title="启用" class="btn mini blue" href="javascript:void(0);"  onclick="enableRole('${c.id?string("#")}')"><i class="icon-play"  ></i> 启用</a>
					</#if>
				</div>
            </td>
		</tr>
	</#list>
</#if>
