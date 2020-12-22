<form action="addModelGroup.action" onsubmit="return addGroup(this)" class="form-horizontal no-bottom-space" >
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>新增分组</h4>
    </div>
    <div class="modal-body">
        <div class="alert alert-info">
        <button class="close" data-dismiss="alert"></button>
        <strong>提示：</strong>
        从该模型的业务角度考虑，将预计要载入到该模型中的数据源进行分组，比如以自然人为例，可分为【基本信息组】，【家庭关系组】等，并选择其中一个分组作为当前模型的主分组。
        </div>
        <div class="control-group">
            <label class="control-label">分组名称<span class="required">*</span></label>
            <div class="controls">
                <input name="groupName" required type="text" class=" medium" maxlength="10"
                       placeholder="请输模型名称，不可超过10字符">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">分组描述<span class="required">*</span></label>
            <div class="controls">
                <input name="groupNote" required type="text" class=" medium" maxlength="30"
                       placeholder="请输模型名称，不可超过30字符">
            </div>
        </div>
        <input type="hidden" name="parentId" value="${_THIS_MODEL_ID}">
    </div>
    <div class="modal-footer">
        <button type="submit" class="btn blue"><i class="icon-ok"></i> 确 定</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i> 关 闭</button>

    </div>
</form>
