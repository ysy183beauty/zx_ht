/**
 * Created by zhanglei on 2017/2/24.
 */

// DataTables 汉化
(function(){
    var oLanguage={
        "oAria": {
            "sSortAscending": ": 升序排列",
            "sSortDescending": ": 降序排列"
        },
        "oPaginate": {
            "sFirst": "首页",
            "sLast": "末页",
            "sNext": "下一页",
            "sPrevious": "上一页"
        },
        "sEmptyTable": "没有相关记录",
        "sInfo": "第 _START_ 到 _END_ 条记录，共 _TOTAL_ 条",
        "sInfoEmpty": "第 0 到 0 条记录，共 0 条",
        "sInfoFiltered": "(从 _MAX_ 条记录中检索)",
        "sInfoPostFix": "",
        "sDecimal": "",
        "sThousands": ",",
        "sLengthMenu": "每页显示条数: _MENU_",
        "sLoadingRecords": "正在载入...",
        "sProcessing": "正在载入...",
        "sSearch": "搜索:",
        "sSearchPlaceholder": "",
        "sUrl": "",
        "sZeroRecords": "没有相关记录"
    }
    $.fn.dataTable.defaults.oLanguage=oLanguage;

    $.fn.dataTable.defaults.aLengthMenu=[10, 15, 20];
})();

// Get url parameter
var getUrlParameter = function getUrlParameter(sParam) {
    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : sParameterName[1];
        }
    }
};

//serialize form data function
function objectifyForm(formArray) {
    var returnArray = {};
    for (var i = 0; i < formArray.length; i++){
        returnArray[formArray[i]['name']] = formArray[i]['value'];
    }
    return returnArray;
}

//custom jquery timeout post method
(function ($) {
    $.nptPOST = function (url, data, success) {
        var settings = {
            type: "POST", //predefine request type to POST
            url: url,
            data: data,
            timeout: 30000,
            beforeSend: function () {
                $(".loadDiv").show();
            },
            success: success,
            error: function () {
                returnInfo(false, "操作失败！");
            },
            complete: function () {
                $(".loadDiv").hide();
            }
        };
        $.ajax(settings)
    };
})(jQuery);