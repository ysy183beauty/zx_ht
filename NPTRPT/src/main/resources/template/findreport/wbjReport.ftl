<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${wctx}/pub/statistics/js/dataFastSelecter.js"></script>
</head>
<body class="page-header-fixed">

<div class="container-fluid">

    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>委办局数据采集情况统计</div>
                </div>
                <div class="portlet-body ">
                    <div id="main" style="overflow:auto;">
                        <div id="leftId" style="width: 500px;height:800px;float: left;"></div>
                        <div id="rightId" style="width: 500px;height:800px;float: right;margin-top: 60px;"></div>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="button" onclick="backIndex()" class="btn blue"><i class="icon-arrow-left icon-white"></i>返回</button>
                </div>
            </div>
        </div>
    </div>
    <!-- END PAGE HEADER-->
</div>
<script>
    //返回页面功能
    function backIndex() {
        window.history.back(-1);
    }
    //页面加载完成事件
    $(function () {
       var jsonObjectTop='${jsonObjectTop}';
       var rightObject='${arrRight}';
       var obj=eval(jsonObjectTop);
       var objRight=eval(rightObject);
        loadData(obj,objRight);
    });
    //加载数据信息
    function loadData(obj,objRight) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('leftId'));
        var rightChart= echarts.init(document.getElementById('rightId'));
        //遍历结果集
        var keys=[];
        var datas=[];
        for(var i=0;i<obj.length;i++){
            keys.push(obj[i].WBJ);
            var temp={};
            temp["value"]=obj[i].SJL;
            temp["name"]=obj[i].WBJ;
            datas.push(temp);
        }
        //遍历饼图数据信息
        var rightKeys=[];
        var rightDatas=[];
        for(var j=0;j<objRight.length;j++){
            var result = {};
            rightKeys.push(objRight[j].WBJ);
            result['value'] = objRight[j].SJL;
            result['name'] =objRight[j].WBJ;
            rightDatas.push(result);
        }
        // 指定图表的配置项和数据
        var option = {
            title: {
                x: 'center',
                text: '前十名委办局(联动)'
            },
            xAxis: [{
                type: 'category',
                data: keys
            }],
            tooltip: {
                trigger: 'axis'
            },
            toolbox: {
                show: true,
                feature: {
                    dataView: {show: true, readOnly: false},
                    magicType: {show: true, type: ['line', 'bar']},
                    restore: {show: true},
                    saveAsImage: {show: true}
                }
            },
            yAxis: [{
                type: 'value',
            }],
            series: [{
                name:'具体数值',
                data: datas,
                type: 'bar',
                itemStyle: {
                    normal: {
                        color: '#269900'
                    }
                },
                showBackground: false
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);

        var opt = {
            title: {
                x: 'center',
                text: ''
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b}: {c} ({d}%)'
            },
            legend: {
                type: 'scroll',
                orient: 'horizontal',
                right: 10,
                data: rightKeys
            },
            series: [
                {
                    name: '委办局数据',
                    type: 'pie',
                    radius: ['50%', '70%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: false,
                            position: 'inside',
                            formatter:"{b}:{d}%"
                        }
                    },
                    emphasis: {
                        label: {
                            show: true,
                            fontSize: '24',
                            fontWeight: 'bold'
                        }
                    },
                    labelLine: {
                        show: false
                    },
                    data: rightDatas
                }
            ]
        };
        rightChart.setOption(opt);
    }
</script>
<!-- END PAGE CONTAINER-->
</body>
<!-- END BODY -->
</html>