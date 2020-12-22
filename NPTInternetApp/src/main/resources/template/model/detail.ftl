
<#if NPT_ACTION_RETURNED_JSON??>
<link href="/pub/index/css/bootstrap.min.css" rel="stylesheet">

<link href="/pub/index/css/main.css" rel="stylesheet">
    <#assign data =  NPT_ACTION_RETURNED_JSON>

    <#if data.dataList??>
        <#list data.dataList as blockGroup>
            <#if (blockGroup.blockList??) && (blockGroup.blockList?size!=0) >
            <div class="page-title center">
                <h3 >${blockGroup.groupTitle!}</h3>
            </div>
                <#list blockGroup.blockList as block>
                <div class="portlet box boxTheme">
                    <div class="portlet-title">
                        <span class="caption hd">
                            <i class="icon-file-text"></i> ${block.blockInfo.poolTitle!}
                            【<span>
                            <#if block.parentInfo??>${block.parentInfo.orgName!}</#if>
                            </span>】
                        </span>
                    </div>
                    <div class="portlet-body >
                        <table class="table table-bordered table-hover tbList">
                            <tbody>
                                <#if (block.dataCount > 0) && block.dataArray??>
                                    <#list block.dataArray as firstElement>
                                        <#if firstElement_index == 0>
                                            <#list firstElement.dataArray as fieldData>
                                            <tr>
                                                <td>${fieldData.title!}</td>
                                                <td title="${fieldData.value!'暂无数据'}">${fieldData.value!'暂无数据'} </td>
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
<iframe id="handleFrame" name="handleFrame" src="" width="0" height="0" style="display:none;" ></iframe>
<script>
    function sethash(){
        var hashH = document.documentElement.scrollHeight+100;
        var urlC = "/npt/internet/blackred/handleFrame.action";
        document.getElementById("handleFrame").src=urlC+"#"+hashH;
    }
    $(function () {
        sethash();
    })
</script>
</#if>
