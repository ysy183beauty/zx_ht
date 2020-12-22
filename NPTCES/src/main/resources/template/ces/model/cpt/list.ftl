<#if cptTypes??&&cptTypes?size gt 0>
    <#list cptTypes as c>
    <tr>
        <td>${c?index+1}</td>
        <td align="left" >${c.title!}</td>
        <td align="left" >
            <#list CES_CPM_LIST as code>
            <#if (c.computeType)?? && (c.computeType)?string == code.code?string>${code.title}
                <#break>
            </#if>
        </#list>
        </td>
        <td class="inputContent_td">${c.countIntervals!}</td>
        <td>
            <div class="controls">
                <a class="btn mini green tooltips" href="javascript:void(0)" onclick="editCptPage('${c.id!}')" data-placement="bottom" data-original-title="编辑"><i class="icon-edit "></i></a>
            <a  class="btn mini tooltips red delete" href="javascript:void(0);" onclick="deleteCpt('${c.id}')" data-placement="bottom" data-original-title="删除"><i class="icon-trash"  ></i></a>
            </div>
        </td>
    </tr>
    </#list>
<#else>
<td colspan="5">暂无数据</td>
</#if>