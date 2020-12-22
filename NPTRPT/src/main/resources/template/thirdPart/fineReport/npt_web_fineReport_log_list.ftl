<#if dataList?size == 0>
<tr><td colspan="5" align="center">暂无信息</td></tr>
<#else>
    <#list dataList as c>
    <tr>
        <td  align="center">${c?index + 1}</td>
        <#--<td title="${c.rpmc!}信用报告"  >${c.rpmc!}信用报告</td>-->
        <td title="${c.template!}"  >${c.template!}</td>
        <td title="${c.createTime?string('yyyy-MM-dd')}"  >${c.createTime?string('yyyy-MM-dd')}</td>
        <td ><a onclick="showReportHistory('${c.fileName!}')" class="btn mini green"><i class="icon-download-alt"></i> 下载</a></td>
    </tr>
    </#list>
</#if>
