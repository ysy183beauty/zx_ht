<#include "/template/Credit_Common/common.ftl">
<form id="editForm" class="form-horizontal" role="form" onsubmit="return submitCheckEditContent(this)" style="margin-bottom: 0px;">
	<div class="control-group">
		<label class="ow control-label">模板标题</label>
		<div class="ow controls">
			<input name="data.rptName" required type="text" class="form-control" value="${data.rptName!}" maxlength="12" placeholder="请输模板标题，不可超过12字符">
		</div>
	</div>
    <div class="control-group">
        <label class="ow control-label">模板说明</label>
        <div class="ow controls">
            <textarea name="data.rptNote" type="text" class="form-control" maxlength="100"
                      >${data.rptNote!}</textarea>
        </div>
    </div>
	<div class="control-group">
		<label for="firstname" class="ow control-label">添加模板</label>
        <div class="ow controls">
            <input name="data.rptPath" type="hidden" value="${data.rptPath!}">
            <div id="upload_file"></div>
        </div>
	</div>
    <div class="control-group">
        <label for="firstname" class="ow control-label">模板类型</label>
        <div class="ow controls">
            <select id="reportCategory" name="data.rptCategory" required class="form-control">
			<#list reportsCategory as d>
                <option value="${d.code}">${d.title}</option>
			</#list>
            </select>
        </div>
    </div>
    <div class="control-group">
        <label for="firstname" class="ow control-label">模板主体</label>
        <div class="ow controls">
            <select id="reportHost" name="data.rptHost" required class="form-control">
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
                <input id="data_showStyle_${d.code}" class="m-wrap" name="data.showStyle" type="radio" value="${d.code}"
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
            <select id="reportMenuIndex" name="data.menuIndex" class="form-control">
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
                <input id="data_pubLevel_${d.code}" class="m-wrap" name="data.pubLevel" type="radio" value="${d.code}"
					<#if !d?has_next>
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
			<label class="checkbox-inline"><input type="checkbox" name="rolename" value="${r.name}" ${roleNames?seq_contains(r.name)?string('checked','')}>${r.title}</label>
		</#list>
		</div>
	</div>
	<div class="form-actions">
  	<div class="ow controls">
  		<input name="data.id" type="hidden" value="${data.id!}">
  		<input id="addRoles" name="addRoles" type="hidden">
  		<input id="delRoles" name="delRoles" type="hidden">
        <input name="data.status" value="0" type="hidden">
        <button id="contentSaveButton" class="btn blue">保存</button>
        <button class="btn blue" onclick="publish()">保存并发布</button>
	    <button type="button" class="btn btn-default backIndexPage">返回</button>
  	</div>
  </div>
</form>
<script type="text/javascript">
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
	(function(){
		 $("input[name='rolename']").click(function(){
			 $(this).parent().toggleClass("checkRole");
			 $(this).toggleClass("checkRole");
		 });
		 $("#data_pubLevel_${data.pubLevel}").attr("checked", true);
		 $("#data_showStyle_${data.showStyle}").attr("checked", true);
	<#if data.menuIndex??>
        $("#reportMenuIndex").val(${data.menuIndex});
        $("#menuIndex").show();
	</#if>
		 $("#reportPath").val('${data.rptPath!}');
        $("#reportCategory").val('${data.rptCategory!}');
        $("#reportHost").val('${data.rptHost!}');

		 if(${data.pubLevel} == 2){
			 $("input[name='rolename']").attr("disabled", false);
		 }else{
			 $("input[name='rolename']").attr("disabled", true);
		 }
	})();
	function createUserCheck(){
		var addRoles = [];
		var delRoles = [];
		 $("input[name='rolename'].checkRole").each(function(){
			 if($(this)[0].checked){
				 addRoles.push(this.value);
			 }else{
				 delRoles.push(this.value);
			 }
		 });
		 $("#addRoles").val(addRoles.join(','));
		 $("#delRoles").val(delRoles.join(','));
	}
</script>