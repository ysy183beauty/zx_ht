<#assign data =  NPT_ACTION_RETURNED_JSON>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4><i class="icon-legal"></i> 查看更多</h4>
</div>
<div class="modal-body ">
    <div class="  scroller" data-height="450px">
    <#if data.dataList?size == 0 || !data.dataList??>
        暂无数据
    <#else>
        <#list data.dataList as block>
            <#if (block.dataCount gt 0) >
                <#if block.dataArray??>
                    <#list block.dataArray as element>
                        <div class="portlet box boxTheme">
                            <div class="portlet-title">
                                    <span class="caption">
                                        <span class="label label-default"> ${element_index+1} </span>
                                        ${block.blockInfo.poolTitle!}
                                    </span>
                            </div>
                            <div class="portlet-body">
                                <table class="table table-bordered table-hover tbList">
                                    <tbody>
                                        <#list element.dataArray as fieldData>
                                        <tr>
                                            <td>${fieldData.title!}</td>
                                            <td title="${fieldData.value!'暂无数据'}">
                                                ${fieldData.value!'暂无数据'}
                                            </td>
                                        </tr>
                                            <#if (element.dataArray?size%2==1)&&(fieldData_index==element.dataArray?size-1)>
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                            </tr>
                                            </#if>
                                        </#list>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </#list>
                </#if>
            </#if>
        </#list>
    </#if>
    </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭
    </button>
</div>
<script>
    App.handleScrollers();
</script>