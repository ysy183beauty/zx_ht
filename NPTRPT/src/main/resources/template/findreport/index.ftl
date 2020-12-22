<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
<!-- 样式引用 -->
<link rel="stylesheet" type="text/css" href="${wctx}/pub/CreditStyle/resource/swfupload/css/default.css"/>
</head>
<body class="page-header-fixed">

<div>
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption">
                <i class="icon-file"></i>
                报表管理
            </div>
            <div class="tools">
                <button id="addBut" type="button" class="btn purple mini"><i class="icon-plus"></i> 新增</button>
                <a href="javascript:;" class="collapse"></a>
            </div>
        </div>
        <div class="portlet-body form">
        <div class="widget-content nopadding" id="indexPage">
            <table class="table table-bordered data-table dataTable" id="pageData">
                <colgroup>
                    <col style="width: 50px;">
                    <col style="width: 30%;">
                    <col style="width: 15%;">
                    <col style="width: 15%;">
                    <col style="width: 10%;">
                    <col style="width: 20%;">
                </colgroup>
                <thead>
                    <tr>
                        <th>序号</th>
                        <th>模板标题</th>
                        <th>类型</th>
                        <th>主体</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                </thead>
                <tbody>
                    <#include "list.ftl">
                </tbody>
            </table>
            <div id="pagination"></div>
        </div>
        <div id="formDiag" class="widget-content nopadding"></div>
        </div>
    </div>
</div>
<div id="reportDiv" style="display: none">
    <iframe id="reportiframe" width='100%' height='1150' class='border bgWhite'></iframe>
</div>

<!-- JS引用 保证页面中已经引用jquery核心js -->
<script language="javascript" src="${wctx}/pub/CreditStyle/resource/swfupload/upload.loader.js"></script>
<script type="text/javascript">
    $(function(){
    });

    function showSecurityFunction(){
        <#list Session["_curentMenuFunctions"] as funcName>
        $(".${funcName}But").show();
        </#list>
    }
    pagination.initPagination("list.${urlExt}", {pageSize: $("#pageSize").val()});

    $("#addBut").click(function(){
        $("#reportDiv").hide();
        $.post("addPage.${urlExt}",function(data){
            $("#formDiag").html(data);
            $("#indexPage").hide();
            $("#formDiag").show();
            $(".backIndexPage").click(function(){
                $("#formDiag").hide()
                $("#indexPage").show();
            });
            bindSecurityChangeEvent();
            bindShowStyleChangeEvent();
        });
    });

    function bindSecurityChangeEvent(){
        $("input[name='data.pubLevel']").click(function(){
             if(this.value == 2){
                 $("input[name='rolename']").attr("disabled", false);
             }else{
                 $("input[name='rolename']").attr("disabled", true);
             }
        });
    }

    function bindShowStyleChangeEvent() {
        $("input[name='data.showStyle']").click(function () {
            if (this.value == 0) {
                $("#menuIndex").hide();
            } else {
                $("#menuIndex").show();
            }
        });
    }

    /**
    * 添加提交表单
    */
    submitCheckAddContent = function (form){
        $.post("add.${urlExt}", $("#addForm").serialize(),function(data){
            if(data.result){
                $("#formDiag").hide();
                $("#indexPage").show();
                pagination.refreshPage();
                showSecurityFunction();
            }else{
                alert("保存失败：" + data.message);
            }
        });
        return false;
    }

    function editData(id){
        $("#reportDiv").hide();
        $.post("editPage.${urlExt}",{id:id},function(data){
            $("#indexPage").hide();
            $("#formDiag").show();
            $("#formDiag").html(data);
            $(".backIndexPage").click(function(){
                $("#formDiag").hide()
                $("#indexPage").show();
            });
            bindSecurityChangeEvent();
            bindShowStyleChangeEvent();
        });
    }
    /**
    * 编辑提交表单
    */
    submitCheckEditContent = function (form){
        createUserCheck();
        $.post("edit.${urlExt}", $("#editForm").serialize(),function(data){
            if(data.result){
                $("#formDiag").hide();
                $("#indexPage").show();
                pagination.refreshPage();
                showSecurityFunction();
            }else{
                alert("保存失败：" + data.message);
            }
        });
        return false;
    }
    function disabledData(id, name){
        $.post("disabled.${urlExt}",{id:id},function(data){
            pagination.refreshPage();
            showSecurityFunction();
        });
    }
    function enabledData(id, name){
        $.post("enabled.${urlExt}",{id:id},function(data){
            pagination.refreshPage();
            showSecurityFunction();
        });
    }
    function deleteData(id, name){
        if(!window.confirm("您确认要删除报表吗？"))return;
        $.post("delete.${urlExt}",{id:id},function(data){
            pagination.refreshPage();
            showSecurityFunction();
        });
    }
    function historyData(id, name) {
        window.location.href = 'history/index.${urlExt}?id='+id;
    }
    function showSecurityFunction(){
        <#list Session["_curentMenuFunctions"] as funcName>
        $(".${funcName}").show();
        </#list>
    }
    showSecurityFunction();

    /**
     * 作者: 张磊
     * 日期: 17/1/9 下午5:43
     * 备注: 保存并发布
     */
    function publish() {
        $("input[name='data.status']").val(1);
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/24 下午2:21
     * 备注: 显示报表
     */
    function showReport(id, define){
        var randomnumber=Math.floor(Math.random()*100000);
        var url = "${ctx}/ReportServer?id="+id;
        if(define.lastIndexOf(".cpt") != -1){ // cpt
            url += "&reportlet=" + define;
        }else{ // frm
            url += "&formlet=" + define;
        }
        url=url+'&randomnumber='+randomnumber;
        $("#reportiframe").attr("src", url);
        $(".collapse").trigger('click');
        $("#reportDiv").show();
    }

    /**
     * 修改后：报表列表跳转的js
     */
    function showReportDetail(id, define) {
        var url="${ctx}/findreport/"+define+".action";
        window.location.href=url;
    }
</script>
</body>
</html>