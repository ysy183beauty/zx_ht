<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/bootstrap.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/comm.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/font-awesome.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/style.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/main.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/index.css" />
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/header.css" />
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery-1.9.1.min.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.cookie.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/bootstrap.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/style.js" ></script>
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/pager/js/kkpager.min.js"></script>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/js/pager/css/kkpager.css" />
<script type="text/javascript" src="${ctx}/r/cms/www/red/js/jquery.scrollTo.min.js"></script>
<!--[if lt IE 9]>
<script src="${ctx}/r/cms/www/red/js/respond.min.js"></script>
<script src="${ctx}/r/cms/www/red/js/html5shiv.min.js"></script>
<![endif]-->
<script>
    var getUrlParameter = function getUrlParameter(sParam) {
        var sPageURL = decodeURIComponent(window.location.search.substring(1)),
                sURLVariables = sPageURL.split('&'),
                sParameterName,
                i;

        for (i = 0; i < sURLVariables.length; i++) {
            sParameterName = sURLVariables[i].split('=');

            if (sParameterName[0] === sParam) {
                return sParameterName[1] === undefined ? true : sParameterName[1];
            }
        }
    };
</script>