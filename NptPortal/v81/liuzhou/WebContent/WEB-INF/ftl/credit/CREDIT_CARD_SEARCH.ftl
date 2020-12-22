<#include "CommonUtil.ftl">

<#include "include/macro.ftl">


    <div class="searchContent">
        <div class="follow-title">查询结果</div>
        <ul>
            <@cardHotList dataList = paginationData.results/>
        </ul>
    </div>
    <div class="bot_bot">
        <div id="kkpager" class="bot_bot kkpager"></div>
    </div>
<script>
    $(function(){
        $("#searchBox li").addClass("col-xs-6");
//        var value= $("#queryInput").val();
        //生成分页控件
        kkpager.generPageHtml({
            pagerid: "kkpager",
            pno: ${paginationData.currentPage},
            mode : 'click',
            //总页码
            total: Math.ceil(${paginationData.totalCount/paginationData.pageSize}),
            //总数据条数
            totalRecords: ${paginationData.totalCount},
            //链接算法
            click: function (n) {
                //获取第n页数据
                page(n);
            }
        },true);
    });
    function page(n){
        $.ajax({
            url: "search.do",
            data: {
                key:keyWord,
                currentPage:n,
                pageSize: 6
            },
            beforeSend: function () {

            },
            success: function (data) {
                $("#searchBox").html(data);
            },
            error: function () {

            }
        });
    }
</script>