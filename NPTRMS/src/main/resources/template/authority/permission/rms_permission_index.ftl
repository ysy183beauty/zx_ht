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
                    权限管理
				</div>
                <div class="tools">
                    <button class="btn purple mini" onclick="addPermissionPage()"><i class="icon-plus icon-white"></i> 新增</button>
                    <a href="javascript:;" class="collapse"></a>
                </div>
			</div>
			<div class="portlet-body" id="indexPage">
                <table id="permissionList" class="table table-bordered data-table dataTable table-hover">
                    <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th width="20%">权限名称</th>
                        <th width="30%">所属机构</th>
                        <th width="20%">状态</th>
                        <th width="25%">操作管理</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#include "rms_permission_list.ftl">
                    </tbody>
                </table>
			</div>
		</div>
	</div>
</div>
<div id="handleRole" class="modal hide fade" tabindex="-1" data-width="600"></div>
	<script type="text/javascript">
       $(function () {
           TableManaged.init("permissionList");
           $("#permissionList").colResizable({
               liveDrag:true,
               draggingClass:"dragging"
           });
       });

       /**
        * 新增权限页面
        */
       function addPermissionPage() {
           $.post("addPage.action",function (data) {
               $("#handleRole").modal("show").html(data);
           })
       }

       /**
        * 处理权限表单
        * @param form
        * @returns {boolean}
        */
       function handlePermissionForm(form) {
           $.post(form.action,$(form).serialize(),function (data) {
               if (data.result){
                   returnInfo(true,data.message||"操作成功！");
                   $("#handleRole").modal("hide");
                   refreshPermissionList();
               }else {
                   returnInfo(false,data.message||"操作失败！");
               }

           });
           return false;
       }
       /**
        * 刷新权限列表
        */
       function  refreshPermissionList() {
           $.post("list.action",function (data) {
               TableManaged.draw("permissionList",data);
           });
       }

       /**
        * 修改权限状态
        * @param id
        * @param status
        */
       function setPermissionStatus(id,status) {
           $.post(
                   "setPermissionStatus.action",
                   {
                       permissionId:id,
                       status:status
                   },
                   function (data) {
                       refreshPermissionList();
                   }
           )
       }

	</script>
</body>
</html>