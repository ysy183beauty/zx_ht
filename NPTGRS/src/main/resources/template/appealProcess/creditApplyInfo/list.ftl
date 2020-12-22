<#assign dataList = dataPagination.list>
<input id="totalCount" value="${dataPagination.totalCount!}" type="hidden">
<input id="totalPage" value="${dataPagination.totalPage!}" type="hidden">
<input id="pageSize" value="${dataPagination.pageSize!}" type="hidden">
<#if dataList?size == 0>
	<tr><td colspan="6">暂无信息</td></tr>
<#else>
	<#assign sn = dataPagination.beginIndex>
	<#list dataList as c>
		<tr>
			<td align="center">${sn + 1 + c_index}</td>
            <td>${c.type!"未知"}</td>
			<td>${c.realname!"未知"}</td>
            <td>${c.idCard!"未知"}</td>
            <td data-container="body" title="${c.createTime}" class="tooltips">${c.createTime?string("yyyy-MM-dd")}</td>
            <td data-container="body" title="${c.syncTime}" class="tooltips">${c.syncTime?string("yyyy-MM-dd")}</td>
            <td>
				<#if c.syncStatus==2>
					<div class="alert alert-info">申请处理中</div>
				<#elseif c.syncStatus==0>
                    <div class="alert alert-error">申请失败</div>
				<#elseif c.syncStatus==1>
                    <div class="alert alert-success">申请成功</div>
				<#else>
                    <div class="alert">未知状态</>
				</#if>
            </td>
		</tr>
	</#list>
</#if>