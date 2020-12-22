<#include "./common.ftl">
<link rel="stylesheet" type="text/css" href="${wctx}/pub/CreditStyle/pagination/pagination.css" />
<script type="text/javascript" src="${wctx}/pub/CreditStyle/pagination/jquery.pagination.js"></script>
<script type="text/javascript">
    var pagination = window.pagination || {};

    pagination.url = "";
    pagination.callback = null;
    pagination.param = {};

    /**
     * 初始化分页
     * @param url 分页数据获取url（必选）
     * @param callback 分页初始化后的回调（可选）
     */
    pagination.initPagination = function(url, param, callback){
        console.log(param);
        console.log(url);
        if(typeof param == "object"){
            pagination.param = param;
        }else if($.isFunction(param)){
            callback = param;
            param = undefined;
        }
        pagination.callback = callback;
        pagination.url = url;

        var pageNo = pagination.param.currPage || 1;

        var totalCount = $("#totalCount").val()|| 0;
        totalCount = totalCount.replace(/,/g, "");
        var totalPage = $("#totalPage").val();
        totalPage = totalPage.replace(/,/g, "");
        var pageSize = $("#pageSize").val();
        pageSize = pageSize.replace(/,/g, "");

        if(pageNo > totalPage && totalPage > 0){
            pagination.refreshPage(totalPage, callback);
            return;
        }
        $("#pagination").pagination(totalCount, {
            callback: pagination.selectCallback,
            prev_text: "← 上一页",
            next_text: "下一页 →",
            items_per_page:pageSize,// 每页数量
            num_edge_entries: 2,//两侧首尾分页条目数
            num_display_entries: 8,//连续分页主体部分分页条目数
            current_page:pageNo-1//当前页索引
        });
        if($.isFunction(pagination.callback)){
            pagination.callback();
        }
    }
    /**
     * 刷新分页
     * @param pageNo 刷新页数（可选，默认刷新当前页）
     * @param searchParam 搜索条件参数（可选）
     */
    pagination.refreshPage = function(pageNo, searchParam){
        if(typeof pageNo == "object"){
            searchParam = pageNo;
            pageNo = undefined;
        }
        pageNo = pageNo || pagination.param.currPage;
        if(searchParam){
            pagination.param = searchParam;
        }

        pagination.param.currPage = pageNo || 1;

//		var waitting = $waiting('操作进行中...');
        $.ajax({
            url: pagination.url,
            async: false,
            type:"post",
            data: pagination.param,
            success: function(result,code,dd) {

                $("#pageData").html(result);
                pagination.initPagination(pagination.url, pagination.param);
//				waitting.close();
            },
            error:function(){
//				waitting.close();
//				$alert("操作失败");
            }
        });
        return false;
    }
    pagination.selectCallback = function (pageIndex, jq){
        pagination.refreshPage(pageIndex+1);
        return true;
    }
</script>