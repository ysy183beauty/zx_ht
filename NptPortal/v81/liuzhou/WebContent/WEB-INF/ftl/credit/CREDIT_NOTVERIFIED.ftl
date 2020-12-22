<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>未登录</title>
<#include "CommonUtil.ftl"/>
<#include "include/header_link.ftl"/>
</head>
<body>
<script>
    $(function () {
        <#if user??>
            window.location.href = "/member/profile.jspx?returnUrl=${returnUrl!"/"}";
        <#else>
            window.location.href = "/login.jspx?returnUrl=${returnUrl!"/"}";
        </#if>
    })
</script>
</body>
</html>