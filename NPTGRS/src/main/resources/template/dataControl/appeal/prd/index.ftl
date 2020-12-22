<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
</head>
<body class="page-header-fixed">


<div class="container-fluid">
    <!-- BEGIN PAGE HEADER-->
    <!-------------------------------------异议处理-------------------------------------------->
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>异议处理</div>
                </div>
                <div class="portlet-body form">
                    <form action="listPRDByCondition.action" onsubmit="return appealFilter(this)" class="form-horizontal">
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label for="" class=" control-label">
                                        信息类名称：</label>
                                    <div class=" controls">
                                        <input type="text" name="dataTypeTitle" class="m-wrap "></div>
                                </div>
                            </div>
                            <div class="span6">
                                <label for="" class=" control-label">审核状态：</label>
                                <div class=" controls">
                                    <select name="appealStatus" class=" m-wrap">
                                        <option value="">全部</option>
                                        <option value="RAS_PROCESSING">待审核</option>
                                        <option value="RAS_ACCEPTED">审核通过</option>
                                        <option value="RAS_REFUSED">审核拒绝</option>
                                        <option value="RAS_EXPIRED">审核过期</option>
                                    </select>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn blue"><i class="icon-search icon-white"></i>&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询
                            </button>
                            <button type="reset" class="btn"><i class="icon-repeat"></i>&nbsp;&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置
                            </button>
                        </div>
                    </form>
                </div>
                <div class="portlet-body">
                    <table class="table table-striped table-bordered table-hover" id="pageData">
                        <thead>
                        <tr>
                            <th width="5%">序号</th>
                            <th width="10%">申请人</th>
                            <th width="10%">联系电话</th>
                            <th width="15%">异议信息类名称</th>
                            <th width="15%">信息源单位</th>
                            <th width="15%">申请时间</th>
                            <th width="10%">状态</th>
                            <th width="10%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#include "list.ftl">
                        </tbody>


                    </table>
                    <div id="pagination"></div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="detailDiv" class="modal hide fade" tabindex="-1" data-width="1200">
</div>


<script>
    pagination.initPagination("listPRDByCondition.action");
    /**
     * 打开审批界面
     */
    function showAppeal(appealNo) {
        $.ajax({
            url: "showPRDAppeal.action",
            data: {primaryKeyValue: appealNo},
            timeout: 30000,
            beforeSend: function () {
                $(".loadDiv").show();
            },
            success: function (data) {
                $("#detailDiv").modal("show").html(data);
                $(".loadDiv").hide();
            },
            error: function () {
                $(".loadDiv").hide();
                returnInfo(false, "操作失败！");
            }
        });
    }
    /*
   * 审批
   * */
    function handleAppeal(form) {
        handleAppealFields();
        $.post(form.action, $(form).serialize(), function (data) {
            if (data.result) {
                $("#detailDiv").modal("hide");
                refreshAppealList();
                returnInfo(true, data.message || "操作成功！");
            }
            else {
                returnInfo(false, data.message || "操作失败！");
            }
        });
        return false;
    }
    /**
     * 刷新列表
     */
    function refreshAppealList() {
        $.post("listPRDByCondition.action",{
            dataTypeTitle:$("input[name='dataTypeTitle']").val(),
            appealStatus:$("select[name='appealStatus']").val()
        }, function (data) {
            $("#pageData tbody").html(data);
            pagination.initPagination("listPRDByCondition.action",{
                dataTypeTitle:$("input[name='dataTypeTitle']").val(),
                appealStatus:$("select[name='appealStatus']").val()
            });
        });
    }
    /**
     * 筛选审核
     */
    function appealFilter(form) {
        $.post(form.action, $(form).serialize(), function (data) {
            $("#pageData tbody").html(data);
            pagination.initPagination(form.action,{
                dataTypeTitle:$("input[name='dataTypeTitle']").val(),
                appealStatus:$("select[name='appealStatus']").val()
            });
        });
        return false;
    }
    /**
     * 处理异议信息
     */
    function handleAppealFields(){
        var data = [];
        var data2 = [];
        $("input[type='radio']:checked").each(function () {
            var field = {};
            var field2 = {}
            if ($(this).val() == 1){
                var trId = $(this).attr("name").slice(12);
                field.id = trId;
                field.result = $("#tr-"+trId).find("input[type='text']").val();
                data.push(field);
            }else {
                var trId = $(this).attr("name").slice(12);
                field2.id = trId;
                field2.result = $("#tr-"+trId).find("input[type='text']").val();
                data2.push(field2);
            }
        });
        $("input[name='passFields']").val(JSON.stringify(data));
        $("input[name='unPassFields']").val(JSON.stringify(data2));
    }
</script>
</body>
<!-- END BODY -->

</html>