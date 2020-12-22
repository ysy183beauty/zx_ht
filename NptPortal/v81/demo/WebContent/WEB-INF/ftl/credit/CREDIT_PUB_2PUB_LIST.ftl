<#include "CommonUtil.ftl"/>
<#assign data = NPT_ACTION_RETURNED_JSON>
<div class="con_top">
    <div class="top_con">
        <ul>
            <li class="criteria row">
            <#list data.webQueryCondition as cond>
                <div class="col-sm-6 row gover">
                    <span class="col-sm-6">${cond.fieldTitle}：</span>
                    <input class="focus col-sm-6" type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>
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

    <div class="bot_con">
        <table class="table-striped table-hover" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
            <thead>
            <#list data.columnTitles as title>
                <#if title_index gt 0>
                    <th>${title}</th>
                </#if>
            </#list>
            <th>职权详情</th>
            <th>数据详情</th>
            </thead>
            <tbody>
            <#if data.dataList??>
            <#list data.dataList as list>
            <tr>
                <#list list.dataArray as array>
                <#if array_index gt 0>
                    <td> ${array.value}</td>
                </#if>

            </#list>

                <td onclick="province(${RequestParameters.poolId})"><a>详细信息</a></td>
                <td onclick="detail(${RequestParameters.poolId},${list.dataArray[0].value})"><a>详细数据</a></td>
            </tr>
            </#list>
            <#else>
            <tr>
                <td colspan="${data.columnTitles?size+1}" style="text-align: center">暂无数据</td>
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
    function detail(uid,kval) {
        $.ajax({
            type:"POST",
            url:"/credit/pub/2pub/detail.do",
            data:{
                poolId : uid ,
                primaryKeyValue : kval
            },
            success:function(html){
                var obj = window.open("about:blank");
                obj.document.write(html);
            }
        });
    }
    function province(pid) {
        $.ajax({
            type:"POST",
            url:"/credit/pub/2pub/province.do",
            data:{
                poolId : pid
            },
            success:function(html){
                var obj = window.open("about:blank");
                obj.document.write(html);
            }
        });
    }

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
                $("#md_content").html(html);
            },
            error:function(msg){
                $("#md_content").html("暂无数据！");
            }
        });
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