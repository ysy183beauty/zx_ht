<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>信用服务</title>
<#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xyfw.css"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xycx.css"/>
    <style>
        .credit_list a{
            font-size:14px!important;
        }
        .content{
            margin-top: 20px;
        }
        #myModal-promise{
            top:100px;
        }
    </style>
</head>
<body>
<#--<div class="load_main">-->
    <#--<img src="/r/cms/www/red/img/loading.gif">-->
<#--</div>-->
<div class="wrap">
<#include "include/header.ftl"/>
<div class="main">
    <div class="modal fade" id="myModal-promise" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <#--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                    <h4 class="modal-title" id="myModalLabel">申报责任说明</h4>
                </div>
                <div class="modal-body">
                    <p>一、信用承诺申报责任</p>
                    <p>1.按照江苏省社会信用体系建设相关文件、管理办法，本系统对需要申报江苏省各类政府项目或审批的单位进行信用承诺申报服务。</p>
                    <p>2.本系统提供的信用承诺申报服务，不收取任何费用。有额外服务的，收取工本费（经物价部门核定）。</p>
                    <p>3.单位信用承诺申报公示不可用于任何商业用途，若由此引发的法律纠纷，与本系统无关。</p>
                    <p>4.信用承诺申报服务本着自愿申报的原则。</p>
                    <p>5.申报法人需对承诺内容的真实性进行负责，因虚假、违约、泄密等不良事件而产生的一切后果，均由申报法人承担，与本系统无关。</p>
                    <p>6.法人申报的承诺内容将完全授权信用平台进行公示和使用，因法人提供的承诺申报内容虚假、有误等问题引发的法律纠纷和道德纠纷，与本系统无关。</p>
                    <p>二、信用承诺申报要求</p>
                    <p>1.承诺内容应包括四部分内容：自身信用状况、申报提交材料的真实性、违背承诺的责任、违背承诺的后续改进操作。</p>
                    <p>2.信用承诺申报的内容需加盖申报法人的公章。</p>
                    <p>3.信用承诺申报上传内容为上述承诺的扫描件。</p>
                    <p>三、本协议由江苏省公共信用信息中心负责最终解释。</p>
                </div>
                <div class="modal-footer">
                    <#--<div id="xieyi">-->
                        <#--<input id="xieyi-text" type="checkbox" onclick="agree()">-->
                        <#--<span>同意信用信息服务平台用户服务协议</span>-->
                    <#--</div>-->
                    <div>
                        <button id="sumbit-btn" type="button" class="btn btn-primary" data-dismiss="modal">同&nbsp;&nbsp;意</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <div id="container">
        <#--<div class="title">-->
            <#--<span>当前位置：<a href="/">首页</a> > <a href="/credit/srv/index.do">信用服务 </a> > <span> 异议处理</span></span>-->
        <#--</div>-->
        <div class="sit height_auto m-t-md">
            <ul>
                <li>
                    <a id="sy" href="/">首页</a>
                </li>
                <li>&gt;</li>
                <li>
                    <a href="/credit/srv/index.do">信用服务</a>
                </li>
                <li class="last-tit"></li>
            </ul>
        </div>
        <div class="row">
            <div id="nav_search" class="col-sm-3">
            <#include "include/NAV_SERVICE_INDEX.ftl"/>
            </div>
            <div class="content col-sm-9" id="mainContent">
            <#if !user??>
                请登录后再来查询信息！
            </#if>
            </div>
        </div>

    </div>
</div>
</div>
<#include "include/footer.ftl"/>
</body>
<script>
    $(function () {
        //    获取页面参数
        var URLParams = new Array();
        var aParams = document.location.search.substr(1).split('&');
        for (i=0; i < aParams.length ; i++){
            var aParam = aParams[i].split('=');
            URLParams[aParam[0]] = aParam[1];
        }
        var word=URLParams["word"];
        if(word && word == 1){
            $("#nav li").eq(1).click();
        }
        else if(word && word == 2){
            $("#nav li").eq(2).click();
        }
        else if(word && word == 5){
            $("#nav li").eq(5).click();
        }
        else{
            $("#nav li:first-child").click();
        }
    })
</script>
</html>