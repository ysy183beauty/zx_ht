<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib  prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${resoucename}- 诚信常熟</title>
<meta http-equiv="X-UA-Compatible" content="IE=8;"/>
<script type="text/javascript"
	src="../../r/cms/www/red/js/pager/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../../r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<link rel="stylesheet" type="text/css"
	src="../../r/cms/www/red/js/pager/css/kkpager.css" />
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
							href="/">首页</a> <span>></span> 信用查询 <span>></span> 
							<c:choose>
                            <c:when test="${param.param_type==1}">
	                                                                                     企业信用查询
		                    </c:when>
	                    	<c:otherwise>
                                                                                                    个人信用查询
                           </c:otherwise>
                           </c:choose>
							
						</span>
					</div>
					<div class="content" style="padding: 20px;">
						<div class="contentDetail">
							<div class="title w">${resoucename}</div>
							<div class="wbj  w">来源委办局：${wbjmc}</div>
							<%int t=1;%>	
						<c:forEach items="${content.data.column_data.list}" var="damin">
							<div style="border-right:1px solid grey;border-top:1px solid grey" class="block w">
								<c:set value="0" var="i" /> 
								<%int x = 1; %>
								 <c:forEach items="${content.data.columnList}" var="key"> 	
									<p class="fl block" id="p<%=t%><%=x%>">
										<span class="bgcolor">${content.data.columnMcList[i]}</span>
										<label>${damin.get(key)}</label>
									</p>
						     	<c:set value="${i + 1}" var="i" /> 
						     	 <%x++; %>
								 </c:forEach> 
								 <div class="fl none" style="border-bottom:0" id="p<%=t%>0">
										<span style="height:35px;border-right:0"></span>
										
								</div>
								 <div class="clear"></div>
								 
							</div><br/>
							<%t++; %>
						</c:forEach>
								
						</div>

					</div>
				</div>
			</div>
		
		</div>
		<%@ include file="include/bottom.jsp"%>
	</div>
<script type="text/javascript">
     
    $(function(){
    	var count='${content.data.column_data.count}';
    	for(var t=1;t<=count;t++){
    		gsh(t);
    	}
    });
	 
	function gsh(t){
   		//控制表格，字段少的在前
    	 var n='${fn:length(content.data.columnList)}';    
    	 for(var i=1;i<=n;i++){
    		 
    		
    		for(var j=1;j<=n-i+1;j++){
    			if($("#p"+t+j).height()>$("#p"+t+(j+1)).height())
    				{
    					$("#p"+t+"0").html($("#p"+t+j).html());
    					$("#p"+t+j).html($("#p"+t+(j+1)).html());
    					$("#p"+t+(j+1)).html($("#p"+t+"0").html())
    				}
    		}
    		if(i%2==0){
    			if($("#p"+t+i).height()>36){
    				$("#p"+t+(i-1)).css("width","800px");
        			$("#p"+t+(i-1)+" label").css("width","647px");
        			$("#p"+t+i).css("width","800px");
        			$("#p"+t+i+" label").css("width","647px");
        		}
    		}
    		if(n%2!=0){

        			$("#p"+t+n).css("width","800px");
        			$("#p"+t+n+" label").css("width","648px");
        		
    		}
    		
			if($("#p"+t+i+" span").height()<$("#p"+t+i+" label").height()){
  	    		
  	    		$("#p"+t+i+" span").height($("#p"+t+i+" label").height());	
  	    	}else{
  	    		$("#p"+t+i+" label").height($("#p"+t+i+" span").height());	
  	    	}
    		
    		
    		for(var y=1;y<=100;y++){
 				if($("#p"+t+i+" label").height()>36*y && $("#p"+t+i+" label").height()<36*(y+1)){
 	    			$("#p"+t+i+" label").height(36*(y+1)-1);
 	    		}
 			}
    		
    	 }
	
    	}
  
</script>
</body>
</html>