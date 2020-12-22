<#assign apply =  applyInfo>
<#assign log = applyLog>
<div class="portlet box boxTheme">
    <div class="portlet-title">
        <div class="caption"><i class="icon-search"></i>审核结果</div>
        <div class="tools">
            <a href="javascript:;" class="collapse"></a>
        </div>
    </div>

    <div class="portlet-body">
        <div class="row-fluid">
            <div class="span12">
                <p>
                    您于${apply.createTime?string("yyyy年MM月dd日")!}提交的查询申请未通过${apply.applyProviderTitle!}审核，您可重新提出申请，感谢您对征信工作的支持！
                </p>
                <p>
                    <strong>
                        您的申请已于${log.createTime?string("yyyy年MM月dd日")!}被拒绝！拒绝原因为：<br/>
                        ${log.remark!}
                    </strong>
                </p>
            </div>
        </div>
    </div>
</div>