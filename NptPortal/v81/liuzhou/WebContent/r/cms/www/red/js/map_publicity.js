/**
 * Created by Administrator on 2017/3/2.
 */
//MAP


// var chart = echarts.init(document.getElementById('map'));
// chart.setOption({
// //        backgroundColor:rbga(242,242,242,1),
//     series: [{
//         type: 'map',
//         map: 'dali'
//     }]
// });
$.getJSON('/r/cms/www/red/js/liuzhou.json', function (e) {
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
            max: 100,
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
                {name:"城中区",value:"293"},
                {name:"鱼峰区",value:"23"},
                {name:"柳城县",value:"24"},
                {name:"柳南区",value:"27"},
                {name:"柳江区",value:"42"},
                {name:"柳北区",value:"86"},
                {name:"鹿寨县",value:"64"},
                {name:"融安县",value:"43"},
                {name:"三江侗族自治县",value:"35"},
                {name:"融水苗族自治县",value:"76"}
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
            data: ['城中区','鱼峰区','柳城县','柳南区','柳江区','柳北区','鹿寨县','融安县','三江侗族自治县','融水苗族自治县']
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: '行政许可（条）',
            min: 0,
            max: 2000,
            interval: 500
//                axisLabel: {
//                    formatter: '{value} ml'
//                }
        },
        {
            type: 'value',
            name: '行政处罚（条）',
            min: 0,
            max: 300000,
            interval: 50000
//                axisLabel: {
//                    formatter: '{value} °C'
//                }
        }
    ],
    series: [
        {
            name:'行政许可',
            type:'bar',
            data:[460, 49, 1200, 1700,600,700, 100, 650, 60,1700],
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
            data:[260000, 230000,220000,80000,220000,160000,220000,200000 ,120000 ,220000],
            itemStyle: {
                normal: {
                    color: '#7fcff5'
                }
            }
        }

    ]
};

    chart2.setOption(option);
