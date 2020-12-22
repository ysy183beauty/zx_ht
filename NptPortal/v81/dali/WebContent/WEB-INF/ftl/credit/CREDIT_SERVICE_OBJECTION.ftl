<#include "CommonUtil.ftl"/>
<link href="/thirdparty/alertify.js/themes/alertify.core.css" rel="stylesheet">
<link href="/thirdparty/alertify.js/themes/alertify.bootstrap.css" rel="stylesheet">
<link href="/thirdparty/fine-uploader/fine-uploader-new.css" rel="stylesheet">
<style>
    .qq-uploader DIALOG[open]{
        left:600px;
    }
</style>
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
                        <div id="fine-uploader">文件上传</div>
                        <p>您可以上传投诉对象的截图或证据，格式为jpg/png/gif或者zip/rar，大小不超过2M。</p>
                        <p style="color:red">一次只能上传一份文件</p>
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
<#include "include/fileUpload.ftl">
<script>
    $("form").submit(function (event) {
        $.ajax({
            type: "POST",
            url: "/credit/srv/objection/add.do",
            data: $("form").serializeArray(),
            success: function (data) {
                if (data == "success") {
                    alertify.success("提交成功");
                    $("form")[0].reset();
                    $("input[name='attach']").val("");
                    $("#fine-uploader").fineUploader('reset');
                } else {
                    alertify.error("提交失败");
                }
            }
        });
        if (document.all) { //判断IE浏览器
            window.event.returnValue = false;
        }
        else {
            event.preventDefault();
        }
    });

</script>
<#else>
请先实名认证
</#if>