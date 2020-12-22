<#macro cardHotList dataList>
    <#list dataList as c>
    <li>
        <div class="col-xs-3 img-per">
            <#if c.imgPath??>
                <img src="${c.imgPath}" alt="">
            <#else>
                <img src="${resSys}/www/red/img/lz/xymp/u1140.png" alt="">
                <span>头像</span>
            </#if>
        </div>
        <div class="col-xs-6">
            <h4>${c.cardTitle!}</h4>
            <p class="textTitle">${c.cardDesc!"-"}</p>
            <span>关注度：${c.hotValue!}</span>
        </div>
        <div class="concern">
            <#if c.relation??>
                <#if c.relation == 0>
                    <img src="${resSys}/www/red/img/lz/xymp/plus.jpg" alt="${c.id}" onclick="follow(this)" title="点击，添加关注" >
                <#elseif c.relation == 1>
                    <img src="${resSys}/www/red/img/lz/xymp/yplus.jpg" alt="${c.id}" onclick="unFollow(this)" title="点击，取消关注" >
                <#elseif c.relation == 2>
                    <img src="${resSys}/www/red/img/lz/xymp/hx_plus.jpg" alt="${c.id}" onclick="unFollow(this)" title="点击，取消关注" >
                </#if>
            <#else>
                <img src="${resSys}/www/red/img/lz/xymp/plus.jpg" alt="${c.id}" onclick="cardLogin()" title="点击，添加关注" >
            </#if>
        </div>
    </li>
    </#list>
</#macro>