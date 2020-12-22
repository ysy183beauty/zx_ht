<script src="/pub/index/js/jquery.js"></script>
<#include "/template/Credit_Common/common.ftl">
<#include "/template/Credit_Common/c_pagination.ftl">
<div class="container first box">
    <div class="treeTb">
        <div class="portlet  blue" style="margin-bottom: 10px">
            <div class="portlet-title">
                <div class="caption"><i class="icon-reorder"></i> ${_MODEL_TREE.root.modelName!}</div>
            </div>
            <div class="portlet-body">
                <div class="accordion">
                <#if _MODEL_TREE.skeletons?? && _MODEL_TREE.skeletons?size gt 0>
                    <#list _MODEL_TREE.skeletons as c>
                        <div class="accordion-group">
                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion1"
                               href="#collapse_${c_index}">
                                <div class="accordion-heading">
                                    <i class="icon-angle-right"></i> ${c.sketeton.groupName!}
                                </div>
                            </a>
                            <div id="collapse_${c_index}" class="accordion-body in collapse" >
                                <div class="accordion-inner">
                                    <div class="container">
                                        <#if c.leafs?? && c.leafs?size gt 0>
                                            <div class="row">
                                                <#list c.leafs as p>
                                                    <div class="col-sm-4">
                                                        <div class="poolBox">
                                                            <div class="chart"><i ></i></div>
                                                            <div class="info">
                                                                <div class="title">${p.poolTitle!}</div>
                                                                <div class="src">${p.providerTitle!}</div>
                                                            </div>
                                                            <#if _HAVE_MFIELDS?string("true","false") == "false">
                                                                <a href="javascript:void (0)" class="more"
                                                                   onclick="showList(${p.id?string("#")})">详情 <i class="icon-angle-right"></i></a>
                                                            <#else >
                                                                <a href="javascript:void (0)" class="more"></a>
                                                            </#if>
                                                        </div>
                                                    </div>
                                                </#list>
                                            </div>
                                        </#if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </#list>
                </#if>
                </div>
            </div>
        </div>
    </div>

    <div class="portlet hide mFieldList green" style="margin-bottom: 10px">
        <div class="portlet-title">
            <div class="caption"><i class="icon-reorder"></i></div>
        </div>
        <div class="portlet-body ">

        </div>
    </div>

    <div class="portlet poolDetail yellow hide">
        <div class="portlet-title">
            <div class="caption"><i class="icon-reorder"></i> 详情</div>
        </div>
        <div class="portlet-body ">

        </div>
    </div>


    <input type="hidden" id="parentId" value="${_MODEL_TREE.root.id?string("#")}">
</div>

<script>
    <#if _HAVE_MFIELDS?string("true","false") == "true">
    showMfield();
    </#if>
    function showMfield() {
        $.post("paginationModelMainDataIndex.action", {modelId: $("#parentId").val()}, function (data) {
            $(".mFieldList").removeClass("hide");
            $(".mFieldList .portlet-body").html(data)
            $(".poolDetail").hide();
            var dataObject = new Object();
            dataObject.parentId = $("#parentId").val();
            pagination.initPagination("paginationModelMainDataAjax.action", {webInvokeCondition: JSON.stringify(dataObject)});
        })
    }
    function showList(id) {
        $.post("paginationPoolDataIndex.action", {poolId: id}, function (data) {
            $(".mFieldList").removeClass("hide");
            if (data.result == "0") {
                $(".mFieldList .portlet-body").addClass("center").html("暂无可显示字段！")
            } else {
                $(".mFieldList .portlet-body").html(data)
                $(".poolDetail").hide();
            <#if _HAVE_MFIELDS?string("true","false") == "false">
                hideTd();
            </#if>
                pagination.initPagination("paginationPoolDataAjax.action", {primaryKey: id});
            }

        });
    }
    function showDetail(pk) {
        var dataObject = new Object();
        dataObject.primaryKeyValue = pk;
        dataObject.parentId = $("#parentId").val();
        $.post("getBaseModelGroupBlocks.action", {webInvokeCondition: JSON.stringify(dataObject)}, function (data) {
            $(".poolDetail").removeClass("hide").show();
            $(".poolDetail .portlet-body").html(data)
        });
    }
    function hideTd() {
        $("#pageData th:last-child").remove()
        $("#pageData td:last-child").remove()
    }
    $(".poolBox").each(function () {
        colorRandom(this);
        chartRandom(this);
    });
    function colorRandom(obj) {
        var colorStr = "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6);
        var aStr =  "#"+("00000"+((Math.random()*16777215+0.5)>>0).toString(16)).slice(-6);
        $(obj).css("background",colorStr);
        $(obj).find("a").css("background",aStr);
    };
    function chartRandom(obj){
        var char = ["icon-comments","icon-file","icon-bar-chart","icon-picture","icon-star-empty","icon-tasks","icon-envelope","icon-map-marker","icon-legal","icon-th-large"];
        var index = parseInt(Math.random()*9);
        $(obj).find(".chart i").addClass(char[index]);
    }
</script>
