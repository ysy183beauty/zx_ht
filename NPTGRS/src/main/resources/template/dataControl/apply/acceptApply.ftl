<#assign apply =  applyInfo>
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
            <span>
               您于${apply.createTime?string("yyyy年MM月dd日")!}提交的查询申请已通过${apply.applyProviderTitle!}审核，在申请查看期内您可对申请查看内容进行查看，感谢您对征信工作的支持！
            </span>
        </div>
    </div>
</div>

</div>