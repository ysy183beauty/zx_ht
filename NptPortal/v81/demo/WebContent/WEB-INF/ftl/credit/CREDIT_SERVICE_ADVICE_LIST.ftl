<#include "CommonUtil.ftl"/>
<#assign pagination = _PAGINATION>

<#list pagination.list as l>
<ul class="list_zx">
    <li>${l.createTime}
        <#if l.response??>
            <img class="list_img_2" src="${ctx}/r/cms/www/red/img/xyfw/u65.png" alt=""/>
        <#else>
            <img class="list_img_1" src="${ctx}/r/cms/www/red/img/xyfw/u63.png" alt=""/>
        </#if>
    </li>
    <li>
        <span>问题：</span>
        <span>${l.text}</span>
    </li>
    <li>
        <span>回复：</span>
        <span>
            <#if l.response??>
            ${l.response}
            <#else>
                暂时还未有回复。
            </#if>
        </span>
    </li>
</ul>
</#list>
<div id="kkpager" class="bot_bot kkpager"></div>
<script>
    $(function () {
        //生成分页控件
        kkpager.generPageHtml({
            pagerid: "kkpager",
            pno: ${pagination.pageNo},
            mode: 'click',
            //总页码
            total: Math.ceil(${pagination.totalCount/pagination.pageSize}),
            //总数据条数
            totalRecords: ${pagination.totalCount},
            //链接算法
            click: function (n) {
                //获取第n页数据
                list(n, ${pagination.pageSize})
            }
        }, true);
    })
</script>
