<#include "/template/Credit_Common/common.ftl">
<#if DATA_ARRAY??>
<#assign data =  DATA_ARRAY>
<div class="modal-header">
    <button data-dismiss="modal" class="close" type="button"></button>
    <h3><i class="icon-exclamation-sign"></i> 详细信息</h3>
</div>
<div class="modal-body" id="dataInfo_modal">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption">
                <i class="icon-globe"></i>
                企业详情
            </div>
            <div class="tools">
                <a href="javascript:" class="collapse" id="hidden_a"></a>
            </div>
        </div>
        <div class="portlet-body" id="computation">
            <div class="row-fluid">
                <div class="span4">
                    <div class="control-group">
                        <div class="ow controls">

                        </div>
                    </div>
                </div>
            </div>
            <table class="table table-bordered " id="dataInfo_modal_table">
            <tr>
                <#list data.dataArray as dataArray>

                    <th width="15%">${dataArray.title}：</th>
                    <td width="35%">${dataArray.value}</td>
                    <#if dataArray?index % 2 == 1>
                    </tr>
                    <tr>
                    </#if>
                </#list>
                <#if data.dataArray?size % 2 ==1>
                    <th></th>
                    <td></td>
                </#if>
            </tr>
            </table>
        </div>
    </div>

<#else >
<#--暂无数据-->
</#if>
</div>
<div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i> 关 闭</button>
</div>