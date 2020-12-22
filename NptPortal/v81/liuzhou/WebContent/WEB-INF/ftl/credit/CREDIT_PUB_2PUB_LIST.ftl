<#include "CommonUtil.ftl"/>
<#assign data = NPT_ACTION_RETURNED_JSON>


    <div class="bot_con">
        <table class="table-striped table-hover table-2pub" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;table-layout:fixed;">

        <#if data.totalCount gt 0>
            <thead>
                <#list data.columnTitles as title>
                    <#if title_index!=0>
                    <th>${title!}</th>
                    </#if>
                </#list>
            <th>数据详情</th>
            </thead>
        </#if>
            <tbody>
            <#if data.dataList??>
                <#list data.dataList as list>
                <tr>
                    <#list list.dataArray as array>
                        <#if array_index gt 0>
                            <td> ${array.value}</td>
                        </#if>

                    </#list>

                    <td onclick="detail(${list.dataArray[0].value})"><a>详细数据</a></td>
                </tr>
                </#list>
            <#else>
            <tr>
                <td colspan="${data.columnTitles?size}">暂无数据</td>
            </tr>
            </#if>
            </tbody>
        </table>
        <div class="bot_bot">
            <div id="kkpager" class="bot_bot kkpager"></div>
        </div>
    </div>


<script>
    $(function () {
        //生成分页控件
        kkpager.generPageHtml({
            pagerid: "kkpager",
            pno: ${data.currPage},
            mode : 'click',
            //总页码
            total: Math.ceil(${data.totalCount/data.pageSize}),
            //总数据条数
            totalRecords: ${data.totalCount},
            //链接算法
            click: function (n) {
                //获取第n页数据
                search(n);
            }
        },true);
    });
</script>