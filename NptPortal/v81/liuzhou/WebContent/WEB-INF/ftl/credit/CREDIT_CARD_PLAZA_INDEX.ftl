<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>信用名片</title>
    <#include "CommonUtil.ftl">
    <#include "include/header_link.ftl">
    <#include "include/macro.ftl">
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/card.css"/>

    <style>
        .credit_list a{
            font-size:14px!important;
        }
        #hotSearch{
            width:70%;
            height:200px;
            margin:0 auto;
        }
    </style>
</head>
<body>
<div class="wrap">
    <div class="body">
        <#include "include/header.ftl">
        <!--内容开始-->
        <div class="w content">
            <div class="sit height_auto m-t-md">
                <#--<ul>-->
                    <#--<li>-->
                        <#--<a id="sy" href="/">首页</a>-->
                    <#--</li>-->
                    <#--<li>&gt;</li>-->
                    <#--<li>-->
                        <#--<a href="/xymp/index.jhtml">信用名片</a>-->
                    <#--</li>-->
                    <#--<li>&gt;</li>-->
                    <#--<li>-->
                        <#--名片广场-->
                    <#--</li>-->
                <#--</ul>-->
            </div>
            <div class="row">
            <#include "include/NAV_CARD_INDEX.ftl">
                <div class="cardContent">
                    <div class="find">
                        <div class="find-title">
                            <h3>发现</h3>
                        </div>
                        <div>
                            <div id="hotSearch"></div>
                            <div class="card-search">
                                <div class="banner-input">
                                    <span class="box-input">
                                        <input id="queryInput" type="text" placeholder="请输入企业名称或人名">
                                    </span>
                                    <button class="btn" onClick="searchInfo();"></button>
                                    <div id="card-tip" class="index-search-tip">
                                        <p class="search-count " id="indexTip">搜索关键词不得少于２个字符</p>
                                        <span class="corner-main">◆</span>
                                        <span class="corner-bottom">◆</span>
                                    </div>
                                </div>
                            </div>
                            <#--<div class="hotSearch">-->
                                <#--<div class="col-xs-2">热搜词：</div>-->
                                <#--<div class="col-xs-10">-->
                                    <#--<#list hotSearch as c>-->
                                        <#--<div class="col-xs-4 searchKey">${c.searchKey!}</div>-->
                                    <#--</#list>-->
                                <#--</div>-->
                            <#--</div>-->
                        </div>
                        <div id="searchBox"></div>
                    </div>
                    <div class="follow">
                        <div class="find-title">
                            <h3>热门关注</h3>
                        </div>
                        <div class="col-xs-6">
                            <div class="follow-title">企业</div>
                            <ul>
                                <@cardHotList dataList = bsHot/>
                            </ul>
                        </div>
                        <div class="col-xs-6">
                            <div class="follow-title">个人</div>
                            <ul>
                            <@cardHotList dataList = psHot/>
                            </ul>
                        </div>
                    </div>
                    <#--<div class="statistics">-->
                        <#--<div class="find-title">-->
                            <#--<h3>使用量统计</h3>-->
                        <#--</div>-->
                        <#--<div id="map" style="width:100%;height:500px;"></div>-->
                    <#--</div>-->
                </div>
            </div>

        </div>
    </div>

</div>
<#include "include/footer.ftl">
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/echarts.min.js"></script>
<script src="${ctx}/r/cms/www/red/js/credit/creditCard.js"></script>

<script src="${ctx}/r/cms/www/red/js/echarts.min.js"></script>
<script src="${ctx}/r/cms/www/red/js/echarts-wordcloud.min.js"></script>
<script>
    function cardLogin(){
        showEHAOTONGLogin();
    }
    $(".searchKey").click(function(){
        var value=$(this).text();
        $("#queryInput").val(value);
    });
    var keyWord;
    function searchInfo() {
        keyWord=$("#queryInput").val();
        if(keyWord && keyWord.length > 1){
            $.ajax({
                url: "search.do",
                data: {
                    key: keyWord,
                    currentPage: 1,
                    pageSize: 6
                },
                beforeSend: function () {

                },
                success: function (data) {
                    $("#searchBox").html(data);
                },
                error: function () {

                }
            });
        }else{
            $("#card-tip").css("display","block");
        }

    }
    $("#queryInput").focus(function(){
        $(".index-search-tip").css("display","none");
    });
    function setSelect(url) {
        if(url.indexOf('card')>-1){
            $("#card").addClass("active");
        }else if(url.indexOf('mycard')>-1){
            $("#mycard").addClass("active");
        }else if(url.indexOf('grsmlc')>-1){
            $("#grsmlc").addClass("active");
        }else if(url.indexOf('qysmlc')>-1){
            $("#qysmlc").addClass("active");
        }else if(url.indexOf('sysc')>-1){
            $("#sysc").addClass("active");
        }
    }
    $(function(){

        setSelect(window.location.pathname);
        //window.location.hash="#sy";
        $(".searchKey").each(function(){
            $(this).css("color",getRandomColor());
        });
    });
    function getRandomColor() {
        var c = '#';
        var cArray = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'];
        for(var i = 0; i < 6;i++)
        {
            var cIndex = Math.round(Math.random()*15);
            c += cArray[cIndex];
        }
        return c;
    }



    var myChart = echarts.init(document.getElementById('hotSearch'));
    var option = {
//        title: {
//            text: "词云图",
//            link: 'https://github.com/ecomfe/echarts-wordcloud',
//            subtext: 'data-visual.cn',
//            sublink: 'http://data-visual.cn',
//        },
        tooltip: {},
        series: [{
            type: 'wordCloud',
            gridSize: 20,
            sizeRange: [12, 50],
            rotationRange: [0, 0],
            shape: 'circle',
            textStyle: {
                normal: {
                    color: function () {
                        return 'rgb(' + [
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160),
                                    Math.round(Math.random() * 160)
                                ].join(',') + ')';
                    }
                },
                emphasis: {
                    shadowBlur: 10,
                    shadowColor: '#333'
                }
            },
            data: [
            <#list hotSearch as c>
                {
                    name: '${c.searchKey!}',
                    value: ${c.hotValue!}
                },
            </#list>
            ]
        }]
    };
    myChart.setOption(option);
    function openOrFold(param) {
        console.log("这是单击");
//            var option = myChart.getOption();
//            var nodesOption = option.series[0].nodes;
//            var linksOption = option.series[0].links;
        var data = param.data;
        console.log(data);
        $("#queryInput").val(data.name);
//            var linksNodes = [];
//        $(".loadDiv").show();
//        $.ajax({
//            url:"/npt/grs/query/bsmap/nodeDetail.action?poolId="+data.poolId+"&&ukValue="+data.ukValue,
//            timeout:20000,
//            success:function(msg){
//                $("#canvas-info").html(msg).addClass("info-active");
//                $("#canvas").stop().addClass("can-active");
//
//                $(".loadDiv").hide();
//            },
//            error:function(){
//                console.log("请求失败！");
//                $(".loadDiv").hide();
//            }
//        });
//            window.open("/npt/grs/query/bsmap/nodeDetail.action?poolId="+data.poolId+"&&ukValue="+data.ukValue);
    }
    myChart.on("click", openOrFold);
</script>
</body>
</html>