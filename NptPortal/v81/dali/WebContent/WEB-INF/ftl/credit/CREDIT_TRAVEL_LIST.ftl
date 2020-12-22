<#include "CommonUtil.ftl"/>

<#assign data =NPT_ACTION_RETURNED_JSON>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>旅游查询</title>
<#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/publicity.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/tableDate.css"/>
</head>
<body>
<div class="wrap">
<#include "include/header.ftl"/>
    <div class="main">
        <div id="container" >
<div class="con_top">
    <div class="top_con">
        <ul>
            <li class="criteria row">
                <#list data.webQueryCondition as cond>
                    <div class="col-sm-6 row gover">
                        <span class="col-xs-5">${cond.fieldTitle}：</span>
                        <input class="col-xs-5" type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>
                    </div>
                </#list>
            </li>
            <li class="row">
                <input class="col-xs-4" type="button" value="查询" onclick="search(1)"/>
                <input class="col-xs-4" type="button" value="重置" onclick="resetSearch()"/>
            </li>
        </ul>
    </div>
</div>
<div class="con-bottom">
    <div class="bot_con">
        <table class="table-hover table-striped" border="1" width="100%" cellpadding="0" cellspacing="0" style="text-align: left;border-collapse:collapse">
            <thead>
                <#list data.columnTitles as title>
                    <#if title_index gt 0>
                    <th>${title}</th>
                    </#if>
                </#list>
            </thead>
            <tbody>
                <#if data.dataList??>
                    <#list data.dataList as list>
                    <tr onclick="detail(${list.dataArray[0].value})">
                        <#list list.dataArray as array>
                            <#if array_index gt 0>
                                <td> ${array.value}</td>
                            </#if>
                        </#list>
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
</div>
    </div>
</div>
    <#include "include/footer.ftl"/>

</body>
</html>
<script>

        //生成分页控件
        kkpager.generPageHtml({
            pagerid: "kkpager",
        pno: <#if data??>${data.currPage}<#else>0</#if>,
            mode : 'click',
            //总页码
        total:<#if data??>Math.ceil(${data.totalCount/data.pageSize})<#else>0</#if> ,
            //总数据条数
        totalRecords: <#if data??>${data.totalCount}<#else>0</#if>,
            //链接算法
            click: function (n) {
                //获取第n页数据
                search(n);
            }
        },true);


    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 分页查询
     */
    function search(n) {
        $.ajax({
            url: "/credit/pub/travel/list.do?flag="+${flag},
            data: {
//                poolId: 121,
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
                $("body").html(html);
            },
            error:function () {
                $("body").html("请求失败！");
            },
            timeout:function () {
                $("body").html("数据请求超时！");
            }
        })
    }
    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 重置搜索框
     */
    function resetSearch() {
    <#if data??>
        <#list data.webQueryCondition as cond>
            $("input[name='${cond.fieldDBName}']").val("");
        </#list>
    </#if>

    }

    function detail(uid) {

        $.ajax({
            type:"POST",
            url:"/credit/pub/travel/detail.do",
            data:{
                flag:${flag},
                primaryKeyValue:uid},
            success:function(html){
                var obj = window.open("about:blank");
                obj.document.write(html);
            }
        });
        /**
         window.open("/credit/query/bs/detail.do?ukValue=" + uid);
         */
    }
</script>