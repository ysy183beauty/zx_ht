<#include "CommonUtil.ftl"/>
<#include "include/macro.ftl">
<#if following.results?? && following.results?size gt 0>
<@cardHotList dataList = following.results/>
</#if>