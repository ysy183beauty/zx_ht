<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>重点人群信用查询</title>

    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
    <script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
    <script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>

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
            margin-bottom:2em;
        }

        .gover{
            /*width:90%;*/
            margin:0 auto;
            border-bottom:1px solid #ccc;
            line-height: 4em;
            height:4em;
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
    </style>
</head>
<body>
<div class="container-fluid">
    <div class="con_top">
        <div class="alert alert-warning">
            <p>查询说明：</p>
            <p>1、输入“姓名”可查询企业信用记录。</p>
            <p>2、目前查询的重点人群信用记录，是有关政府部门按照有关规定向社会公开的信用信息。</p>
            <p>3、对查看到的信用信息有异议，可在信用江苏网站提处异议处理。</p>

        </div>
        <div class="top_con">
            <ul>
                <li class="row">
                    <div class=" row gover">
                        <span class="col-xs-5">姓名：</span>
                        <input class="col-xs-7" id="name" type="text" placeholder="请输入姓名" />
                    </div>
                </li>
                <li class="button">
                    <input class="btn btn-danger btn-block" type="button" value="查询" onclick="search()"/>
                </li>
                <li class="button">
                    <input class="btn btn-primary btn-block" type="button" value="重置" onclick="resetSearch()"/>
                </li>
            </ul>
        </div>
    </div>
</div>

</body>
</html>
<script>
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
    function search() {
        var keyWord=$("#name").val();
//        $.ajax({
//            url: encodeURI( "/credit/query/kc/kcSearch.do?keyword=" + keyWord),
//            success: function (html) {
//                $("body").html(html);
//            }
//        });
        window.location=encodeURI( "/credit/query/kc/listMobile.do?keyword=" + keyWord);
    }
    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 重置搜索框
     */
    function resetSearch() {
        $("#name").val("");
    }
</script>