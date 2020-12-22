<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
    <link href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/nav.css" rel="stylesheet" type="text/css"/>
</head>
<body class="page-header-fixed">

<!-------------------------------------非企业法人信用信息查询-------------------------------------------->
<div id="indexDiv">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>非企业法人信用信息查询</div>
            <div class="tools">
                <#--<div class="btn-group ">-->
                    <#--<button class="btn mini purple dropdown-toggle " data-toggle="dropdown"><i class="icon-cogs"></i> 工具-->
                        <#--<i-->
                                <#--class="icon-angle-down"></i></button>-->
                    <#--<ul class="dropdown-menu pull-right">-->
                        <#--<li><a href="#"><i class="icon-download-alt"></i> 导出</a></li>-->
                        <#--<li><a href="print.action"><i class="icon-print"></i> 打印</a></li>-->
                        <#--<li><a href="#"><i class="icon-trash"></i> 删除</a></li>-->
                    <#--</ul>-->
                <#--</div>-->
            </div>
        </div>
    <#if NPT_ACTION_RETURNED_JSON??>
        <#assign data = NPT_ACTION_RETURNED_JSON>

        <#if data.modelTree??>
            <div>
                <#include "../customModel/grs_model_tree.ftl">
            </div>
        </#if>
        <div class="portlet-body form">
            <!-- BEGIN FORM-->
            <form action="#" class="form-horizontal" id="searchList" style="display:none">
                <div class="row-fluid">
                    <#if data.textBoxes?size!=0>
                        <#list data.textBoxes as textBox>
                            <div class="span4"  <#if textBox_index%3==1>style="margin-left: 0"</#if>>
                                <div class="control-group">
                                    <label class="ow control-label" title="${textBox.title}">${textBox.title} </label>
                                    <input type="hidden" id="textName_${textBox_index}" value="${textBox.name}">
                                    <div class="ow controls">
                                        <input type="text" class="small" id="textValue_${textBox_index}">
                                    </div>
                                </div>
                            </div>
                        </#list>
                        <input id="textBoxCount" type="hidden" value="${data.textBoxes?size!0}">
                    </#if>

                    <#if data.dropBoxList?size!=0>
                        <#list data.dropBoxList as dropBox>
                            <div class="span4" <#if (data.textBoxes?size + dropBox_index)%3 ==1>style="margin-left: 0"</#if>>
                                <div class="control-group">
                                    <label class="ow control-label" title="${dropBox.title} ">${dropBox.title} </label>
                                    <input type="hidden" id="dropName_0" value="${dropBox.name}">
                                    <div class="ow controls">
                                        <select class="small " id="dropValue_${dropBox_index}">
                                            <option value="">全部</option>
                                            <#list dropBox.orderedElements as element>
                                                <option value="${element.id}">${element.title}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </#list>
                        <input id="dropBoxCount" type="hidden" value="${data.dropBoxList?size!0}">
                    </#if>

                </div>
                <div class="form-actions" style="display:none">
                    <button type="button" class="btn blue" onclick="search()"><i class="icon-search icon-white"></i>&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询
                    </button>
                    <button type="reset" class="btn"><i class="icon-repeat"></i>&nbsp;&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置
                    </button>
                </div>
            </form>
            <!-- END FORM-->
        </div>
        <div class="portlet-body" style="display:none">
        <#--<div class="clearfix">-->
        <#--<div class="btn-group pull-left">-->
        <#--<button class="btn purple dropdown-toggle " data-toggle="dropdown"><i class="icon-cogs"></i> 工具 <i-->
        <#--class="icon-angle-down"></i></button>-->
        <#--<ul class="dropdown-menu pull-left">-->
        <#--<li><a href="#"><i class="icon-download-alt"></i> 导出</a></li>-->
        <#--<li><a href="print.action"><i class="icon-print"></i> 打印</a></li>-->
        <#--<li><a href="#"><i class="icon-trash"></i> 删除</a></li>-->
        <#--</ul>-->
        <#--</div>-->
        <#--</div>-->
            <table class="table table-striped table-bordered table-hover" id="pageData">
                <#if NPT_ACTION_RETURNED_JSON??>
                    <#assign data = NPT_ACTION_RETURNED_JSON>
                    <#if data.totalCount gt 0>
                        <thead>
                        <tr>
                            <th style="width:30px">序号</th>
                            <#list data.columnTitles as title>
                                <#if title_index!=0>
                                    <th width="${80/(data.columnTitles?size-1)}%">${title!}</th>
                                </#if>
                            </#list>
                            <th style="width: 30px">操作</th>
                        </tr>
                        </thead>
                    </#if>
                </#if>
                <tbody style="display:none">
                    <#include "../customModel/grs_main_field_list.ftl">
                </tbody>
            </table>
            <div id="pagination" style="display:none"></div>
        </div>
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
<div id="detailDiv"></div>


<div id="modalDiv" class="modal hide fade " tabindex="-1" data-width="900">

</div>

<script>
    function showSecurityFunction() {
    <#list Session["_curentMenuFunctions"] as funcName>
        $(".${funcName}").show();
    </#list>
    }
</script>
<script type="text/javascript" src="${wctx}/pub/baseModel/grs_basemodel_nonlegal_query_controller.js"></script>
</body>
<!-- END BODY -->

</html>