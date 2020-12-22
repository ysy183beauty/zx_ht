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
                    <div class="caption"><i class="icon-search"></i>企业信用评估情况统计</div>
                </div>
                <div class="portlet-body ">
                    <div id="main" style="width: 1000px;height:600px;">

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
<!-- END PAGE CONTAINER-->
<script>
    //返回按钮
    function backIndex() {
        window.history.back(-1);
    }
    //页面加载完成事件
    $(function () {
       var xypg='${xypg}';
        var obj=eval(xypg);
        //加载数据信息
        loadData(obj);
    });
    //加载数据信息
    function loadData(obj) {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        var indicators=[];
        var tempDatas=[];
        var datas=[];
        var showReuslt=false;
        for(var i=0;i<obj.length;i++){
            if(i==0){
                showReuslt=true;
            }else{
                showReuslt=false;
            }
            var indicat={};
            indicat["name"]=obj[i].A;
            indicat["axisLabel"]={show:showReuslt};
            indicat["max"]=100;
            indicat["color"]='#000';
            indicators.push(indicat);
            tempDatas.push(Math.round((obj[i].B)*100)/100);
        }
        var data={"name":'企业信用评估信息','value':tempDatas};
        datas.push(data);
        var option = {
            title: {
                text: '企业信用评估情况',
                x:'center'
            },
            tooltip: {
                confine: true,
                enterable: true
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            legend: {

            },
            radar: {
                //shape: 'circle',
                name: {
                    textStyle: {
                        color: '#444444',
                        backgroundColor: '#999',
                        borderRadius: 3,
                        padding: [3, 5]
                    }
                },
                indicator: indicators,
                center: ['50%','50%'],
                radius: 200,  //半径也就是整个图像的大小
                axisLine: {
                    lineStyle: {
                        color: '#AAAAAA',
                    },
                },
                axisLabel: {
                    show: true,
                    fontSize: 16,
                    color: '#838D9E',
                    showMaxLabel: false, //不显示最大值，即外圈不显示数字30
                    showMinLabel: false //显示最小数字，即中心点显示0
                },
                splitArea : {
                    show : false,
                    areaStyle : {
                        color: 'rgba(255,0,0,0)', // 图表背景的颜色
                    },
                }
            },
            calculable : true,
            series: [{
                name: '',
                type: 'radar',
                // areaStyle: {normal: {}},
                data: datas,
                symbol: 'circle', // 拐点的样式，还可以取值'rect','angle'等
                symbolSize: 8// 拐点的大小
            }]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    }
</script>
</body>
<!-- END BODY -->
</html>