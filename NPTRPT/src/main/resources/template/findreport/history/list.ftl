<#assign dataList = dataPagination.list>
<input id="totalCount" value="${dataPagination.totalCount!}" type="hidden">
<input id="totalPage" value="${dataPagination.totalPage!}" type="hidden">
<input id="pageSize" value="${dataPagination.pageSize!}" type="hidden">
<#if dataList?size == 0>
<tr><td colspan="4">暂无信息</td></tr>
<#else>
    <#assign sn = dataPagination.beginIndex>
    <#list dataList as c>
    <tr>
        <td align="center">${sn + 1 + c_index}</td>
        <td align="center">${c.createTime!}</td>
        <td align="center">${(c.status==1)?string('正在使用','禁用')}</td>
        <td>
            <a class="downloadBut" href="download.action?id=${c.id}">下载</a>
            <#if c.status!=1>
                <a class="deleteBut" href="javascript:void(0);" onclick="deleteData(${c.id});">删除</a>
            </#if>
        </td>
    </tr>
    </#list>
</#if>
