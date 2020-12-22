/**
 * 
 */
$(function(){

	


	
 /**
  * 找回密码
  */
//	$("#nextbtn").click(function(){
//		 $(".step1").css("display","none");
//		 $("#step1").removeClass("current");                                                                         
//		 $(".step2").css("display","block");
//		 $("#step2").addClass("current");
////		 validate ();
//	});
//	$("#nextbtn2").click(function(){
//		 $(".step2").css("display","none");
//		 $("#step2").removeClass("current");                                                                         
//		 $(".step3").css("display","block");
//		 $("#step3").addClass("current");
//	});
	$("#prebtn").click(function(){
		 $(".step2").css("display","none");
		 $("#step2").removeClass("current");                                                                         
		 $(".step1").css("display","block");
		 $("#step1").addClass("current");

	});
//	$("#okbtn").click(function(){
//		 $(".step3").css("display","none");
//		 $("#step3").removeClass("current");                                                                         
//		 $(".step4").css("display","block");
//		 $("#step4").addClass("current");
//	});
//	$("#returnbtn").click(function(){
//		alert("返回并登陆");
//	});
////	
//	$("#smnext").click(function(){
//		 $(".step1").css("display","none");
//		 $("#step1").removeClass("current");                                                                         
//		 $(".step2").css("display","block");
//		 $("#step2").addClass("current");
//	});
//	$("#combtn").click(function(){
//		 $(".step2").css("display","none");
//		 $("#step2").removeClass("current");                                                                         
//		 $(".step4").css("display","block");
//		 $("#step4").addClass("current");
//	});
	$("#backbtn").click(function(){
		 $(".step2").css("display","none");
		 $("#step2").removeClass("current");                                                                         
		 $(".step1").css("display","block");
		 $("#step1").addClass("current");
	});

//	$("#sfznext").click(function(){
//		 $(".step1").css("display","none");
//		 $("#step1").removeClass("current");                                                                         
//		 $(".step2").css("display","block");
//		 $("#step2").addClass("current");
//	});
//	$("#combtn2").click(function(){
//		 $(".step2").css("display","none");
//		 $("#step2").removeClass("current");                                                                         
//		 $(".step4").css("display","block");
//		 $("#step4").addClass("current");
//	});
	$("#backbtn2").click(function(){
		 $(".step2").css("display","none");
		 $("#step2").removeClass("current");                                                                         
		 $(".step1").css("display","block");
		 $("#step1").addClass("current");
	});

	
});	
	
	
	/**
	 * 验证码
	 */
	var code ; //在全局 定义验证码
	function createCode(){
	code = new Array();
	var codeLength = 4;//验证码的长度
	var checkCode = document.getElementById("checkCode");
	checkCode.value = "";
	var selectChar = new Array(2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

	for(var i=0;i<codeLength;i++) {
	  var charIndex = Math.floor(Math.random()*32);
	  code +=selectChar[charIndex];
	}
	if(code.length != codeLength){
	  createCode();
	}
	checkCode.value = code;
	}

	function validate(){
	var inputCode = document.getElementById("input1").value.toUpperCase();

	if(inputCode.length <=0) {
		$("#codeerror").html("不能为空");
		 $("#codeerror").css("display","block");
	   return false;
	}
	else if(inputCode == code ){
		 $("#codeerror").css("display","none");
	   return true;
	}
	else {
		$("#codeerror").html("验证码错误");
		 $("#codeerror").css("display","block");
	createCode();
	   return false;
	}
	}

/**
 * 验证码
 */
var checkUsercCode ; //在全局 定义验证码
function createUserCode(){
    checkUsercCode = new Array();
    var codeLength = 4;//验证码的长度
    var checkCode = document.getElementById("checkUsercCode");
    checkCode.value = "";
    var selectChar = new Array(2,3,4,5,6,7,8,9,'A','B','C','D','E','F','G','H','J','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z');

    for(var i=0;i<codeLength;i++) {
        var charIndex = Math.floor(Math.random()*32);
        checkUsercCode +=selectChar[charIndex];
    }
    if(checkUsercCode.length != codeLength){
        createCode();
    }
    checkCode.value = checkUsercCode;
}
	
	
/**
 * 下拉菜单
 * @param obj
 * @returns
 */function dropMenu(obj){
		$(obj).each(function(){
			var theSpan = $(this);
			var theMenu = theSpan.find(".submenu");
			var tarHeight = theMenu.height();
			theMenu.css({height:0,opacity:0});
			theSpan.hover(
				function(){
					$(this).addClass("selected");
					theMenu.stop().show().animate({height:tarHeight,opacity:1},400);
				},
				function(){
					$(this).removeClass("selected");
					theMenu.stop().animate({height:0,opacity:0},400,function(){
						$(this).css({display:"none"});
					});
				}
			);
		});
	}

	
	
	 

/**
 * 成员单位显示/隐藏
 * @returns
 */
	 function show_hiddendiv(n){
		document.getElementById("_strHref"+n).className="bg_hide";
	 	document.getElementById("hidden_div"+n).style.display='block';
	 	document.getElementById("_strHref"+n).href='javascript:hidden_showdiv('+n+');';

	 }
	 function hidden_showdiv(n){
		document.getElementById("_strHref"+n).className="bg_show";
	 	document.getElementById("hidden_div"+n).style.display='none';
	 	document.getElementById("_strHref"+n).href='javascript:show_hiddendiv('+n+');';

	 }
	 	
	 	
	/*倒计时返回首页*/ 	
	 	var returnTime=5; 
	 	function returnIndex(val) { 
	 	if (returnTime == 0) { 
	 		window.open("/index.jhtml","_self");
	 		return;
	 	} else { 

	 	val.innerHTML= returnTime + "秒后跳转到首页"; 
	 	returnTime--; 
	 	} 
	 	setTimeout(function() { 
	 	returnIndex(val) 
	 	},1000) 
	 	} 
	//替换敏感字符 	
	/* function revalue(a){
		 if (value == null) {    
			 return null;   
			 } 
		   var result = new StringBuffer(value.length()); 
		   for (var i=0; i<value.length(); ++i) 
		       {    switch (value.charAt(i)) {    case '<': 
		     result.append("&lt;");     break;     case '>': 
		     result.append("&gt;");     break;    case '"': 
		     result.append("&quot;");     break;    case '\'': 
		     result.append("&#39;");     break;    case '%': 
		     result.append("&#37;");     break;    case ';': 
		     result.append("&#59;");     break;    case '(': 
		     result.append("&#40;");
		     break;    case ')': 
		     result.append("&#41;");     break;    case '&': 
		     result.append("&amp;");     break;    case '+': 
		     result.append("&#43;");     break;    default: 
		     result.append(value.charAt(i));     break;    }   } 
		   return result; 
		  } 
	 */
	 