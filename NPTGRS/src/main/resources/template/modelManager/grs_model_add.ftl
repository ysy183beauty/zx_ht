<form action="addModelBaseInfo.action" onsubmit="return addModelBaseInfo(this)" class="form-horizontal no-bottom-space" >
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>新增模型</h4>
    </div>
    <div class="modal-body">
        <div class="alert alert-info">
            <button class="close" data-dismiss="alert"></button>
            <strong>提示：</strong>
            创建基础模型的基本信息，为模型指定一个唯一的名称，设置该模型所属的信息实体，并指定该模型是属于内部模型还是自定义模型
        </div>
        <div class="control-group">
            <label class="control-label">模型名称<span class="required">*</span></label>
            <div class="controls">
                <input name="modelName" required type="text" class=" medium" maxlength="10"
                       placeholder="请输模型名称，不可超过10字符">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">模型描述<span class="required">*</span></label>
            <div class="controls">
                <textarea name="modelNote"   class=" medium" maxlength="256"
                          placeholder="请输模型描述，不可超过256字符"></textarea>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">信用实体<span class="required">*</span></label>
            <div class="controls">
                <select name="modelHost"   class="small m-wrap">
                <#list _MODEL_HOST_LIST as c>
                    <option value="${c.name()}">${c.title}</option>
                </#list>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">模型类别<span class="required">*</span></label>
            <div class="controls">
                <select name="modelCatelog"  class="small m-wrap">
            <#list _MODEL_CATE_LIST as c>
                <option value="${c.name()}">${c.title}</option>
            </#list>
            </select>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="submit" class="btn blue"><i class="icon-ok"></i> 确 定</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i> 关 闭</button>
    </div>
</form>
