<#include "CommonUtil.ftl"/>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/js/pager/css/kkpager.css" />
<style type="text/css">
    *{padding:0px; margin:0px;list-style: none;font-size:1em;}
    body{
        background-color:#f2f2f2;
        overflow: scroll;
        /*overflow-x: hidden;*/
        /*overflow-y: hidden;*/
    }
    .con_top{
        margin-top:2em;
        margin-bottom:5em;
    }
    .con_top li.row{
        background-color:#fff;
        margin-bottom:4em;
    }

    .gover{
        /*width:90%;*/
        margin:0 auto;
        border-bottom:1px solid #ccc;
        line-height: 6em;
        height:6em;
    }
    .gover span{
        display:inline-block;
        height:100%;
        text-align: left;
    }
    .gover input[type=text]{
        height:100%;
        border:none;
        outline:none;
        text-align: right;
        white-space: nowrap;
    }
    .button{
        margin-bottom:1em;
    }
    .table-responsive{
        background-color:#fff;
    }
    .table-responsive th,.table-responsive td{
        text-align: center;
        padding:1em 0;
    }
    thead th{
        background: #999!important;
        color:#fff!important;
    }
</style>
<#if _RESULT.code == 0>
    <#assign data =NPT_ACTION_RETURNED_JSON>
<#--<div class="con_top">-->
    <#--<div class="top_con">-->
        <#--<ul>-->
            <#--<li class="criteria row">-->
                <#--<#list data.webQueryCondition as cond>-->
                    <#--<div class="col-sm-10 row gover">-->
                        <#--<span class="col-xs-4">${cond.fieldTitle}：</span>-->
                        <#--<input class="col-xs-8" type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>-->
                    <#--</div>-->
                <#--</#list>-->
            <#--</li>-->
            <#--<li class="button">-->
                <#--<input class="btn btn-danger btn-block" type="button" value="查询" onclick="search(1)"/>-->
            <#--</li>-->
            <#--<li class="button">-->
                <#--<input class="btn btn-primary btn-block" type="button" value="重置" onclick="resetSearch()"/>-->
            <#--</li>-->
        <#--</ul>-->
    <#--</div>-->
<#--</div>-->
<div class="con-bottom">
    <div class="bot_con">
        <table class="table table-striped" border="1" width="100%" cellpadding="0" cellspacing="0" style="text-align: left;border-collapse:collapse">
            <thead>
                <#list data.columnTitles as title>
                    <#if title_index < 3>
                    <th>${title}</th>
                    </#if>
                </#list>
            </thead>
            <tbody>
                <#if data.dataList??>
                    <#list data.dataList as list>
                    <tr onclick="${list.dataArray[0].value}">
                        <#list list.dataArray as array>
                            <#if array_index < 3>
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
    <#--<div class="bot_bot">-->
        <#--<div id="kkpager" class="bot_bot kkpager"></div>-->
    <#--</div>-->
</div>
<#else >
<table class="table-hover table-striped" border="1" width="100%" cellpadding="0" cellspacing="0" style="text-align: left;border-collapse:collapse">
    <tr>
        <td>${_RESULT.title}</td>
    </tr>
</table>
</#if>
<#--</body>-->
<#--</html>-->
<script>
//    $("table tr").bind("click",function(){
//        var va = $(this).find("td").eq(0).text();
//        window.location.href="/credit/pub/ucc/detailMobile.do?ukValue="+va;
//    })




    $(function () {
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
    });

    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 分页查询
     */
    function search(n) {
        $.ajax({
            url: "/credit/pub/ucc/index.do",
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
                $("#mainContent").html(html);
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

//    function detail(kval) {
//        $.ajax({
//            type:"POST",
//            url:"/credit/pub/ucc/detailMobile.do",
//            data:{
////                poolId : uid ,
//                ukValue : kval
//            },
//            success:function(html){
//                var obj = window.open("about:blank");
//                //var obj = window.location.href="/credit/pub/ucc/detailMobile.do?ukValue=" + kval;
//                obj.document.write(html);
//            }
//        });
//    }
</script>