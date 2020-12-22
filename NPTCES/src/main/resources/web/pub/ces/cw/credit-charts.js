<!--折线图-->
var myChart_line = echarts.init(document.getElementById('riskTrend-canvas'));
window.onresize=myChart_line.resize;
$.get('listRiskIndex.action', function (data) {

    // console.log(data);
    var xAxisData = [];
    var yAxisData = [];
    data.forEach(function (item) {
        xAxisData.push(item.weekNo);
        yAxisData.push(item.riskIndex);
    });
    myChart_line.setOption(option = {
        title: {
            text: '信用风险指数'
        },
        tooltip: {
            trigger: 'axis'
        },
        xAxis: {
            data: xAxisData
        },
        yAxis: {},
        toolbox: {
            left: 'center',
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                restore: {},
                saveAsImage: {}
            }
        },
        dataZoom: [{
            startValue: '2014-06-01'
        }, {
            type: 'inside'
        }],
        visualMap: {
            top: 10,
            right: 10,
            pieces: [{
                gt: 0,
                lte: 100,
                color: '#096'
            }, {
                gt: 100,
                lte: 200,
                color: '#ffde33'
            }, {
                gt: 200,
                lte: 300,
                color: '#ff9933'
            }, {
                gt: 300,
                lte: 400,
                color: '#cc0033'
            }, {
                gt: 400,
                lte: 500,
                color: '#660099'
            }, {
                gt: 500,
                lte: 600,
                color: '#006699'
            },{
                gt: 600,
                lte: 700,
                color: '#ffff99'
            },{
                gt: 700,
                color: '#7e0023'
            }],
            outOfRange: {
                color: '#999'
            }
        },
        series: {
            name: '风险指数',
            type: 'line',
            data: yAxisData,
            markLine: {
                silent: true,
                data: [{
                    yAxis: 100
                }, {
                    yAxis: 200
                }, {
                    yAxis: 200
                }, {
                    yAxis: 300
                }, {
                    yAxis: 400
                }]
            }
        }
    });

});
