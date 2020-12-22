<option value="">请选择</option>
<#list _GROUP_POOL_LIST as c>
<option value="${c.id}">${c.poolTitle!}</option>
</#list>