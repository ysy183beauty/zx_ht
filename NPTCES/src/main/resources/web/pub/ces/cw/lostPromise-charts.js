var myChart = echarts.init(document.getElementById('lostPromise-canvas'));
window.onresize = myChart.resize;
$.get("analyzeTendency.action", {creditEntityId: $("#creditEntityId").val()}, function (data) {
    // console.log(data);
    var xAxisData = [];
    var legendData = [];
    var year = "";
    data.forEach(function (l) {
        xAxisData.push(l.batchNo.split("#")[1]);
        year = l.batchNo.split("#")[0];
        Object.keys(l.dsMap).forEach(function (k) {
            if (legendData[k] === undefined) {
                legendData[k] = [];
            }
            legendData[k].push(l.dsMap[k]);
        });
    });

    $("#year").html(year);

    // console.log(xAxisData);
    // console.log(legendData);


    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        legend: {
            data: Object.keys(legendData)
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        yAxis: {
            type: 'value'
        },
        xAxis: {
            type: 'category',
            data: xAxisData
        },
        series: (function () {
            var seriesData = [];
            Object.keys(legendData).forEach(function (k) {
                seriesData.push({
                    name: k,
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'insideRight'
                        }
                    },
                    data: legendData[k]
                });
            });
            return seriesData;
        })()
    };
    myChart.setOption(option);

});
