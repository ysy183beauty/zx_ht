<div class="search_list col-md-3" >
    <div class="height_auto nav-md">
        <div class="left_nav">
            <span>信用公示</span>
            <ul id="nav">
                <li class="level1"><i></i><a>双公示</a>
                    <ul class="nav_seclist" id="pub">
                        <li onclick="publicityNavi('/credit/pub/2pub/alIndex.do',null,'#mainContent')"><i></i><a>行政许可</a></li>
                        <li onclick="publicityNavi('/credit/pub/2pub/apIndex.do',null,'#mainContent')"><i></i><a>行政处罚</a></li>
                    </ul>
                </li>
                <li class="level1"><i></i><a>红黑榜</a>
                    <ul class="nav_seclist" id="hhb">
                        <li onclick="publicityNavi('/credit/pub/rbl/red/index.do',null,'#mainContent')"><i></i><a>红榜</a></li>
                        <li onclick="publicityNavi('/credit/pub/rbl/black/index.do',null,'#mainContent')"><i></i><a>黑榜</a></li>
                    </ul>
                </li class="level1">
                <li class="level1" onclick="publicityNavi('/credit/pub/ucc/index.do',null,'#mainContent')"><a>统一信用代码</a></li>
                <li class="level1" onclick="publicityNavi('/dxsl/index.jhtml',null,'#mainContent')"><a >典型事例</a></li>
                <#--<li class="level1" style="display: none;" onclick="publicityNavi('/credit/pub/dl/index.do',null,'#mainContent')"><a >责任清单</a></li>-->
            </ul>
            <span>行业部门评价</span>
            <ul id="">
                <li class="level1" onclick="publicityNavi('/hypjbz/index.jhtml',null,'#mainContent')"><a >行业评价标准</a></li>
                <li class="level1" onclick="publicityNavi('/hypjjg/index.jhtml',null,'#mainContent')"><a >行业评价结果</a></li>
            </ul>
            <span>第三方评价</span>
            <ul id="">
                <li class="level1" onclick="publicityNavi('/hypjbg/index.jhtml',null,'#mainContent')"><a >评价报告</a></li>
            </ul>
        </div>
    </div>
</div>


<script type="text/javascript">
    function load(){
        window.setTimeout("$('.load_main').hide()",100);//100毫秒后，隐藏你的DIV
    }
    function publicityNavi(url,param,domId){
//        $(domId).stop();
//        if(param){
//            $(domId).html();
//        }
        $(".load_main").show()//显示你的DIV
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
//二级Nav显示隐藏效果
    $(".level1 a").bind("click",function () {
        $(this).parent().find(".nav_seclist").stop().slideToggle();
        $(this).parent().siblings().find(".nav_seclist").stop().slideUp();
        $(".search_list li a").removeClass("active");
        $(this).stop().addClass("active");
//        $("#title span span:last-child").html(" > "+$(".search_list .active").text());
        $(".last-tit").text(">"+$(".search_list .active").text());
//LOADING
    });
    $(".nav_seclist").bind("click",function () {

    })

</script>
