<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>信用报告验证</title>
<#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xycx.css"/>
<#--<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/publicity.css"/>-->
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/tableDate.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/qyxycx.css"/>
    <style type="text/css">
        .con_title{
            color:#2e71b8;
            font-size:18px;
        }
        #content table{
            margin-top:20px;
            margin-bottom:30px;
        }
        #msg{
            margin-left:10px;
            color: #c00;
        }
        table{
            text-align: center;
        }

        table thead{
            background-color:rgb(58, 114, 174);
        }
        table thead th{
            text-align: center;
            padding:10px 0;
            color:#fff;
        }
        .p-title{
            text-indent: 2em;
        }
        .confirm-title{
            font-size:18px;
            font-weight:bold;
        }
        .btn-primary{
            background-color: #337ab7;
            border-color: #2e6da4;
            height:auto!important;
        }
        .btn-primary:hover,.btn-primary:focus,.btn-primary:active,.btn-primary:visited {
            color: #fff;
            background-color: #286090;
            border-color: #204d74;
        }
        .modal-dialog{
            margin-top:240px;

        }
        .modal-header .close span{
            font-size:14px;
            color:#999;
        }
        .real-img{
            position:absolute;
            top:200px;
            right:20px;
        }
        .real-img img{
            width:160px;
        }
    </style>
</head>
<body>
<div class="wrap">
    <div id="appealDiv" class="modal fade " tabindex="-1"  role="dialog" >
        <form action="/credit/srv/addAppeal.do" onsubmit="return addAppeal(this)" class=" no-bottom-space"  >
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModal-list"><i class="glyphicon glyphicon-pencil"></i> 信用信息异议申请表</h4>
                    </div>
                    <div class="modal-body">
                        <div class="scroller" data-height="500px">
                            <div class="portlet boxTheme">
                                <div class="portlet-title">
                                    <div class="caption"><i class="glyphicon glyphicon-search"></i> 异议申请表</div>
                                </div>
                                <div class="portlet-body">
                                    <table class="table table-bordered" id="tableInfoList" >

                                    </table>
                                </div>
                            </div>
                            <div class="portlet boxTheme">
                                <div class="portlet-title">
                                    <div class="caption"><i class="glyphicon glyphicon-search"></i> 异议修改建议</div>
                                </div>
                                <div class="portlet-body">
                                    <div class="advice-info">
                                        <span>来源：</span>
                                        <textarea name="source" cols="100" rows="5"></textarea>
                                    </div>

                                <#--<input type="file" name="attachmentDir" class="filestyle" data-classButton="btn btn-primary" data-input="false" data-classIcon="icon-plus" data-buttonText="添加附件">-->
                                    <div class="apply">
                                        <input type="hidden" name="attach" value="">

                                        <span id="spanButtonPlaceHolder"></span>
                                        <input class="cancel btn btn-primary" id="btnCancel" type="button" value="取消" onclick="swfu.cancelQueue();" disabled="disabled" style="display: none">
                                    </div>
                                    <div id="fsUploadProgress"></div>

                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <input type="hidden" name="key" value="${key!}">
                    <#--<input type="hidden" name="orgName" value="${orgInfo.orgName!}">-->
                    <#--<input type="hidden" name="dtTitle" value="${dataTypeInfo.typeName!}">-->
                    <#--<input type="hidden" name="orgId" value="${orgInfo.id}">-->
                        <input type="hidden" name="byPKFieldId" value="${byPKFieldId!}">
                    <#--<input type="hidden" name="appealDTID"  value="${dataTypeInfo.id}">-->
                        <input type="hidden" name="appealFields" >
                        <button type="submit" class="btn btn-primary" ><i class="glyphicon glyphicon-ok"></i> 确认申请</button>
                        <button type="button" class="btn" data-dismiss="modal"><i class="glyphicon glyphicon-remove"></i>
                            &nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
                    </div>
                </div>
            </div>
        </form>

    </div>
    <div class="main">
        <div id="container">
            <div class="real-img">
                <img src="${ctx}/r/cms/www/red/img/xycx/real.PNG" alt="">
            </div>
        <#if _RESULT.code == 0>
            <#assign list = _BEAN.dataList[0]>
            <div class="con_center">
                <div class="center_title">${list.groupTitle}</div>
                <div class=" bg_1">
                    <#if list.blockList?size gt 0>
                        <#list list.blockList as blockList>
                            <h3 class="col-md-9">${blockList.blockInfo.poolTitle}</h3>
                            <#--<div class="error col-md-3" data-toggle="modal" data-target="#appealDiv" >-->
                                <#--<img src="${ctx}/r/cms/www/red/img/sjxq/u87.png" alt=""/>异议-->
                            <#--</div>-->
                            <#if blockList.dataCount gt 0>
                                <#if blockList.dataArray??>
                                    <#if blockList.dataArray?size == 1>
                                        <#list blockList.dataArray as dataArray>
                                            <table class="tab-col" border="1" width="100%" cellpadding="0"
                                                   cellspacing="0" style="border-collapse:collapse;">
                                            <tr>
                                                <#list dataArray.dataArray as array>
                                                    <th width="15%">${array.title}：</th>
                                                    <td width="35%"><span style="word-break:break-all;">${array.value}</span></td>
                                                    <#if array?index%2 ==1>
                                                    </tr>
                                                    <tr>
                                                    </#if>
                                                </#list>
                                                <#if dataArray.dataArray?size%2 ==1>
                                                    <th></th>
                                                    <td></td>
                                                </#if>
                                            </tr>
                                            </table>
                                        </#list>
                                    <#else>
                                        <table class="tab-row table-striped table-hover" border="1" width="100%"
                                               cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
                                            <tr>
                                                <#list blockList.dataArray[0].dataArray as array>
                                                    <th>${array.title}</th>
                                                </#list>
                                            </tr>
                                            <#list blockList.dataArray as dataArray>
                                                <tr>
                                                    <#list dataArray.dataArray as array>
                                                        <td>${array.value}</td>
                                                    </#list>
                                                </tr>
                                            </#list>
                                        </table>
                                    </#if>
                                <#else>
                                    <div class="null">
                                        本内容需企业实名认证后才能查看，共计${blockList.dataCount}条
                                    </div>

                                </#if>
                            <#else>
                                <div class="null">
                                    本平台暂未收录！
                                </div>

                            </#if>
                        </#list>
                    <#else>
                        <div class="no-found">无相关信息</div>
                    </#if>
                </div>
            </div>
        </#if>

        <#--检测结果：${_IS_VALID}-->
        </div>
    </div>

</body>
<script src="/thirdparty/swfupload/swfupload.js"></script>
<script src="/thirdparty/swfupload/swfupload.queue.js"></script>
<script src="/thirdparty/swfupload/fileprogress.js"></script>
<script src="/thirdparty/swfupload/handlers.js"></script>
<link href="/thirdparty/swfupload/process.css" rel="stylesheet"/>
<script>
    var swfu;
    $(function () {
        var uploadUrl = "/credit/srv/file/o_swfupload.do";
        //在firefox、chrome下，上传不能保留登录信息，所以必须加上jsessionid。
        var jsessionid = $.cookie("JSESSIONID");
        if (jsessionid) {
            uploadUrl += ";jsessionid=" + jsessionid;
        }
        swfu = new SWFUpload({
            upload_url: uploadUrl,
            flash_url: "/thirdparty/swfupload/swfupload.swf",
            file_size_limit: "2 MB",
            file_types: "*.jpg;*.png;*.gif;*.zip;*.rar",
            file_types_description: "All Files",
            file_queue_limit: 1,
            file_upload_limit: 1,
            custom_settings: {
                progressTarget: "fsUploadProgress",
                cancelButtonId: "btnCancel"
            },
            debug: false,

            button_image_url: "/res/common/img/theme/menu_search.jpg",
            button_placeholder_id: "spanButtonPlaceHolder",
            button_text: "<span class='btnText'>上传文件</span>",
            button_width: 52,
            button_height: 19,
            button_text_top_padding: 2,
            button_text_left_padding: 0,
            button_text_style: '.btnText{color:#666666;}',

            file_queued_handler: fileQueued,
            file_queue_error_handler: fileQueueError,
            file_dialog_complete_handler: fileDialogComplete,
            upload_start_handler: uploadStart,
            upload_progress_handler: uploadProgress,
            upload_error_handler: uploadError,
            upload_success_handler: uploadSuccess2,
            upload_complete_handler: uploadComplete,
            queue_complete_handler: queueComplete
        });
    });
    function uploadSuccess2(file, serverData) {
        // 设置文件名
        $("input[name='attach']").val(serverData);

        // 以下为 uploadSuccess 的内容
        try {
            var progress = new FileProgress(file, this.customSettings.progressTarget);
            progress.setComplete();
            progress.setStatus("Complete.");
            progress.toggleCancel(false);
        } catch (ex) {
//            this.debug(ex);
        }
    }
    //打开异议申请页面
    $(".error").bind("click",function() {
        var data = $(this).parent().find("table").html();
        $("#tableInfoList").html(data);
        $("#tableInfoList td>span").addClass("col-sm-6");
        $("#tableInfoList td").append("<div class='addDiv'><input type='text' class='addInput' placeholder='请输入异议内容'/></div>");
    });
    //提交异议申请
    function addAppeal(form) {
        if (handelFields()){
            $.post(form.action,$(form).serialize(),function(data){
                if (data.result){
                    $("#appealDiv").modal("hide");
                    returnInfo(true,data.message||"操作成功！");
                    $(form)[0].reset();
                }else {
                    returnInfo(false,data.message||"操作失败！");
                }
            });
        } else {
            returnInfo(false,"请至少提交一个异议信息！");
        }
        return false;
    }
    function returnInfo(flag,data){
        $(".alert-sumbit").stop().show(500);
        if(flag == true){
            $(".alert-sumbit").addClass("alert-success");
            $("#alert-content").html(data);
        }
        else{
            $(".alert-sumbit").addClass("alert-warning");
            $("#alert-content").html(data);
        }
        setTimeout(function () {
            $(".alert-sumbit").hide(500);
        },5000);
    }
    function handelFields(){
        var fields=[];
        $(".addInput").each(function () {
            if($(this).val() != ""){
                var field={};
                field.id = $(this).parent().parent().attr("id");
                console.log($(this).parent());
                field.value = $(this).val();//更改后的信息
                field.defaultValue=  $(this).parent().prev().text();//原信息
                fields.push(field);
            }
        });
        $("input[name='appealFields']").val(JSON.stringify(fields));
        if (fields.length == 0){
            return false
        }
        return true;
    }

</script>
</html>