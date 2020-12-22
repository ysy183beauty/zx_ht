<#include "CommonUtil.ftl"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/lead.css">
<div class="lan">
    <div class="lan1" id="hd_ul">
        <span class='red'>统一信用代码</span>
    </div>
    <div class="lan2" style="width:110px;"></div>
    <a id="rb-more" class='lanshang' href="/credit/pub/index.do?word=3">更多+</a>
</div>

<div class="bd">
    <div class="png">
        <img src="${ctx}/r/cms/www/red/img/aaaa/14.png" alt="" style="width:121px;height:168px;">
    </div>
<#if _RESULT.code == 0>
    <#assign data=NPT_ACTION_RETURNED_JSON>
    <ul class="bd_ul">
        <#if data.dataList??>
            <#list data.dataList as list>
                <li onclick="detail(${list.dataArray[0].value})">
                    <dl>
                        <dt>
                        <div class="fang"></div>
                        <a title="${list.dataArray[1].value}">${list.dataArray[2].value}</a>
                        </dt>
                    </dl>
                </li>
            </#list>
        <#else>
            <tr>
                <td colspan="${data.columnTitles?size}">暂无数据</td>
            </tr>
        </#if>
    </ul>
<#else>
    <div>${_RESULT.title}</div>
</#if>
</div>


<script type="text/javascript">
    function detail(kval) {
        console.log("1");
        $.ajax({
            type:"POST",
            url:"/credit/pub/ucc/detail.do",
            data:{
//                poolId : uid ,
                ukValue : kval
            },
            success:function(html){
                var obj = window.open("about:blank");
                obj.document.write(html);
            }
        });
    }
</script>