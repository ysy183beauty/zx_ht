<#include "CommonUtil.ftl"/>
<link href="${ctx}/r/cms/www/red/css/iconfont.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/r/cms/www/red/css/fileUpload.css" rel="stylesheet" type="text/css">
<style type="text/css">
    #content{
        margin-top:10px;
    }
    .con_title{
        color:#2e71b8;
        font-size:18px;
    }
    #content table{
        width:95%;
        margin:0 auto;
        margin-top:20px;
        margin-bottom:10px;
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
    .upload{
        width:95%;
        margin:0 auto;
        margin-bottom:20px;
    }
    .smrz{
        border-bottom:5px solid #0099FF;
        margin-bottom: 20px;
    }
</style>
<#if userExt?? && userExt.flag?? && userExt.flag=="3">
<#if _APPLY_LOG.totalCount gt 0 && _APPLY_LOG.list[0].applyFlag=="3">
    <div class="report-yy">
        <button class="btn btn-warning" onclick="javascript:window.location='/credit/srv/index.do?word=1'"><i class="glyphicon glyphicon-pencil"></i>异议</button>
    </div>
    <iframe id="reportFrame" width="900" height="1200" frameborder=0 src="/ReportServer?reportlet=${(userExt.type=="company")?string("企业","个人")}信用报告.cpt"></iframe>
<#else>
<div id="content">
    <#--实名信息 START-->
        <div class="smrz">
            <div >
                <span class="con_title">您的实名认证信息如下：</span>
                <span id="msg"></span>
            </div>
            <table class=" table-striped table-bordered"  width="100%"  style="border-collapse:collapse;text-align: center;">
                <thead>
                <tr>
                    <#if userExt.type == "person">
                        <th>真实姓名</th>
                        <th>个人身份证号</th>
                    <#elseif userExt.type == "company">
                        <th>企业名称</th>
                        <th>企业工商注册号</th>
                    </#if>
                    <th>手机号码</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${userExt.realname!}</td>
                    <td>${userExt.idCard!}</td>
                    <td>${userExt.mobile!}</td>
                    <td><a data-toggle="modal" data-target="#myModalDate">申请数据</a></td>
                </tr>
                </tbody>
            </table>
            <div class="upload">
                <div id="fileUploadContent" class="fileUploadContent">

                </div>
            </div>
        </div>

    <#--实名信息 END-->

    <#--申请历史 start-->
    <div class="con_title">
        <#if userExt.type == "person">
            个人数据申请历史如下：
        <#elseif userExt.type == "company">
            企业数据申请历史如下：
        </#if>
    </div>
    <table class=" table-striped table-bordered" width="100%" style="border-collapse:collapse;">
        <thead>
        <tr>
            <th align='center'>序号</th>
            <th align='center'>申请提交时间</th>
            <th align='center'>申请状态</th>
        </tr>
        </thead>
        <tbody>
            <#if _APPLY_LOG.totalCount gt 0>
            <#list _APPLY_LOG.list as log>
            <tr>
                <td>${log_index+1}</td>
                <td>${log.applyTime!}</td>
                <td>
                <#list ["申请提交","申请处理中","申请成功","申请失败","申请过期"] as flag>
                    <#if (flag_index+1)?string("#") == log.applyFlag>
                    ${flag}<#break>
                    </#if>
                </#list>
                </td>
            </tr>
            </#list>
            <#else>
            <tr>
                <td colspan="3">暂无申请</td>
            </tr>
            </#if>
        </tbody>
    </table>
    <#--申请历史 end-->
</div>
<#--MODAL START-->
<div class="modal fade" id="myModalDate" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <div class="confirm-title modal-title" id="myModalLabelDate">提示信息：</div>
            </div>
            <div class="modal-body">
                您是否确认要申请数据？
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="selftApplyData()">提交</button>
            </div>
        </div>
    </div>
</div>
<#--MODAL END-->
</#if>
<script>
    function selftApplyData() {
        $.ajax({
            url:"/credit/query/ps/apply.do",
            data:{},
            success: function (msg) {
                $("#msg").text(msg);

                // 申请成功后立即通过
                $.ajax({
                    url: "/credit/nptSync/autoUpdateApplyInfo.do"
                })
            },
            error:function () {
                $("#msg").html("<tr>数据请求失败！</tr>");
            },
            timeout:function(){
                $("#msg").html("<tr>数据请求超时！</tr>");
            }
        });
    }
</script>
<#else>
<div style="color:red;text-align: center;margin-top:50px;background-color:#ccc;">
    <span>查询失败：</span>
    <span>您未进行实名认证，暂无法查询个人信用报告</span>
</div>
</#if>

    <script type="text/javascript" src="${ctx}/r/cms/www/red/js/fileUpload.js"></script>
    <script type="text/javascript">
        if(document.getElementById("fileUploadContent")){
            $("#fileUploadContent").initUpload({
                "uploadUrl":"#",//上传文件信息地址
                "progressUrl":"#",//获取进度信息地址，可选，注意需要返回的data格式如下（{bytesRead: 102516060, contentLength: 102516060, items: 1, percent: 100, startTime: 1489223136317, useTime: 2767}）
                "showSummerProgress":false,//总进度条，默认限制
                //"size":350,//文件大小限制，单位kb,默认不限制
                //"maxFileNumber":3,//文件个数限制，为整数
                //"filelSavePath":"",//文件上传地址，后台设置的根目录
                //"beforeUpload":beforeUploadFun,//在上传前执行的函数
                //"onUpload":onUploadFun，//在上传后执行的函数
                autoCommit:false,//文件是否自动上传
                //"fileType":['png','jpg','docx','doc']，//文件类型限制，默认不限制，注意写的是文件后缀
            })
            function beforeUploadFun(opt){
                opt.otherData =[{"name":"你要上传的参数","value":"你要上传的值"}];
            }
            function onUploadFun(opt,data){
                uploadTools.uploadError(opt);//显示上传错误
            }
        }
    </script>