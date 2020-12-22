<form action="addGroupoolLink.action" onsubmit="return addGroupoolLink(this)" class="no-bottom-space" id="relative-form">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>关联模型</h4>
    </div>
    <div class="modal-body">
        <div class="scroller" data-height="470">
            <table class="table table-bordered data-table dataTable table-hover">
                <thead>
                <tr>
                    <th width="5%">序号</th>
                    <th width="20%">关联数据池</th>
                    <th width="10%">业务主键</th>
                    <th width="10%">数据主键</th>
                    <th width="5%">状态</th>
                    <th width="5%">关联</th>
                    <th width="10%">标题</th>
                </tr>
                </thead>
                <tbody>
                <#if  _ALL_POOL_LIST??&&_ALL_POOL_LIST?size gt 0>
                    <#list _ALL_POOL_LIST as c>
                    <tr>
                        <td>${c_index+1}</td>
                        <td align="left">${c.poolTitle!}</td>
                        <td>${c.pFieldTitle!}</td>
                        <td>${c.uFieldTitle!}</td>
                        <td>
                            <#if c.status == 1>
                                启用
                            <#else >
                                禁用
                            </#if>
                        </td>
                        <td>
                            <input type="checkbox" name="toPoolId[]" value="${c.id}"
                                <#if true == c.bFlag>
                                   checked
                                </#if>
                            >
                        </td>
                        <td>
                            <input type="text" name="linkTitle[]" value="${c.linkTitle!}"
                                <#if false == c.bFlag> disabled</#if>
                            >
                        </td>
                    </tr>
                    </#list>
                <#else >
                <tr>
                    <td colspan="6"> 暂无数据</td>
                </tr>
                </#if>
                </tbody>
            </table>
        </div>
    </div>
    <div class="modal-footer">
        <input type="hidden" name="fromPoolId" value="${_POOL_ID}">
        <input type="hidden" name="fkFieldId" value="${_FK_FIELD_ID}">
        <button type="submit" class="btn blue"><i class="icon-ok"></i>&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;定</button>
        <#--<button type="button" class="btn green" onclick="removeDate()"><i class="icon-ok"></i>&nbsp;&nbsp;清&nbsp;&nbsp;&nbsp;&nbsp;空</button>-->
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>
</form>
<script>
    App.handleScrollers();
    $("input[name='toPoolId[]']").on("change", function () {
        var input = $(this).parent("td").next().find("input");
        if($(this).prop("checked")){
            input.prop("disabled", false);
            input.prop("required", true);
        }else{
            input.val("");
            input.prop("disabled", true);
            input.prop("required", false);
        }
    });
//    function removeDate(){
//        $.post("deleteGroupoolLink.action",$("#relative-form").serialize(),function (data) {
//            if(data.result == "0") {
//                returnInfo(false, data.message || "操作失败！");
//            }else{
//                returnInfo(true, "操作成功！");
//                $("#standardModal").modal("hide");
//                $("#poolList").empty();
//            }
//        });
//    }
</script>