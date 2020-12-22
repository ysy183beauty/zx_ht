<form action="editRole.action" class="form-horizontal no-bottom-space" role="form" onsubmit="return handleRoleForm(this)">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>修改角色</h4>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <label class="control-label">角色名<span class="required">*</span></label>
            <div class="controls">
                <input name="roleName" required type="text" class="m-wrap medium" maxlength="10"
                       placeholder="请输入角色名，不可超过10字符" value="${_ROLE_INFO.roleName!}">
                <span class="help-inline">必填</span>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">角色描述<span class="required">*</span></label>
            <div class="controls">
                <input name="roleNote" required type="text" class="m-wrap medium" maxlength="30"
                       placeholder="请输入角色描述，不可超过30字符" value="${_ROLE_INFO.roleNote!}">
                <span class="help-inline">必填</span>
            </div>
        </div>
        <div>
            <div class="controls">
                <div class="allPermission">可添加权限</div>
                <div class="ownPermission">已有权限</div>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">权限列表</label>
            <div class="controls">
                <select multiple="multiple" id="roleMultiple" name="my_multi_select1">

                        <#if _ROLE_PERMISSION_LIST??&&_ROLE_PERMISSION_LIST?size gt 0>
                            <#list _ROLE_PERMISSION_LIST as b>
                              <option value="${b.id?string("#")}" selected>${b.permissionTitle!}</option>
                            </#list>
                            <#list _OTHER_PERMISSION_LIST as a>
                                <option value="${a.id?string("#")}" >${a.permissionTitle!}</option>
                            </#list>
                         <#else >
                             <#list _PERMISSION_LIST as c>
                                 <option value="${c.id?string("#")}">${c.permissionTitle!}</option>
                             </#list>
                        </#if>
                </select>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <input type="hidden" name="roleId" value="${_ROLE_INFO.id?string("#")}">
        <input type="hidden"  name="rolePermissions">
        <button type="submit" class="btn blue saveBtn"><i class="icon-ok"></i>&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭
        </button>
    </div>
</form>
<script>
    $('#roleMultiple').multiSelect();
</script>