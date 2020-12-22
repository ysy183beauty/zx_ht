<link href="/pub/index/css/bootstrap.min.css" rel="stylesheet">
<link href="/pub/index/css/main.css" rel="stylesheet">
<style>

    .redBlackBox {
        width: 99.999%;
        font-size: 13px;
    }

    .redBlackBox ul {
        width: 100%;
        overflow: hidden;
        margin: 0;
        padding: 0;
        cursor: pointer;
    }

    .redBlackBox ul li {
        line-height: 40px;
        height: 40px;
        list-style: none;
        font-size: 1.1em;
    }

    .redBlackBox .hd {
        background-color: #BCD5E9;
        /*background-image: -webkit-linear-gradient(top, #FFF, #BCD5E9);*/
        /*background-image: -o-linear-gradient(top, #FFF, #BCD5E9);*/
        /*background-image: -ms-linear-gradient(top, #FFF, #BCD5E9);*/
        /*background-image: linear-gradient(top, #FFF, #BCD5E9);*/
        /*background-repeat: repeat-x;*/
        height: 40px;
    }

    .redBlackBox .hd ul li {
        float: left;
        display: block;
        padding: 0 15px;
    }

    .redBlackBox .hd ul li.on,
    .redBlackBox .hd ul li:hover {
        border-top: 3px solid #2e71b8;
        color: #2e71b8;
        background: #fff;
        border-right: 1px solid #ddd;
        height: 40px;
    }

</style>
<div class="container">
    <div class="box">
        <div class="row">
            <div class="container">
                <div class="row">
                    <div class="redBlackBox">
                        <div class="hd">
                            <ul class="pull-left" id="rbTitle">
                                <li class="on">红榜信息</li>
                                <li>黑榜信息</li>
                            </ul>
                        </div>
                        <div class="redBlackList">
                            <ul>

                                <iframe src="/npt/internet/blackred/getRedPoolList.action" id="frame_content2"  frameborder="0" scrolling="no"
                                        marginwidth="0" style="width: 100%;"></iframe>
                            </ul>
                            <ul style="display: none">
                                <iframe src="/npt/internet/blackred/getBlackPoolList.action" id="frame_content" scrolling="no"
                                        frameborder="0" marginwidth="0" style="width: 100%;"></iframe>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="/pub/index/js/jquery.js"></script>
<script>
    $("#frame_content").load(function () {
        var mainheight = $(this).contents().find("body").height() + 30;
        $(this).height(mainheight);
    });
    $("#frame_content2").load(function () {
        var mainheight = $(this).contents().find("body").height() + 30;
        $(this).height(mainheight);
    });
    $("#rbTitle li").click(function () {
        $(this).addClass("on").siblings().removeClass("on");
        $(".redBlackList ul").eq($(this).index()).show().siblings().hide();
    });
</script>
