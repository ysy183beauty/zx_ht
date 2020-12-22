<form action="setRole.action" class="form-horizontal no-bottom-space" role="form"
      onsubmit="return handleRoleForm(this)">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-tag"></i> 分配角色</h4>
    </div>
    <div class="modal-body">
        <div>
            <div class="controls">
                <div class="allPermission">所有角色</div>
                <div class="ownPermission">添加角色</div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">角色列表</label>
            <div class="controls">
                <select multiple="multiple" id="roleMultiple">
                <#list _ROLE_LIST as c>
                    <option value="${c.id?string("#")}">${c.roleName!}</option>
                </#list>
                </select>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <input type="hidden" name="userId">
        <input type="hidden" name="roles">
        <button type="submit" class="btn blue saveBtn"><i class="icon-ok"></i>&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存
        </button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭
        </button>
    </div>
</form>
<script>
    $('#roleMultiple').multiSelect();
    <#if _ROLE_ADDED_LIST?? && _ROLE_ADDED_LIST?length gt 0>
    $('#roleMultiple').multiSelect('select',[<#list _ROLE_ADDED_LIST as c>'${c.id?string("#")}'<#sep>,</#sep></#list>]);
    </#if>
</script>