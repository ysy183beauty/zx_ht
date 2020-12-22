<#assign apply =  applyInfo>
<#assign applyFields =  applyFields>
<div class="portlet box boxTheme">
    <div class="portlet-title">
        <div class="caption"><i class="icon-search"></i>审批详情</div>
        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>

    <div class="portlet-body">
        <table class="table table-bordered table-hover" >
            <tbody>
            <tr>
                <td width="13%" align="right" class="bg">申请查看信息：</td>
                <td width="22%" align="left" class="bold" title="${apply.applyBusinessKey!}">${apply.applyBusinessKey!}</td>
                <td width="15%" align="right" class="bg">信息源单位：</td>
                <td width="20%" align="left" class="bold" title="${apply.applyProviderTitle!}">${apply.applyProviderTitle!}</td>
                <td width="10%" align="right" class="bg">信息类名称：</td>
                <td width="20%" align="left" class="bold" title="${apply.applyGrouPoolTitle!}">${apply.applyGrouPoolTitle!}</td>
            </tr>

            <tr>
                <td align="right" class="bg"> 信息项：</td>
                <td colspan="5" align="left" title=" <#list applyFields as fields>${fields.fieldName}、</#list>">
                    <#list applyFields as fields>
                            ${fields.fieldName}、
                    </#list>
                </td>
            </tr>
            <tr>
                <td align="right" class="bg">联系电话：</td>
                <td align="left" colspan="2" title="${apply.applyUserTel!}">${apply.applyUserTel!}</td>
                <td align="right" class="bg">计划查看时间：</td>
                <td align="left" colspan="2" title="${apply.applyedStartDate?string("yyyy年MM月dd日")!}
                    - ${apply.applyedEndDate?string("yyyy年MM月dd日")!}"> ${apply.applyedStartDate?string("yyyy年MM月dd日")!}
                    - ${apply.applyedEndDate?string("yyyy年MM月dd日")!} </td>
            </tr>
            <tr>
                <td align="right" class="bg">申请原因：</td>
                <td colspan="5" align="left" title="${apply.applyReason!}">${apply.applyReason!}</td>
            </tr>
            <tr>
                <td align="right" class="bg">申请单位：</td>
                <td id="applyUserOrg" align="left" colspan="2" title="{apply.applyUserOrgTitle!}">${apply.applyUserOrgTitle!}</td>
                <td align="right" class="bg">申请人：</td>
                <td align="left" colspan="2" title="${apply.applyUserName!}">${apply.applyUserName!}</td>
            </tr>
            <tr>
                <td align="right" class="bg">申请状态：</td>
                <td align="left" colspan="2" title="${apply.applyStatusTitle!}">${apply.applyStatusTitle!}</td>
                <td align="right" class="bg">申请结果：</td>
                <td align="left" colspan="2" title="<#if apply.applyStatus == 4>${apply.expiredTitle!}</#if>"><#if apply.applyStatus == 4>${apply.expiredTitle!}</#if></td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

