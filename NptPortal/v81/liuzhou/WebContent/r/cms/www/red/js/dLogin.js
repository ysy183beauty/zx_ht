var EHAOTONGObj;

//初始化
function EHAOTONG(obj) {
	//render, passLoginFunc, failLoginFunc, submitType(norefresh), toUrl, closeable, 
	//submitTarget(_self, _blank, _parent), shade, backgroundStyle(mini), showTipMsn,
	//source
	
	EHAOTONGObj = obj;
	
	EHAOTONGcheckIsLogin();
	
	$("#"+EHAOTONGObj.render).parent().append('<link href="https://e.liuzhou.gov.cn/skin/lz/css/divLogin/lzlogin.css" rel="stylesheet">');
}


//hecy代码开始
var divHtml = '' +
'<div id="EHAOTONGLOGINDiV" class="cy-lzlogin" style="display: block; z-index:9999">'+
'<a href="javascript:void(0)" class="cy-closelogin" id="EHAOTONGCloseBtn" onclick="closeEHAOTONGLogin()"></a>'+

'<div class="cy-loginContent">'+
'      <!--[if lt IE 9]>'+
'          <div class="ieShadow"></div>'+
'      <![endif]-->'+
'<div class="cy-loginshashow" style="width:100%">'+
'<div class="cy-lzlogintipmsn" id="cy-lzlogintipmsn" style="display:none;"></div>'+
'<h3 class="cy-loginContent-title"></h3>'+
'<ul class="cy-logform">'+
'   <form action="https://e.liuzhou.gov.cn/visit/loginData/doLogin" method="POST" id="EHAOTONGLOGINFORM">'+
'   	 <input type="hidden" id="EHAOTONGSERVICEURL" name="serviceURL" value="">'+
'   	 <input type="hidden" id="EHAOTONGSERVICESOURCE" name="source" value="">'+
'      <li>'+
'        <select id="EHAOTONGUSERTYPE" name="userType" class="cy-selecttxt">'+
'           <option value="0">个人用户</option>' +
'			<option value="1">企业/单位用户</option>'+
'        </select>'+
'      </li>'+
'      <li name="zh">'+
'      	<input type="text" class="cy-inputxt" id="EHAOTONGUSERNAME" name="userName" value="" tip="请输入您的账号"/>'+
'      </li>'+
'      <li name="mm">'+
'      	<input type="password" class="cy-inputxt" id="EHAOTONGPASSWORD" name="password" value="" />'+
'      	<input type="text" class="cy-inputxt cy-paswInp" value="请输入您的密码" tip="请输入您的密码"/>'+
'      </li>'+
'      <li name="yzm">'+
'      	<input type="text" id="EHAOTONGRANDOM" class="cy-inputxt cy-yzInp" tip="请输入您的验证码"/>'+
'      	<img src="https://e.liuzhou.gov.cn/visit/codevalidateData/createRandomCode" alt="验证码" class="cy-yzImg" id="EHAOTONGRANDOMimg" onclick="reloadEHAOTONGRandomCodeImg(\'EHAOTONGRANDOMimg\')">'+
'      </li>'+
'      <li style="height:38px;margin-bottom:8px;">'+
'      	<button type="button" class="cy-button38 cy-lzloginsubmit" id="lzloginsubmit" onclick="EHAOTONGcheckIsAllowToLogin()">登录</button>'+
'      </li>'+
'      <li class="cy-logingo" style="margin-bottom:0px;">'+
'      	<a id="EHAOTONGREGLINK" href="https://e.liuzhou.gov.cn/visit/registPage/toNewPReg" target="_blank" style="float:left;">没有账号？马上注册</a>'+
'      	<a href="https://e.liuzhou.gov.cn/visit/findPwdPage/toFindPwdUser" target="_blank" style="float:right;">忘记密码</a>'+
'      </li>'+
'   </form>'+
'</ul>'+
'</div>'+
'</div>'+
'</div>'+
'<div class="cy-lzloginBg" id="EHAOTONGLOGINDiVBg"></div>';


function initUI() {

    
     
	//输入框显示默认文字
	$(".cy-inputxt").each(function(){
		$(this).val($(this).attr("tip"));
		$(this).focus(function(){
			if($(this).val()==$(this).attr("tip"))
			{
				$(this).val("").css({color:"#000"});
			}
		})
		$(this).blur(function(){
			if($(this).val()=="")
			{
				$(this).val($(this).attr("tip")).css({color:"#666"});
			}
		})
	});
	$(".cy-paswInp").siblings(".cy-inputxt").hide();
	$(".cy-paswInp").focus(function(){
		$(this).hide();
		$(this).siblings(".cy-inputxt").show();
		$(this).siblings(".cy-inputxt").focus().css({color:"#000"});
	});
	$(".cy-paswInp").siblings(".cy-inputxt").blur(function(){
		if($(this).val()=="")
		{
			$(this).hide();
			$(".cy-paswInp").show();
		}
	});
}
//hecy代码，xujj封装
function EHAOTONGTipssHow(liName, tips) {
	
	$(".cy-logform").find("li .cy-logintip").remove();
	
	$(".cy-logform").find("li[name="+liName+"]")
					.append('<div class="cy-logintip"><span class="dbtriglB"><i class="triglB"></i><em class="triglB"></em></span><p class="cy-logintip-div"><span class="cy-logintip-icon"></span><span class="cy-logintip-content">' + 
							tips + 
							'</span><a href="javascript:void(0)" class="cy-logintip-close">[关闭]</a></p></div>');
							
	$(".cy-logintip").css({
		height:$(".cy-logintip-div").outerHeight(),
		top:-($(".cy-logintip-div").height()+20),
		marginLeft:-$(".cy-logintip-div").width()/2
	});
	
	$(".cy-logintip-close").click(function(){
		$(this).parents(".cy-logintip").hide();
		$(this).parents(".cy-logintip").remove();
	});
	
}

//hecy代码结束



//打开登录div
function showEHAOTONGLogin() {
	if(EHAOTONGObj.render) {
		
		//填入html
		$("#"+EHAOTONGObj.render).html(divHtml);
		//初始化hcy的js
		initUI();
		
		if(EHAOTONGObj.source != undefined) {
			$("#EHAOTONGSERVICESOURCE").val(EHAOTONGObj.source);
			$("#EHAOTONGREGLINK").attr("href", "https://e.liuzhou.gov.cn/visit/registPage/toNewPReg?source="+EHAOTONGObj.source)
		}
		
		// if(EHAOTONGObj.loginType != undefined) {
		// 	// 是否需要隐藏企业用户,econet和enet不隐藏
		// 	if (EHAOTONGObj.source != "econet" && EHAOTONGObj.source != "enet") {
		// 		$("#EHAOTONGUSERTYPE").find("option[value="+EHAOTONGObj.loginType+"]").siblings().remove();
		// 	}
		// }
		
		//登录后要前往的地址
		if(EHAOTONGObj.toUrl) {
			$("#EHAOTONGSERVICEURL").val(EHAOTONGObj.toUrl);
		} else {
			$("#EHAOTONGSERVICEURL").val(window.location.href);
		}
		
		if(EHAOTONGObj.submitTarget) {
			$("#EHAOTONGLOGINFORM").attr("target", EHAOTONGObj.submitTarget);
		}
		
		//登录框是否能关闭
		var closeable = EHAOTONGObj.closeable
		if(closeable == false) {
			$("#EHAOTONGCloseBtn").hide();
		}
		
		//背景样式
		//if(EHAOTONGObj.backgroundStyle == 'mini') {
			$("#EHAOTONGLOGINDiV").addClass("cy-minilzlogin");
		//}
		
		//显示登陆框
		$("#"+EHAOTONGObj.render).show();
		$("#EHAOTONGLOGINDiV").show();
		
		//是否关闭遮罩
		var shade = EHAOTONGObj.shade
		if(shade == false) {
			$("#EHAOTONGLOGINDiVBg").css({opacity:0});
		}
		
		//是否显示提示文字
		if(EHAOTONGObj.showTipMsn)
		{
			$("#cy-lzlogintipmsn").show();
			$("#cy-lzlogintipmsn").html(EHAOTONGObj.showTipMsn);
		}
		
		//固定高度
	    //$(".cy-loginContent").parent().height($(".cy-loginContent").height());
		
	}
}

//关闭登录div
function closeEHAOTONGLogin() {
	
	//隐藏登录框
	$("#"+EHAOTONGObj.render).hide();
	$("#EHAOTONGLOGINDiV").hide();
	$("#EHAOTONGLOGINDiVBg").hide();
	
	/*数据清理*/
	$("#EHAOTONGUSERNAME").val("");
	$("#EHAOTONGPASSWORD").val("");
	$("#EHAOTONGRANDOM").val("");
	reloadEHAOTONGRandomCodeImg("EHAOTONGRANDOMimg");
	
	
	//html删掉
	$("#"+EHAOTONGObj.render).html("");
	
}

//是否已经登录
function EHAOTONGcheckIsLogin() {

	$.ajax({
		type : "post",
		url : "https://e.liuzhou.gov.cn/cas/checkLogin?dataType=jsonp",
		dataType : "jsonp",
		jsonpCallback : "flightHandler",
		success : function(result) {
			
			if(result.state == 'true' && EHAOTONGObj.passLoginFunc) {
				eval("" + EHAOTONGObj.passLoginFunc + "()")
			}
			if(result.state == 'false' && EHAOTONGObj.failLoginFunc) {
				eval("" + EHAOTONGObj.failLoginFunc + "()")
			}
		}
	});
}

//是否允许登录
function EHAOTONGcheckIsAllowToLogin() {
	
	var EHAOTONGUSERTYPE = $("#EHAOTONGUSERTYPE").find("option:selected").val();
	var EHAOTONGUSERNAME = $.trim($("#EHAOTONGUSERNAME").val());
	var EHAOTONGPASSWORD = $.trim($("#EHAOTONGPASSWORD").val());
	var EHAOTONGRANDOM = $.trim($("#EHAOTONGRANDOM").val());
	
	if(EHAOTONGUSERNAME == $("#EHAOTONGUSERNAME").attr("tip")) {
		EHAOTONGTipssHow("zh", "未填写账号");
		return ;
	}
	if(EHAOTONGPASSWORD.length == 0) {
		EHAOTONGTipssHow("mm", "未填写密码");
		return ;
	}
	if(EHAOTONGRANDOM == $("#EHAOTONGRANDOM").attr("tip")) {
		EHAOTONGTipssHow("yzm", "未输入验证码");
		return ;
	}
	
	//验证码验证
	$.ajax({
		url:"https://e.liuzhou.gov.cn/visit/codevalidateData/checkRandomCode",
		type:"POST",
		dataType:"jsonp",
		jsonp: "callback",
		data:{
			"randomCode":$("#EHAOTONGRANDOM").val(),
			"dataType":"jsonp"
		},
		success:function(data) {
			
			//验证码正确后就提交
			if(data.state == 'true') {
				$.ajax({
					type : "post",
					url : "https://e.liuzhou.gov.cn/visit/loginData/doAjaxLoginCheck",
					dataType : "jsonp",
					jsonp: "callback",
					data:{
						"userType":EHAOTONGUSERTYPE,
						"userName":EHAOTONGUSERNAME,
						"password":EHAOTONGPASSWORD
					},
					success : function(result) {
						
						if(result.result == 'true') {
							
							if(EHAOTONGObj.submitType && EHAOTONGObj.submitType == 'norefresh') {
								//不刷新提交
								norefreshEHAOTONGfunc();
							} else {
								$("#EHAOTONGLOGINFORM").submit();
							}
						} 
						
						if(result.result == 'false') {
							EHAOTONGTipssHow("zh", result.msg);
						}
					}
				});
				
			} else {
				
				EHAOTONGTipssHow("yzm", "验证码错误");
			}
		}
		
	});
}

//处理无刷新提交
function norefreshEHAOTONGfunc() {
	$("#"+EHAOTONGObj.render).append("<iframe style='display:none;' name='EHAOTONGIFRAME'></iframe>");
	$("#EHAOTONGLOGINFORM").attr("target", "EHAOTONGIFRAME")
	$("#EHAOTONGLOGINFORM").submit();
	closeEHAOTONGLogin();
	eval("" + EHAOTONGObj.passLoginFunc + "()");
}

//验证码刷新
function reloadEHAOTONGRandomCodeImg(obj) {
	$("#"+obj).attr("src", 'https://e.liuzhou.gov.cn/visit/codevalidateData/createRandomCode?r='+Math.random());
}

//退出
function EHAOTONGLogout() {
	alert("https://e.liuzhou.gov.cn/cas/logout?service=");
	window.location.href = 'https://e.liuzhou.gov.cn/cas/logout?service=' + window.location.href;
}

