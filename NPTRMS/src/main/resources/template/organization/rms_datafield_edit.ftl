<#include "/template/Credit_Common/common.ftl">
<form id="addForm" action="${ctx}/npt/rms/datafield/edit.action" class="form-horizontal no-bottom-space" role="form" onsubmit="return submitForm(this)"
     >
    <input name="id" type="hidden" value="${(data.id)?string("#")}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>修改字段</h4>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <label class="control-label">名称</label>
            <div class="controls">
                <input name="data.fieldName" type="text" class="m-wrap medium" readonly value="${data.fieldName!}">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">别名</label>
            <div class="controls">
                <input name="data.alias" type="text" maxlength="15" class="m-wrap medium" value="${data.alias!}">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">数据库表名</label>
            <div class="controls">
                <input name="data.fieldDbName" type="text" readonly class="m-wrap medium" value="${data.fieldDbName!}">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">状态</label>
            <div class="controls">
                <#if data.status??>
                    <label class="radio"><input name="data.status" type="radio" value="1" ${(data.status==1)?string("checked","")}>启用</label>
                    <label class="radio"><input name="data.status" type="radio" value="0" ${(data.status==1)?string("","checked")}>禁用</label>
                <#else >
                    <label class="radio"><input name="data.status" type="radio" value="1" checked>启用</label>
                    <label class="radio"><input name="data.status" type="radio" value="0" >禁用</label>
                </#if>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">公开级别</label>
            <div class="controls">
            <#if data.pubLevel??>
                <label class="radio"><input name="data.pubLevel" type="radio" value="0" ${(data.pubLevel==0)?string("checked","")}>授权访问</label>
                <label class="radio"><input name="data.pubLevel" type="radio" value="1" ${(data.pubLevel==1)?string("checked","")}>政务公开</label>
                <label class="radio"><input name="data.pubLevel" type="radio" value="2" ${(data.pubLevel==2)?string("checked","")}>社会公开</label>
            <#else >
                <label class="radio"><input name="data.pubLevel" type="radio" value="0" >授权访问</label>
                <label class="radio"><input name="data.pubLevel" type="radio" value="1" checked>政务公开</label>
                <label class="radio"><input name="data.pubLevel" type="radio" value="2" >社会公开</label>
            </#if>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">显示方式</label>
            <div class="controls">
            <#if data.showStyle??>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_COMMON_TEXT" ${(data.showStyle=="FSS_COMMON_TEXT")?string("checked","")}>普通文本</label>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_PARTHIDE_TEXT" ${(data.showStyle=="FSS_PARTHIDE_TEXT")?string("checked","")}>部分显示</label>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_DATE" ${(data.showStyle=="FSS_DATE")?string("checked","")}>日期类型</label>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_CODE" ${(data.showStyle=="FSS_CODE")?string("checked","")}>码表转换</label>
             <#else >
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_COMMON_TEXT"  checked>普通文本</label>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_PARTHIDE_TEXT" >部分显示</label>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_DATE"  >日期类型</label>
                <label class="radio"><input name="data.showStyle" type="radio" value="FSS_CODE">码表转换</label>
            </#if>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">创建时间</label>
            <div class="controls">
                <input name="data.createTime" readonly type="text" class="m-wrap medium" value="${data.createTime?string("yyyy-MM-dd HH:mm:ss")}">
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