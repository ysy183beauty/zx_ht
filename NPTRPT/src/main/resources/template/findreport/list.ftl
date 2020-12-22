<#assign dataList = dataPagination.list>
<input id="totalCount" value="${dataPagination.totalCount!}" type="hidden">
<input id="totalPage" value="${dataPagination.totalPage!}" type="hidden">
<input id="pageSize" value="${dataPagination.pageSize!}" type="hidden">
<#assign reportDisplay=findReportDisplay>
<#if dataList?size == 0>
	<tr><td colspan="6">暂无信息</td></tr>
<#else>
	<#assign sn = dataPagination.beginIndex>
	<#list dataList as c>
		<tr>
			<td align="center">${sn + 1 + c_index}</td>
			<#-- 根据报表显示的不同方式：1、findReport方式显示报表 2、通过js方式显示报表 -->
			<#if reportDisplay==1>
				<#if c.rptPath?last_index_of(".cpt") != -1>
					<td align="left"><a class="editBut" onclick="showReport(${c.id!},'${c.rptPath!}')">${c.rptName!}</a></td>
				<#elseif c.rptPath?last_index_of(".frm") != -1>
					<td align="left"><a class="editBut" onclick="showReport(${c.id!},'${c.rptPath!}')">${c.rptName!}</a></td>
				<#else>
				</#if>
			 <#else>
			   <td align="left"><a class="editBut" onclick="showReportDetail(${c.id!},'${c.rptSecondPath!}')">${c.rptName!}</a></td>
			</#if>
            <td align="center">${c.rptCategory!}</td>
            <td align="center">${c.rptHost!}</td>
			<td align="center">${(c.status==1)?string('已发布','未发布')}</td>
			<td>
				<#if c.status==1>
				<a class="disabledBut" href="javascript:void(0);" onclick="disabledData(${c.id});">撤销</a>
				<#else>
                <a class="editBut" href="javascript:void(0);" onclick="editData(${c.id});">编辑</a>
				<a class="enabledBut" href="javascript:void(0);" onclick="enabledData(${c.id});">发布</a>
				</#if>
				<#if isAdmin>
				<a class="deleteBut" href="javascript:void(0);" onclick="deleteData(${c.id});">删除</a>
				</#if>
                <a class="historyBut" href="javascript:void(0);" onclick="historyData(${c.id});">历史版本</a>
			</td>
		</tr>
	</#list>
</#if>
