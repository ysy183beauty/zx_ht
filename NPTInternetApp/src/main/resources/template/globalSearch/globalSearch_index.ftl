<#include "/template/common/common.ftl">
<!doctype html>
<html>
<head>
</head>
<body class="page-header-fixed">


<div class="container-fluid">
    <!-------------------------------------全站搜索-------------------------------------------->
    <div class="row-fluid">
        <div class="portlet box boxTheme" id="searchInfo">
            <div class="portlet-title">
                <div class="caption"><i class="icon-search"></i>全站检索</div>
            </div>
            <div class="portlet-body form">
                <form action="resultList.action" class="form-horizontal" onsubmit="return search(this);">
                <div class="row-fluid">
                    <div class="span4">
                        <div class="control-group">
                            <label class="ow control-label">标识名称：</label>
                            <div class="ow controls">
                                <select name="fromField" id="fieldValue" class="small m-wrap">
                                    <option value="身份证">身份证</option>
                                    <option value="企业名称">企业名称</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="span4">
                        <div class="control-group">
                            <label for="" class="ow control-label" id="keyValue">身份证：</label>
                            <div class="ow controls">
                                <input type="text" class="m-wrap small" name="primaryKeyValue" placeholder="请输入标识内容">
                            </div>
                        </div>
                    </div>
                    <div class="span4">
                        <div class="control-group">
                            <label for="" class="ow control-label"> 关键字：</label>
                            <div class="ow controls">
                                <input type="text" class="m-wrap small" name="toField" placeholder="请输入关键信息"></div>
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
            <div class="portlet-body" id="searchResult">

            </div>
        </div >

    </div>
    <!-- END PAGE HEADER-->
</div>
<!-- END PAGE CONTAINER-->
<div id="appealDiv" class="modal hide fade " tabindex="-1" data-width="760" >

</div>

<script>
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
                $("#searchResult").html(data);

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
        handelFields();
        $.post(form.action,$(form).serialize(),function(data){
           if (data.results){
               $("#appealDiv").modal("hide")
           }else {
               alert(data.message);
           }
        });
        return false;
    }
    function handelFields(){
        var fields=[];
        $(".field").each(function () {
           if($(this).val() != ""){
               var field={};
               field.id = $(this).attr("id");
               field.value = $(this).val();
               fields.push(field);
           }
        });

        $("input[name='appealFields']").val(JSON.stringify(fields));

    }
</script>

</body>
<!-- END BODY -->

</html>