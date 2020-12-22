<#assign dataList =  NPT_ACTION_RETURNED_JSON>
	<#list dataList as c>
    <tr onclick="showColumn('${c.id?string('#')}')">
        <td>${c_index+1}</td>
        <td align="left" title="${c.typeName!}">${c.typeName!}</td>
        <td align="left" title="${c.alias!}">${c.alias!}</td>
        <td align="left" title="${c.typeDbName!}">${c.typeDbName!}</td>
        <td title="${c.createTime!}">${c.createTime!}</td>
        <td title="${c.ukFieldTitle!}">${c.ukFieldTitle!}</td>
        <td>${(c.status==1)?string('启用','禁用')!}</td>
        <td>
            <div class="controls">
                <a   class="btn mini blue "  href="#columnDiv"  onclick="showColumn('${c.id?string('#')}');" ><i class="icon-search"  ></i> 查看字段</a>
                <a  class="btn mini green " href="javascript:void(0);"  onclick="editType(this,'${c.id?string("#")}')"><i class="icon-edit"  ></i> 编辑</a>
            </div>
        </td>
    </tr>
	</#list>

