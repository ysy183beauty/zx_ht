<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>公示信息 - 信用大理</title>
<meta http-equiv="X-UA-Compatible" content="IE=8;">

	<script type="text/javascript"
		src="../../r/cms/www/red/js/pager/js/jquery-1.10.2.min.js"></script>
</head>
<body>
        <%@ include file="include/head.jsp"%>
        <div class="main">
            <div class="w">
                <div class="pull-left w240">
                    <div class="height_auto m-t-md">
                        <div class="left_nav down">
                            <span>信用公示</span>
                            <ul>
                                <li>
                                    <a id="sgs">信用双公示</a>
                                    <dl>
                                        <dd>
                                            <a id="xzxk" href="/xzxk/index.jhtml">行政许可</a>
                                        </dd>
                                        <dd>
                                            <a id="xzcf" href="/xzcf/index.jhtml">行政处罚</a>
                                        </dd>
                                    </dl>
                                </li>
                                <li>
                                    <a id="xxgsdm">信用公示代码</a>
                                    <dl>
                                        <dd>
                                            <a id="gsj" href="/gsj/index.jhtml">大理市工商局</a>
                                        </dd>
                                        <dd>
                                            <a id="mzj" href="/mzj/index.jhtml">大理市民政局</a>
                                        </dd>
                                        <dd>
                                            <a id="bb" href="/bb/index.jhtml">大理市编办</a>
                                        </dd>
                                    </dl>
                                </li>
                                <li>
                                    <a id="hhb">红黑榜</a>
                                    <dl>
                                        <dd>
                                            <a id="red" href="/red/index.jhtml">红榜</a>
                                        </dd>
                                        <dd>
                                            <a id="black" href="/black/index.jhtml">黑榜</a>
                                        </dd>
                                    </dl>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            <div class="w">
                <div class="w740 pull-right">
                    <div class="m-t-md">
                        <div class="g_tit">
                            <span>信用测试</span>
                        </div>
                        <div class="zx_tab height_auto">
                            <div class="bd m-t-xs">
                                <ul>
                            <jsp:useBean id="dateValue" class="java.util.Date" />
                            <c:forEach items="${content.list}" var="domain" varStatus="status">
                             <jsp:setProperty name="dateValue" property="time"
                                                 value="${domain.fbsj}" />
                                    <li>
                                        <dl>
                                            <dt>
                                            <a href="/credit/credit_publity/creditPublityView?paramId=${domain.id}&start=0&limit=20&type=1&first=true" target="_blank">${domain.xxmc}</a>
                                            </dt>
                                            <dd>${domain.xxlywbj}</dd>
                                        </dl>
                                        <div style="clear:both"></div>
                                    </li>
                            </c:forEach>
                                </ul>
                                <div class="clear"></div></div>

                            <div style="clear: both"></div>
                            <div class="page text-center m-t-lg" >
                                <ul id="fy">

                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            <div class="clear"></div>
</div>
                </div>
        </div>
		<%@ include file="include/bottom.jsp"%>
	<script type="text/javascript">
        //生成分页
        $(function(){
            var size=10;							//每页显示记录条数
            var start=${param.start};				//起始记录索引
            var count=${content.count};				//记录总条数
            var total=parseInt((count-1)/size)+1;	//总页数
            var pageIndex = (start / size) + 1;		//当前页
            var url=baseUrl +'?qymc=' + qymc + '&gszch=' + gszch;
            var str;
            $("#fy").empty();
            $("#fy").append("共"+count+"记录 "+pageIndex+"/"+total+"页&nbsp;&nbsp;");
            if(count==0){
                $("#fy").append("<span style='font-size: 16px;'>没有查询到相关信息!</span>");
            }
            if(count>0 && start==0){
                $("#fy").append("<li ><a disabled='disabled'>首页</a></li> <li><a disabled='disabled'>上一页</a></li>&nbsp;&nbsp;");
            }
            if(count>0&&start>0){
                $("#fy").append("<li><a href='"+url+"&start=0&limit="+size+"'>首页</a></li> <li> <a href='"+url+"&start="+(start-size)+"&limit="+size+"'>上一页</a></li>");
            }
            if(count>0&&pageIndex==total){
                $("#fy").append("<li><a disabled='disabled'>下一页</a></li> <li> <a disabled='disabled'>尾页</a></li>");
            }
            if(count>0&&pageIndex<total){
                $("#fy").append("<li><a href='"+url+"&start="+(start+size)+"&limit="+size+"'>下一页</a> </li><li><a href='"+url+"&start="+(total-1)*size+"&limit="+size+"'>尾页</a></li>");
            }

            if (count > 0) {
                str = "&nbsp;&nbsp;第<select style='line-height: 30px;height: 30px;width: 66px;padding-left: 4px;margin-left:3px;margin-right:3px;' onchange='selectfy(\"" + url
                        + "\",this.value," + size + ")'>";
                for (var i = 1; i <= total; i++) {
                    var temp = (i - 1) * size;
                    if (i == pageIndex) {
                        str += "<option selected='selected' value =" + temp + ">" + i + "</option>";
                    } else {
                        str += "<option value ="+temp+">" + i + "</option>";
                    }
                }
                str = str + "</select>页";
                $("#fy").append(str);
            }
        });

        function selectfy(url, start, limit) {
            window.location = url + "&start=" + start + "&limit=" + limit;
        }
	</script>

</body>
</html>
