<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
</head>
<body class="page-header-fixed">

<div>
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption">
                <i class="icon-file"></i>
                历史版本
            </div>
        </div>
        <div class="portlet-body form">
            <div class="widget-content nopadding" id="indexPage">
                <table class="table table-bordered data-table dataTable" id="pageData">
                    <colgroup>
                        <col style="width: 50px;">
                        <col style="width: 30%;">
                        <col style="width: 20%;">
                        <col style="width: 20%;">
                    </colgroup>
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>更新时间</th>
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
            <div class="form-actions">
                <div class="ow controls">
                    <button type="button" class="btn btn-default backIndexPage">返回</button>
                </div>
            </div>
            <div id="formDiag" class="widget-content nopadding"></div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
    });

    pagination.initPagination("list.${urlExt}?id=${request.getParameter('id')}");

    function deleteData(id, name){
        if(!window.confirm("您确认要删除报表吗？"))return;
        $.post("delete.${urlExt}",{id:id},function(data){
            pagination.refreshPage();
        });
    }

    /**
     * 作者: 张磊
     * 日期: 17/1/10 上午11:59
     * 备注: 返回
     */
    $(".backIndexPage").click(function(){
        window.location.href = '../index.${urlExt}';
    });
</script>
</body>
</html>