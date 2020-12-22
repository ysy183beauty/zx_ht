<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
</head>
<body class="page-header-fixed">

<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<!-- BEGIN PAGE CONTAINER-->
<div class="container-fluid">
    <!-- BEGIN PAGE HEADER-->
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>我的异议</div>
                </div>
                <div class="portlet-body form">
                    <form action="list.${urlExt}" class="form-horizontal" onsubmit="return query(this)">
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label class="control-label">异议信息企业/个人</label>
                                    <div class="controls">
                                        <input type="text" name="appealBusinessKey" class="m-wrap span12"> </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="control-group">
                                    <label for="" class="control-label">
                                        信息类名称：</label>
                                    <div class="controls">
                                        <input type="text" name="appealDTTitle" class="m-erap span12"> </div>
                                </div>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label class="control-label">信息项</label>
                                    <div class="controls">
                                        <input type="text" name="appealPKTitle" class="m-wrap span12"> </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="control-group">
                                    <label for="" class="control-label">
                                        信息源单位：</label>
                                    <div class="controls">
                                        <select class=" m-wrap" name="appealProviderId">
                                            <option value="">全部</option>
                                        <#if _ORG_LIST??>
                                            <#list _ORG_LIST as c>
                                                <option value="${c.id}">${c.orgName!}</option>
                                            </#list>
                                        </#if>
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
                    <table class="table table-striped table-bordered table-hover" id="pageData">
                        <thead>
                        <tr>
                            <th width="5%">序号</th>
                            <th width="15%">异议信息企业/个人</th>
                            <th width="15%">信息类名称</th>
                            <th width="10%">信息项</th>
                            <th width="10%">信息源单位</th>
                            <th width="10%">申请时间</th>
                            <th width="5%">状态</th>
                            <th width="10%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
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
<div id="detailDiv" class="modal hide fade" tabindex="-1" data-width="1200"></div>

<script>
    $("form").submit();

    /**
     * 作者: 张磊
     * 日期: 2017/04/07 下午02:57
     * 备注:
     */
    function query(form) {
        $.ajax({
            type: "post",
            url: form.action,
            data: $(form).serialize(),
            success: function (data) {
                $("#pageData tbody").html(data);
                pagination.initPagination(form.action, objectifyForm($(form).serializeArray()));
            }
        });
        return false;
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/07 下午03:41
     * 备注: 查看详情
     */
    function showAppeal(appealNo) {
        $.ajax({
            url: "showMyAppeal.${urlExt}",
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
</script>
</body>
<!-- END BODY -->

</html>