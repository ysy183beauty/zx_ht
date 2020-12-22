<#include "CommonUtil.ftl">
<style>
    #container h2{
        padding:50px 0;
        border-bottom: 1px solid #ccc;
        color: rgb(58, 114, 174);
    }
    .no-found {
        font-weight: bold;
        text-align: center;
        font-size: 20px;
    }
</style>
    <#assign data = NPT_ACTION_RETURNED_JSON>

    <div id="container">

            <h2>查询详情</h2>

        <div class="con-bottom">
            <#--<div class="load_main">-->
                <#--<img src="/r/cms/www/red/img/load.gif">-->
            <#--</div>-->
            <div class="bot_con">
                <#if data.dataList?? && data.dataList?size gt 0>
                    <table class="table-striped table-hover" border="1" width="100%" cellpadding="0" cellspacing="0"
                           style="border-collapse:collapse">
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
                <div id="kkpager" class="bot_bot kkpager"></div>
            </div>
        </div>
    </div>

<script>
    function detail(uid) {
        $(".load_main").show();
        $.ajax({
            type:"POST",
            url:"/credit/query/bs/detail.do",
            data:{ukValue:uid},
            success:function(html){
                var obj = window.open("about:blank");
                obj.document.write(html);
                $(".load_main").hide();
            }
        });
//        window.open("/credit/query/bs/detail.do?ukValue=" + uid);
    }
//    var URLParams = new Array();
//    var aParams = document.location.search.substr(1).split('&');
//    for (i=0; i < aParams.length ; i++){
//        var aParam = aParams[i].split('=');
//        URLParams[aParam[0]] = aParam[1];
//    }
//    var key=URLParams["keyword"];

    //    $("title").text("搜索："+decodeURIComponent(key));
    <#--$(function () {-->
        <#--//生成分页控件-->
        <#--kkpager.generPageHtml({-->
            <#--pagerid: "kkpager",-->
            <#--pno: ${data.currPage},-->
            <#--mode : 'click',-->
            <#--//总页码-->
            <#--total: Math.ceil(${data.totalCount/data.pageSize}),-->
            <#--//总数据条数-->
            <#--totalRecords: ${data.totalCount},-->
            <#--//链接算法-->
            <#--click: function (n) {-->
                <#--//获取第n页数据-->
                <#--search(n);-->
            <#--}-->
        <#--},true);-->
    <#--});-->

    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 分页查询
     */
    function search(n) {
        $.ajax({
            url: "/credit/query/bs/bsSearch.do",
            data: {
                poolId: 121,
                pageSize: ${data.pageSize},
                currPage: n,
                keyword:$("#queryInput").val()
            },
            success: function (html) {
                $("#searchContent").html(html);
            },
            error:function () {
                $("#searchContent").html("请求失败！");
            },
            timeout:function () {
                $("#searchContent").html("数据请求超时！");
            }
        })
    }
    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 重置搜索框
     */
    <#--function resetSearch() {-->
    <#--<#list data.webQueryCondition as cond>-->
    <#--$("input[name='${cond.fieldDBName}']").val("");-->
    <#--</#list>-->
    <#--}-->
</script>