<#if  _MODEL_GROUP_LIST??&&_MODEL_GROUP_LIST?size gt 0>
<table  class="table table-bordered data-table dataTable table-hover" id="groupTb">
    <thead>
    <tr>
        <th width="5%">序号</th>
        <th width="10%">维度名称</th>
        <th width="10%">维度描述</th>
        <th>预警上限</th>
        <th>预警下限</th>
        <th>权重</th>
        <th width="10%">状态</th>
        <th width="10%">是否为主分组</th>
        <th width="15%">操作管理</th>
    </tr>
    </thead>
    <tbody id="groupTbList">
        <#list _MODEL_GROUP_LIST as c>
        <tr id="${c.modelGroup.id}">
            <td>${c?index+1}</td>
            <td align="left">${c.modelGroup.groupName!}</td>
            <td align="left">${(c.groupProperties.note)!}</td>
            <td>${(c.groupProperties.upLimit)!}</td>
            <td>${(c.groupProperties.lowLimit)!}</td>
            <td>${(c.groupProperties.disCount)!}</td>
            <td>${(c.modelGroup.status==1)?string('启用','禁用')!}</td>
            <td>
                <#if c.modelGroup.mainGroup == 0>
                    是
                <#else >
                    否
                </#if>
            </td>
            <td>
                <div class="controls">
                    <a class="btn mini green tooltips" href="javascript:void(0)" onclick="editDimensionPropsPage('${(c.groupProperties.id)!}','${c.modelGroup.id}')" data-placement="bottom" data-original-title="编辑分组属性"><i class="icon-edit "></i></a>
                    <a class="btn mini blue tooltips" href="javascript:void(0);" onclick="configGroup('${c.modelGroup.id}')" data-placement="bottom" data-original-title="查看子维度"><i class="icon-tasks"></i></a>
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
        var groupTbList = document.getElementById("groupTbList");
        Sortable.create(groupTbList, { group: "omega" });
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
                function () {

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