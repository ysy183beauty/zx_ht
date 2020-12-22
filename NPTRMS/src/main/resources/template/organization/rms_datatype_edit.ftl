<#include "/template/Credit_Common/common.ftl">
<div class="dataTables_wrapper" role="grid">
	<div class="ui-toolbar">
		<button type="button" class="btn " onclick="backToContent();"><i class="m-icon-swapleft"></i> 返回</button>
	</div>
</div>
<form id="editForm" action="edit.${urlExt}" class="form-horizontal" role="form" onsubmit="return submitForm(this)">
    <div class="control-group">
        <label required class="control-label">机构名称</label>
        <div class="controls" id="parentList">
            <input name="data.orgId" type="text" required class="m-wrap medium" maxlength="11" value="${data.orgId!}" readonly>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">资产名称</label>
        <div class="controls">
            <input name="data.assetName" required type="text" class="form-control" maxlength="30" value="${data.assetName!}" placeholder="请输入资产名称">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">数据库表名</label>
        <div class="controls">
            <input name="data.assetDbName"  required type="text" class="form-control" maxlength="30" value="${data.assetDbName!}" placeholder="请输入数据库表名">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">关键字</label>
        <div class="controls">
            <input name="data.keyword"  type="text" class="form-control" maxlength="30" value="${data.keyword!}" placeholder="请输入关键字">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">状态</label>
        <div class="controls ">
            <label class="radio"><input name="data.status"  type="radio"  value="1" ${(data.status==1)?string('checked','')}>启用</label>
            <label class="radio"><input name="data.status"  type="radio"  value="0" ${(data.status==1)?string('','checked')}>禁用</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否可视</label>
        <div class="controls ">
            <label class="radio"><input name="data.pubLevel"  type="radio"  value="1" ${(data.pubLevel==1)?string('checked','')}>可视</label>
            <label class="radio"><input name="data.pubLevel"  type="radio"  value="0" ${(data.pubLevel==1)?string('','checked')}>不可视</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">信用类型</label>
        <div class="controls ">
            <#if data.creditType??>
                <label class="radio"><input name="data.creditType"  type="radio"  value="1"  ${(data.creditType==1)?string('checked','')}>人口类型</label>
                <label class="radio"><input name="data.creditType"  type="radio"  value="0"  ${(data.creditType==1)?string('','checked')}>企业类型</label>
            <#else >
                <label class="radio"><input name="data.creditType"  type="radio"  value="1"  checked>人口类型</label>
                <label class="radio"><input name="data.creditType"  type="radio"  value="0"  >企业类型</label>
            </#if>
        </div>
    </div>

    <div class="form-actions">
		<input name="data.assetId" type="hidden" value="${data.assetId!}">
        <button type="submit" id="contentSaveButton" class="btn blue"><i class="icon-ok"></i> 保存</button>
	    <button type="button" class="btn" onclick="backToContent();"><i class="m-icon-swapleft"></i>  返回</button>
  </div>
</form>
<script type="text/javascript">
    App.initUniform();
</script>