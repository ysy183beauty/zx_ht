<#include "CommonUtil.ftl">

<#assign data = NPT_ACTION_RETURNED_JSON>
            <div class="con_top">
                <h2>查询详情</h2>
            </div>
            <div class="con-bottom">
                <div class="bot_con">
                <#if data.dataList?? && data.dataList?size gt 0>
                    <table class="table-striped table-hover" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse">
                        <thead>
                        <#assign list=data.dataList[0]>
                            <#list list.dataArray as array>
                                    <#if array_index gt 0>
                                    <th>${array.title}</th>
                                    </#if>
                            </#list>

                                </thead>
                                <tbody>
                                    <#list data.dataList as list>
                                    <tr onclick="detail(${list.dataArray[0].value})">
                                        <#list list.dataArray as array>
                                            <#if array_index gt 0>
                                                <td> ${array.value}</td>
                                            </#if>
                                        </#list>
                                        <#--<td><a href="/credit/query/bs/detail.do?ukValue=${list.dataArray[0].value}" target="_blank">详细数据</a></td>-->
                                    </tr>
                                    </#list>
                        </tbody>
                    </table>
                <#else>
                    <div class="no-found">暂无数据</div>
                </#if>
                </div>
                <div class="bot_bot">
                    <!--<div id="kkpager" class="bot_bot kkpager"></div>-->
                </div>
            </div>
        </div>
    </div>
</div>

<!--<script>
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

</script>-->
