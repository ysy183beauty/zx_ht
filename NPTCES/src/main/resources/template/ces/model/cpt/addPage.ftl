<#include "/template/Credit_Common/common.ftl">
<form id="form_props" action="addCpt.${urlExt}" method="post" onsubmit="return editProps(this, refreshCptList)" class="form-horizontal no-bottom-space" >
    <div class="modal-header">
        <button data-dismiss="modal" class="close" type="button"></button>
        <h3><i class="icon-exclamation-sign"></i> 新增计算方法</h3>
    </div>
    <div class="modal-body">
        <div class="alert alert-error hide">
            <button class="close" data-dismiss="alert"></button>
            You have some form errors. Please check below.
        </div>
        <div class="alert alert-success hide">
            <button class="close" data-dismiss="alert"></button>
            Your form validation is successful!
        </div>
        <div class="control-group">
            <label class="control-label">名称<span class="required">*</span></label>
            <div class="controls pointFa">
                <input type="text" class="span6 m-wrap" name="countType.title" value="${(countType.title)!}">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">计算方式<span class="required">*</span></label>
            <div class="controls pointFa">
                <select class="span3 plus_width" name="countType.computeType">
                <#list CES_CPM_LIST as c>
                    <option value="${c.code}" <#if (countType.computeType)?? && (countType.computeType)?int == c.code>selected</#if>>${c.title}</option>
                </#list>
                </select>
                <p class="point-out">这是一段测试文字</p>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">定性计算数据条数区间及区间值<span class="required">*</span></label>
            <div class="controls pointFa">
                <div class="inputBox">
                    <input id="baseValue" type="text" class="span4 m-wrap " name="countType.countIntervals" value="${(countType.countIntervals)!}">
                    <a class="box_a btn mini green tooltips" href="javascript:void(0)" onclick="giveValue('tableBox','plusQjz','baseValue')" data-placement="bottom" data-original-title="编辑区间值"><i class="icon-edit "></i></a>

                </div>
                <p class="point-out">这是一段测试文字</p>
                <div class="tableBox" id="tableBox">
                    <table id="plusQjz" class="table table-bordered span4">
                        <tbody>
                        </tbody>
                    </table>
                    <a class="btn mini blue tooltips" onclick="plusTd('plusQjz','plusQjz','baseValue')" data-placement="bottom" data-original-title="添加区间值"><i class="icon-plus "></i></a>
                    <a class="btn mini red tooltips" onclick="minusTd('plusQjz',plusQjz,baseValue)" data-placement="bottom" data-original-title="添加区间值"><i class="icon-minus "></i></a>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button class="btn blue ok" type="submit"><i class="icon-ok"></i> 保 存 </button>
        <button class="btn" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i> 取 消 </button>
    </div>
</form>
<script type="text/javascript" src="${wctx}/pub/ces/cw/addTable.js"></script>