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
                    <div class="caption"><i class="icon-search"></i>单位行政效能考评结果详情</div>
                </div>
                <div class="portlet-body form">
                    <form role="form" class="form-horizontal">
                        <div class="row-fluid">
                            <div class="span4">
                                <div class="control-group">
                                    <label class="ow control-label">单位</label>
                                    <div class="ow controls">
                                        <select class=" m-wrap" tabindex="1" id="orgId">
                                            <option value="-1"></option>
                                        <#if _ORG_LIST??>
                                            <#list _ORG_LIST as c>
                                                <option value="${c.id?string("#")}">${c.orgName}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="span4">
                                <div class="control-group">
                                    <label class="ow control-label">时间类型</label>
                                    <div class="ow controls">
                                        <label style="float:left"><input name="dataType" type="radio" value="0" checked="checked">全部</label>
                                        <label style="float:left"><input name="dataType" type="radio" value="1">本年</label>
                                        <label style="float:left"><input name="dataType" type="radio" value="2">本季</label>
                                        <#--<label style="float:left"><input name="dataType" type="radio" value="3">本月</label>-->
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
        //设置默认值信息
        var count=$("#orgId option").length;
        if(count>1){
            $("#orgId").val($("#orgId option").get(1).value);
        }
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
        var orgId = $("#orgId").val();
        var orgName = $("#orgId").find("option:selected").text();
        var startDate = $("input[name='startDate']").val();
        var endDate = $("input[name='endDate']").val();
        console.log(orgId);
        console.log(startDate);
        console.log(endDate);
        $.ajax({
            url: 'list10.${urlExt}',
            data: {
                orgId: orgId,
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

                    if(option.series[0].data.filter(function (value) {
                                return value != 0;
                            }).length == 0){
                        $('#main').html("暂无数据")
                    }else{
                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    }
                }
            }
        });
    }
</script>
</body>
<!-- END BODY -->
</html>