<#include "CommonUtil.ftl"/>
<#assign data = NPT_ACTION_RETURNED_JSON>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>重点人群详情</title>
<#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/zqsjxq.css"/>
</head>
<body>
<div class="wrap">
<#include "include/header.ftl"/>
<#if data.dataArray??>
    <div id="container">
        <#--<div class="title">-->
            <#--<span><a href="">当前位置</a> ：<a href="/">首页</a> > <a href="/credit/query/index.do">信用查询 </a> > <span> 重点人群信息</span> </span>-->
        <#--</div>-->
        <div class="sit height_auto m-t-md">
            <ul>
                <li>
                    <a id="sy" href="/">首页</a>
                </li>
                <li>&gt;</li>
                <li>
                    <a href="/credit/pub/index.do">信用查询</a>
                </li>
                <li class="last-tit">
                <li>&gt;</li>
                <li>重点人群查询</li>
                </li>
            </ul>
        </div>
        <div class="content">
            <div class="con_top">
                <div class="con_top_shu">
                    <h2>${data.title!}</h2>
                    <#--<div class="error">-->
                        <#--<img src="${ctx}/r/cms/www/red/img/sjxq/u87.png" alt=""/>异议-->
                    <#--</div>-->
                    <table  border="0" width="100%" cellpadding="0" cellspacing="0">
                    <#list data.dataArray as array>

                        <tr class="row">
                            <td class="col-md-3 td_left">${array.title}：</td>
                            <td class="col-md-9 td_right">${array.value!}</td>
                        </tr>

                    </#list>
                    </table>
                </div>
            </div>
        </div>
    </div>
<#else>
    暂无数据
</#if>
</div>
<#include "include/footer.ftl"/>
</body>

</html>