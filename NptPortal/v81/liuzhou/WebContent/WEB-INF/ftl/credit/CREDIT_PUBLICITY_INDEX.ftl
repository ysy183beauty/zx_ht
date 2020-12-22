<!--
    信用公示首页
-->
<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta name="save" content="history">
    <meta http-equiv="X-UA-Compatible" content="IE=10" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <title>双公示</title>
    <#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/publicity.css" />
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/nav.css"/>
    <style>
        .cardContent{
            width:90%;
            float:right;
            overflow: hidden;
            padding-right: 15px;
        }
         .table-2pub thead th:nth-child(5),.table-2pub thead th:last-child{
             width:12%;
         }
        .table-2pub td{
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;

        }
        td{
            text-align: center;
        }
    </style>
</head>
<body>

<div class="wrap">
    <div class="body">

    </div>
<#include "include/header.ftl"/>
<div class="">
    <div class="load_main">
        <img src="/r/cms/www/red/img/load.gif">
    </div>
    <div id="container" class="">

            <div class="sit height_auto m-t-md">
                <#--<ul>-->
                    <#--<li>-->
                        <#--<a id="sy" href="/">首页</a>-->
                    <#--</li>-->
                    <#--<li>&gt;</li>-->
                    <#--<li>-->
                        <#--<a href="/credit/pub/index.do">信用公示</a>-->
                    <#--</li>-->
                    <#--<li class="last-tit"></li>-->
                <#--</ul>-->
            </div>
        <div class="row">

            <#include "include/NAV_PUB_INDEX.ftl"/>
             <div class="cardContent" >

                 <div id="mainContent" >
                     <div class="con_top row">
                         <div class="top_left col-sm-4">
                             <div id="map">
                             </div>
                             <div class="time">
                                2017-05-01 至 2017-06-01
                             </div>
                         </div>
                        <div class="top_right col-sm-8">
                             <div class="right_top ">
                                 <div class="col-sm-2" style="padding-left:0px;padding-right:0px;">
                                     <h3 class="">双公示</h3>
                                 </div>

                                 <ul class="col-sm-10"  style="padding-left:0px;padding-right:0px;">
                                     <li>
                                         <span>行政许可：<b>6527</b>项</span>
                                         <span>行政处罚：<b>2846</b></span>
                                         <span>合计双公示：<b>9373项</b  ></span>
                                     </li>
                                     <li>
                                         <span>2017-05-01 至 2017-06-01</span>
                                         <a href="">当天 </a>
                                         <a href="">七天</a>
                                         <a href="">一个月 </a>
                                         <a href="">三个月 </a>
                                         <a href="">更多</a>
                                     </li>
                                 </ul>
                             </div>
                             <div id="map2" >

                             </div>
                         </div>
                     </div>
                     <div class="con_bottom">
                         <div class="bot_title">
                             <h2>公示信息</h2>
                         </div>
                         <div class="bot_con row">
                             <div class="bot_left col-xs-6">
                                 <div class="bot_con_box_left">
                                     <div class="left_title">
                                         <span class="col-xs-9">红黑名单公示</span>
                                         <span>2017-06-30</span>
                                     </div>
                                     <ul class="bot-ul">
                                         <li class="list_first">
                                             <div class="img_first">
                                                 <img src="${ctx}/r/cms/www/red/img/sgs/redBlack.jpg" alt=""/>
                                             </div>
                                             <span>红黑榜名单：中央文明委2014年8月13日发布的《关于推进诚信建设制度化的意见》第十六条明确指出：建立诚信发布制度，推动各地各部门依据法律法规，按照客观、真实、准确的原则，建立诚信红黑名单制度，把恪守诚信者列入“红名单”，把失信违法者列入“黑名单”。
                                            </span>
                                         </li>
                                         <li>
                                             <p>公示单位：（共48个单位） 市发展改革委、市工信委、市教育局、市公安局、市司法局、市财政局 ...</p>
                                             <#--<a href="/credit//pub/2pub/permission.do">更多>></a>-->
                                         </li>
                                         <li>
                                             <p>被公示名单：（共个信用对象） 广西柳州东风商城、柳州(山水柳州)国际游艇俱乐部有限公司、柳州市龙城化工总厂...</p>
                                             <#--<a >更多>></a>-->
                                         </li>
                                     </ul>
                                 </div>

                             </div>
                             <div class="bot_right col-xs-6">
                                 <div class="bot_con_box_right">
                                     <div class="right_title">
                                         <span class="col-xs-9">信用代码公示</span>
                                         <span>2017-06-30</span>
                                     </div>
                                     <ul class="bot-ul">
                                         <li class="list_first">
                                             <div class="img_first">
                                                 <img src="${ctx}/r/cms/www/red/img/sgs/xydm.jpg" alt=""/>
                                             </div>
                                             <span>统一社会信用代码：由工商局、编办、质监局、民政局牵头，根据三证合一的工作要求，将组织机构代码、工商营业号、纳税人营业号合并为统一社会信用代码证，最终实现“一证一码”。</span>
                                         </li>
                                         <li>
                                             <p>公示单位：（共4个单位） 工商局、编办、质监局、民政局</p>
                                             <#--<a href="/credit//pub/2pub/permission.do">更多>></a>-->
                                         </li>
                                         <li>
                                             <p>被公示名单：（共个信用对象） 柳州地区柳州港运输公司、柳州华山医院、柳州铁路福利总厂、柳州和平医院、南宁柳州商会、柳州金融学会、柳州通信学会、柳州军事博物园、柳州警察协会...</p>
                                             <#--<a href="">更多>></a>-->
                                         </li>
                                     </ul>
                                 </div>

                             </div>

                         </div>
                     </div>
                 </div>
             </div>

        </div>


    </div>
</div>
</div>
<#include "include/footer.ftl"/>
</body>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/echarts.min.js"></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/map_publicity.js"></script>
<script type="application/javascript">
//    判断是由首页红黑榜进入
    $(function () {
        //    获取页面参数
        var URLParams = new Array();
        var aParams = document.location.search.substr(1).split('&');
        for (i=0; i < aParams.length ; i++){
            var aParam = aParams[i].split('=');
            URLParams[aParam[0]] = aParam[1];
        }
        var word=URLParams["word"];
        console.log(word);
        if(word && word == 0){
            publicityNavi('/credit/pub/2pub/al/index.do',null,'#mainContent')
        }
        else if(word == 1){
            publicityNavi('/credit/pub/2pub/ap/index.do',null,'#mainContent')
        }
        else if(word == 2){
            publicityNavi('/credit/pub/rbl/red/index.do',null,'#mainContent')
        }
        else if(word == 3){
            publicityNavi('/credit/pub/rbl/black/index.do',null,'#mainContent')
        }
        else if(word == 4){
            publicityNavi('/credit/pub/ucc/index.do',null,'#mainContent')
        }
        else if(word == 5){
            publicityNavi('/dxsl/index.jhtml',null,'#mainContent')
        }
    })
</script>
</html>