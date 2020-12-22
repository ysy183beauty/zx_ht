<#assign dataList = dataPagination.list>
<input id="totalCount" value="${dataPagination.totalCount!}" type="hidden">
<input id="totalPage" value="${dataPagination.totalPage!}" type="hidden">
<input id="pageSize" value="${dataPagination.pageSize!}" type="hidden">
<#if dataList?size == 0>
	<tr><td colspan="7">暂无信息</td></tr>
<#else>
	<#assign sn = dataPagination.beginIndex>
	<#list dataList as c>
		<tr>
			<td align="center">${sn + 1 + c_index}</td>
            <td>${(c.type == "company")?string("企业","个人")}</td>
			<td>${c.realname!}</td>
            <td>${c.mobile!}</td>
            <td>${c.idCard!}</td>
            <td>
				<#if c.syncStatus==0>
                    待认证
				<#else>
                    已认证
				</#if>
            </td>
            <td>
                <div class="controls">
					<#if c.syncStatus == 0>
                        <a onclick="showUserInfo('${c.type}','${c.idCard}','${c.id}')" class="btn mini green"><i class="icon-edit"></i>实名认证</a>
					<#else>
                            <a onclick="showUserInfo('${c.type}','${c.idCard}','${c.id}')" class="btn mini blue"><i class="icon-search"></i>查看</a>
					</#if>
					<#--<#if c.syncStatus == 1>-->
                        <#--已同步-->
					<#--<#elseif c.syncStatus == 2>-->
                        <#--等待同步<a onclick="syncResponse()" class="btn mini yellow"><i class="icon-refresh"></i>立即同步</a>-->
					<#--</#if>-->
                </div>
            </td>
		</tr>
	</#list>
</#if>
