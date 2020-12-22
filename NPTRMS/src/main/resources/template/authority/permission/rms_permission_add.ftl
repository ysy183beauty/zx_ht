<form action="add.action" class="form-horizontal no-bottom-space" role="form" onsubmit="return handlePermissionForm(this)">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-tag"></i> 新增权限</h4>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <label class="control-label">权限名<span class="required">*</span></label>
            <div class="controls">
                <select name="actionId" >
                <#list _ACTION_LIST as c>
                    <option value="${c.code?string("#")}">${c.title!}</option>
                </#list>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">所属机构<span class="required">*</span></label>
            <div class="controls">
                <select name="orgId" >
                    <#list _ORG_LIST as c>
                        <option value="${c.id?string("#")}">${c.orgName!}</option>
                    </#list>
                </select>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="submit" class="btn blue saveBtn"><i class="icon-ok"></i>&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭
        </button>
    </div>
</form>
<script>
    $('#roleMultiple').multiSelect();
</script>