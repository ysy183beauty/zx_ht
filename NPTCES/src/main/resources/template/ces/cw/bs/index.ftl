<!DOCTYPE html>
<html>
<head>
<#include "/template/Credit_Common/common.ftl">
<script src="${wctx}/pub/CreditStyle/echarts/echarts3.min.js"></script>
<link rel="stylesheet" href="${wctx}/pub/ces/cw/credit-rating/css/rating.css">
</head>
<body>
    <div class="main-content">
        <div class="top-search">
            <img class="search-img" src="${wctx}/pub/ces/cw/credit-rating/images/title-img.jpg" alt=""/>

            <input id="keyWord" class="search-input" type="text" />
            <button class=" btn-primary search-btn" onclick="searchList()">查询</button>

        </div>
        <#--<div class="radar">-->
            <#--<div class="radar-left">-->
                <#--<h5 class="radar-title">企业信用预维度趋势</h5>-->
                <#--<div id="radar-canvas-bus" class="radar-canvas"></div>-->
            <#--</div>-->
            <#--<div class="radar-right">-->
                <#--<h5 class="radar-title">个人信用预维度趋势</h5>-->
                <#--<div id="radar-canvas-people" class="radar-canvas"></div>-->
            <#--</div>-->
        <#--</div>-->
        <div class="topList">
            <div class="topList-left">
                <div>
                    <h5 class="topList-title">信用风险TOP10</h5>
                    <a class="title-more" href="topIndex.action">更多</a>
                </div>
                <#---->
                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th width="30%">${creditEntityIT!}</th>
                        <th width="30%">${creditEntityTT!}</th>
                        <th>预警评分</th>
                        <th>预警等级</th>
                        <th width="20%">评估时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if bsTopResults?size gt 0>
                        <#list bsTopResults as bu>
                            <tr class="td-val" onclick="bsDetail('${bu.creditEntityId!}')">
                                <td>${bu.creditEntityId!}</td>
                                <td>${bu.creditEntityTitle!}</td>
                                <td >${bu.riskScore!}</td>
                                <td class="level">${bu.riskLevel!}</td>
                                <td>${bu.createTime?string("yyyy-MM-dd")!}</td>
                            </tr>
                        </#list>
                    <#else>
                    <tr class="td-val">
                        <td colspan="5">暂无数据</td>
                    </tr>
                    </#if>
                    </tbody>
                </table>
            </div>
            <#--<div class="topList-left">-->
                <#--<div>-->
                    <#--<h5 class="topList-title">个人信用风险TOP10</h5>-->
                    <#--<a class="title-more" href="topList.action?ceTypeCode=2&currPage=1&pageSize=10">更多</a>-->
                <#--</div>-->

                <#--<table class="table table-bordered">-->
                    <#--<thead>-->
                    <#--<tr>-->
                        <#--<th width="70%">企业名称</th>-->
                        <#--<th width="30%">风险等级</th>-->
                    <#--</tr>-->
                    <#--</thead>-->
                    <#--<tbody>-->
                    <#--<#if puCwDmsResults?size gt 0>-->
                        <#--<#list puCwDmsResults as pu>-->
                        <#--<tr class="td-val">-->
                            <#--<td>${pu.creditEntityTitle}</td>-->
                            <#--<td >${pu.riskLevel}</td>-->
                        <#--</tr>-->

                        <#--</#list>-->
                    <#--<#else>-->
                    <#--<tr class="td-val">-->
                        <#--<td colspan="2">暂无数据</td>-->
                    <#--</tr>-->
                    <#--</#if>-->
                    <#--</tbody>-->
                <#--</table>-->
            <#--</div>-->
            <div class="topList-right">
                <div>
                    <h5 class="topList-title">本局星标关注TOP10</h5>
                    <a class="title-more" href="starIndex.action">更多</a>
                </div>

                <table class="table table-bordered table-hover">
                    <thead>
                    <tr>
                        <th width="70%">${creditEntityTT!}</th>
                        <th width="30%">风险等级</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if concernResults?size gt 0>
                        <#list concernResults as cu>
                        <tr class="td-val" onclick="bsDetail('${cu.creditEntityId!}')">
                            <td>${cu.creditEntityTitle}</td>
                            <td class="level">${cu.riskLevel}</td>
                        </tr>

                        </#list>
                    <#else>
                    <tr class="td-val">
                        <td colspan="2">暂无数据</td>
                    </tr>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="bottomList">
            <div id="showBox" class="showBox">
                <ul id="showDiv" class="showDiv">
                    <#list bsDmsTopReuslt?keys as key>
                        <li class="bottomList-left">
                            <div>
                                <h5 class="bottomList-title">${key}TOP10</h5>

                            </div>

                            <table class="table table-bordered bottom-table table-hover">
                                <thead>

                                </thead>
                                <tbody>
                                    <#if bsDmsTopReuslt[key]?size gt 0>
                                        <#list bsDmsTopReuslt[key] as bs>
                                        <tr class="td-val" onclick="bsDetail('${bs.creditEntityId!}')">
                                            <td>${bs.creditEntityTitle}</td>
                                            <td class="level">${bs.riskLevel}</td>
                                        </tr>

                                        </#list>
                                    <#else>
                                    <tr class="td-val">
                                        <td colspan="2">暂无数据</td>
                                    </tr>
                                    </#if>

                                </tbody>
                            </table>
                        </li>
                    </#list>
                </ul>
            </div>
            <div class="prev"> < </div>
            <div class="next"> > </div>
        </div>
        <div class="riskTrend">
            <div class="info-title">
                <h5 class="riskTrend-title">信用风险指数趋势</h5>
            </div>
            <div id="riskTrend-canvas" class="riskTrend-canvas"></div>
        </div>
    </div>
    <#--打开DETAIL-->
    <script>
        function bsDetail(bsId){
            window.location="detail.action?creditEntityId="+bsId;
        }
        function searchList(){
            var key=$("#keyWord").val();
            window.location="searchIndex.action?keyword="+key;
        }
    </script>
    <!--区分颜色-->
    <script type="text/javascript">
        $(function(){
           bgColor();
        });
        function bgColor(){
            $(".td-val").each(function(){
                var val=$(this).find(".level").text();
                console.log(val);
                if(val == 'A'){
                    $(this).css("background","#ff0000");
                    console.log(val);
                }
                if(val == "B"){
                    $(this).css("background","#ff5e18");
                }
                if(val == "C"){
                    $(this).css("background","#fb9411");
                }
                if(val == "D"){
                    $(this).css("background","#f5c614");
                }
                if(val == "E"){
                    $(this).css("background","#a5ff47");
                }
                if(val == "F"){
                    $(this).css("background","#5bff88");
                }
                if(val == "G"){
                    $(this).css("background","#60c4fd");
                }
            });
        }
    </script>
    <!--雷达图-->
    <#--<script type="text/javascript">-->
        <#--var myChart_radarLeft = echarts.init(document.getElementById('radar-canvas-bus'));-->
        <#--var option = {-->
            <#--title: {-->
                <#--text: '企业信用预警变迁',-->
                <#--subtext: '纯属虚构',-->
                <#--x:'right',-->
                <#--y:'bottom'-->
            <#--},-->
            <#--tooltip: {-->
                <#--trigger: 'item',-->
                <#--backgroundColor : 'rgba(0,0,250,0.2)'-->
            <#--},-->
            <#--legend: {-->
                <#--data: (function (){-->
                    <#--var list = [];-->
                    <#--for (var i = 1; i <=7; i++) {-->
                        <#--list.push(i + 2010 + '');-->
                    <#--}-->
                    <#--return list;-->
                <#--})()-->
            <#--},-->
            <#--visualMap: {-->
                <#--color: ['green', 'red']-->
            <#--},-->
            <#--radar: {-->
                <#--indicator : [-->
                    <#--{ text: '欠税', max: 400},-->
                    <#--{ text: '品控', max: 400},-->
                    <#--{ text: '信贷', max: 400},-->
                    <#--{ text: '投诉', max: 400},-->
                    <#--{ text: '环保', max: 400}-->
                <#--]-->
            <#--},-->
            <#--series : (function (){-->
                <#--var series = [];-->
                <#--for (var i = 1; i <= 28; i++) {-->
                    <#--series.push({-->
                        <#--name:'浏览器（数据纯属虚构）',-->
                        <#--type: 'radar',-->
                        <#--symbol: 'none',-->
                        <#--itemStyle: {-->
                            <#--normal: {-->
                                <#--lineStyle: {-->
                                    <#--width:1-->
                                <#--}-->
                            <#--},-->
                            <#--emphasis : {-->
                                <#--areaStyle: {color:'rgba(0,250,0,0.3)'}-->
                            <#--}-->
                        <#--},-->
                        <#--data:[-->
                            <#--{-->
                                <#--value:[-->
                                    <#--(40 - i) * 10,-->
                                    <#--(38 - i) * 4 + 60,-->
                                    <#--i * 5 + 10,-->
                                    <#--i * 9,-->
                                    <#--i * i /2-->
                                <#--],-->
                                <#--name: i + 2000 + ''-->
                            <#--}-->
                        <#--]-->
                    <#--});-->
                <#--}-->
                <#--return series;-->
            <#--})()-->
        <#--};-->
        <#--myChart_radarLeft.setOption(option);-->
    <#--</script>-->
    <#--<script type="text/javascript">-->
        <#--console.log("3");-->
        <#--var myChart_radarRight = echarts.init(document.getElementById('radar-canvas-people'));-->
        <#--var option = {-->
            <#--title: {-->
                <#--text: '个人信用预警变迁',-->
                <#--subtext: '纯属虚构',-->
                <#--x:'right',-->
                <#--y:'bottom'-->
            <#--},-->
            <#--tooltip: {-->
                <#--trigger: 'item',-->
                <#--backgroundColor : 'rgba(0,0,250,0.2)'-->
            <#--},-->
            <#--legend: {-->
                <#--data: (function (){-->
                    <#--var list = [];-->
                    <#--for (var i = 1; i <=7; i++) {-->
                        <#--list.push(i + 2010 + '');-->
                    <#--}-->
                    <#--return list;-->
                <#--})()-->
            <#--},-->
            <#--visualMap: {-->
                <#--color: ['green', 'red']-->
            <#--},-->
            <#--radar: {-->
                <#--indicator : [-->
                    <#--{ text: '欠税', max: 400},-->
<#--//                { text: '品控', max: 400},-->
                    <#--{ text: '信贷', max: 400},-->
                    <#--{ text: '违法', max: 400},-->
                    <#--{ text: '失信', max: 400}-->
                <#--]-->
            <#--},-->
            <#--series : (function (){-->
                <#--var series = [];-->
                <#--for (var i = 1; i <= 28; i++) {-->
                    <#--series.push({-->
                        <#--name:'浏览器（数据纯属虚构）',-->
                        <#--type: 'radar',-->
                        <#--symbol: 'none',-->
                        <#--itemStyle: {-->
                            <#--normal: {-->
                                <#--lineStyle: {-->
                                    <#--width:1-->
                                <#--}-->
                            <#--},-->
                            <#--emphasis : {-->
                                <#--areaStyle: {color:'rgba(0,250,0,0.3)'}-->
                            <#--}-->
                        <#--},-->
                        <#--data:[-->
                            <#--{-->
                                <#--value:[-->
                                    <#--(40 - i) * 10,-->
                                    <#--(38 - i) * 4 + 60,-->
                                    <#--i * 5 + 10,-->
                                    <#--i * 9,-->
                                    <#--i * i /2-->
                                <#--],-->
                                <#--name: i + 2000 + ''-->
                            <#--}-->
                        <#--]-->
                    <#--});-->
                <#--}-->
                <#--return series;-->
            <#--})()-->
        <#--};-->
        <#--myChart_radarRight.setOption(option);-->

    <#--</script>-->
<script src="${wctx}/pub/ces/cw/credit-charts.js"></script>
    <#--滚动效果-->
    <script type="text/javascript">
        var boxwin=0;//BOX横移距离
        var leftWin;//列表宽度
        var divwin;//BOX宽度
        var D_value;//BOX横移的最大距离
        var num=0;//BOX横移的次数
        $(function(){
           listWidth();
           $(".bottomList").css("height",$(".showDiv").height());
           var num=$("#showDiv").find(".bottomList-left").length;
           console.log(num);
           if( num < 6 ){
               $(".prev").hide();
               $(".next").hide();
           }
        });
        $(window).resize(function(){
                    listWidth();
//            myChart_radarLeft.resize();
//            myChart_radarRight.resize();
            myChart_line.resize();
        });
        function listWidth(){
            var listWin=$(".bottomList").width()*0.18;
            var listpadLeft=$(".bottomList").width()*0.01;
            var listpadRight=$(".bottomList").width()*0.01;
            $(".bottomList-left").css("width",listWin+"px");
            $(".bottomList-left").css("padding-left",listpadLeft+"px");
            $(".bottomList-left").css("padding-right",listpadRight+"px");
            leftWin=$(".bottomList").width()*0.2;
            divwin=$("#showDiv>li").length*leftWin;
            D_value=divwin-$(".bottomList").width();
            $("#showBox").css("width",divwin+"px");
            $("#showBox").css("left", -num*leftWin + "px");
            boxwin=-num*leftWin;
        }
        $(".next").click(function(){
            boxwin=boxwin-leftWin;
            num++;
            if (Math.abs(boxwin) > D_value || Math.abs(boxwin) == D_value) {
                boxwin = 0;
                num = 0;
            }

            $("#showBox").css("left", boxwin + "px");
        });
        $(".prev").click(function(){
            boxwin=boxwin+leftWin;
            num--;
            if (boxwin >0 || boxwin == 0) {
                boxwin = 0;
                num = 0;
            }
            $("#showBox").css("left", boxwin + "px");
        });
    </script>
</body>

</html>
