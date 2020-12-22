<#include "/template/Credit_Common/common.ftl">
<#assign appeal =  _APPEAL>
<div class="portlet box boxTheme">
    <div class="portlet-title">
        <div class="caption"><i class="icon-search"></i> 异议申请表</div>
        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>
    <div class="portlet-body">
        <table class="table table-bordered"  >
            <tbody>
            <tr>
                <td width="20%" align="right" class="bg">信息源单位：</td>
                <td width="30%" align="left" class="bold">${appeal.appealProviderTitle!}</td>
                <td width="20%" align="right" class="bg" >异议信息类名称：</td>
                <td width="30%" align="left" class="bold"  >${appeal.appealDTTitle!}</td>

            </tr>
            <tr>
                <td align="right"  class="bg">申请人：</td>
                <td align="left"  >${appeal.appealUser!}</td>
                <td align="right" class="bg" >联系电话：</td>
                <td align="left"  >${appeal.appealUserTel!}</td>
            </tr>
            <tr>
                <td align="right"  class="bg">邮箱：</td>
                <td align="left" colspan="3">${appeal.appealUserEmail!}</td>
            </tr>

            <tr  >
                <td align="right"  class="bg">登记人：</td>
                <td align="left"><#if appeal.source==1 && appeal.appealStatus==0>${(_STEP_USER.realname)!}<#else>${(_STEP_USER.userName)!}</#if></td>
                <td align="right" class="bg">异议信息冻结时间：</td>
                <td align="left">
                    <#if appeal.frozenStartDate?? && appeal.frozenEndDate??>
                    ${appeal.frozenStartDate?string("yyyy年MM月dd日")!} - ${appeal.frozenEndDate?string("yyyy年MM月dd日")!}
                    </#if>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
</div>

<script>
    function downloadAttach(id) {
        window.open("${ctx}/npt/grs/appeal/downloadAttach.${urlExt}?id=" + id);
    }
</script>