<#include "CommonUtil.ftl"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/lead.css">
<#if _RESULT??>
    <#if _RESULT.code == 0>
        <#assign data=NPT_ACTION_RETURNED_JSON>

                <#if data.dataList??>
                    <#list data.dataList as list>
                        <li onclick="uccDetail(${list.dataArray[0].value})">
                            <a class="col-xs-6" title="${list.dataArray[1].value}">
                                <div class="fang"></div>
                            ${list.dataArray[2].value}
                            </a>
                            <a class="col-xs-6">${list.dataArray[1].value}</a>
                            <#--<dl>-->
                                <#--<dt>-->
                                <#--<div class="fang"></div>-->
                                    <#--<a title="${list.dataArray[1].value}">${list.dataArray[2].value}</a>-->
                                <#--</dt>-->
                            <#--</dl>-->
                        </li>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="${data.columnTitles?size}">暂无数据</td>
                    </tr>
                </#if>

    <#else>
        <div>${_RESULT.title}</div>
    </#if>
<#else>
    <div>暂无数据</div>
</#if>
<script type="text/javascript">
    function uccDetail(kval) {

//        $.ajax({
//            type:"POST",
//            url:"/credit/pub/ucc/detail.do",
//            data:{
////                poolId : uid ,
//                ukValue : kval
//            },
//            success:function(html){
//                var obj = window.open("about:blank");
//                obj.document.write(html);
//            }
//        });
        window.open("/credit/pub/ucc/detail.do?ukValue="+kval);
    }
</script>
