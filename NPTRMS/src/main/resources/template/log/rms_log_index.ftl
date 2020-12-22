<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
</head>
<body class="page-header-fixed">

<div class="container-fluid">

    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme" id="listPage">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>系统日志管理</div>
                </div>
                <div class="portlet-body form">
                    <form action="list.${urlExt}" role="form" class="form-horizontal" onsubmit="return query(this)">
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label class="ow control-label">单位名称</label>
                                    <div class="ow controls">
                                        <select class="span6 m-wrap" tabindex="1" name="orgId">
                                            <option value="">全部</option>
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
                                    <label class="ow control-label">用户名</label>
                                    <div class="ow controls">
                                        <input class="span6 m-wrap" type="text" name="userName">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label class="ow control-label">操作名称</label>
                                    <div class="ow controls">
                                        <input class="span6 m-wrap" type="text" name="actionName">
                                    </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="control-group">
                                    <label class="ow control-label">时间段选择</label>
                                    <div class="ow controls demo">
                                        <i class="icon-calendar"></i>
                                        <input id="form-date-range" class="span6"  type="text">
                                        <input type="hidden" name="startDate" value="${.now?string("yyyyMMdd")}">
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
                <div class="portlet-body ">
                    <table class="table table-bordered table-hover" id="pageData">
                        <thead>
                        <tr>
                            <th width="8%">序号</th>
                            <th>机构名</th>
                            <th>用户名</th>
                            <th>登录名</th>
                            <th>操作功能</th>
                            <#--<th>IP</th>-->
                            <th>操作时间</th>
                            <th>操作结果</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div id="pagination"></div>
            </div>
            <div id="detailPage"></div>
        </div>
    </div>
</div>
<div id="modalDiv" class="modal hide fade " tabindex="-1" data-width="1000">
    <div class="modal-body">
        <div class="row-fluid">
            <div id="detail"></div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>
</div>

<script>
    $(function () {
        App.initUniform();
        $('#form-date-range').daterangepicker({
            opens:'left',
            startDate: Date.today(),
            endDate: Date.today()
        }, function (start, end,label) {
            $("input[name='startDate']").val(start.format('YYYYMMDD'));
            $("input[name='endDate']").val(end.format('YYYYMMDD'));
        });

        $("form").submit();

        $.fn.modal.defaults.maxHeight = function(){
            // subtract the height of the modal header and footer
            return $(window).height() - 165;
        };

        $("#modalDiv").on('show',function () {
            $("#detail").empty();
            $.ajax({
                url:'detail.${urlExt}',
                data:{
                    id: $(event.target).closest('a').data('id')
                },
                success: function (data) {
                    $("#detail").html(data);
                }
            });
        })
    });

    function query(form) {
        $.ajax({
            url:form.action,
            data:$(form).serialize(),
            timeout:30000,
            success:function (data) {
                $("#pageData tbody").html(data);
                pagination.initPagination(form.action, objectifyForm($(form).serializeArray()));
            }
        });
        return false;
    }
</script>
</body>
</html>