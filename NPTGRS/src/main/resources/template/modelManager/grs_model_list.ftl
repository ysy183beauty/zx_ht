<#if _MODEL_LIST??&&_MODEL_LIST?size gt 0>
    <#list _MODEL_LIST as c>
    <tr>
        <td>${c?index+1}</td>
        <td align="left" >${c.modelName!}</td>
        <td align="left" >${c.cateTitle!}</td>
        <td  >${c.hostTitle!}</td>
        <td>${c.createTime?string('yyyy-MM-dd')!}</td>
        <td  >${(c.status==1)?string('启用','禁用')!}</td>
        <td>
            <div class="controls">
                <a class="btn mini purple tooltips" href="javascript:void (0);" onclick="enterModelIndex(${c.id?string("#")})" data-placement="bottom" data-original-title="查看模型"><i class="icon-search "></i></a>
                <a class="btn mini blue tooltips" href="javascript:void (0);" onclick="showGroup('${c.id?string("#")}')" data-placement="bottom" data-original-title="查看分组"><i class="icon-tasks"></i></a>
                <a class="btn mini green tooltips" href="javascript:void(0)" onclick="editModel('${c.id?string("#")}',this)" data-placement="bottom" data-original-title="编辑模型"><i class="icon-edit "></i></a>
                <a  class="btn mini tooltips red delete" href="javascript:void(0);" onclick="deleteModel('${c.id?string("#")}')" data-placement="bottom" data-original-title="删除模型"><i class="icon-trash"  ></i></a>
                <a  class="btn mini tooltips yellow delete" href="javascript:void(0);" onclick="optimizeQeury('${c.id?string("#")}')" data-placement="bottom" data-original-title="优化查询"><i class="icon-wrench"  ></i></a>
                <a  class="btn mini tooltips pink  delete" href="javascript:void(0);" onclick="copyModal('${c.id?string("#")}')" data-placement="bottom" data-original-title="模型复制"><i class="icon-random"  ></i></a>
            </div>
        </td>
    </tr>
    </#list>
</#if>