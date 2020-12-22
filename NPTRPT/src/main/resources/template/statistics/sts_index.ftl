<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
</head>
<body class="page-header-fixed">

<div id="pageData">
<#include "sts_list.ftl">
</div>
<div id="pagination"></div>
<script>
    function showSecurityFunction() {
    <#list Session["_curentMenuFunctions"] as funcName>
        $(".${funcName}").show();
    </#list>
    }

    $(function () {
        pagination.initPagination("list.${urlExt}",{pageSize:9});
    });
</script>
</body>
</html>