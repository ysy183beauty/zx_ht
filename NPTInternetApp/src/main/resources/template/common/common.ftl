<#assign ctx = request.contextPath />
<#assign wctx = ctx + application["webSourcePathKey"] />
<#assign urlExt = application["strutsUrlExt"] />
<#assign platformName = (_styleParam.platformName)!"爱客道客户关系管理平台">
<#assign platformLogo = wctx + "/" + (_styleParam.styleLogo)!"pub/matrix/img/logo-ikdo.png">
<#assign platformCopyright = (_styleParam.copyright)!>
<#setting number_format="#">