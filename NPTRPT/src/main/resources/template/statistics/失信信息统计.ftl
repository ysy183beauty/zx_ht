<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${wctx}/pub/statistics/js/dataFastSelecter.js"></script>
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
                    <div class="caption"><i class="icon-search"></i>各单位荣誉/失信信息统计</div>
                </div>
                <div class="portlet-body form">
                    <form class="form-horizontal">
                        <div class="row-fluid">
                            <div class="span6">
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
                            <div class="span6">
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
                            <button type="button" class="btn blue" onclick="search()"><i class="icon-search icon-white"></i>查询</button>
                            <button type="reset" class="btn"><i class="icon-repeat"></i>重置</button>
                        </div>
                    </form>
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
            url: 'list2.${urlExt}',
            data: {
                startDate: startDate,
                endDate: endDate
            },
            success: function (msg) {
                if (msg.result) {
                    var data = JSON.parse(msg.message);

                    console.log(data);

                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            x: 'center',
                            text: '各单位荣誉/失信信息统计'
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            y: 'bottom',
                            data: ['荣誉信息', '失信信息']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                dataView: {show: true, readOnly: false},
                                magicType: {show: true, type: ['line', 'bar']},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        calculable: true,
                        xAxis: [
                            {
                                type: 'category',
                                data: data.ORG
                            }
                        ],
                        yAxis: [
                            {
                                type: 'value'
                            }
                        ],
                        series: [
                            {
                                name: '企业荣誉信息',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        color: '#269900'
                                    }
                                },
                                stack: 'one',
                                label: {
                                    normal: {
                                        show: false,
                                        position: 'top'
                                    }
                                },
                                data: data.QYRY
                            },
                            {
                                name: '个人荣誉信息',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        color: 'green'
                                    }
                                },
                                stack: 'one',
                                label: {
                                    normal: {
                                        show: false,
                                        position: 'top'
                                    }
                                },
                                data: data.GRRY
                            },
                            {
                                name: '企业失信信息',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        color: '#c23531'
                                    }
                                },
                                stack: 'two',
                                label: {
                                    normal: {
                                        show: false,
                                        position: 'top'
                                    }
                                },
                                data: data.QYSX
                            },
                            {
                                name: '个人失信信息',
                                type: 'bar',
                                itemStyle: {
                                    normal: {
                                        color: 'red'
                                    }
                                },
                                stack: 'two',
                                label: {
                                    normal: {
                                        show: false,
                                        position: 'top'
                                    }
                                },
                                data: data.GRSX
                            }
                        ]
                    };


                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                } else{
                    $('#main').html(msg.message)
                }
            }
        });
    }
</script>
</body>
<!-- END BODY -->
</html>