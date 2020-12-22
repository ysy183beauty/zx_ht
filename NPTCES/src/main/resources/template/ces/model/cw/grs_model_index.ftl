<!DOCTYPE html>
<html>
<head>
<#include "/template/Credit_Common/common.ftl">
<link rel="stylesheet" href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/chosen.css" />
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/colResizable-1.5.min.js"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/chosen.jquery.min.js"></script>
<script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/jquery.validate.min.js"></script>
<script src="${wctx}/pub/CreditStyle/jquery-validation/localization/messages_zh.js"></script>
<script src="${wctx}/pub/ces/cw/form-validation.js"></script>
<link rel="stylesheet" href="${wctx}/pub/ces/cw/ces-modual.css">

</head>
<body>
<div class="widget-box">

    <div id="indexDiv" class="widget-content nopadding">
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-globe"></i>
                    计算方法
                </div>
                <div class="tools">
                <button class="btn purple mini" onclick="addCptPage()"><i class="icon-plus icon-white"></i> 新增</button>
                    <a href="javascript:;" class="collapse" id="hidden_a"></a>
                </div>
            </div>
            <div class="portlet-body" id="computation">
                <div class="row-fluid">
                    <div class="span4">
                        <div class="control-group">
                            <label class="ow control-label">计算方式：</label>
                            <div class="ow controls">
                                <select name="countType.computeType" id="cptType" class="small">
                                    <option value="">全部</option>
                                <#list CES_CPM_LIST as c>
                                    <option value="${c.code}" <#if (subDmsProps.computeType)?? && (subDmsProps.computeType)?int == c.code>selected</#if>>${c.title}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <table id="computationTb" class="table table-bordered data-table dataTable table-hover">
                    <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th width="10%">名称</th>
                        <th width="10%">计算方式</th>
                        <th width="60%">定性计算数据条数区间及区间值</th>
                        <th width="15%">操作管理</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#include "../cpt/list.ftl">
                    </tbody>
                </table>
            </div>
        </div>
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-globe"></i>
                    预警模型管理
                </div>
                <div class="tools">
                    <#--<button class="btn purple mini" onclick="addModelPage()"><i class="icon-plus icon-white"></i> 新增</button>-->
                    <a href="javascript:;" class="collapse"></a>
                </div>
            </div>
            <div class="portlet-body" id="indexPage">
                <table id="modelTb" class="table table-bordered data-table dataTable table-hover">
                    <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th width="10%">模型名称</th>
                        <th width="10%">预警上限</th>
                        <th width="10%">预警下限</th>
                        <th width="10%">创建时间</th>
                        <th width="5%">状态</th>
                        <th width="15%">操作管理</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#include "grs_model_list.ftl">
                    </tbody>
                </table>
            </div>
        </div>
        <div id="modelGroup"></div>
        <div id="GroupPool"></div>
        <div id="handlePool"></div>
        <div id="modelMainFieldList"></div>
    </div>
    </div>
</div>
<input type="hidden" id="modelId">
<input type="hidden" id="groupId">
<div id="standardModal" class="modal hide fade" tabindex="-1" data-width="900"></div>
<div id="confirmModal" class="modal hide fade" tabindex="-1" data-width="300">
    <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button"></button>
        <h3><i class="icon-exclamation-sign"></i> 操作提示</h3>
    </div>
    <div class="modal-body">
        确认要删除选择的数据吗？
    </div>
    <div class="modal-footer">
        <button class="btn blue ok" data-dismiss="modal"><i class="icon-ok"></i> 确 定</button>
        <button class="btn" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i> 关 闭 </button>
    </div>
</div>

<script>

    function showSecurityFunction() {
    <#list Session["_curentMenuFunctions"] as funcName>
        $(".${funcName}").show();
    </#list>
    }

    $(function () {

        TableManaged.init("modelTb");
        $("#modelTb").colResizable({
            liveDrag:true,
            draggingClass:"dragging"
        });
        App.handleTooltips();

    });
    $(function(){
        $("#hidden_a").trigger("click");
        console.log( $("#hidden_a"));
    });


    function editModelPropsPage(id, modelId) {
        $.nptPOST(
                "editModelPropsPage.${urlExt}",
                {
                    id: id
                },
                function (data) {
                    $("#standardModal").modal("show").html(data);
                    $("input[name='modelProps.modelId']").val(modelId);
                    $("#modelId").val(id);
                    FormValidation.init();
                });
    }

    function editDimensionPropsPage(id, groupId) {
        $.nptPOST(
                "editDimensionPropsPage.${urlExt}",
                {
                    id: id
                },
                function (data) {
                    $("#standardModal").modal("show").html(data);
                    $("input[name='dimensionProps.groupId']").val(groupId);
                    $("#groupId").val(id);
                    FormValidation.init();
                });
    }

    function editSubDmsPropsPage(id, poolId) {
        $.nptPOST(
                "editSubDmsPropsPage.${urlExt}",
                {
                    id: id,
                    poolId: poolId
                },
                function (data) {
                    $("#standardModal").modal("show").html(data);
                    $("input[name='subDmsProps.poolId']").val(poolId);
                    App.init();
                    FormValidation.init();
                });
    }

    function editProps(form, handler) {
        if($(form).valid()){
            $.nptPOST(form.action,$(form).serialize(), function (data) {
                        if (data.result == "0") {
                            returnInfo(false, data.message || "操作失败！");
                        } else {
                            handler();
                            $('#standardModal').modal("hide");
                            returnInfo(true, "操作成功！");
                        }
                    }
            );
        }

        return false;
    }

    $("#cptType").on("change", function () {
        refreshCptList($(this).val());
    });

    function editCptPage(id) {
        $.nptPOST(
                "editCptPage.${urlExt}",
                {
                    id: id
                },
                function (data) {
                    $("#standardModal").modal("show").html(data);
                    FormValidation.init();
                });
    }

    function dataProgress(id) {
        $.nptPOST("preTreat.${urlExt}", {id: id}, function (data) {
            if (data.result == "0") {
                returnInfo(false, data.message || "操作失败！");
            } else {
                returnInfo(true, "操作成功！");
            }
        });
    }

    /**
     *作者：OWEN
     *时间：2016/11/13 13:19
     *描述:
     *      进入添加模型的页面
     */
    function addModelPage() {
        $.ajax({
            type: "post",
            url: "addModel.action",
            success: function (html) {
                $('#standardModal').modal("show").html(html);
            }
        });
    }

    /**
     *作者：OWEN
     *时间：2016/11/13 13:12
     *描述:
     *      进入选中的模型的主信息列表
     */
    function enterModelIndex(id){
        $.ajax({
            type: "post",
            url: "${ctx}/npt/grs/query/custom/openIndex.action",
            data: {parentId:id},
            timeout: 30000,
            beforeSend:function(){
                $(".loadDiv").show();
            },
            success: function (html) {
                $("#GroupPool").empty();
                $("#handlePool").empty();
                $("#modelGroup").empty();
                $("#modelId").val(id);
                $(".loadDiv").hide();
                $('#modelMainFieldList').html(html);
            },
            error:function () {
                $(".loadDiv").hide();
                returnInfo(false,"操作失败！");
            }
        });
    }

    /**
     * 显示模型分组列表
     */
    function showGroup(id){
        initDiv();
        $.nptPOST("listModelGroups.action",{modelId:id},function (data) {
            $("#modelId").val(id);
            $("#modelGroup").html(data);
        });
    }

    /**
     * 展开新增分组页面
     */
    function addGroupPage(id) {
        $.nptPOST("addGroupPage.action",{modelId:id},function (data) {
            $('#standardModal').modal("show").html(data);
        });
    }

    /**
     * 展开新增计算方法页面
     */
    function addCptPage() {
        $.nptPOST("addCptPage.action", {}, function (data) {
            $('#standardModal').modal("show").html(data);
            FormValidation.init();
        });
    }

    /**
     * 新增分组
     */
    function addGroup(form) {
        $.nptPOST(form.action,$(form).serialize(), function (data) {
                    if (data.result == "0") {
                        returnInfo(false, data.message || "操作失败！");
                    } else {
                        $('#standardModal').modal("hide");
                        returnInfo(true, "操作成功！");
                        $("#groupDiv").html(data);
                        $("#GroupPool").empty();
                        $("#handlePool").empty();
                    }
                }
        );
        return false;
    }

    /**
     * 设置主分组
     * @param id
     */
    function setMainGroup(id,modelId) {
        $.nptPOST(
                "  setModelMainGroup.action",
                {
                    modelId: modelId,
                    groupId: id
                },
                function (data) {
                    if (data.result) {
                        returnInfo(true, "操作成功！");
                    } else {
                        returnInfo(false, data.message || "操作失败！");
                    }
                }
        );
    }
    /**
     * 新增模型
     */
    function addModelBaseInfo(form) {
        $.nptPOST(form.action, $(form).serialize(),function (data) {
            if (data.result) {
                $("#standardModal").modal("hide");
                returnInfo(true, data.message || "操作成功！");
                refreshModelList();
                initDiv();
                $("#modelGroup").empty();
            }else {
                returnInfo(false, data.message || "操作失败！");
            }
        });
        return false;
    }
    /**
     * 刷新模型列表
     * */
    function refreshModelList(){
        $.nptPOST("refreshModels.action",function (data) {
            TableManaged.draw("modelTb",data);
        })
    }

    /**
     * 刷新分组列表
     */
    function refreshGroupList(){
        $.nptPOST("listModelGroups.action",{modelId:$("#modelId").val()},function(data){
           $("#modelGroup").html(data);
        });
    }

    /**
     * 刷新数据池列表
     */
    function refreshPoolList(){
        $.nptPOST("configBaseModelGroup.action",{groupId:$("#groupId").val()},function(data){
            $("#GroupPool").html(data);
        });
    }

    /**
     * 刷新计算方法列表
     */
    function refreshCptList(computeType) {
        $.nptPOST("listCpt.action", {computeType: computeType}, function (data) {
            $("#computationTb tbody").html(data);
        });
    }

    /**
     * 禁用/启用分组
     * @param id
     * @param status
     */
    function setGroupStatus(id, status) {
        $.nptPOST(
                " setModelGroupStatus.action",
                {
                    modelId: $("#parentId").val(),
                    groupId: id,
                    groupStatus: status
                },
                function (data) {
                    if (data.result) {
                        returnInfo(true, "操作成功！");
                    } else {
                        returnInfo(false, data.message || "操作失败！");
                    }
                }
        );
    }


    /***
     * 配置数据池
     * ***/
    function configGroup(id) {
        $("#groupId").val(id);
        $.ajax({
            url: "configBaseModelGroup.action",
            data: {
                groupId: id,
            },
            timeout: 30000,
            success: function (data) {
                initDiv();
                $("#GroupPool").html(data);
            }
        });
    }

    /**
     * 显示新增数据池页面
     */
    function addPoolPage(id){
        $.nptPOST("addPoolPage.action",{groupId:id},function (data) {
            $("#handlePool").html(data);
        })
    }
    /**
     * 新增数据池
     */
    function addPool(form) {
        var poolType = $("select[name='poolType']").val();

        checkConditionAndIndexFields();

        if (checkpField()){
            $.nptPOST(form.action,$(form).serialize(),function (data) {
                        if (data.result == "0") {
                            returnInfo(false, data.message || "操作失败！");
                        } else {
                            refreshGroupList();
                            $("#poolList").html(data);
                            $("#handlePool").empty();
                            returnInfo(true, "操作成功！");
                        }
                    }
            );
        }

        return false;
    }

    /**
     * 设置主数据池
     */
    function setMainPool(id) {
        $.nptPOST(
                "setGroupMainPool.action",
                {
                    poolId: id,
                    groupId: $("#groupId").val()
                },
                function (data) {
                    if (data.result) {
                        returnInfo(true, data.message || "操作成功！");
                    } else {
                        returnInfo(false, data.message || "操作失败！");
                    }
                }
        );
    }

    /**
     * 显示修改数据池页面
     */
    function configPool(id){
        $.nptPOST("configBaseModelGroupool.action",{poolId:id},function (data) {
            $("#handlePool").html(data);
        });
    }

    /**
     * 修改数据池
     */
    function editPool(form) {
        var poolType = $("select[name='poolType']").val();

        checkConditionAndIndexFields();

        if (checkpField()) {
            $.nptPOST(form.action, $(form).serialize(), function (data) {
                if (data.result) {
                    $("#handlePool").empty();
                    showGroup($("#modelId").val());
                    configGroup($("#groupId").val());
                    returnInfo(true, data.message || "操作成功！");

                } else {
                    returnInfo(false, data.message || "操作失败！");
                }
            });
        }
        return false;
    }

    /**
     *  获取选中主字段
     */
    function checkConditionAndIndexFields() {
        var cdtFields = [];
        $("input[name='pConFieldIds']:checked").each(function () {
            cdtFields.push($(this).val());
        });
        $("input[name='paramCdtFields']").val(cdtFields.join(","));

        var idxFields = [];
        $("input[name='pIdxFieldIds']:checked").each(function () {
            idxFields.push($(this).val());
        });
        $("input[name='paramIdxFields']").val(idxFields.join(","));
    }

    /**
     * 检查是否选取主键
     */
    function checkpField(){
        if ($("input[name='pFieldId']:checked").val() == null){
            returnInfo(false,"请选择主键！");
            return false;
        }
        return true;
    }

    /**
     * 查看关联模型页面
     */
    function loadPoolLinks(id) {
        $.nptPOST("listPoolLinks.action",{poolId:id},function (data) {
            $("#standardModal").modal("show").html(data);
        })
    }

    /**
     * 显示所有模型分组
     */
    function showGroupLink(fieldId) {
        $.nptPOST(
                "listPossibleLinkablePools.action",
                {
                    fromPoolId:$("#poolId").val(),
                    fkFieldId:fieldId
                },
                function (data) {
                $("#standardModal").modal("show").html(data);
        });
    }
    /**
     * 作者: 张磊
     * 日期: 2017/04/11 下午08:43
     * 备注: 显示自由模型关联数据
     */
    function showGroupFreeLink(poolId, fieldId) {
        $.nptPOST(
                "listDSGroups.action",
                {
                    fromPoolId: poolId,
                    fkFieldId: fieldId
                },
                function (data) {
                    $("#standardModal").modal("show").html(data);
                });
    }
    /**
     * 新增模型关联
     */
    function addGroupoolLink(form) {
        $.nptPOST(form.action,$(form).serialize(),function (data) {
            $("#standardModal").modal("hide");
            $("#poolList").html(data);
        });
        return false;
    }
    /**
     * 修改关联模型状态
     */
    function setPoolLinkStatus(id,status,obj){

        $.nptPOST(
                "setPoolLinkStatus.action",
                {
                    poolLinkId:id,
                    newStatus:status
                },
                function (data) {
                    if (data.result) {
                        changePoolLinkStatusText(id,status,obj);
                        returnInfo(true, data.message || "操作成功！");
                    } else {
                        returnInfo(false, data.message || "操作失败！");
                    }
                });
    }

    /**
     * 作者: 张磊
     * 日期: 2017/04/12 上午10:34
     * 备注: 删除poolLink
     */
    function deletePoolLink(id, obj) {
        $.nptPOST(
                "deletePoolLink.action",
                {
                    poolLinkId: id
                },
                function (data) {
                    if (data.result) {
                        $(obj).closest("tr").remove();
                        configGroup($("#groupId").val());
                        returnInfo(true, "操作成功！");
                    } else {
                        returnInfo(false, data.message || "操作失败！");
                    }
                });
    }

    /**
     * 改变关联模型文本状态
     * @param id
     * @param status
     * @param obj
     */
    function changePoolLinkStatusText(id,status,obj){
        if (status == "IDS_DISABLED"){
            $(obj).parent().prev().html("禁用");
            $(obj).removeClass("red")
                    .addClass("green")
                    .attr("data-original-title","启用")
                    .attr("onclick","setPoolLinkStatus("+id+",'IDS_ENABLED',this)")
                    .html("<i class='icon-play'></i>");
        }else{
            $(obj).parent().prev().html("启用");
            $(obj).removeClass("green")
                    .addClass("red")
                    .attr("data-original-title","禁用")
                    .attr("onclick","setPoolLinkStatus("+id+",'IDS_DISABLED',this)")
                    .html("<i class='icon-stop'></i>");;
        }
    }

    /**
     * 初始化数据池类别
     */
    function initPoolType() {
        $("select[name='poolType']").val("CLD_ADDITION");
        $(".isFlag").hide();
    }
    
    //置顶
    function toTop(obj){
        var $tr = $(obj).parents("tr");
        $tr.fadeOut().fadeIn();
        $tr.parent().prepend($tr);
        $tr.find("td").css("color", "#f60");
    }
    //置底部
    function toBottom(obj){
        var $tr = $(obj).parents("tr");
        $tr.fadeOut().fadeIn();
        $tr.parent().append($tr);
        $tr.find("td").css("color", "#000");
    }

    /**
     * 显示排序按钮
     */
    function showMoveBtn(obj){
        var btns=' <a href="javascript:void(0)" class="btn tooltips blue up mini"  data-placement="bottom" data-original-title="上移" ><i class="icon-long-arrow-up"></i></a>' +
                ' <a href="javascript:void(0)" class="btn tooltips green down mini"  data-placement="bottom" data-original-title="下移" ><i class="icon-long-arrow-down"></i></a>' +
                ' <a href="javascript:void(0)" class="btn tooltips yellow top mini"  data-placement="bottom" data-original-title="置顶" ><i class="icon-pushpin"></i></a>';
        $(obj).after(btns);
        App.handleTooltips();
    }

    /**
     * 隐藏排序按钮
     */
    function hideMoveBtn(obj) {
        $(obj).nextAll().remove();
    }

    /**
     *
     */
    function initOrderBtn() {
        //上移
        var $up = $(".up");
        $up.unbind("click").click(function () {
            var $tr = $(this).parents("tr");
            console.log($tr.index());
            if ($tr.index() != 0) {
                $tr.fadeOut().fadeIn();
                $tr.prev().before($tr);
            }
        });
        //下移
        var $down = $(".down");
        var len = $down.length;
        $down.unbind("click").click(function () {
            var $tr = $(this).parents("tr");
            console.log($tr.index());
            if ($tr.index() != len - 1) {
                $tr.fadeOut().fadeIn();
                $tr.next().after($tr);
            }
        });
        //置顶
        var $top = $(".top");
        $top.unbind("click").click(function () {
            var $tr = $(this).parents("tr");
            $tr.fadeOut().fadeIn();
            $tr.parent().prepend($tr);
            $tr.css("color", "#f60");
        });
    }
    /**
     * 初始化页面
     */
    function initDiv() {
        $("#GroupPool").empty();
        $("#handlePool").empty();
        $("#modelMainFieldList").empty();
    }

    /**
     * 删除数据池
     * @param id
     */
    function deletePool(id){
        $("#confirmModal").modal("show");

        $("#confirmModal .ok").attr("onclick","confirmDeletePool('"+id+"')");

    }
    function confirmDeletePool(id){
        $.nptPOST("deleteModelGrouPool.action",
                {
                    groupId:$("#groupId").val(),
                    poolId:id
                },
                function(data){
                    if (data.result){
                        refreshPoolList();
                        $("#handlePool").empty();
                        returnInfo(true,data.message||"操作成功！");
                    }else {
                        returnInfo(false,data.message||"操作失败！");
                    }
                }
        );
    }
    /**
     * 删除分组
     * @param id
     */
    function deleteGroup(id) {
        $("#confirmModal").modal("show");
        $("#confirmModal .ok").attr("onclick","confirmDeleteGroup('"+id+"')");

    }
    function confirmDeleteGroup(id){
        $.nptPOST("deleteModelGroup.action",
                {
                    modelId: $("#modelId").val(),
                    groupId:id
                },
                function (data) {
                    if (data.result){
                        refreshGroupList();
                        initDiv();
                        returnInfo(true,data.message||"操作成功！");
                    }else {
                        returnInfo(false,data.message||"操作失败！");
                    }
                });
    }
    /**
     * 删除模型
     * @param id
     */
    function deleteModel(id,status){
        $("#confirmModal").modal("show");
        $("#confirmModal .ok").attr("onclick","confirmDeleteModel('"+id+"')");

    }

    /**
     * 复制模型里面的数据池
     * @param id
     */
    function copyModal(id){

            $("#copyModal").modal("show");

        // $.nptPOST("getModalAndPool.action",
        //         {
        //             id: id
        //         },
        //         function (data) {
        //             if (data.result){
        //                 $("#copyModal").modal("show");
        //
        //             }else {
        //                 $("#copyModal").modal("show");
        //             }
        //         });
    }

    function deleteCpt(id){
        $("#confirmModal").modal("show");
        $("#confirmModal .ok").attr("onclick","confirmDeleteCpt('"+id+"')");

    }

    function confirmDeleteCpt(id) {
        $.nptPOST("deleteCpt.action",
                {
                    id: id
                },
                function (data) {
                    if (data.result){
                        refreshCptList();
                        returnInfo(true,data.message||"操作成功！");
                    }else {
                        returnInfo(false,data.message||"操作失败！");
                    }
                });
    }

    function optimizeQeury(id) {
        $.nptPOST("optimizeQuery.action",{modelId:id},function (data) {

        });
    }

    function confirmDeleteModel(id){
        $.nptPOST("deleteModel.action",{modelId:id},function (data) {
            if (data.result){
                refreshModelList();
                initDiv();
                $("#modelGroup").empty();
                returnInfo(true,data.message||"操作成功！");
            }else{
                returnInfo(false,data.message||"操作失败！");
            }
        });
    }


    /**
     * 修改模型
     * @param obj
     */
    function editModel(id,obj){
        $(obj).attr("onclick", "saveModel('"+id+"',this)")
                .attr("data-original-title","保存模型")
                .html("<i class='icon-ok '></i>");
        var tds=$(obj).parent().parent().parent().find("td");
        var select=false;
        if (tds[5].innerHTML=="启用"){
            select=true;
        }
        tds[1].innerHTML = "<input type='text' style='width:90%;' name='modelName' value='" + tds[1].innerHTML + "'>";
        tds[5].innerHTML = '<select  style="width:100%" name="status">' +
                '<option '+(select?"selected":"")+' value="1">启用</option>' +
                '<option '+(select?"":"selected")+' value="0">禁用</option>' +
                '</select>';
        App.initUniform();
    }

    /**
     * 保存模型修改
     * @param obj
     */
    function saveModel(id,obj){
        var modelName = $(obj).parent().parent().parent().find("td input[name='modelName']");
        var status = $(obj).parent().parent().parent().find("td select");
        $.nptPOST("editModel.action",
                {
                    modelId:id,
                    modelName:$(modelName).val(),
                    status:$(status).val()
                },
                function (data) {
                    if (data.result){
                        modelName.parent()[0].innerHTML=$(modelName).val();
                        status.parent()[0].innerHTML=(status.val()==1?"启用":"禁用");
                        $(obj).attr("onclick", "editModel('"+id+"',this)")
                                .attr("data-original-title","编辑模型")
                                .html("<i class='icon-edit '></i>");
                        returnInfo(true,data.message||"操作成功！");
                    } else {
                        returnInfo(false,data.message || "操作失败！");
                    }
        });

    }

    /**
     * 修改分组
     * @param obj
     */
    function editGroup(id,obj){
        $(obj).attr("onclick", "saveGroup('"+id+"',this)")
                .attr("data-original-title","保存分组")
                .html("<i class='icon-ok '></i>");
        var tds=$(obj).parent().parent().parent().find("td");
        var select=false;
        if (tds[3].innerHTML=="启用"){
            select=true;
        }
        console.log(select);
        tds[1].innerHTML = "<input type='text' style='width:90%;' name='groupName' value='" + tds[1].innerHTML + "'>";
        tds[2].innerHTML = "<input type='text' style='width:90%;' name='groupNote'  value='" + tds[2].innerHTML + "'>";
        tds[3].innerHTML = '<select  style="width:100%" name="status">' +
                '<option '+(select?"selected":"")+' value="1">启用</option>' +
                '<option '+(select?"":"selected")+' value="0">禁用</option>' +
                '</select>';
        App.initUniform();
    }

    /**
     * 保存分组修改
     * @param obj
     */
    function saveGroup(id,obj){
        var groupName = $(obj).parent().parent().parent().find("td input[name='groupName']");
        var groupNote = $(obj).parent().parent().parent().find("td input[name='groupNote']");
        var status = $(obj).parent().parent().parent().find("td select");
        $.nptPOST("editGroup.action",
                {
                    modelId:$("#modelId").val(),
                    groupId:id,
                    groupName:$(groupName).val(),
                    groupNote:$(groupNote).val(),
                    status:$(status).val()

                },
                function (data) {
                    if (data.result){
                        groupName.parent()[0].innerHTML=$(groupName).val();
                        groupNote.parent()[0].innerHTML=$(groupNote).val();
                        status.parent()[0].innerHTML=(status.val()==1?"启用":"禁用");
                        $(obj).attr("onclick", "editGroup('"+id+"',this)")
                                .attr("data-original-title","编辑分组")
                                .html("<i class='icon-edit '></i>");
                        returnInfo(true,data.message||"操作成功！");
                    } else{
                        returnInfo(false,data.message || "操作失败！");
                    }
                });
    }


</script>
</body>
</html>