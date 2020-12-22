<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
    <script type="text/javascript" src="${wctx}/pub/statistics/js/dataFastSelecter.js"></script>
</head>
<body class="page-header-fixed">

<div class="container-fluid">

    <!-------------------------------------信息入库总量统计-------------------------------------------->
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme" id="listPage">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>信息入库总量统计</div>
                </div>
                <div class="portlet-body form">
                    <form action="list.action"  role="form" class="form-horizontal" onsubmit="return query(this)">
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label class="ow control-label">单位名称</label>
                                    <div class="ow controls">
                                        <select class=" m-wrap" tabindex="1" name="orgId">
                                            <option value="-1">全部</option>
                                        <#if _ORG_LIST??>
                                            <#list _ORG_LIST as c>
                                                <option value="${c.id?string("#")}">${c.orgName}</option>
                                            </#list>
                                        </#if>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="control-group">
                                    <label for="" class="ow control-label"> 主体</label>
                                    <div class="ow controls">
                                        <select class=" m-wrap" tabindex="1" name="subId">
                                            <option value="-1">全部</option>
                                            <option value="106101">企业数据</option>
                                            <option value="106102">个人数据</option>
                                        </select></div>
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
                                        <input id="form-date-range" class="small"  type="text">
                                        <input type="hidden" name="startDate" value="20160101">
                                        <input type="hidden" name="endDate" value="${.now?string("yyyyMMdd")}">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn blue" ><i class="icon-search icon-white"></i>查询</button>
                            <button type="reset" class="btn"><i class="icon-repeat"></i>重置</button>
                        </div>
                    </form>
                </div>
                <div id="list" class="portlet-body ">
                <#include "sts_data_list.ftl">
                </div>
                <div class="form-actions">
                    <button type="button" onclick="backIndex()" class="btn blue"><i class="icon-arrow-left icon-white"></i>返回</button>
                </div>
            </div>
            <div id="detailPage"></div>
        </div>
    </div>
    <!-- END PAGE HEADER-->
</div>
<!-- END PAGE CONTAINER-->
<script>
    $(function () {
        App.initUniform();
        $('#form-date-range').daterangepicker({
            opens:'left',
            startDate: Date.parse("2016 01 01"),
            endDate: Date.today()
        }, function (start, end,label) {
            $("input[name='startDate']").val(start.format('YYYYMMDD'));
            $("input[name='endDate']").val(end.format('YYYYMMDD'));
        });
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
    function showDetal(data) {
        $("#detailPage").html(data);
        $("#listPage").hide();
        $("#detailPage").show();
    }
</script>
</body>
<!-- END BODY -->
</html>