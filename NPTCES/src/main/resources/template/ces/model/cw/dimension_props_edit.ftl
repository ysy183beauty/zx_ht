<#include "/template/Credit_Common/common.ftl">
<form id="form_props" action="editDimensionProps.${urlExt}" method="post" onsubmit="return editProps(this, refreshGroupList)" class="form-horizontal no-bottom-space" >
<div class="modal-header">
    <button data-dismiss="modal" class="close" type="button"></button>
    <h3><i class="icon-exclamation-sign"></i> 编辑属性</h3>
</div>
<div class="modal-body">
    <div class="alert alert-error hide">
        <button class="close" data-dismiss="alert"></button>
        You have some form errors. Please check below.
    </div>
    <div class="alert alert-success hide">
        <button class="close" data-dismiss="alert"></button>
        Your form validation is successful!
    </div>
    <input type="hidden" name="dimensionProps.id" value="${(dimensionProps.id)!}">
    <input type="hidden" name="dimensionProps.groupId" value="${(dimensionProps.groupId)!}">
    <div class="control-group">
        <label class="control-label">预警上限值<span class="required">*</span></label>
        <div class="controls"><input type="number" min="0" max="100" class="span6 m-wrap" name="dimensionProps.upLimit"
                                     value="${(dimensionProps.upLimit)!}"></div>
    </div>
    <div class="control-group">
        <label class="control-label">预警下限值<span class="required">*</span></label>
        <div class="controls"><input type="number" min="0" max="100" class="span6 m-wrap" name="dimensionProps.lowLimit"
                                     value="${(dimensionProps.lowLimit)!}"></div>
    </div>
    <div class="control-group">
        <label class="control-label">预警值权重<span class="required">*</span></label>
        <div class="controls" id="percentBox">
            <input type="number" min="0" class="span6 m-wrap" name="dimensionProps.disCount" value="${(dimensionProps.disCount)!}">
            <span id="percent_wd">%</span>
        </div>

    </div>
    <div class="control-group">
        <label class="control-label">维度备注</label>
        <div class="controls"><input type="text" class="span6 m-wrap" name="dimensionProps.note"
                                     value="${(dimensionProps.note)!}"></div>
    </div>
</div>
<div class="modal-footer">
    <button class="btn blue ok" type="submit"><i class="icon-ok"></i> 保 存 </button>
    <button class="btn" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i> 取 消 </button>
</div>
</form>