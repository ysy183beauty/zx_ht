<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
</head>
<body class="page-header-fixed">

<!-------------------------------------专题信用信息查询-------------------------------------------->
<div id="indexDiv">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>专题模型查询</div>
            <div class="tools">
                <button class="btn mini" onclick="backModelIndex()"><i class="icon-long-arrow-left"></i> 返回</button>
                <a href="javascript:;" class="collapse"></a>
            </div>
        </div>

    <div class="portlet-body">
    <#if _MODEL_LIST??>
        <#assign data = _MODEL_LIST>
        <div class="row-fluid">
        <#list data as c>
            <#if c?index%4==0 && c?index!=0>
            </div>
            <div class="row-fluid">
            </#if>
            <div class="span3 poolBox" data-tablet="span6" data-desktop="span4" onclick="showModelIndex('${c.id?string("#")}')">
                <div class="dashboard-stat">
                    <div class="visual"><i></i></div>
                    <div class="details">
                        <div class="number"> ${c.modelName!}</div>
                        <div class="desc"> ${c.hostTitle!}</div>
                    </div>
                    <span class="poolNote">${c.modelNote!}</span>
                </div>
            </div>
        </#list>
    </div>
        <#else >
            <div class="page-title">
                <h3>暂无数据</h3>
            </div>
    </#if>

    </div>

</div>
</div>

<div id="modelIndex"></div>
<input type="hidden" id="modelId">


<script>
    function showSecurityFunction() {
    <#list Session["_curentMenuFunctions"] as funcName>
        $(".${funcName}").show();
    </#list>
    }
    /**
     *  获取随机颜色
     */
    $(".poolBox").each(function () {
        colorRandom(this);
        chartRandom(this);
    }).addClass("margin-bottom-10");
    function colorRandom(obj) {
        var colorStr = "#" + ("00000" + ((Math.random() * 16777215 + 0.5) >> 0).toString(16)).slice(-6);
        $(obj).css("background", colorStr);
    }
    ;
    function chartRandom(obj) {
        var char = ["icon-comments", "icon-file", "icon-bar-chart", "icon-picture", "icon-star-empty", "icon-tasks", "icon-envelope", "icon-map-marker", "icon-legal", "icon-th-large"];
        var index = parseInt(Math.random() * 9);
        $(obj).find(".visual i").addClass(char[index]);
    }

    /**
     * 显示所选专题模型主页
     */
    function showModelIndex(id){
        $.post("openIndex.action",{parentId:id},function (data) {
            $("#modelId").val(id);
            $("#modelIndex").show().html(data);
            pagination.initPagination("openIndexAjax.action",{parentId:id});
        });
    }
    function backModelIndex() {
        $("#modalPage").show();
        $("#detailDiv").hide();
    }
</script>
</body>
<!-- END BODY -->

</html>