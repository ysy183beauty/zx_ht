/**
 * Created by xuqinyuan on 2016/10/27.
 */

var personalId='';

function catchObj() {
    var textBoxCount = $("#textBoxCount").val();
    var dropBoxCount = $("#dropBoxCount").val();

    var  obj=[];

    for(var i=0;i<textBoxCount;i++){
        var textObj={};
        textObj.name=$("#textName_"+i).val();
        textObj.value=$("#textValue_"+i).val();
        obj.push(textObj);
    }

    for(var i=0;i<dropBoxCount;i++){
        var dropObj={};
        dropObj.name=$("#dropName_"+i).val();
        dropObj.value=$("#dropValue_"+i).val();
        obj.push(dropObj);
    }

    var data = new Object();
    data.queryCondition = obj;
    return JSON.stringify(data);
}
pagination.initPagination(ctx+"/npt/grs/query/personal/reportIndexAjax.action", {webInvokeCondition: catchObj()}, showSecurityFunction);

function search() {
    $.ajax({
        type: "post",
        url: ctx+"/npt/grs/query/personal/reportIndexAjax.action",
        data: {webInvokeCondition:catchObj()},
        timeout: 30000,
        beforeSend:function(){
            $(".loadDiv").show();
        },
        success: function (html) {
            $('#pageData tbody').html(html);
            pagination.initPagination(ctx+"/npt/grs/query/personal/reportIndexAjax.action", {webInvokeCondition: catchObj()}, showSecurityFunction);
            $(".loadDiv").hide();
        },
        error:function () {
            $(".loadDiv").hide();
            returnInfo(false,"操作失败！");
        }
    });
}

$(function () {
    $("#pageData").colResizable({
        liveDrag:true,
        draggingClass:"dragging"
    });
    $("#modalDiv").on('show',function () {
        console.log($(event.target).closest('a').data('id'));
        $("#selected_rpbs").val($(event.target).closest('a').data('id'));
    });
    $.ajax({
        type: "post",
        url: ctx+"/findreport/getReportList.action",
        data: {
            rptCategory: 0,
            rptHost: 1
        },
        success: function (data) {
            if (data.result) {
                $("#template").empty();
                var msg = JSON.parse(data.message);
                msg.forEach(function (d) {
                    $("#template").append("<option value='" + d.id + "'>" + d.rptName + "</option>");
                });
            }
        }
    });
});

function showDetail(data) {
    var dataObject = new Object();
    dataObject.primaryKeyValue = data;
    $.ajax({
        url:ctx+"/npt/grs/query/personal/authIndex.action",
        data:{webInvokeCondition:JSON.stringify(dataObject)},
        timeout:30000,
        beforeSend:function(){
            $(".loadDiv").show();
        },
        success:function (data) {
            $("#detailDiv").show().html(data);
            $("#indexDiv").hide();
            $(".loadDiv").hide();
        },
        error:function () {
            $(".loadDiv").hide();
            returnInfo(false,"操作失败！");
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
        url:ctx+"/npt/grs/query/personal/authLoadApplyFields.action",
        data:{webInvokeCondition:JSON.stringify(dataObject)},
        timeout:30000,
        beforeSend:function(){
            $(".loadDiv").show();
        },
        success:function (data) {
            $(".error").hide();
            $(".loadDiv").hide();
            $("#modalDiv").modal("show").html(data);

        },
        error:function () {
            $(".loadDiv").hide();
            returnInfo(false,"操作失败！");
        }
    });
}

function applyCol(form){
    var flag=handelFields();
    if(flag){
        $.post(ctx+"/npt/grs/apply/addFieldApply.action",$(form).serialize(),function (data) {
            if (data.result) {
                $("#modalDiv").modal("hide");
                returnInfo(true, data.message || "操作成功！");
            }
            else {
                returnInfo(false, data.message || "操作失败！");
            }
        });
    }

    return false;
}
function handelFields(){
    var fiedls=[];
    $("input[name='field']:checked").each(function () {
        fiedls.push($(this).val());
    });
    $("input[name='fieldIds']").val(fiedls.join(","));

    if( $("input[name='fieldIds']").val()==""){
        $(".error").show();
        return false;
    }
    else  return true;
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
        url: ctx+"/npt/grs/query/personal/authBlockMore.action",
        data: {webInvokeCondition: JSON.stringify(dataObject)},
        timeout: 30000,
        beforeSend: function () {
            $(".loadDiv").show();
        },
        success: function (data) {
            $(".loadDiv").hide();
            $("#modalDiv").modal("show").html(data);

        },
        error:function () {
            $(".loadDiv").hide();
            returnInfo(false,"操作失败！");
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
        url: ctx+"/npt/grs/query/personal/getGroupoolLinkedPoolData.action",
        data: {webInvokeCondition: JSON.stringify(dataObject)},
        timeout: 30000,
        beforeSend: function () {
            $(".loadDiv").show();
        },
        success: function (data) {
            $(".loadDiv").hide();
            $("#modalDiv").modal("show").html(data);

        },
        error:function () {
            $(".loadDiv").hide();
            returnInfo(false,"操作失败！");
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
        url:ctx+"/npt/grs/query/personal/authBlockLasted.action",
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
    $.post(ctx+"/npt/grs/query/personal/authIndexAjax.action" ,{webInvokeCondition: JSON.stringify(data)},function (data) {
            $("#filterInfo").html(data);
        });
}

/**
 * 查看报告
 */
function showReport() {
    $("#modalDiv").modal('hide');
    $("#detailDiv").empty();
    $("#logDiv").empty();
    $.post(ctx+"/findreport/getReportById.action", {id: $("#template").val()}, function (data) {
        var url=ctx+"/ReportServer?id=" + $("#template").val() + "&reportlet=" + data.message + "&rpbs=" + $("#selected_rpbs").val() + "&rptype=0&rybh="+personalId;
        console.log(url);
        // if(data.result){
        $(".collapse").trigger('click');
        $("<iframe id='reportFrame' width='100%'  height='1150' class='border bgWhite'  src='"+url+"'></iframe>").prependTo($("#detailDiv"));
        // }
    });
}

/**
 * 历史报告
 */
function loadReportHistory(rpbs) {
    $("#detailDiv").empty();
    $("#logDiv").empty();
    $.ajax({
        url: ctx+"/npt/web/finereport/log/index.action",
        data: {
            rptype: 1,
            rpbs: rpbs,
            rpmc: rpbs
        },
        success: function (data) {
            $(".collapse").trigger('click');
            $("#logDiv").html(data);
            TableManaged.init("logList");
            $("#logList").colResizable({
                liveDrag:true,
                draggingClass:"dragging"
            });
            $.post(ctx+"/npt/web/finereport/log/list.action",{rptype: 1,rpbs: rpbs,rpmc: rpbs},function (data) {
                TableManaged.draw("logList",data);
            });
        }
    });
}

/**
 *打开选择框
 */
function openModal(id) {
    $("#modalDiv").modal('show');
    personalId=id;
}