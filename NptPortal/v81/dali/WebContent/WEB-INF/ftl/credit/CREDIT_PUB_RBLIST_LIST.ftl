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
             <!--   <div class="col-sm-6 row gover">
                    <span class="col-sm-6">图片验证码：</span>
                    <input class="focus col-sm-3" id="captcha" type="text" name="验证码" placeholder="请输入验证码" />
                    <img style="cursor: pointer;height:34px;border:1px solid #1AB394;" onclick="this.src='/captcha.svl?d='+new Date()" alt="" src="/captcha.svl" class="focus col-sm-3">
                </div>-->
            </li>
            <li>
                <input type="button" value="查询" onclick="search(1)"/>
                <input type="button" value="重置" onclick="resetSearch()"/>
            </li>
        </ul>
    </div>
</div>
<#--红黑榜详细描述-->
<#if data.webNote??>
    <div class="caption alert">
        ${data.webNote}
    </div>
</#if>

<div class="con-bottom">
    <div class="bot_con">
        <table class="table-striped table-hover" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
            <thead>
            <#list data.columnTitles as title>
                <#if title_index gt 0>
                    <th>${title}</th>
                </#if>
            </#list>
            <#--<th>数据详情</th>-->
            </thead>
            <tbody>
            <#if data.dataList??>
                <#list data.dataList as list>
                <tr onclick="detail(${RequestParameters.poolId},${list.dataArray[0].value})">
                    <#list list.dataArray as array>
                        <#if array_index gt 0>
                            <td> ${array.value}</td>
                        </#if>
                    </#list>

                    <#--<td><a href="/credit/pub/rbl/detail.do?poolId=${RequestParameters.poolId}&primaryKeyValue=${list.dataArray[0].value}" target="_blank">详细数据</a></td>-->
                </tr>
                </#list>
            <#else>
            <tr>
                <td colspan="${data.columnTitles?size}">暂无数据</td>
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
            url: "/credit/pub/rbl/list.do",
            data: {
                poolId: ${RequestParameters.poolId},
                pageSize: ${data.pageSize},
                currPage: n,
               // captcha:captcha,
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