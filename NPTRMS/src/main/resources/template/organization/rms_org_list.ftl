
<div class="portlet box boxTheme" style="display: none;">
    <div class="portlet-title">
        <div class="caption"><i class="icon-calendar"></i> 数据统计</div>
        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>
    <div class="portlet-body">
        <div class="row-fluid">
            <div class="span6">
                <div class="sparkline-chart">
                    <div class="number" id="myzcsmyhs"></div>
                    <span>每月注册实名用户数 </span>
                </div>
            </div>
            <div class="margin-bottom-10 visible-phone"></div>
            <div class="span6">
                <div class="sparkline-chart">
                    <div class="number" id="mysjzzs"></div>
                    <span>每月数据增长数 </span>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="portlet box boxTheme">
    <div class="portlet-title">
        <div class="caption"><i class="icon-bullhorn"></i> 各委办局数据量</div>
        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>
    <div class="portlet-body">
            <div id="main1" style="height:400px;"></div>

    </div>
</div>
<script src="${wctx}/pub/CreditStyle/echarts/echarts.min.js"></script>
<script>
    $.ajax({
        url: "${ctx}/npt/sts/statistics/nptOrgIndex.${urlExt}",
        success: function (data) {
            if (data.result) {
                var jsonData = JSON.parse(data.message);
                $("#myzcsmyhs").sparkline(jsonData.myzcsmyhs, {
                            type: 'bar',
                            width: '100',
                            barWidth: 5,
                            height: '55',
                            barColor: '#ffb848',
                            negBarColor: '#e02222'
                        }
                );

                $("#mysjzzs").sparkline(jsonData.mysjzzs, {
                    type: 'line',
                    width: '100',
                    height: '55',
                    lineColor: '#ffb848'
                });

                myChart = echarts.init(document.getElementById('main1'));
                dataAxis = [];
                dataShadow = [];
                var yMax = 0;
                for (var i = 0, len = jsonData.gwbjsjl.length; i < len; i++) {
                    dataAxis.push(jsonData.gwbjsjl[i].name);
                    if (jsonData.gwbjsjl[i].value > yMax) {
                        yMax = jsonData.gwbjsjl[i].value;
                    }
                }
                for (var i = 0, len = jsonData.gwbjsjl.length; i < len; i++) {
                    dataShadow.push(Math.floor(yMax / 5 + 1) * 5);
                }

                option = {
                    tooltip: {
                        trigger: 'axis',
                        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                        },
                        formatter: "{b} : {c1}<br/>"
                    },
                    xAxis: {
                        data: dataAxis
                    },
                    yAxis: {
                        axisLine: {
                            show: false
                        },
                        axisTick: {
                            show: false
                        },
                        axisLabel: {
                            textStyle: {
                                color: '#999'
                            }
                        }
                    },
                    dataZoom: [
                        {
                            type: 'inside'
                        }
                    ],
                    series: [
                        { // For shadow
                            type: 'bar',
                            itemStyle: {
                                normal: {color: 'rgba(0,0,0,0.05)'}
                            },
                            barGap: '-100%',
                            barCategoryGap: '50%',
                            data: dataShadow
                        },
                        {
                            type: 'bar',
                            itemStyle: {
                                normal: {
                                    color: new echarts.graphic.LinearGradient(
                                            0, 0, 0, 1,
                                            [
                                                {offset: 0, color: '#83bff6'},
                                                {offset: 0.5, color: '#188df0'},
                                                {offset: 1, color: '#188df0'}
                                            ]
                                    )
                                },
                                emphasis: {
                                    color: new echarts.graphic.LinearGradient(
                                            0, 0, 0, 1,
                                            [
                                                {offset: 0, color: '#2378f7'},
                                                {offset: 0.7, color: '#2378f7'},
                                                {offset: 1, color: '#83bff6'}
                                            ]
                                    )
                                }
                            },
                            data: jsonData.gwbjsjl
                        }
                    ]
                };
                myChart.setOption(option);
            }
        }
    })
</script>