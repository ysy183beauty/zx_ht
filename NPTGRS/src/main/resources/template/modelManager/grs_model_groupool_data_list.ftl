
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
    <tbody id = "modelGroupListTBody">
    <#list _GROUP_POOL_LIST as c>
    <tr id="${c.id?string("#")}">
        <td>${c_index+1}</td>
        <td align="left">${c.poolTitle}</td>
        <td>${c.providerTitle}</td>
        <td>${c.pFieldTitle}</td>
        <td>
            <#if c.mainPool == 0>
                是
            <#else >
                否
            </#if>
        </td>
        <td>
        ${c.linkedPoolCount!}
            <#if c.linkedPoolCount gt 0>
                <a  class="btn mini tooltips green" href="javascript:void(0);"  onclick="loadPoolLinks('${c.id?string("#")}')" data-placement="bottom" data-original-title="查看"><i class="icon-search"  ></i> </a>
            </#if>
        </td>
        <td>
            <#if c.lockLevel == 0>
                无限制
            <#elseif c.lockLevel == 1 >
                一级锁
            <#elseif c.lockLevel == 2 >
                二级锁
            <#elseif c.lockLevel == 3 >
                三级锁
            </#if>
        </td>
        <td>${(c.status==1)?string('启用','禁用')!} </td>
        <td>
            <#--<a  class="btn mini tooltips blue" href="javascript:void(0);" onclick="setMainPool('${c.id?string("#")}','${c.modelId?string("#")}')" data-placement="bottom" data-original-title="设为主数据池"><i class="icon-home"  ></i></a>-->
            <div class="controls">
                <a  class="btn mini tooltips green" href="javascript:void(0);"  onclick="configPool('${c.id?string("#")}')" data-placement="bottom" data-original-title="编辑数据池"><i class="icon-edit"  ></i></a>
                <a  class="btn mini tooltips red" href="javascript:void(0);"  onclick="deletePool('${c.id?string("#")}')" data-placement="bottom" data-original-title="删除数据池"><i class="icon-trash"  ></i></a>
                <a  class="btn mini tooltips yellow"  href="#myModal" data-toggle="modal" data-id="${c.id}" data-placement="bottom" data-original-title="修改备注"><i class="icon-pencil"  ></i></a>
            </div>
          </td>
    </tr>
    </#list>
       </tbody>
</table>




<script>
    $(function () {
    <#if _groupPoolPage?exists>
    var page =${_groupPoolPage};
    <#else >
     var page = 0;
    </#if>
        goPage(page);
    });
    /**
     * 拖拽排序
     */
    function dragSortGrouPool() {
        /**
         * 表格拖拽排序
         * */
        //var modelGroupListTBody = document.getElementById("modelGroupListTBody");
        //Sortable.create(modelGroupListTBody, { group: "omega" });
        $("#poolTb").tableDnD();
    }
    /**
     * 更新字段显示顺序
     */
    function saveSortGrouPool() {
        var sortedIdList = [];
        $('#modelGroupListTBody>tr').each(function () {
            sortedIdList.push($(this).attr('id'));
        });

        $.post(
                "/npt/grs/model/sortGrouPool.action",
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
    TableManaged.init("poolTb");
    $("#poolTb").colResizable({
        liveDrag:true,
        draggingClass:"dragging"
    });

    function goPage(page) {
        var table = $('#poolTb').dataTable();
        table.api().page(page*1-1).draw(false);
    }

    function getPage() {
        var page = $('#poolTb_paginate').find('li .active').get(0).text();
       return page;
    }


    /**
     * 显示备注框
     */
    function showSummernote(){
        $("#editorList").show();
    }
</script>
</#if>
