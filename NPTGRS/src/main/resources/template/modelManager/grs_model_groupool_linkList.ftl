<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4>关联模型管理</h4>
</div>
<div class="modal-body">
    <div class="scroller" data-height="470">
    <#if  _POOL_LINK_LIST??&&_POOL_LINK_LIST?size gt 0>
        <table class="table table-bordered data-table dataTable table-hover">
            <thead>
            <tr>
                <th width="8%">序号</th>
                <th width="10%">标题</th>
                <th width="20%">业务外键</th>
                <th width="25%">关联数据池</th>
                <th width="15%">数据来源</th>
                <th width="10%">状态</th>
                <th width="12%">操作管理</th>
            </tr>
            </thead>
            <tbody>
                <#list _POOL_LINK_LIST as c>
                <tr>
                    <td>${c_index+1}</td>
                    <td align="left">${c.linkTitle!}</td>
                    <td align="left">${c.poolRefKeyTitle!}</td>
                    <td align="left">${c.toPoolTitle!}</td>
                    <td>${c.toPoolProviderTitle!}</td>
                    <td>
                        <#if c.status == 1>
                           启用
                        <#else >
                            禁用
                        </#if>
                    </td>
                    <td>
                        <#if c.status == 1>
                            <a class="btn mini red tooltips" href="javascript:void(0);" data-placement="bottom" data-original-title="停用"
                               onclick="setPoolLinkStatus('${c.id?string("#")}','IDS_DISABLED',this)"><i
                                    class="icon-stop"></i></a>
                        <#else>
                            <a class="btn mini  green tooltips" href="javascript:void(0);" data-placement="bottom" data-original-title="启用"
                               onclick="setPoolLinkStatus('${c.id?string("#")}','IDS_ENABLED',this)"><i
                                    class="icon-play"></i></a>
                        </#if>
                        <a class="btn mini red tooltips" href="javascript:void(0);" data-placement="bottom"
                           data-original-title="删除" onclick="deletePoolLink('${c.id?string("#")}',this)"><i
                                class="icon-trash"></i></a>

                    </td>
                </tr>
                </#list>
            </tbody>
        </table>
    <#else >
        <div class="page-title">
            <h3>暂无数据</h3>
        </div>
    </#if>
    </div>
</div>
<div class="modal-footer">
    <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i> &nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
</div>
<script>
    App.handleTooltips();
</script>