<#include "/template/Credit_Common/common.ftl">
<#if NPT_ACTION_RETURNED_JSON??>

    <#assign data =  NPT_ACTION_RETURNED_JSON>

    <#if !(data.dataList??)>
    暂无数据
    <#else>
        <#list data.dataList as blockGroup>
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
                        <div class="tools">
                            <a href="javascript:;" class="aspan"
                               onclick="loadApplyColumns('${block.blockInfo.id?string("#")}','${data.primaryKeyValue}')">
                                <i class="icon-search"></i> 申请授权查看</a>
                            <#if block.dataCount gt 1>
                                <a href="javascript:showMore('${block.blockInfo.id?string("#")}','${data.primaryKeyValue}');"
                                   class=" aspan more">
                                    <i class="icon-star"></i>
                                    共计<strong>[${block.dataCount}]</strong>条---查看更多</a>
                            </#if>
                            <#if block.dataArray??>
                                <a href="javascript:;"
                                   class="collapse collapse${block.blockInfo.id?string("#")}${block_index}"></a>
                            <#else >
                                <a href="javascript:;"
                                   onclick="loadPoolData('${block.blockInfo.id?string("#")}','${data.primaryKeyValue}',this);"
                                   class="expand collapse${block.blockInfo.id?string("#")}${block_index}"></a>
                            </#if>
                        </div>
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
<script>
    function triggerTb(id) {
        $(".collapse" + id).click();
    }

    function linkedPoolData(poolId, ukValue, refFieldId) {
        var dataObject = new Object();
        dataObject.parentId = poolId;
        dataObject.primaryKeyValue = ukValue;
        dataObject.primaryKeyId = refFieldId;

        //将webInvokeCondition转换成JSON串，调用ACTION，webInvokeCondition不可改名
        $.ajax({
            type: "post",
            url: "${ctx}/npt/grs/query/custom/getGroupoolLinkedPoolData.action",
            data: {webInvokeCondition: JSON.stringify(dataObject)},
            timeout: 30000,
            beforeSend: function () {
                $(".loadDiv").show();
            },
            success: function (data) {
                $(".loadDiv").hide();
                $("#modalDiv").modal("show").html(data);

            }
        });
    }
</script>
<#else >
暂无数据
</#if>