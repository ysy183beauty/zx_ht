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
            <div class="portlet box boxTheme" id="listPage">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>政府部门行政效能考评详情排名</div>
                </div>
                <div class="portlet-body form">
                    <form role="form" class="form-horizontal">
                        <div class="row-fluid">
                            <div class="span4">
                                <div class="control-group">
                                    <label class="ow control-label">时间类型</label>
                                    <div class="ow controls">
                                        <label style="float:left"><input name="dataType" type="radio" value="0" checked="checked">全部</label>
                                        <label style="float:left"><input name="dataType" type="radio" value="1">本年</label>
                                        <label style="float:left"><input name="dataType" type="radio" value="2">本季</label>
                                    </div>
                                </div>
                            </div>
                            <div class="span4">
                                <div class="control-group">
                                    <label class="ow control-label">时间段选择</label>
                                    <div class="ow controls demo">
                                        <label>
                                        <i class="icon-calendar"></i>
                                        <input id="form-date-range" class="small" type="text">
                                        </label>
                                        <input type="hidden" name="startDate" value="20160101">
                                        <input type="hidden" name="endDate" value="${.now?string("yyyyMMdd")}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="button" onclick="search()" class="btn blue" ><i class="icon-search icon-white"></i>查询</button>
                            <button type="reset" class="btn"><i class="icon-repeat"></i>重置</button>
                        </div>
                    </form>
                </div>
                <div id="list" class="portlet-body ">
                    <div id="main" style="width: 1000px;height:600px;"></div>
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
        App.initUniform();
        FormComponents.init();
        $('#form-date-range').daterangepicker({
            opens:'left',
            startDate: Date.parse("2016 01 01"),
            endDate: Date.today()
        }, function (start, end,label) {
            $("input[name='startDate']").val(start.format('YYYYMMDD'));
            $("input[name='endDate']").val(end.format('YYYYMMDD'));
        });
        search();
    });
    /**
     * 作者: 张磊
     * 日期: 2017/04/10 下午05:15
     * 备注: 返回统计首页
     */
    function backIndex() {
        window.location.href="${ctx}/npt/sts/statistics/index/index.action";
    }

    function search() {
        var startDate = $("input[name='startDate']").val();
        var endDate = $("input[name='endDate']").val();

        $.ajax({
            url: 'list11.${urlExt}',
            data: {
                startDate: startDate,
                endDate: endDate
            },
            success: function (msg) {
                if (msg.result) {
                    var data = JSON.parse(msg.message);

                    console.log(data);

                    var list00 = {};
                    data.forEach(function (value) {
                        if(list00[value[0]] == undefined){
                            list00[value[0]] = {};
                        }
                        list00[value[0]][value[1]] = value[2];
                    });

                    console.log(list00);



                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));

                    // 指定图表的配置项和数据
                    var dataMap = {};

                    var sdate = new Date(startDate.substring(0,4),eval(startDate.substring(4,6))-1);
                    var edate = new Date(endDate.substring(0,4),eval(endDate.substring(4,6))-1);
                    for(var date = sdate; date<edate; date.setMonth(date.getMonth()+1)){
                        var yyyy = ''+date.getFullYear();
                        var MM ;
                        if(date.getMonth()+1<10){
                            MM = '0'+(date.getMonth()+1);
                        }else{
                            MM = ''+(date.getMonth()+1);
                        }
                        var fdate = yyyy+'-'+MM;
                        console.log(fdate);

                        dataMap[fdate] = [];
                        Object.keys(list00).forEach(function (org) {
                            var num = list00[org][yyyy+'-'+MM];
                            if(num == undefined){
                                num = 0;
                            }else{
                                num /= 100;
                            }
                            dataMap[fdate].push(num);
                        })
                    }
                    console.log(dataMap)

//                    dataMap.dataGDP = {
//                        //max : 100,
//                        2011: [51.93, 7.28, 15.76, 37.55, 59.88, 26.7, 68.83, 82, 95.69, 10.27],
//                        2010: [13.58, 24.46, 94.26, 0.86, 72, 57.27, 67.58, 68.6, 65.98, 25.48],
//                        2009: [53.03, 21.85, 35.48, 58.31, 40.25, 12.49, 78.75, 87, 46.45, 57.3],
//                        2008: [15, 19.01, 11.97, 15.4, 96.2, 68.58, 26.1, 14.37, 69.87, 81.98],
//                        2007: [46.81, 52.76, 7.32, 24.45, 23.18, 64.3, 84.69, 4, 94.01, 18.48],
//                        2006: [17.78, 62.74, 67.6, 78.61, 44.25, 4.52, 75.12, 11.8, 72.24, 42.05],
//                        2005: [69.52, 5.64, 12.11, 30.53, 5.03, 47.26, 20.27, 13.7, 47.66, 98.69],
//                        2004: [33.21, 10.97, 77.63, 71.37, 41.07, 72, 22.01, 50.6, 72.83, 3.6],
//                        2003: [7.21, 78.03, 21.29, 55.23, 88.38, 2.54, 62.08, 57.4, 94.23, 42.87],
//                        2002: [15, 50.76, 18.28, 24.8, 40.94, 58.22, 48.54, 37.2, 41.03, 6.85]
//                    };

                    var option = {
                        baseOption: {
                            timeline: {
                                // y: 0,
                                axisType: 'category',
                                // realtime: false,
                                // loop: false,
                                autoPlay: true,
                                // currentIndex: 2,
                                playInterval: 2500,
                                // controlStyle: {
                                //     position: 'left'
                                // },
                                data: Object.keys(dataMap),
                                label: {
                                    formatter: function (s) {
                                        return s.substring(2,s.length);
                                    }
                                }
                            },
                            title: {
                                x: 'center'
                            },
                            tooltip: {},
                            calculable: true,
                            grid: {
                                top: 80,
                                bottom: 100
                            },
                            yAxis: [
                                {
                                    'type': 'category',
                                    'axisLabel': {'interval': 0},
                                    'data': Object.keys(list00),
                                    splitLine: {show: false}
                                }
                            ],
                            xAxis: [
                                {
                                    type: 'value',
                                    name: ''
                                }
                            ],
                            series: [
                                {name: '绩效考核', type: 'bar'}
                            ]
                        },
                        options: (function () {
                            var options = [];
                            Object.keys(dataMap).forEach(function (date) {
                                options.push({
                                    title: {text: date+'行政效能考评结果排行'},
                                    series: [
                                        {data: dataMap[date]}
                                    ]
                                });
                            });
                            return options;
                        })()
                    };


                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            }
        });
    }
</script>
</body>
<!-- END BODY -->
</html>