<div class="portlet-body form">
    <!-- BEGIN FORM-->
    <form action="#" class="form-horizontal" id="searchList">
        <div class="row-fluid">
        <#assign data = NPT_ACTION_RETURNED_JSON>
        <#if data.textBoxes?size!=0>
            <#list data.textBoxes as textBox>
                <div class="span4"  <#if textBox_index%3==1>style="margin-left: 0"</#if>>
                    <div class="control-group">
                        <label class="ow control-label"
                               title="${textBox.title} ">${textBox.title} </label>
                        <input type="hidden" id="textName_${textBox_index}" value="${textBox.name}">
                        <div class="ow controls">
                            <input type="text" class="m-wrap small" id="textValue_${textBox_index}">
                        </div>
                    </div>
                </div>
            </#list>
            <input id="textBoxCount" type="hidden" value="${data.textBoxes?size!0}">
        </#if>

        <#if data.dropBoxList?size!=0>
            <#list data.dropBoxList as dropBox>
                <div class="span4"
                     <#if (data.textBoxes?size + dropBox_index)%3 ==1>style="margin-left: 0"</#if>>
                    <div class="control-group">
                        <label class="ow control-label"
                               title="${dropBox.title}">${dropBox.title} </label>
                        <input type="hidden" id="dropName_0" value="${dropBox.name}">
                        <div class="ow controls">
                            <select class="small m-wrap" id="dropValue_${dropBox_index}">
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
                <th style="width: 30px">操作</th>
            </tr>
            </thead>
        </#if>
    </#if>
        <tbody>
        <#include "../customModel/grs_main_field_list.ftl">
        </tbody>
    </table>
    <div id="pagination"></div>
</div>