<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<link rel="stylesheet" href="../../r/cms/www/red/css/bootstrap.css" />
<link rel="stylesheet" href="../../r/cms/www/red/css/comm.css" />
<link rel="stylesheet" href="../../r/cms/www/red/css/font-awesome.css" />
<link rel="stylesheet" href="../../r/cms/www/red/css/style.css" />
<link rel="stylesheet" href="../../r/cms/www/red/css/main.css" />
<script type="text/javascript" src="../../r/cms/www/red/js/jquery-2.1.0.js" ></script>
<script type="text/javascript" src="../../r/cms/www/red/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="../../r/cms/www/red/js/style.js" ></script>
<script type="text/javascript" src="../../r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="../../r/cms/www/red/js/login.js" ></script>
<script type="text/javascript" src="../../r/cms/www/red/js/user.js" ></script>
<script type="text/javascript" src="../../r/cms/www/red/js/rules.js" ></script>
<script type="text/javascript" src="../../r/cms/www/red/js/jquery.validate.js" ></script>
<script type="text/javascript" src="../../r/cms/www/red/js/formValidatorClass.js" ></script>
<script type="text/javascript" src="../../r/cms/www/red/js/common.js" ></script>
<div class="top">
    <div class="w">
        <div class="logo pull-left">
            <span><img src="../../r/cms/www/red/img/images/logo.png"></span>
        </div>
        <div class="login pull-right m-t-sm">
            <ul>
                <div id="wdl">
                    <li>
                        <a onClick="showLogin()" style="cursor: pointer">登录</a>
                    </li>
                    <li>|</li>
                    <li>
                        <a onClick="showRegin()" style="cursor: pointer">注册</a>
                    </li>
                </div>
                <div style="display: none" id="ydl">
                    <li>
                        <div style="float: left;margin-top: 2px;">欢迎: </div>
                        <a id="showName" onClick="" style="cursor: pointer;float: right;margin-left: 3px;color:blue;"></a>
                        <div class="clear"></div>
                    </li>
                    <li>|</li>
                    <li>
                        <a onClick="userLogout()" style="cursor: pointer">退出</a>
                    </li>
                </div>
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
            <a href="/xzxk/index.jhtml">信用公示</a>
        </li>
        <li>
            <a href="/credit/credit_company/baseCompanyList?start=0&amp;limit=0">信用查询</a>
        </li>
        <li>
            <a href="/country/index.jhtml">政策法规</a>
        </li>
        <li>
            <a href="/xyzs/index.jhtml">信用知识</a>
        </li>
        <li>
            <a href="/fwjg/index.jhtml">信用服务</a>
        </li>
        <li>
            <a href="/yytj/index.jhtml">异议处理</a>
        </li>
        <li>
            <a href="/yhxy/index.jhtml">帮助中心</a>
        </li>
    </ul>
</div>

<!--登陆-->
<div class="modal inmodal fade" id="login" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h3 class="modal-title pull-left">欢迎登录信用大理</h3>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="col-lg-2 control-label">登录名</label>
                        <div class="col-lg-10">
                            <input id="username" type="text" placeholder="用户名/手机号/邮箱" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-2 control-label">密码</label>
                        <div class="col-lg-10">
                            <input id="loginpwd" type="password" placeholder="6-20个字符（区分大小写）" class="form-control">
                        </div>
                    </div>
                    <P class="text-right">
                        <a class="forget" onClick="showChange()">忘记密码？</a>
                    </P>
                    <div class="form-group">
                        <div class="col-lg-10 pull-right">
                            <button type="button" class="btn_sytle" onclick="userLogin()">立即登录</button>
                        </div>
                    </div>
                    <p class="text-right">没有帐号？
                        <a class="reign_a" onClick="showRegin()">立即注册</a>
                    </p>
                </form>
            </div>
        </div>
    </div>
</div>
<!--注册-->
<div class="modal inmodal fade" id="regin" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h3 class="modal-title pull-left">欢迎来信用大理注册</h3>
            </div>
            <div class="modal-body">
                <form class="form-horizontal"  id="registerform" name="registerform" novalidate="novalidate">
                    <div class="form-group" >
                        <div class="col-lg-3 control-label">用户类别<font>*</font></div>
                        <div class="col-lg-9">
                            <select id="usertype" type="text" class="form-control" onchange="TypeChange()">
                                <option value="person" selected="selected">个人账户</option>
                                <option value="company">企业账户</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" >
                        <div id="loginUserNameTitle"  class="col-lg-3 control-label">个人姓名<font>*</font></div>
                        <div class="col-lg-9">
                            <input id="loginUserName" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-3 control-label">用户名<font>*</font></div>
                        <div class="col-lg-9" >
                            <input id="loginName" type="text" placeholder="2-14个字符：英文、数字或中文" class="form-control">
                        </div>
                    </div>
                    <div class="form-group"  >
                        <div class="col-lg-3 control-label">密码<font>*</font></div>
                        <div class="col-lg-9" id="showpasswd">
                            <input type="password" placeholder="6-20个字符（区分大小写）"  maxlength="20" id="repassword" name="repassword"
                                   onkeyup="value=value.replace(/\s/g,'')" onpaste="return false;" oncontextmenu="return false;"
                                   oncopy="return false;" oncut="return false;" class="form-control">
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-3 control-label">确认密码<font>*</font></div>
                        <div class="col-lg-9" id="repwd">
                            <input type="password" placeholder="再次输入密码"  oncut="return false;" id="checkpassword" oncontextmenu="return false;" oncopy="return false;"
                                   onkeyup="value=value.replace(/\s/g,'')" onpaste="return false;" name="checkpassword" maxlength="20"class="form-control">
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-3 control-label">邮箱<font>*</font></div>
                        <div class="col-lg-9" id="showemail">
                            <input type="email" id="email" name="email" placeholder="请输入您的邮箱账号" class="form-control">
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-3 control-label">验证码<font>*</font></div>
                        <div class="col-lg-9" id="showcode">
                            <input type="text" placeholder="图片验证码" class="form-control pull-left yz_input" maxlength="6" name="safecode" id="safecode">
                            <div id="yz" class="pull-left" style="margin-left: -20px;width: 15px;line-height: 34px;height: 34px;"></div>
                            <div class="pull-left yzm_img m-l-sm">
                                <input class="zcyzm" id="checkCode" onclick="createCode()" onfocus=this.blur()></input>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-3 control-label">手机号码<font>*</font></div>
                        <div class="col-lg-9"  id="showmobile">
                            <input type="text" placeholder="请输入您的手机号" class="form-control" maxlength="13" id="mobilenum" name="mobilenum">
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-3 control-label">校验码<font>*</font></div>
                        <div class="col-lg-9" id="showmsgcode">
                            <input type="text" placeholder="手机验证码" class="form-control pull-left yz_input" id="msgcode" name="msgcode" maxlength="10">
                            <input class="jy_btn pull-right disabled" type="button"  id="sendmsgcode" onClick="sendmsg(this)" value="发送验证码" style="text-align: center"></input>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-10 pull-right p-xxs">
                            <div class="m-l-md pull-left">
                                <input type="checkbox" style="width: 12px;height: 12px;" id="xieyi"/>
                                <div style="float: right;">我已经阅读并同意
                                    <a class="reign_a" href="/yhxy/index.jhtml"  target="_blank">《用户服务协议》</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-10 pull-right">
                            <button type="button" onClick="registerUser();" class="btn_sytle" id="btnsub">立即注册</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<!--找回密码-->
<div class="modal inmodal fade" id="zh_password" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h3 class="modal-title pull-left">欢迎登录信用大理</h3>
            </div>
            <div class="modal-body">
                <input id="name" style="display: none;">
                <div class="tablist m-b-lg">
                    <ul>
                        <li id="dlmbt" class="active">登录名</li>
                        <li id="yzsfbt">验证身份</li>
                        <li id="xgmmbt">修改密码</li>
                        <li id="wcbt">完成</li>
                    </ul>
                </div>
                <form class="form-horizontal w_100">
                    <div class="tab_0 w310 tab_m" id="dygym">
                        <div class="form-group">
                            <label class="col-lg-2 control-label">登录名</label>
                            <div class="col-lg-10">
                                <input type="text" id="checkUuid" placeholder="用户名/手机号" class="form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-2 control-label">验证码</label>
                            <div class="col-lg-10">
                                <input type="text" placeholder="" class="form-control pull-left yz_input">
                                <div class="pull-left yzm_img m-l-sm">
                                    <input id="checkUsercCode" class="zcyzm"  onclick="createUserCode()" onfocus=this.blur()></input>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-lg-10 pull-right">
                                <button type="button" class="btn_sytle" onclick="backPassword_checkUser()">下一步</button>
                            </div>
                        </div>
                    </div>
                    <div class="tab_1 tab_m" id="fsyzm">
                        <p>您正在为帐号<span id="checkPhone1" class="color_blue"></span>找回密码,为了保护帐号安全，需要验证身份</p>
                        <div class="box m-t-md">
                            <div class="pull-left fa fa-mobile s1"></div>
                            <div class="pull-left">通过密保手机<span id="checkPhone2" class="color_blue"></span>验证</div>
                            <button  type="button" class="pull-right btn_sytle btn-w-m" onClick="xgmmNext('yzjym','yzsfbt')">验证</button>
                        </div>
                        <!--<div class="box m-t-md">
                            <div class="pull-left fa fa-envelope-o s2"></div>
                            <div class="pull-left">通过登录邮箱<span class="color_blue">1*****0@qq.com</span>验证</div>
                            <button  type="button" class="pull-right btn_sytle btn-w-m">验证</button>
                        </div>-->
                    </div>
                    <div class="tab_2 tab_m" id="yzjym">
                        <p class="text-center">短信验证码已发送至<span  id="checkPhone3" class="color_blue"></span></p>
                        <div class="form-group text-center">
                            <div class="w260">
                                <input id="sjyzm" type="text" placeholder="" class="form-control pull-left jy_input">
                                <button  type="button" class="jy_btn pull-right" onClick="backPassword_sendMsg()">获取验证码</button>
                                <button  type="button" class="btn_sytle m-t-md" onClick="xgmmNext('srxmm','xgmmbt')">下一步</button>
                            </div>
                        </div>
                    </div>
                    <div class="tab_3 w310 tab_m" id="srxmm">
                        <div class="form-horizontal">
                            <div class="form-group">
                                <label class="col-lg-2 control-label">新密码</label>
                                <div class="col-lg-10">
                                    <input id="changepassword1" type="password" placeholder="6-20个字符（区分大小写）" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-lg-2 control-label">确认密码</label>
                                <div class="col-lg-10">
                                    <input id="changepassword2" type="password" placeholder="再次输入密码" class="form-control">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-lg-10 pull-right">
                                    <button type="button" class="btn_sytle" onClick="backPassword_changePassword()">下一步</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="tab_4 text-center tab_m" id="xgcg">
                        <img src="${resSys}/www/red/img/images/ok.png">
                        <p class="m-t-md">恭喜你，密码修改成功！</p>
                    </div>

                </form>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        createUserCode();
        //用户判断
        var username=window.sessionStorage.getItem('username');
        if(username){
            $("#ydl").show();
            $("#wdl").hide();
            $("#showName").html(username);
        }
    });

    function showChange(){
        createUserCode();
        $("#login").modal('hide');
        $("#zh_password").modal('show');
        xgmmNext('dygym','dlmbt');
    }
    function showLogin(){
        $("#login").modal('show');
    }
    function showRegin(){
        $("#login").modal('hide');
        $("#regin").modal('show');
        $("#loginregist").val('');
        $("#repassword").val('');
        $("#checkpassword").val('');
        $("#mobilenum").val('');
        $("#msgcode").val('');
        $("#cardnum").val('');
        $("#safecode").val('');
        $("#email").val('');
        createCode();
    }
    function xgmmNext(id,titleId) {
        //标题掩藏
        $("#dlmbt").removeClass("active");
        $("#yzsfbt").removeClass("active");
        $("#xgmmbt").removeClass("active");
        $("#wcbt").removeClass("active");
        //内容掩藏
        $("#yzjym").hide();
        $("#fsyzm").hide();
        $("#dygym").hide();
        $("#xgcg").hide();
        $("#srxmm").hide();
        $("#"+id).show();
        $("#"+titleId).addClass("active");
    }
    function registerUser() {

        if(!$('#xieyi').is(':checked')) {
            alert("不同意相关协议，不允许注册！");
            return;
        }

        var safecode =$("#safecode").val();
        if(safecode!=code){
            alert("图片验证码不正确！");
            return;
        }

        var username =$("#loginUserName").val();
        if(!username){
            alert("公司名称或者个人姓名不能为空！");
            return;
        }
        var loginname =$("#loginName").val();
        if(!loginname){
            alert("用户名不能为空！");
            return;
        }
        var email =$("#email").val();
        if(!email){
            alert("电子邮件不能为空！");
            return;
        }else{
            var reg=/[a-zA-Z0-9]{1,10}@[a-zA-Z0-9]{1,5}\.[a-zA-Z0-9]{1,5}/;
            if(!reg.test(email)){
                alert("请正确填写邮箱！");
                return;
            }
        }
        var password1 =$("#repassword").val();
        if(!password1){
            alert("密码不能为空！");
            return;
        }
        var password2 =$("#checkpassword").val();
        if(password2!=password1){
            alert("两次密码不相等！");
            return;
        }
        var phone =$("#mobilenum").val();
        if(!phone){
            alert("手机号码不能为空！");
            return;
        }else{
            var reg=/^1[0-9]{10}/;
            if(!reg.test(phone)){
                alert("请正确填写手机号！");
                return;
            }
        }
        var msgcode =$("#msgcode").val();
        if(!msgcode){
            alert("短信验证码不能为空！");
            return;
        }

        var type = $("#usertype").val();
        $.ajax({
            url: encodeURI("/credit/user/userRegister"),
            dataType : 'json',
            data:{loginname:loginname,
                username:username,
                password:password1,
                phone:phone,
                email:email,
                phoneyzm:msgcode,
                type,type},
            success : function(data) {
                alert(data.msg);
                if(data.flag=='ok'){
                    $("#regin").modal('hide');
                    $("#registerform").reset();
                }
            },
            error : function(response) {
                var s=response;
                alert(s.statusText);
            },
            timeout:60000
        });
    }

    //注册修改类型
    function TypeChange(){
        var type = $("#usertype").val();
        if(type=="person"){
            $('#loginUserNameTitle').html("个人姓名<font>*</font>");
        }else{
            $('#loginUserNameTitle').html("企业名称<font>*</font>");
        }
    }

    function userLogin() {
        var username = $("#username").val();
        var pwd = $("#loginpwd").val();
        $.ajax({
            url: encodeURI("/credit/user/userLogin"),
            dataType : 'json',
            data:{loginname:username,
                password:pwd},
            success : function(data) {
                if(data.flag=='ok'){
                    $("#login").modal('hide');
                    $("#ydl").show();
                    $("#wdl").hide();
                    var username= data['list']['USERNAME'];
                    $("#showName").html(username);
                    window.sessionStorage.setItem('username', username);
                    $("#username").val("");
                    $("#loginpwd").val("");
                }else {
                    alert(data.msg+"用户名或密码不正确。");
                    $("#login").modal('show');
                }
            },
            error : function(response) {
                var s=response;
                alert(s.statusText);
            },
            timeout:6000
        });
    }

    function userLogout() {
        $.ajax({
            url: encodeURI("/credit/user/userLogout"),
            dataType : 'json',
            data:{},
            success : function(data) {
                window.sessionStorage.removeItem('username');
                //alert("退出登录成功！");
                $("#showName").html('');
                $("#ydl").hide();
                $("#wdl").show();
            },
            error : function(response) {
                var s=response;
                alert(s.statusText);
            },
            timeout:6000
        });
    }

    function backPassword_checkUser() {
        var uuidName = $("#checkUuid").val();
        var reg=/^1[0-9]{10}/;
        var type='';
        if(reg.test(uuidName)){
            type='phone';
        }else {
            type='username';
        }
        if(checkUsercCode!=$('#checkUsercCode').val()){
            alert("输入的验证码不对");
            return;
        }
        $.ajax({
            url: encodeURI("/credit/user/checkUser"),
            dataType : 'json',
            data:{type:type,
                value:uuidName},
            success : function(data) {
                if(data){
                    var phone = data['PHONE'];
                    $("#name").val(phone);
                    if(phone){
                        phone = phone.substr(0,4)+"****"+phone.substr(8,3);
                        $("#checkPhone1").html(phone);
                        $("#checkPhone2").html(phone);
                        $("#checkPhone3").html(phone);
                        xgmmNext('fsyzm','yzsfbt');
                    }else{
                        alert("输入的用户名不正确")
                    }
                }else{
                    alert("输入的用户名不正确")
                }
            },
            error : function(response) {
                var s=response;
                alert(s.statusText);
            },
            timeout:60000
        });
    }

    function backPassword_sendMsg() {
        var phone = $("#name").val();
        $.ajax({
            url: encodeURI("/credit/user/sendMessage"),
            dataType : 'json',
            data:{phone:phone},
            success : function(data) {
                if(data){
                    alert("发送成功！")
                }else{
                    alert("发送失败！")
                }
            },
            error : function(response) {
                var s=response;
                alert(s.statusText);
            },
            timeout:60000
        });
    }

    function backPassword_checkMsg() {
        var yzm=$("#sjyzm").val();
        var phone = $("#name").val();
        $.ajax({
            url: encodeURI("/credit/user/checkMessage"),
            dataType : 'json',
            data:{yzm:yzm,
                phone:phone},
            success : function(data) {
                if(data){
                    xgmmNext('srxmm','xgmmbt');
                }else{
                    alert("短信验证码校验失败！")
                }
            },
            error : function(response) {
                var s=response;
                alert(s.statusText );
            },
            timeout:60000
        });
    }

    function backPassword_changePassword() {

        var pwd1=$('#changepassword2').val();
        var pwd2=$('#changepassword1').val();
        var phone = $("#name").val();
        if(pwd1!=pwd2){
            alert("两次输入的密码不一样！");
            return;
        }

        $.ajax({
            url: encodeURI("/credit/user/changePassword"),
            dataType : 'json',
            data:{pwd:pwd1,
                phone:phone},
            success : function(data) {
                if(data=='ok'){
                    xgmmNext('xgcg','wcbt');
                }else{
                    alert("密码修改失败！")
                }
            },
            error : function(response) {
                var s=response;
                alert(s.statusText );
            },
            timeout:60000
        });

    }

</script>