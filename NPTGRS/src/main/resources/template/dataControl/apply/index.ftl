<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
</head>
<body class="page-header-fixed">
<#assign data = dataPagination>
<input id="totalCount" value="${data.totalCount!}" type="hidden">
<input id="totalPage" value="${data.totalPage!}" type="hidden">
<input id="pageSize" value="${data.pageSize!}" type="hidden">

<div class="container-fluid">

    <!-------------------------------------信用信息使用申请-------------------------------------------->
    <div class="row-fluid">
        <div class="">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>信用信息使用申请</div>
                </div>
                <div class="portlet-body form">
                    <form action="listByCondition.action" onsubmit="return filterMyApply(this)" class="form-horizontal">
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label class="control-label">数据源单位</label>
                                    <div class="controls">
                                        <select class="" name="orgId">
                                            <option value="">全部</option>
                                            <#list orgList as c>
                                                <option value="${c.id?string("#")}">${c.orgName!}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="control-group">
                                    <label for="" class="control-label">
                                        信息类名称</label>
                                    <div class="controls">
                                        <input type="text" class=" " name="poolTitle"> </div>
                                </div>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label for="" class="control-label">
                                        申请查看企业/个人</label>
                                    <div class="controls">
                                        <input type="text" class=" " name="businessKey"> </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="control-group">
                                    <label class="control-label">
                                        申请状态</label>
                                    <#--<div class=" controls demo">-->
                                        <#--<i class="icon-calendar"></i>-->
                                        <#--<input id="form-date-range" type="text">-->
                                    <#--</div>-->
                                    <div class=" controls">
                                        <select name="applyStatus" class="">
                                            <option value="">全部</option>
                                            <option value="RAS_PROCESSING">待审核</option>
                                            <option value="RAS_ACCEPTED">审核通过</option>
                                            <option value="RAS_REFUSED">审核拒绝</option>
                                            <option value="RAS_EXPIRED">审核过期</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn blue"><i class="icon-search icon-white"></i>&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询</button>
                            <button type="reset" class="btn"><i class="icon-repeat"></i>&nbsp;&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置</button>
                        </div>
                    </form>
                </div>
                <div class="portlet-body">
                    <#--<div class="clearfix">-->
                        <#--<div class="btn-group pull-left">-->
                            <#--<button class="btn purple dropdown-toggle " data-toggle="dropdown"><i class="icon-cogs"></i> 工具 <i class="icon-angle-down"></i> </button>-->
                            <#--<ul class="dropdown-menu pull-left">-->
                                <#--<li><a href="#"><i class="icon-download-alt"></i> 导出</a></li>-->
                                <#--<li><a href="print.action"><i class="icon-print"></i> 打印</a></li>-->
                                <#--<li><a href="#"><i class="icon-trash"></i> 删除</a></li>-->
                            <#--</ul>-->
                        <#--</div>-->
                    <#--</div>-->
                    <table class="table table-striped table-bordered table-hover" id="pageData">
                        <thead>
                        <tr>
                            <th width="5%">序号</th>
                            <th width="15%">申请查看企业/个人</th>
                            <th width="15%">信息类名称</th>
                            <th width="15%" >信息源单位</th>
                            <th width="15%" >申请时间</th>
                            <th width="10%">状态</th>
                            <th width="10%">是否过期</th>
                            <th width="15%">操作</th>
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
    <!-- END PAGE HEADER-->
</div>
<!-- END PAGE CONTAINER-->

<!-- BEGIN MODAL -->
<div id="remindDiv" class="modal hide fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>申请催办</h4>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <div class="">
                您好，我于2016年5月11日提交的查询大理XX有限公司企业不良信息的申请,
                目前正在等待审核，请尽快进审批理，谢谢！
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">取消</button>
        <button type="button" class="btn blue">确定</button>
    </div>
</div>
<div id="detailDiv" class="modal hide fade" tabindex="-1" data-width="900">

</div>

<div id="deleteDiv" class="modal hide fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>删除申请信息</h4>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <div class="">
                您好，确定删除2016年2月10日提交的查询大理XX有限公司企业基本信息
                的申请吗？
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">取消</button>
        <button type="button" class="btn blue">确定</button>
    </div>
</div>
<!-- END MODAL -->
<script>
    pagination.initPagination("listByCondition.action");
    function showDetail(applyNo){
        $.post("showDetail.action",{primaryKeyValue:applyNo},function (data) {
            $("#detailDiv").modal("show").html(data);

        });

    }
    $(function () {
        $('#form-date-range').daterangepicker({
            opens:'left'
        }, function (start, end,label) {
            $("input[name='applyStartDate']").val(start.format('YYYY-MM-DD'));
            $("input[name='applyEndDate']").val(end.format('YYYY-MM-DD'));
        });
    });
    function filterMyApply(form) {
        $.post(form.action,$(form).serialize(),function (data) {
            $("#pageData tbody").html(data);
            pagination.initPagination(form.action,{
                orgId:$("select[name='orgId']").val(),
                poolTitle:$("input[name='poolTitle']").val(),
                businessKey:$("input[name='businessKey']").val(),
                applyStatus:$("select[name='applyStatus']").val()
            });
        });
        return false;
    }
</script>
</body>
<!-- END BODY -->

</html>