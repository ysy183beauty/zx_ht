<#assign data = NPT_ACTION_RETURNED_JSON>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4><i class="icon-legal"></i> 查看更多字段</h4>
</div>
<div class="modal-body">
<table class="table table-bordered table-hover" >
<thead>
<tr>
    <th style="width:30px">序号</th>
    <th width=""></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
    <th></th>
</tr>
</thead>
<tbody>
    <#list data.dataList[0].dataArray as dataRow>
    <tr >
        <td>${(data.beginIndex + dataRow_index + 1)?string("#")}</td>
        <#list dataRow.keySet() as key>
            <#if key_index!=0>
                <td align="left" title="${dataRow.get(key)!}">${dataRow.get(key)!}</td>
            </#if>
        </#list>
    </tr>
    </#list>
</tbody>

</table>
</div>
<div class="modal-footer">
    <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
</div>