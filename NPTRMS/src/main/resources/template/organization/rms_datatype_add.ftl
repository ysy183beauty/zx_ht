<#include "/template/Credit_Common/common.ftl">
<div class="dataTables_wrapper" role="grid" >
	<div class="ui-toolbar">
		<button type="button" class="btn " onclick="backToContent();"><i class="m-icon-swapleft"></i> 返回</button>
	</div>
</div>
<form id="addForm" action="add.${urlExt}" class="form-horizontal" role="form" onsubmit="return submitForm(this)" style="margin-top: 15px;">
    <div class="control-group">
        <label class="control-label">机构名称</label>
        <div class="controls" id="parentList">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">资产名称</label>
        <div class="controls">
            <input name="data.assetName" required type="text" class="m-wrap medium" maxlength="30" placeholder="请输入资产名称">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">数据库表名</label>
        <div class="controls">
            <input name="data.assetDbName"  required type="text" class="m-wrap medium" maxlength="30" placeholder="请输入数据库表名">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">关键字</label>
        <div class="controls">
            <input name="data.keyword"  type="text" class="m-wrap medium" maxlength="30" placeholder="请输入关键字">
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">状态</label>
        <div class="controls ">
            <label class="radio"><input name="data.status"  type="radio"  value="1" checked="checked">启用</label>
            <label class="radio"><input name="data.status"  type="radio" value="0">禁用</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">是否可视</label>
        <div class="controls">
            <label class="radio"><input name="data.pubLevel"  type="radio"  value="1" checked="checked">可视</label>
            <label class="radio"><input name="data.pubLevel"  type="radio" value="0">不可视</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">信用类型</label>
        <div class="controls">
            <label class="radio"><input name="data.creditType"  type="radio"  value="1" checked="checked">人口类型</label>
            <label class="radio"><input name="data.creditType"  type="radio" value="0">企业类型</label>
        </div>
    </div>

  <div class="form-actions">
      <button type="submit" class="btn blue"><i class="icon-ok"></i> 保存</button>
      <button type="button" class="btn" onclick="backToContent();"><i class="m-icon-swapleft"></i> 返回</button>
  </div>
</form>
<script type="text/javascript">
    App.initUniform();
</script>