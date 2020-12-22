<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${resoucename}- 诚信常熟</title>
<meta http-equiv="X-UA-Compatible" content="IE=8;"/>
<script type="text/javascript"
	src="../../r/cms/www/red/js/pager/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../../r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css"	src="../../r/cms/www/red/js/pager/css/kkpager.css" />
<style type="text/css">
.contentDetail {
	border-left: 0;
	border-right: 0px;
	border-top: 0;
	border-radius: 4px;
	border-spacing: 0;
	width:800px;
	margin: 0px auto;
	font-size: 12px;
	margin-bottom: 30px;
}

.contentDetail .title
{
	height: 40px;
	font-size: 20px;
	text-align: center;
	padding-bottom: 10px;
}

.contentDetail .wbj
{
	height: 25px;
	font-size: 15px;
	text-align: left;
	padding-bottom: 10px;
}

.contentDetail div p
{
	width: 400px;
	display: block;
	float: left;
	border-bottom:1px solid grey;
	text-indent:0em; 

}
.contentDetail p span
{
	width: 140px;
	display: block;
	float: left;
	font-size: 14px;
	border-right: 0;
	border-left: 1px solid grey;
	border-right: 1px solid grey;
	padding-right:5px; 
	*padding-right:5px;
	min-height: 35px;
	line-height: 35px;
}
.contentDetail p label
{
	width: 246px;
	display: block;
	float: left;
	font-size: 13px;
	border-right: 0;
	padding-left:5px; 
	*padding-left:5px; 
	min-height: 35px;
	line-height: 35px;
}
.bgcolor
{
	background: #F5F5F5;
	font-weight: bold;
	text-align: right;
}


.pre-next-page {
	margin-left: 300px;
	margin-bottom: 10px;
	font-size: 12px;
	margin-top: 10px;
}

.pre-next-page li {
	padding: 5px;
}

.f-left, .f-right {
	text-align: right;
}
</style>
</head>

<body>
	<div class="wrap head_bg">
		<%@ include file="include/head.jsp"%>
		<div id="main">
			<div class="main">
				<div class="list_article">
					<div class="location_box">
						<label class="fl">当前位置</label> <span class="fl"> <a
							href="/">首页</a> <span>></span> 双公示专栏 <span>></span> 
						双公示
						</span>
					</div>
					<div class="content" style="padding: 20px;">
						<div class="contentDetail">
							<div class="title w">常熟市市政许可和行政处罚等信用信息公示专栏</div>
							<div style="border-right:1px solid grey;border-top:1px solid grey" class="block w"> 
							
								<p class="fl block" id="p1">
										<span class="bgcolor">行政许可决定书文号</span>
										<label id="xk_wsh" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p2">
										<span class="bgcolor">项目名称</span>
										<label id="xk_xmmc" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p3">
										<span class="bgcolor">审批类别</span>
										<label id="xk_splb" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p4">
										<span class="bgcolor">许可内容</span>
										<label id="xk_nr" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p5">
										<span class="bgcolor">行政相对人名称</span>
										<label id="xk_xdr" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p6">
										<span class="bgcolor">统一社会信用代码</span>
										<label id="xk_xdr_shxym" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p7">
										<span class="bgcolor">组织机构代码</span>
										<label id="xk_xdr_zdm" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p8">
										<span class="bgcolor">居民身份证号</span>
										<label id="xk_xdr_sfz" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p9">
										<span class="bgcolor">法定代表人姓名</span>
										<label id="xk_fr" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p10">
										<span class="bgcolor">许可决定日期</span>
										<label id="xk_sxq" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p11">
										<span class="bgcolor">许可截止期</span>
										<label id="xk_jzq" style="height: 35px;"></label>
								</p>
								<p class="fl block" id="p12">
										<span class="bgcolor">许可机关</span>
										<label id="xk_xzjg" style="height: 35px;"></label>
								</p>
									 <div class="clear"></div>	
								</div>
								
							</div>
						
								
						</div>

					</div>
				</div>
			</div>
			<%@ include file="include/bottom.jsp"%>
		</div>

<script type="text/javascript">
     
    $(function(){
   		//控制表格，字段少的在前
    	 var n='${fn:length(content.data.columnList)}';
    	 for(var i=1;i<=n;i++){
    		 
    		
    		for(var j=1;j<=n-i+1;j++){
    			if($("#p"+j).height()>$("#p"+(j+1)).height())
    				{
    					$("#p0").html($("#p"+j).html());
    					$("#p"+j).html($("#p"+(j+1)).html());
    					$("#p"+(j+1)).html($("#p0").html())
    				}
    		}
    		if(i%2==0){
    			if($("#p"+i).height()>36){
    				$("#p"+(i-1)).css("width","800px");
        			$("#p"+(i-1)+" label").css("width","647px");
        			$("#p"+i).css("width","800px");
        			$("#p"+i+" label").css("width","647px");
        		}
    		}
    		if(n%2!=0){

        			$("#p"+n).css("width","800px");
        			$("#p"+n+" label").css("width","648px");
        		
    		}
    		
			if($("#p"+i+" span").height()<$("#p"+i+" label").height()){
  	    		
  	    		$("#p"+i+" span").height($("#p"+i+" label").height());	
  	    	}else{
  	    		$("#p"+i+" label").height($("#p"+i+" span").height());	
  	    	}
    		
    		
    		for(var y=1;y<=100;y++){
 				if($("#p"+i+" label").height()>36*y && $("#p"+i+" label").height()<36*(y+1)){
 	    			$("#p"+i+" label").height(36*(y+1)-1);
 	    		}
 			}
    		
    	 }
	
    	 param1 ={
					"start":0,
					"limit":1,
					"type":2,
					"xzxdrm":'${param.xzxdrm}'
			},
    	
  	   $.ajax({
			url : "/credit/credit_publity/creditSGS",
			type : "GET",
			async: false,
			data: param1,
			success : function(data) {	
		     var data1 = data.list;		
				var jzq ='';
				var sxq='';
				if(data1[0].xk_jzq!=''){
				var starttime = new Date(data1[0].xk_jzq);
				jzq = starttime.getFullYear()+"年"+(starttime.getMonth()+1)+"月"+starttime.getDate()+"日";
				}
				if(data1[0].xk_sxq!=''){
					var starttime = new Date(data1[0].xk_sxq);
					sxq = starttime.getFullYear()+"年"+(starttime.getMonth()+1)+"月"+starttime.getDate()+"日";
				}
				$("#xk_wsh").html(data1[0].xk_wsh);
				$("#xk_xmmc").html(data1[0].xk_xmmc);
				$("#xk_splb").html(data1[0].xk_splb);
				$("#xk_nr").html(data1[0].xk_nr);
				$("#xk_xdr").html(data1[0].xk_xdr);
				$("#xk_xdr_shxym").html(data1[0].xk_xdr_shxym);
				$("#xk_xdr_zdm").html(data1[0].xk_xdr_zdm);
				$("#xk_xdr_sfz").html(data1[0].xk_xdr_sfz);
				$("#xk_fr").html(data1[0].xk_fr);
				$("#xk_sxq").html(sxq);
				$("#xk_jzq").html(jzq);
				$("#xk_xzjg").html(data1[0].xk_xzjg);
				
			
				
				
			
			
			},
			
			timeout : 6000
		});
    });
	 
 
  
</script>
</body>
</html>