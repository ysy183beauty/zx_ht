<#include "CommonUtil.ftl"/>
<style>
    /*.put-div{*/
        /*display: inline-block;*/
        /*overflow: hidden;*/
        /*width:100%;*/
    /*}*/
    .pub-line{
        width:48%;
        padding:0 5px;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        float:right;
    }
    .nav-text:hover{
        text-decoration: none;
    }
</style>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/lead.css">

    <div id="al">
<#if alBean??>
    <#assign dataAl=alBean>
    <#if dataAl.dataList??>
            <#list dataAl.dataList as list>
                <li onclick="alDetail(${(list.dataArray[0].value)!})" >
                    <a target="_blank" class="nav-2pub-text col-xs-6">
                        <div class="fang"></div>
                        ${(list.dataArray[1].value)!}
                    </a>
                    <a class="nav-2pub-text col-xs-6" style="padding-left:15px;">${(list.dataArray[2].value)!}</a>
                </li>
            </#list>

    <#else>
        暂无数据
    </#if>
<#else>
    <div>暂无数据</div>
</#if>
    </div>


<div id="ap">
<#if apBean??>
    <#assign dataAp=apBean>
    <#if dataAp.dataList??>
        <#list dataAp.dataList as list>
            <li onclick="apDetail(${(list.dataArray[0].value)!})">
                <a target="_blank" class="nav-2pub-text col-xs-6">
                    <div class="fang"></div>
                    ${(list.dataArray[1].value)!}
                </a>
                <a class="nav-2pub-text col-xs-6" style="padding-left:15px;">${(list.dataArray[2].value)!}</a>
            </li>
        </#list>

    <#else>
        暂无数据
    </#if>
<#else>
    <div>暂无数据</div>
</#if>
</div>
<script>
    function alDetail(kval) {
//        $.ajax({
//            type:"POST",
//            url:"/credit/pub/2pub/al/detail.do",
//            data:{
//                primaryKeyValue : kval
//            },
//            success:function(html){
//                var obj = window.open("about:blank");
//                obj.document.write(html);
//            }
//        });
        window.open("/credit/pub/2pub/al/detail.do?primaryKeyValue="+kval);
    }
    function apDetail(kval) {
//        $.ajax({
//            type:"POST",
//            url:"/credit/pub/2pub/ap/detail.do",
//            data:{
//                primaryKeyValue : kval
//            },
//            success:function(html){
//                var obj = window.open("about:blank");
//                obj.document.write(html);
//            }
//        });
        window.open("/credit/pub/2pub/ap/detail.do?primaryKeyValue="+kval);
    }

</script>