<#--<#include "/template/Credit_Common/common.ftl">-->
<#--<link rel="stylesheet" href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/inputStyle.css">-->
<#assign data = NPT_ACTION_RETURNED_JSON>
<#--<div id="canvas-info" class="canvasInfo">-->
    <div class="info-title">
        <h3>详细信息：</h3>
        <#--<div class="info-icon">-->
            <#--<i class=" icon-chevron-right" onclick="hide()"></i>-->
        <#--</div>-->
    </div>
    <div class="info-data">
    <#if data.dataList??>
        <table class="" border="0" width="90%" cellpadding="0" cellspacing="0" style="">
            <#list data.dataList as list>
                <#list list.blockList as blockList>
                    <#list blockList.dataArray as array>
                        <#list array.dataArray as dataArray>
                            <tr>
                                <th>${dataArray.title}：</th>
                                <td>${dataArray.value}</td>
                            </tr>
                        </#list>
                    </#list>
                </#list>
            </#list>

        </table>
    </#if>
    </div>
    <div class="info-bottom">

    </div>
<#--</div>-->

<script>
//    $(function(){
//        $("#canvas-info").addClass("info-active");
//    });
    function hide() {
        $("#canvas-info").stop().removeClass("info-active");
        $("#canvas").stop().removeClass("can-active");
        $("#svg-info").stop().removeClass("info-active");
    }
</script>