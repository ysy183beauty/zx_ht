<#include "/template/Credit_Common/common.ftl">

<div class="dataTables_wrapper" role="grid">

    <button type="button" class="btn margin-bottom-10" onclick="backToContent();"><i class="m-icon-swapleft"></i> 返回</button>

    <p class="pull-right">
        <button type="button" class="btn red-stripe" onclick="editTb(this)"><i class="icon-edit "></i> <span>修改</span></button>
        <a class="btn blue-stripe" data-toggle="modal" href="#addUserDiv"><i class="icon-user"></i> 新增用户</a>
        <a class="btn purple-stripe" onclick="addOrg()" data-toggle="modal" href="#addOrgDiv"><i class="icon-bookmark"></i> 新增机构</a>
    </p>

</div>
<div class="portlet  box boxTheme">
    <div class="portlet-title">
        <div class="caption"><i class="icon-tags"></i> 机构信息</div>
        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>
    <div class="portlet-body">
        <form id="editForm" action="edit.${urlExt}" class="form-horizontal" role="form"
              onsubmit="return submitForm(this)">
            <table class="table table-bordered table-hover" id="orgTb">
                <tbody>
                <tr>
                    <td width="20%"><b>机构名称</b></td>
                    <td width="30%" class="data.orgName">${data.orgName!}</td>
                    <td width="20%"><b>英文缩写</b></td>
                    <td width="30%" class="data.orgCode">${data.orgCode!}</td>
                </tr>
                <tr>
                    <td><b>机构描述</b></td>
                    <td class="data.orgNote">${data.orgNote!}</td>
                    <td><b>机构地址</b></td>
                    <td class="data.orgAddr">${data.orgAddr!}</td>
                </tr>
                <tr>
                    <td><b>机构电话</b></td>
                    <td class="data.orgTel">${data.orgTel!}</td>
                    <td><b>机构邮箱</b></td>
                    <td class="data.orgMail">${data.orgMail!}</td>
                </tr>
                <tr>
                    <td><b>机构网址</b></td>
                    <td class="data.orgSite">${data.orgSite!}</td>
                    <td><b>机构微信号</b></td>
                    <td class="data.orgWechart">${data.orgWechart!}</td>
                </tr>
                <tr>
                    <td><b>机构状态</b></td>
                    <td class="data.status">${(data.status==1)?string('启用','禁用')}</td>
                    <td><b>是否可视</b></td>
                    <td class="data.pubLevel">${(data.pubLevel==1)?string('可视','不可视')}</td>
                </tr>
                <input name="id" type="hidden" value="${data.id!}">
                <input name="data.parentId" type="hidden" value="${data.parentId!}">
                </tbody>
            </table>
        </form>
    </div>
</div>
<div class="portlet box boxTheme">
    <div class="portlet-title">
        <div class="caption"><i class="icon-group"></i> 机构成员</div>
        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>
    <div class="portlet-body" id="orgUserList">

</div>
</div>
<!---------------------------------->
<div id="addUserDiv" class="modal hide fade" tabindex="-1" data-width="560">
    <#include "rms_org_userAdd.ftl">
</div>

<script type="text/javascript">
    orgUser(${data.id!});

    /**
     * 修改机构信息
     * **/
    function editTb(obj) {

        $(obj).attr("onclick", "saveTb(this)").html("<i class='icon-ok '></i> 保存");
        var tds = $("#editDiv #orgTb td");
        for (var i = 0; i < tds.length; i++) {
            if (i % 2 != 0) {
                if (tds[i].className == "data.status") {

                    tds[i].innerHTML = '<div class="controls" style="margin-left: 0"> ' +
                            '<label class="radio">' +
                            '<input type="radio" name="' + tds[i].className + '" value="1" ' + (tds[i].innerHTML == "启用" ? "checked" : "") + ' >启用</label>' +
                            ' <label class="radio"><input type="radio" name="' + tds[i].className + '"  ' + (tds[i].innerHTML == "启用" ? "" : "checked") + ' value="0">禁用</label>' +
                            '</div>';
                }
                else if (tds[i].className == "data.pubLevel") {

                    tds[i].innerHTML = '<div class="controls" style="margin-left: 0"> ' +
                            '<label class="radio">' +
                            '<input type="radio" name="' + tds[i].className + '" value="1" ' + (tds[i].innerHTML == "可视" ? "checked" : "") + ' >可视</label>' +
                            ' <label class="radio"><input type="radio" name="' + tds[i].className + '"  ' + (tds[i].innerHTML == "可视" ? "" : "checked") + ' value="0">不可视</label>' +
                            '</div>';
                }
                else {
                    tds[i].innerHTML = "<input type='text' name='" + tds[i].className + "' value='" + tds[i].innerHTML + "'>";
                }
            }
        }
        App.initUniform();
    }

    /***
     * 保存机构信息修改
     * **/
    function saveTb(obj) {
        $.post("edit.action", $("#editForm").serialize(), function (data) {
            if (data.result) {
                var zTree = $.fn.zTree.getZTreeObj("orgTree");
                var nodes = zTree.getSelectedNodes();
                var treeNode = nodes[0];
                treeNode.name = $("input[name='data.orgName']").val();
                zTree.updateNode(treeNode, false);

                editData('${data.id?string("#")!}');
                $(obj).attr("onclick", "editTb(this)").html("<i class='icon-edit '></i> 修改机构");
                returnInfo(true,data.message||"操作成功！");
            } else {
                returnInfo(false,data.message || "操作失败！");
            }
        });
        return false;
    }
    /**
     * 新增机构用户
     * */
    function addOrgUser(id) {
        $.post("${ctx}/npt/rms/user/list.action", {departmentId: id}, function (data) {
            $("#addOrgDiv").modal("show").html(data);
        })
    }


    /**
     * 提交新增数据
     * **/
    function addOrgUserFrom(form) {
        $.post("${ctx}/npt/rms/user/add.action", $(form).serialize(), function (data) {
            if (data.result) {
                $("#addUserDiv").on("hide",function () {
                    editData(${data.id!});
                });
                $("#addUserDiv").modal("hide");
                returnInfo(true,data.message||"操作成功！");
            } else {
                returnInfo(false,"保存失败：" + data.message);
            }
        });
        return false;
    }


    /**
     * 禁用用户
     * @param id
     */
    function disabledUser(id){
        $.post("${ctx}/npt/rms/user/disabled.action",{id:id},function(data){
            editData(${data.id!});
        });
    }
    /**
     * 启用用户
     * @param id
     */
    function enabledUser(id){
        $.post("${ctx}/npt/rms/user/enabled.action",{id:id},function(data){
            editData(${data.id!});
        });
    }
    /**
     * 显示当前机构下的用户列表
     * @param id
     */
    function orgUser(id){
        $.post("${ctx}/npt/rms/user/listByOrg.action",{orgId:id},function (data) {
            $("#orgUserList").html(data);
            TableManaged.init("orgUserListTb");
            $("#orgUserListTb").colResizable({
                liveDrag:true,
                draggingClass:"dragging"
            });
        })
    }

</script>