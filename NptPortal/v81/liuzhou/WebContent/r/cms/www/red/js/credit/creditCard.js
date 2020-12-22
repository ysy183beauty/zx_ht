function follow(img) {
    $.ajax({
        url: "follow.do",
        type: "post",
        data: {
            toUserId: img.alt
        },
        beforeSend: function () {

        },
        success: function (data) {
            if (data === "RST_SUCCESS") {
                img.src = "/r/cms/www/red/img/lz/xymp/yplus.jpg";
                $(img).attr("onclick", "unFollow(this)");
                $(img).attr("title", "点击，取消关注");
            } else {

            }
        },
        error: function () {

        }
    });
}

function unFollow(img) {
    $.ajax({
        url: "unFollow.do",
        type: "post",
        data: {
            toUserId: img.alt
        },
        beforeSend: function () {

        },
        success: function (data) {
            if (data === "RST_SUCCESS") {
                img.src = "/r/cms/www/red/img/lz/xymp/plus.jpg";
                $(img).attr("onclick", "follow(this)");
                $(img).attr("title", "点击，添加关注");
            } else {

            }
        },
        error: function () {

        }
    });
}