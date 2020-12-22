$("#star").on("click", function () {
    var _this = this;
    if($(_this).hasClass("icon-star-empty")){
        $.nptPOST("star.action", {
            creditEntityId: $("#creditEntityId").val(),
            creditEntityType: 15
        }, function (data) {
            if (data.result){
                returnInfo(true,data.message||"关注成功！");
                $(_this).removeClass("icon-star-empty");
                $(_this).addClass("icon-star");
                $(_this).attr("data-original-title","取消关注");
            }else {
                returnInfo(false,data.message||"关注失败！");
            }
        });
    } else {
        $.nptPOST("unStar.action", {
            creditEntityId: $("#creditEntityId").val(),
            creditEntityType: 15
        }, function (data) {
            if (data.result){
                returnInfo(true,data.message||"取消关注成功！");
                $(_this).removeClass("icon-star");
                $(_this).addClass("icon-star-empty");
                $(_this).attr("data-original-title","关注");
            }else {
                returnInfo(false,data.message||"取消关注失败！");
            }
        });
    }
});