<div class="search_list col-md-3">
    <div class="height_auto nav-md">
        <div class="left_nav">
            <span>信用查询</span>
                <ul id="nav">
                    <li onclick="publicityNavi('/credit/query/bs/list.do',null,'#mainContent')"><a >企业基础查询</a></li>
                    <li onclick="publicityNavi('/credit/query/kc/index.do',null,'#mainContent')"><a >重点人群查询</a></li>
                    <li onclick="login('/credit/query/ps/index.do',null,'#mainContent')"><a >个人信用查询</a></li>
                </ul>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(".search_list li").bind("click",function(){
        $(this).stop().addClass("active").siblings().removeClass("active");
        $(".last-tit").text(">"+$(".search_list .active").text());
    });
    function load(){
        window.setTimeout("$('.load_main').hide()",100);
    }
    function publicityNavi(url,param,domId){
        $(".load_main").show();
        $.ajax({
            url: url,
            data: param,
            timeout: 30000,
            success: function (data) {
                $(domId).html(data);
                load();
            },
            error:function () {
                $(domId).html("数据请求失败！");
                load();
            },
            timeout:function(){
                $(domId).html("数据请求超时！");
                load();
            }
        });
    }
</script>