<#include "/template/Credit_Common/common.ftl">

<form id="addUser" class="form-horizontal no-bottom-space" role="form" onsubmit="return addOrgUserFrom(this)">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>新增用户</h4>
    </div>
    <div class="modal-body">
    <div class="control-group">
        <label class="control-label">登录名<span class="required">*</span></label>
        <div class="controls">
            <input name="user.loginName" required type="text" class=" medium" maxlength="30"
                   placeholder="请输登录名，不可超过30字符">
            <span class="help-inline">必填</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名<span class="required">*</span></label>
        <div class="controls">
            <input name="user.userName" required type="text" class=" medium" maxlength="10"
                   placeholder="请输入用户姓名，不可超过10汉字">
            <span class="help-inline">必填</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">性别</label>
        <div class="controls">
            <label class="radio"><input name="user.sex" type="radio" value="男" checked>男</label>
            <label class="radio"><input name="user.sex" type="radio" value="女">女</label>
        </div>
    </div>
    <input name="user.password" type="hidden" required class=" medium" maxlength="11"
           value="123456">
    <input name="user.department" type="hidden" required class=" medium" maxlength="11"
           value="${data.id}">

    <div class="control-group">
        <label class="control-label">手机号码</label>
        <div class="controls">
            <input name="user.mobile" type="text" class=" medium" maxlength="11" placeholder="请输入手机号码">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">电子邮件</label>
        <div class="controls">
            <input name="user.email" type="text" class=" medium" maxlength="11" placeholder="请输入电子邮件">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户描述</label>
        <div class="controls">
            <textarea name="user.description" rows="3" class="medium " placeholder="请输入用户描述"></textarea>
        </div>
    </div>
    </div>
    <div class="modal-footer">
        <button type="submit" class="btn blue saveBtn"><i class="icon-ok"></i>&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭
        </button>
    </div>
</form>
<script type="text/javascript">
    App.initUniform();
</script>