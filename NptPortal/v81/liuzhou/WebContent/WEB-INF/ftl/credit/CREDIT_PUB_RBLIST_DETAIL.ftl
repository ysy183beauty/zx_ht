<#include "CommonUtil.ftl"/>
<#assign data = NPT_ACTION_RETURNED_JSON>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>数据详情</title>
<#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/zqsjxq.css"/>
</head>
<body>
<div class="wrap">
    <div class="body">
    <#include "include/header.ftl"/>
    <#if data.dataArray??>
        <div id="container">
        <#--<div class="title">-->
        <#--<span><a href="">当前位置</a> ：<a href="">首页</a> > <a href="">信用公示 </a> > <a href=""> 双公示</a> > <a href="">行政许可公示</a></span>-->
        <#--</div>-->
            <div class="sit height_auto m-t-md">
                <#--<ul>-->
                    <#--<li>-->
                        <#--<a id="sy" href="/">首页</a>-->
                    <#--</li>-->
                    <#--<li>&gt;</li>-->
                    <#--<li>-->
                        <#--<a href="/credit/pub/index.do">信用公示</a>-->
                    <#--</li>-->
                    <#--<li class="last-tit">-->
                    <#--<li>&gt;</li>-->
                    <#--<li>-->
                        <#--红黑榜-->
                    <#--</li>-->
                    <#--</li>-->
                <#--</ul>-->
            </div>
            <div class="">
                <div class="">
                    <div class="">
                        <div class="h3-menu">
                            <div class="leftBG"></div>
                            <div class="labelBG">详细信息</div>
                            <div class="centerBG"></div>
                        </div>
                        <table  class="table table-bordered tab-col"  border="0" width="100%" cellpadding="0" cellspacing="0">
                            <#list data.dataArray as array>

                                <tr class="row">
                                    <td class="col-xs-3 td_left">${array.title}：</td>
                                    <td class="col-xs-9 td_right">${array.value!}</td>
                                </tr>

                            </#list>
                        </table>
                    </div>
                </div>
            <#--<div class="btn">-->
            <#--<input type="submit" value="返回" onclick="javascript:history.go(-1);"/>-->
            <#--</div>-->
            </div>
        </div>
    <#else>
        暂无数据
    </#if>
    </div>

</div>

<#include "include/footer.ftl"/>

</body>
</html>