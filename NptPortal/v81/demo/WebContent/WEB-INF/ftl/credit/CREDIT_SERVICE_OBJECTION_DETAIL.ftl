<#include "CommonUtil.ftl"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bs-is-fun.css"/>
<style>
    pre {
        white-space: pre-wrap;
        word-wrap: break-word;
    }
</style>
<#assign info=_DATA>
<div class="data-top">
    <div class="application">申请表：</div>
    <table class="table table-hover">
        <tr>
            <td width="20%">申请人姓名：</td>
            <td width="80%">${userExt.realname}</td>
        </tr>
        <tr>
            <td>身份证号码：</td>
            <td>${info.user.idCard!}</td>
        </tr>
        <tr>
            <td>联系电话：</td>
            <td>${info.tel}</td>
        </tr>
        <tr>
            <td>联系邮箱：</td>
            <td>${info.email}</td>
        </tr>
        <tr>
            <td>标题：</td>
            <td>${info.title!}</td>
        </tr>
    <#if info.flag == "0" || info.flag == "4">
        <tr>
            <td>来源：</td>
            <td>${info.source!}</td>
        </tr>
    </#if>
        <tr>
            <td>附件信息：</td>
            <td><#if info.attach??><a class="btn btn-default btn-xs" href="javascript:window.open('/credit/srv/download.do?id=${info.id}')">下载</a><#else>无</#if></td>
        </tr>

        <tr>
            <td>详细描述：</td>
            <td>
            <#if info.flag != "4">
                <pre class="textarea" disabled>${info.text!}</pre>
            <#else>
                <@appealDetail data=_TEXT response=false/>
            </#if>
            </td>
        </tr>
    <#if info.responseTime??>
        <tr>
            <td>回复信息：</td>
            <td>
            <#if info.flag != "4">
                <pre class="textarea" disabled>${info.response!}</pre>
            <#else>
                <@appealDetail data=_RESPONSE response=true/>
            </#if>
            </td>
        </tr>
    </#if>
    </table>
</div>
<div class="data-bottom">
    <div class="application">申请进度：</div>
    <div class="sqjd">
        <ul class="nav-all nav-pills nav-justified step step-round">
            <li class="progress-active"><a><span>已提交<br>${info.createTime}</span></a></li>
        <#if info.syncStatus ==1>
            <li class="progress-active"><a><span>处理中<br>${info.syncTime}</span></a></li>
        <#else>
            <li><a><span>处理中</span></a></li>
        </#if>
        <#if info.responseTime??>
            <li class="progress-active"><a><span>已完成<br>${info.responseTime}</span></a></li>
        <#else>
            <li><a><span>已完成</span></a></li>
        </#if>
        </ul>
    </div>
    <div class="btn_1">
        <input type="button" value="返回" onclick="hide_info()"/>
    </div>
</div>

<#--START MACRO-->
<#macro appealDetail data response=false>
<table class="table table-condensed">
    <thead>
        <tr>
            <th width="20%">异议项</th>
            <th width="20%">系统信息</th>
            <th width="20%">异议信息</th>
        <#if response==true>
            <th width="20%">审核结果</th>
            <#if !data.userAppeal.appealResult??>
            <th width="20%">反馈结果</th>
            </#if>
        </#if>
        </tr>
    </thead>
    <tbody>
        <#list data.appealDetails as l>
        <tr>
            <td style="text-align: left;">
            <#list _FIELD_LIST as field>
                <#if field.id == l.fieldId>${field.alias}<#break></#if>
            </#list>
            </td>
            <td>${l.defaultValue}</td>
            <td>${l.appealValue}</td>
        <#if response==true>
            <td>${(l.status==0)?string("未通过","已通过")}</td>
            <#if !data.userAppeal.appealResult??>
            <td>${l.detailResult!}</td>
            </#if>
        </#if>
        </tr>
        </#list>
    </tbody>
</table>
<#if data.userAppeal.appealResult??>
<table class="table table-condensed">
    <tbody>
    <tr>
        <td width="20%">反馈结果：</td>
        <td width="80%"><pre>${_RESPONSE.userAppeal.appealResult!}</pre></td>
    </tr>
    </tbody>
</table>
</#if>
</#macro>
<#--END MACRO-->