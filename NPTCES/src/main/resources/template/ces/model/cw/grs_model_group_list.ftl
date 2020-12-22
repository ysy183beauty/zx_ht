<div class="portlet box boxTheme">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-globe"></i>
            维度分组管理
        </div>
        <div class="tools">
            <#--<button class="btn green mini" onclick="dragSortGroup()">激活拖动排序</button>-->
            <#--<button class="btn yellow mini" onclick="saveSortGroup()">更新顺序</button>-->
            <#--<button class="btn purple mini" onclick="addGroupPage('${_THIS_MODEL_ID}')"><i class="icon-plus icon-white"></i> 新增</button>-->
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>
    <div class="portlet-body" id="groupDiv">
     <#include "grs_model_group_data_list.ftl">
    </div>
</div>
<script>

    App.handleTooltips();
</script>