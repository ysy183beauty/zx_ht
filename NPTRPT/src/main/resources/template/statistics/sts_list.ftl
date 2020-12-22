<#include "/template/Credit_Common/common.ftl">

<#assign dataList = dataPagination.list>
<#assign data = dataPagination>
<input id="totalCount" value="${data.totalCount!}" type="hidden">
<input id="totalPage" value="${data.totalPage!}" type="hidden">
<input id="pageSize" value="${data.pageSize!}" type="hidden">
<#if dataList?size == 0>
<div>暂无信息</div>
<#else>
    <#assign sn = dataPagination.beginIndex>
<div class="row-fluid">
    <#list dataList as c>
        <#if c?index%3==0 && c?index!=0>
</div>
<div class="row-fluid">
        </#if>
    <div class="span4 stsBox">
        <p class="pic">
            <a href="${ctx}/${c.url}">
                <img src="${wctx}/pub/statistics/img/${c.id?string('#')}.png" />
            </a>
        </p>
        <p class="title">${sn + c?index+1}. ${c.title}</p>
    </div>
    </#list>
</div>
</#if>
