<#include "CommonUtil.ftl"/>

<#assign data =NPT_ACTION_RETURNED_JSON>
<#--<html>-->
<#--<head lang="en">-->
    <#--<meta charset="UTF-8">-->
    <#--<meta http-equiv="X-UA-Compatible" content="IE=10"/>-->
    <#--<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>-->
    <#--<title>旅游查询</title>-->
<#--<#include "include/header_link.ftl"/>-->
    <#--<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css"/>-->
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/publicity.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/tableDate.css"/>
<#--</head>-->
<#--<body>-->
<#--<div class="wrap">-->
<#--<#include "include/header.ftl"/>-->
    <#--<div class="main">-->
        <#--<div id="container" >-->
            <#--<div class="con_top">-->
                <#--<div class="top_con">-->
                    <#--<ul>-->
                        <#--<li class="criteria row">-->
                        <#--<#list data.webQueryCondition as cond>-->
                            <#--<div class="col-sm-6 row gover">-->
                                <#--<span class="col-xs-5">${cond.fieldTitle}：</span>-->
                                <#--<input class="col-xs-5" type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>-->
                            <#--</div>-->
                        <#--</#list>-->
                        <#--</li>-->
                        <#--<li class="row">-->
                            <#--<input class="col-xs-4" type="button" value="查询" onclick="search(1)"/>-->
                            <#--<input class="col-xs-4" type="button" value="重置" onclick="resetSearch()"/>-->
                        <#--</li>-->
                    <#--</ul>-->
                <#--</div>-->
            <#--</div>-->
            <div class="con-bottom" >
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
                            <tr onclick="detail_${flag}(${list.dataArray[0].value})">
                                <#list list.dataArray as array>
                                    <#if array_index gt 0>
                                        <td> ${array.value}</td>
                                    </#if>
                                </#list>
                            </tr>
                            </#list>
                        <#else>
                        <tr>
                            <td colspan="${data.columnTitles?size}" style="text-align: center;">暂无数据</td>
                        </tr>
                        </#if>
                        </tbody>
                    </table>
                </div>
                <div class="bot_bot">
                    <div id="kkpager_${flag}" class="bot_bot kkpager"></div>
                </div>
            </div>
        <#--</div>-->
    <#--</div>-->
<#--</div>-->
<#--<#include "include/footer.ftl"/>-->

<#--</body>-->
<#--</html>-->
<script>
$(function(){
    var id;
    $(".kkpager").hover(function(){
        id=$(this).attr("id");
    });
    //生成分页控件
    kkpager.generPageHtml({
        pagerid: "kkpager_${flag}",
        pno: <#if data??>${data.currPage}<#else>0</#if>,
        mode : 'click',
        //总页码
        total:<#if data??>Math.ceil(${data.totalCount/data.pageSize})<#else>0</#if> ,
        //总数据条数
        totalRecords: <#if data??>${data.totalCount}<#else>0</#if>,
        isGoPage:false,
        //链接算法
        click: function (n) {
            //获取第n页数据
            console.log(id);
            if(id == "kkpager_1091"){
                search_1091(n);
            }
            if(id == "kkpager_1092"){
                search_1092(n);
            }
            if(id == "kkpager_1093"){
                search_1093(n);
            }
            if(id == "kkpager_10101"){
                search_10101(n);
            }
            if(id == "kkpager_10102"){
                search_10102(n);
            }
            if(id == "kkpager_10111"){
                search_10111(n);
            }
        }
    },true);

});
    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 分页查询
     */

    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 重置搜索框
     */

    function detail_${flag}(kval) {

        <#if flag lt 1094>
             window.open("/credit/pub/spaq/detail.do?poolId=${pool.id}&&primaryKeyValue=" + kval);
         <#elseif flag gt 10100 && flag lt 10103>
             console.log(${flag});
             window.open("/credit/pub/bzxrxyjg/detail.do?poolId=${pool.id}&&primaryKeyValue=" + kval);
         <#elseif flag == 10111>
            window.open("/credit/pub/gcjs/detail.do?poolId=${pool.id}&&primaryKeyValue=" + kval);
        </#if>
    }
</script>