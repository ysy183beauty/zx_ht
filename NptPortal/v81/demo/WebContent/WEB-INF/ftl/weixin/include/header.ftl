
		<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
		<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/comm.css" />
		<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/font-awesome.css" />
		<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/style.css" />
		<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/main.css" />
		<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.js" ></script>
        <script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
        <script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.SuperSlide.2.1.1.js"></script>
        <script type="text/javascript" src="${ctx}/r/cms/www/red/js/style.js" ></script>
        <script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
        <link rel="stylesheet" href="${ctx}/r/cms/www/red/js/pager/css/kkpager.css" />
        <script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.scrollTo.min.js"></script>
        <div class="top">
            <div class="w">
                <div class="logo pull-left">
                    <span><img src="${ctx}/r/cms/www/red/img/images/logo.png"></span>
                </div>
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
        <div class="menu">
            <ul>
                <li>
                    <a href="/">首 页</a>
                </li>
                <li>
                    <a href="/bsdt/index.jhtml">信用资讯</a>
                </li>
                <li>
                    <a href="/credit/pub/index.do">信用公示</a>
                </li>
                <li>
                    <a href="/credit/query/index.do">信用查询</a>
                </li>
                <li>
                    <a href="/country/index.jhtml">政策法规</a>
                </li>
                <li>
                    <a href="/xyzs/index.jhtml">信用知识</a>
                </li>
                <li>
                    <a href="/credit/srv/index.do">信用服务</a>
                </li>
                <!-- <li>
                     <a href="/yytj/index.jhtml">异议处理</a>
                 </li>-->
                <li>
                    <a href="/yhxy/index.jhtml">帮助中心</a>
                </li>
                <li>
                    <a href="/member/userInfo.jspx">用户中心</a>
                </li>
            </ul>
        </div>
        <script type="text/javascript">
            function userLogout() {
                //优先执行第三方退出 在执行本地退出
                location="/logout.jspx";
            }
        </script>