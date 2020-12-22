<#include "/template/Credit_Common/common.ftl">
<div class="dataTables_wrapper" role="grid">
    <div class="ui-toolbar">
        <button class="btn " onclick="backIndexPage()"><i class="m-icon-swapleft"></i> 返回</button>
    </div>
</div>
<form id="addForm" class="form-horizontal" role="form" onsubmit="return submitCheckAddContent(this)"
      style="margin-top: 15px;">
    <div class="control-group">
        <label class="control-label">登录名<span class="required">*</span></label>
        <div class="controls">
            <input name="user.loginName" required type="text" class="m-wrap medium" maxlength="30"
                   placeholder="请输登录名，不可超过30字符">
            <span class="help-inline">必填</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">姓名<span class="required">*</span></label>
        <div class="controls">
            <input name="user.userName" required type="text" class="m-wrap medium" maxlength="10"
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
    <div class="control-group">
        <label class="control-label">登陆密码<span class="required">*</span></label>
        <div class="controls">
            <input name="user.password" type="password" required class="m-wrap medium" maxlength="11"
                   placeholder="请输入密码">
            <span class="help-inline">必填</span>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">部门分类</label>
        <div class="controls" id="parentList">

        </div>
    </div>
    <div class="control-group">
        <label class="control-label">手机号码</label>
        <div class="controls">
            <input name="user.mobile" type="text" class="m-wrap medium" maxlength="11" placeholder="请输入手机号码">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">电子邮件</label>
        <div class="controls">
            <input name="user.email" type="text" class="m-wrap medium"  placeholder="请输入电子邮件">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">用户描述</label>
        <div class="controls">
            <textarea name="user.remark" rows="3" class="medium" placeholder="请输入用户描述"></textarea>
        </div>
    </div>

    <#--<div class="control-group">-->
        <#--<label class="control-label">系统角色</label>-->
        <#--<div class="controls">-->
            <#--<select name="user.role"></select>-->
        <#--</div>-->
    <#--</div>-->
    <div class="form-actions">
        <button type="submit" class="btn blue"><i class="icon-ok"></i> 保存</button>
        <button type="button" class="btn "  onclick="backIndexPage()"><i class="m-icon-swapleft"></i> 返回</button>
    </div>
</form>
<script>
    App.initUniform();
</script>