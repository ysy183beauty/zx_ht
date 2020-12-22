<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${wctx}/pub/statistics/js/dataFastSelecter.js"></script>
    <style type="text/css">
        table.gridtable {
            font-family: verdana,arial,sans-serif;
            font-size:11px;
            color:#333333;
            border-width: 1px;
            border-color: #666666;
            border-collapse: collapse;
            text-align: center;
            margin: 15px;
        }
        table.gridtable tr{
            line-height: 11px;
            height: 11px;
        }
        table.gridtable th {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #00DDDD;
        }
        table.gridtable td {
            border-width: 1px;
            padding: 8px;
            border-style: solid;
            border-color: #666666;
            background-color: #ffffff;
        }
    </style>
</head>
<body class="page-header-fixed">

<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<!-- BEGIN PAGE CONTAINER-->
<div class="container-fluid">
    <!-- BEGIN PAGE HEADER-->
    <!-------------------------------------各单位荣誉/失信信息统计-------------------------------------------->
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>学生报表信息</div>
                </div>
                <div class="portlet-body form">
                    <div class="page-title">
                        <h3>学生人数统计信息</h3>
                    </div>
                    <table class="gridtable">
                        <tr>
                            <th width="350px">学历</th><th width="380px">人数</th>
                        </tr>
                    </table>
                </div>
                <div class="portlet-body">
                    <div id="main" style="width: 1000px;height:400px;"></div>
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
    $(function () {
        var jsonObjectOne='${jsonObjectOne}';
        var objOne= JSON.parse(jsonObjectOne);
        var str="";
        var i=0;
        //遍历json对象信息
        for (var key in objOne) {
            str+="<tr><td>"+key+"</td><td>"+objOne[key]+"</td></tr>";
        }
        $(".gridtable").append(str);
        //表格隔行变色
        $(".gridtable tr:odd td").css("background-color","#CCEEFF");  //改变偶数行背景色
        //获取饼图数据信息
        var jsonObjectTwo='${jsonObjectTwo}';
        var objTwo=JSON.parse(jsonObjectTwo);
        loadData(objTwo);
    });
    //饼图形状
    function loadData(obj){
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));
        // 指定图表的配置项和数据
        var option = {
            title : {
                text: '各类别学生占比(实时)统计',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                right: 'right',
                y: 'center',
                data: Object.keys(obj)
            },
            series : [
                {
                    name: '各类别学生占比(实时)统计',
                    type: 'pie',
                    radius : '65%',
                    center: ['50%', '60%'],
                    data:(function () {
                        var list = [];
                        Object.keys(obj).forEach(function (item) {
                            var result = {};
                            result['value'] = obj[item];
                            result['name'] = item;
                            if(obj[item] != 0){
                                list.push(result);
                            }
                        });
                        return list;
                    })(),
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        },
                        normal:{
                            color:function(params) {
                                //自定义颜色
                                var colorList = [
                                    '#FF3EFF', '#00FF00', '#00FFCC', '#FF8C00', '#FF0000', '#BBFF00',
                                ];
                                return colorList[params.dataIndex]
                            }
                        }
                    }
                }
            ]
        };
        if(option.series[0].data.length == 0){
            $('#main').html("暂无数据")
        }else{
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);
        }
    }
    //返回功能
    function backIndex() {
        window.history.back(-1);
    }
</script>
</body>
<!-- END BODY -->
</html>