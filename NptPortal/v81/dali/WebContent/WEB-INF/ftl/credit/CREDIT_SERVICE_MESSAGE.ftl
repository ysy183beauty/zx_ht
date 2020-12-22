<#include "CommonUtil.ftl"/>
<div id="xy_list">
    <div class="nav-title">
        <a onclick="list('0', 1, 10)" class="on">异议处理</a>
        <a onclick="list('1', 1, 10)" class="off">信用投诉</a>
    </div>
    <div class="con_center"></div>
</div>
<div id="xy_detail"></div>

<script>
    $(".nav-title a").bind("click",function(){
        $(this).stop().removeClass("off").addClass("on").siblings().removeClass("on").addClass("off");
    })
    function show_info(id) {
        $.ajax({
            url: "/credit/srv/objection/detail.do",
            data: {
                id: id
            },
            success: function (html) {
                $("#xy_detail").html(html);
                $("#xy_list").hide();
                $("#xy_detail").show();
            }
        })
    }
    function hide_info() {
        $("#xy_list").show();
        $("#xy_detail").hide();
    }
    // 分页
    function list(flag, pageNo, pageSize) {
        $.ajax({
            url: "/credit/srv/objection/list.do",
            data: {
                flag: flag,
                pageNo: pageNo,
                pageSize: pageSize
            },
            success: function (html) {
                $("div.con_center").html(html);
            }
        })
    }

    $(function () {
        list("0", 1, 10);
    })
</script>