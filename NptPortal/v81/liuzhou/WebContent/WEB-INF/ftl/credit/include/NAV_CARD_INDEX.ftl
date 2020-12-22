<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/secondNav.css">
<div class="col-sm-1">

        <div class="secondNav-box">
            <a id="card" href="/credit/card/plaza.do" >
                <div class='card bg-01'>
                    <span class='card-content'>名片广场</span>
                </div>
            </a>
        </div>
        <div class="secondNav-box">
            <a id="mycard" href="[#if user??]/credit/card/myCard.doo[#else]javascript:showEHAOTONGLogin();[/#if]">
                <div class='card bg-02'>
                    <span class='card-content'>我的名片</span>
                </div>
            </a>
        </div>
</div>