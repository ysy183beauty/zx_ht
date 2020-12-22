<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
    <!--引入hightCharts相关js -->
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/highChart/highcharts.js"></script>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/highChart/highcharts-more.js"></script>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/highChart/exporting.js"></script>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/highChart/data.js"></script>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/highChart/highcharts-zh_CN.js"></script>
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

<div class="container-fluid">

    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>自然人表彰与处罚情况统计</div>
                </div>
                <div class="portlet-body form">
                    <div class="page-title">
                        <h3>自然人表彰与处罚情况分析</h3>
                    </div>
                </div>
                <div class="portlet-body ">
                    <div id="main" style="overflow:auto;">
                        <div id="div1" style="float: left;width:100%;">
                            <div style="float: left;width: 40%;" id="leftDiv1">
                                <table class="gridtable">
                                    <tr>
                                        <th style="width: 100px;">时间</th>
                                        <th style="width: 100px;">表彰数量(次)</th>
                                        <th style="width: 100px;">处罚数量(次)</th>
                                    </tr>
                                </table>
                            </div>
                            <div style="float: right;width: 60%" id="topContainer"></div>
                        </div>
                        <div id="div2" style="float: left;width:100%;">
                            <div style="float: left;width: 50%;" id="leftBottom"></div>
                            <div style="float: right;width: 50%;" id="rightBottom"></div>
                        </div>
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
    var dates=[];
    //表彰与处罚数目
    var bzCounts=[];
    var cfCounts=[];
    //表彰与处罚趋势
    var bzQs=[];
    var cfQs=[];
    //返回功能
    function backIndex() {
        window.history.back(-1);
    }
    //加载完成事件
    $(function () {
       var bycfCount='${bycfCount}';
        var tbObj=eval(bycfCount);
        //调用加载表格数据信息
        loadTableData(tbObj);
        //加载表彰与处罚数据信息
        loadByCfData();
        //加载表彰领域
        var bzWork='${bzWork}';
        var bzWorkObj=eval(bzWork);
        loadbyWordData(bzWorkObj);
        //加载处罚领域数据信息
        var cfWork='${cfWork}';
        var cfWorkObj=eval(cfWork);
        loadCfWorkData(cfWorkObj);
        //隐藏图标
        $(".highcharts-credits").css("display","none");
    });
    //加载表格数据信息
    function loadTableData(tbObj) {
        var str="";
        for(var i=0;i<tbObj.length;i++){
            str+='<tr><td>'+tbObj[i].DATETIME+'</td><td>'+tbObj[i].PRAISECOUNT+'</td><td>'+tbObj[i].PUNISHCOUNT+'</td></tr>';
            //添加数据信息
            dates.push(parseInt(tbObj[i].DATETIME));
            bzCounts.push(parseInt(tbObj[i].PRAISECOUNT));
            cfCounts.push(parseInt(tbObj[i].PUNISHCOUNT));
            bzQs.push(parseFloat(tbObj[i].C));
            cfQs.push(parseFloat(tbObj[i].D));
        }
        $(".gridtable").append(str);
        //表格隔行变色
        $(".gridtable tr:odd td").css("background-color","#CCEEFF");  //改变偶数行背景色
    }
    //加载表彰与处罚图形数据
    function loadByCfData() {
        var chart = Highcharts.chart('topContainer', {
            chart: {
                zoomType: 'xy'
            },
            title: {
                text: '自然人表彰与处罚分析'
            },
            subtitle: {
                text: ''
            },
            xAxis: [{
                categories:dates,
                crosshair: true,
                labels: {
                    align: 'right',
                    style: {font: 'normal 14px 宋体'},
                    x:5
                }
            }],
            yAxis: [{ // Primary yAxis
                labels: {
                    format: '{value}',
                    style: {
                        color:'#444444'
                    }
                },
                title: {
                    text: '',
                    style: {
                        color:Highcharts.getOptions().colors[1]
                    }
                }
            }, { // Secondary yAxis
                title: {
                    text: '',
                    style: {
                        color:Highcharts.getOptions().colors[2]
                    }
                },
                labels: {
                    format: '{value}',
                    style: {
                        color:'#444444'
                    }
                },
                opposite: true
            },{ // Secondary yAxis
                title: {
                    text: '',
                    style: {
                        color: Highcharts.getOptions().colors[2]
                    }
                },
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[2]
                    }
                },
                opposite: true
            },{ // Secondary yAxis
                title: {
                    text: '',
                    style: {
                        color: Highcharts.getOptions().colors[3]
                    }
                },
                labels: {
                    format: '{value}',
                    style: {
                        color: Highcharts.getOptions().colors[3]
                    }
                },
                opposite: true
            }
            ],
            tooltip: {
                shared: true
            },
            legend: {
                layout: 'vertical',
                align: 'left',
                x: 120,
                verticalAlign: 'top',
                y: 100,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: 5,
                y: 100
            },
            series: [{
                name: '表彰数目',
                type: 'column',
                yAxis: 1,
                data:bzCounts,
                tooltip: {
                    valueSuffix: ''
                }
            }, {
                name: '处罚数目',
                type: 'column',
                yAxis: 1,
                data: cfCounts,
                tooltip: {
                    valueSuffix: ''
                }
            },{
                name: '表彰趋势',
                type: 'spline',
                data:bzQs,
                tooltip: {
                    valueSuffix: ''
                }
            },{
                name: '处罚趋势',
                type: 'spline',
                data:cfQs,
                tooltip: {
                    valueSuffix: ''
                }
            }]
        });
    }

    //加载表彰各大领域
    function loadbyWordData(bzWorkObj) {
        var datas=[];
        for(var i=0;i<bzWorkObj.length;i++){
            var result={};
            result["name"]=bzWorkObj[i].WORK;
            result["y"]=parseFloat(bzWorkObj[i].PERCENT);
            datas.push(result);
        }
        Highcharts.chart('leftBottom', {
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false,
                type: 'pie'
            },
            title: {
                text: '表彰奖励各大领域占比'
            },
            legend: {
                layout: 'vertical',
                align: 'left',
                verticalAlign: 'top',
                x: 20,
                y: 100
            },
            tooltip: {
                pointFormat:'{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                name: '',
                colorByPoint: true,
                data:datas
            }]
        });
        //添加总结信息
        $("#leftBottom").append("<p style='text-align: center;color: #FF8800'>分析结果：1、每年的表彰此事从成上升状态 2、受环境污染等因素，近三年被处罚情况呈上升趋势</p>");
    }
    //加载处罚领域数据信息
    function loadCfWorkData(obj) {
        var datas=[];
        for(var i=0;i<obj.length;i++){
            var result={};
            result['name']=obj[i].WORK;
            result['y']=parseFloat(obj[i].PERCENT);
            datas.push(result);
        }
        Highcharts.chart('rightBottom', {
            chart: {
                type: 'pie'
            },
            title: {
                text: '行政处罚各大领域占比'
            },
            subtitle: {
                text: '',
                x:'bottom',
                y:'center'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: 20,
                y: 100
            },
            tooltip: {
                headerFormat: '',
                pointFormat:'<b> {point.name}</b><br/>{point.percentage:.1f}%</b>'
            },
            series: [{
                minPointSize: 10,
                innerSize: '20%',
                zMin: 0,
                name: 'countries',
                data: datas
            }]
        });
    }
</script>
</body>
<!-- END BODY -->
</html>