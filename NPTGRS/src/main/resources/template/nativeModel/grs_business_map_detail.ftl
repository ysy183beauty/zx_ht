<#include "/template/Credit_Common/common.ftl">

<!doctype html>
<html>
<head>

<#include "/template/Credit_Common/c_pagination.ftl">
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/echarts/echarts.js"></script>
</head>
<body class="page-header-fixed">

 <div id="mapGraph" style="width:100%;height:800px;margin:0 auto;">

 </div>

<!-------------------------------------专题信用信息查询-------------------------------------------->

<script>

    require.config({
        paths : {
            echarts : '${wctx}/pub/CreditStyle/echarts'
        }
    });

    var myChart;
    var themes = ["default","macarons","macarons2","infographic","shine","helianthus"];
    require([ "echarts", "echarts/chart/force"], function(ec) {
        var themeIdx = Math.floor((Math.random()*themes.length));
        myChart = ec.init(document.getElementById('mapGraph'), themes[themeIdx]);

        var option = {

            title : {
                text: '企业图谱',
                subtext: '神州数码',
                x:'left',
                y:'top',
                textStyle:{
                    color:'#6f4c90'
                }
            },
            toolbox: {
                show : true,
                feature : {
                    restore : {show: true},
                    magicType: {show: false, type: ['force', 'chord']},
                    saveAsImage : {show: true}
                }
            },
            series: [
                {
                    type:'force',
                    name : "人物关系",
                    ribbonType: false,
                    itemStyle: {
                        normal: {
                            label: {
                                show: true,
                                textStyle: {
                                    color: "#333"
                                }
                            },
                            nodeStyle: {
                                brushType: 'both',
                                borderColor: 'rgba(255,215,0,0.4)',
                                borderWidth: 5
                            },
                            linkStyle: {
                                type: 'line',
                                textPosition:'inside'
                            }
                        }
                    },
                    categories: [
                        {name: '公司'},
                        {name: '结构'},
                        {name: '代表'}
                    ],
                    useWorker: false,
                    minRadius : 15,
                    maxRadius : 25,
                    gravity: 1.1,
                    scaling: 1.1,
                    roam: 'move',
                    nodes:
                               ${uiNodes!},
                    links:
                               ${uiLinks!}

                }
            ]
        };

        myChart.setOption(option);
        $("#canvas").resize(function(){
                myChart.resize();
        });
        function openOrFold(param) {
            console.log("这是单击");
//            var option = myChart.getOption();
//            var nodesOption = option.series[0].nodes;
//            var linksOption = option.series[0].links;
            var data = param.data;
//            var linksNodes = [];
            $(".loadDiv").show();
            $.ajax({
                url:"/npt/grs/query/bsmap/nodeDetail.action?poolId="+data.poolId+"&&ukValue="+data.ukValue,
                timeout:20000,
                success:function(msg){
                    $("#canvas-info").html(msg).addClass("info-active");
                    $("#canvas").stop().addClass("can-active");

                    $(".loadDiv").hide();
                },
                error:function(){
                    console.log("请求失败！");
                    $(".loadDiv").hide();
                }
            });
//            window.open("/npt/grs/query/bsmap/nodeDetail.action?poolId="+data.poolId+"&&ukValue="+data.ukValue);
        }
        myChart.on("click", openOrFold);
    });


</script>
</body>
<!-- END BODY -->

</html>