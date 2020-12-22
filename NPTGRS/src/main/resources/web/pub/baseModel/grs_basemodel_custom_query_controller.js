/**
 * Created by xuqinyuan on 2016/10/27.
 */

pagination.initPagination(ctx+"/npt/grs/query/custom/openIndexAjax.action", {webInvokeCondition: catchObj(),parentId:$('#modelId').val()} ,showSecurityFunction);

$(function () {
    $("#pageData").colResizable({
    liveDrag:true,
    draggingClass:"dragging"
});
});

function showDetail(data) {
    var dataObject = new Object();
    dataObject.primaryKeyValue = data;

    var mdt = $("#modelDataType").val();
    var urlParam = ctx + "/npt/grs/query/custom/authIndex.action";
    var parentIdParam = $("#modelId").val();
    if(mdt == "MODEL_TREE"){
        urlParam = ctx + "/npt/grs/query/custom/poolDetail.action";
        parentIdParam = $("#selectedPoolId").val();
    }
    $.ajax({
        url:urlParam,
        data:{webInvokeCondition:JSON.stringify(dataObject),parentId:parentIdParam},
        timeout:30000,
        beforeSend:function(){
            $(".loadDiv").show();
        },
        success:function (data) {
            $("#detailDiv").show().html(data);
            $("#modalPage").hide();
            $(".loadDiv").hide();
        }
    });
}
function  backIndex(){
    $("#detailDiv").hide();
    $("#indexDiv").show();
}

function loadApplyColumns(pid,pvalue){
    var dataObject = new Object();
    dataObject.parentId = pid;
    dataObject.primaryKeyValue = pvalue;
    $.ajax({
        url:ctx+"/npt/grs/query/custom/authLoadApplyFields.action",
        data:{webInvokeCondition:JSON.stringify(dataObject)},
        timeout:30000,
        beforeSend:function(){
            $(".loadDiv").show();
        },
        success:function (data) {
            $(".error").hide();
            $(".loadDiv").hide();
            $("#modalDiv").modal("show").html(data);

        }
    });
}

function applyCol(form){
    var flag=handelFields();
    if(flag){
        var flag=handelFields();
        if(flag){
            $.post(ctx+"/npt/grs/apply/addFieldApply.action",$(form).serialize(),function (data) {
                if (data.result){
                    $("#modalDiv").modal("hide");
                    returnInfo(true,data.message||"操作成功！");
                }
                else {
                    returnInfo(false,data.message||"操作失败！");
                }
            });
        }
    }

    return false;
}
function handelFields(){
    var fiedls=[];
    $("input[name='field']:checked").each(function () {
        fiedls.push($(this).val());
    });
    $("input[name='fieldIds']").val(fiedls.join(","));

    //
    // if( $("input[name='fieldIds']").val()==""){
    //     $(".error").show();
    //     return false;
    // }
    // else
        return true;
}
function checkAll(){
    $(".field").attr("checked",true).parent().addClass("checked");
    $("#checkBtn").attr("onclick","unCheckAll()");
}
function unCheckAll(){
    $(".field").attr("checked",false).parent().removeClass("checked");
    $("#checkBtn").attr("onclick","checkAll()");
}
function showMore(datasourceId,keyValue) {
    var dataObject = new Object();
    dataObject.parentId = datasourceId;
    dataObject.primaryKeyValue = keyValue;

    //将webInvokeCondition转换成JSON串，调用ACTION，webInvokeCondition不可改名
    $.ajax({
        type: "post",
        url: ctx+"/npt/grs/query/custom/authBlockMore.action",
        data: {webInvokeCondition: JSON.stringify(dataObject)},
        timeout: 30000,
        beforeSend: function () {
            $(".loadDiv").show();
        },
        success: function (data) {
            $(".loadDiv").hide();
            $("#modalDiv").modal("show").html(data);

        }
    });
}


function jumpMore(datasourceId,keyValue) {
    var dataObject = new Object();
    dataObject.parentId = datasourceId;
    dataObject.primaryKeyValue = keyValue;

    //将webInvokeCondition转换成JSON串，调用ACTION，webInvokeCondition不可改名
    $.ajax({
        type: "post",
        url: ctx+"/npt/grs/query/custom/getGroupoolLinkedPoolData.action",
        data: {webInvokeCondition: JSON.stringify(dataObject)},
        timeout: 30000,
        beforeSend: function () {
            $(".loadDiv").show();
        },
        success: function (data) {
            $(".loadDiv").hide();
            $("#modalDiv").modal("show").html(data);

        }
    });
}

function loadPoolData(datasourceId,keyValue,obj) {
    $(obj).removeAttr("onclick");
    var dataObject = new Object();
    dataObject.parentId =  datasourceId;
    dataObject.primaryKeyValue = keyValue;

    //将webInvokeCondition转换成JSON串，调用ACTION，webInvokeCondition不可改名
    $.ajax({
        type:"post",
        url:ctx+"/npt/grs/query/custom/authBlockLasted.action",
        data:{webInvokeCondition:JSON.stringify(dataObject)},
        timeout:30000,
        beforeSend:function(){
//            $(".loadDiv").show();
        },
        success:function (data) {
            $(obj).parent().parent().next().html(data);
        }
    });
}

/**
 * 基础条件筛选
 */
function baseFilter(size,primarykeyValue) {
    var obj = [];
    var data = {};
    for (var i = 0; i < size; i++) {
        var param = {};
        param[$("#dropName_" + i).val()] = $("#dropValue_" + i).val();
        obj.push(param);
    }
    data.queryCondition = obj;
    data.primaryKeyValue = primarykeyValue;
    $.post(ctx+"/npt/grs/query/custom/authIndexAjax.action" ,{webInvokeCondition: JSON.stringify(data)},function (data) {
        $("#filterInfo").html(data);
        });
}
function catchObj() {
    var textBoxCount = $("#textBoxCount").val();
    var dropBoxCount = $("#dropBoxCount").val();

    var obj = [];

    for (var i = 0; i < textBoxCount; i++) {
        var textObj = {};
        textObj.name = $("#textName_" + i).val();
        textObj.value = $("#textValue_" + i).val();
        obj.push(textObj);
    }

    for (var i = 0; i < dropBoxCount; i++) {
        var dropObj = {};
        dropObj.name = $("#dropName_" + i).val();
        dropObj.value = $("#dropValue_" + i).val();
        obj.push(dropObj);
    }
    var data = new Object();
    data.queryCondition = obj;
    return JSON.stringify(data);
}

function search() {

    var mdt = $("#modelDataType").val();
    var urlParam = ctx + "/npt/grs/query/custom/openIndexAjax.action";
    var parentIdParam = $("#modelId").val();
    if(mdt == "MODEL_TREE"){
        urlParam = ctx + "/npt/grs/query/custom/poolIndexAjax.action";
        parentIdParam = $("#selectedPoolId").val();
    }

    $.ajax({
        type: "post",
        url: urlParam,
        data: {webInvokeCondition:catchObj(),parentId:parentIdParam},
        timeout: 30000,
        beforeSend:function(){
            $(".loadDiv").show();
        },
        success: function (html) {
            $("#pageData tbody").html(html);
            pagination.initPagination(urlParam,{webInvokeCondition:catchObj(),parentId:parentIdParam},showSecurityFunction);
            $(".loadDiv").hide();
        },
        error:function () {
            $(".loadDiv").hide();
            returnInfo(false,"操作失败！");
        }
    });
}