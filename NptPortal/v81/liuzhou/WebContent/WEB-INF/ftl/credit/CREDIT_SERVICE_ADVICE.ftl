<#include "CommonUtil.ftl"/>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1"/>
    <title>信用问答</title>
<#include "include/header_link.ftl"/>
    <link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xyfw.css"/>
</head>
<body>
<div id="show_con">
    <div class="con_top_shu">
        <h2>
            <img src="${ctx}/r/cms/www/red/img/xyfw/titleLine-left.png" alt="">
            信用问答
            <img src="${ctx}/r/cms/www/red/img/xyfw/titleLine-right.png" alt="">
        </h2>
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
                <textarea name="text" style="width:100%;height:500px;" placeholder="请耐心些，尽量详细描述你的问题！"></textarea>
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
                    creditService("advice");
                }
                else{
                    alert("发出声音失败！");
                }
            }
        })
    });
</script>
</html>