<link href="/r/w/pub/bootstrap/frame/media/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/r/w/pub/matrix/js/jquery.min.js"></script>
<script src="/r/w/pub/bootstrap/frame/media/js/bootstrap.min.js" type="text/javascript"></script>
<#include "/template/Credit_Common/common.ftl">
<#include "/template/Credit_Common/c_pagination.ftl">
<link href="/pub/index/css/main.css" rel="stylesheet">

    <div class="center">
        <h3>信用查询</h3>
        <table class="table tbale-hover table-bordered" id="pageData">
        <#include "list.ftl">
        </table>
    </div>
    <div id="pagination"></div>
<div id="modelDetail" class="margin-bottom-25"></div>

<script>
    pagination.initPagination("paginationModelMainDataAjax.action");
    function showDetail(id) {
        var dataObject = new Object();
        dataObject.primaryKeyValue = id;
        $.ajax({
            url: "listModelDetailBlocks.action",
            data: {webInvokeCondition: JSON.stringify(dataObject)},
            timeout: 30000,
            success: function (data) {
                $(".load").hide();
                $("#modelDetail").html(data);

            }
        });
    }

</script>
