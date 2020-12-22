<#include "/template/Credit_Common/common.ftl">
<form id="addForm" action="${ctx}/npt/rms/datafield/batchUpdate.action" class="form-horizontal no-bottom-space" role="form" onsubmit="return submitForm(this)"
     >
    <input name="ids" type="hidden" value="<#list _DATA_FIELDS as data>${(data.id)?string("#")}<#sep>,</#sep></#list>">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>批量修改字段</h4>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <label class="control-label">名称</label>
            <div class="controls">
                <input name="data.fieldName" type="text" class="m-wrap large" readonly value="<#list _DATA_FIELDS as data>${data.fieldName!}<#sep>,</#sep></#list>">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">状态</label>
            <div class="controls">
                <label class="radio"><input name="data.status" type="radio" value="1" checked>启用</label>
                <label class="radio"><input name="data.status" type="radio" value="0" >禁用</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">公开级别</label>
            <div class="controls">
                <label class="radio"><input name="data.pubLevel" type="radio" value="0" >授权访问</label>
                <label class="radio"><input name="data.pubLevel" type="radio" value="1" checked>政务公开</label>
                <label class="radio"><input name="data.pubLevel" type="radio" value="2" >社会公开</label>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">显示方式</label>
            <div class="controls">
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_COMMON_TEXT"  checked>普通文本</label>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_PARTHIDE_TEXT" >部分显示</label>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_DATE"  >日期类型</label>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_CODE">码表转换</label>
            </div>
        </div>

    </div>
    <div class="modal-footer">
        <button type="submit" class="btn blue saveBtn"><i class="icon-ok"></i>&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>
</form>
<script>
    App.initUniform();
</script>