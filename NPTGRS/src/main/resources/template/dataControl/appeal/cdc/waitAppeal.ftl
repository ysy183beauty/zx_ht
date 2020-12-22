<#assign appeal =  _APPEAL>
<#assign appealFields =  _APPEAL_FIELDS>
<#assign appealData = _APPEAL_DATA>
<form action="appealByCDC.action"  onsubmit="return handleAppeal(this)" class="form-horizontal no-bottom-space">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-legal"></i> 信用信息审批表</h4>
    </div>

    <div class="modal-body">
    <#if appeal.appealStatus == 0>
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
                            <th width="30%">系统信息</th>
                            <th width="30%">异议信息</th>
                            <#if appeal.appealStatus == 2>
                                <th width="20%">审核结果</th>
                                <th width="20%">反馈结果</th>
                            </#if>
                        </tr>
                        </thead>
                        <tbody id="dataList">
                            <#list appealData?keys as key>
                            <tr>
                                <td class="bg">${key}</td>
                                <td title="${appealData.get(key)!}">${appealData.get(key)!}</td>
                                <td title="${appealFields[key_index].appealValue!}">${appealFields[key_index].appealValue!}</td>
                                <#if appeal.appealStatus == 2>
                                <td>
                                    <#if appealFields[key_index].status == 0>
                                        未通过
                                    <#else >
                                        已通过
                                    </#if>
                                </td>
                                <td>
                                    ${appealFields[key_index].detailResult!}
                                </td>
                                </#if>
                            </tr>
                            </#list>
                        </tbody>
                    </table>
                    <#include "../info.ftl">
                </div>
            </div>
            <#if appeal.appealStatus == 0>
                <div class="portlet box boxTheme">
                    <div class="portlet-title">
                        <div class="caption"><i class="icon-search"></i>异议审核</div>
                        <div class="tools">
                            <a href="javascript:;" class="collapse"></a>
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="control-group">
                            <label class="control-label">审核结果</label>
                            <div class="controls">
                                <label class="radio"><input name="appealStatus" type="radio" value="1" checked>通过</label>
                                <label class="radio"><input name="appealStatus" type="radio" value="0">不通过</label>
                            </div>
                        </div>
                        <div class="control-group hide appealResult">
                            <label class="control-label">反馈结果</label>
                            <div class="controls">
                                <textarea name="appealResult" id="" class="span6" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="control-group frozenDate">
                            <label class="control-label">冻结时间</label>
                            <div class="controls">
                                <input type="text" id="form-date-range">
                                <input type="hidden" name="frozenStartDate">
                                <input type="hidden" name="frozenEndDate">
                            </div>
                        </div>
                    </div>
                </div>
            </#if>
            <input type="hidden" name="primaryKeyValue" value="${appeal.appealNo}">
            <input type="hidden" name="passFields">
        </div>
    </div>
    <div class="modal-footer">
    <#if appeal.appealStatus == 0>
        <button type="submit"  class="btn blue"><i class="icon-ok icon-white"></i>&nbsp;&nbsp;确&nbsp;&nbsp;&nbsp;&nbsp;认</button>
    </#if>
    <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>

</form>
<script>
    App.initUniform();
    App.handleScrollers();

    $("input[name='appealStatus']").change(function () {
        if ($(this).val() == 1){
            $(".frozenDate").show();
            $(".appealResult").hide();
        }else {
            $(".appealResult").show();
            $(".frozenDate").hide();
        }
    });

    $('#form-date-range').daterangepicker({
        opens: 'right'
        ,drops:'top'
        , startDate: Date.today()
        , endDate: Date.today().add({
            days:+20
        })
        , showDropdowns: true
    }, function (start, end, label) {
        $("input[name='frozenStartDate']").val(start.format('YYYY-MM-DD'));
        $("input[name='frozenEndDate']").val(end.format('YYYY-MM-DD'));
    });
    $("input[name='frozenStartDate']").val( Date.today().toString('yyyy-MM-dd'));
    $("input[name='frozenEndDate']").val( Date.today().add({days: +20}).toString('yyyy-MM-dd'));
</script>