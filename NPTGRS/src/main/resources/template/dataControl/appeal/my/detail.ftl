<#include "/template/Credit_Common/common.ftl">
<link rel="stylesheet" href="${wctx}/pub/CreditStyle/bootstrap/frame/media/css/bs-is-fun.css">
<#assign appeal =  _APPEAL>
<#assign appealFields =  _APPEAL_FIELDS>
<#assign appealData = _APPEAL_DATA>
<style type="text/css">
    .step li{
        width:25%;
    }
    .sqjd{
        text-align: center;
    }
    .sqjd span{
        display:block;
        text-align: center;
        margin-top:5px;
    }
    .sqjd ul{
        width:60%;
        display: inline-block;
    }
    .sqjd ul li a:hover{
        text-decoration: none;
    }
</style>
<form class="form-horizontal no-bottom-space">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-legal"></i> 异议详情</h4>
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
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption"><i class="icon-search"></i>申请进度</div>
                <div class="tools">
                    <a href="javascript:;" class="collapse"></a>
                </div>
            </div>
            <div class="portlet-body">

                <div class="sqjd">
                    <ul class="nav-all nav-pills nav-justified step step-round">
                    <#if _APPEAL_LOG??>
                        <#list _APPEAL_LOG as log>
                            <li class="progress-active"><a><span>${log.remark}<br>${log.createTime?string("yyyy-MM-dd")}</span></a></li>
                        <#if log.result ==1>
                            <li class="progress-active"><a><span>信息冻结中<br>${log.createTime?string("yyyy-MM-dd")}</span></a></li>
                        </#if>
                        <#if !log_has_next>
                            <#if log.result == 0>
                                <li class=""><a><span>核实中<br></span></a></li>
                                <li class=""><a><span>核实完成<br></span></a></li>
                            <#elseif log.result == 1>

                                <li class=""><a><span>核实完成<br></span></a></li>
                            </#if>
                        </#if>
                        </#list>

                    </#if>



                    </ul>
                </div>

            </div>
        </div>
    </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭
        </button>
    </div>

</form>
<script>
    App.initUniform();
    App.handleScrollers();
</script>