<#include "CommonUtil.ftl"/>
<#assign data = NPT_ACTION_RETURNED_JSON>
<style type="text/css">
    #container h2{
        padding:50px 0;
        border-bottom: 1px solid #ccc;
        color: rgb(58, 114, 174);
    }
    .center_title{
        margin-top:30px!important;
    }
    .no-found{
        font-weight:bold;
        text-align: center;
        font-size:20px;
    }
</style>
<div class="main">
    <div id="container">
        <h2>查询详情</h2>
        <div class="con_top_shu">
            <div class="con_center">
            <#if data.dataList?? && data.dataList?size gt 0>
                <#assign mapValue = data.dataList>
                <#list mapValue?keys as key>
                    <div class="bg_1">
                        <#if mapValue[key]?size gt 0>
                            <div class="center_title">${key}</div>
                            <table class="table-striped table-hover" border="1" width="100%" cellpadding="0"
                                   cellspacing="0"
                                   style="border-collapse:collapse;">
                                <thead>
                                    <#list mapValue[key].titleValues[0].dataArray as tbColumns>
                                        <#if tbColumns_index != 0>
                                        <th>${tbColumns.title}</th>
                                        </#if>
                                    </#list>
                                </thead>
                                <tbody>
                                    <#list mapValue[key].titleValues as rowValue>
                                    <tr onclick="detail(${mapValue[key].pool.id},${rowValue.dataArray[0].value})">
                                        <#list rowValue.dataArray as fieldValue>
                                            <#if fieldValue_index != 0>
                                                <td>${fieldValue.value!}</td>
                                            </#if>
                                        </#list>
                                    <#--<td>-->
                                    <#--<a href="/credit/query/kc/detail.do?poolId=${mapValue[key].pool.id}&primaryKeyValue=${rowValue.dataArray[0].value}"-->
                                    <#--target="_blank">详细数据</a></td>-->
                                    </tr>
                                    </#list>
                                </tbody>
                            </table>
                        </#if>
                    </div>
                </#list>
            <#else>
                <div class="no-found">暂无数据</div>
            </#if>
            </div>
        </div>
    </div>
</div>
<script>
    function detail(uid,kval) {
        $.ajax({
            type:"POST",
            url:"/credit/query/kc/detail.do",
            data:{
                poolId : uid ,
                primaryKeyValue : kval
            },
            success:function(html){
                var obj = window.open("about:blank");
                obj.document.write(html);
            }
        });
//        window.open("/credit/query/kc/detail.do?poolId=" + uid + "&primaryKeyValue=" + kval);
    }
    var URLParams = new Array();
    var aParams = document.location.search.substr(1).split('&');
    for (i = 0; i < aParams.length; i++) {
        var aParam = aParams[i].split('=');
        URLParams[aParam[0]] = aParam[1];
    }
//    var key = URLParams["keyword"];
    //    $("title").text("搜索：" + decodeURIComponent(key));
</script>