<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">

    <link rel="stylesheet" href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/inputStyle.css">
    <link rel="stylesheet" href="${wctx}/pub/CreditStyle/d3/v3/d3.css">
    <script src="${wctx}/pub/CreditStyle/d3/v3/d3.bubble.js"></script>
    <script src="${wctx}/pub/CreditStyle/d3/v3/d3-transform.js"></script>
    <script src="${wctx}/pub/CreditStyle/d3/v3/extarray.js"></script>
    <script src="${wctx}/pub/CreditStyle/d3/v3/misc.js"></script>
    <script src="${wctx}/pub/CreditStyle/d3/v3/micro-observer.js"></script>
    <script src="${wctx}/pub/CreditStyle/d3/v3/microplugin.js"></script>
    <script src="${wctx}/pub/CreditStyle/d3/v3/bubble-chart.js"></script>
    <script src="${wctx}/pub/CreditStyle/d3/v3/central-click.js"></script>
    <script src="${wctx}/pub/CreditStyle/d3/v3/lines.js"></script>
    <#--<link rel='stylesheet' href='http://fonts.googleapis.com/css?family=Play:700,400' type='text/css'>-->
    <#--<script type="text/javascript" src="http://iop.io/js/vendor/polymer/PointerEvents/pointerevents.js"></script>-->
    <#--<script type="text/javascript" src="http://iop.io/js/vendor/polymer/PointerGestures/pointergestures.js"></script>-->
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/d3/v3/iopctrl.js"></script>
</head>
<body class="page-header-fixed">

<!-------------------------------------企业信用图谱-------------------------------------------->
<div id="indexDiv">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <#--<div class="caption"><i class="icon-search"></i>企业信用信息查询</div>-->
            <div class="tools btn mini green">
                <a href="javascript:;" class="collapse"></a>
            </div>
        </div>

        <div class="portlet-body form" style="margin-top:20px;">
            <div class="searchTitle">
                <img src="${wctx}/pub/style/themes/bright/images/qytp.png" alt="">
            </div>
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
                <#include "../nativeModel/grs_business_map_field_list.ftl">
            </table>
            <div id="pagination"></div>
        </div>
    </div>

</div>

<div id="detailDiv" >
    <div class="portlet box boxTheme">
    <#--<div class="portlet-title">-->
            <#--<div class="caption"><i class="icon-search"></i>企业图谱</div>-->
            <#--<div class="tools">-->
            <#--</div>-->
        <#--</div>-->
        <div id="canvas">

        </div>

    </div>
</div>

<div id="modalDiv" class="modal hide fade " tabindex="-1" data-width="900">

</div>
<#--<script type="text/javascript" src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/inputStyle.js"></script>-->
<script>
    function showSecurityFunction() {
        <#list Session["_curentMenuFunctions"] as funcName>
            $(".${funcName}").show();
        </#list>
    }

    function search() {
        $("#pageData").show();
        $("#detailDiv").hide();
        var txt=$("#searchKey").val();
        if(txt !=""){
            $.ajax({
                type: "post",
                url: "search.action",
                data: {searchKeyValue: $("#searchKey").val()},
                success: function (html) {
                    $('#pageData').html(html);
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

    function showMap(ukValue, version) {
        $(".collapse").trigger('click');
        version = version === undefined ? "" : version.toLocaleUpperCase();
        $.ajax({
            url: ctx+"/npt/grs/query/bsmap/mapDetail"+version+".action",
            data: {mapUKValue: ukValue},
            timeout: 30000,
            beforeSend: function () {
                $(".loadDiv").show();
            },
            success: function (data) {
                $(".loadDiv").hide();
                $("#detailDiv").show();
                $("#canvas").html(data);
            },
            error: function () {
                $(".loadDiv").hide();
                returnInfo(false, "操作失败！");
            }
        });
    }
    function showTop(){
        $("#indexDiv").show(2000);
    }
    function hideTop(){
        $("#indexDiv").hide(2000);
    }
</script>
</body>
<!-- END BODY -->

</html>