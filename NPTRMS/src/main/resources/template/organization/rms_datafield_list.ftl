<#assign dataList =  NPT_ACTION_RETURNED_JSON>

	<#list dataList as c>
    <tr id="${c.id?string('#')}">
        <td>${c_index+1}</td>
        <td align="left" title="${c.fieldName!}">${c.fieldName!}</td>
        <td align="left" title="${c.alias!}">${c.alias!}</td>
        <td align="left" title="${c.fieldDbName!}">${c.fieldDbName!}</td>
        <td title="">${c.createTime!}</td>
        <td>${(c.status==1)?string('启用','禁用')!}</td>
        <td>
            <div class="controls">
                <a  class="btn mini green tooltips" href="javascript:void(0);" onclick="editField('${c.id?string("#")}')"><i class="icon-edit "  ></i> 编辑</a>
            </div>
        </td>
    </tr>
	</#list>

