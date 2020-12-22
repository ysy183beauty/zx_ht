<#include "/template/Credit_Common/common.ftl">
<style>
    .form-horizontal .row-fluid .controls input, .form-horizontal .row-fluid .controls select .m-wrap{
        width:auto!important;
        margin-left:20px;
    }
    .sliderbox li{
        width:22%!important;
        margin-right:2%;
    }
</style>
<#assign user = _USER>
<div class="portlet-body form">
    <form class="form-horizontal">
        <input type="hidden" name="data.id" value="${user.id}">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
            <#--<h4>回复</h4>-->
        </div>
        <div class="modal-body">
            <div id="userInfo">

            </div>
            <div class="row-fluid">
                <div class="portlet box boxTheme">
                    <div class="portlet-title">
                        <label onclick="triggerTb('3')">
                        <span class="caption">
                            <i class="icon-file-text"></i>
                            认证照片
                        </span>
                        </label>
                        <div class="tools">
                            <a href="javascript:;"  class="collapse collapse3 expand"></a>
                        </div>
                    </div>
                    <div class="portlet-body ">
                        <ul class="sliderbox">
                        <#list user.mapFile?keys as key>
                            <#if user.mapFile[key]??>
                                <li>
                                    <div><a><img src='data:image/gif;base64,${user.mapFile[key]}'></a></div>
                                </li>
                            <#else>
                                <li>
                                    <div><a><img src='data:image/png;base64,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'></a>
                                    </div>
                                </li>
                            </#if>
                        </#list>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <label onclick="triggerTb('4')">
                        <span class="caption">
                            <i class="icon-file-text"></i>
                            认证信息
                        </span>
                    </label>
                    <div class="tools">
                        <a href="javascript:;"  class="collapse collapse4 expand"></a>
                    </div>
                </div>
                <div class="portlet-body ">
                    <div class="row-fluid">
                        <div class="span6">
                            <div class="control-group">
                                <label class="ow control-label" title="认证状态">认证状态:</label>
                                <div class="ow controls">
                                <#list _USER_FLAG as d>
                                    <label class="radio">
                                        <input id="data_flag_${d.code}" class="m-wrap" name="data.flag" type="radio"
                                               value="${d.code}"
                                            <#if d?is_first>
                                               checked="checked"
                                            </#if>
                                               <#if user.syncStatus != 0>disabled</#if>
                                        >${d.title}
                                    </label>
                                </#list>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row-fluid">
                        <div class="">
                            <div class="control-group">
                                <label class="ow control-label" title="认证失败原因">认证失败原因:</label>
                                <div class="ow controls">
                            <textarea class="large m-wrap" rows="3" style="margin-left: 20px; width: 90%!important; height: 100px;"
                                      <#if user.syncStatus != 0>disabled</#if>
                                      name="data.failComment">${user.failComment!}</textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div class="modal-footer">
<#if user.syncStatus == 0>
            <button type="submit" class="btn blue">&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
</#if>
            <button type="button" data-dismiss="modal" class="btn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
        </div>
    </form>
</div>

<script>
    $(function () {
    <#if user.flag??>
        $("#data_flag_${user.flag}").attr("checked", true);
    </#if>
    });

    $("form").submit(function () {
        event.preventDefault ? event.preventDefault() : (event.returnValue = false);
        $.ajax({
            type: "POST",
            url: "edit.${urlExt}",
            data: $("form").serializeArray(),
            success: function (data) {
                if (data.result) {
                    returnInfo(true, data.message || "操作成功！");
                    $("#modalDiv").modal("hide");
                    $("form")[0].reset();
                    syncResponse();
                    pagination.refreshPage();
                } else {
                    returnInfo(false, data.message || "操作失败！");
                }

            }
        });
    });
</script>