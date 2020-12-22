<#include "CommonUtil.ftl"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/xzxk.css"/>
<link rel="stylesheet" href="${ctx}/r/cms/www/red/css/tableDate.css"/>
<#if _RESULT.code == 0>
    <#assign data =NPT_ACTION_RETURNED_JSON>
<div class="con_top">
    <div class="top_con">
        <ul>
            <li class="criteria row">
                <#list data.webQueryCondition as cond>
                    <div class="col-sm-10 row gover">
                        <span class="col-xs-4">${cond.fieldTitle}：</span>
                        <input class="col-xs-8" type="text" name="${cond.fieldDBName}" placeholder="请输入${cond.fieldTitle}" value="${cond.fieldQueryValue!}"/>
                    </div>
                </#list>
               <!-- <div class="col-sm-10 row gover">
                    <span class="col-xs-4">图片验证码：</span>
                    <input class="focus col-xs-5" id="captcha" type="text" name="验证码" placeholder="请输入验证码" />
                    <img style="cursor: pointer;height:34px;border:1px solid #1AB394;" onclick="this.src='/captcha.svl?d='+new Date()" alt="" src="/captcha.svl" class="focus col-xs-3">
                </div>-->
            </li>
            <li class="row">
                <input class="col-xs-4" type="button" value="查询" onclick="search(1)"/>
                <input class="col-xs-4" type="button" value="重置" onclick="resetSearch()"/>
            </li>
        </ul>
    </div>
</div>
<div class="con-bottom">
    <div class="bot_con">
        <table class="table-hover table-striped" border="1" width="100%" cellpadding="0" cellspacing="0" style="text-align: left;border-collapse:collapse">
            <thead>
                <#list data.columnTitles as title>
                    <#if title_index gt 0>
                    <th>${title}</th>
                    </#if>
                </#list>
            </thead>
            <tbody>
                <#if data.dataList??>
                    <#list data.dataList as list>
                    <tr onclick="detail(${list.dataArray[0].value})">
                        <#list list.dataArray as array>
                            <#if array_index gt 0>
                                <td> ${array.value}</td>
                            </#if>
                        </#list>
                    </tr>
                    </#list>
                <#else>
                <tr>
                    <td colspan="${data.columnTitles?size}">暂无数据</td>
                </tr>
                </#if>
            </tbody>
        </table>
    </div>
    <div class="bot_bot">
        <div id="kkpager" class="bot_bot kkpager"></div>
        <input type="button" id="captcha" onclick="hideCaptcha()" value="确定" style="width: 44px;
        height: 20px;line-height: 20px;font-family: arial, 宋体, sans-serif;text-align: center;background-color: rgb(0, 99, 220);color: rgb(255, 255, 255);
        position: absolute;left: 0px;top: -1px;display: none;padding: 0px;border-width: 0px;border-style: initial;border-color: initial;border-image: initial;">
    </div>
</div>
<#else >
<table class="table-hover table-striped" border="1" width="100%" cellpadding="0" cellspacing="0" style="text-align: left;border-collapse:collapse">
    <tr>
        <td>${_RESULT.title}</td>
    </tr>
</table>
</#if>
<#--</body>-->
<#--</html>-->
<script>
    $(function () {
        //生成分页控件
        kkpager.generPageHtml({
            pagerid: "kkpager",
        pno: <#if data??>${data.currPage}<#else>0</#if>,
            mode : 'click',
            //总页码
        total:<#if data??>Math.ceil(${data.totalCount/data.pageSize})<#else>0</#if> ,
            //总数据条数
        totalRecords: <#if data??>${data.totalCount}<#else>0</#if>,
            //链接算法
            click: function (n) {
                //showCaptcha();
                //获取第n页数据
                search(n);
            }
        },true);
    });

    function showCaptcha(){
        $("#captcha").show();
    }

    function hideCaptcha(){
        $("#captcha").hide();
    }


    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 分页查询
     */
    function search(n) {
        $.ajax({
            url: "/credit/pub/ucc/index.do",
            data: {
                poolId: 121,
                pageSize:<#if data??> ${data.pageSize}<#else>0</#if>,
                currPage: n,
                //captcha:captcha,
            <#if data??>
                <#list data.webQueryCondition as cond>
                    "webQueryCondition[${cond?index}].fieldTitle": "${cond.fieldTitle}",
                    "webQueryCondition[${cond?index}].fieldDBName": "${cond.fieldDBName}",
                    "webQueryCondition[${cond?index}].fieldQueryValue": $("input[name='${cond.fieldDBName}']").val(),
                </#list>
            </#if>
            },
            success: function (html) {
                $("#mainContent").html(html);
            }
        })
    }
    /**
     * 作者: 张磊
     * 日期: 2017/03/15 下午03:05
     * 备注: 重置搜索框
     */
    function resetSearch() {
    <#if data??>
        <#list data.webQueryCondition as cond>
            $("input[name='${cond.fieldDBName}']").val("");
        </#list>
    </#if>

    }

    function detail(kval) {
        $.ajax({
            type:"POST",
            url:"/credit/pub/ucc/detail.do",
            data:{
//                poolId : uid ,
                ukValue : kval
            },
            success:function(html){
                var obj = window.open("about:blank");
                obj.document.write(html);
            }
        });
    }
</script>