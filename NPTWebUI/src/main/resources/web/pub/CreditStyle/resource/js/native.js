/**
 * Created by xuqinyuan on 2016/10/27.
 */


function catchObj() {
    var  obj=[];
    for(var i=0;i<3;i++){
        var textObj={};
        var dropObj={};
        textObj.name=$("#textName_"+i).val();
        textObj.value=$("#textValue_"+i).val();
        obj.push(textObj);
        dropObj.name=$("#dropName_"+i).val();
        dropObj.value=$("#dropValue_"+i).val();
        obj.push(dropObj);
    }

    var data = new Object();
    data.queryCondition = obj;
    return JSON.stringify(data);
}
pagination.initPagination("openIndexAjax.action",{webInvokeCondition:catchObj()},showSecurityFunction);
$(function () {
});
function search() {
    $.ajax({
        type: "post",
        url: "openIndexAjax.action",
        data: {webInvokeCondition:catchObj()},
        success: function (html) {
            $('#pageData').html(html);
            pagination.initPagination("openIndexAjax.action",{webInvokeCondition:catchObj()},showSecurityFunction);
        }
    });
}

function showDetail(data) {
    var dataObject = new Object();
    dataObject.primaryKeyValue = data;
    $.ajax({
        url:"authIndex.action",
        data:{webInvokeCondition:JSON.stringify(dataObject)},
        timeout:30000,
        beforeSend:function(){
            $(".loadDiv").show();
        },
        success:function (data) {
            $("#detailDiv").show().html(data);
            $("#indexDiv").hide();
            $(".loadDiv").hide();
        }
    });
}
function  backIndex(){
    $("#detailDiv").hide();
    $("#indexDiv").show();
}

function loadApplyColumns(pid){
    var dataObject = new Object();
    dataObject.parentId = pid;
    $.ajax({
        url:"authLoadApplyFields.action",
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

function showApply(obj,dataType) {
    $(".error").hide();
    var fields="";
    for (var i=0;i<obj.length;i++){
        fields+='<label class="checkbox"><input type="checkbox" name="field"  value="'+obj[i].id+'" >'+obj[i].name+'</label>';
    }
    var applyDataTypeId='<input type="hidden" name="applyDataTypeId"  value="'+dataType[1]+'">'+dataType[2];
    var applyBusinessKey='<input type="hidden" name="applyBusinessKey"  value="'+dataType[3]+'">'+dataType[0];
    $("#applyDataTypeId").html(applyDataTypeId);
    $("#applyBusinessKey").html(applyBusinessKey);
    $("#applyUserOrg").html(dataType[4]);
    $("#fields").html(fields);
    $("#applyDiv").modal();

}
function applyCol(form){
    var flag=handleData();
    if(flag){
        $.post(ctx+"/npt/grs/apply/addApply.action",$(form).serialize(),function (data) {
            $("#applyDiv").modal("hide");
        });
    }

    return false;
}
function handleData(){
    var dateRange=$("#dateRange").html().split(" 到 ");
    var fiedls="";
    $("input[name='applyedStartDate']").val(dateRange[0]);
    $("input[name='applyedEndDate']").val(dateRange[1]);
    console.log($("input[name='applyedEndDate']").val());
    $("input[name='field']:checked").each(function () {
        fiedls+=","+$(this).val();
    });
    $("input[name='fieldIds']").val(fiedls.substr(1));

    if( $("input[name='fieldIds']").val()==""){
        $(".error").show();
        return false;
    }
    else  return true;

}
function checkAll(){
    $("input[name='field']").attr("checked",true).parent().addClass("checked");
    $("#checkBtn").attr("onclick","unCheckAll()");
}
function unCheckAll(){
    $("input[name='field']").attr("checked",false).parent().removeClass("checked");
    $("#checkBtn").attr("onclick","checkAll()");
}
function showMore(datasourceId,keyValue) {
    var dataObject = new Object();
    dataObject.parentId = datasourceId;
    dataObject.primaryKeyValue = keyValue;

    //将webInvokeCondition转换成JSON串，调用ACTION，webInvokeCondition不可改名
    $.ajax({
        type: "post",
        url: "authBlockMore.action",
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
        url:"authBlockLasted.action",
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