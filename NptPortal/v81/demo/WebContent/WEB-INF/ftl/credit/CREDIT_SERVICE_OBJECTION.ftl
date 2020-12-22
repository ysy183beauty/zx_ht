<#include "CommonUtil.ftl"/>
<#if (userExt.flag)?? && (userExt.flag == "3")>
<#assign flag = _FLAG>
<#assign flagName = _FLAG_NAME>
<div class="con_top_shu">
    <h2>${flagName}</h2>
    <div class="con_center">
        <form method="post" enctype="multipart/form-data" style="margin-bottom:10px;">
            <table border="0">
                <input type="hidden" name="flag" value="${flag}">
            <#if user??>
            <tbody style="overflow: hidden; zoom: 100%;">
                <tr>
                    <td>
                        <span>*</span>
                        <#if userExt.type == "person">
                            申请人姓名：
                        <#elseif userExt.type == "company">
                            企业名称：
                        </#if>
                   </td>
                    <td>
                        <input type="text" value="${userExt.realname}" disabled>
                    </td>
                </tr>
                <tr>
                    <td>
                        <span>*</span>
                        <#if userExt.type == "person">
                            身份证号码：
                        <#elseif userExt.type == "company">
                            工商注册号：
                        </#if></td>
                    <td>
                        <input type="text" value="${user.idCard!}" disabled>
                    </td>
                </tr>
                <tr>
                    <td><span>*</span> 联系电话：</td>
                    <td>
                        <input type="text" name="tel" value="${user.mobile!}" required>
                    </td>
                </tr>
                <tr>
                    <td><span>*</span> 联系邮箱：</td>
                    <td>
                        <input type="text" name="email" value="${user.email!}" required>
                    </td>
                </tr>
            </#if>
                <tr>
                    <td><span>*</span> 标题：</td>
                    <td>
                        <input type="text" name="title" value="" required>
                    </td>
                </tr>
                <#if flag == "0">
                    <tr>
                        <td><span>*</span> 来源：</td>
                        <td>
                            <input type="text" name="source" value="${_SOURCE!}">
                        </td>
                    </tr>
                <#else>
                    <input type="hidden" name="source" value="${_SOURCE!}">
                </#if>
                <tr>
                    <td><span>*</span> 详细描述：</td>
                    <td>
                        <#if flagName=="异议处理">
                            <textarea name="text"  placeholder="请详细说明异议企业/个人、统一信用代码/身份证号，以及异议内容。"
                              class="textarea" required></textarea>
                        <#else>
                            <textarea name="text"  placeholder="请详细说明投诉企业/个人、统一信用代码/身份证号，以及投诉内容。"
                                      class="textarea"   required></textarea>
                        </#if>
                    </td>
                </tr>
                <tr>
                    <td>附件上传：</td>
                    <input type="hidden" name="attach" value="">
                    <td>
                        <span id="spanButtonPlaceHolder"></span>
                        <input class="cancel" id="btnCancel" type="button" value="取消" onclick="swfu.cancelQueue();"
                               disabled="disabled">
                        <div id="fsUploadProgress"></div>
                            <p>您可以上传投诉对象的截图或证据，格式为jpg/png/gif或者zip/rar，大小不超过2M。</p>
                    </td>
                </tr>
            </tbody>
            </table>

            <div class="button">
                <input type="submit" class="btn" value="提交">
            </div>
            <ul>
                <li>投诉须知：</li>
                    <#if flagName=="异议处理">
                        <li>1.请您保证所提的异议内容与事实一致。</li>
                        <li>2.请您尽可能填写详实内容，以利于您的异议申请的解决。</li>
                    <#else>
                        <li>1.请您保证所投诉的内容与事实一致。</li>
                        <li>2.请您尽可能填写详实内容，以利于您的投诉问题的解决。</li>
                    </#if>
            </ul>
        </form>
    </div>

</div>

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
        if(jsessionid) {
            uploadUrl += ";jsessionid="+jsessionid;
        }
        swfu=new SWFUpload({
            upload_url : uploadUrl,
            flash_url : "/thirdparty/swfupload/swfupload.swf",
            file_size_limit : "2 MB",
            file_types : "*.jpg;*.png;*.gif;*.zip;*.rar",
            file_types_description : "All Files",
            file_queue_limit : 1,
            file_upload_limit : 1,
            custom_settings : {
                progressTarget : "fsUploadProgress",
                cancelButtonId : "btnCancel"
            },
            debug: false,

            button_image_url : "/res/common/img/theme/menu_search.jpg",
            button_placeholder_id : "spanButtonPlaceHolder",
            button_text: "<span class='btnText'>上传文件</span>",
            button_width: 52,
            button_height: 19,
            button_text_top_padding: 2,
            button_text_left_padding: 0,
            button_text_style: '.btnText{color:#666666;}',

            file_queued_handler : fileQueued,
            file_queue_error_handler : fileQueueError,
            file_dialog_complete_handler : fileDialogComplete,
            upload_start_handler : uploadStart,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess2,
            upload_complete_handler : uploadComplete,
            queue_complete_handler : queueComplete
        });

        $("form").submit(function (event) {
            $.ajax({
                type: "POST",
                url: "/credit/srv/objection/add.do",
                data: $("form").serializeArray(),
                success: function (data) {
                    if (data == "success") {
                        alert("提交成功");
                        creditService($(".search_list .active a").attr("name"));
                    }
                }
            });
            if(document.all){ //判断IE浏览器
                window.event.returnValue = false;
            }
            else{
                event.preventDefault();
            };
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
</script>
<#else>
请先实名认证
</#if>