<#if  _GROUP_POOL_LIST??&&_GROUP_POOL_LIST?size gt 0>
<table class="table table-bordered data-table dataTable table-hover" id="poolTb">
    <thead>
    <tr>
        <th width="5%">序号</th>
        <th width="20%">数据类别</th>
        <th width="20%">数据来源</th>
        <th width="10%">业务主键</th>
        <th width="10%">主数据池</th>
        <th width="10%">关联模型</th>
        <th width="10%">加锁级别</th>
        <th width="5%">状态</th>
        <th width="10%">操作</th>
    </tr>
    </thead>
    <tbody id = "modelPoolListTBody">
    <#list _GROUP_POOL_LIST as c>
    <tr id="${c.modelPool.id}">
        <td>${c?index+1}</td>
        <td align="left">${c.modelPool.poolTitle}</td>
        <td>${c.modelPool.providerTitle}</td>
        <td>${c.modelPool.pFieldTitle}</td>
        <td>
            <#if c.modelPool.mainPool == 0>
                是
            <#else >
                否
            </#if>
        </td>
        <td>
        ${c.modelPool.linkedPoolCount!}
            <#if c.modelPool.linkedPoolCount gt 0>
                <a  class="btn mini tooltips green" href="javascript:void(0);"  onclick="loadPoolLinks('${c.modelPool.id}')" data-placement="bottom" data-original-title="查看"><i class="icon-search"  ></i> </a>
            </#if>
        </td>
        <td>
            <#if c.modelPool.lockLevel == 0>
                无限制
            <#elseif c.modelPool.lockLevel == 1 >
                一级锁
            <#elseif c.modelPool.lockLevel == 2 >
                二级锁
            <#elseif c.modelPool.lockLevel == 3 >
                三级锁
            </#if>
        </td>
        <td>${(c.modelPool.status==1)?string('启用','禁用')!} </td>
        <td>
            <#--<a  class="btn mini tooltips blue" href="javascript:void(0);" onclick="setMainPool('${c.id}','${c.modelId}')" data-placement="bottom" data-original-title="设为主数据池"><i class="icon-home"  ></i></a>-->
            <div class="controls">
                <a class="btn mini green tooltips" href="javascript:void(0)" onclick="editSubDmsPropsPage('${(c.poolProperties.id)!}','${c.modelPool.id}')" data-placement="bottom" data-original-title="编辑数据池属性"><i class="icon-edit "></i></a>
                <#--<a  class="btn mini tooltips red" href="javascript:void(0);"  onclick="deletePool('${c.id}')" data-placement="bottom" data-original-title="删除数据池"><i class="icon-trash"  ></i></a>-->
                <a  class="btn mini tooltips yellow"  href="#myModal" data-toggle="modal" data-id="${c.modelPool.id}" data-placement="bottom" data-original-title="修改备注"><i class="icon-pencil"  ></i></a>
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
    function dragSortGrouPool() {
        /**
         * 表格拖拽排序
         * */
        var modelPoolListTBody = document.getElementById("modelPoolListTBody");
        Sortable.create(modelPoolListTBody, { group: "omega" });
    }
    /**
     * 更新字段显示顺序
     */
    function saveSortGrouPool() {
        var sortedIdList = [];
        $('#modelPoolListTBody>tr').each(function () {
            sortedIdList.push($(this).attr('id'));
        });

        $.post(
                "/npt/grs/model/sortGrouPool.action",
                {sortedIdList: sortedIdList.join(",")},
                function () {

                }
        )
    }
    App.handleTooltips();
    TableManaged.init("poolTb");
    $("#poolTb").colResizable({
        liveDrag:true,
        draggingClass:"dragging"
    });
    /**
     * 显示备注框
     */
    function showSummernote(){
        $("#editorList").show();
    }
</script>
</#if>
