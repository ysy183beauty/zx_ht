<#include "CommonUtil.ftl"/>
<#if NPT_ACTION_RETURNED_JSON??>
    <#assign data=NPT_ACTION_RETURNED_JSON>
    <#if flag != 1083>
        <div class="con_top">
        <div class="top_con">
            <ul>
                <li class="criteria row">
                    <#list data.webQueryCondition as cond>
                        <div class="col-sm-6 row gover">
                            <span class="col-sm-5">${cond.fieldTitle}：</span>
                            <input class="col-sm-5" type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>
                        </div>
                    </#list>
                </li>
                <li class="row">
                    <input class="col-xs-5" type="button" value="查询" onclick="search(1)"/>
                    <input class="col-xs-5" type="button" value="重置" onclick="resetSearch()"/>
                </li>
            </ul>
        </div>
    </div>
    <#else>
        <#if data.dataList??>
            <#list data.dataList as dataList>
                <div class="col-sm-6">
                        <div>
                            <b class="col-sm-8" title="${dataList.dataArray[1].value}">${dataList.dataArray[1].title} : ${dataList.dataArray[1].value}</b>
                            <span class="box-address col-sm-4">${dataList.dataArray[2].title} : ${dataList.dataArray[2].value}</span>
                        </div>
                        <div class="introduction col-sm-12" >
                        ${dataList.dataArray[3].title} : ${dataList.dataArray[3].value}
                        </div>
                </div>
            </#list>
        <#else>
            <div>暂无数据！</div>
        </#if>
    </#if>
<#else>
    <div style="width:10%;margin:0 auto;color:#000;font-size:18px;">暂无数据</div>
</#if>
<script>
    function resetSearch() {
    <#if data??>
        <#list data.webQueryCondition as cond>
            $("input[name='${cond.fieldDBName}']").val("");
        </#list>
    </#if>
    }
    <#if flag!=1083>
    function search(n) {
        $.ajax({
            url: "/credit/pub/travel/list.do?flag="+${flag},
            data: {
                poolId: 121,
            pageSize:<#if data??> ${data.pageSize}<#else>0</#if>,
                currPage: n,
            <#if data??>
                <#list data.webQueryCondition as cond>
                    "webQueryCondition[${cond?index}].fieldTitle": "${cond.fieldTitle}",
                    "webQueryCondition[${cond?index}].fieldDBName": "${cond.fieldDBName}",
                    "webQueryCondition[${cond?index}].fieldQueryValue": $("input[name='${cond.fieldDBName}']").val(),
                </#list>
            </#if>
            },
            success: function (html) {
                    var obj = window.open("about:blank");
                    obj.document.write(html);
            }
        })
    }
    </#if>
</script>