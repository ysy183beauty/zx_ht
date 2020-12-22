<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
</head>
<body>
<div class="widget-box">

	<div class="widget-content nopadding" >
		<div class="portlet box boxTheme">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-globe"></i>
					角色管理
				</div>
                <div class="tools">
                    <button class="btn purple mini" onclick="addRolePage()"><i class="icon-plus icon-white"></i> 新增</button>
                </div>
			</div>
			<div class="portlet-body" id="indexPage">
                <table id="roleList" class="table table-bordered data-table dataTable table-hover">
                    <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th width="20%">角色名称</th>
                        <th width="30%">角色描述</th>
                        <th width="20%">状态</th>
                        <th width="25%">操作管理</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#include "rms_role_list.ftl">
                    </tbody>
                </table>
			</div>
		</div>
	</div>
</div>
<div id="handleRole" class="modal hide fade" tabindex="-1" data-width="600"></div>
	<script type="text/javascript">
       $(function () {
           TableManaged.init("roleList");
           $("#roleList").colResizable({
               liveDrag:true,
               draggingClass:"dragging"
           });
       });

       /**
        * 新增角色页面
        */
       function addRolePage() {
           $.post("addPage.action",function (data) {
               $("#handleRole").modal("show").html(data);
           })
       }
       /**
        * 获取选中权限
        */
       function handlePermission(){
           var permission = [];
           $(".ms-selection li").each(function () {
               if($(this).is(":visible")){
                   var str = $(this).attr("id");
                   var i = str.indexOf("-");
                   permission.push(str.substring(0,i));
               }
           })
           $("input[name='rolePermissions']").val(permission.join(","));
       }
       /**
        * 打开编辑页面
        */
       function showEditPage(id) {
           $.post("editPage.action",{roleId:id},function (data) {
               $("#handleRole").modal("show").html(data);
           });
       }
       /**
        * 处理角色表单
        * @param form
        * @returns {boolean}
        */
       function handleRoleForm(form) {
           handlePermission();
           $.post(form.action,$(form).serialize(),function (data) {
               if (data.result){
                   returnInfo(true,data.message||"操作成功！");
                   $("#handleRole").modal("hide");
                   refreshRoleList();
               }else {
                   returnInfo(false,data.message||"操作失败！");
               }

           });
           return false;
       }
       /**
        * 刷新角色列表
        */
       function  refreshRoleList() {
           $.post("list.action",function (data) {
               TableManaged.draw("roleList",data);
           });
       }
       /**
        * 禁用角色
        * @param id
        */
       function disableRole(id) {
            $.post("disabled.action",{roleId:id},function () {
                refreshRoleList();
            });
       }
       /**
        * 启用角色
        * @param id
        */
       function enableRole(id) {
           $.post("enabled.action",{roleId:id},function(){
                refreshRoleList();
           });
       }
	</script>

</body>
</html>