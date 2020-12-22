<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/nav.css" rel="stylesheet" type="text/css"/>
</head>
<body class="page-header-fixed">

<!-------------------------------------企业信用信息查询-------------------------------------------->
<div id="modalPage">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>模型详情</div>
            <div class="tools">
                <a href="javascript:;" class="collapse"></a>
            </div>
        </div>
    <#if NPT_ACTION_RETURNED_JSON??>
        <#assign data = NPT_ACTION_RETURNED_JSON>
        <#if _RESULT_TYPE == "MODEL_INDEX">
            <#include "../customModel/grs_custom_query_list.ftl">
        <#elseif _RESULT_TYPE == "MODEL_TREE">
            <#include "../customModel/grs_model_tree.ftl">
        </#if>
        <input id="modelDataType" type="hidden" value=${_RESULT_TYPE}>
    <#else >
        <div class="portlet-body">
            <table class="table table-bordered">
                <tr>
                    <th>暂无数据</th>
                </tr>
            </table>
        </div>
    </#if>
    </div>

</div>
<div id="modalDiv" class="modal hide fade " tabindex="-1" data-width="900"></div>
<div id="detailDiv"></div>

<script type="text/javascript" src="${wctx}/pub/baseModel/grs_basemodel_custom_query_controller.js"></script>
</body>
<!-- END BODY -->
</html>
