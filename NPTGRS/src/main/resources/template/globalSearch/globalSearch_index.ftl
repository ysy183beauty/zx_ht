<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${wctx}/pub/CreditStyle/resource/swfupload/css/default.css"/>
</head>
<body class="page-header-fixed">


<div class="container-fluid">
    <!-------------------------------------全站搜索-------------------------------------------->
    <div class="row-fluid">
        <div class="portlet box boxTheme" id="searchInfo">
            <div class="portlet-title">
                <div class="caption"><i class="icon-search"></i>异议登记</div>
            </div>
            <div class="portlet-body form">
                <form action="resultList.action" class="form-horizontal" onsubmit="return search(this);">
                <div class="row-fluid">
                    <div class="span4">
                        <div class="control-group">
                            <label class="ow control-label">依据项：</label>
                            <div class="ow controls">
                                <select name="fromField" id="fieldValue" class="small">
                                    <option value="企业名称">企业名称</option>
                                    <option value="身份证">身份证</option>
                                    <option value="姓名">姓名</option>
                                    <option value="企业标识">企业标识</option>
                                    <option value="工商注册号">工商注册号</option>
                                    <option value="信用代码">信用代码</option>
                                    <option value="负责人">负责人</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="span4">
                        <div class="control-group">
                            <label for="" class="ow control-label" id="keyValue">企业名称：</label>
                            <div class="ow controls">
                                <input type="text" class=" small" required name="primaryKeyValue" placeholder="请输入标识内容">
                            </div>
                        </div>
                    </div>
                    <div class="span4">
                        <div class="control-group">
                            <label for="" class="ow control-label"> 异议数据项：</label>
                            <div class="ow controls">
                                <a href="javascript:void (0);" class="popovers" data-placement="top" data-content="请输入关键信息,多个数据项以‘ ；’隔开">
                                    <input type="text" class="small" required name="toField" placeholder="请输入关键信息,多个数据项以‘ ；’隔开">
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn blue"><i class="icon-search icon-white"></i>&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询
                    </button>
                    <button type="reset" class="btn"><i class="icon-repeat"></i>&nbsp;&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置
                    </button>
                </div>
                </form>
            </div>
            <div class="portlet-body no-padding" id="searchResult">

            </div>
        </div >

    </div>
    <!-- END PAGE HEADER-->
</div>
<!-- END PAGE CONTAINER-->
<div id="appealDiv" class="modal hide fade " tabindex="-1" data-width="900" >

</div>
<script src="${wctx}/pub/CreditStyle/resource/swfupload/upload.loader.js"></script>
<script>
    $(function () {
        $('.popovers').popover()
    });
    function search(form) {
        $.ajax({
            url:form.action,
            data:$(form).serialize(),
            timeout:30000,
            beforeSend: function () {
                $(".loadDiv").show();
            },
            success:function (data) {
                $(".loadDiv").hide();
                $("#searchResult").removeClass("no-padding").html(data);

            },
            error:function () {
                $(".loadDiv").hide();
                returnInfo(false,"操作失败！");
            }
        });
        return false;
    }
    $("#fieldValue").change(function () {
        $("#keyValue").html($(this).val()+"：");
    });

    function owenTest(){
        alert("功能待开发");
    }

    function showAppealInfo(dtId,pkId,pkValue) {

        var obj = new Object();
        obj.pkValue = pkValue;
        obj.pkFieldId = pkId;
        obj.dataTypeId = dtId;

        $.post("showAppeal.action",{globalSearchBean:JSON.stringify(obj)},function (data) {
            $("#appealDiv").modal("show").html(data);

        });
        return false;
    }
    function backToIndex() {
        $("#searchInfo").show();
        $("#appealInfo").hide();
    }
    function addAppeal(form) {
        if (handelFields()){
            $.post(form.action,$(form).serialize(),function(data){
               if (data.result){
                   $("#appealDiv").modal("hide");
                   returnInfo(true,data.message||"操作成功！");
               }else {
                   returnInfo(false,data.message||"操作失败！");
               }
            });
        } else {
            returnInfo(false,"请至少提交一个异议信息！");
        }
        return false;
    }
    function handelFields(){
        var fields=[];;
        $(".field").each(function () {
           if($(this).val() != ""){
               var field={};
               field.id = $(this).attr("id");
               field.value = $(this).val();
               field.default=  $(this).parent().prev().text();
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

</body>
<!-- END BODY -->

</html>