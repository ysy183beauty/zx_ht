<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
    <style type="text/css">
        .container-fluid{
            background-color:#282e33;
        }
        .row-fluid{
            text-align: center;
        }
        .map-row{
            display:inline-block;
            border:1px solid #ccc;
            width:50%;
        }
        #detailDiv{
            display:none;
        }
        #searchKey{
            width:95%;
            padding: 14px 0;
            padding-left:5%;
            border: 0;
            outline: none;
        }
        #searchKey:focus{
            box-shadow: none!important;
        }
        #searchBtn{
            margin-left: -5px;
            width: 100px;
            height: 50px;
            background: #009ce7 url(${wctx}/pub/style/themes/bright/images/banner-search.png) no-repeat center;
        }
        #searchBtn:hover{
            background-color:#2ab4f7!important;
        }
        #searchBtn:active,#searchBtn:focus{
            background-color:#009ce7!important;
        }
        .portlet.box.boxTheme{
            position:relative;
            overflow: hidden;
        }
        #canvas{
            width:100%;
            float:left;
            border:1px solid #ccc;
            transition:width 0.5s;
            -moz-transition:width 0.5s; /* Firefox 4 */
            -webkit-transition:width 0.5s; /* Safari and Chrome */
            -o-transition:width 0.5s; /* Opera */
        }
        .can-active{
            width:70%!important;
        }
        .canvasInfo{
            position:absolute;
            top:50px;
            right:0px;
            width:28%;
            height:750px;
            border:1px solid #ccc;
            background-color:#006699;
            filter:alpha(opacity=50); /*IE滤镜，透明度50%*/
            -moz-opacity:0.5; /*Firefox私有，透明度50%*/
            opacity:0.5;/*其他，透明度50%*/
            border-radius: 5px 0 0 5px;
            overflow: hidden;   
          -webkit-transform: translateX(100%);
            -ms-transform: translateX(100%);
            transform: translateX(100%);
            -webkit-transition: -webkit-transform .5s,box-shadow .5s;
            transition: transform .5s,box-shadow .5s;
        }
         .info-active{
            box-shadow: 0 2px 8px rgba(0,0,0,.2);
            -webkit-transform: translateX(0);
            -ms-transform: translateX(0);
            transform: translateX(0);
        }
        .canvasInfo h3{
            padding-left:20px;
            color:#fff;
            font-family: STFangsong , FangSong;
        }
        .info-title{
            position:relative;
        }
        .info-data{
            background-color:#fff;
            height:610px;
            overflow-y: scroll;
        }
        table{
            margin:0 auto;
        }
        tr{;
            height:50px;
            border-bottom:2px solid #ccc;
        }
        th{
            width:35%;
            text-align: right;
        }
        td{
            line-height: 30px;
            word-wrap:break-word;
            white-space: normal;
        }
        .info-icon{
            position:absolute;
            top:10px;
            right:30px;
        }
        .info-icon>i{
            color: #fff;
            font-size: 30px;
        }
        .info-icon>i:hover{
            color:#0c91e5;
        }
    </style>
</head>
<body class="page-header-fixed">

<!-------------------------------------企业信用图谱-------------------------------------------->
<div id="indexDiv">
<div class="portlet box boxTheme">
    <div class="portlet-title">
        <div class="caption"><i class="icon-search"></i>企业信用信息查询</div>
        <div class="tools">
        </div>
    </div>

    <div class="portlet-body form" style="margin-top:20px;">
        <!-- BEGIN FORM-->
        <form action="#" class="form-horizontal" id="searchList">
            <div class="row-fluid">
                <div class="map-row">
                    <input id="searchKey" type="text" class="small" placeholder="${_PLACE_HOLDER!}">
                </div>
                <button id="searchBtn" type="button" class="btn" onclick="search()">
                </button>
            </div>
            <#--<div class="form-actions">-->
                <#---->
                <#--&lt;#&ndash;<button type="reset" class="btn"><i class="icon-repeat"></i>&nbsp;&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置&ndash;&gt;-->
                <#--&lt;#&ndash;</button>&ndash;&gt;-->
            <#--</div>-->
        </form>
        <!-- END FORM-->
    </div>

    <div class="portlet-body">
        <table class="table table-striped table-bordered table-hover" id="pageData">

            <thead>
                <#if NPT_ACTION_RETURNED_JSON??>
                    <#assign data = NPT_ACTION_RETURNED_JSON>
                    <#if data.totalCount gt 0>
                    <tr>
                        <th style="width:30px">序号</th>
                        <#list data.columnTitles as title>
                        <#if title_index!=0>
                            <th width="${80/(data.columnTitles?size-1)}%">${title!}</th>
                        </#if>
                        </#list>
                        <th style="width: 30px">操作</th>
                    </tr>
                    </#if>
                </#if>
            </thead>

            <tbody>
                <#include "../customModel/grs_main_field_list.ftl">
            </tbody>
        </table>
    <div id="pagination"></div>
</div>

</div>
</div>
<div id="detailDiv" >
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>企业图谱</div>
            <div class="tools">
            </div>
        </div>
        <div id="canvas">

        </div>
        <div id="canvas-info" class="canvasInfo">

        </div>
    </div>
</div>

<div id="modalDiv" class="modal hide fade " tabindex="-1" data-width="900">

</div>

<script>
    function showSecurityFunction() {
        <#list Session["_curentMenuFunctions"] as funcName>
            $(".${funcName}").show();
        </#list>
    }

    function search() {
        var txt=$("#searchKey").val();
        if(txt !=""){
            $.ajax({
                type: "post",
                url: "search.action",
                data: {searchKeyValue: $("#searchKey").val()},
                success: function (html) {
                    $('#pageData tbody').html(html);
                    pagination.initPagination(ctx+"/npt/grs/query/bsmap/search.action", {searchKeyValue: $("#searchKey").val()}, showSecurityFunction);
                }
            });
        }
        else{
            $(".map-row").css("box-shadow","inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(255,0,0,0.6)");
        }
    }
    $("#searchKey").focus(function () {
        $(".map-row").css("box-shadow","none");
    });

    function showDetail(ukValue) {
        $.ajax({
            url: ctx+"/npt/grs/query/bsmap/mapDetail.action",
            data: {mapUKValue: ukValue},
            timeout: 30000,
            success: function (data) {
                $("#detailDiv").show();
                $("#canvas").html(data);
            }
        });
    }
</script>
</body>
<!-- END BODY -->

</html>