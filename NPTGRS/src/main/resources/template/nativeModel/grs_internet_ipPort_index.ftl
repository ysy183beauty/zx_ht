<form action="updateRemoteServer.action" onsubmit="return updateRemoteServer(this)" class="form-horizontal no-bottom-space">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-legal"></i> 设置IP和端口</h4>
    </div>
    <div class="modal-body">
        <div class="control-group">
            <label class="control-label">IP</label>
            <div class="controls">
                <input name="remoteIp" required type="text" class=" medium"  value="<#if REMOTE_IP??>${REMOTE_IP!}</#if>">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">端口号</label>
            <div class="controls">
             <input name="remotePort" required type="text" class=" medium"  value="<#if REMOTE_PORT??>${REMOTE_PORT?string("#")!}</#if>">
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="submit" class="btn blue saveBtn"><i class="icon-ok"></i>&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>
</form>