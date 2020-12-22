<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4>同步指定数据池数据</h4>
</div>
<div class="modal-body">
    <div class="scroller" data-height="470">
        <table class="table table-striped table-hover table-bordered">
            <thead>
            <tr>
                <th width="10%">序号</th>
                <th width="20%">标题</th>
                <th width="10%">上次同步时间</th>
                <th width="5%">操作</th>
            </tr>
            </thead>
            <tbody>
            <#list pools as pool>
            <tr>
                <td>${pool_index+1}</td>
                <td>${pool.poolTitle}</td>
                <td>
                <#list poolStamps as poolStamp>
                    <#if pool.dataTypeId == poolStamp.poolId>
                    ${poolStamp.lastTime!}<#break>
                    </#if>
                </#list>
                </td>
                <td><input type="checkbox" name="poolId[]" value="${pool.id}"></td>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
<div class="modal-footer">
    <button type="submit" class="btn blue"><i class="icon-refresh"></i>&nbsp;&nbsp;同&nbsp;&nbsp;&nbsp;&nbsp;步</button>
    <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
</div>
