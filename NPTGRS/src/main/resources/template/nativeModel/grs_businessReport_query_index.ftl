<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
    <script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/colResizable-1.5.min.js" type="text/javascript"></script>
</head>
<body class="page-header-fixed">

<div id="indexDiv">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>企业信用报告查询</div>
            <div class="tools">
                <a href="javascript:;" class="collapse"></a>
            </div>
        </div>
        <#if NPT_ACTION_RETURNED_JSON??>
            <#assign data = NPT_ACTION_RETURNED_JSON>
            <div class="portlet-body form">
                <!-- BEGIN FORM-->
                <form action="#" class="form-horizontal" id="searchList">
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
                                        <select class=" m-wrap" id="dropValue_${dropBox_index}">
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
                        <div class="form-actions">
                            <button type="button" class="btn blue" onclick="search()"><i class="icon-search icon-white"></i>&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询
                            </button>
                            <button type="reset" class="btn"><i class="icon-repeat"></i>&nbsp;&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置
                            </button>
                        </div>
                </form>
                <!-- END FORM-->
            </div>
            <div class="portlet-body">
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
                                <th style="width: 82px">操作</th>
                            </tr>
                            </thead>
                        </#if>
                    </#if>
                    <tbody>
                        <#include "../customModel/grs_mainReport_field_list.ftl">
                    </tbody>
                </table>
                <div id="pagination"></div>
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
<div id="logDiv"></div>


<div id="modalDiv" class="modal hide fade " tabindex="-1" data-width="560">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>请选择模板</h4>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <div class="">
                <input id="selected_rpbs" hidden>
                <select id="template">
                    <option value="41">企业信用报告</option>
                    <option value="2">模板2</option>
                    <option value="21">模板3</option>
                </select>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn blue" onclick="showReport()">&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
        <button type="button" data-dismiss="modal" class="btn">&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>
</div>


<script>
    function showSecurityFunction() {
    <#list Session["_curentMenuFunctions"] as funcName>
        $(".${funcName}").show();
    </#list>
    }
</script>
<script type="text/javascript" src="${wctx}/pub/baseModel/grs_basemodel_businessReport_query_controller.js"></script>
</body>
<!-- END BODY -->

</html>