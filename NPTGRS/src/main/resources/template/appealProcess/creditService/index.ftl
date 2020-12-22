<!DOCTYPE html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
<style>
    textarea {
        width: 98%;
    }
</style>
</head>
<body class="page-header-fixed">

<div>
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption">
                <i class="icon-file"></i>
            <#if flag??>
                门户异议受理
            <#else>
                信用咨询及投诉受理
            </#if>
            </div>
            <div class="tools">
                系统将在<span id="hours">2</span>小时<span id="minutes">0</span>分钟后自动刷新 <a type="button" class="btn yellow mini" onclick="syncResponse();sync();"><i class="icon-download-alt"></i>立即刷新</a>
            </div>
        </div>
        <div class="portlet-body form">
            <form action="list.${urlExt}" class="form-horizontal" onsubmit="return query(this)" id="searchForm">
                <input type="hidden" name="flag" value="${flag!}">
                <div class="row-fluid">
                    <div class="span4">
                        <div class="control-group">
                            <label class="ow control-label">处理状态：</label>
                            <div class="ow controls">
                                <select name="syncStatus" class="small">
                                    <option value="">全部</option>
                                    <option value="0">待回复</option>
                                    <option value="1">已回复</option>
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
            <div class="widget-content nopadding">
                <table class="table table-bordered data-table dataTable" id="pageData">
                    <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th width="10%">用户类型</th>
                        <th width="15%">用户名称</th>
                        <th width="10%">服务类型</th>
                        <th width="10%">创建时间</th>
                        <th width="5%">状态</th>
                        <th width="5%">操作</th>
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
<div id="modalDiv" class="modal hide fade " tabindex="-1" data-width="760">
    <form class="form-horizontal" id="responseForm">
    </form>
</div>
<#include "../cms_tools.ftl">
<script type="text/javascript">
    $(function(){
        $("#searchForm").submit();
    });

    /**
     * 作者: 张磊
     * 日期: 2017/04/01 下午10:42
     * 备注: 编辑
     */
    $("#modalDiv").on('show',function () {
        $("#responseForm").empty();
        $.ajax({
            url:"editPage.${urlExt}",
            data:{id:$(event.target).closest('a').data('id')},
            timeout: 30000,
            beforeSend: function () {
                $(".loadDiv").show();
            },
            success:function (data) {
                $(".loadDiv").hide();
                $("#responseForm").html(data);
            },
            error: function () {
                $(".loadDiv").hide();
                returnInfo(false, "操作失败！");
            }
        });
    });

    /**
     * 作者: 张磊
     * 日期: 2017/04/07 下午05:00
     * 备注: 查询
     */
    function query(form) {
        $.ajax({
            type: "post",
            url: form.action,
            data: $(form).serialize(),
            timeout: 30000,
            beforeSend: function () {
                $(".loadDiv").show();
            },
            success: function (data) {
                $(".loadDiv").hide();
                $("#pageData tbody").html(data);
                pagination.initPagination(form.action, objectifyForm($(form).serializeArray()));
            },
            error: function () {
                $(".loadDiv").hide();
                returnInfo(false, "操作失败！");
            }
        });
        return false;
    }

    $("#responseForm").submit(function () {
        event.preventDefault ? event.preventDefault() : (event.returnValue = false);
        $.ajax({
            type: "POST",
            url: "edit.${urlExt}",
            data: $("#responseForm").serialize(),
            success: function (data) {
                if (data.result) {
                    returnInfo(true, data.message || "操作成功！");
                    $("#modalDiv").modal("hide");
                    $("#responseForm")[0].reset();
                    syncResponse();
                    pagination.refreshPage();
                } else {
                    returnInfo(false, data.message || "操作失败！");
                }
            }
        });
    });

    function downloadAttach(id) {
        window.open("downloadAttach.${urlExt}?id=" + id);
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/30 下午08:06
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