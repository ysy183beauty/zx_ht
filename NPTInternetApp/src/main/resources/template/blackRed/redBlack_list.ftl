<link href="/pub/index/css/bootstrap.min.css" rel="stylesheet">
<link href="/pub/index/css/main.css" rel="stylesheet">
<#include "/template/Credit_Common/common.ftl">
<#include "/template/Credit_Common/c_pagination.ftl">
<table class="table table-bordered table-hover" id="pageData">
    <#include "redBlack_info.ftl">
</table>
<div id="pagination" style="margin-bottom: 100px;"></div>
<iframe id="handleFrame" name="handleFrame" src="" width="0" height="0" style="display:none;" ></iframe>
<script>
    function sethash(){
        var hashH = document.documentElement.scrollHeight;
        var urlC = "/npt/internet/blackred/handleFrame.action";
        document.getElementById("handleFrame").src=urlC+"#"+hashH;
    }
    $(function () {
        sethash();
    })
</script>