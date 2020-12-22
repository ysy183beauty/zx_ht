// 关键字
var keyWord = "诚信建设,双公示,红黑榜,征信系统";
// 摘要
var summary = "加快推进全市信用信息公共平台建设，推进建立覆盖全市的征信系统，坚决打赢创建全国闻名城市建设攻坚战。";
// 标题
var title_eq = "坚决打赢创建全国文明城市诚信建设攻坚战";
var title_like = "李国胜：坚决打赢创建全国文明城市诚信建设攻坚战";
var content_message = "标题\""+title_eq+"\" 与 \""+title_like+"\" 相似<br>内容与 <a href='/tzgg/318.jhtml' target='_blank'>/tzgg/318.jhtml</a> 相似";


// 自动提取关键词
$(function () {
    $("input[name='title']").prop("onchange", "");
    $("#tr-tagStr td:nth-child(2)").append('<label><input type="checkbox" onclick="createKeyWord()">智能关键字</label>')
});
function createKeyWord() {
    $("input[name='tagStr']").val(keyWord);
}

// 自动提取摘要
$(function () {
    $("#tr-description td:nth-child(2)").append('<label><input type="checkbox" onclick="createSummary()">智能摘要</label>')
});
function createSummary() {
    $("textarea[name='description']").html(summary);
}

// 自动查重
$(function () {
    $("#jvForm").submit(function (event) {
        event.preventDefault();
        if ($("input[name='title']").val() == title_eq) {
            alertify.set({ labels: {
                ok     : "继续提交",
                cancel : "返回修改"
            } });
            alertify.confirm(content_message, function (e) {
                if (e) {
                    // user clicked "ok"
                    $("#jvForm")[0].submit();
                } else {
                    // user clicked "cancel"
                }
            })
        } else {
            $("#jvForm")[0].submit();
        }
    })
});

// 自动关联相关文档
$(function () {
    $("#docfileupload").after('<label><input type="checkbox" onclick="createDoc(this)">关联文档</label>')
        // .after('<label><input type="checkbox" onclick="createContent()">智能纠正</label>');
});
function createDoc(target) {
    if($(target).prop("checked")){
        alertify.set({ labels: {
            ok     : "确定",
            cancel : "取消"
        } });
        alertify.confirm("找到相关文档《2016年江苏省双公示信息.doc》，是否关联", function (e) {
            if (e) {
                // user clicked "ok"
            } else {
                // user clicked "cancel"
            }
        })
    }
}
function createContent() {
    var editor= UE.getEditor('txt');
    editor.setContent('<p>\
    aoe<span style="border: 1px solid rgb(0, 0, 0); background-color: rgb(227, 108, 9);">uao</span><strong>eu</strong><strong>&lt;auoeu&gt;</strong>\
        </p>');
}



