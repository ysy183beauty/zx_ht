<#include "/template/Credit_Common/common.ftl"/>
<form id="addForm" class="form-horizontal" role="form" onsubmit="return submitCheckAddContent(this)"
      style="margin-bottom: 0px;">

    <div class="control-group">
        <label class="ow control-label">模板标题</label>
        <div class="ow controls">
            <input name="data.rptName" required type="text" class="form-control" maxlength="12"
                   placeholder="请输入模板标题，不可超过12个汉字">
        </div>
    </div>

    <div class="control-group">
        <label class="ow control-label">模板说明</label>
        <div class="ow controls">
            <textarea name="data.rptNote" type="text" class="form-control" maxlength="100"
                      ></textarea>
        </div>
    </div>

    <div class="control-group">
        <label required class="ow control-label">添加模板</label>
        <div class="ow controls">
            <input name="data.rptPath" type="hidden">
            <div id="upload_file"></div>
        </div>
    </div>

    <div class="control-group">
        <label required class="ow control-label">模板类型</label>
        <div class="ow controls">
            <select name="data.rptCategory" class="form-control">
            <#list reportsCategory as d>
                <option value="${d.code}">${d.title}</option>
            </#list>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label required class="ow control-label">模板主体</label>
        <div class="ow controls">
            <select name="data.rptHost" class="form-control">
            <#list reportsHost as d>
                <option value="${d.code}">${d.title}</option>
            </#list>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label required class="ow control-label">展示方式</label>
        <div class="ow controls">
        <#list reportsStyle as d>
            <label class="radio">
                <input class="m-wrap" name="data.showStyle" type="radio" value="${d.code}"
                    <#if d?is_first>
                       checked="checked"
                    </#if>
                >${d.title}
            </label>
        </#list>
        </div>
    </div>
    <div class="control-group" id="menuIndex" style="display: none">
        <label required class="ow control-label">选择菜单</label>
        <div class="ow controls">
            <select name="data.menuIndex" class="form-control">
        <#assign l=0..(MAX_MENU_INDEX-1)/>
        <#list l as d>
            <label class="radio">
                <option value="${d}">菜单${d}</option>
            </label>
        </#list>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label class="ow control-label">访问控制</label>
        <div class="ow controls">
        <#list reportsPubLevel as d>
            <label class="radio">
                <input class="m-wrap" name="data.pubLevel" type="radio" value="${d.code}"
                       <#if d?is_last>
                       checked="checked"
                       </#if>
                >${d.title}
            </label>
        </#list>
        </div>
    </div>

    <div class="control-group">
        <label class="ow control-label">权限设置</label>
        <div class="ow controls">
            <#list grantRoles as r>
                <label class="checkbox-inline"><input type="checkbox" name="rolename"
                                                      value="${r.name}">${r.title}</label>
            </#list>
        </div>
    </div>

    <div class="form-actions">
        <div class="ow controls">
            <input name="data.status" value="0" type="hidden">
            <button class="btn blue">保存</button>
            <button class="btn blue" onclick="publish()">保存并发布</button>
            <button type="button" class="btn btn-default backIndexPage">返回</button>
        </div>
    </div>
</form>
<script>
    App.initUniform();
    $(function () {
        $("#upload_file").initUploader({
            contextPath : "${ctx}",
            name : "upload_file",
            file_size_limit : "80MB", //每次上传的文件大小，必须小于struts中设置的值
            file_types : "*.cpt", //限制上传文件的类型，如:"*.txt",多种文件类型:"*.xls;*.xlsx"
            file_types_description : "cpt模板文件", //在文件选择对话框中显示的允许上传的文件类型
            file_upload_limit: 1
        });
    });
</script>