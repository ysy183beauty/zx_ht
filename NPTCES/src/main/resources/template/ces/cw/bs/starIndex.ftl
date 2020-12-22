<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
<#include "/template/Credit_Common/c_pagination.ftl">
    <link rel="stylesheet" href="${wctx}/pub/ces/cw/credit-rating/css/rating-list.css">
</head>
<body>
<div class="container-fluid">
    <!-- BEGIN PAGE HEADER-->
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>本局星标信息</div>
                </div>
                <div class="portlet-body form">
                    <form  class="form-horizontal">
                        <div class="row-fluid">
                            <div class="span4">
                                <div class="control-group">
                                    <label class="control-label">榜单数量</label>
                                    <div class="controls">
                                        <select id="pageSize" onchange="list()">
                                            <option value="10">10</option>
                                            <option value="20">20</option>
                                            <option value="50">50</option>
                                            <option value="100">100</option>
                                            <option value="200">200</option>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <#--<div class="span4">-->
                                <#--<div class="control-group">-->
                                    <#--<label for="" class="control-label">-->
                                        <#--预警维度：-->
                                    <#--</label>-->
                                    <#--<div class="controls">-->
                                        <#--<select id="dmsId" class="small">-->
                                            <#--<option value="">全部</option>-->
                                        <#--<#list CES_DMS_LIST as c>-->
                                            <#--<option value="${c.baseModelGroupId}">${c.dimensionName}</option>-->
                                        <#--</#list>-->
                                        <#--</select>-->
                                    <#--</div>-->
                                <#--</div>-->
                            <#--</div>-->
                        </div>

                        <div class="form-actions">
                        <#--<button type="submit" class="btn blue"><i class="icon-search icon-white"></i>&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询</button>-->
                            <#--<button type="reset" class="btn"><i class="icon-repeat"></i>&nbsp;&nbsp;重&nbsp;&nbsp;&nbsp;&nbsp;置</button>-->
                        </div>
                    </form>
                </div>
                <div class="portlet-body">
                    <table class="table  table-bordered table-hover" id="pageData">
                        <thead>
                        <tr>
                            <th width="30%">${creditEntityIT!}</th>
                            <th width="30%">${creditEntityTT!}</th>
                            <th>预警评分</th>
                            <th>预警等级</th>
                            <th width="20%">评估时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <div id="pagination"></div>
                </div>
            </div>
        </div>
    </div>
    <!-- END PAGE HEADER-->
</div>

<#--加载数据-->
<script>
    $(function(){
        list();
    });
    function bsDetail(bsId){
        window.location="detail.action?creditEntityId="+bsId;
    }
    function list() {
        var data = {
            ceTypeCode: $("#ceTypeCode").val(),
            dmsId: $("#dmsId").val(),
            currPage: 1,
            pageSize: $("#pageSize").val()
        };
        $.nptPOST("starList.action", data, function (data) {
            $("#pageData tbody").html(data);
            bgColor();
        });
        pagination.initPagination("starList.${urlExt}", data,bgColor);
    }
</script>
<#--划分颜色-->
<script type="text/javascript">
    function bgColor(){
        $(".td-val").each(function(){
            var val=$(this).find(".level").text();
            console.log(val);
            if(val == 'A'){
                $(this).css("background","#ff0000");
                console.log(val);
            }
            if(val == "B"){
                $(this).css("background","#ff5e18");
            }
            if(val == "C"){
                $(this).css("background","#fb9411");
            }
            if(val == "D"){
                $(this).css("background","#f5c614");
            }
            if(val == "E"){
                $(this).css("background","#a5ff47");
            }
            if(val == "F"){
                $(this).css("background","#5bff88");
            }
            if(val == "G"){
                $(this).css("background","#60c4fd");
            }
        });
    }
</script>
</body>
</html>
