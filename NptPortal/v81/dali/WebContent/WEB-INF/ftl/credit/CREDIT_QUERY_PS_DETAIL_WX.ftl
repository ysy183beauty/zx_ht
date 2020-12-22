<#include "CommonUtil.ftl"/>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/qyxycx.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/index.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/main.css" />

<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/js/pager/css/kkpager.css" />
<#--<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/jquery-confirm.min.css"/>-->
<#--<script src="${ctx}/r/cms/www/red/js/app.js"></script>-->
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
    .login{
        overflow: hidden;
        margin-left:20px;
        margin-top:20px;
    }
    .tab-col{
        width:96%;
        margin:0 auto;
        font-size:12px!important;
    }
    .tab-col td{
        text-indent: 0!important;
    }
    .th-align{
        text-align: right;
    }
</style>
<div class="login">
    <ul>
        <#if user??>
        <div id="ydl">
            <li>
                <div style="float: left;margin-top: 2px;">欢迎: </div>
                <a id="showName" href="/member/userInfo.jspx" style="float: right;margin-left: 3px;color:blue;">${user.username!}
                </a>
            </li>
            <#if user.flag??>
            <#if user.flag=='3'>
            <li> <span class="glyphicon glyphicon-ok-sign" title="已认证" style="margin-left: 2px;color: #02a831;margin-top: 3px;" ></span>|</li>
            <#elseif user.flag=='4'>
            <li> <span class="glyphicon glyphicon-ok-sign" title="认证失败" style="margin-left: 2px;color: red;margin-top: 3px;" ></span>|</li>
            <#elseif user.flag=='2'>
            <li> <span class="glyphicon glyphicon-ok-sign" title="认证中" style="margin-left: 2px;margin-top: 3px;" ></span>|</li>
            <#else>
            <li> <span class="glyphicon glyphicon-ok-sign" title="其他" style="margin-left: 2px;margin-top: 3px;" ></span>|</li>
            </#if>
            <#else>
            <li> <span class="glyphicon glyphicon-ok-sign" title="未认证" style="margin-left: 2px;margin-top: 3px;" ></span>|</li>
            </#if>
            <li>
                <a onClick="userLogout()" style="cursor: pointer">退出</a>
            </li>
        </div>
        <#else>
        <div id="wdl">
            <li>
                <a href="/login.jspx" style="cursor: pointer">登录</a>
            </li>
            <li>|</li>
            <li>
                <a href="/register.jspx" style="cursor: pointer">注册</a>
            </li>
        </div>
        </#if>
    </ul>
</div>

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
<#assign result = _RESULT_>

<!-- 0 查询成功,5数据未申请,其它直接打印原因 -->
<#if result.code = 0>
    <#assign data=NPT_ACTION_RETURNED_JSON>
<div class="content">
    <div class="con_top wdl">
        <div class="active-time">
            <#if _START_DATE??>
                <span>信息更新时间：${_START_DATE}</span>
                <span>有效期：7天</span>
            </#if>
        </div>
        <div class="con_top_shu">
            <h2>${_REAL_NAME!}</h2>

            <#--<ul class="alert alert-info">-->
                <#--<#list data.dataList as list>-->
                <#--<li>-->
                    <#--<div class="info">${list.groupTitle}：</div>-->
                    <#--<div class="info-con">-->
                        <#--<#list list.blockList as blockList>-->
                            <#--<span class="col-sm-6">${blockList.blockInfo.poolTitle}</span>-->
                        <#--</#list>-->
                    <#--</div>-->
                <#--</li>-->
                <#--</#list>-->
            <#--</ul>-->
            <div class="con_center">
                <#list data.dataList as list>
                    <div class="center_title">${list.groupTitle}</div>
                    <div class="">
                    <#if list.blockList?size gt 0>
                        <#list list.blockList as blockList>
                        <div>
                            <h4 class="col-md-6">${blockList.blockInfo.poolTitle}</h4>
                            <#--<div class="error col-md-6" data-toggle="modal" data-target="#appealDiv" >-->
                                <#--<img src="${ctx}/r/cms/www/red/img/sjxq/u87.png" alt=""/>信息纠错-->
                            <#--</div>-->
                            <#if blockList.dataArray??>
                                <#if blockList.dataArray?size == 1>
                                    <#list blockList.dataArray as dataArray>
                                        <#--<table class="tab-col" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">-->
                                        <#--<tr>-->
                                            <#--<#list dataArray.dataArray as array>-->
                                                <#--<th>${array.title}：</th>-->
                                                <#--<td id="${array.fieldId!}"><span>${array.value}</span></td>-->
                                                <#--<#if array?index%2 ==1>-->
                                                <#--</tr>-->
                                                <#--<tr>-->
                                                <#--</#if>-->
                                            <#--</#list>-->
                                            <#--<#if dataArray.dataArray?size%2 ==1>-->
                                                <#--<th></th>-->
                                                <#--<td></td>-->
                                            <#--</#if>-->
                                        <#--</tr>-->
                                        <#--</table>-->
                                        <table class="tab-col" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
                                        <#list dataArray.dataArray as array>
                                            <tr>
                                                <th class="th-align">${array.title}：</th>
                                                <td id="${array.fieldId!}"><span>${array.value}</span></td>
                                            </tr>
                                        </#list>
                                        </table>
                                    </#list>
                                <#else>
                                    <#list blockList.dataArray as dataArray>
                                        <table class="tab-row" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
                                            <tr>
                                                <#list dataArray.dataArray as array>
                                                    <th>${array.title}</th>
                                                </#list>
                                            </tr>
                                            <tr>
                                                <#list dataArray.dataArray as array>
                                                    <td>${array.value}</td>
                                                </#list>
                                            </tr>
                                        </table>
                                    </#list>
                                </#if>
                            <#else>
                                <div class="null">
                                    本平台暂未收录！
                                </div>

                            </#if>
                        </div>
                        </#list>
                    <#else>
                        <div class="no-found">无相关信息</div>
                    </#if>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</div>

<#elseif result.code = 5>
<div style="color:red;text-align: center;margin-top:50px;background-color:#ccc;">
    <span>查询失败：</span>

        <span>数据未申请。请前往信用大理门户网站进行数据申请！</span>

</div>
    <#--<div class="alert alert-warning" role="alert">-->
        <#--<strong>-->
        <#--<h3>注意：</h3>-->
            <#--<p class="p-title">-->
                <#--为了保护您的个人信用数据，我们要求在查看自身信用信息之前，请进行信息申请以及验证注册时的手机号码!-->
            <#--</p>-->
            <#--<p class="p-title">-->
                <#--每次申请的数据会保存一个星期的时间，过期之后再次查看需要再次进行信息申请!-->
            <#--</p>-->
        <#--</strong>-->
    <#--</div>-->
    <#--<div id="content">-->
        <#--<div >-->
            <#--<span class="con_title">您的实名认证信息如下：</span>-->
            <#--<span id="msg"></span>-->
        <#--</div>-->
        <#--<table class=" table-striped table-bordered"  width="100%"  style="border-collapse:collapse;text-align: center;">-->
            <#--<tr>-->
                <#--<#if userExt.type == "person">-->
                    <#--<td>真实姓名</td>-->
                    <#--<td>个人身份证号</td>-->
                <#--<#elseif userExt.type == "company">-->
                    <#--<td>企业名称</td>-->
                    <#--<td>企业工商注册号</td>-->
                <#--</#if>-->
                <#--<td>手机号码</td>-->
                <#--<td>操作</td>-->
                <#--<td>申请记录</td>-->
            <#--</tr>-->
            <#--<tr>-->
                <#--<td>${_REAL_NAME!}</td>-->
                <#--<td>${_IDCARD!}</td>-->
                <#--<td>${_MOBILE!}</td>-->
                <#--<td><a data-toggle="modal" data-target="#myModalDate">申请数据</button></td>-->
                <#--<td><a onclick="getApplyDataHistory(1,20)">历史详情</a></td>-->
            <#--</tr>-->
        <#--</table>-->
        <#--<div class="con_title">-->
            <#--<#if userExt.type == "person">-->
                <#--个人数据申请历史如下：-->
            <#--<#elseif userExt.type == "company">-->
                <#--企业数据申请历史如下：-->
            <#--</#if>-->
        <#--</div>-->
        <#--<table  class=" table-striped table-bordered"   width="100%" style="border-collapse:collapse;">-->
            <#--<thead>-->
                <#--<th align='center'>序号</th>-->
                <#--<th align='center'>申请提交时间</th>-->
                <#--<th align='center'>申请状态</th>-->
                <#--<th align='center'>回复时间</th>-->
            <#--</thead>-->
            <#--<tbody id="tbody">-->
                <#--<tr>-->
                    <#--<td colspan="4">请提交申请</td>-->
                <#--</tr>-->
            <#--</tbody>-->
        <#--</table>-->
        <#--<div id="kkpager"></div>-->
    <#--</div>-->


<#else >
    <div style="color:red;text-align: center;margin-top:50px;background-color:#ccc;">
        <span>查询失败：</span>
        <span>${result.title}</span>
    </div>
    <#--<div class="btn-login">-->
        <#--<a href="/login.jspx" class="btn btn-primary">登录</a>-->
    <#--</div>-->
</#if>
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

<#--<div class="alert alert-sumbit alert-warning" role="alert">-->
    <#--<div id="alert-content"></div>-->
<#--</div>-->
<script src="/thirdparty/swfupload/swfupload.js"></script>
<script src="/thirdparty/swfupload/swfupload.queue.js"></script>
<script src="/thirdparty/swfupload/fileprogress.js"></script>
<script src="/thirdparty/swfupload/handlers.js"></script>
<link href="/thirdparty/swfupload/process.css" rel="stylesheet"/>
<script type="text/javascript">
    function userLogout() {
        //优先执行第三方退出 在执行本地退出
        location="/logout.jspx?returnUrl=login.jspx";
    }
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
    $(function () {
        getApplyDataHistory(1,20);
    });
//打开异议申请页面
    $(".error").bind("click",function() {
        var data = $(this).parent().find("table").html();
        $("#tableInfoList").html(data);
        $("#tableInfoList td").append("<div class='addDiv'><input type='text' class='addInput'/></div>");
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


    function selftApplyData() {
//        是否提交信息
        $.ajax({
            url:"/credit/query/ps/apply.do",
            data:{},
            success:function (msg) {
                $("#msg").text(msg);
            },
            error:function () {
                $("#msg").html("<tr>数据请求失败！</tr>");
            },
            timeout:function(){
                $("#msg").html("<tr>数据请求超时！</tr>");
            }
        });
    }

    function getApplyDataHistory(currPage,pageSize){
        $(".load_main").show();
        $.ajax({
            url:"/credit/query/ps/getApplyInfo.do",
            data:{pageNo:currPage,
                  pageSize:pageSize},
            success:function (data) {
                $("#tbody").empty();

                if(data){
                    var list=data.list;
                    var len =list.length;
                    var tr =""
                    for(var i=0;i<len ;i++){
                        var one = list[i];
                        tr=tr+"<tr><td align='center'>"+(i+1)+"</td><td align='center'>"+one['applyTime']
                                +"</td><td align='center'>"
                                +one['applyFlagValue']
                                +"</td><td align='center'>"
                                +checkUndefind(one['replyTime'])
                                +"</td></tr>"
                    }
                    if(data.totalCount>pageSize){
                        initPage(currPage,pageSize,data.totalCount,data.totalPage);
                        console.log(data.totalCount+"--"+currPage+"--"+pageSize);
                    }
                    $("#tbody").html(tr);
                }else{
                    $("#tbody").html("<tr>暂无记录</tr>");
                }
                load();
            },
            error:function () {
                $("#tbody").html("<tr>数据请求失败！</tr>");
                load();
            },
            timeout:function(){
                $("#tbody").html("<tr>数据请求超时！</tr>");
                load();
            }
        })
    }

   function initPage(currPage,pageSize,sum,totalPage) {
        //生成分页控件
        kkpager.generPageHtml({
            pagerid: "kkpager",
            pno: currPage,
            mode : 'click',
            //总页码
            total: totalPage,
            //总数据条数
            totalRecords: sum,
            //链接算法
            click: function (n) {
                //获取第n页数据
                getApplyDataHistory(n,pageSize);
            }
        },true);
    };
    //字段的值是否为undefined
    function checkUndefind(value,replaceChar){
        if(replaceChar==undefined){
            replaceChar='-';
        }
        var re =  value==undefined?replaceChar:value;
        return re
    }

    function showAppealInfo(dtId, pkId, pkValue) {
        $.ajax({
            url: "/credit/srv/showAppeal.do",
            data: {
                dataTypeId: dtId,
                pkFieldId: pkId,
                pkValue: pkValue
            },
            success: function (data) {
                $("#appealDiv").modal("show").html(data);
            }
        });
    }
</script>