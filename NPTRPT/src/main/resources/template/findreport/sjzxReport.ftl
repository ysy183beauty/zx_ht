<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${wctx}/pub/statistics/js/dataFastSelecter.js"></script>
    <link rel="stylesheet" href="${wctx}/pub/CreditStyle/jOrgChart/css/jquery.jOrgChart.css"/>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/jOrgChart/jquery.min.js"></script>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/jOrgChart/jquery-ui.min.js"></script>
    <script src="${wctx}/pub/CreditStyle/jOrgChart/jquery.jOrgChart.js"></script>
</head>
<body class="page-header-fixed">

<div class="container-fluid">

    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>数据展现情况统计</div>
                </div>
                <div class="portlet-body ">
                    <div id="main" style="overflow:auto;" class="orgChart">
                    </div>
                </div>
                <div class="form-actions">
                    <button type="button" onclick="backIndex()" class="btn blue"><i class="icon-arrow-left icon-white"></i>返回</button>
                </div>
            </div>
        </div>
    </div>
    <!-- END PAGE HEADER-->
    <ul id="org" style="display:none">
        <li>企业名称：<span id="companyName"><a href="javascript:void(0);" style="color: yellow;"></a></span>
            <ul>
                <li>登记类信息
                    <ul>
                        <li>市场监督管理局
                            <ul>
                                <li>组织机构代码信息</li>
                            </ul>
                        </li>
                        <li>人社局
                            <ul>
                                <li>单位参保信息</li>
                            </ul>
                        </li>
                        <li>人行
                            <ul>
                                <li>企业授权信息</li>
                            </ul>
                        </li>
                    </ul>
                </li>
                <li>监管类信息</li>
                <li>表彰类信息
                    <ul>
                        <li>人社局
                            <ul>
                                <li>安检诚信单位信息</li>
                            </ul>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</div>
<!-- END PAGE CONTAINER-->
<script>
    //返回页面功能
    function backIndex() {
        window.history.back(-1);
    }
    //页面加载完成事件
    $(function () {
        var result='${result}';
        var obj=eval(result);
        $("#companyName a").text(obj[0].FRMC);
        loadData();
    });
    //加载数据信息
    function loadData() {
        $("#org").jOrgChart({
            chartElement : '#main',dragAndDrop:true
        });
    }
</script>
</body>
<!-- END BODY -->
</html>