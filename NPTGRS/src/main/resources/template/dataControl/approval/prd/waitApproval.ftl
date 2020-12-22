<#assign apply =  applyInfo!>
<#assign applyFields =  applyFields!>
<#assign applyData = applyData!>
<form action="#" onsubmit="return approval(this)" class="form-horizontal no-bottom-space">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-legal"></i> 信用信息审批表</h4>
    </div>

    <div class="modal-body">
    <#if apply.applyStatus == 1>
        <div class="scroller" data-height="500px">
    </#if>
        <#include "../../dataDetail.ftl">
        <#if apply.applyStatus == 1>
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>信息详情</div>
                    <div class="tools">
                        <a href="javascript:;" class="collapse"></a>
                    </div>
                </div>

                <div class="portlet-body">
                    <table class="table table-bordered table-hover tbList">
                        <tbody>
                            <#list applyData?keys as key>
                            <tr>
                                <td class="bg">${key}</td>
                                <td title="${applyData.get(key)!}">${applyData.get(key)!}</td>
                            </tr>
                                <#if (applyData?size%2==1)&&(key_index==applyData?size-1)>
                                <tr>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                                </#if>
                            </#list>
                        </tbody>
                    </table>
                </div>

                <div class="portlet-body">
                    <form action="#" class="form-horizontal">
                        <div class="control-group">
                            <label class="control-label">审批结果：</label>
                            <div class="controls">
                                <label class="checkbox">
                                    <input type="radio" value="1" name="approvalResult" checked>通过
                                </label>
                                <label class="checkbox">
                                    <input type="radio" value="0" name="approvalResult">未通过
                                </label>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">批准查看时间：</label>
                            <div class=" controls demo">
                                <i class="icon-calendar"></i>
                                <input id="form-date-range" type="text">

                            </div>
                        </div>
                        <#--<div class="control-group">-->
                            <#--<label class="control-label">常用意见：</label>-->
                            <#--<div class="controls">-->
                                <#--<select class="span6" multiple="multiple" data-placeholder="Choose a Category"-->
                                        <#--tabindex="1">-->
                                    <#--<option value="不同意，身份验证不通过。">不同意，身份验证不通过。</option>-->
                                    <#--<option value="不同意，信息内容正在核查中。">不同意，信息内容正在核查中。</option>-->
                                    <#--<option value="不同意，信息内容涉及企业具体财务情况，暂不公开。">不同意，信息内容涉及企业具体财务情况，暂不公开。</option>-->
                                <#--</select>-->
                            <#--</div>-->
                        <#--</div>-->
                        <div class="control-group">
                            <label class="control-label">审批意见：</label>
                            <div class="controls">
                                <textarea name="approvalNote" class="span6 m-wrap" rows="3"></textarea>
                            </div>
                        </div>

                </div>
            </div>
            <input type="hidden" name="primaryKeyValue" value="${apply.applyNo}">
            <input type="hidden" name="confirmStartDate" value=" ${apply.applyedStartDate?string("yyyy-MM-dd")!}">
            <input type="hidden" name="confirmEndDate" value="${apply.applyedEndDate?string("yyyy-MM-dd")!}">
        </#if>

        </div>
    </div>
    <div class="modal-footer">
    <#if apply.applyStatus == 1>
        <button type="submit" class="btn blue"><i class="icon-search icon-white"></i> 确认</button>
    </#if>
    <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i> 返回</button>
    </div>

</form>
<script>
    App.initUniform();
    App.handleScrollers();

    $('#form-date-range').daterangepicker({
        opens: 'right'
        , startDate: '${apply.applyedStartDate !}'
        , endDate: '${apply.applyedEndDate!}'
        , showDropdowns: true
    }, function (start, end, label) {
        $("input[name='confirmStartDate']").val(start.format('YYYY-MM-DD'));
        $("input[name='confirmEndDate']").val(end.format('YYYY-MM-DD'));
    });

</script>