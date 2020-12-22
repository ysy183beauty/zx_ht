<#assign dataList = NPT_ACTION_RETURNED_JSON>
<#if dataList?size == 0>

<#else>
	<#list dataList as c>
		<tr>
			<td align="center">${c_index+1}</td>
			<td align="left">${c.loginName!}</td>
			<td align="left">${c.userName!}</td>
			<td align="center">${c.enable?string('启用','禁用')}</td>
			<td align="left">
				<#if _ORG_LIST??>
				    <#list _ORG_LIST as a>
						<#if c.orgId??>
								<#if a.id?string("#") == c.orgId>
									${a.orgName!}
								</#if>
						</#if>
				    </#list>
				</#if>
			</td>
			<td>
				<div class="controls">
                    <a title=分配角色" class="btn mini green" href="javascript:void(0);" onclick="setRolePage(${c.userId});"><i class="icon-edit "  ></i> 分配角色</a>
                    <a title="编辑" class="btn mini green" href="javascript:void(0);" onclick="editUser(${c.userId});"><i class="icon-edit "  ></i> 编辑</a>
					<#if c.enable>
                        <a title="禁用"  class="btn mini yellow" href="javascript:void(0);" onclick="disabledUser(${c.userId});"><i class="icon-stop"  ></i> 禁用</a>
					<#else>
                        <a title="启用" class="btn mini blue" href="javascript:void(0);" onclick="enabledUser(${c.userId});"><i class="icon-play"  ></i> 启用</a>
					</#if>
				</div>
            </td>
		</tr>
	</#list>
</#if>
