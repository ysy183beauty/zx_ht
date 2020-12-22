<#if  _MODEL_GROUP_LIST??&&_MODEL_GROUP_LIST?size gt 0>
<table  class="table table-bordered data-table dataTable table-hover" id="groupTb">
    <thead>
    <tr>
        <th width="5%">序号</th>
        <th width="25%">分组名称</th>
        <th width="35%">分组描述</th>
        <th width="10%">状态</th>
        <th width="10%">是否为主分组</th>
        <th width="15%">操作管理</th>
    </tr>
    </thead>
    <tbody id="groupTbList">
        <#list _MODEL_GROUP_LIST as c>
        <tr id="${c.id?string("#")}">
            <td>${c_index+1}</td>
            <td align="left">${c.groupName!}</td>
            <td align="left">${c.groupNote!}</td>
            <td>${(c.status==1)?string('启用','禁用')!}</td>
            <td>
                <#if c.mainGroup == 0>
                    是
                <#else >
                    否
                </#if>
            </td>
            <td>
                <div class="controls">
                    <a  class="btn mini tooltips green" href="javascript:void(0);"  onclick="editGroup('${c.id?string("#")}',this)"  data-placement="bottom" data-original-title="编辑分组"><i class="icon-edit"  ></i></a>
                    <a class="btn mini blue tooltips" href="javascript:void(0);" onclick="configGroup('${c.id?string("#")}')" data-placement="bottom" data-original-title="查看数据池"><i class="icon-tasks"></i></a>
                    <a  class="btn mini tooltips red delete" href="javascript:void(0);" onclick="deleteGroup('${c.id?string("#")}')" data-placement="bottom" data-original-title="删除分组"><i class="icon-trash"  ></i></a>
                </div>
            </td>
        </tr>
        </#list>
    </tbody>
</table>
<script>

    /**
     * 拖拽排序
     */
    function dragSortGroup() {
        /**
         * 表格拖拽排序
         * */
        //var groupTbList = document.getElementById("groupTbList");
        //Sortable.create(groupTbList, { group: "omega" });
        $("#groupTb").tableDnD();
    }
    /**
     * 更新字段显示顺序
     */
    function saveSortGroup() {
        var sortedIdList = [];
        $('#groupTbList>tr').each(function () {
            sortedIdList.push($(this).attr('id'));
        });

        $.post(
                "/npt/grs/model/sortGroup.action",
                {sortedIdList: sortedIdList.join(",")},
                function (data) {
                    if (data.result) {
                        returnInfo(true,data.message||"操作成功！");
                    }else{
                        returnInfo(false,data.message || "操作失败！");
                    }
                }
        )
    }

    App.handleTooltips();
    TableManaged.init("groupTb");
    $("#groupTb").colResizable({
        liveDrag:true,
        draggingClass:"dragging"
    });
</script>
</#if>