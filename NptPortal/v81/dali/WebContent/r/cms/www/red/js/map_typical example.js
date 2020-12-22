/**
 * Created by Administrator on 2017/3/2.
 */
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
            min: 0,
            max: 100,
            color:['#005a32','#fff'],
            text:['高','低'],           // 文本，默认为数值文本
            calculable : true,
            show:false
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
        data:['诚信事例','失信事例']
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
            name: '单位（条）',
            min: 0,
            max: 25,
            interval: 5
//                axisLabel: {
//                    formatter: '{value} ml'
//                }
        }
//        {
//            type: 'value',
//            name: '行政处罚（条）',
//            min: 0,
//            max: 300000,
//            interval: 50000
////                axisLabel: {
////                    formatter: '{value} °C'
////                }
//        }
    ],
    series: [
        {
            name:'诚信事例',
            type:'bar',
            data:[25, 21, 12, 17,6,7, 10, 6, 6,17, 6, 20],
            itemStyle: {
                normal: {
                    color: '#fe8634'
                }
            }
        },
        {
            name:'失信事例',
            type:'bar',

            data:[20, 23,15,16,8,10,9,5 ,3 ,4,14,9],
            itemStyle: {
                normal: {
                    color: '#7fcff5'
                }
            }
        }

    ]
};

    chart2.setOption(option);

//window.onresize = function(){
//    alert("1111");
//    chart2.setOption(option);
//    cha2();
//};