<#if _MODEL_LIST??&&_MODEL_LIST?size gt 0>
    <#list _MODEL_LIST as c>
    <tr>
        <td>${c?index+1}</td>
        <td align="left" >${c.baseModel.modelName!}</td>
        <td align="left" >${(c.modelProperties.upLimit)!}</td>
        <td  >${(c.modelProperties.lowLimit)!}</td>
        <td>${c.baseModel.createTime?string('yyyy-MM-dd')!}</td>
        <td  >${(c.baseModel.status==1)?string('启用','禁用')!}</td>
        <td>
            <div class="controls">
                <a class="btn mini purple tooltips" href="javascript:void (0);" onclick="enterModelIndex(${c.baseModel.id})" data-placement="bottom" data-original-title="查看模型"><i class="icon-search "></i></a>
                <a class="btn mini blue tooltips" href="javascript:void (0);" onclick="showGroup('${c.baseModel.id}')" data-placement="bottom" data-original-title="查看分组"><i class="icon-tasks"></i></a>
                <a class="btn mini green tooltips" href="javascript:void(0)" onclick="editModelPropsPage('${(c.modelProperties.id)!}','${c.baseModel.id}')" data-placement="bottom" data-original-title="编辑模型属性"><i class="icon-edit "></i></a>
                <#--<a  class="btn mini tooltips red delete" href="javascript:void(0);" onclick="deleteModel('${c.id}')" data-placement="bottom" data-original-title="删除模型"><i class="icon-trash"  ></i></a>-->
                <a  class="btn mini tooltips yellow delete" href="javascript:void(0);" onclick="dataProgress('${c.baseModel.id}')" data-placement="bottom" data-original-title="数据预处理"><i class="icon-wrench"  ></i></a>
            </div>
        </td>
    </tr>
    </#list>
</#if>