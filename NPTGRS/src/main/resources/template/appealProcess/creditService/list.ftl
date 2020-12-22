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
            <td>${c.userType!}</td>
            <td>${c.userName!}</td>
            <td>${c.flag!}</td>
            <td title="${c.createTime}">${c.createTime?string("yyyy-MM-dd")}</td>
            <td>
				<#if c.syncStatus==0>
                    待回复
				<#else>
                    已回复
				</#if>
            </td>
            <td>
                <div class="controls">
					<#if c.syncStatus==0>
                        <a data-toggle="modal" data-id="${c.id}" data-target="#modalDiv" class="btn mini green"><i class="icon-edit"></i>回复</a>
					<#else>
                        <a data-toggle="modal" data-id="${c.id}" data-target="#modalDiv" class="btn mini blue"><i class="icon-search"></i>查看</a>
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
