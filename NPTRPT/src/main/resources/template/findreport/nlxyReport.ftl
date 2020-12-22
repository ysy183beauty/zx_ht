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
    <!--引入echarts相关Js -->
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
            line-height: 12px;
            height: 12px;
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
                    <div class="caption"><i class="icon-search"></i>自然人年龄段信用情况统计</div>
                </div>
                <div class="portlet-body form">
                    <div class="page-title">
                        <h3>自然人年龄段信用情况分析</h3>
                    </div>
                </div>
                <div class="portlet-body ">
                    <div id="main" style="overflow:auto;">
                      <div id="div1" style="float: left;width:100%;">
                         <div style="float: left;width: 50%;" id="leftDiv1">
                             <table class="gridtable">
                                 <tr>
                                     <th style="width: 150px;">失信人群年龄分布</th>
                                     <th style="width: 150px;">男性</th>
                                     <th style="width: 150px;">女性</th>
                                 </tr>
                             </table>
                         </div>
                          <div style="float: right;width: 50%" id="topContainer"></div>
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
<!-- END PAGE CONTAINER-->
<script>
    //返回功能
    function backIndex() {
        window.history.back(-1);
    }
    //页面加载完成事件
    $(function () {
        var sexResult='${sexResult}';
        var grudationResult='${grudationResult}';
        var sexObj=eval(sexResult);
        var graduationObj=eval(grudationResult);
        //获取失信人群职业信息
        var workResult='${workResult}';
        var workObj=eval(workResult);
        var str="";
        var sxList=[];
        var man=[];
        var feman=[];
        for(var i=0;i<sexObj.length;i++){
            str+='<tr><td>'+sexObj[i].SXRQ+'</td><td>'+Math.abs(sexObj[i].MAN)+'</td><td>'+Math.abs(sexObj[i].FEMAN)+'</td></tr>';
            sxList.push(sexObj[i].SXRQ);
            man.push(parseInt(sexObj[i].MAN));
            feman.push(parseInt(sexObj[i].FEMAN));
        }
        $(".gridtable").append(str);
        //加载金字塔
        loadJinZiTaData(sxList,man,feman);
        //加载失信人群学历分布情况
        loadGraduationData(graduationObj);
        //加载失信人群职业分步信息
        loadWorkData(workObj);
        //影藏英文图标
        $(".highcharts-credits").css("display","none");
        //添加分析结果
        $("#topContainer").append("<p style='text-align: center;color: #FF8800'>分析结果：1、年轻人更珍视自己的信用，中年群体风险更高 2、信贷人数随年龄呈现正态分布趋势</p>");
        $("#leftBottom").append("<p style='text-align: center;color: #FF8800'>分析结果：1、失信人群中，大学专科、中专、大学本科、高中学历为主要失信人群 <br/>2、失信人群中，大学专科学历占比最高，博士研究生占比最低</p>");
        $("#rightBottom").append("<p style='text-align: center;color: #FF8800'>分析结果：1、失信人群中，企业基层工作人员、工人占比将近一半</p>");
        //表格隔行变色
        $(".gridtable tr:odd td").css("background-color","#CCEEFF");  //改变偶数行背景色

    });
    //加载金字塔数据信息
    function loadJinZiTaData(sxList,man,feman) {
        var chart = Highcharts.chart('topContainer', {
            chart: {
                type: 'bar'
            },
            title: {
                text: '失信人群年龄分布'
            },
            subtitle: {
                useHTML: true,
                text: ''
            },
            xAxis: [{
                categories: sxList,
                reversed: false,
                labels: {
                    step: 1
                }
            }],
            yAxis: {
                title: {
                    text: ''
                },
                labels: {
                    formatter: function () {
                        return this.value;
                    }
                },
                min: -150,
                max: 150
            },
            plotOptions: {
                series: {
                    stacking: 'normal'
                }
            },
            tooltip: {
                formatter: function () {
                    return '<b>'+this.point.category+'</b><br/>'+this.series.name
                        +": "+Highcharts.numberFormat(this.point.y, 0);
                }
            },
            legend: {
                layout: 'vertical',
                align: 'right',
                verticalAlign: 'top',
                x: -40,
                y: 100
            },
            series: [{
                name: '男',
                data: man,
                color:'#00BBFF'
            }, {
                name: '女',
                data: feman,
                color:'#00DD00'
            }]
        });
    }
    //加载实现人群学历分布信息
    function loadGraduationData(obj) {
        var datas=[];
        for(var i=0;i<obj.length;i++){
            var result={};
            result['name']=obj[i].A;
            result['y']=parseFloat(obj[i].B);
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
                text: '失信人群学历分布'
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
                name: '失信人群学历信息',
                colorByPoint: true,
                data:datas
            }]
        });
    }
    //加载职业分步情况
    function loadWorkData(obj) {
        var datas=[];
        for(var i=0;i<obj.length;i++){
            var result={};
            result['name']=obj[i].A;
            result['y']=parseFloat(obj[i].B);
            datas.push(result);
        }
        Highcharts.chart('rightBottom', {
            chart: {
                type: 'pie'
            },
            title: {
                text: '失信人群职业分步'
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