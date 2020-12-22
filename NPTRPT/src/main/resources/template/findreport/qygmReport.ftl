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
                    <div class="caption"><i class="icon-search"></i>企业规模信用统计分析</div>
                </div>
                <div class="portlet-body ">
                    <div id="main" style="overflow:auto;">
                        <div id="leftId" style="width: 500px;height:800px;float: left;margin-top: 15px;"></div>
                        <div id="rightId" style="width: 500px;height:800px;float: right;margin-top: 15px;"></div>
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
    //返回页面功能
    function backIndex() {
        window.history.back(-1);
    }
    //页面加载完成事件
    $(function () {
       var result='${result}';
       var obj=eval(result);
       loadData(obj);
    });
    //加载数据信息
    function loadData(obj) {
        //加载左边数据信息
        var myChart = echarts.init(document.getElementById('leftId'));
        var rightChart = echarts.init(document.getElementById('rightId'));
        //遍历结果集
        var keys=[];
        var ryDatas=[];
        var sxDatas=[];
        for(var i=0;i<obj.length;i++){
            var temp={};
            var temp2={};
            keys.push(obj[i].SCAL);
            temp["value"]=obj[i].RYCOUNT;
            temp["name"]=obj[i].SCAL;
            temp2["value"]=obj[i].SXCOUNT;
            temp2["name"]=obj[i].SCAL;
            ryDatas.push(temp);
            sxDatas.push(temp2);
        }
        var option={
            title: {
                x:'center',
                text: '企业荣誉信息占比分析',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: keys
            },
            color:['#00FF00', '#00BFFF', '#66FFFF','#FF00FF','#6A5ACD'],
            series: [
                {
                    name: '企业荣誉',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data:ryDatas,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);

        var opt={
            title: {
                text: '企业失信信息占比分析',
                left: 'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: '{a} <br/>{b} : {c} ({d}%)'
            },
            legend: {
                orient: 'vertical',
                left: 'right',
                data: keys
            },
            color:['#FF8C00', '#EE82EE', '#00FF00','#00FFFF','#FF8800'],
            series: [
                {
                    name: '企业失信',
                    type: 'pie',
                    radius: '55%',
                    center: ['50%', '60%'],
                    data:sxDatas,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        // 使用刚指定的配置项和数据显示图表。
        rightChart.setOption(opt);
    }
</script>
</body>
<!-- END BODY -->
</html>