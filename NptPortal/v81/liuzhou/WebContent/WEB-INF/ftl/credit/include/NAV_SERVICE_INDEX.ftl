
<#--<div class="search_list ">-->
    <#--<div class="height_auto nav-md">-->
        <#--<div class="left_nav">-->
            <#--<span>信用服务</span>-->
            <#--<ul id="nav">-->
                <#--<li class="active" onclick="srvNavi('/fwjg/index.jhtml',null,'#mainContent')"><a>服务机构</a></li>-->
                <#--<li onclick="loginSrv('/credit/srv/objection.do',null,'#mainContent')"><a name="objection">异议处理</a></li>-->
                <#--<li onclick="loginSrv('/credit/srv/complain.do',null,'#mainContent')"><a name="complain">信用投诉</a></li>-->
                <#--<li onclick="loginSrv('/credit/srv/advice.do',null,'#mainContent')"><a name="advice">信用咨询</a></li>-->
                <#--<li onclick="loginSrv('/credit/srv/massage.do',null,'#mainContent')"><a name="massage">我的消息</a></li>-->

            <#--</ul>-->
        <#--</div>-->
    <#--</div>-->
<#--</div>-->
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/secondNav.css">
<div class="col-sm-1">
    <div style="display: none" class="secondNav-box" onclick="srvNavi('/fwjg/index.jhtml',null,'#mainContent')">
        <a id="" >
            <div class='card bg-01'>
                <span class='card-content'>服务机构</span>
            </div>
        </a>
    </div>
    <div class="secondNav-box" onclick="loginSrv('/credit/srv/objection.do',null,'#mainContent')">
        <a id="mycard"   name="objection">
            <div class='card bg-02'>
                <span class='card-content'>信用申诉</span>
            </div>
        </a>
    </div>
    <div class="secondNav-box" onclick="loginSrv('/credit/srv/complain.do',null,'#mainContent')" >
        <a id="" name="complain">
            <div class='card bg-01'>
                <span class='card-content'>信用投诉</span>
            </div>
        </a>
    </div>
    <div class="secondNav-box" onclick="loginSrv('/credit/srv/advice.do',null,'#mainContent')">
        <a id=""  name="advice">
            <div class='card bg-02'>
                <span class='card-content'>信用问答</span>
            </div>
        </a>
    </div>
    <div class="secondNav-box" onclick="loginSrv('/credit/srv/massage.do',null,'#mainContent')">
        <a id=""  name="massage">
            <div class='card bg-01'>
                <span class='card-content'>我的消息</span>
            </div>
        </a>
    </div>

</div>
<script>
//
    var debug = true;
    $(".search_list li").bind("click",function(){
        $(this).stop().addClass("active").siblings().removeClass("active");
        $(".last-tit").text(">"+$(".search_list .active").text());
//        creditService($(".search_list .nav_on a").attr("name"));
    });
    function creditService(name) {
//        if(debug) console.log(name);
        $.get("/credit/srv/"+name+".do", function (data) {
            $("#mainContent").html(data);
        })
    }
    function srvNavi(url,param,domId) {
//        $(".load_main").show()//显示你的DIV
        $.ajax({
            url: url,
            data: param,
            timeout: 30000,
            success: function (data) {
                $(domId).html(data);
//                load();
            },
            error: function () {
                $(domId).html("数据请求失败！");
//                load();
            },
            timeout: function () {
                $(domId).html("数据请求超时！");
//                load();
            }
        });
    }
//    判断用户是否登录
function loginSrv(url,param,domId) {
    <#if user??>
        srvNavi(url,param,domId);

    <#else>
        showEHAOTONGLogin();
    </#if>
}
</script>