<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="wctx" value="${ctx}${applicationScope.webSourcePathKey}"/>
<c:set var="themeName" value="${sessionScope.userViewStyleKey}"/>
<c:set var="sctx" value="${wctx}/pub/style/${themeName}"/>
<c:set var="platformName" value="${_styleParam.platformName }"/>
<c:set var="platformLogo" value="${wctx}/${_styleParam.styleLogo }"/>
<c:set var="platformCopyright" value="${_styleParam.copyright }"/>
<%
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache");
    response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html>
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>诚信之道</title>
    <link href="/pub/index/css/bootstrap.min.css" rel="stylesheet">
    <link href="/pub/index/css/font-awesome.min.css" rel="stylesheet">
    <link href="/pub/index/css/prettyPhoto.css" rel="stylesheet">
    <link href="/pub/index/css/main.css" rel="stylesheet">
    <link href="/pub/index/css/image-float.css" rel="stylesheet">

    <!--[if lt IE 9]>
    <script src="/pub/index/js/html5shiv.js"></script>
    <script src="/pub/index/js/respond.min.js"></script>
    <![endif]-->
    <link rel="shortcut icon" href="/pub/index/xin.ico">


<sitemesh:write property='head'/>
</head>
<section id="main-slider" class="carousel">
    <div class="carousel-inner">
        <div class="item active">
            <div class="container">
                <div class="carousel-content">
                    <h1><strong>信</strong></h1>
                    <p class="lead"><strong>吾日三省吾身，为人谋而不忠乎？与朋友交而不信乎？传不习乎？......信近于义，言可复也!</strong></p>
                </div>
            </div>
        </div><!--/.item-->
        <div class="item">
            <div class="container">
                <div class="carousel-content">
                    <h1><strong>信用</strong></h1>
                    <p class="lead"><strong>信者，无形之态，旧事之义。信用于人，可规之行，信用于物，可范其态！</strong></p>
                </div>
            </div>
        </div><!--/.item-->
        <div class="item">
            <div class="container">
                <div class="carousel-content">
                    <h1><strong>征信</strong></h1>
                    <p class="lead"><strong>君子之言，信而有征，故怨远于其身!</strong></p>
                </div>
            </div>
        </div>
    </div>
    <a class="prev" href="#main-slider" data-slide="prev"><i class="icon-angle-left"></i></a>
    <a class="next" href="#main-slider" data-slide="next"><i class="icon-angle-right"></i></a>
</section>

<sitemesh:write property="body"/>

<footer id="footer">
    <div class="container">
        <div class="row">
            <div class="col-sm-6">
                <span> &copy;2017 神州数码NPT团队.All rights reserved.</span>
            </div>
        </div>
    </div>
</footer>

<script src="/pub/index/js/jquery.js"></script>
<script src="/pub/index/js/bootstrap.min.js"></script>
<script src="/pub/index/js/jquery.isotope.min.js"></script>
<script src="/pub/index/js/jquery.prettyPhoto.js"></script>
<script src="/pub/index/js/main.js"></script>
<script>
    window.onload = function () {
        resizePic();
    };
    window.onresize = function () {
        resizePic();
    }
    function resizePic() {
        var w = $(".he_border1").width();
        $(".he_border1").height(w);
    }
</script>
</body>
</html>