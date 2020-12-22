<#include "CommonUtil.ftl"/>
<#assign data = NPT_ACTION_RETURNED_JSON>
<div class="con_top">
    <div class="top_title">
        <span> 当前选择：> <b> </b></span>
        <a class="more">更多 >></a>
    </div>
    <div class="top_con">
        <ul>
            <li>
                <span class="sp_tit">许可机关：</span>
                <span>州发展改革委</span>
                <span>州工信委</span>
                <span>州教育局</span>
                <span>州科技局</span>
                <span>州公安局</span>
                <span>
                    <input type="checkbox" checked disabled/>
                    展示全部单位
                </span>
            </li>
            <li>
                <span class="sp_tit">公示时间：</span>
                <span><b>当天 </b><b>七天 </b><b>一个月</b><b>三个月 </b></span>
                <span>更多</span><span>主体：</span><span>企业</span><span>个人</span>
            </li>
            <li class="row">
            <#list data.webQueryCondition as cond>
                <div class="col-md-6 row gover">
                    <span class="col-md-5">${cond.fieldTitle}：</span>
                    <input type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>
                </div>
            </#list>
            </li>
            <li>
                <input type="button" value="查询" onclick="search(1)"/>
                <input type="button" value="重置" onclick="resetSearch()"/>
            </li>
        </ul>
    </div>
</div>
<div class="con-bottom">
    <div class="bot_title">公示信息</div>
    <div class="bot_con">
        <table border="1" width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed;word-break:break-all;border-collapse:collapse;">
            <thead>
            <#list data.columnTitles as title>
            <th>${title}</th>
            </#list>
            <th style="display: none">职权详情</th>
            <th>数据详情</th>
            </thead>
            <tbody>
            <#if data.dataList??>
                <#list data.dataList as list>
                <tr>
                    <#list list.dataArray as array>
                        <td> ${array.value}</td>
                    </#list>

                    <td style="display: none" ><a href="/credit/pub/2pub/province.do">详细信息</a></td>
                    <td><a href="/credit/pub/2pub/detail.do?poolId=${RequestParameters.poolId}&primaryKeyValue=${list.dataArray[0].value}" target="_blank">详细数据</a></td>
                </tr>
                </#list>
            <#else>
            <tr>
                <td colspan="${data.columnTitles?size+2}">暂无数据</td>
            </tr>
            </#if>
            </tbody>
        </table>
    </div>
    <div class="bot_bot">
        <div id="kkpager" class="bot_bot kkpager"></div>
    </div>
</div>
<script>

    $(".more").bind("click",function () {
        $(".top_nav").slideDown(1000);
    });
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

    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 分页查询
     */
    function search(n) {
        $.ajax({
            url: "/credit/pub/2pub/list.do",
            data: {
                poolId: ${RequestParameters.poolId},
                pageSize: ${data.pageSize},
                currPage: n,
            <#list data.webQueryCondition as cond>
                "webQueryCondition[${cond?index}].fieldTitle": "${cond.fieldTitle}",
                "webQueryCondition[${cond?index}].fieldDBName": "${cond.fieldDBName}",
                "webQueryCondition[${cond?index}].fieldQueryValue": $("input[name='${cond.fieldDBName}']").val(),
            </#list>
            },
            success: function (html) {
                $(".md_content").html(html);
            }
        })
    }
    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 重置搜索框
     */
    function resetSearch() {
    <#list data.webQueryCondition as cond>
        $("input[name='${cond.fieldDBName}']").val("");
    </#list>
    }
</script>