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
                    <div class="caption"><i class="icon-search"></i>信息量排行统计</div>
                </div>
                <div class="portlet-body form">
                    <form role="form" class="form-horizontal">
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label class="ow control-label">统计量</label>
                                    <div class="ow controls">
                                        <select class="small m-wrap" tabindex="1" id="tjType">
                                            <option value="100100">荣誉信息</option>
                                            <option value="100101" selected="selected">失信信息</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="control-group">
                                    <label class="ow control-label"><span>失信信息</span>数量</label>
                                    <div class="ow controls">
                                        <select class="small m-wrap" tabindex="1" id="sjl">
                                            <option value="0">全部</option>
                                            <option value="10" selected="selected">大于10</option>
                                            <option value="20" selected="selected">大于20</option>
                                            <option value="30" selected="selected">大于30</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
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
    function showDetal(qyId,qyName, startDate, endDate, tjType, tjName) {
        $.ajax({
            url: 'detail8.${urlExt}',
            data: {
                tjType: tjType,
                qyId: qyId,
                startDate: startDate,
                endDate: endDate
            },
            success: function (msg) {
                if (msg.result) {
                    var data = JSON.parse(msg.message);
                    console.log(data);

                    // 基于准备好的dom，初始化echarts实例
                    var detailChart = echarts.init(document.getElementById('detail'));

                    // 指定图表的配置项和数据
                    var detail_option = {
                        title: {
                            x: 'center',
                            text: qyName+tjName+'量统计'
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
                            data: data.map(function (value) {
                                return value.name;
                            })
//                            data: ['2015年1月', '2015年2月', '2015年3月', '2015年4月', '2015年5月', '2015年6月', '2015年7月', '2015年8月', '2015年9月']
                        },
                        yAxis: {
                            type: 'value',
                            axisLabel: {
                                formatter: '{value}'
                            }
                        },
                        series: [
                            {
                                name: tjName,
                                type: 'line',
                                data: data.map(function (value) {
                                    return value.value;
                                })
//                                data: [11, 11, 15, 13, 12, 13, 10, 1, 2]
                            }
                        ]
                    };


                    // 使用刚指定的配置项和数据显示图表。
                    detailChart.setOption(detail_option);
                    $("#listPage").hide();
                    $("#detailPage").show();
                }
            }
        });
    }


    function search() {
        var tjType = $("#tjType").val();
        var tjName = $("#tjType").find("option:selected").text();
        var sjl = $("#sjl").val();
        var startDate = $("input[name='startDate']").val();
        var endDate = $("input[name='endDate']").val();
        console.log(sjl);
        console.log(tjType);
        console.log(startDate);
        console.log(endDate);
        $.ajax({
            url: 'list8.${urlExt}',
            data: {
                tjType: tjType,
                sjl:sjl,
                startDate: startDate,
                endDate: endDate
            },
            success: function (msg) {
                if (msg.result) {
                    var data = JSON.parse(msg.message);
                    console.log(data);

                    var orgList = (function () {
                        var list=[];
                        data.forEach(function(value){
                            var org = value[1];
                            if(list.indexOf(org) == -1){
                                list.push(org);
                            }
                        });
                        return list;
                    })();
                    var companyList = (function () {
                        var listValue = {};
                        data.forEach(function(value){
                            var company = value[0];
                            if(listValue[company] == undefined){
                                listValue[company] = value[2];
                            }else{
                                listValue[company] += value[2];
                            }
                        });

                        var list = Object.keys(listValue);
                        // 排序
                        list.sort(function (a, b) {
                            return listValue[a] - listValue[b];
                        });
                        // 过滤
                        while(listValue[list[0]]<sjl){
                            list.shift();
                        }

                        return list;
                    })();

                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));

                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            x: 'center',
                            text: '企业'+tjName+'数量排行',
                            subtext: startDate+'~'+endDate
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'shadow'
                            }
                        },
                        legend: {
                            y: 'bottom',
                            data: orgList
//                            data: ['工商局', '海关', '公积金', '公安局', '食药监', '国土局', '城管局', '国税局', '统计局']
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
                            data: companyList
//                            data: ['公司A', '公司B', '公司C', '公司D', '公司E', '公司F']
                        },
                        series: (function () {
                            var obj = {};
                            orgList.forEach(function (org) {
                                obj[org] = {};
                            });
                            data.forEach(function (value) {
                                obj[value[1]][value[0]] = {};
                                obj[value[1]][value[0]].name = value[3];
                                obj[value[1]][value[0]].value = value[2];
                            });

                            var series = [];
                            orgList.forEach(function (org) {
                                var s = {};
                                s.name = org;
                                s.type = 'bar';
                                s.stack = 'one';
                                s.data = (function () {
                                    var sdata = [];
                                    companyList.forEach(function (company) {
                                        if (obj[org][company] == undefined) {
                                            obj[org][company] = {};
                                        }
                                        if(obj[org][company].value == undefined){
                                            obj[org][company].value = 0;
                                        }
                                        sdata.push({
                                            name : obj[org][company].name,
                                            value: obj[org][company].value
                                        });
                                    });
                                    return sdata;
                                })();
                                series.push(s);
                            });

                            return series;
                        })()
                        /*series: [
                            {
                                name: '工商局',
                                type: 'bar',
                                stack: 'one',
                                data: [18203, 23489, 29034, 104970, 131744, 163023]
                            },
                            {
                                name: '海关',
                                type: 'bar',
                                stack: 'one',
                                data: [19325, 23438, 31000, 121594, 134141, 168180]
                            },
                            {
                                name: '公积金',
                                type: 'bar',
                                stack: 'one',
                                data: [19325, 23438, 31000, 121594, 134141, 168180]
                            },
                            {
                                name: '公安局',
                                type: 'bar',
                                stack: 'one',
                                data: [19325, 23438, 31000, 121594, 134141, 168180]
                            },
                            {
                                name: '食药监',
                                type: 'bar',
                                stack: 'one',
                                data: [19325, 23438, 31000, 121594, 134141, 168180]
                            },
                            {
                                name: '国土局',
                                type: 'bar',
                                stack: 'one',
                                data: [19325, 23438, 31000, 121594, 134141, 168180]
                            },
                            {
                                name: '城管局',
                                type: 'bar',
                                stack: 'one',
                                data: [19325, 23438, 31000, 121594, 134141, 168180]
                            },
                            {
                                name: '国税局',
                                type: 'bar',
                                stack: 'one',
                                data: [19325, 23438, 31000, 121594, 134141, 168180]
                            },
                            {
                                name: '统计局',
                                type: 'bar',
                                stack: 'one',
                                data: [19325, 23438, 31000, 121594, 134141, 68180]
                            }
                        ]*/
                    };

                    if(companyList.length==0){
                        $('#main').html("暂无数据")
                    }else {
                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);

                        // 添加监听事件
                        function eConsole(param) {
                            console.log(param);
                            showDetal(param.data.name,param.name, startDate, endDate, tjType, tjName);
                        }

                        myChart.on("click", eConsole);
                    }
                }
            }
        });
    }
</script>
</body>
<!-- END BODY -->
</html>