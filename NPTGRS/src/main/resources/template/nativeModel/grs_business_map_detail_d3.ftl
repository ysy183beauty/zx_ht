<!DOCTYPE html>
<html>
<head>
<#include "/template/Credit_Common/c_pagination.ftl">
<style>
    #mapGraph {
        width: 66%;
        height: 600px;

        border: 1px solid #ccc;
        position: relative;
    }
    .grid {
        stroke: black;
        stroke-width: 1px;
        fill: red;
    }
    .arc {
        stroke: black;
        fill: none;
    }
    .node {
        /*fill: lightgray;*/
        /*stroke: black;*/
        stroke-width: 1px;
    }
    .node polyline{
        fill: lightgray;
    }
    circle.active {
        fill: red;
    }
    path.active {
        stroke: red;
    }

    .company{
        stroke: #1f77b4;
        cursor:pointer;
    }
    .relationship{
        stroke: #2ca02c;
        cursor:pointer;
    }
    .person{
        stroke: #ff7f0e;
        cursor:pointer;
    }

</style>
</head>
<body class="page-header-fixed">
<div id="creditModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h3 id="myModalLabel">Modal header</h3>
    </div>
    <div class="modal-body">
        <p>One fine body…</p>
    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
        <button class="btn btn-primary">Save changes</button>
    </div>
</div>
<div id="d3-top">
    <#--<div class="tab span3">-->

            <#--<table class="table table-striped table-bordered table-hover">-->
                <#--<tr>-->
                    <#--<td></td>-->
                    <#--<td></td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<td></td>-->
                    <#--<td></td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<td></td>-->
                    <#--<td></td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<td></td>-->
                    <#--<td></td>-->
                <#--</tr>-->
                <#--<tr>-->
                    <#--<td></td>-->
                    <#--<td></td>-->
                <#--</tr>-->
            <#--</table>-->

        <#--<div class="d3-name">概要信息</div>-->
    <#--</div>-->

    <div id="sx" class="sx span3">
        <div class="bubbleChart"></div>
        <div class="d3-name">失信统计(总计${badStsCount!0}条)</div>
    </div>
        <div class="xypj span3">
            <span id="speedometer"></span>
            <div class="d3-name">信用评分</div>
            <div class="score">
                <img src="${wctx}/pub/CreditStyle/d3/v3/img/${creditGrade}.png" alt="">
            </div>
        </div>
    <div id="cx" class="cx span3">
        <div class="bubbleChart"></div>
        <div class="d3-name">荣誉统计(总计${goodStsCount!0}条)</div>
    </div>

    <div class="clear"></div>
</div>
<div style="position:relative;margin-top:40px;">
    <div id="mapGraph">
        <svg></svg>
    </div>
    <div id="canvas-info" class="canvasInfo ">

    </div>
</div>

  <#--失信统计图-->
<script>

//    $("#sx").ready(function () {
    <#if badSts?? && badSts != "[]">
//    $(document).ready(function () {
        var sx = new d3.svg.BubbleChart({
            supportResponsive: true,
            container:"#sx div.bubbleChart",
            size: 600,
            innerRadius: 600 / 3.5,
            radiusMin: 50,
            data: {
                items: ${badSts},
                eval: function (item) {return item.count;},
                classed: function (item) {return item.text.split(" ").join("");}
            },
            plugins: [
                {
                    name: "central-click",
                    options: {
                        text: "",
                        style: {
                            "font-size": "16px",
                            "font-style": "italic",
                            "font-family": "Source Sans Pro, sans-serif",
                            //"font-weight": "700",
                            "text-anchor": "middle",
                            "fill": "white"
                        },
                        attr: {dy: "65px"},
                        centralClick: function() {
                            console.log("Here is more details!!");
                        }
                    }
                },
                {
                    name: "lines",
                    options: {
                        format: [
                            {// Line #0
                                textField: "count",
                                classed: {count: true},
                                style: {
                                    "font-size": "28px",
                                    "font-family": "Source Sans Pro, sans-serif",
                                    "text-anchor": "middle",
                                    fill: "white"
                                },
                                attr: {
                                    dy: "0px",
                                    x: function (d) {return d.cx;},
                                    y: function (d) {return d.cy;}
                                }
                            },
                            {// Line #1
                                textField: "text",
                                classed: {text: true},
                                style: {
                                    "font-size": "14px",
                                    "font-family": "Source Sans Pro, sans-serif",
                                    "text-anchor": "middle",
                                    fill: "white"
                                },
                                attr: {
                                    dy: "20px",
                                    x: function (d) {return d.cx;},
                                    y: function (d) {return d.cy;}
                                }
                            }
                        ],
                        centralFormat: [
                            {// Line #0
                                style: {"font-size": "50px"},
                                attr: {}
                            },
                            {// Line #1
                                style: {"font-size": "30px"},
                                attr: {dy: "40px"}
                            }
                        ]
                    }
                }]
        });

        sx.moveToCentral(d3.select("#sx .node"));
//    });
    <#else>
    $("#sx div.bubbleChart").html("<img src='${wctx}/pub/CreditStyle/d3/v3/img/zwsj.png' style='width:100%;height:100%;'>");
    </#if>
</script>
<#--荣誉统计图-->
<script>

//    $("#cx").ready(function () {
    <#if goodSts?? && goodSts != "[]">
//    $(document).ready(function () {
        var cx = new d3.svg.BubbleChart({
            supportResponsive: true,
            container:"#cx div.bubbleChart",
            size: 600,
            innerRadius: 600 / 3.5,
            radiusMin: 50,
            data: {
                items: ${goodSts},
                eval: function (item) {return item.count;},
                classed: function (item) {return item.text.split(" ").join("");}
            },
            plugins: [
                {
                    name: "central-click",
                    options: {
                        text: "",
                        style: {
                            "font-size": "16px",
                            "font-style": "italic",
                            "font-family": "Source Sans Pro, sans-serif",
                            //"font-weight": "700",
                            "text-anchor": "middle",
                            "fill": "white"
                        },
                        attr: {dy: "65px"},
                        centralClick: function() {
                            console.log("Here is more details!!");
                        }
                    }
                },
                {
                    name: "lines",
                    options: {
                        format: [
                            {// Line #0
                                textField: "count",
                                classed: {count: true},
                                style: {
                                    "font-size": "28px",
                                    "font-family": "Source Sans Pro, sans-serif",
                                    "text-anchor": "middle",
                                    fill: "white"
                                },
                                attr: {
                                    dy: "0px",
                                    x: function (d) {return d.cx;},
                                    y: function (d) {return d.cy;}
                                }
                            },
                            {// Line #1
                                textField: "text",
                                classed: {text: true},
                                style: {
                                    "font-size": "14px",
                                    "font-family": "Source Sans Pro, sans-serif",
                                    "text-anchor": "middle",
                                    fill: "white"
                                },
                                attr: {
                                    dy: "20px",
                                    x: function (d) {return d.cx;},
                                    y: function (d) {return d.cy;}
                                }
                            }
                        ],
                        centralFormat: [
                            {// Line #0
                                style: {"font-size": "50px"},
                                attr: {}
                            },
                            {// Line #1
                                style: {"font-size": "30px"},
                                attr: {dy: "40px"}
                            }
                        ]
                    }
                }]
        });

        cx.moveToCentral(d3.select("#cx .node"));
//    });
    <#else>
    $("#cx div.bubbleChart").html("<img src='${wctx}/pub/CreditStyle/d3/v3/img/zwsj.png' style='width:100%;height:100%;'>");
    </#if>
</script>
<#--仪表图-->
<script>
    var win=$(".span3").width();
    var scale = win/408;
    var svg = d3.select("#speedometer")
            .append("svg:svg")
            .attr("preserveAspectRatio", "xMinYMin meet")
            .attr("viewBox", "0 0 " + win + " " + win/1.5)
            .append("g")
            .attr("transform", "scale("+scale+") translate("+(win/scale-339.4)/2+", "+(win/1.5/scale-272)/2+")");


    var gauge = iopctrl.arcslider()
            .radius(120)
            .events(false)
            .indicator(iopctrl.defaultGaugeIndicator);
    gauge.axis().orient("in")
            .normalize(true)
            .ticks(12)
            .tickSubdivide(3)
            .tickSize(10, 8, 10)
            .tickPadding(5)
            .scale(d3.scale.linear()
                    .domain([0, 800])
                    .range([-3*Math.PI/4, 3*Math.PI/4]));

    var segDisplay = iopctrl.segdisplay()
            .width(80)
            .digitCount(6)
            .negative(false)
            .decimals(0);

    svg.append("g")
            .attr("class", "segdisplay")
            .attr("transform", "translate(130, 200)")
            .call(segDisplay);

    svg.append("g")
            .attr("class", "gauge")
            .call(gauge);

//    svg.select(".gauge").attr("transform","translate(-35,-20)");

    segDisplay.value(${creditScore!0});
    gauge.value(${creditScore!0});
</script>
  <#--高度自适应-->
<script>
    $(function(){
        var win=$(".span3").width();
        $('.span3').height(win/1.5);
    });
    $(window).resize(function() {
        var win=$(".span3").width();
        $('.span3').height(win/1.5);
    });
//    var p=0,t;
//    var iheight= $("#canvas").height();
//    $(window).resize(function(){
//        var top=$('#canvas').offset().top;
//        t=top-42;
//        iheight= $("#canvas").height();
//            var p=$(window).scrollTop();
//            if(p>t){
//                var h=p-t;
//                $("#canvas-info").height(iheight-h);
//            }
//            else{
//                $("#canvas-info").height(iheight);
//            }
//    });
//    $(window).scroll(function () {
//    //$(window).scrollTop()这个方法是当前滚动条滚动的距离
//    //$(window).height()获取当前窗体的高度
//    //$(document).height()获取当前文档的高度
//        var top=$('#canvas').offset().top;
//        t=top-42;
//        var p=$(window).scrollTop();
//        if(p>t){
//            var h=p-t;
//            $("#canvas-info").height(iheight-h);
//        }
//        else{
//            $("#canvas-info").height(iheight);
//        }
//    });
</script>
<#--关系图-->
<script>
    var data = {
        "nodes": ${uiNodes!},
        "links": ${uiLinks!}
    };
    var mapGraph = {
        width: $("#mapGraph").width(),
        height: $("#mapGraph").height()
    };

    dataViz(data.nodes, data.links);

    function dataViz(nodes, edges) {

        var nodeHash = {};
        var nodeWeightHash = [];
        for (x in nodes) {
            nodeHash[nodes[x].id] = nodes[x];
            if(nodeWeightHash.indexOf(nodes[x].weight) == -1){
                nodeWeightHash.push(nodes[x].weight);
            }
        }
        nodeWeightHash.sort(function (a, b) {
            return a - b;
        });

        for (x in edges) {
            edges[x].weight = parseInt(edges[x].weight);
            edges[x].source = nodeHash[edges[x].source];
            edges[x].target = nodeHash[edges[x].target];
        }
        var weightScale = d3.scale.linear()
                .domain(d3.extent(edges, function(d) {return d.weight;}))
                .range([.1,1]);
        var depthScale = d3.scale.category10(nodeWeightHash);
        var force = d3.layout.force()
                .charge(-1500)
                .gravity(0.3)
        .size([mapGraph.width, mapGraph.height])
                .nodes(nodes)
                .links(edges)
                .on("tick", forceTick);

        force.linkStrength(function (d) {return weightScale(d.weight);});

        var baseSvg = d3.select("#mapGraph svg")
                .attr("preserveAspectRatio", "xMinYMin meet")
        .attr("viewBox", "0 0 " + mapGraph.width + " " + mapGraph.height)
        .style("width", mapGraph.width)
        .style("height", mapGraph.height)
                .append("g");
        baseSvg
                .selectAll("line.link")
                .data(edges, function (d) {return d.source.id + "-" + d.target.id;})
                .enter()
                .append("line")
                .attr("class", "link")
                .style("stroke", "black")
                .style("opacity", .5)
                .style("stroke-width", function(d) {return "1px"});

        var nodeEnter = baseSvg.selectAll("g.node")
                .data(nodes, function (d) {return d.id})
                .enter()
                .append("g")
                .attr("class", function (d) {
            return "node " + d.dataHost + (d.weight===15?" root":"");
                });

        var tooltip = d3.select("body").append("div")
                .attr("class", "tooltip")
                .style("z-index", "10")
                .style("position", "absolute")
                .style("visibility", "hidden")
                .style("background-color", "yellow");


        d3.html("${wctx}/pub/CreditStyle/d3/svg/noun_694252_cc.svg", function (err, data) {
            nodeEnter.each(function (d) {
                var _this = this;
            if(d.dataHost === "company"){
                    d3.select(data).selectAll("path")
                            .attr("transform", "scale(0.6) translate(-40,-70)")
                            .each(function (datum, index, outerIndex) {
                                _this.appendChild(this.cloneNode(true));
                            })
                }
            })
        });
        nodeEnter.each(function (d) {
        if(d.dataHost === "relationship"){
                d3.select(this).append("polyline")
                .attr("points", "-40,0 0,-14 40,0 0,14 -40,0")
                        .attr("stroke", "black")
                        .attr("stroke-width", 1)
            }
        });
        d3.html("${wctx}/pub/CreditStyle/d3/svg/noun_920636_cc.svg", function (err, data) {
            nodeEnter.each(function (d) {
                var _this = this;
            if(d.dataHost === "person"){
                    d3.select(data).selectAll("path")
                            .attr("transform", "scale(2) translate(-16,-20)")
                            .each(function (datum, index, outerIndex) {
                                _this.appendChild(this.cloneNode(true));
                            })
                }
            })
        });

        var colorScale = d3.scale.category10(["person", "company", "relationship"]);
        nodeEnter.attr("stroke", function (d) {
        return colorScale(d.dataHost);
        });


        nodeEnter.append("text")
                .style("text-anchor", "middle")
                .style("font-size", "12px")
                .attr("y", function (d) {
                    if (d.dataHost === "company") {
                return 24;
                    } else if (d.dataHost === "relationship") {
                        return 6;
                    } else if (d.dataHost === "person") {
                        return 26;
                    } else {
                        return 6;
                    }
                })
                .text(function(d) {return d.label;});


        nodeEnter
                .filter(function (d) {
                    return d.label.indexOf("公司") != -1 && d.weight != 7;
                })
                .on("mouseover", function (d) {
                    d3.selectAll("line.link").filter(function (a) {
                        return a.source.id == d.id || a.target.id == d.id;
                    }).style("stroke", "red");
                    tooltip.html("<table><tbody>" +
                            "<tr><td>公司名称</td><td>"+ d.label +"</td></tr>" +
                            "<tr><td>组织机构代码</td><td></td></tr>" +
                            "<tr><td>注册地址</td><td></td></tr>" +
                            "<tr><td>经营范围</td><td></td></tr>" +
                            "<tr><td>注册资金</td><td></td></tr>" +
                            "</tbody></table>")
                            .style("visibility", "visible");
                })
                .on("mousemove", function () {
                    return tooltip.style("top", (event.pageY - 10) + "px").style("left", (event.pageX + 10) + "px");
                })
                .on("mouseout", function () {
                    d3.selectAll("line.link").style("stroke", "black");
                    tooltip.style("visibility", "hidden");
                });


        var n = nodes.length;
        nodes.forEach(function(d, i) {
            d.x = d.y = 1000 / n * i;
        });

        force.start();
        for (var i = 0; i < 100; ++i) force.tick();
        force.stop();

        function forceTick() {
            baseSvg.selectAll("line.link")
                    .attr("x1", function (d) {
                        return d.source.x;
                    })
                    .attr("x2", function (d) {
                        return d.target.x;
                    })
                    .attr("y1", function (d) {
                        return d.source.y;
                    })
                    .attr("y2", function (d) {
                        return d.target.y;
                    });

            baseSvg.selectAll("g.node")
                    .attr("transform", function (d) {
                        return "translate(" + d.x + "," + d.y + ")";
                    });
        }


        var drag = force.drag()
                .on("dragstart", dragstart);
        baseSvg.selectAll("g.node").on("dblclick", dblclick)
                .call(drag);
        function dblclick(d) {
            d3.select(this).classed("fixed", d.fixed = false);
        }
        function dragstart(d) {
            d3.select(this).classed("fixed", d.fixed = true);
        }

        function zoom() {
            baseSvg.attr("transform", "translate(" + d3.event.translate + ")scale(" + d3.event.scale + ")");
        }
        var zoomListener = d3.behavior.zoom().scaleExtent([0.1, 3]).on("zoom", zoom);
        // baseSvg.call(zoomListener);

        d3.select("#controls").append("button")
                .on("click", centerNode).html("Center Node");
        function centerNode() {
            // scale = zoomListener.scale();
        x = mapGraph.width/2 - baseSvg.select(".root").data()[0].x;
        y = mapGraph.height/2 - baseSvg.select(".root").data()[0].y;

            baseSvg.transition()
                    .attr("transform", "translate(" + x + "," + y + ")");
            // zoomListener.scale(scale);
            // zoomListener.translate([x, y]);

        }
    centerNode();

        nodeEnter.filter(function (d) {
            return d.id == "13";
        }).on("click", addNodesAndEdges);
        function addNodesAndEdges(d) {

            force.stop();
            var oldEdges = force.links();
            var oldNodes = force.nodes();

            var newNode1 = {id: "raj", weight: 5, label: "张三"};
            var newNode2 = {id: "wu", weight: 5, label: "李四"};
            var newEdge1 = {source: d, target: newNode1, weight: 3};
            var newEdge2 = {source: d, target: newNode2, weight: 3};
            oldEdges.push(newEdge1,newEdge2);
            oldNodes.push(newNode1,newNode2);
            force.links(oldEdges).nodes(oldNodes);
            baseSvg.selectAll("line.link")
                    .data(oldEdges, function(d) {
                        return d.source.id + "-" + d.target.id
                    })
                    .enter()
                    .insert("line", "g.node")
                    .attr("class", "link")
                    .style("stroke", "black")
                    .style("stroke-width", "1px");

            var nodeEnter = baseSvg.selectAll("g.node")
                    .data(oldNodes, function (d) {
                        return d.label;
                    }).enter()
                    .append("g")
                    .attr("class", "node")
                    .call(force.drag());
            nodeEnter.append("circle")
                    .attr("r", function (d) {
                        return d.weight * 3;
                    })
                    .style("fill", function (d) {
                        return depthScale(d.weight);
                    })
                    .style("stroke", "black")
                    .style("stroke-width", "1px");
            nodeEnter.append("text")
                    .style("text-anchor", "middle")
                    .style("font-size", "12px")
                    .attr("y", 6)
                    .text(function(d) {return d.label;});
            force.start();
            // for (var i = 0; i < 100; ++i) force.tick();
            // force.stop();
        }

        nodeEnter.filter(function (d) {
            return d.dataHost !== "relationship";
    })
        .append("circle")
        .attr("cx", 0)
        .attr("cy", 0)
        .attr("r", 30)
        .attr("stroke", "none")
        .attr("fill", "none")
        .style("pointer-events", "visible")
        .on("click", openOrFold);
//        自动单击根节点
        baseSvg.select("g.root").each(openOrFold);
    }

    function openOrFold(data) {
        $.ajax({
            url: "nodeDetail.${urlExt}",
            data: {
                poolId: data.poolId,
                ukValue: data.ukValue
            },
            timeout: 30000,
            beforeSend: function () {
                $(".loadDiv").show();
            },
            success: function (msg) {
                $(".loadDiv").hide();
                $("#canvas-info").html(msg).addClass("info-active");
            },
            error: function () {
                $(".loadDiv").hide();
                returnInfo(false, "操作失败！");
            }
        });
    }
</script>

</body>
<!-- END BODY -->
</html>