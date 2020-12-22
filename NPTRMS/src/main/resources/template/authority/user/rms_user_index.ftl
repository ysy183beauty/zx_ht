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
					用户管理
				</div>
                <div class="tools">
                    <button class="btn purple mini" id="addUser"><i class="icon-plus icon-white"></i> 新增</button>
                </div>
			</div>
			<div class="portlet-body" id="indexPage">
                <table id="userList" class="table table-bordered table-responsive dataTable table-hover">
                    <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th width="15%">登录名</th>
                        <th width="15%">姓名</th>
                        <th width="10%">状态</th>
                        <th width="25%">所属单位</th>
                        <th width="25%">操作管理</th>
                    </tr>
                    </thead>
                    <tbody>
					<#include "rms_user_list.ftl">
                    </tbody>
                </table>
			</div>
            <div class="portlet-body form" id="formDiag"></div>
		</div>
	</div>

</div>
<div id="handleRole" class="modal hide fade" tabindex="-1" data-width="600"></div>
	<script type="text/javascript">
        $(function () {
            TableManaged.init("userList");
            $("#userList").colResizable({
                liveDrag:true,
                draggingClass:"dragging"
            });
        });
       /**
        * 刷新用户列表
        * */
        function refreshList() {
            $.post("list.${urlExt}", function (data) {
                TableManaged.draw("userList",data)
                }
            );
        }
        /**
         * 返回主页面
         * **/
        function backIndexPage() {
            $("#formDiag").hide();
            $("#indexPage").show();
        }
        /**
         * 显示新增/编辑页面
         * */
        function showFirmDiag(data){
            $("#formDiag").html(data);
            $("#indexPage").hide();
            $("#formDiag").show();
        }

		$("#addUser").click(function(){
			$.post("addPage.action",function(data){
                showFirmDiag(data);
				num=0;
                ajaxParent(-1);
			});
		});
		/**
		* 添加提交表单
		*/
		submitCheckAddContent = function (form){
            var flag = checkSelect();
            if (flag){
                $.post("add.action", $("#addForm").serialize(),function(data){
                    if(data.result){
                        backIndexPage()
                        refreshList();
                        returnInfo(true,data.message||"操作成功！");
                    }else{
                        returnInfo(false,"保存失败：" + data.message);
                    }
                });
            }
			return false;
		}
        /**
         *编辑用户
         * */
		function editUser(id){
//			$.post("editPage.action",{id:id},function(data){
//                showFirmDiag(data);
//            });
            window.location.href = "modifyPage.${urlExt}?id=" + id;
		}
        /**
         *禁用用户
         * */
		function disabledUser(id, name){
			$.post("disabled.action",{id:id},function(data){
                refreshList();
			});
		}
        /**
         *启用用户
         * */
		function enabledUser(id, name){
			$.post("enabled.action",{id:id},function(data){
                refreshList();
			});
		}

        var num;
        /**
         *加载机构列表
         * */
        function ajaxParent(id) {

            $.post("${ctx}/npt/rms/org/showOrgTreeByParentId.${urlExt}", {parentId: id}, function (data) {

                if (data.length != 0) {
                    var nameStr;
                    if (num == 0) {
                        nameStr ="user.department"
                    }
                    var select = '<select class="small m-wrap"  style="float: left; margin-right: 10px;" id="addRoot' + num + '" onchange="rootChange(this)" name="'+nameStr+'" ><option value="-1">请选择</option>';
                    for (var i = 0; i < data.length; i++) {
                        select += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                    }

                    $("#parentList").append(select + "</select>");
                    num++;
                }

            });
        }
        /**
         *  机构级联
         * */
        function rootChange(obj) {
            var index = obj.id.substring(7, 8) - 1;
            if ($("#" + obj.id).val() == -1) {
                $("#parentList select:gt(" + (index+1) + ")").remove()
            }
            else {
                ajaxParent($("#" + obj.id).val());
            }
            changeName("parentList", obj.id);
        }
        /**
         *   动态存储机构id
         * */
        function changeName(id, index) {
            $("#" + id + " select").attr("name", "");
            if ($("#" + index).val() == -1) {
                $("#" + id + " select:nth-last-child(2)").attr("name", "user.department");
            }
            else {
                $("#" + id + " select:last-child").attr("name", "user.department");
            }

        }
        /**
         * 更改权限
         */
        function showRole(obj){
            $(obj).attr("onclick","hideRole(this)").html("确定");
            $("#userRoleTitle").hide();
            $(".userRoleTitleList").show().change(function () {
                if(0 ==$(this).val()){
                    $("#contentSaveButton").prop("disabled",true);
                    $("#userRoleTitle").html($(this).find("option:selected").text());
                    $("#userRoleTitle").parent().parent().addClass("error");
                }else {
                    $("#contentSaveButton").prop("disabled",false);
                    $("input[name=roleLevel]").val($(this).val());
                    $("#userRoleTitle").html($(this).find("option:selected").text());
                    $("#userRoleTitle").parent().parent().removeClass("error");
                }

            });
        }
        /**
         * 保存权限
         */
        function hideRole(obj) {
            $(obj).attr("onclick","showRole(this)").html("更改");
            $(".userRoleTitleList").hide();
            $("#userRoleTitle").show();
        }
        /**
         * 校验是否选择部门
         */
        function checkSelect(){
            if ($("select[name='user.department']").val() == -1){
                returnInfo(false,"请选择部门分类！");
                return false
            }
            return true;
        }

        /**
         * 分配角色页面
         */
        function setRolePage(userId) {
            $.post("setRolePage.action",{userId: userId},function (data) {
                $("#handleRole").modal("show").html(data);
                $("input[name='userId']").val(userId);
            })
        }
        /**
         * 获取选中角色
         */
        function handleRole(){
            var role = [];
            $(".ms-selection li").each(function () {
                if($(this).is(":visible")){
                    var str = $(this).attr("id");
                    var i = str.indexOf("-");
                    role.push(str.substring(0,i));
                }
            });
            $("input[name='roles']").val(role.join(","));
        }

        /**
         * 处理角色表单
         * @param form
         * @returns {boolean}
         */
        function handleRoleForm(form) {
            handleRole();
            $.post(form.action,$(form).serialize(),function (data) {
                if (data.result){
                    returnInfo(true,data.message||"操作成功！");
                    $("#handleRole").modal("hide");
                }else {
                    returnInfo(false,data.message||"操作失败！");
                }

            });
            return false;
        }
	</script>
</body>
</html>