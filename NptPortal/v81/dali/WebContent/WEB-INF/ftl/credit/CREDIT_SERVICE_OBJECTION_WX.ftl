<#include "CommonUtil.ftl"/>
<#assign flag = _FLAG>
<#assign flagName = _FLAG_NAME>
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/comm.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/style.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/main.css" />
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/style.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<style type="text/css">
    *{padding:0px; margin:0px;list-style: none;font-size:1em;}
    body{
        background-color:#f2f2f2;
        overflow: scroll;
        /*overflow-x: hidden;*/
        /*overflow-y: hidden;*/
    }
    .con_top{
        margin-top:2em;
        margin-bottom:5em;
    }
    .con_top li.row{
        background-color:#fff;
        margin-bottom:4em;
    }

    .gover{
        /*width:90%;*/
        margin:0 auto;
        border-bottom:1px solid #ccc;
        line-height: 6em;
        height:6em;
    }
    .gover span{
        display:inline-block;
        height:100%;
        text-align: left;
    }
    .gover input[type=text]{
        height:100%;
        border:none;
        outline:none;
        text-align: right;
        white-space: nowrap;
    }
    .table-responsive{
        background-color:#fff;
    }
    .table-responsive th,.table-responsive td{
        text-align: center;
        padding:1em 0;
        border-bottom:1px solid #dedede;
    }
    /*.wrap{*/
        /*width: 100px;*/
        /*margin: 0 auto;*/
        /*white-space: nowrap;*/
        /*text-overflow: ellipsis;*/
        /*overflow: hidden;*/
    /*}*/
    .button{
        margin-bottom:1em;
    }
</style>
<#if user??&&user.flag="3">
<div class="con_top_shu">
    <h2>${flagName}</h2>
    <div class="con_center">
        <div class="table-responsive">
        <form method="post" enctype="multipart/form-data" style="margin-bottom:10px;">
            <table border="0" width="100%" cellpadding="0" cellspacing="0" style="table-layout:fixed;word-break:break-all;border-collapse:collapse;">
                <input type="hidden" name="flag" value="${flag}">
                <input type="hidden" name="source" value="${_SOURCE!}">
            <#--<#if user??>-->
                <tr>
                    <td ><span>*</span> 申请人姓名：</td>
                    <td style="word-wrap:break-word;">
                        <input  type="text" value="${user.username}" disabled>
                    </td>
                </tr>
                <tr>
                    <td><span>*</span> 身份证号码：</td>
                    <td style="word-wrap:break-word;">
                        <input type="text" value="${user.idCard!}" disabled>
                    </td>
                </tr>
                <tr>
                    <td><span>*</span> 联系电话：</td>
                    <td style="word-wrap:break-word;">
                        <input type="text" name="tel" value="${user.mobile!}" required>
                    </td>
                </tr>
                <tr>
                    <td><span>*</span> 联系邮箱：</td>
                    <td style="word-wrap:break-word;">
                        <input type="text" name="email" value="${user.email!}" required>
                    </td>
                </tr>
            <#--</#if>-->
                <tr>
                    <td><span>*</span> 详细描述：</td>
                    <td>
                        <textarea name="text"  placeholder="请详细说明异议或投诉企业/个人、统一信用代码/身份证号，以及内容。"
                                  required></textarea>
                    </td>
                </tr>
                <tr>
                    <td>附件上传</td>
                    <input type="hidden" name="attach" value="">
                    <td>
                        <span id="spanButtonPlaceHolder"></span>
                        <input class="cancel" id="btnCancel" type="button" value="取消" onclick="swfu.cancelQueue();"
                               disabled="disabled">
                        <div id="fsUploadProgress">
                            <p>您可以上传投诉对象的截图或证据，格式为jpg/png/gif或者zip/rar，大小不超过2M。</p>
                    </td>
                </tr>
            </table>

            <li class="button">
                <input class="btn btn-primary btn-block"  type="submit"   value="提交">
            </li>
            <ul>
                <li>投诉须知：</li>
                <li>1.请您保证所投诉的内容与事实一致。</li>
                <li>2.请您尽可能填写详实内容，以利于您的投诉问题的解决。</li>
            </ul>
        </form>
        </div>
    </div>
</div>
<#--<#elseif user.flag=1>-->
<#--<div>-->
    <#--未实名认证-->
<#--</div>-->
<#--<#elseif user.flag=2>-->
<#--<div>-->
    <#--实名认证中-->
<#--</div>-->
<#--<#elseif user.flag=3>-->
<#--<div>-->
    <#--实名认证失败-->
<#--</div>-->
<#elseif user??&&user.flag="2" >
<div>
    实名认证中
</div>
<#elseif user??&&user.flag="4" >
<div>
    实名认证失败
</div>
<#elseif user??&&user.flag="1" >
<div>
    未进行实名认证
</div>
<#else >
  <div>
     请先<a href="http://npt.s1.natapp.cc/common/getOpenIdUrl.jspx?rediretUrl=http://npt.s1.natapp.cc/login.jspx">登录！</a>登陆后才可以进行操作！
  </div>
</#if>

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

        $("form").submit(function () {
            $.ajax({
                type: "POST",
                url: "/credit/srv/objection/add.do",
                data: $("form").serializeArray(),
                success: function (data) {
                    if (data == "success") {
                        alert("提交成功");
                        creditService($(".search_list .nav_on a").attr("name"));
                    }
                }
            });
            event.preventDefault();
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