<!DOCTYPE html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
    <style>
.sliderbox li{
    display: inline-block;
}
    </style>
</head>
<body class="page-header-fixed">

<div>
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption">
                <i class="icon-file"></i>
                实名认证
            </div>
            <div class="tools">
                系统将在<span id="hours">2</span>小时<span id="minutes">0</span>分钟后自动刷新 <a type="button" class="btn yellow mini" onclick="syncResponse();sync();"><i class="icon-download-alt"></i>立即刷新</a>
            </div>
        </div>
        <div class="portlet-body form">
            <div class="form-horizontal">
                <div class="row-fluid">
                    <div class="span4">
                        <div class="control-group">
                            <label class="ow control-label">处理状态：</label>
                            <div class="ow controls">
                                <select name="syncStatus" id="syncStatus" class="small">
                                    <option value="">全部</option>
                                    <option value="0">未认证</option>
                                    <option value="1">已认证</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn blue" onclick="search()"><i class="icon-search icon-white"></i>&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询
                    </button>
                <#--<button type="reset" class="btn"><i class="icon-repeat"></i>&nbsp;&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置-->
                <#--</button>-->
                </div>
            </div>
        </div>
        <div class="portlet-body form">
            <div class="widget-content nopadding" id="indexPage">
                <table class="table table-bordered data-table dataTable" id="pageData">
                    <thead>
                    <tr>
                        <th style="width: 5%;">序号</th>
                        <th style="width: 10%;">信用体类型</th>
                        <th style="width: 35%;">信用体名称</th>
                        <th style="width: 15%;">信用体电话</th>
                        <th style="width: 15%;">信用体证件号码</th>
                        <th style="width: 10%;">状态</th>
                        <th style="width: 10%;">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#include "list.ftl">
                    </tbody>
                </table>
                <div id="pagination"></div>
            </div>
            <div id="formDiag" class="widget-content nopadding"></div>
        </div>
    </div>
</div>
<div id="modalDiv" class="modal hide fade " tabindex="-1" data-width="1000">
</div>
<#include "../cms_tools.ftl">
<script type="text/javascript">
    function search(){
        $.ajax({
            type:"post",
            url:"list.${urlExt}",
            data:{
                pageSize: $("#pageSize").val(),
                syncStatus:$("#syncStatus").val(),
                currPage : 1
            },
            success:function(data){
                $("#pageData tbody").html(data);
                pagination.initPagination("list.${urlExt}", {pageSize: $("#pageSize").val(),syncStatus:$("#syncStatus").val(), currPage : 1});
            }
        });
    }
    $(function(){
    });

    function showSecurityFunction(){
    <#list Session["_curentMenuFunctions"] as funcName>
        $(".${funcName}But").show();
    </#list>
    }
    pagination.initPagination("list.${urlExt}", {pageSize: $("#pageSize").val()});

    function edit(id, html) {
        $(".loadDiv").show();

        $.post("editPage.${urlExt}",{id:id},function (data) {
            $(".loadDiv").hide();
            $("#modalDiv").html(data);
            $("#userInfo").html(html);

            //隐藏申请查看按钮
            var objs = $(".tools a");
            for (var i = 0, len = objs.length; i < len; i++) {
                if ($(objs[i]).text().includes("申请查看")) {
                    $(objs[i]).hide();
                }
            }
//            $(".tools a:first-child").hide();
//            $(".tools a:last-child").show();

            $(".loadDiv").hide();
            $("#modalDiv").modal("show");
        })
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/30 下午02:35
     * 备注: 进行实名认证
     */
    function showUserInfo(type, idCard, userId) {
        $(".loadDiv").show();
        $.ajax({
            url: "${ctx}/npt/grs/query/custom/rnAuthLoader.${urlExt}",
            timeout: 30000,
            data: {
                primaryKeyValue: idCard,
                _rn_type: type === "person" ? "BMHG_RNAUTH_PS" : "BMHG_RNAUTH_BS"
            },
            success: function (data) {
                edit(userId, data);
            }
        })
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/30 下午02:36
     * 备注: 同步
     */
    function sync() {
        console.log("sync");
        $.post("${ctx}/npt/grs/scheduler/outside/creditCmsUser/sync.${urlExt}",{},function () {
            pagination.refreshPage();
        })
    }
    function syncResponse() {
        console.log("syncResponse");
        $.post("${ctx}/npt/grs/scheduler/outside/creditCmsUser/syncResponse.${urlExt}",{},function () {
//            pagination.refreshPage();
        })
    }
</script>
</body>
</html>