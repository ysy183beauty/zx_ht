/**
 * 
 */
$(function(){
	  $("#repassword").bind( "keyup",this.pwStrength);
      $("#citipassword").bind( "keyup",this.pwStrength1);
      $("#backoff").bind("click",this.back);
	  $("#sedmcd").bind("click",this.cardmsgcode);
      $("#closeRegist").click(function(){
    	$("#loginregist").val('');
      	$("#repassword").val('');
      	$("#checkpassword").val('');
      	$("#mobilenum").val('');
      	$("#msgcode").val('');
      	$("#cardnum").val('');
      	$("#safecode").val('');
      	$("#email").val('');
      	secs = 0;
      	$("#messageerrortext").attr("style","display:none;");
      	$("#register").parent().find('label').remove();
      	$("#cardregstep1").parent().find('label').remove();	
      	$("#gototab1").click();
    });
	 
	 //输入框的提示信息添加开始
		//市民卡号（添加提示信息与删除提示信息）
		$("#cardnum").focus(function(){
		 	$("#showmsgkam").find('label').remove();
			if($("#cardnum").val()==''){
			   
				$("#showmsgkam").append('<label style="color:#b94a48;margin-left:48px;">请输入市民卡卡面号</label>');
			}
		});
		//$("#cardnum").blur(function(){
		//	if($("#cardnum").val()==''){
		//	    $("#showmsgkam").parent().find('label').remove();
		//	}
		//});
		//验证码
		$("#cityzm").focus(function(){
			$("#cityzmdiv").find('label').remove();
		});

		//市民卡用户密码验证信息

		
		 //输入框的提示信息添加开始
		//密码框的得到焦点与失去焦点事件（添加提示信息与删除提示信息）
		$("#citipassword").focus(function(){
			if($("#citipassword").val()==''){
			    $("#smsgpasscard").find('label').remove();
				$("#smsgpasscard").append('<label>密码为6-20个字符，只能包含大小写字母、数字和符号</label>');
			}
		});
		$("#citipassword").blur(function(){
			if($("#citipassword").val()==''){
			    $("#smsgpasscard").parent().find('label').remove();
			    $("#pwstr1").css("display","none");
			}
		});
		//用户名的得到焦点与失去焦点事件（添加提示信息与删除提示信息）
		$("#usnm").focus(function(){
			if($("#usnm").val()==''){
			    $("#smsgcard").parent().find('label').remove();
				$("#smsgcard").append('<label>用户名是由6-20个英文、数字、“-”或"_"组成，请以英文字母开头</label>');
			}
		});
		$("#usnm").blur(function(){
			if($("#usnm").val()==''){
			    $("#smsgcard").parent().find('label').remove();
			}
		});
		//重复密码的得到焦点与失去焦点事件（添加提示信息与删除提示信息）
		$("#recitipassword").focus(function(){
			if($("#recitipassword").val()==''){
			    $("#smsgrepascard").find('label').remove();
				$("#smsgrepascard").append('<label>请再次输入您的密码</label>');
			}
		});
		$("#recitipassword").blur(function(){
			if($("#recitipassword").val()==''){
			    $("#smsgrepascard").parent().find('label').remove();
			}
		});
		
		
		
		
		//市民卡用户密码验证信息END


    //验证码
    $("#safecode").blur(function(){
        var value =$("#safecode").val().toUpperCase();
        $("#showcode").parent().find('label').remove();
        $("#yz").css("background","");
        if(value==''){
            $("#showcode").append('<label>请输入验证码</label>');
        }else if(value==code){
            $("#yz").css("background","url(r/cms/www/red/img/newEdition/right.png) no-repeat left 0px top 10px");
        }else{
            $("#yz").css("background","url(r/cms/www/red/img/newEdition/right.png) no-repeat left 0px top -17px");
        }
    });
    //输入框的提示信息添加开始
		//密码框的得到焦点与失去焦点事件（添加提示信息与删除提示信息）
		$("#repassword").focus(function(){
			if($("#repassword").val()==''){
			    $("#showpasswd").parent().find('label').remove();
				$("#showpasswd").append('<label>密码为6-20个字符，只能包含大小写字母、数字和符号(除空格)</label>');
			}
		});
		$("#repassword").blur(function(){
			if($("#repassword").val()==''){
			    $("#showpasswd").parent().find('label').remove();
			    $("#pwstr").css("display","none");
			}
		});
		//用户名的得到焦点与失去焦点事件（添加提示信息与删除提示信息）
		$("#loginregist").focus(function(){
			if($("#loginregist").val()==''){
			    $("#showrule").parent().find('label').remove();
				$("#showrule").append(" <label>用户名是由6-20个英文、数字、“-”或“_”组成，请以字母开头</label>");
			}
		});
		$("#loginregist").blur(function(){
			if($("#loginregist").val()==''){
			    $("#showrule").parent().find('label').remove();
			}
		});
		//重复密码的得到焦点与失去焦点事件（添加提示信息与删除提示信息）
		$("#checkpassword").focus(function(){
			if($("#checkpassword").val()==''){
			    $("#repwd").parent().find('label').remove();
				$("#repwd").append("<label>请再次输入您的密码</label>");
			}
		});
		$("#checkpassword").blur(function(){
			if($("#checkpassword").val()==''){
			    $("#repwd").parent().find('label').remove();
			}
		});
		//手机号码的得到焦点与失去焦点事件（添加提示信息与删除提示信息）
		$("#mobilenum").focus(function(){
			$("#showmobile").parent().find('label').remove();
			if($("#mobilenum").val()==''){
				$("#showmobile").append('<label>请输入手机号码</label>');
			}
		});
		$("#mobilenum").blur(function(){
			if($("#mobilenum").val()==''){
			    $("#showmobile").parent().find('label').remove();
			}
		});
		$("#msgcode").blur(function(){
			if($("#msgcode").val()==''){
			    $("#showmsgcode").parent().find('label').remove();
			}
		});
//输入框的提示信息添加结束
    	 //注册协议不勾选会将按钮失效，反之
		  $("#xieyi").bind("click", function () {
                if($("#xieyi:checked").size()>0){
	 	 			$("#btnsub").attr("disabled",false);
	 	 			$("#showxieyi").find('label').remove();
	 	 	 	}else{
	 	 			$("#btnsub").attr("disabled",true);
	 	 			//$("#showxieyi").append('<label style="color:#b94a48">请阅读《用户服务协议》并勾选"口"确认同意</label>').css("margin-left","25px");
	 	 	 }
	      });

		  //注册协议不勾选会将按钮失效，反之
		  $("#xieyi1").bind("click", function () {
                if($("#xieyi1:checked").size()>0){
	 	 			$("#subRegiste2").attr("disabled",false);
	 	 			$("#showxieyi1").find('label').remove();
	 	 	 	}
	      });
		  

		
		
        

    	
	
	//
$("#registerform").validate({
		rules: {  
			//注册名校验规则 
			loginregist: {   
				required: true,
			    chrnum:true,   
			    minlength: 6,
				maxlength: 20,
				openchar: true,
				citizenCard: true,
			    remote:{   
                     url: "/credit/User_Center/LoginNameVerification", //后台处理程序    
                     data:  {       //要传递的数据   
                         "LOGIN": function(){
                         		return $("#loginregist").val();
                         	}
                         }   
                  	}, 
			      },  
			//密码校验规则   
			repassword: {   
				required: true,
				//chrnum: true,
			    minlength: 6,
				maxlength: 20,
			      },   
			//重复密码   
			checkpassword: { 
				required: true,  
				equalTo: "#repassword",
			      },
			   //邮箱   
			 email:{
                      required:true,
                      email:true,
                      remote:{   
                          url: "/credit/User_Center/LoginNameVerification", //后台处理程序    
                          data:  {       //要传递的数据   
                              "LOGIN": function(){
                              		return $("#email").val();
                              	}
                              }   
                       	}, 
                  },
             //普通验证码
                  safecode: {
                      required: true
                  },
			//手机验证码
			      msgcode: {
				   required: true, 
				   minlength: 6,
				   maxlength: 6,
				   number:true,
				   remote:{   
	                     url: "/credit/User_Center/MsgcodeVerification",  
	                     data:  {  
	                     		 "VCODE": function(){
	                         		return $("#msgcode").val();
	                         	 },
	                        	 "MOBILENUM": function(){
	                         		return $("#mobilenum").val();
	                         	 },
	                        	 "BUSINESSTYPE": function(){
	                         		return '03';
	                         	 },
	                         	 
	                         }   
	                  	}, 
		        	},
   			//手机号码校验
		      mobilenum: {
			    required: true, 
				isMobile: true,
				remote:{   
                  	 url: "/credit/User_Center/PhoneNumVerification", //后台处理程序    
                   	data:  {       //要传递的数据   
                      	 "MOBILENUM": function(){
                       		return $("#mobilenum").val();
                       	 }
                       }   
               	},
			      },
			},       
 
		messages: {   
		    //注册名校验信息 
			loginregist: {
				required : "请不要忘记输入用户名",    
				chrnum: "您输入的用户名包含非法字符",  	
				minlength: "用户名只能在6-20个字符之间",
				maxlength: "用户名只能在6-20个字符之间", 
				openchar:"您输入的用户名要以字母开头", 
				citizenCard:"您输入的用户名已被占用",
				remote: "您输入的用户名已被占用",
			      }, 
			//密码校验信息   
			repassword: {
				required: "请不要忘记输入密码",
				minlength: "密码只能在6-20个字符之间",
				maxlength: "密码只能在6-20个字符之间",
				//chrnum:"您输入的密码包含非法字符",
			      },   
			//重复密码   
			checkpassword: {   
				required: "您输入的两次密码不一致",
				equalTo: "您输入的两次密码不一致",
			      }, 
		    email:{
                      required:"请不要忘记输入邮箱",
                      email:"邮箱格式不正确"
                  },
            safecode: {
                      required: "请不要忘记输入验证码"
                  },
		//手机号码校验信息   
            mobilenum: {
				required: "请不要忘记输入手机号码",       
			    isMobile: "您输入的手机号码有误",  
			    remote: "您输入的手机号码已被占用",
			      },  
			//手机验证码 
			msgcode: {
				    required: "请不要忘记输入验证码",
				    number: "您输入的验证码有误",
				    minlength: "您输入的验证码有误",
					maxlength: "您输入的验证码有误",
					remote:"您输入的验证码有误或失效",
				      },
		 }, 
	    focusInvalid: false,   
	    onkeyup: false,   			       
	    errorPlacement: function(error, element) {
	    	if(element[0].id == "mobilenum"){
	    		$("#sendmsgcode").attr("disabled",true);
	    	}
	    	element.parent().find("label[class='right']").remove();
	    	element.parent().find("label").remove();
	    
		    error.insertAfter(element.parent());
	    }, 
		submitHandler : function(form) {
		    saveRegistBind();
				},
		success: function(label) {
			if(label.prev().find("input")[0].id == "mobilenum"){
	      		if(countdown<1 || countdown==60){
	      			$("#sendmsgcode").attr("disabled",false);
	      		}
			}	
            label.insertAfter(label.parent().find('span').get(1)).attr('class',"right");
            
            }   
		
				
		});

//找回密码验证----填写登录名

$("#findpwdone").validate({
	rules: {  
		loginname: {   
			required: true,
		    minlength: 6,
			maxlength: 20,	
		      },  
		
		safecode: {   
			 required: true,
             codevalidate: true
		},  
	},       

	messages: {   
		loginname: {
			required : "请不要忘记输入用户名",    
			minlength: "用户名只能在6-20个字符之间",
			maxlength: "用户名只能在6-20个字符之间", 
		      }, 
		safecode: {
			required: "请不要忘记输入验证码",
			codevalidate:"验证码输入错误"		
		      },
	 }, 
    focusInvalid: false,   
    onkeyup: false,   
		       
    errorPlacement: function(error, element) {
    	element.parent().find("label[class='right']").remove();
    	element.parent().find("label").remove();  
	    error.insertAfter(element.parent());
    }, 
    
	submitHandler : function() {
		param={"LOGIN":$("#loginname").val()},
            $.ajax({
    			url : "/credit/User_Center/UserNameverification",
    			type : "POST",
    			data: param,
    			success : function(data) {
    				if(data.head.rtnCode=="000000"){
    					$(".step1").css("display","none");
    					 $("#step1").removeClass("current");                                                                         
    					 $(".step2").css("display","block");
    					 $("#step2").addClass("current");
    					 $("#phonenum").attr("value",data.body.mobilenum);
    				}else{ 				
    					 $("#loginfindpw").parent().find("label").remove();
    					 $("#loginfindpw").append('<label class="error" for="loginname">用户名不存在</label>');   					
    				}
    			},
    			timeout : 6000
    		}); 
	},
	
	});


//找回密码验证----重置密码

$("#resetpw").validate({
	rules: {  
		//密码校验规则   
		repassword: {   
			required: true,
			chrnum: true,
		    minlength: 6,
			maxlength: 20,
		      },   
		//重复密码   
		checkpassword: { 
			required: true,  
			equalTo: "#repassword",
		      },
	},       

	messages: {   
		//密码校验信息   
		repassword: {
			required: "请不要忘记输入密码",
			minlength: "密码只能在6-20个字符之间",
			maxlength: "密码只能在6-20个字符之间",
			chrnum:"您输入的密码包含非法字符",
		      },   
		//重复密码   
		checkpassword: {   
			required: "您输入的两次密码不一致",
			equalTo: "您输入的两次密码不一致",
		      }, 
	 }, 
    focusInvalid: false,   
    onkeyup: false,   
		       
    errorPlacement: function(error, element) {
    	
    	element.parent().find("label[class='right']").remove();
    	element.parent().find("label").remove();  
	    error.insertAfter(element.parent());
    }, 
    
	submitHandler : function() {
		param={"LOGIN":$("#loginname").val(),
				"PASSWORD":$("#repassword").val(),
				"CODE":$("#msgcode").val(),
				"FINDTYPE":"mobile",
				"PWDSTRENGTH":"1"
				},
            $.ajax({
    			url : "/credit/User_Center/Resetpassword",
    			type : "POST",
    			data: param,
    			success : function(data) {
    				if(data.head.rtnCode=="000000"){
    					 $(".step3").css("display","none");
    					 $("#step3").removeClass("current");                                                                         
    					 $(".step4").css("display","block");
    					 $("#step4").addClass("current");   
    					 $("#returnindex").click();
    				}else{ 				
    					 $("#showreseterror").parent().find("label").remove();
    					 $("#showreseterror").append('<label class="error" for="repassword">'+data.head.rtnMsg+'</label>');   					
    				}
    			},
    			timeout : 6000
    		});
//	 

	},
	
	 
      
	});

//***************市民卡验证框架开始

$("#smkvalidate").validate({
		rules: {  
			//市民卡号校验规则 
			ycnum: {   
				required: true,			    
			    minlength: 8,
				maxlength: 8,
				remote:{   
                   	 url:"/credit/User_RNAuth/YctNumverification", //后台处理程序    
                    	data:  {       //要传递的数据   
                       	 "CARDNUM": function(){
                        		return $("#ycnum").val();
                        	 }
                        }   
                	},  
			},  
		},       
 
		messages: {   
		    //市民卡号校验信息 
			ycnum: {
				required: "请不要忘记输入市民卡卡面号",
			    isCitizenCard:"您输入的市民卡卡面号错误",   
			    minlength: "您输入的市民卡卡面号错误",
				maxlength: "您输入的市民卡卡面号错误",
				remote:"您输入的市民卡卡面号已被注册"
			      }, 
		 }, 
	    focusInvalid: false,   
	    onkeyup: false,   
			       
	    errorPlacement: function(error, element) {
	    	element.parent().parent().find("label").remove();
		    error.insertAfter(element.parent());
	    }, 
	    
		submitHandler : function(form) {
			param={"CARDNUM":$("#ycnum").val(),	
				"accessTicket":accessTicket
					},					
			sfzhm = $("#sfz").val();
	            $.ajax({
	    			url : "/credit/User_RNAuth/GetUserDetailByYct",
	    			type : "POST",
	    			data: param,
	    			success : function(data) {
	    				if(data.head.rtnCode=="000000"){
	    					if(data.body.idcardcode==sfzhm){
	    					 $(".step1").css("display","none");
	    					 $("#step1").removeClass("current");                                                                         
	    					 $(".step2").css("display","block");
	    					 $("#step2").addClass("current");
	    					 $("#ycnum2").attr("value",data.body.cardnum);
	    					 $("#ckr").attr("value",data.body.name);
	    					 $("#phonenum").attr("value",mobilenum);
	    					 
	    					}else{
	    						 $("#yctshow").parent().find("label").remove();
		    					 $("#yctshow").parent().append('<label class="error" for="ycnum">身份证和虞城通号不匹配</label>');   					
	    					}
	    				}else{ 				
	    					 $("#yctshow").parent().find("label").remove();
	    					 $("#yctshow").parent().append('<label class="error" for="ycnum">'+data.head.rtnMsg+'</label>');   					
	    				}
	    			},
	    			timeout : 6000
	    		});
		},
		
		 
          
		});
//市民卡验证最后一步
$("#smkvalidate2").validate({
	rules: {  
		//手机验证码
		dxcode: {
		   required: true, 
		   minlength: 6,
		   maxlength: 6,
		   number:true,
		   remote:{   
               url: "/credit/User_Center/MsgcodeVerification",  
               data:  {  
               		 "VCODE": function(){
                   		return $("#dxcode").val();
                   	 },
                  	 "MOBILENUM": function(){
                   		return $("#phonenum").val();
                   	 },
                  	 "BUSINESSTYPE": function(){
                   		return '06';
                   	 },
                   	 
                   }   
            	}, 
      	},
	},       

	messages: {   
	    //市民卡号校验信息 
		dxcode: {
			 required: "请不要忘记输入验证码",
			    number: "您输入的验证码有误",
			    minlength: "您输入的验证码有误",
				maxlength: "您输入的验证码有误",
				remote:"您输入的验证码有误或失效",
		      }, 
	 }, 
    focusInvalid: false,   
    onkeyup: false,   
		       
    errorPlacement: function(error, element) {
    	element.parent().parent().find("label").remove();
	    error.insertAfter(element.parent());
    }, 
    
	submitHandler : function(form) {
		if(level=='02'||level=='03'||level=='0201'){
			alert("您已经实名认证或者实名认证审核中，请不要重复认证！");
		}else{
		param={"CARDNUM":$("#ycnum").val(),	
				"NAME":$("#ckr").val(),
				"IDCARDCODE":$("#sfz").val(),
				"MOBILENUM":mobilenum,
				"CODE":$("#dxcode").val(),
				"accessTicket":accessTicket
				},					
            $.ajax({
    			url : "/credit/User_RNAuth/CitizencardauthenticationInfo",
    			type : "POST",
    			data: param,
    			success : function(data) {
    				if(data.head.rtnCode=="000000"){
    					 $(".step2").css("display","none");
    					 $("#step2").removeClass("current");                                                                         
    					 $(".step4").css("display","block");
    					 $("#step4").addClass("current");	
    					 $("#ycth").html($("#ycnum").val());
    					 $("#returnindex").trigger("click");
    				}else{ 				
    					 $("#submitshow").parent().find("label").remove();
    					 $("#submitshow").parent().append('<label class="error" for="submitshow">'+data.head.rtnMsg+'</label>');   					
    				}
    			},
    			timeout : 6000
    		});
	}
	},
	
	   
	});

//身份证市民认证开始
$("#sfzform").validate({
	rules: { 
		//真实姓名验证
		relname: {
		   required: true, 
		   minlength: 2,
		   maxlength: 6, 
	},  
	sfznum: { 
		   required: true, 
		   isIdCardNo:true ,
		   remote:{
			     url: "/credit/User_RNAuth/SFZHMRealtimeverification",  
	                data:  {  
	                		 "IDCODE": function(){
	                    		return $("#sfznum").val();
	                    	 },
	                    }  
		   }
      	},
    Bphoto:{ 
	   required: true, 
  	},
  	Fphoto:{ 
 	   required: true, 
   	},
  	dxcode: {
 		 required: true, 
 		 minlength: 6,
 		 maxlength: 6,
 		 number:true,
 		 remote:{   
                url: "/credit/User_Center/MsgcodeVerification",  
                data:  {  
                		 "VCODE": function(){
                    		return $("#dxcode").val();
                    	 },
                   	 "MOBILENUM": function(){
                    		return mobilenum;
                    	 },
                   	 "BUSINESSTYPE": function(){
                    		return '01';
                    	 },
                    	 
                    }   
             	}, 
       	},
     safecode: {
	      required: true,
	      codevalidate: true
     },
	},  

	messages: {   
	    //市民卡号校验信息 
		relname: {
			 required: "请不要忘记输入姓名",
			 minlength: "您输入的姓名有误",
		     maxlength: "您输入的姓名有误",
			 remote:"您输入的姓名有误或失效",
		      }, 
		sfznum: {
			 required: "请不要忘记输入身份证号码",
			 isIdCardNo:"请正确填写身份证号码",
			 remote:"该身份证已经实名认证"
				      }, 
     Fphoto:{ 
			required: "请不要忘记身份证照片", 
				     	},
	 Bphoto:{ 
			 required: "请不要忘记身份证照片", 
				      	},		      
	    dxcode: {
			required: "请不要忘记输入验证码",
		    number: "您输入的验证码有误",
			minlength: "您输入的验证码有误",
			maxlength: "您输入的验证码有误",
			remote:"您输入的验证码有误或失效",
					 }, 
		safecode: {
			required: "请不要忘记输入验证码",
			codevalidate:"验证码输入错误"		
        },
		
	 }, 
    focusInvalid: false,   
    onkeyup: false,   
		       
    errorPlacement: function(error, element) {
    	element.parent().parent().find("label").remove();
	    error.insertAfter(element.parent());
    }, 
    submitHandler : function(form) {
    	$(".step1").css("display","none");
		$("#step1").removeClass("current");                                                                         
		$(".step2").css("display","block");
		$("#step2").addClass("current");
		$("#checkname span").html($("#relname").val());
		$("#checksfznum span").html($("#sfznum").val());
		$("#sfz2").html($("#sfznum").val());
    },
	   
	});

//企业认证开始
$("#qyrzform").validate({
	rules: { 
		//真实姓名验证
	dwmc: {
		   required: true, 
		   minlength: 2,
		   maxlength:20, 
		   remote:{
			     url: "/credit/Enterprise/entNameCheck",  
	                data:  {  
	                		 "QYMC": function(){
	                    		return $("#dwmc").val();
	                    	 },
	                    }  
		   }
	},  
	dwbh:{
		   required: true, 
		   minlength: 8,
		   maxlength:10, 
		   remote:{
			     url: "/credit/Enterprise/entNumCheck",  
	                data:  {  
	                		 "ZZJGDM": function(){
	                    		return $("#dwbh").val();
	                    	 },
	                    }  
		   }
	},  
	gszch :{
		 required: true, 
		 remote:{
		     url: "/credit/Enterprise/businessNumCheck",  
                data:  {  
                		 "YYZZH": function(){
                    		return $("#gszch").val();
                    	 },
                    }  
	   }
	},
	startDate: { 
		   required: true, 
      	},
      endDate:{ 
	   required: true, 
  	},
  	dwhm:{ 
 	   required: true,
 	  isPhone:true,
   	},
   	dwmail: {
 		 required: true, 
 	     email:true,
             	}, 
       	
      frmc: {
	      required: true,
	      minlength: 2,
		  maxlength:20,
		  remote:{
			     url: "/credit/Enterprise/PeoNameCheck",  
	                data:  {  
	                		 "FRDBXM": function(){
	                    	return $("#frmc").val();
	                   },
	      }  
		   }
	                  },
	  dwsfz: {
	      required: true,
	      minlength: 2,
	      maxlength:20,
		  remote:{
			     url: "/credit/Enterprise/PeoNumCheck",  
	                data:  {  
	                		 "FRDBZJH": function(){
	                    	return $("#dwsfz").val();
	                   },
	      }  
		   }
	          },   
	img1:{ 
		   required: true, 
	  	},
	img2:{ 
			required: true, 
		  	},
	img3:{ 
			required: true, 
			},
	img5:{ 
		required: true, 
			},
	img6:{ 
			required: true, 
			},
	  	
	},  

	messages: {   
	    //市民卡号校验信息 
		dwmc: {
			 required: "请不要忘记输入姓名",
			 minlength: "您输入的姓名有误",
		     maxlength: "您输入的姓名有误",
			 remote:"您输入的企业名称不存在",
		      }, 
	 dwbh: {
			 required: "请不要忘记输入身份证号码",
			 minlength: "您输入的机构代码有误",
		     maxlength: "您输入的机构代码有误",
			 remote:"您输入的组织结构代码不存在"
				      }, 
	 gszch : {
		 required: "请不要忘记输入工商注册号",
		 remote:"您输入的工商注册号不存在",
	 }	,		      
	 startDate:{ 
			required: "请不要忘记身份证照片", 
				     	},
   	 endDate:{ 
			 required: "请不要忘记身份证照片", 
				      	},		      
 	dwhm: {
			required: "请不要忘记输入验证码",
		    number: "您输入的验证码有误",
			minlength: "您输入的验证码有误",
			maxlength: "您输入的验证码有误",
			remote:"您输入的验证码有误或失效",
					 }, 
	 dwmail: {
			required: "请不要忘记输入验证码",
			codevalidate:"验证码输入错误"		
					},
	frmc: {
		required: "请不要忘记输入验证码",
		minlength: "您输入的法人名称长度错误",
		maxlength:"您输入的法人名称长度错误",
		remote:"您输入的法人名称不存在",
				},
	dwsfz	: {
		     required: "请不要忘记输入验证码",
			 minlength: "您输入的法人名称有误",
		     maxlength: "您输入的法人名称有误",	
		     remote:"您输入的身份证号码不存在",
				},
			
		img1:{ 
				 required: "请不要忘记上传图片", 
							  	},
		img2:{ 
				required: "请不要忘记上传图片", 
								  	},
		img3:{ 
				required: "请不要忘记上传图片", 
									},
		img5:{ 
				required: "请不要忘记上传图片", 
									},
		img6:{ 
				required: "请不要忘记上传图片", 
									},
	 }, 
    focusInvalid: false,   
    onkeyup: false,   
		       
    errorPlacement: function(error, element) {
    	element.parent().find("label").remove();
	    error.insertAfter(element);
    }, 
    submitHandler : function(form) {
    	var param={
    	"userName":	loginname,
    	"organizationCode":	$("#dwbh").val(),
    	"entName":	$("#dwmc").val(),
    	"entType":	$("#qytype").val(),
    	"startTime":	$("#startDate").val(),
    	"endTime":	$("#endDate").val(),
    	"phone":	$("#dwhm").val(),
    	"email":	$("#dwmail").val(),
    	"img1":	$("#experImage_input_1").val(),
    	"img2":	$("#experImage_input_2").val(),
    	"img3":	$("#experImage_input_3").val(),
    	"img5":	$("#experImage_input_5").val(),
    	"img6":	$("#experImage_input_6").val(),
    	"corporationName":	$("#frmc").val(),
    	"corporationId":	$("#dwsfz").val(), 	
    	"YYZZH":	$("#gszch").val(), 
    	}
    	$("#returnindex").trigger("click");
    	$.ajax({
			url : "/credit/Enterprise/entApprove",
			type : "POST",
			data: param,
			success : function(data) {
				if(data==true){
					alert("认证提交成功,等待审核");
					$("#returnindex").trigger("click");
				}else{ 				
					alert("认证失败");
				}
			},
			timeout : 6000
		});
    },
	 
	});

//修改绑定手机号
$("#changephoneform").validate({
	rules: {  
		//手机验证码
		dxcode2: {
		   required: true, 
		   minlength: 6,
		   maxlength: 6,
		   number:true,
		   remote:{   
               url: "/credit/User_Center/MsgcodeVerification",  
               data:  {  
               		 "VCODE": function(){
                   		return $("#dxcode2").val();
                   	 },
                  	 "MOBILENUM": function(){
                   		return $("#phonenum2").val();
                   	 },
                  	 "BUSINESSTYPE": function(){
                   		return '01';
                   	 },
                   	 
                   }   
            	}, 
      	},      	
      	phonenum2: {
			    required: true, 
				isMobile: true,
				remote:{   
                  	 url: "/credit/User_Center/PhoneNumVerification", //后台处理程序    
                   	data:  {       //要传递的数据   
                      	 "MOBILENUM": function(){
                       		return $("#phonenum2").val();
                       	 }
                       }   
               	},
	      	},
	},       

	messages: {   
	    //市民卡号校验信息 
		dxcode2: {
			 required: "请不要忘记输入验证码",
			    number: "您输入的验证码有误",
			    minlength: "您输入的验证码有误",
				maxlength: "您输入的验证码有误",
				remote:"您输入的验证码有误或失效",
		      }, 
    	phonenum2 :{
		required: "不要忘记输入手机号", 
		isMobile: "手机号码输入有误",
		remote:"您输入的手机号已被占用" ,
	}  
		  
	 }, 
    focusInvalid: false,   
    onkeyup: false,   
		       
    errorPlacement: function(error, element) {
    	if(element[0].id == "phonenum2"){
    		$("#sendmsgcode2").attr("disabled",true);
    	}
    	element.parent().find("label[class='right']").remove();
    	element.parent().find("label").remove();  
	    error.insertAfter(element.parent());
 
    }, 
    
	submitHandler : function(form) {
		param={"MOBILENUM":$("#phonenum2").val(),	
				"CODE":$("#dxcode2").val(),
				"accessTicket":accessTicket
				},					
            $.ajax({
    			url : "/credit/User_Center/ChangePhone",
    			type : "POST",
    			data: param,
    			success : function(data) {
    				if(data.head.rtnCode=="000000"){
    					alert("修改绑定手机成功");
    				}else{ 				
    					 $("#okphone").parent().find("label").remove();
    					 $("#okphone").parent().append('<label class="error" for="submitshow">'+data.head.rtnMsg+'</label>');   					
    				}
    			},
    			timeout : 6000
    		});
	},
	success: function(label) {
		if(label.prev().find("input")[0].id == "phonenum2"){
      		if(countdown<1 || countdown==60){
      			$("#sendmsgcode2").attr("disabled",false);
      			$("#sendmsgcode2").css("background-color","white");
      		}
		}	
        label.insertAfter(label.parent().find('span').get(1)).attr('class',"right");
        
        }
	
	   
	});

//修改密码
$("#changepwd").validate({
	rules: {  
		//手机验证码
		oldpwd: {
		   required: true, 
		   minlength: 6,
		   maxlength: 20,
      	},      	
      	newpwd: {
			    required: true, 
			    minlength: 6,
				maxlength: 20,
	      	},
	    checknewpwd: {
			    required: true, 
			    equalTo: "#newpwd",
	      	},
	},       

	messages: {   
		oldpwd: {
			 required: "请不要忘记输入密码",			
			    minlength: "您输入的密码有误",
				maxlength: "您输入的密码有误",
		      }, 
       newpwd :{
    	   required: "请不要忘记输入密码",

    	    minlength: "您输入的密码有误",
    		maxlength: "您输入的密码有误",
	}  ,
	checknewpwd :{
		required: "请不要忘记输入密码",  
		equalTo: "您输入的密码不一致",
		  	} 
		  
	 }, 
    focusInvalid: false,   
    onkeyup: false,   
		       
    errorPlacement: function(error, element) {
    	alert("error");
    	element.parent().parent().find("label").remove();
	    error.insertAfter(element.parent());
    },   
    
    submitHandler : function(form) {    
		param={"OLDPWD":$("#oldpwd").val(),	
				"NEWPWD":$("#newpwd").val(),
				"accessTicket":accessTicket,
				"PWDSTRENGTH":"01"
				},
            $.ajax({
    			url : "/credit/User_RNAuth/Changepassword",
    			type : "POST",
    			data: param,
    			success : function(data) {
    				if(data.head.rtnCode=="000000"){
    					alert("修改成功请重新登陆");
    					$.ajax({
    						url : "/credit/User_Center/loginout",
    						type : "POST",
    						success : function(data) {
    							window.location.href="/";
    							},
    						timeout : 6000
    					});
    				}else{ 				
    					 $("#oldpwd").parent().find("label").remove();
    					 $("#oldpwd").parent().append('<label class="error" for="submitshow" style="margin-bottom:-8px;">'+data.head.rtnMsg+'</label>');   					
    				}
    			},
    			timeout : 6000
    		});
	},
	
	   
	});

})
  


  function  saveRegistBind (){  
		$("#btnsub").attr("disabled",true); 
		var ln=$('#loginregist').val();
		var pwd=$('#repassword').val();
		   param = {"LOGIN":ln,
				   "PASSWORD":pwd,
				   "SITEID":"320581",
				   "MOBILENUM":$('#mobilenum').val(),
				   "CODE":$('#msgcode').val(),
				   "PWDSTRENGTH":"1",
				   "EMAIL":$('#email').val()
		   };
		   $.ajax({
				url : "/credit/User_Center/Register",
				type : "POST",
				data: param,
				success:function(data){ 
					if (data.head.rtnCode == "000000") {
//						alert("注册成功");
						 returnLogin(ln,pwd);
					//	register.qyregister();
					}else if(data.head.rtnCode == "000001"){
						//$("#yzm").after("<label id='yzmts' class='error'  style='width:300px;'><font style='color:red'>您输入的验证码有误或失效</font></label>");
						$("#yzm").html("<label for='checkpassword' class='error' style='margin-left:1px'>您输入的验证码有误或失效</label>");
						$("#showmsgcode").find('.right').remove();
					//	$("#btnsub").attr("disabled",false);
					}else {
						popAny(data.head.rtnMsg);
					//	$("#btnsub").attr("disabled",false);
						return;
					}
				//	$("#btnsub").attr("disabled",false);
			},
				timeout : 6000
			});
	
		
    }


/*注册后登陆*/
function  returnLogin(login,pwd)
{
	 var LOGIN =login;
	 var PASSWORD = pwd;
	
	 param={
	    "LOGIN":LOGIN,
	    "PASSWORD":PASSWORD
	 },
	 $.ajax({
			url : "/credit/User_Center/UserLogin",
			type : "POST",
			data: param,
			success : function(data) {
				if(data!=null){
				if(data.head.rtnCode=="000000"){
					window.location.reload();
					$("#modaltrigger_rz").trigger("click"); //提示认证
				}else{
					  $("#lperror").html(data.head.rtnMsg);
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