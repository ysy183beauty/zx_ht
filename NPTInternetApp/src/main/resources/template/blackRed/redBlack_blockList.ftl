<style>
    .redBlackLi{
        display: block;
        width: 100%;
        float: left;
        border-bottom:1px solid #ddd;
        line-height: 40px;
        height: 40px;
        font-size: 13px;
        cursor: pointer;
        font-family: "Microsoft YaHei UI";
    }
    .redBlackLi span{
        padding: 0 10px;
    }
    .redBlackLi:hover{
        color: #2e71b8;
    }
    .redBlackLi span:first-child{
        width: 57%;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
        float: left;
    }
    .redBlackLi span:last-child{
        color: #afafaf;
        float: right;
    }
</style>
<#if _POOL_LIST??>

    <#list _POOL_LIST as p>
    <li class="redBlackLi" onclick="showList(${p.id?string("#")})"><span>${p.poolTitle!}</span><span>[ ${p.providerTitle!} ]</span></li>
    </#list>


<div id="poolInfo"></div>
</#if>
<script src="/pub/index/js/jquery.js"></script>
<script>
    function showList(id) {
        $.post("paginationPoolDataIndex.action", {poolId: id}, function (data) {
            $("#poolInfo").html(data);
            pagination.initPagination("paginationPoolDataAjax.action", {primaryKey: id});
        });
    }
</script>