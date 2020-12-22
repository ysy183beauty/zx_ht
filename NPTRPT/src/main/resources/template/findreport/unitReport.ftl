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
</head>
<body class="page-header-fixed">

<div class="container-fluid">

    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>各单位失信情况统计</div>
                </div>
                <div class="portlet-body ">
                    <div id="main" style="width: 1000px;height: 600px;">
                        <div style="display:none">
                            <table id="freq" border="0" cellspacing="0" cellpadding="0">
                                <tr nowrap bgcolor="#CCCCFF">
                                    <th colspan="2" class="hdr"></th>
                                </tr>
                            </table>
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
    //返回页面功能
    function backIndex() {
        window.history.back(-1);
    }
    //页面加载完成事件
    $(function () {
        //拼接需要的动态数据信息
        var result='${sxResult}';
        var obj=eval(result);
        var str='<tr nowrap bgcolor="#CCCCFF"><th class="freq">Direction</th><th class="freq">各单位失信情况</th></tr>';
        var totalCount=0;
        for(var i=0;i<obj.length;i++){
             if(i%2==0){//偶数
                 str+='<tr nowrap bgcolor="#DDDDDD"><td class="dir">'+obj[i].QY+'</td><td class="data">'+obj[i].SJL+'</td></tr>';
             }else{//奇数
                 str+='<tr nowrap><td class="dir">'+obj[i].QY+'</td><td class="data">'+obj[i].SJL+'</td></tr>';
             }
            totalCount+=parseInt(obj[i].SJL);
        }
        //添加总数信息
        str+='<tr nowrap><td class="totals">总数</td><td class="totals">'+totalCount+'</td></tr>';
        $("#freq").append(str);
        loadData(obj);
        //隐藏右下角，highChart官网英文名
        $(".highcharts-credits").css("display","none");
    });
    //加载数据信息
    function loadData(obj) {
        // 使用数据功能模块进行数据解析
        var chart = Highcharts.chart('main', {
            data: {
                table: 'freq',
                startRow: 1, //从第一行开始 hdr
                endRow:(obj.length+1),//Direction第一行开始数行数
                endColumn: 2
            },
            chart: {
                polar: true,
                type: 'column'
            },
            title: {
                text: '各单位失信情况统计',
                style:{
                    color: '#3E576F',
                    fontSize: '24px'
                }
            },
            subtitle: {
                text: ''
            },
            pane: {
                size: '85%'
            },
            legend: {
                align: 'right',
                verticalAlign: 'top',
                y: 100,
                layout: 'vertical',
                itemStyle:{
                    'fontSize' : '18px'
                }
            },
            xAxis: {
                tickmarkPlacement: 'on',
                gridLineColor:'#AAAAAA',
                labels : {
                    style : {'fontSize' : '14px'}
                }
            },
            yAxis: {
                min: 0,
                endOnTick: false,
                showLastLabel: true,
                gridLineColor: '#888888',
                gridLineWidth:1,
                title: {
                    text: ''
                },
                labels: {
                    formatter: function () {
                        return this.value;
                    },
                    style : {'fontSize' : '18px'}
                },
                reversedStacks: false
            },
            tooltip: {
                valueSuffix: '',
                style:{
                    'fontSize' : '16px'
                }
            },
            plotOptions: {
                series: {
                    stacking: 'normal',
                    shadow: false,
                    groupPadding: 0,
                    pointPlacement: 'on'
                }
            }
        });
    }
</script>
<!-- END PAGE CONTAINER-->
</body>
<!-- END BODY -->
</html>