<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>信用咨询</title>
<#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xyfw.css"/>
</head>
<body>
<div id="show_con">
    <div class="con_top_shu">
        <h2>信用咨询</h2>
        <div class="error">
            <a onclick="show()"><img src="${ctx}/r/cms/www/red/img/xyfw/u71.png" alt=""/>发出声音</a>
        </div>
        <div id="adviceList" class="con_center"></div>
    </div>
</div>
<div id="hide_con">
    <div class="con_top_shu">
        <h2>发出声音</h2>
        <div class="con_center">
            <form>
            <td>
                <textarea name="text" cols="80" rows="20" placeholder="请耐心些，尽量详细描述你的问题！"></textarea>
            </td>
            <div class="button">
                <input class="btn" type="button" id="btnSubmit" value="发布"/>
                <input class="btn" type="button" value="取消" onclick="hide()"/>
            </div>
            </form>
        </div>

    </div>
</div>

</body>
<script>
    $(function () {
        list(1, 10);
    });
    function list(pageNo, pageSize) {
        $.ajax({
            url: "/credit/srv/advice/list.do?timestamp="+new Date().getTime(),
            data: {
                pageNo: pageNo,
                pageSize: pageSize
            },
            success: function (html) {
                $("#adviceList").html(html);
            }
        })
    }
    function show() {
        $("#show_con").hide();
        $("#hide_con").show();
    }
    function hide() {
        $("#show_con").show();
        $("#hide_con").hide();
    }
    $("#btnSubmit").click(function () {
        $.ajax({
            type: "POST",
            url: "/credit/srv/advice/add.do",
            data: $("form").serializeArray(),
            success: function (msg) {
                if (msg == "success") {
                    creditService($(".search_list .active a").attr("name"));
                }
                else{
                    alert("发出声音失败！");
                }
            }
        })
    });
</script>
</html>