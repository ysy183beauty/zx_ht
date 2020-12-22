
<div class="search_list ">
    <div class="height_auto nav-md">
        <div class="left_nav">
            <span>信用服务</span>
            <ul id="nav">
                <li class="active" onclick="srvNavi('/fwjg/index.jhtml',null,'#mainContent')"><a>服务机构</a></li>
                <li onclick="loginSrv('/credit/srv/objection.do',null,'#mainContent')"><a name="objection">异议处理</a></li>
                <li onclick="loginSrv('/credit/srv/complain.do',null,'#mainContent')"><a name="complain">信用投诉</a></li>
                <li onclick="loginSrv('/credit/srv/advice.do',null,'#mainContent')"><a name="advice">信用咨询</a></li>
                <li onclick="loginSrv('/credit/srv/massage.do',null,'#mainContent')"><a name="massage">我的消息</a></li>

            </ul>
        </div>
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
        $("#nav li:gt(0)").bind("click",function(){
            $(this).find("a").attr("href","/login.jspx");
        });
    </#if>
}
</script>