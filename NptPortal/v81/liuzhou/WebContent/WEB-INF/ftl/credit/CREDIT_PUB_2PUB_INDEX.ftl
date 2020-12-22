<#include "CommonUtil.ftl"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/tableDate.css"/>
<style>
    .table-2pub thead th:nth-child(5),.table-2pub thead th:last-child{
        width:12%;
    }
    .table-2pub td{
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;

    }
</style>
    <#if NPT_ACTION_RETURNED_JSON??>
        <#assign data = NPT_ACTION_RETURNED_JSON>
        <div class="con_top">
            <div class="dqxz">当前选择：
                <#if _type == "al">
                    行政许可查询
                <#else>
                    行政处罚查询
                </#if>
            </div>
            <div class="top_con">
                <ul>
                    <li class="criteria row">
                        <#if data.textBoxes?size!=0>
                            <#list data.textBoxes as cond>
                                <div class="col-sm-6 gover">
                                    <span class="col-sm-4">${cond.title}：</span>
                                    <input type="hidden" id="textName_${cond_index}" value="${cond.name}">
                                    <input id="textValue_${cond_index}" class="focus col-sm-8" type="text" placeholder="请输入${cond.title}" value="${cond.value!}"/>
                                </div>
                            </#list>
                            <input id="textBoxCount" type="hidden" value="${data.textBoxes?size!0}">
                        </#if>
                    </li>
                    <li>
                        <input type="button" value="查询" onclick="search(1)"/>
                        <input type="button" value="重置" onclick="resetSearch()"/>
                    </li>
                </ul>
            </div>
        </div>
        <div id="pageData"  class="con-bottom">
            <div class="bot_con">
                <div class="bot_con-title">公示信息</div>
                <table class="table-striped table-hover table-2pub" border="1" width="100%" cellpadding="0" cellspacing="0" style="border-collapse:collapse;table-layout: fixed">

                    <#if data.totalCount gt 0>
                        <thead>
                            <#list data.columnTitles as title>
                                <#if title_index!=0>
                                <th>${title!}</th>
                                </#if>
                            </#list>
                            <th>数据详情</th>
                        </thead>
                    </#if>
                <tbody>
                    <#if data.dataList??>
                        <#list data.dataList as list>
                        <tr>
                            <#list list.dataArray as array>
                                <#if array_index gt 0>
                                    <td> ${array.value}</td>
                                </#if>

                            </#list>

                            <td onclick="detail(${list.dataArray[0].value})"><a>详细数据</a></td>
                        </tr>
                        </#list>
                    <#else>
                    <tr>
                        <td colspan="${data.columnTitles?size}">暂无数据</td>
                    </tr>
                    </#if>
                </tbody>
            </table>
                <div class="bot_bot">
                    <div id="kkpager" class="bot_bot kkpager"></div>
                </div>
            </div>
        </div>
    <#else >
        <div class="portlet-body">
            <table class="table table-bordered">
                <tr>
                    <th>暂无数据</th>
                </tr>
            </table>
        </div>
    </#if>

<#if NPT_ACTION_RETURNED_JSON??>
    <#assign data = NPT_ACTION_RETURNED_JSON>
<script>
    function detail(kval) {
        $.ajax({
            type:"get",
            async: false,//同步请求
            url:"/credit/pub/2pub/${_type}/detail.do",
            data:{
                primaryKeyValue : kval
            },
            success:function(html){
//                var obj = window.open("about:blank");
//                obj.document.write(html);
                window.open("/credit/pub/2pub/${_type}/detail.do?primaryKeyValue="+kval);
            }
        });
    }
    $(function () {
        //生成分页控件
        kkpager.generPageHtml({
            pagerid: "kkpager",
            pno: ${data.currPage},
            mode : 'click',
            //总页码
            total: Math.ceil(${data.totalCount/data.pageSize}),
            //总数据条数
            totalRecords: ${data.totalCount},
            //链接算法
            click: function (n) {
                //获取第n页数据
                search(n);
            }
        },true);
    });

    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 分页查询
     */
    function catchObj() {
        var textBoxCount = $("#textBoxCount").val();
        var obj = [];
        for (var i = 0; i < textBoxCount; i++) {
            var textObj = {};
            textObj.name = $("#textName_" + i).val();
            textObj.value = $("#textValue_" + i).val();
            obj.push(textObj);
        }
        var data = new Object();
        data.queryCondition = obj;
        return JSON.stringify(data);
    }
    function search(n) {
        $.ajax({
            url: "/credit/pub/2pub/${_type}/list.do",
            data: {
                webInvokeCondition: catchObj(),
                currPage:n,
                pageSize:10
            },
            timeout: 30000,
            beforeSend:function(){
                $(".load_main").show();
            },
            success: function (html) {
                $('#pageData').html(html);
                $(".load_main").hide();
            },
            error:function () {
                $(".loadDiv").hide();
//                returnInfo(false,"操作失败！");
            }
        });
    }
    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 重置搜索框
     */
    function resetSearch() {
    <#list data.textBoxes as cond>
        $("input[name='${cond.name}']").val("");
    </#list>
    }
</script>
</#if>