<#include "CommonUtil.ftl"/>
<link href="/thirdparty/alertify.js/themes/alertify.core.css" rel="stylesheet">
<link href="/thirdparty/alertify.js/themes/alertify.bootstrap.css" rel="stylesheet">
<link href="/thirdparty/fine-uploader/fine-uploader-new.css" rel="stylesheet">
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
    .qq-uploader DIALOG[open]{
        left:600px;
    }
</style>
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
                    <div class="bg bg_1">
                    <#if list.blockList?size gt 0>
                        <#list list.blockList as blockList>
                        <div>
                            <h3 class="col-md-6">${blockList.blockInfo.poolTitle}</h3>
                            <div class="error col-md-6" data-toggle="modal" data-target="#appealDiv" >
                                <img src="${ctx}/r/cms/www/red/img/sjxq/u87.png" alt=""/>信息纠错
                            </div>
                            <#if blockList.dataArray??>
                                <#if blockList.dataArray?size == 1>
                                    <#list blockList.dataArray as dataArray>
                                        <table class="tab-col" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;">
                                        <tr>
                                            <#list dataArray.dataArray as array>
                                                <th>${array.title}：</th>
                                                <td id="${array.fieldId!}"><span style="word-break:break-all;">${array.value}</span></td>
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

    <div class="alert alert-warning" role="alert">
        <strong>
        <h3>注意：</h3>
            <p class="p-title">
                为了保护您的个人信用数据，我们要求在查看自身信用信息之前，请进行信息申请以及验证注册时的手机号码!
            </p>
            <p class="p-title">
                每次申请的数据会保存一个星期的时间，过期之后再次查看需要再次进行信息申请!
            </p>
        </strong>
    </div>
    <div id="content">
        <div >
            <span class="con_title">您的实名认证信息如下：</span>
            <span id="msg"></span>
        </div>
        <table class=" table-striped table-bordered"  width="100%"  style="border-collapse:collapse;text-align: center;">
            <tr>
                <#if userExt.type == "person">
                    <td>真实姓名</td>
                    <td>个人身份证号</td>
                <#elseif userExt.type == "company">
                    <td>企业名称</td>
                    <td>企业工商注册号</td>
                </#if>
                <td>手机号码</td>
                <td>操作</td>
                <td>申请记录</td>
            </tr>
            <tr>
                <td>${_REAL_NAME!}</td>
                <td>${_IDCARD!}</td>
                <td>${_MOBILE!}</td>
                <td><a data-toggle="modal" data-target="#myModalDate">申请数据</button></td>
                <td><a onclick="getApplyDataHistory(1,20)">历史详情</a></td>
            </tr>
        </table>
        <div class="con_title">
            <#if userExt.type == "person">
                个人数据申请历史如下：
            <#elseif userExt.type == "company">
                企业数据申请历史如下：
            </#if>
        </div>
        <table  class=" table-striped table-bordered"   width="100%" style="border-collapse:collapse;">
            <thead>
                <th align='center'>序号</th>
                <th align='center'>申请提交时间</th>
                <th align='center'>申请状态</th>
                <th align='center'>回复时间</th>
            </thead>
            <tbody id="tbody">
                <tr>
                    <td colspan="4">请提交申请</td>
                </tr>
            </tbody>
        </table>
        <div id="kkpager"></div>
    </div>


<#else >
    <div style="color:red;text-align: center;margin-top:50px;background-color:#ccc;">
        <span>查询失败：</span>
        <span>${result.title}</span>
    </div>
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
                                <input type="hidden" name="attach" value="">
                                <div id="fine-uploader">文件上传</div>
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

<div class="alert alert-sumbit alert-warning" role="alert">
    <div id="alert-content"></div>
</div>
<#include "include/fileUpload.ftl">
<script>
    $("form").submit(function () {
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
        if (document.all) { //判断IE浏览器
            window.event.returnValue = false;
        }
        else {
            event.preventDefault();
        }
    });

    $(function () {
        getApplyDataHistory(1,20);
    });
//打开异议申请页面
    $(".error").bind("click",function() {
        var data = $(this).parent().find("table").html();
        $("#tableInfoList").html(data);
        $("#tableInfoList td").append("<div class='addDiv'><input type='text' placeholder='请输入异议内容' class='addInput'/></div>");
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
                    var tr ="";
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