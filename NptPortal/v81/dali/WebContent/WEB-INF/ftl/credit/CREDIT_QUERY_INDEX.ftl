<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>企业信用查询</title>
<#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css"/>
    <#--<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/publicity.css"/>-->
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/qyxycx.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xycx.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/nav.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/tableDate.css"/>
</head>
<body oncopy="alert('对不起，禁止复制！');return false;">

<div class="wrap">
<#include "include/header.ftl"/>

    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">信用信息服务平台用户服务协议</h4>
                </div>
                <div class="modal-body">
                    <p>“信用大理”官方网站（以下简称“本网站”）为本网站用户提供企业、重点人群和企业、个人自身在大理区域内的信用信息在线查询服务。为明确用户在接受和提供本网站服务过程中的权利和义务，特制订本协议。用户在通过本网站查询信用信息前，请仔细阅读本协议的全部条款。一经点击 “同意”，则协议成立。如果您是本网站的日常用户，请随时关注本协议以及网站内容的更新。本着平等自愿的原则，双方就相关事宜达成如下协议:</p>
                    <p>第一条：服务事项</p>
                    <p>本网站提供大理地区企业、重点人群和企业、个人自身信用信息在线查询服务。</p>
                    <p>第二条：用户的权利和义务</p>
                    <p>（一）用户必须为年满18岁并具有完全民事行为能力的自然人，或法人。</p>
                    <p>（二）未进行实名认证登录，企业、个人用户只能查询重点人群、企业公开信用信息。不得查询企业、个人保密信息以及敏感信息。</p>
                    <p>（三）实名认证登录后，企业、个人可查询自身详细信用信息。</p>
                    <p>（四）用户点击"同意"，即是向本网站证明、声明和保证用户为所查询信用信息的主体。</p>
                    <p>（五）用户应对以该用户名义进行的申请、查询等所有操作行为承担法律责任。</p>
                    <p>（六）用户应注意以下事项，否则用户承担由此带来的不利后果：</p>
                    <p class="modal-body-info">1.用户应向本网站提供正确、完整、真实的用户注册申请资料和其它表单，并根据实际变化情况及时更新。因注册资料有误引起的后果由用户承担。</p>
                    <p class="modal-body-info">2.用户妥善保管本人信息，包括并不限于账号、密码。如因用户本人保管个人信息不善，或其它因素出现安全问题，导致他人获得您的个人信息，或因此导致个人信息或企业信息被他人取得，可能导致用户遭受损失的后果由用户承担。</p>
                    <p class="modal-body-info">3. 遵守中国有关的法律和法规，不得为任何非法目的而使用该网站，遵守所有与网络服务有关的网络协议、规定和程序，不得利用本网站进行任何可能对互联网的正常运转造成不利影响的行为，不得利用本网站进行任何不利于云南政务服务网的行为。</p>

                    <p>（七）查询获得的信用信息仅供用户了解自己的信用状况以及信息是否正确、完整，并因此提出异议申请。</p>
                    <p>（八）用户不得传送任何包含病毒、木马、蠕虫等可能破坏，感染，密码拦截任何系统，数据和信息的程序，不得通过黑客、密码破译等方式违法侵入计算机和网络系统，他人账户。</p>
                    <p>第三条：信用信息中心的权利和义务</p>
                    <p>（一）中心有权制定本网站查询服务的相关业务操作规范。</p>
                    <p>（二）中心有权依据法律、法规、规章或业务需要对信用信息查询的服务内容、操作流程进行调整，并在本网站对外公告有关变更事项后实施，不另行单独通知用户。</p>
                    <p>（三）中心收集本人信息，只为核实并确认身份及从数据库中抽取正确的信用信息，不会对外提供或泄露，不会用作其他用途。</p>
                    <p>（四）为处理用户使用本网站查询全社会信用信息的需求，以确定用户即为上述所证明、声明和保证的人士，中心可查阅和使用系统中任何和全部用户的信用信息，以及用户在使用本网站时所提供的用于配对的资料。</p>
                    <p>（五）中心建立和完善内部管理制度，维护本网站的正常运行，保证按照有关业务规则公布的时间对外提供服务。</p>
                    <p>（六）本网站提供的网络服务内容可能包括：文字、软件、声音、图片、录象、图表等。所有这些内容受版权、商标和其它财产所有权法律的保护，用户只有在获得本网站或其他相关权利人的授权之后才能使用这些内容，而不能擅自复制、再造这些内容、或创造与内容有关的派生产品。</p>
                    <p>第四条 责任说明</p>
                    <p>（一）因下列原因致使本网站不能正常提供服务而可能导致的损失，中心不承担责任：</p>
                    <p class="modal-body-info">1.因台风、地震、海啸、洪水、战争等不可抗力因素，造成互联网不能正常执行业务。</p>
                    <p class="modal-body-info">2.计算机病毒、黑客攻击、网络通信故障等中心不能控制的因素。</p>
                    <p class="modal-body-info">3.为了维护本网站的正常运行，中心定期或不定期地对系统运行的相关设备进行维护或者检修，因此而造成查询服务在合理时间内的中断，中心不承担责任。</p>
                    <p>（二）用户明确同意其使用本网站服务所存在的风险将完全由其自己承担；因其使用本网站服务而产生的一切后果也由其自己承担，中心对用户不承担任何责任。</p>
                    <p>（三）本网站不担保网络服务一定能满足用户的要求，也不担保网络服务不会中断，对网络服务的及时性、安全性、准确性也都不作担保。</p>
                    <p>（四）本网站不保证为方便用户而设置的外部链接的准确性和完整性，同时，对于该等外部链接指向的不由本站实际控制的任何网页上的内容，本网站不承担任何责任</p>
                    <p>（五）本网站提供查询的企业、个人和其他组织信用信息由各地各部门报送，信用信息保密级别也由其自行确定，中心不对信用信息的正确性和保密级别设定负责，但接受异议和投诉处理，并转相关职能部门解决。</p>
                    <p>第五条 协议的变更和终止</p>
                    <p>鉴于网络服务的特殊性，中心变更本协议及其附件的若干条款时，将提前通过本网站公告有关变更事项。如用户在本网站发布上述协议变更的有关公告后继续使用互联网查询的，视为用户已接受协议的有关变更，并受其约束。本协议中的相关条款根据该变更而自动做相应修改，双方无须另行签订书面协议。</p>
                </div>
                <div class="modal-footer">
                    <div id="xieyi">
                        <input id="xieyi-text" type="checkbox" onclick="agree()">
                        <span>同意信用信息服务平台用户服务协议</span>
                    </div>
                    <div>
                        <button id="sumbit-btn" type="button" class="btn btn-primary" data-dismiss="modal"
                                onclick="loginQue() " disabled>确认登录</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="myModal_authed" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title">提示信息：</h4>
                </div>
                <div class="modal-body">
                 <p>请立即前往实名认证页面！</p>
                </div>
                <div class="modal-footer">
                    <div>
                        <button id="sumbit-btn" type="button" class="btn btn-primary" data-dismiss="modal"
                                onclick="loginQue()">确认前往</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
<div class="main">
    <div class="load_main">
        <img src="/r/cms/www/red/img/load.gif">
    </div>
    <div id="container" class="container">
        <div class="sit height_auto m-t-md">
            <ul>
                <li>
                    <a id="sy" href="/">首页</a>
                </li>
                <li>&gt;</li>
                <li>
                    <a href="/credit/query/index.do">信用查询</a>
                </li>
                <li class="last-tit"></li>
            </ul>
        </div>
        <div class="row">
            <div class="content">

                <div id="mainContent">
                    <div class="query-con">
                        <div class="banner">
                            <div class="banner-con">
                                <div class="banner-title">
                                    <span id="bus"><a class="banner-on">企业</a><i class="index-arrow-down"></i></span>
                                    <span id="self" data-toggle="modal" onclick="loginSelf('/credit/query/ps/index.do',null,'#searchContent')"><a>注册用户</a><i class="index-arrow-down"></i></span>
                                <#--功能正常，目前不需要    -->
                                <#--<span id="kc"><a>重点人群</a><i class="index-arrow-down"></i></span>-->

                                </div>
                                <div class="banner-input">
                                    <span style="float: left;width:90%;" id="span">
                                        <input style="float: left; padding:13px;width:65%;border:0;outline:none; height:50px;background-color:#fff; border:1px solid #ccc;" id="queryInput" type="text" onfocus="clearMsg()" placeholder="请输入企业名称，法定代表人姓名，工商注册号，组织机构代码">
                                        <img style="cursor: pointer;float: left;width:16%; height:50px;background-color:#fff; border:1px solid #1AB394;margin-left: 0.5%;margin-right: 0.5%;" id="img" onclick="this.src='/captcha.svl?d='+new Date()" alt="" src="/captcha.svl">
                                        <input type="text" placeholder="图片验证码" style="float: left;width:17%; padding:13px;border:0;outline:none; height:50px;background-color:#fff; border:1px solid #ccc;" onfocus="clearCaptchaMsg()" maxlength="6" name="captcha" id="captcha">
                                    </span>
                                    <button class="btn" onClick="searchInfo();"></button>
                                    <div class="index-search-tip" style="left:35px;" id="search-tip">
                                        <p class="search-count " id="indexTip">搜索关键词不得少于２个字符</p>
                                        <span class="corner-main">◆</span>
                                        <span class="corner-bottom">◆</span>
                                    </div>
                                    <div class="index-search-tip" id="captchaMsg">
                                        <p class="search-count " id="errorCaptcha">验证码不能为空</p>
                                        <span class="corner-main">◆</span>
                                        <span class="corner-bottom">◆</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <ul class="banner-con-ul">
                            <li>
                                <div>
                                    <i class="content-icon content-icon1"></i>
                                    <p>${bsCount}企业</p>
                                    <span>支持多种查询，海量信息想查就查</span>
                                </div>
                            </li>
                            <li>
                                <div>
                                    <i class="content-icon content-icon2"></i>
                                    <p>官方个人信息</p>
                                    <span>经实名认证可查询更多详细信息，保护你的隐私</span>
                                </div>
                            </li>
                            <li>
                                <div>
                                    <i class="content-icon content-icon3"></i>
                                    <p>权威来源</p>
                                    <span>数据源于大理州各个政府部门，实时更新</span>
                                </div>
                            </li>
                            <li>
                                <div>
                                    <i class="content-icon content-icon4"></i>
                                    <p>全量数据</p>
                                    <span>基础信息、资质信息、荣誉信息处罚信息等</span>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div id="searchContent">

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<#include "include/footer.ftl"/>
</body>
<script type="text/javascript">

    $('.banner-title span').click(function () {
        var id = $(this).attr("id");
        if (id == "bus") {
            $("#queryInput").attr("placeholder", "请输入企业名称，法定代表人姓名，工商注册号，组织机构代码");
        }
        else if (id == "kc") {
            $("#queryInput").attr("placeholder", "请输入个人姓名");
        }
        else if (id == "self") {
            $("#queryInput").attr("placeholder", "请输入搜索关键字");
        }
//        support();
    });

    $(function () {
        $("#bus a").addClass("banner-on");
        $("#bus i").css("display","block");
        checkCaptcha();
    });

    function checkCaptcha() {
        var error='${captcha}';
        if(error){
            $('#img').attr('src','/captcha.svl?d='+new Date())
            $('#errorCaptcha').text('图片验证码错误');
            $("#captchaMsg").css("display", "block");
        }
        var keyword='${keyword}';
        if(keyword){
            $('#queryCondition').val(keyword);
        }
    }

    $(".banner-title span").bind("click",function () {
        $(".banner-input button").removeAttr("disabled");
        $(this).find("a").stop().addClass("banner-on").parent().siblings().find("a").removeClass("banner-on");
        $(this).find("i").stop().show().parent().siblings().find("i").hide();
        if($(this).attr("id") == "self"){
//            console.log($(this).attr("id"));
            $(".banner-input button").attr("disabled","disabled");
        }
    });
    function searchInfo() {
        var id = $('.banner-title a[class=banner-on]').parent().attr('id');
        var captcha = $('#captcha').val();
        if(!captcha){
            var width = $('#span').width()*0.83;
            $("#captchaMsg").css('left',width);
            $('#errorCaptcha').text('验证码不能为空');
            $("#captchaMsg").css("display", "block");

            return;
        }

        var keyWord = $('#queryInput').val();
        if (keyWord && keyWord.length > 1) {
            $(".load_main").show();
            if (id == "bus") {
                $.ajax({
                    url: encodeURI("/credit/query/bs/bsSearch.do?keyword=" + keyWord+"&captcha="+captcha),
                    success: function (data) {
                        if(data=="error"){
                            var width = $('#span').width()*0.83;
                            $("#captchaMsg").css('left',width);
                            $('#errorCaptcha').text('图片验证码错误');
                            $("#captchaMsg").css("display", "block");
                        }else {
                            $("#searchContent").html(data);
                            $("body .main").eq(1).css("background", "none");
                            $.scrollTo("#searchContent", 500);
                        }
                        $(".load_main").hide();
                    },
                    error:function(){
                        $("#searchContent").html("数据请求失败！");
                        $(".load_main").hide();
                    }
                });
            }
//            功能正常，目前不需要
//            else if (id == "kc") {
//                $.ajax({
//                    url:encodeURI( "/credit/query/kc/kcSearch.do?keyword=" + keyWord),
//                    success: function (data) {
//                        $("#searchContent").html(data);
//                        $("body .main").eq(1).css("background", "none");
//                        $.scrollTo("#searchContent", 500);
//                        $(".load_main").hide();
//                    }
//                });
//            }
        }
        else {
            $("#search-tip").css("display", "block");
        }
        $('#img').attr('src','/captcha.svl?d='+new Date())
    }
//    提示框隐藏
    function clearMsg() {
        $("#search-tip").css("display", "none");
    }

    //    提示框隐藏
    function clearCaptchaMsg() {
        $("#captchaMsg").css("display", "none");
    }

//    模态框同意协议
    function agree() {
        var check = document.getElementById("xieyi-text").checked;
//        console.log(check);
        if(check == true){
//            console.log(check);
            $("#sumbit-btn").removeAttr("disabled");
        }
        else{
            $("#sumbit-btn").attr("disabled","disabled");
        }
    }
//    自身登录
    function loginSelf(url,param,domId) {
        <#if user??>

            <#if user_authed?? && user_authed == true>
                queryNavi(url,param,domId);
            <#else>
                $("#self").attr( "data-target","#myModal_authed");
            </#if>
        <#else>
            $("#self").attr( "data-target","#myModal");
        </#if>
    }

    function loginQue() {
        <#if user??>
            window.location.href="/member/profile.jspx";
        <#else>
            window.location.href="/login.jspx";
        </#if>
    }

    function queryNavi(url,param,domId){
        $(".load_main").show();
        $.ajax({
            url: url,
            data: param,
            timeout: 30000,
            success: function (data) {
                $(domId).html(data);
                load();
                $.scrollTo("#searchContent", 500);
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
    function load(){
        window.setTimeout("$('.load_main').hide()",100);//100毫秒后，隐藏你的DIV
    }
</script>
</html>