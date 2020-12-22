<!DOCTYPE html>
<html>
<head>
<#include "/template/Credit_Common/common.ftl">
<script src="${wctx}/pub/CreditStyle/react/15.5.4/react.min.js"></script>
<script src="${wctx}/pub/CreditStyle/react/15.5.4/react-dom.min.js"></script>
<script src="${wctx}/pub/CreditStyle/babel-core/5.8.24/browser.min.js"></script>
<link href="${wctx}/pub/CreditStyle/antd/2.10.4/antd.min.css" rel="stylesheet">
<script src="${wctx}/pub/CreditStyle/antd/2.10.4/antd.min.js"></script>
<style>
    *,:after,:before{box-sizing:unset!important;}
</style>
</head>
<body>
<div class="dataTables_wrapper" role="grid">
    <div class="ui-toolbar">
        <a class="btn " href="index.${urlExt}"><i class="m-icon-swapleft"></i> 返回</a>
    </div>
</div>
<form id="editForm" action="edit.${urlExt}" class="form-horizontal" role="form">
    <div class="control-group">
        <label  class="control-label">登录名</label>
        <div class="controls">
            <input name="user.loginName" readonly type="text" class="m-wrap medium" maxlength="30" value="${user.loginName!}" placeholder="请输登录名，不可超过30字符">
        </div>
    </div>
    <div class="control-group">
        <label  class="control-label">姓名<span class="required">*</span></label>
        <div class="controls">
            <input name="user.userName" required type="text" class="m-wrap medium" maxlength="10" value="${user.userName!}" placeholder="请输入用户姓名，不可超过10汉字">
            <span class="help-inline">必填</span>
        </div>
    </div>
    <div class="control-group">
        <label  class="control-label">性别</label>
        <div class="controls ">
            <#if user.sex??>
                <label class="radio"><input name="user.sex" type="radio" value="男" ${(user.sex=="男")?string("checked","")}>男</label>
                <label class="radio"><input name="user.sex" type="radio" value="女" ${(user.sex=="女")?string("checked","")}>女</label>
                <#else >
                    <label class="radio"><input name="user.sex" type="radio" value="男" checked>男</label>
                    <label class="radio"><input name="user.sex" type="radio" value="女" >女</label>
            </#if>

        </div>
    </div>
    <div class="control-group">
        <label  class="control-label">所属机构</label>
        <div class="controls" id="parentList">
            <div id="department" class="m-wrap medium"></div>
            <input name="user.department" type="hidden" value="${user.orgId!}" >
        </div>
    </div>
    <div class="control-group">
        <label  class="control-label">手机号码</label>
        <div class="controls">
            <input name="user.mobile" type="text" class="m-wrap medium" maxlength="11" value="${user.mobile!}"  placeholder="请输入手机号码">
        </div>
    </div>
    <div class="control-group">
        <label  class="control-label">电子邮件</label>
        <div class="controls">
            <input name="user.email" type="text" class="m-wrap medium" maxlength="11" value="${user.email!}" placeholder="请输入电子邮件">
        </div>
    </div>
    <div class="control-group">
        <label  class="control-label">用户描述</label>
        <div class="controls">
            <textarea name="user.remark" rows="3" class="medium m-wrap" >${user.remark!}</textarea>
        </div>
    </div>

    <div class="form-actions">
            <input name="id" type="hidden" value="${user.userId!}">
            <input id="addRoleIds" name="addRoleIds" type="hidden">
            <input id="delRoleIds" name="delRoleIds" type="hidden">
            <button type="submit" id="contentSaveButton" class="btn blue"><i class="icon-ok"></i> 保存</button>
            <a class="btn" href="index.${urlExt}"><i class="m-icon-swapleft"></i> 返回</a>
    </div>
</form>
<script type="text/javascript">
    $(function () {
        App.initUniform();
    });

    (function () {
        $("input[name='rolename']").click(function () {
            $(this).parent().toggleClass("checkRole");
            $(this).toggleClass("checkRole");
        });
    })();
    function createUserCheck() {
        var addRoleIds = [];
        var delRoleIds = [];
        $("input[name='rolename'].checkRole").each(function () {
            if ($(this)[0].checked) {
                addRoleIds.push(this.value);
            } else {
                delRoleIds.push(this.value);
            }
        });
        $("#addRoleIds").val(addRoleIds.join(','));
        $("#delRoleIds").val(delRoleIds.join(','));
    }
    /**
     * 编辑提交表单
     */
    $("#editForm").submit(function(event){
        event.preventDefault ? event.preventDefault() : (event.returnValue = false);
        createUserCheck();
        $.post("edit.${urlExt}", $("#editForm").serialize(),function(data){
            if(data.result){
                returnInfo(true,data.message||"操作成功！");
                window.location.href="index.${urlExt}";
            }else{
                returnInfo(false,"保存失败：" + data.message);
            }
        });
    });
</script>
<script type="text/babel">
    const {TreeSelect} = antd;

    const treeData = [{
        <#assign org = rootOrg>
        label: '${org.alias}',
        value: '${org.id}',
        key: '${org.id}',
        children: [
            <#list ncmOrgs as org>
        {
            label: '${org.alias}',
            value: '${org.id}',
            key: '${org.id}',
        }<#sep>,</#sep></#list>]}];

    class App extends React.Component {
        constructor(props) {
            super(props);
            this.state = {
                value: '${user.orgId!}',
            };
            this.onChange = this.onChange.bind(this);
        }

        onChange(value) {
            $("input[name='user.department']").val(value);
            this.setState({value});
        }

        render() {
            return (
                    <TreeSelect
                            <#--style={{width: 220}}-->
                            value={this.state.value}
                            dropdownStyle={{maxHeight: 400, overflow: 'auto'}}
                            treeData={treeData}
                            placeholder="请选择机构"
                            treeDefaultExpandAll
                            onChange={this.onChange}
                    />
            );
        }
    }

    ReactDOM.render(<App />, document.getElementById('department'));
</script>
</body>
</html>