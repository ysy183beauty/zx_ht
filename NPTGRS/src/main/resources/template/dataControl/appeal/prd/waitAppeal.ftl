<#assign appeal =  _APPEAL>
<#assign appealFields =  _APPEAL_FIELDS>
<#assign appealData = _APPEAL_DATA>
<form action="appealByPRD.action" onsubmit="return handleAppeal(this)" class="form-horizontal no-bottom-space">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-legal"></i> 信用信息审批表</h4>
    </div>

    <div class="modal-body">
    <#if appeal.appealStatus == 1>
    <div class="scroller" data-height="500px">
    </#if>
    <#include "../dataDetail.ftl">
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption"><i class="icon-search"></i>异议修改建议</div>
                <div class="tools">
                    <a href="javascript:;" class="collapse"></a>
                </div>
            </div>

            <div class="portlet-body">
                <table class="table table-bordered table-condensed">
                    <thead>
                    <tr>
                        <th width="20%">异议项</th>
                        <th width="20%">系统信息</th>
                        <th width="20%">异议信息</th>
                        <th width="20%">审核结果</th>
                        <th width="20%">反馈结果</th>
                    </tr>
                    </thead>
                    <tbody id="dataList">
                        <#list appealData?keys as key>
                        <tr id="tr-${appealFields[key_index].fieldId?string("#")}">
                            <td class="bg">${key}</td>
                            <td title="${appealData.get(key)!}">${appealData.get(key)!}</td>
                            <td title="${appealFields[key_index].appealValue!}">${appealFields[key_index].appealValue!}</td>
                            <td>
                            <#if appeal.appealStatus == 1>
                                <div class="controls no-margin">
                                    <label class="checkbox">
                                        <input type="radio" value="1" name="fieldStatus-${appealFields[key_index].fieldId?string("#")}"  <#if appealFields[key_index].status == 1>checked</#if>>通&nbsp;&nbsp;&nbsp;&nbsp;过
                                    </label>
                                    <label class="checkbox">
                                        <input type="radio" value="0" name="fieldStatus-${appealFields[key_index].fieldId?string("#")}" <#if appealFields[key_index].status == 0>checked</#if>>未通过
                                    </label>
                                </div>
                                <#else >
                                    <#if appealFields[key_index].status == 0>
                                        未通过
                                    <#else >
                                        已通过
                                    </#if>
                            </#if>
                            </td>
                            <td>
                                <#if appeal.appealStatus == 1>
                                    <input type="text"  name="detailResult-${appealFields[key_index].fieldId?string("#")}">
                                <#else >
                                    ${appealFields[key_index].detailResult!}
                                </#if>
                            </td>

                        </tr>
                        </#list>
                    </tbody>
                </table>
            <#include "../info.ftl">
            </div>
        </div>
        <input type="hidden" name="primaryKeyValue" value="${appeal.appealNo}">
        <input type="hidden" name="passFields">
        <input type="hidden" name="unPassFields">
    </div>
    </div>
    <div class="modal-footer">
    <#if appeal.appealStatus == 1>
        <button type="submit" class="btn blue"><i class="icon-ok icon-white"></i>&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;认</button>
    </#if>
        <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>

</form>
<script>
    App.initUniform();
    App.handleScrollers();

    $('#form-date-range').daterangepicker({
        opens: 'right'
        , startDate: '${appeal.appealFrozenStartDate !}'
        , endDate: '${appeal.appealFrozenEndDate !}'
        , showDropdowns: true
    }, function (start, end, label) {
        $("input[name='frozenStartDate']").val(start.format('YYYY-MM-DD'));
        $("input[name='frozenEndDate']").val(end.format('YYYY-MM-DD'));
    });

</script>