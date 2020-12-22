<p>用户通过${_LOG.actionName}，看到结果如下：</p>

<#if grs_group_info_list??>
    <#list grs_group_info_list as blockGroup>
        <#if (blockGroup.blockList??) && (blockGroup.blockList?size!=0) >
        <div class="page-title">
            <h3>${blockGroup.groupTitle!}</h3>
        </div>
            <#list blockGroup.blockList as block>
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <label onclick="triggerTb('${block.blockInfo.id?string("#")}${block_index}')">
                <span class="caption">
                    <i class="icon-file-text"></i> ${block.blockInfo.poolTitle!}
                    【<span>
                    <#if block.parentInfo??>${block.parentInfo.orgName!}</#if>
                    </span>】
                </span>
                    </label>
                </div>
                <div class="portlet-body 	<#if !(block.dataArray??)>hide</#if>">
                    <table class="table table-bordered table-hover tbList">
                        <tbody>
                            <#if (block.dataCount > 0) && block.dataArray??>
                                <#list block.dataArray as firstElement>
                                    <#if firstElement_index == 0>
                                        <#list firstElement.dataArray as fieldData>
                                        <tr>
                                            <td>${fieldData.title!}</td>
                                            <td title="${fieldData.value!'暂无数据'}">
                                                <#if fieldData.linked == 1  && fieldData.value??>
                                                    <a href="javascript:linkedPoolData('${block.blockInfo.id?string("#")}','${firstElement.uFieldValue}','${fieldData.fieldId?string("#")}','');">${fieldData.value}</a>
                                                <#elseif fieldData.valueNote?? && fieldData.value??>
                                                ${fieldData.value}&nbsp;&nbsp;&nbsp;&nbsp;
                                                    <i class="icon-flag">(${fieldData.valueNote})</i>
                                                <#else >
                                                ${fieldData.value!'暂无数据'}
                                                </#if>
                                            </td>
                                        </tr>
                                            <#if (firstElement.dataArray?size%2==1)&&(fieldData_index==firstElement.dataArray?size-1)>
                                            <tr>
                                                <td>&nbsp;</td>
                                                <td>&nbsp;</td>
                                            </tr>
                                            </#if>
                                        </#list>
                                    </#if>
                                </#list>
                            </#if>
                        </tbody>
                    </table>
                </div>
            </div>
            </#list>
        <#else >
        </#if>
    </#list>
</#if>



<#if grs_main_field_list??>
<table class="table table-striped table-bordered table-hover" id="pageData">
    <thead>
        <#assign dataTitle = grs_main_field_list[0]>
    <tr>
        <th style="width:30px">序号</th>
        <#list dataTitle.dataArray as fieldData>
            <#if fieldData_index!=0>
                <th align="left" title="${fieldData.title!}">${fieldData.title!}</th>
            </#if>
        </#list>
    </tr>
    </thead>
    <tbody>
        <#list grs_main_field_list as dataRow>
        <tr>
            <td>${(dataRow_index + 1)?string("#")}</td>
            <#list dataRow.dataArray as fieldData>
                <#if fieldData_index!=0>
                    <td align="left" title="${fieldData.value!}">${fieldData.value!}</td>
                </#if>
            </#list>
        </tr>
        </#list>
    </tbody>
</table>
</#if>


<#if grs_block_info_list??>
    <#list grs_block_info_list as block>
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


