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
                    <div class="caption"><i class="icon-search"></i>各地区荣誉信息、失信信息占比统计</div>
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
                                        <label style="float:left"><input name="dataType" type="radio" value="3">本月</label>
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
                    <div id="main" style="width: 1000px;height:800px;"></div>
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
        console.log(startDate);
        console.log(endDate);
        $.ajax({
            url: 'list4.${urlExt}',
            data: {
                startDate: startDate,
                endDate: endDate
            },
            success: function (msg) {
                if (msg.result) {
                    var data = JSON.parse(msg.message);

                    $.get('${wctx}/pub/CreditStyle/echarts/map/json/map.json', function (geoJson) {
                        echarts.registerMap('map', geoJson);
                        // 基于准备好的dom，初始化echarts实例
                        var myChart = echarts.init(document.getElementById('main'));
                        // 指定图表的配置项和数据
                        var option = {
//            backgroundColor: '#404a59',
                            title: {
                                x: 'center',
                                text: '各地区企业荣誉、失信信息占比统计',
                                subtext: startDate+'~'+endDate
                            },
                            tooltip: {
                                show: false
                            },
                            legend: {
                                orient: 'vertical',
                                left: 'left'
                            },
                            toolbox: {
                                show: true,
                                orient: 'vertical',
                                left: 'right',
                                top: 'center',
                                feature: {
                                    dataView: {readOnly: false},
                                    restore: {},
                                    saveAsImage: {}
                                }
                            },
                            series: [
                                {
                                    name: '企业荣誉、失信信息',
                                    type: 'map',
                                    mapType: 'map',
                                    roam: false,
                                    label: {
                                        normal: {
                                            show: true
                                        },
                                        emphasis: {
                                            show: true
                                        }
                                    }
                                }
                            ]
                        };
                        myChart.setOption(option);

                        Object.keys(data).forEach(function (item) {
                            option.series.push({
                                type: 'pie',
                                tooltip: {
                                    show: true
                                },
                                center: getPosByGeo(myChart, item.split(',').map(Number)),
                                radius: '5%',
                                label: {
                                    normal: {
                                        show: false
                                    }
                                },
                                data: [
                                    {
                                        name: "荣誉",
                                        itemStyle: {
                                            normal: {
                                                color: 'green'
                                            }
                                        },
                                        value: data[item].split(',')[0]
                                    },
                                    {
                                        name: "失信",
                                        itemStyle: {
                                            normal: {
                                                color: 'red'
                                            }
                                        },
                                        value: data[item].split(',')[1]
                                    }
                                ]
                            });
                        });

                        myChart.setOption(option);
                    });
                }
            }
        });
    }

    /**
     * @param  {Array} [ew,ns] （经纬度）
     * @returns {Array} [x, y] 坐标值
     */
    function getPosByGeo(myChart, geo) {
        // 获取系列
        var seriesModel = myChart.getModel().getSeriesByIndex(0);
        // 获取地理坐标系实例
        var coordSys = seriesModel.coordinateSystem;
        // dataToPoint 相当于 getPosByGeo
        var point = coordSys.dataToPoint(geo);
        return point;
    }
</script>
</body>
<!-- END BODY -->
</html>