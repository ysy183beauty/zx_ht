<#include "/template/Credit_Common/common.ftl">
<form id="form_props" action="editModelProps.${urlExt}" method="post" onsubmit="return editProps(this, refreshModelList)" class="form-horizontal no-bottom-space" >
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
    <input type="hidden" name="modelProps.id" value="${(modelProps.id)!}">
    <input type="hidden" name="modelProps.modelId" value="${(modelProps.modelId)!}">
    <div class="control-group">
        <label class="control-label">预警上限值<span class="required">*</span></label>
        <div class="controls"><input type="number" min="0" max="100" class="span6 m-wrap" name="modelProps.upLimit"
                                     value="${(modelProps.upLimit)!}"></div>
    </div>
    <div class="control-group">
        <label class="control-label">预警下限值<span class="required">*</span></label>
        <div class="controls"><input type="number" min="0" max="100" class="span6 m-wrap" name="modelProps.lowLimit"
                                     value="${(modelProps.lowLimit)!}"></div>
    </div>
    <div class="control-group">
        <label class="control-label">业务主键标题</label>
        <div class="controls"><input type="text" class="span6 m-wrap" name="modelProps.cIT"
                                     value="${(modelProps.cIT)!}"></div>
    </div>
    <div class="control-group">
        <label class="control-label">信用实体标题</label>
        <div class="controls"><input type="text" class="span6 m-wrap" name="modelProps.cTT"
                                     value="${(modelProps.cTT)!}"></div>
    </div>
</div>
<div class="modal-footer">
    <button class="btn blue ok" type="submit"><i class="icon-ok"></i> 保 存 </button>
    <button class="btn" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i> 取 消 </button>
</div>
</form>