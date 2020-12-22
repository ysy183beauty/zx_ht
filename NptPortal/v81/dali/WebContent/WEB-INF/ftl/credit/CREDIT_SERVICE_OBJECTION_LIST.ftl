<#include "CommonUtil.ftl"/>
<#assign pagination=_PAGINATION>
<#assign flag = _FLAG>
<#assign flagName = _FLAG_NAME>

<ul class="reply">
<#list pagination.list as l>
<li>
    <div>
        <span>${flagName}回复</span>
        <span><#if l.syncTime??>${l.syncTime?string("yyyy-MM-dd HH:mm:ss")}</#if></span>
        <img class="list_img_1" src="${ctx}/r/cms/www/red/img/xyfw/u63.png" alt="">
    </div>
    <div>
        <p>
            <#if l.response??>
                您在“${flagName}”提出的申请，目前已收到回复
            <#else>
                您在“${flagName}”提出的申请，我们已经收到，目前正在审核中。
            </#if>
        </p>
        <a onclick="show_info('${l.id?string("#")}')"> > </a>
    </div>
</li>
</#list>
</ul>
<div id="kkpager" class="bot_bot kkpager"></div>
<script>
    $(function () {
        //生成分页控件
        kkpager.generPageHtml({
            pagerid: "kkpager",
            pno: ${pagination.pageNo},
            mode : 'click',
            //总页码
            total: Math.ceil(${pagination.totalCount/pagination.pageSize}),
            //总数据条数
            totalRecords: ${pagination.totalCount},
            //链接算法
            click: function (n) {
                //获取第n页数据
                list("${flag}", n, ${pagination.pageSize})
            }
        },true);
    })
</script>