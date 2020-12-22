<div class="portlet box boxTheme">
    <div class="portlet-title">
        <div class="caption">
            <i class="icon-globe"></i>
            数据池管理【！每个模型分组只能存在一个主数据池，若要变更，请先取消已存在的主数据池！】
        </div>
        <div class="tools">
            <button class="btn green mini" onclick="dragSortGrouPool()">激活拖动排序</button>
            <button class="btn yellow mini" onclick="saveSortGrouPool()">更新顺序</button>
            <button class="btn purple mini" onclick="addPoolPage('${_THIS_GROUP_ID}')"><i class="icon-plus icon-white"></i> 新增</button>
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>
    <div class="portlet-body" id="poolList">
        <#include "grs_model_groupool_data_list.ftl">
    </div>
</div>