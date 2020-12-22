<form action="addDSGroupoolLink.action" onsubmit="return addGroupoolLink(this)" class="no-bottom-space form-horizontal">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>自由模型</h4>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <div class="span4">
                <div class="control-group">
                    <label for="" class="ow control-label">分组</label>
                    <div class="ow controls">
                        <select class=" m-wrap" name="groupId" id="orgListFree">
                            <option value="">请选择</option>
                    <#if _MODEL_GROUP_LIST??>
                        <#list _MODEL_GROUP_LIST as c>
                            <option value="${c.id}">${c.groupName!}</option>
                        </#list>
                    </#if>
                        </select>
                    </div>
                </div>
            </div>
            <div class="span4">
                <div class="control-group">
                    <label for="" class="ow control-label">数据池</label>
                    <div class="ow  controls">
                        <select class=" m-wrap" name="toPoolId" id="poolListFree">
                            <option value="">请选择</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>

        <div class="scroller" data-height="470">
            <table class="table table-striped table-hover table-bordered" id="dataFieldFree">
                <thead>
                <tr>
                    <th width="10%">序号</th>
                    <th width="20%">字段名称</th>
                    <th width="10%">访问权限</th>
                    <th width="10%">数据类型</th>
                    <th width="10%">当前状态</th>
                    <th width="5%">关联</th>
                    <th width="30%">标题</th>
                    <th width="5%">隐式</th>
                </tr>
                </thead>
                <tbody id="modelFieldListTBodyFree">

                </tbody>
            </table>
        </div>
    </div>
    <div class="modal-footer">
        <input type="hidden" name="fromPoolId" value="${_POOL_ID}">
        <input type="hidden" name="fkFieldId" value="${_FK_FIELD_ID}">
        <button type="submit" class="btn blue"><i class="icon-ok"></i>&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>
</form>
<script>
    App.handleScrollers();
</script>