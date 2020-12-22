<div class="contain-header">
    <div class="dl ft14 tr">
        <div class="head-login">
            <div class="login pull-right m-t-sm">
                <ul>
                [#if user??]
                    <div id="ydl">
                        <li>
                            <div style="float: left;margin-top: 2px;">欢迎: </div>
                            <a id="showName" href="/member/userInfo.jspx" style="float: right;margin-left: 3px;color:blue;">${user.username!}
                            </a>
                        </li>
                        [#if user.flag??]
                            [#if user.flag=='3']
                                <li> <span class="glyphicon glyphicon-ok-sign" title="已认证" style="margin-left: 2px;color: #02a831;margin-top: 3px;" ></span>|</li>
                            [#elseif user.flag=='4']
                                <li> <span class="glyphicon glyphicon-ok-sign" title="认证失败" style="margin-left: 2px;color: red;margin-top: 3px;" ></span>|</li>
                            [#elseif user.flag=='2']
                                <li> <span class="glyphicon glyphicon-ok-sign" title="认证中" style="margin-left: 2px;margin-top: 3px;" ></span>|</li>
                            [#else]
                                <li> <span class="glyphicon glyphicon-ok-sign" title="其他" style="margin-left: 2px;margin-top: 3px;" ></span>|</li>
                            [/#if]
                        [#else]
                            <li> <span class="glyphicon glyphicon-ok-sign" title="未认证" style="margin-left: 2px;margin-top: 3px;" ></span>|</li>
                        [/#if]
                        <li>
                            <a onClick="userLogout()" style="cursor: pointer">退出</a>
                        </li>
                    </div>
                [#else]
                    <div id="wdl">
                        <li>
                            <a href="/login.jspx" style="cursor: pointer">登录</a>
                        </li>
                        <li>|</li>
                        <li>
                            <a href="/register.jspx" style="cursor: pointer">注册</a>
                        </li>
                    </div>
                [/#if]
                </ul>
            </div>
        </div>
    </div>
    <!-- logo -->
    <div class="logo"><img src="${ctx}/r/cms/www/red/1/images/logo.png" /></div>
</div>

<!-- 菜单栏 -->
<div class="contain-nav">
    <div class="nav ft16 clearfix nav-css">
        <p class='nav_date fl'></p>
        <span id="time">2017年5月18日  星期四</span>
        <ul class="fr clearfix nav-index">
            <li><a href="/" >首页</a> </li>
            [#--<li>--]
                [#--<a href="/bsdt/index.jhtml">信用资讯</a>--]
            [#--</li>--]
            <li>
                <a href="/xyzs/index.jhtml">政策知识</a>
            </li>
            <li>
                <a href="/credit/pub/index.do">信用公示</a>
            </li>
            <li>
                <a href="/credit/query/index.do">信用查询</a>
            </li>

            <li>
                <a href="/xyfx/index.jhtml">信用分析</a>
            </li>
            <li>
                <a href="/xypj/index.jhtml">信用分析</a>
            </li>
            <li>
                <a href="/credit/srv/index.do">信用服务</a>
            </li>
            <li>
                <a href="/member/userInfo.jspx">用户中心</a>
            </li>
        </ul>
    </div>
</div>


<script type="text/javascript">
//            判断浏览器，提示升级
$(function(w){
    if(!("WebSocket"in w&&2===w.WebSocket.CLOSING)){
        var d=document.createElement("div");
        d.className="browsehappy";
        d.innerHTML='<div style="width:100%;height:100px;font-size:20px;line-height:100px;text-align:center;background-color:#E90D24;color:#fff;margin-bottom:40px;">\u4f60\u7684\u6d4f\u89c8\u5668\u5b9e\u5728<strong>\u592a\u592a\u65e7\u4e86</strong>\uff0c会影响您使用本网站。请点击 <a target="_blank" href="http://browsehappy.osfipin.com/" style="background-color:#31b0d5;border-color: #269abc;text-decoration: none;padding: 6px 12px;background-image: none;border: 1px solid transparent;border-radius: 4px;color:#FFEB3B;">\u7acb\u5373\u5347\u7ea7</a></div>';
        var f=function(){
            var s=document.getElementsByTagName("body")[0];
            if ("undefined" == typeof(s)) {
                setTimeout(f, 10)
            }
            else {
                s.insertBefore(d, s.firstChild)
            }
        };
        f()
    }
}(window));


    var loginName;
    var userName;
    var type;
    var flag;
    $(function () {
        initUser();
    });

    function userLogout() {
        //优先执行第三方退出 在执行本地退出
        location = "/logout.jspx";
    }


    function initUser() {
    [#if user??]
        loginName = '${user.loginName!}';
        userName = '${user.userName!}';
        type = '${user.type!}';
        flag = '${user.flag!}';
    [/#if]
    }


</script>