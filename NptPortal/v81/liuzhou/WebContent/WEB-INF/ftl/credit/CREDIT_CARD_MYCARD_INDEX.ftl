<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>我的名片</title>
<#include "CommonUtil.ftl"/>
<#include "include/header_link.ftl"/>
<#include "include/macro.ftl">
    <link rel="stylesheet" href="${resSys}/www/red/css/share.css"/>
    <script src="${resSys}/front.js" type="text/javascript"></script>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/card.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/simplePage/page.css"/>
    <style>
        .credit_list a{
            font-size:14px!important;
        }
    </style>
</head>
<body>
<div class="wrap">
    <div class="body">
    <#include "include/header.ftl">
        <!--内容开始-->
        <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                    </div>
                    <div class="modal-body">
                        ...
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="w content" >
            <div class="sit height_auto m-t-md">
                <#--<ul>-->
                    <#--<li>-->
                        <#--<a id="sy" href="/">首页</a>-->
                    <#--</li>-->
                    <#--<li>&gt;</li>-->
                    <#--<li>-->
                        <#--<a href="/xymp/index.jhtml">信用名片</a>-->
                    <#--</li>-->
                    <#--<li>&gt;</li>-->
                    <#--<li>-->
                        <#--我的名片-->
                    <#--</li>-->
                <#--</ul>-->
            </div>
            <div class="row">
            <#include "include/NAV_CARD_INDEX.ftl">
                <div class="cardContent">
                    <div class="find">
                        <div class="find-title">
                            <h3>我的名片</h3>
                        </div>
                        <#assign card = NPT_ACTION_RETURNED_JSON>
                        <div id="psCard" class="psCard">
                            <div class="psCard-info">
                                <div class="col-xs-4 img-ps">
                                    <img src="${resSys}/www/red/img/lz/xymp/u1140.png" alt="">
                                    <span>头像</span>
                                </div>
                                <div class="col-xs-8">
                                    <h3>${card.title!}</h3>
                                    <ul>
                                        <#list card.dataList[0].blockList[0].dataArray[0].dataArray as data>
                                        <#if data_index < 5>
                                            <li>${data.title}: ${data.value}</li>
                                        </#if>
                                        </#list>
                                    </ul>
                                </div>
                                <div class="qrCord">
                                    <img src="/r/cms/www/red/img/card/${creditCard.cardType!}_${creditCard.ukValue!}@40x40.png" alt="">
                                </div>
                            </div>
                            <div class="share-platform">
                                <div  class="bdsharebuttonbox">
                                    <ul>
                                        <li>分享到：</li>
                                        <li class="s1">
                                            <a href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a>
                                        </li>
                                        <li class="s2">
                                            <a href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a>

                                        </li>
                                        <li class="s3">
                                            <a href="#" class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a>
                                        </li>
                                        <li class="s4">
                                            <a href="#" class="bds_sqq" data-cmd="sqq" title="分享到QQ好友"></a>
                                        </li>
                                        <li>
                                            <a href="/credit/card/mCI.do?t=${creditCard.cardType!}&k=${creditCard.ukValue!}"></a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="follow">
                        <div id="following" class="col-xs-6 ">
                            <div class="find-title">
                                <h3 >关注</h3>
                            </div>

                            <ul>
                            <#if following.totalCount gt 0>
                                <@cardHotList dataList = following.results/>
                            </#if>
                            </ul>
                            <div class="bot_bot">
                                <div id="kkpagerFollow" class="bot_bot kkpager"></div>
                            </div>
                        </div>
                        <div id="follower" class="col-xs-6">
                            <div class="find-title">
                                <h3 >粉丝</h3>
                            </div>
                            <ul>
                            <#if follower.totalCount gt 0>
                            <@cardHotList dataList = follower.results/>
                            </#if>
                            </ul>
                            <div class="bot_bot">
                                <div id="kkpagerFollower" class="bot_bot kkpager"></div>
                            </div>
                        </div>
                    </div>
                    <#--<div class="statistics">-->
                        <#--<div class="find-title">-->
                            <#--<h3>我的消息</h3>-->
                        <#--</div>-->
                        <#--<div class="info-content">-->

                        <#--</div>-->
                    <#--</div>-->
                </div>
            </div>

        </div>
    </div>

</div>
<#include "include/footer.ftl">
<script src="${ctx}/r/cms/www/red/simplePage/js/jquery.z-pager.js"></script>
<script src="${ctx}/r/cms/www/red/js/html2canvas.js"></script>
<script>
    window._bd_share_config = {
    "common": {
        "bdSnsKey": {},
        "bdText": document.title,
        "bdMini": "1",
        "bdMiniList": ["bdxc", "tqf", "douban", "bdhome", "sqq", "thx", "ibaidu", "meilishuo", "mogujie", "diandian", "huaban", "duitang", "hx", "fx", "youdao", "sdo", "qingbiji", "people", "xinhua", "mail", "isohu", "yaolan", "wealink", "ty", "iguba", "fbook", "twi", "linkedin", "h163", "evernotecn", "copy", "print"],
        "bdPic": "${domain!"/"}r/cms/www/red/img/card/${creditCard.cardType!}_${creditCard.ukValue!}_card.png",
        "bdStyle": "1",
        "bdSize": "32"
    }, "share": {}
};
with (document)0[(getElementsByTagName('head')[0] || body).appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion=' + ~(-new Date() / 36e5)];
</script>

<script src="${ctx}/r/cms/www/red/js/credit/creditCard.js"></script>
<script>
//生成名片图片
$(".bdsharebuttonbox li").click(function(){
    var index=$(this).index();
    if(index>0 &&index<5){
        html2canvas($("#psCard"), {
            onrendered: function(canvas) {
//                document.body.appendChild(canvas);
//                var image = canvas.toDataURL("image/png").replace("image/png", "image/octet-stream");
                var image=canvas.toDataURL();
                console.log(image);
            }
        });
    }
});


    function setSelect(url) {
        if(url.indexOf('card')>-1){
            $("#card").addClass("active");
        }else if(url.indexOf('mycard')>-1){
            $("#mycard").addClass("active");
        }else if(url.indexOf('grsmlc')>-1){
            $("#grsmlc").addClass("active");
        }else if(url.indexOf('qysmlc')>-1){
            $("#qysmlc").addClass("active");
        }else if(url.indexOf('sysc')>-1){
            $("#sysc").addClass("active");
        }
    }

    $(function(){
        setSelect(window.location.pathname);

        $("#searchBox li").addClass("col-xs-6");
//判断分页插件的ID
        var id;
        $(".kkpager").hover(function(){
            id=$(this).attr("id");
        });
        //生成分页控件
        kkpager.generPageHtml({
            pagerid: "kkpagerFollow",
            pno: ${following.currentPage},
            mode : 'click',
            //总页码
            total: Math.ceil(${following.totalCount/following.pageSize}),
            //总数据条数
            totalRecords: ${following.totalCount},
            //链接算法
            isGoPage:false,
            isShowTotalPage:false,
            isShowFirstPageBtn:false,
            isShowLastPageBtn:false,
            isShowTotalRecords:false,
            click: function (n) {
                //获取第n页数据
                if(id == "kkpagerFollow"){
                    page(n);
                }
                else if(id == "kkpagerFollower"){
                    pageFollower(n);
                }
            }
        },true);
        kkpager.generPageHtml({
            pagerid: "kkpagerFollower",
            pno: ${follower.currentPage},
            mode : 'click',
            //总页码
            total: Math.ceil(${follower.totalCount/follower.pageSize}),
            //总数据条数
            totalRecords: ${follower.totalCount},
            //链接算法
            isGoPage:false,
            isShowTotalPage:false,
            isShowFirstPageBtn:false,
            isShowLastPageBtn:false,
            isShowTotalRecords:false,
            click: function (n) {
                //获取第n页数据
                if(id == "kkpagerFollow"){
                    page(n);
                }
                else if(id == "kkpagerFollower"){
                    pageFollower(n);
                }
            }
        },true);
    });
//    $("#kkpagerFollow").zPager({
//        url:'/credit/card/page.do',
//        htmlBox: $('#following'),
//        btnShow: false
//        // ,
//        // dataRender: function(data){
//        // 	console.log(data+'---data-2');
//        // }
//    });

    function page(n){
        $.ajax({
            url: "/credit/card/page.do",
            data: {
                followType:following ,
                currentPage:n,
                pageSize: 6
            },
            beforeSend: function () {

            },
            success: function (data) {
                $("#following").html(data);
            },
            error: function () {

            }
        });
    }
    function pageFollower(n){
        $.ajax({
            url: "/credit/card/page.do",
            data: {
                followType:follower ,
                currentPage:n,
                pageSize: 6
            },
            beforeSend: function () {

            },
            success: function (data) {
                $("#follower").html(data);
            },
            error: function () {

            }
        });
    }
</script>
</body>
</html>