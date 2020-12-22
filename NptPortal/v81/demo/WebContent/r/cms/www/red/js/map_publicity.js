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
$.getJSON('/r/cms/www/red/js/dali.json', function (e) {
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
                {name:"大理",value:"293"},
                {name:"宾川",value:"23"},
                {name:"漾濞",value:"24"},
                {name:"剑川",value:"27"},
                {name:"洱源",value:"42"},
                {name:"鹤庆",value:"86"},
                {name:"祥云",value:"64"},
                {name:"弥渡",value:"43"},
                {name:"南涧",value:"35"},
                {name:"巍山",value:"76"},
                {name:"云龙",value:"86"},
                {name:"永平",value:"36"}
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
            data:[460, 49, 1200, 1700,600,700, 100, 650, 60,1700, 60, 200],
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
            data:[260000, 230000,220000,80000,220000,160000,220000,200000 ,120000 ,220000,220000,280000],
            itemStyle: {
                normal: {
                    color: '#7fcff5'
                }
            }
        }

    ]
};

    chart2.setOption(option);
