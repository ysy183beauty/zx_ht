<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
    <title>企业基础查询 - 诚信大理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=8;"/>
    <link rel="stylesheet" href="../../r/cms/www/red/css/bootstrap.css" />
    <link rel="stylesheet" href="../../r/cms/www/red/css/comm.css" />
    <link rel="stylesheet" href="../../r/cms/www/red/css/font-awesome.css" />
    <link rel="stylesheet" href="../../r/cms/www/red/css/style.css" />
    <link rel="stylesheet" href="../../r/cms/www/red/css/main.css" />
    <script type="text/javascript" src="../../r/cms/www/red/js/jquery-2.1.0.js" ></script>
    <script type="text/javascript" src="../../r/cms/www/red/js/jquery.SuperSlide.2.1.1.js"></script>
    <script type="text/javascript" src="../../r/cms/www/red/js/style.js" ></script>
    <script type="text/javascript" src="../../r/cms/www/red/js/pager/js/kkpagerGis.js"></script>
    <link rel="stylesheet" href="../../r/cms/www/red/js/pager/css/kkpagerGis.css" />
</head>
<body>
<%@ include file="include/head.jsp"%>
<div class="main">
    <div class="w">
        <div class="sit height_auto m-t-md">
            <ul >
                <li>
                    <a href="">首页</a>
                </li>
                <li>&gt;</li>
                <li>
                    <a href="">信用查询</a>
                </li>
            </ul>
        </div>
        <div class="search height_auto text-center">
            <div class="m-t-md search_li">
                <ul id="queryType">
                    <li><span><a href="/credit/credit_company/baseCompanyList?start=0&limit=10">企业基础信息查询</a></span></li>
                    <li class="active"><span><a href="/credit/credit_company/creditCompanyList?start=0&limit=10">企业信用信息查询</a></span></li>
                    <li><span><a href="/credit/credit_company/creditPersonList?start=0&limit=10">重点人群信用查询</a></span></li>
                </ul>
            </div>
        </div>
        <div class="height_auto">
            <div class="m-t-md search_w">
                <input class="search_input" placeholder="企业信用查询，请输入企业名称" />
                <button class="serch_btn"><i class="fa fa-search"></i></button>
            </div>
        </div>

    </div>
    <div class="w">
        <table class="table table-bordered m-t-bg dataview">
            <thead id="thead">
            <tr>
                <th>法人/自然人</th>
                <th>社会信用代码/身份证号码</th>
                <th>信用记录</th>
                <th>更新时间</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="dateValue" class="java.util.Date"/>
            <c:forEach items="${content.list}" var="domain" varStatus="status">
                <tr>
                    <td>
                        <a href="/credit/credit_company/creditCompanyDetail?qybs=${domain.qybs}" title="${domain.qymc}" target="_blank">
                            <span >${domain.qymc}</span>
                        </a>
                    </td>
                    <td>${domain.qybs}</td>
                    <td></td>
                    <td></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <div class="page text-center m-t-lg" >
            <ul id="fy">

            </ul>
        </div>
        <div class="page text-center m-t-lg" >
            <ul id="kkpagerGis">

            </ul>
        </div>
    </div>
    <div class="clear"></div>
    <%@ include file="include/bottom.jsp"%>
</div>
<script type="text/javascript">
    var baseUrl = "/credit/credit_company/creditCompanyList";

    //查询
    function searchCompany() {
        var name = $('#companyName').val();
        var code = $('#companyCode').val();
        var url = baseUrl + '?start=0&limit=10&qymc=' + name + '&gszch=' + code;
        location.href = url;
    }

    //重置
    function reset() {
        $('#companyName').val('');
        $('#companyCode').val('');
    }

    function searchInfo(){
        var id =  $('#queryType>li[class=active]').attr('id');
        var condition = $('#queryCondition').val();
        if(condition=='' || condition==undefined){
            alert("请输入查询条件！");
            return;
        }
        if(id=="companyBaseInfo"){
            if(!isNaN(condition)){
                searchCompany('',condition);
            }else{
                searchCompany(condition,'');
            }
        }else if(id=="companyCreditInfo"){

        }else{

        }


    }

    //生成分页
    $(function(){
        var start= ${param.start};
        var limit= ${param.limit};           //起始记录索引
        var count= ${content.count};
        var qymc = "${param.qymc}";
        var gszch = "${param.gszch}";//记录总条数
        initFyPager(start,limit,count,qymc,gszch);
    });

    //初始化分页控件
    function initFyPager(start,limit,count,qymc,gszch) {
        var pno = Math.ceil(start / limit + 1);
        var total = Math.ceil((count+limit-1)/limit);

        kkpagerGis.pagetableid = '';
        kkpagerGis.total=total;
        kkpagerGis.totalRecords=total;
        kkpagerGis.pagelimit = limit;
        //生成分页控件
        kkpagerGis.generPageHtml({
            pno: pno,
            //总页码
            total: total,
            //总数据条数
            totalRecords: count,
            //链接算法
            getLink: function (n) {
                return '/credit/credit_company/creditCompanyList?start=' + (n - 1) * limit +'&limit='+limit+'&qymc='+qymc+'&gsczh='+gszch;
            }
        });
    }
</script>
</body>
</html>