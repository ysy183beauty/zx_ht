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
                    <div class="caption"><i class="icon-search"></i>人民群众反映部门意见情况统计</div>
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
                                    <label class="ow control-label"> 时间段选择</label>
                                    <div class="ow controls demo">
                                        <i class="icon-calendar"></i>
                                        <input id="form-date-range" class="small" type="text">
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
            <div id="detailPage" style="display: none">
                <div id="detail" style="width: 1000px;height:400px;"></div>
                <button type="button" class="btn blue" onclick="returnList()">返回</button>
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

    /**
     * 作者: 张磊
     * 日期: 16/10/28 下午2:58
     * 备注: 入库总量统计查询
     */
    function query(form) {
        $.post("list.action",$(form).serialize(),function (data) {
            $("#list").html(data);
        });
        return false;
    }

    /**
     * 作者: 张磊
     * 日期: 16/10/28 下午3:3
     * 备注: 信息量变化详情
     */
    function detail(id) {
        $.ajax({
            url: "detail.${urlExt}",
            timeout:30000,
            data: {
                id:id,
                startDate:$("input[name='startDate']").val(),
                endDate: $("input[name='endDate']").val()
            },
            success: function (data) {
                showDetal(data);
            }
        });
    }

    /**
     *作者: xuqinyuan
     *时间: 2016/10/30 14:49
     *备注: 返回主页
     */
    function returnList(){
        $("#listPage").show();
        $("#detailPage").hide();
    }
    /**
     *作者: xuqinyuan
     *时间: 2016/10/30 14:52
     *备注:显示详情
     */
    function showDetal(orgId,orgName,startDate,endDate) {
        console.log(orgId);
        console.log(orgName);
        console.log(startDate);
        console.log(endDate);
        $.ajax({
            url: 'list9.${urlExt}',
            data: {
                orgId: orgId,
                startDate: startDate,
                endDate: endDate
            },
            success: function (msg) {
                if (msg.result) {
                    var data = JSON.parse(msg.message);

                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('detail'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            x: 'center',
                            text: orgName+'行政效能考评结果'
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
                            boundaryGap: false,
                            data: data.map(function (obj) {
                                return obj.name;
                            })
                        },
                        yAxis: {
                            type: 'value',
                            axisLabel: {
                                formatter: '{value}'
                            }
                        },
                        series: [
                            {
                                name: '行政效能',
                                type: 'line',
                                data: data.map(function (obj) {
                                    return obj.value/100;
                                })
                            }
                        ]
                    };


                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);

                    $("#listPage").hide();
                    $("#detailPage").show();
                }
            }
        });
    }

    function search() {
        var startDate = $("input[name='startDate']").val();
        var endDate = $("input[name='endDate']").val();
        console.log(startDate);
        console.log(endDate);
        $.ajax({
            url: 'list9.${urlExt}',
            data: {
                startDate: startDate,
                endDate: endDate
            },
            success: function (msg) {
                if (msg.result) {
                    var data = JSON.parse(msg.message);

                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            x: 'center',
                            text: '人民群众反映部门意见情况统计'
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        grid: {
                            left: '3%',
                            right: '4%',
                            bottom: '3%',
                            containLabel: true
                        },
                        xAxis: {
                            type: 'value',
                            boundaryGap: [0, 0.01]
                        },
                        yAxis: {
                            type: 'category',
                            data: (function () {
                                var list = [];
                                data.forEach(function (item) {
                                    list.push(item['name']);
                                });
                                return list;
                            })()
                        },
                        series: [
                            {
                                name: '统计',
                                type: 'bar',
                                label: {
                                    normal: {
                                        show: true,
                                        position: 'right'
                                    }
                                },
                                data: (function () {
                                    var list = [];
                                    data.forEach(function (item) {
                                        list.push(item['value']);
                                    });
                                    return list;
                                })()
                            }
                        ]
                    };

                    var orgIdList = (function () {
                        var list = [];
                        data.forEach(function (item) {
                            list.push(item['id']);
                        });
                        return list;
                    })();

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);

                    // 添加监听事件
                    function eConsole(param) {
                        showDetal(orgIdList[param.dataIndex],param.name,startDate,endDate);
                    }

                    myChart.on("click", eConsole);
                }
            }
        });
    }
</script>
</body>
<!-- END BODY -->
</html>