<!--
    信用公示首页
-->
<#include "CommonUtil.ftl"/>
<#assign ms=_MS>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="save" content="history">
    <meta http-equiv="X-UA-Compatible" content="IE=10" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <title>双公示</title>
    <#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/publicity.css" />
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/nav.css"/>

</head>
<body>

<div class="wrap">
<#include "include/header.ftl"/>
<div class="main">
    <div class="load_main">
        <img src="/r/cms/www/red/img/load.gif">
    </div>
    <div id="container" class="">

            <div class="sit height_auto m-t-md">
                <ul>
                    <li>
                        <a id="sy" href="/">首页</a>
                    </li>
                    <li>&gt;</li>
                    <li>
                        <a href="/credit/pub/index.do">信用公示</a>
                    </li>
                    <li class="last-tit"></li>
                </ul>
            </div>
        <div class="row">
            <#--<div id="nav_search col-md-2">-->
            <#include "include/NAV_PUB_INDEX.ftl"/>
             <div class="content col-md-9" >

                 <div id="mainContent" >
                     <div class="con_top row">
                         <div class="top_left col-sm-3">
                             <div id="map">
                             </div>
                             <div class="time">
                                 2015-07-01 至 2017-02-16
                             </div>
                         </div>
                         <div class="top_right col-sm-9">
                             <div class="right_top ">
                                 <div class="col-sm-2" style="padding-left:0px;padding-right:0px;">
                                     <h3 class="">双公示</h3>
                                 </div>

                                 <ul class="col-sm-10"  style="padding-left:0px;padding-right:0px;">
                                     <li>
                                         <span>行政许可：<b>${ms.groupStatistics[0].dataCount}</b>项</span>
                                         <span>行政处罚：<b>${ms.groupStatistics[1].dataCount}</b>项</span>
                                         <span>合计双公示：<b>${ms.dataCount}</b>项</span>
                                     </li>
                                     <li>
                                         <span>2015-07-01 至 2017-02-16</span>
                                         <a href="">当天 </a>
                                         <a href="">七天</a>
                                         <a href="">一个月 </a>
                                         <a href="">三个月 </a>
                                         <a href="">更多</a>
                                     </li>
                                 </ul>
                             </div>
                             <div id="map2" >

                             </div>
                         </div>
                     </div>
                     <div class="con_bottom">
                         <div class="bot_title">
                             <h2>双公示</h2>
                         </div>
                         <div class="bot_con row">
                             <div class="bot_left col-md-6">
                                 <div class="bot_con_box_left">
                                     <div class="left_title">
                                         <h3>行政许可公示</h3>
                                         <span>2017-01-19</span>
                                     </div>
                                     <ul>
                                         <li class="list_first">
                                             <div class="img_first">
                                                 <img src="${ctx}/r/cms/www/red/img/sgs/xuke.png" alt=""/>
                                             </div>
                                             <span>
                                                    行政许可，是指在法律一般禁止的情况下，
                                                    行政主体根据行政相对方的申请，经依法审查，
                                                    通过颁发许可证、执照等形式，赋予或确认
                                                    行政相对方从市某种活动的法律资格或法律权利
                                                    的一种具体行政行为。
                                            </span>
                                         </li>
                                         <li>
                                             <p>公示单位：（共${ms.groupStatistics[0].poolStatistics?size}个单位） 州发展改革委、州工信委、州教育局、州公安局、州司法局、州财政局 ...</p>
                                             <#--<a href="/credit//pub/2pub/permission.do">更多>></a>-->
                                         </li>
                                         <li>
                                             <p>被公示名单：（共292048个信用对象） 志必达电脑技术有限公司、天天点
                                                 信息科技有限公司、光驱管理有限公司...</p>
                                             <#--<a >更多>></a>-->
                                         </li>
                                     </ul>
                                 </div>

                             </div>
                             <div class="bot_right col-md-6">
                                 <div class="bot_con_box_right">
                                     <div class="right_title">
                                         <h3>行政处罚公示</h3>
                                         <span>2017-01-19</span>
                                     </div>
                                     <ul>
                                         <li class="list_first">
                                             <div class="img_first">
                                                 <img src="${ctx}/r/cms/www/red/img/sgs/chufa.png" alt=""/>
                                             </div>
                                             <span>行政处罚，是指行政机关或其他行政主体依法定职权和程序对违反行政法规尚未构成犯罪的相对人给予行政制裁的具体行政行为。</span>
                                         </li>
                                         <li>
                                             <p>公示单位：（共${ms.groupStatistics[1].poolStatistics?size}个单位） 州发展改革委、州工信委、州教育局、州公安局、州司法局、州财政局 ...</p>
                                             <#--<a href="/credit//pub/2pub/permission.do">更多>></a>-->
                                         </li>
                                         <li>
                                             <p>被公示名单：（共292048个信用对象） 志必达电脑技术有限公司、天天点
                                                 信息科技有限公司、光驱管理有限公司...</p>
                                             <#--<a href="">更多>></a>-->
                                         </li>
                                     </ul>
                                 </div>

                             </div>

                         </div>
                     </div>
                 </div>
             </div>

        </div>


    </div>
</div>
</div>
<#include "include/footer.ftl"/>
</body>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/echarts.min.js"></script>
<#--<script type="text/javascript" src="${ctx}/r/cms/www/red/js/map_publicity.js"></script>-->
<script type="application/javascript">
//    判断是由首页红黑榜进入
    $(function () {
        //    获取页面参数
        var URLParams = new Array();
        var aParams = document.location.search.substr(1).split('&');
        for (i=0; i < aParams.length ; i++){
            var aParam = aParams[i].split('=');
            URLParams[aParam[0]] = aParam[1];
        }
        var word=URLParams["word"];

        if(word && word == 1){
            $("#nav>li").eq(1).click();
            $("#hhb li").eq(0).click();
        }
        else if(word && word == 2){
            $("#nav>li").eq(1).click();
            $("#hhb li").eq(1).click();
        }
        else if(word && word == 3){
            $("#nav>li").eq(2).click();
        }
    })
//    地图
$.getJSON('${ctx}/r/cms/www/red/js/dali.json', function (e) {
    echarts.registerMap('dali', e);
    var chart = echarts.init(document.getElementById('map'));
    var option1=({
        tooltip:{
            show:true,
            trigger: 'item',
            formatter: '{a} : {b}-{c}'
        },
        dataRange: {
            itemWidth:10,
            itemHeight:50,
            x:10,
            y:250,
            min: 0,
            max: 100000,
            color:['#f45009','#fff'],
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true,
            show:true
        },
        title:{
            text:"行政许可和行政处罚公示专栏",
            x:"center",
            y:"20px",
            textStyle:{
                fontSize: 14,
                fontWeight: 'bolder',
                color: '#333'
            }
        },
        series: [{
            name:"区域",
            type: 'map',
            map: 'dali',
            formatter:"{a}:{b}",
            itemStyle:{
                normal:{label:{show:true}},
                emphasis:{label:{show:true}}
            },
            data:[
                {name:"大理",value: ${ms.dataCount}},
                {name:"宾川",value:"0"},
                {name:"漾濞",value:"0"},
                {name:"剑川",value:"0"},
                {name:"洱源",value:"0"},
                {name:"鹤庆",value:"0"},
                {name:"祥云",value:"0"},
                {name:"弥渡",value:"0"},
                {name:"南涧",value:"0"},
                {name:"巍山",value:"0"},
                {name:"云龙",value:"0"},
                {name:"永平",value:"0"}
            ]
        }

        ]
    });
    chart.setOption(option1)
});
//map2
var chart2 = echarts.init(document.getElementById('map2'));
var option = {
    backgroundColor: '#fff',
    tooltip: {
        trigger: 'axis'
    },
//        toolbox: {
//            feature: {
//                dataView: {show: true, readOnly: false},
//                magicType: {show: true, type: [ 'bar']},
//                restore: {show: true},
//                saveAsImage: {show: true}
//            }
//        },
    legend: {
        show:true,
        orient: 'horizontal', // 'vertical'
        //
        data:['行政许可','行政处罚']
    },
    xAxis: [
        {
            type: 'category',
            data: ['剑川','鹤庆','云龙','洱源','永平','漾濞','大理','宾川','巍山','弥渡','祥云','南涧']
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '行政许可（条）',
            min: 0,
            max: ${ms.groupStatistics[0].dataCount}+100000,
            interval: Math.floor((${ms.groupStatistics[0].dataCount}+100000)/6)
//                axisLabel: {
//                    formatter: '{value} ml'
//                }
        },
        {
            type: 'value',
            name: '行政处罚（条）',
            min: 0,
            max:  ${ms.groupStatistics[1].dataCount}+1000,
            interval: Math.floor((${ms.groupStatistics[1].dataCount}+1000)/6)
//                axisLabel: {
//                    formatter: '{value} °C'
//                }
        }
    ],
    series: [
        {
            name:'行政许可',
            type:'bar',
            data:[0, 0, 0, 0,0,0, ${ms.groupStatistics[0].dataCount}, 0, 0,0, 0, 0],
            itemStyle: {
                normal: {
                    color: '#fe8634'
                }
            }
        },
        {
            name:'行政处罚',
            type:'bar',
            yAxisIndex: 1,
            data:[0, 0,0,0,0,0, ${ms.groupStatistics[1].dataCount},0 ,0 ,0,0,0],
            itemStyle: {
                normal: {
                    color: '#7fcff5'
                }
            }
        }

    ]
};

chart2.setOption(option);
</script>
</html>