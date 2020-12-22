<#include "/template/Credit_Common/common.ftl">
<#assign data =  NPT_ACTION_RETURNED_JSON>

<#if  data.dataList??>
<div class="portlet-body form">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>查询条件</div>
            <div class="tools">
                <button class="btn mini" onclick="backIndex()"><i class="icon-long-arrow-left"></i> 返回</button>
            </div>
        </div>
        <div class="portlet-body form">
            <form action="#" class="form-horizontal" id="searchList">
                <div class="row-fluid">
                    <#if data.textBoxes??&&data.textBoxes?size!=0>
                        <#list data.textBoxes as textBox>
                            <div class="span4"  <#if textBox_index%3==1>style="margin-left: 0"</#if>>
                                <div class="control-group">
                                    <label class="ow control-label">${textBox.title} </label>
                                    <input type="hidden" id="textName_${textBox_index}" value="${textBox.name}">
                                    <div class="ow controls">
                                        <input type="text" class="m-wrap small" id="textValue_${textBox_index}">
                                    </div>
                                </div>
                            </div>
                        </#list>
                        <input id="textBoxCount" type="hidden" value="${data.textBoxes?size!0}">
                    </#if>

                    <#if data.dropBoxList??&&data.dropBoxList?size!=0>
                        <#list data.dropBoxList as dropBox>
                            <div class="span4"
                                 <#if (data.textBoxes?size + dropBox_index)%3 ==1>style="margin-left: 0"</#if>>
                                <div class="control-group">
                                    <label class="ow control-label">${dropBox.title} </label>
                                    <input type="hidden" id="dropName_${dropBox_index}" value="${dropBox.name}">
                                    <div class="ow controls">
                                        <select class="small m-wrap" id="dropValue_${dropBox_index}"
                                                onchange="baseFilter(${data.dropBoxList?size},'${data.primaryKeyValue!}')">
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
                <#--<div class="form-actions">-->
                    <#--<button type="button" class="btn blue" onclick="search()"><i class="icon-search icon-white"></i>&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询-->
                    <#--</button>-->
                    <#--<button class="btn" onclick="backIndex()"><i class="m-icon-swapleft"></i> 返回</button>-->
                    <#--</button>-->
                <#--</div>-->
            </form>
        </div>
    </div>
</div>
<div id="filterInfo">
    <#include "../customModel/grs_group_info_list.ftl">
</div>
<#else>
<div class="page-title">
    <h3>暂无数据</h3>
</div>

</#if>
<script>
    App.initUniform();
</script>