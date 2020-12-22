<#assign apply =  applyInfo>
<#assign log = applyLog>
<div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    <h4><i class="icon-legal"></i> 信用信息申请表</h4>
</div>
<div class="modal-body">
    <div class="scroller" data-height="500px">
<#include "../dataDetail.ftl">
    <#if apply.applyStatus==2>
        <#include "acceptApply.ftl">
    </#if>
    <#if apply.applyStatus==3>
        <#include "refusedApply.ftl">
    </#if>
    <div class="portlet">
        <div class="portlet-title">
            <div class="caption">审核进度</div>
        </div>

        <div class="portlet-body">
            <div class="parts">
                <div class="part part1">
                    <div class="on">
                        <div class="line"></div>
                        <div class="round"><i class="icon-ok"></i></div>
                        <div class="line2"></div>
                    </div>
                    <ul>
                        <li>申请已提交</li>
                        <li>${apply.createTime?string("yyyy年MM月dd日")!}</li>
                    </ul>
                </div>
                <div class="part part2">
                    <div <#if apply.applyStatus==1 || apply.applyStatus==2 || apply.applyStatus==3> class="on" </#if>>
                        <div class="line"></div>
                        <div class="round"><i class="icon-ok"></i></div>
                        <div class="line2"></div>
                    </div>
                    <ul>
                        <li>审核中</li>
                        <li></li>
                    </ul>
                </div>
                <div class="part part3">
                    <div <#if apply.applyStatus==2 || apply.applyStatus==3> class="on" </#if>>
                        <div class="line"></div>
                        <div class="round"><i class="icon-ok"></i></div>
                        <div class="line2"></div>
                    </div>
                    <ul>
                        <li>审核已完成</li>
                        <li><#if apply.applyStatus==2 || apply.applyStatus==3> ${log.createTime?string("yyyy年MM月dd日")!}</#if></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
        </div>
</div>
<div class="modal-footer">
    <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭
    </button>
</div>

<script>
    App.handleScrollers();
</script>