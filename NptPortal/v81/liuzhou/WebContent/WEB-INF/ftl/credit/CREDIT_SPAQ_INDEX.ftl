<#include "CommonUtil.ftl"/>
<style>
    .row>.btn{
        color:#fff!important;
        height:auto!important;
    }
</style>
<#if NPT_ACTION_RETURNED_JSON??>
    <#assign data=NPT_ACTION_RETURNED_JSON>
    <#if flag lt 1094>
        <div class="con_top">
            <div class="top_con">
                <ul>
                    <li class="criteria row cond_input_${flag}">
                        <#list data.webQueryCondition as cond>
                            <div class="col-sm-6 row gover">
                                <span class="col-sm-5">${cond.fieldTitle}：</span>
                                <input class="col-sm-5" type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>
                            </div>
                        </#list>
                    </li>
                    <li class="row">
                        <input class="col-xs-5 btn btn-primary" type="button" value="查询" onclick=search_${flag}(1)>
                        <input class="col-xs-5 btn btn-success" type="button" value="重置" onclick="resetSearch(${flag})"/>
                    </li>
                </ul>
            </div>
        </div>
        <div class="info_bottom"></div>
    <#elseif flag gt 10100>
        <div class="con_top">
            <div class="top_con">
                <ul>
                    <li class="criteria row cond_input_${flag}">
                        <#list data.webQueryCondition as cond>
                            <div class="row gover">
                                <span class="col-sm-4">${cond.fieldTitle}：</span>
                                <input class="col-sm-5" type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>
                            </div>
                        </#list>
                    </li>
                    <li class="row">
                        <#assign flagNum = flag>
                        <input class="col-xs-5 btn btn-primary" type="button" value="查询" onclick=search_${flag}(1)>
                        <input class="col-xs-5 btn btn-success" type="button" value="重置" onclick="resetSearch(${flag})"/>
                    </li>
                </ul>
            </div>
        </div>
        <div class="info_bottom"></div>
    <#else>
        <div class="con_top">
            <div class="top_con">
                <ul>
                    <li class="criteria row cond_input_${flag}">
                        <#list data.webQueryCondition as cond>
                            <div class="row gover">
                                <span class="col-sm-4">${cond.fieldTitle}：</span>
                                <input class="col-sm-5" type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>
                            </div>
                        </#list>
                    </li>
                    <li class="row">
                        <#assign flagNum = flag>
                        <input class="col-xs-5 btn btn-primary" type="button" value="查询" onclick=search_${flag}(1)>
                        <input class="col-xs-5 btn btn-success" type="button" value="重置" onclick="resetSearch(${flag})"/>
                    </li>
                </ul>
            </div>
        </div>
        <div class="info_bottom"></div>
    </#if>
<#else>
    <div style="width:10%;margin:0 auto;color:#000;font-size:18px;">暂无数据</div>
</#if>
<script>
    function resetSearch(flag){
        $(".cond_input_"+flag+" input").val("");
    }
    <#--<#if flag!=1083>-->
    var id;
    var ID;
    $(".searchBar>div").hover(function(){
        ID=$(this).attr("id");
        console.log(ID);
    });
    <#if flag lt 1094>
        function search_${flag}(n) {
            <#assign num=flag-1091>
            id =${groups[num].id};
            $.ajax({
                url: "/credit/pub/spaq/list.do?flag=" +${flag},
                data: {
                    poolId: id,
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
                    <#if flag == 1091>
                        $("#foodSafe-safe .info_bottom").html(html);
                    <#elseif flag == 1092>
                        $("#foodSafe-black .info_bottom").html(html);
                    <#elseif flag == 1093>
                        $("#foodSafe-al .info_bottom").html(html);
                    </#if>
                }
            });
        }
    <#elseif flag lt 10103 && flag gt 10100>
        function search_${flag}(n) {
            <#assign num=flag-10101>
            id=${groups[num].id};
            $.ajax({
                url: "/credit/pub/bzxrxyjg/list.do?flag="+${flag},
                data: {
                    poolId: id,
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
                    <#if flag == 10101>
                        $("#xyjg-bzx .info_bottom").html(html);
                    <#elseif flag == 10102>
                        $("#xyjg-sxbzx .info_bottom").html(html);
                    </#if>
                }
            });
        }
    <#else>
        function search_${flag}(n) {
            <#assign num=flag-10111>
            id=${groups[num].id};
            $.ajax({
                url: "/credit/pub/gcjs/list.do?flag="+${flag},
                data: {
                    poolId: id,
                    pageSize:<#if data??> ${data.pageSize}<#else>0</#if>,
                    currPage: n,
                    <#if data??>
                        <#list data.webQueryCondition as cond>
                            "webQueryCondition[${cond?index}].fieldTitle": "${cond.fieldTitle}",
                            "webQueryCondition[${cond?index}].fieldDBName": "${cond.fieldDBName}",
                            "webQueryCondition[${cond?index}].fieldQueryValue": $("#"+ID+" input[name='${cond.fieldDBName}']").val(),
                        </#list>
                    </#if>
                },
                success: function (html) {
                    <#if flag == 10111>
                        $("#construction-engineer .info_bottom").html(html);
                    </#if>
                }
            });
        }
    </#if>
</script>