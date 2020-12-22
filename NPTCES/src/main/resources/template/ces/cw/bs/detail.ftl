<!DOCTYPE html>
<html>
<head lang="en">
<#include "/template/Credit_Common/common.ftl">
<meta charset="UTF-8">
<title>信用预警报告 </title>
<script src="${wctx}/pub/CreditStyle/echarts/echarts3.min.js"></script>
<link rel="stylesheet" href="${wctx}/pub/ces/cw/credit-rating/css/rating-detail.css">
    <script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/colResizable-1.5.min.js"></script>

</head>
<body>
<#assign detailBox=creditEntityDetailBox>
<div class="main">
    <div class="main-top">
        <span class="main-top-left">评估时间：</span>
        <span>${detailBox.cwResult.createTime?string("yyyy-MM-dd")!}</span>
        <span class="main-top-right">
            <i id="star"
            <#if star == true >
               class="icon-star tooltips"
               data-original-title="取消关注"
            <#else >
               class="icon-star-empty tooltips"
               data-original-title="关注"
            </#if>
            ></i>
        </span>
    </div>
    <div class="table-content">
        <div class="content-title">
            <div class="title-score">
                <div>
                    <span>预警评分：</span>
                    <strong class="level">${detailBox.cwResult.riskScore!}</strong>
                </div>
                <dvi>
                    <span>预警等级：</span>
                    <strong class="level">${detailBox.cwResult.riskLevel!}</strong>
                </dvi>

            </div>


            <h1>${detailBox.cwResult.creditEntityTitle!}</h1>
        </div>
        <div class="baseInfo">
            <div class="info-title">
                <h5>基本信息</h5>
            </div>
            <table class="table table-bordered">
            <tr>
                <#list detailBox.creditEntityBasicInfo.dataArray as basicArray>

                    <th width="15%">${basicArray.title}：</th>
                    <td width="35%">${basicArray.value}</td>
                    <#if basicArray?index % 2 == 1>
                        </tr>
                        <tr>
                    </#if>
                </#list>
                <#if detailBox.creditEntityBasicInfo.dataArray?size % 2 ==1>
                    <th></th>
                    <td></td>
                </#if>
            </tr>
            </table>
        </div>
        <div class="yjwd">
            <div class="info-title">
                <h5>信用预警维度</h5>
                <span>本次预警主要是从以下角度进行衡量的，不同的维度对预警结果的影响权重不同</span>
            </div>
            <table class="table table-bordered table-striped table-hover">
                <thead>
                    <th width="10%">序号</th>
                    <th>维度名称</th>
                    <th width="10%">维度评分</th>
                    <th width="10%">维度等级</th>
                    <th width="10%">维度权重(%)</th>
                    <th>备注</th>
                </thead>
                <tbody>
                <#list detailBox.dmsResults as dms>
                    <tr>
                        <td>${dms?index+1}</td>
                        <td>${dms.dimensionName!}</td>
                        <td>${dms.riskScore!}</td>
                        <td>${dms.riskLevel!}</td>
                        <td>${dms.dmsWeight!0}</td>
                        <td>${dms.dmsNote!}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="dataStatistics">
            <div class="info-title">
                <h5>预警基准数据</h5>
            </div>
            <table class="table table-bordered" id="baseDate_table">
                <thead>
                <tr>
                    <th>来源单位</th>
                    <th width="20%">数据项</th>
                    <th width="5%">序号</th>
                    <th width="15%">时间</th>
                    <th>地点</th>
                    <th>描述</th>
                    <th width="10%">金额（元）</th>
                    <th>详情</th>
                    <th>分数</th>

                </tr>
                </thead>
                <tbody>
            <#list detailBox.sdmsResultMap?keys as key>
                <#list detailBox.sdmsResultMap.get(key) as sdms>
                    <#list detailBox.sdmsRDetailMap?keys as detailKey>
                        <#if sdms.id == detailKey>
                        <tr>
                            <th rowspan="${detailBox.sdmsRDetailMap.get(detailKey)?size}">${sdms.providerTitle}</th>
                            <td rowspan="${detailBox.sdmsRDetailMap.get(detailKey)?size}" class="child-th">${sdms.subDimensionTitle}</td>
                            <td>1</td>
                            <td>${detailBox.sdmsRDetailMap.get(detailKey)[0].affairWhen!}</td>
                            <td>${detailBox.sdmsRDetailMap.get(detailKey)[0].affairWhere!}</td>
                            <td>${detailBox.sdmsRDetailMap.get(detailKey)[0].affairWhat!}</td>
                            <td>${detailBox.sdmsRDetailMap.get(detailKey)[0].affairAmount!}</td>
                            <td>
                                <a class="btn mini purple tooltips" href="javascript:void (0);" onclick="loadDetail(${detailBox.sdmsRDetailMap.get(detailKey)[0].dataTypeId},${detailBox.sdmsRDetailMap.get(detailKey)[0].uFieldValue})" data-placement="bottom" data-original-title="查看详情"><i class="icon-search "></i></a>
                            </td>
                            <td rowspan="${detailBox.sdmsRDetailMap.get(detailKey)?size}">${sdms.riskScore}</td>
                        </tr>
                            <#list detailBox.sdmsRDetailMap.get(detailKey) as sdmsValue>
                            <#if sdmsValue?index gt 0>
                            <tr>
                                <td>${sdmsValue?index+1}</td>
                                <td>${sdmsValue.affairWhen!}</td>
                                <td>${sdmsValue.affairWhere!}</td>
                                <td>${sdmsValue.affairWhat!}</td>
                                <td>${sdmsValue.affairAmount!}</td>
                                <td>
                                    <a class="btn mini purple tooltips" href="javascript:void (0);" onclick="loadDetail(${sdmsValue.dataTypeId!},${sdmsValue.uFieldValue!})" data-placement="bottom" data-original-title="查看详情"><i class="icon-search "></i></a>
                                </td>

                            </tr>
                            </#if>

                            </#list>
                        </#if>
                    </#list>
                </#list>
            </#list>

                </tbody>
            </table>


        </div>
        <div class="lostPromise">
            <div class="info-title">
                <h5><span id="year"></span>年预警分趋势</h5>
                <span>从左向右柱子高度不变或降低表示失信风险在降低，反之表示失信风险在增加</span>
            </div>
            <div id="lostPromise-canvas" class="lostPromise-canvas"></div>
        </div>

    </div>

</div>
<div id="standardModal" class="modal hide fade" tabindex="-1" data-width="900"></div>
<input id="creditEntityId" type="hidden" value="${ceId!}">
<script src="${wctx}/pub/ces/cw/lostPromise-charts.js"></script>
<script src="${wctx}/pub/ces/cw/star.js"></script>
<script>
    function loadDetail(dataTypeId, uFieldValue) {
        $.nptPOST("poolDetail.action", {dataTypeId: dataTypeId, uFieldValue: uFieldValue}, function (data) {
            $("#standardModal").modal("show").html(data);
        });
    }
    $(function(){
        App.handleTooltips();
    });
    /**
     * 显示排序按钮
     */
    function showMoveBtn(obj){
        var btns=' <a href="javascript:void(0)" class="btn tooltips blue up mini"  data-placement="bottom" data-original-title="上移" ><i class="icon-long-arrow-up"></i></a>' +
                ' <a href="javascript:void(0)" class="btn tooltips green down mini"  data-placement="bottom" data-original-title="下移" ><i class="icon-long-arrow-down"></i></a>' +
                ' <a href="javascript:void(0)" class="btn tooltips yellow top mini"  data-placement="bottom" data-original-title="置顶" ><i class="icon-pushpin"></i></a>';
        $(obj).after(btns);
        App.handleTooltips();
    }

    /**
     * 隐藏排序按钮
     */
    function hideMoveBtn(obj) {
        $(obj).nextAll().remove();
    }

//    给基准数据添加分层背景
    $("#baseDate_table tr").hover(function(){
        $(this).css("background-color","#f5f5f5");
    },function(){
        $(this).css("background-color","transparent");
    });
</script>
</body>
</html>