<!DOCTYPE html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
<style>
td .alert{
    padding: 0;
    margin-bottom: 0;
}
</style>
</head>
<body class="page-header-fixed">

<div>
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption">
                <i class="icon-file"></i>
                门户用户申请记录
            </div>
            <div class="tools">
                系统将在<span id="hours">2</span>小时<span id="minutes">0</span>分钟后自动刷新 <a type="button" class="btn yellow mini" onclick="syncResponse();sync();"><i class="icon-download-alt"></i>立即刷新</a>
            </div>
        </div>
        <div class="portlet-body form">
            <form id="searchForm" action="list.${urlExt}" class="form-horizontal">
                <div class="row-fluid">
                    <div class="span4">
                        <div class="control-group">
                            <label class="ow control-label">处理状态：</label>
                            <div class="ow controls">
                                <select name="syncStatus" class="small">
                                    <option value="">全部</option>
                                    <option value="1">申请成功</option>
                                    <option value="2">申请处理中</option>
                                    <option value="0">申请失败</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn blue"><i class="icon-search icon-white"></i>&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询
                    </button>
                </div>
            </form>
        </div>
        <div class="portlet-body">
            <table class="table table-bordered data-table dataTable" id="pageData">
                <thead>
                <tr>
                    <th style="width: 5%;">序号</th>
                    <th style="width: 10%;">信用体类型</th>
                    <th style="width: 35%;">信用体名称</th>
                    <th style="width: 15%;">信用体证件号码</th>
                    <th style="width: 15%;">申请时间</th>
                    <th style="width: 15%;">最后处理时间</th>
                    <th style="width: 10%;">处理结果</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
            <div id="pagination"></div>
        </div>
    </div>
</div>
<#include "../cms_tools.ftl">
<script type="text/javascript">
    $("#searchForm").submit(function (event) {
        event.preventDefault ? event.preventDefault() : (event.returnValue = false);
        var queryData = objectifyForm($(this).serializeArray());
        queryData.pageSize = $("#pageSize").val();
        queryData.currPage = 1;
        $.ajax({
            type: "post",
            url: "list.${urlExt}",
            data: queryData,
            timeout: 30000,
            beforeSend: function () {
                $(".loadDiv").show();
            },
            success: function (data) {
                $(".loadDiv").hide();
                $("#pageData tbody").html(data);
                pagination.initPagination("list.${urlExt}", queryData, showSecurityFunction);
            },
            error: function () {
                $(".loadDiv").hide();
                returnInfo(false, "操作失败！");
            }
        });
    });

    $(function(){
        $("#searchForm").submit();
    });

    function showSecurityFunction(){
    <#list Session["_curentMenuFunctions"] as funcName>
        $(".${funcName}But").show();
    </#list>
        App.handleTooltips();
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/30 下午02:36
     * 备注: 同步
     */
    function sync() {
        $.post("sync.${urlExt}",{},function () {
            pagination.refreshPage();
        })
    }
    function syncResponse() {
        $.post("syncResponse.${urlExt}",{},function () {
//            pagination.refreshPage();
        })
    }
</script>
</body>
</html>