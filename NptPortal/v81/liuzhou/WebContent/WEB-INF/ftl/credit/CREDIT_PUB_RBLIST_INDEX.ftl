
<#include "CommonUtil.ftl"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/tableDate.css"/>
<#--<#if NPT_BASEMODEL_GROUPOOL??>-->
    <#assign pools=_POOLS>
    <#if pools?size gt 0>
        <div class="row top_nav">
            <div >
                <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                    <#list pools?keys as key>
                        <div class="panel panel-default">
                            <div class="panel-heading" role="tab" id="heading${key_index}">
                                <h4 class="panel-title">
                                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${key_index}" aria-expanded="false" aria-controls="collapse${key_index}">
                                    ${key}
                                        <span>[${pools[key]?size}]</span>
                                    </a>
                                </h4>
                            </div>
                            <div id="collapse${key_index}" class="panel-collapse collapse " role="tabpanel" aria-labelledby="heading${key_index}">
                                <div class="panel-body">
                                <p class="row">
                                    <#list pools[key] as value>
                                        <div class="col-md-4 col-sm-6">
                                            <div class="box-nav" onclick="show_info(null,${value.id},0)">
                                                <div class="box-text">
                                                ${value.poolTitle}
                                                </div>
                                            </div>
                                        </div>
                                    </#list>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
        <div id="md_content"></div>
        <#else>
        <div style="font-size:24px;text-align:center;">
            暂无数据
        </div>
    </#if>
<#--<#else>-->
    <#--<div>暂无数据</div>-->
<#--</#if>-->

    <#--</body>-->
<script>
    function show_info(param,id,_index) {
        $(".load_main").show();
        $.ajax({
            url:"/credit/pub/rbl/list.do",
            data:{
                poolId : id,
                pageSize : 10,
                currPage : 1,
                fromIndex: _index,
                bean  : param
            },
            success:function (html) {
                $("#md_content").html(html);
                $(".focus").eq("0").focus();
                $.scrollTo('#md_content',500);
                load();
            },
            error:function () {
                $("#md_content").html("数据请求失败！");
                load();
            },
            timeout:function(){
                $("#md_content").html("数据请求超时！");
                load();
            }
        })
    }

</script>
<#--</html>-->