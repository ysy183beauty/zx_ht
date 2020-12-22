/**
 * 
 */
//验证用户名
var login ="";
function LoginNameVerification(){ 
	var text = $("#registername").val().replace(/\s+/g,"");;
   if(text==""||text==null){
	   login = text;
	   $("#nameerror").html("用户名不能空");
	   $("#nameerror").css("display","block"); 
	   return;
	 }
   if(login==text){
	   return;
   }
   if(login!=text){
	   login = text;
	   param = {"LOGIN":text};
	   $.ajax({
			url : "/credit/User_Center/LoginNameVerification",
			type : "POST",
			data: param,
			success : function(data) {
				if(data.body==true){
					 $("#nameerror").css("display","none");
				}else{
					 $("#nameerror").html("用户名已被注册");
					 $("#nameerror").css("display","block");
				}
			},
			timeout : 6000
		});
   }
   
} 
//验证手机号码
var phone="";
function PhoneNumVerification(){
	var text = $("#mobilenum").val().replace(/\s+/g,"");;
	   if(text==""||text==null){
		   phone = text;
		   $("#mobilenum").html("手机号码不能空");
			$("#mobilenum").css("display","block");	   
		   return false;
		 }
	   if(phone==text){
		   return true;
	   }
	   if(phone!=text){
		   phone = text;
		   param = {"MOBILENUM":text};
		   $.ajax({
				url : "/credit/User_Center/PhoneNumVerification",
				type : "POST",
				data: param,
				success : function(data) {
					if(data.body==true){
						 $("#phoneerror").css("display","none");
						 return true;
					}else{
						 $("#phoneerror").html("该号码已被注册");
						 $("#phoneerror").css("display","block");
						 return false;
					}
				},
				timeout : 6000
			});
	   }
}

/**
 * 倒计时
 */
	 var countdown=60; 
	 function settime(val) { 
	 if (countdown == 0) { 
	 val.removeAttribute("disabled");    
	 val.value="发送验证码"; 
	 countdown = 60; 
	 return;
	 } else { 
	 val.setAttribute("disabled", true); 
	 val.value="重新发送(" + countdown + ")"; 
	 countdown--; 
	 } 
	 setTimeout(function() { 
	 settime(val) 
	 },1000) 
	 } 
	
	 //注册发送手机验证码
	 function sendmsg(val){


			 param = {"MOBILENUM":$("#mobilenum").val(),
					  "BUSINESSTYPE":"03",
					  "SOURCE":"资源管理中心"
					 };
			   $.ajax({
					url : "/credit/User_Center/SendMsg",
					type : "POST",
					data: param,
					success : function(data) {
						if(data.head.rtnCode=="000000"){
							$("#sendmsgcode").parent().find('label').remove();
							$("#showmobile").append("<label class='red block ml170 fl'>"+data.body+"</label>");
						 settime(val);
						}
					},
					timeout : 6000
				});
	
		 
		 
	 }
	 
	 //找回密码发送手机验证码
	 function sendmsg2(val){ 
			 param = {"MOBILENUM":$("#phonenum").val(),
					  "BUSINESSTYPE":"04",
					  "SOURCE":"资源管理中心"
					 };
			   $.ajax({
					url : "/credit/User_Center/SendMsg",
					type : "POST",
					data: param,
					success : function(data) {
						if(data.head.rtnCode=="000000"){
							$("#msgfindpw").parent().find('em').remove();
							$("#msgfindpw").append("<em class='red block ml170 fl mt10'>"+data.body+"</em>");
						 settime(val);
						}
					},
					timeout : 6000
				});
	
		 
		 
	 }
	 
	 //市民卡实名验证发送手机验证码
	 function sendmsg3(val){		
			 param = {"MOBILENUM":$("#phonenum").val(),
					  "BUSINESSTYPE":"06",
					  "SOURCE":"资源管理中心"
					 };
			   $.ajax({
					url : "/credit/User_Center/SendMsg",
					type : "POST",
					data: param,
					success : function(data) {
						if(data.head.rtnCode=="000000"){
							$("#sendmsgcode").parent().find('em').remove();
							$("#sendmsgcode").append("<em class='red block ml170 fl mt10'>"+data.body+"</em>");
						 settime(val);
						}
					},
					timeout : 6000
				});			 
			
	 }
	 

	 //找回密码验证手机短信
    function msgfindpwsumbit(){
    	if($("#msgcode").val()==""){
    		 $("#showmsgerror").parent().find("em").remove();
			 $("#showmsgerror").append('<em class="red block ml170 fl mt10" >请填写验证码</em>');
    	}
		param={"VCODE":$("#msgcode").val(),
      		 "MOBILENUM":$("#phonenum").val() ,
      		 "BUSINESSTYPE":"04"
      	 },
        $.ajax({
			url : "/credit/User_Center/MsgcodeVerification",
			type : "POST",
			data: param,
			success : function(data) {
				if(data.responseData==true){
					 $(".step2").css("display","none");
					 $("#step2").removeClass("current");                                                                         
					 $(".step3").css("display","block");
					 $("#step3").addClass("current");
					
				}else{ 				
					 $("#showmsgerror").parent().find("em").remove();
					 $("#showmsgerror").append('<em class="red block ml170 fl mt10" >验证码错误</em>');   					
				}
			},
			timeout : 6000
		});
    }

	 //身份证实名验证发送手机验证码
	 function sendmsg4(val){
			$("#sendmsgcode").parent().find('label').remove();
			if($("#phonenum").val()==''){
				$("#phonenum").append('<label style="color:#b94a48 ;margin-left:48px;line-height:20px;">请输入手机号码</label>');
			}else{
			 param = {"MOBILENUM":$("#phonenum").val(),
					  "BUSINESSTYPE":"01",
					  "SOURCE":"资源管理中心"
					 };
			   $.ajax({
					url : "/credit/User_Center/SendMsg",
					type : "POST",
					data: param,
					success : function(data) {
						if(data.head.rtnCode=="000000"){
							$("#sendmsgcode").parent().find('em').remove();
							$("#sendmsgcode").append("<em class='red block ml170 fl mt10'>"+data.body+"</em>");
						 settime(val);
						}
					},
					timeout : 6000
				});
			}
	 }
	 
	 //修改绑定手机发送手机号码
	 function sendmsg5(val){
			$("#sendmsgcode2").parent().find('label').remove();
			if($("#phonenum2").val()==''){
				$("#phonenum2").parent().append('<label style="color:#b94a48 ;margin-left:100px;line-height:20px;">请输入手机号码</label>');
			}else{
			 param = {"MOBILENUM":$("#phonenum2").val(),
					  "BUSINESSTYPE":"01",
					  "SOURCE":"资源管理中心"
					 };
			   $.ajax({
					url : "/credit/User_Center/SendMsg",
					type : "POST",
					data: param,
					success : function(data) {
						if(data.head.rtnCode=="000000"){
							$("#sendmsgcode2").parent().find('em').remove();
							$("#sendmsgcode2").parent().append("<em class='red block ml100 fl mt10'>"+data.body+"</em>");
						 settime(val);
						}
					},
					timeout : 6000
				});
			}
	 }
	
	 //身份证实名认证提交
	 function submitsfz(){
		 param = {"relname":$("#relname").val(),
				  "sfznum":$("#sfznum").val(),
				  "FPICTURE":$("#Fphoto").val(),
				  "BPICTURE":$("#Bphoto").val(),
				  "phonenum":$("#phonenum").val(),
				  "dxcode":$("#dxcode").val(),
				  "accessTicket":accessTicket
				 },
		 $.ajax({
						url : "/credit/User_RNAuth/SFZHMauthenticationInfo",
						type : "POST",
						data: param,
						success : function(data) {
							if(data!=null){
							if(data.head.rtnCode=="000000"){
								 $(".step2").css("display","none");
								 $(".step2").removeClass("current");                                                                         
								 $(".step4").css("display","block");
								 $(".step4").addClass("current");
								 $("#returnindex").trigger("click");
							}else{
								 $("#sfzerror").parent().css("display","block");
								  $("#sfzerror").html(data.head.rtnMsg);
							}}else{
								alert("请求失败");
							}
						},
						error : function(response) {
							alert("请求失败");
						},
						timeout : 6000
					});
	 }
	

  


//用户登录
function userlogin(){
	 var LOGIN = $("#username").val();
	 var PASSWORD = $("#loginpwd").val();
	 if(LOGIN==""||LOGIN==null){
		 $("#lnerror").html("用户名不能为空");
		 return ;
	 }
	 else{
		 $("#lnerror").html("");
		 }
	  if(PASSWORD==""||PASSWORD==null){
		  $("#lperror").html("密码不能为空");
		 return ;
	 }
	 param={
	    "LOGIN":LOGIN,
	    "PASSWORD":PASSWORD
	 },
	 $.ajax({
			url : "/credit/User_Center/UserLogin",
			type : "POST",
			data: param,
			success : function(data) {


				if(data.result=="000000"){
					window.location.reload();

				}else{
					  $("#lperror").html("登录失败");
				}
			},

			timeout : 6000
		});
}