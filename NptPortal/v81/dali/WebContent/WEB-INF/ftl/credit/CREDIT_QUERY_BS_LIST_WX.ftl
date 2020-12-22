<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
    <script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
    <script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
    <script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/js/pager/css/kkpager.css" />
    <style type="text/css">
        *{padding:0px; margin:0px;list-style: none;font-size:14px;}
        body{
            background-color:#f2f2f2;
            overflow: scroll;
            /*overflow-x: hidden;*/
            /*overflow-y: hidden;*/
        }
        .load {
            width: 100%;
            text-align: center;
            margin-bottom: 5px;
            display: none;
        }
        .load img{
            height:30px;
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
            border-bottom:1px solid #dedede;
        }
        .wrap{
            /*width: 100px;*/
            margin: 0 auto;
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
        }
        table th{
            background: #999!important;
            color:#fff!important;
        }
        table th:first-child{
            width:15%;
        }
        tbody tr:hover{
            background: #ffffcc !important;
        }
    </style>
</head>
<body>
<#assign data =NPT_ACTION_RETURNED_JSON>
    <div>
        <div class="con-bottom">
            <div class="">
                <table  class="table-responsive table-striped table"  width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed;word-break:break-all;border-collapse:collapse;">
                    <thead>
                    <#list data.columnTitles as title>
                        <#if title_index < 3 >
                        <th style="background-color:#46a3ff;">${title}</th>
                        </#if>
                    </#list>
                    </thead>
                    <tbody>
                    <#if data.dataList??>
                        <#list data.dataList as list>

                            <tr onclick="detail(${list.dataArray[0].value})">
                                <td class="wrap">${list_index+1}</td>
                                <#list list.dataArray as array>
                                    <#if array_index lt 3 && array_index gt 0>

                                        <td class="wrap" >

                                        ${array.value}

                                        </td>

                                    </#if>
                                </#list>
                            </tr>

                        </#list>
                    <#else>
                    <tr>
                        <td colspan="3">暂无数据</td>
                    </tr>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="bot_bot">
            <div class="load">
                <img src="/r/cms/www/red/img/load.gif">
            </div>
            <div >
                <li class="button btn-prev">
                    <input class="btn btn-primary btn-block" type="button" value="上一页" onclick="next(-1)"/>
                </li>
        <#--<span id="count">1</span>-->
                <li class="button btn-next">
                    <input class="btn btn-danger btn-block" type="button" value="下一页" onclick="next(1)"/>
                </li>
            </div>
        </div>
    </div>
</body>
</html>
<script>
    //  var m = 1;

    function publicityNavi(url,param){
        $.ajax({
            url: url,
            data: {ukValue: param},
            timeout: 30000,
            success: function (data) {
                window.open(url+"?ukValue="+param);
            }
        });
    }

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

    //      var m = 1;
    //    function openMore(){
    //        m += 1;
    //        document.cookie = m;
    ////        $(this).attr("addval",m);
    //        var a = m;
    //        search(a);
    //        alert(a);
    //    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 分页查询
     */
//    $(function(w) {
//        if (w.creditQueryCurrPage === undefined) {
//            w.creditQueryCurrPage = 1;
//        }
//    }(window));

    if (window.creditQueryCurrPage === undefined) {
        window.creditQueryCurrPage = 1;
    }
    var total =  Math.ceil(${data.totalCount/data.pageSize});

        if(creditQueryCurrPage === 1){
            $(".btn-prev").remove();
        }
        if(creditQueryCurrPage === total){
            $(".btn-next").remove();
        }

    function next(n) {
        $(".load").css("display","block");
        window.creditQueryCurrPage=creditQueryCurrPage+n;
        console.log(creditQueryCurrPage);

        if(creditQueryCurrPage < 1){
            window.creditQueryCurrPage=1;
        }
        else{
            $.ajax({
                url: "/credit/query/bs/listMobile.do",
                data: {
                    poolId: 121,
                    pageSize: ${data.pageSize},
                    currPage: creditQueryCurrPage,
                <#list data.webQueryCondition as cond>
                    "webQueryCondition[${cond?index}].fieldTitle": "${cond.fieldTitle}",
                    "webQueryCondition[${cond?index}].fieldDBName": "${cond.fieldDBName}",
                    "webQueryCondition[${cond?index}].fieldQueryValue": "${cond.fieldQueryValue!}",
                </#list>
                },
                success: function (html) {
                    $(".load").css("display","none");
                    $("html").html(html);
                    if(creditQueryCurrPage == total){
                        $(".btn-next").remove();
                    }
                }
            })
        }

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

    function detail(uid) {
        $(".load_main").show();
//    $.ajax({
//        type:"POST",
//        url:"/credit/query/bs/detailMobile.do",
//        data:{ukValue:uid},
//        success:function(html){
//            $(".load_main").hide();
//            var obj = window.location.href="/credit/query/bs/detailMobile.do?ukValue=" + uid;
//            obj.document.write(html);
//        }
//    });
        window.location="/credit/query/bs/detailMobile.do?ukValue=" + uid;
//        window.open("/credit/query/bs/detail.do?ukValue=" + uid);
        // window.location.href="/credit/query/bs/detail.do?ukValue=" + uid;
    }
</script>