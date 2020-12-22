<div class="portlet box boxTheme" >
    <div class="portlet-title">
        <div class="caption"><i class="icon-search"></i>信息统计详情</div>
    </div>
    <div class="portlet-body">
        <div class="dataTables_wrapper">
            <div class="btn-group">
                <button class="btn " onclick="returnList()"><i class="m-icon-swapleft"></i> 返回</button>
            </div>
        </div>
        <div id="main" style="width: 600px;height:400px;"></div>
    </div>
    <div class="portlet-body"><h3>${ORG.alias}-${DATATYPE.alias}信息量变化详情表</h3></div>
    <div class="portlet-body">
        <table class="table table-striped table-bordered table-hover" id="sample_1">
            <thead>
            <tr>
                <th>时间</th>
                <th>新增数量</th>
                <th>更新数量</th>
                <th>减少数量</th>
                <th>入库总量</th>
            </tr>
            </thead>
            <tbody>
            <#list DATALIST as data>
            <tr>
                <td>${data[0]}</td>
                <td>${data[1]}</td>
                <td>${data[2]}</td>
                <td><#if data[3] != '0'>-</#if>${data[3]}</td>
                <td>${data[4]}</td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
</div>
</div>

</div>

<script>

    var xData = [];
    var sData = [];
    <#list DATALIST as data>
    xData.push('${data[0]}');
    sData.push(${data[4]});
    </#list>

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '${DATATYPE.alias}信息量趋势',
            subtext: '${ORG.alias}'
        },
        tooltip: {
            trigger: 'axis'
        },
        toolbox: {
            show: true,
            feature: {
                dataZoom: {
                    yAxisIndex: 'none'
                },
                dataView: {readOnly: false},
                magicType: {type: ['line', 'bar']},
                restore: {},
                saveAsImage: {}
            }
        },
        xAxis: {
            type: 'category',
            data: xData
        },
        yAxis: {
            type: 'value',
            scale: true,
            axisLabel: {
                formatter: '{value}'
            }
        },
        series: [
            {
                name: '总量',
                type: 'line',
                data: sData
            }
        ]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
</script>
