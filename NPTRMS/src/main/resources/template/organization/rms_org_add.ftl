<#include "/template/Credit_Common/common.ftl">

<form action="add.${urlExt}" role="form" onsubmit="return submitForm(this)" class="no-bottom-space">
    <#if data?? && data.id??>
        <input name="data.parentId" type="hidden" value="${data.id?string("#")!}">
    </#if>
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>新增机构</h4>
    </div>
    <div class="modal-body">
    <div class="row-fluid">
        <div class="span6">
            <div class="control-group">
                <label class="control-label">机构名称<span class="required">*</span></label>
                <div class="controls">
                    <input name="data.orgName" required type="text" class=" span12" maxlength="30" placeholder="请输入机构名称">
                </div>
            </div>
        </div>
            <div class="span6">
                <div class="control-group">
                    <label class="control-label">机构别名<span class="required">*</span></label>
                    <div class="controls">
                        <input name="data.alias" required  type="text" class=" span12" maxlength="30" placeholder="请输入机构别名">
                    </div>
                </div>
            </div>
    </div>
    <div class="row-fluid">
        <div class="span6">
            <div class="control-group">
                <label class="control-label">英文缩写<span class="required">*</span></label>
                <div class="controls">
                    <input name="data.orgCode"  required type="text" class=" span12" maxlength="30" placeholder="请输入机构英文缩写">
                </div>
            </div>
        </div>
        <div class="span6">
            <div class="control-group">
                <label class="control-label">机构描述</label>
                <div class="controls">
                    <input name="data.orgNote"  type="text" class=" span12" maxlength="30" placeholder="请输入机构描述">
                </div>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span6">
            <div class="control-group">
                <label class="control-label">机构网址</label>
                <div class="controls">
                    <input name="data.orgSite"  type="text" class=" span12" maxlength="30" placeholder="请输入机构网址">
                </div>
            </div>
        </div>
        <div class="span6">
            <div class="control-group">
                <label  class="control-label">机构微信号</label>
                <div class="controls">
                    <input name="data.orgWechart"  type="text" class=" span12" maxlength="30" placeholder="请输入机构微信号">
                </div>
            </div>
        </div>
    </div>
    <div class="row-fluid">
        <div class="span6">
            <div class="control-group">
                <label class="control-label">机构电话</label>
                <div class="controls">
                    <input name="data.orgTel"  type="text" class=" span12" maxlength="30" placeholder="请输入机构电话">
                </div>
            </div>
        </div>
        <div class="span6">
            <div class="control-group">
                <label class="control-label">机构邮箱</label>
                <div class="controls">
                    <input name="data.orgMail"  type="text" class=" span12" maxlength="30" placeholder="请输入机构邮箱">
                </div>
            </div>
        </div>
    </div>

    <div class="row-fluid">
        <div class="span6">
            <div class="control-group">
                <label class="control-label">机构状态</label>
                <div class="controls">
                    <label class="radio"><input type="radio" name="data.status" value="1" checked>启用</label>
                    <label class="radio"><input type="radio" name="data.status" value="0">禁用</label>
                </div>
            </div>

        </div>
        <div class="span6">
            <div class="control-group">
                <label class="control-label">是否可视</label>
                <div class="controls">
                    <label class="radio"><input type="radio" name="data.pubLevel" value="1" checked>可视</label>
                    <label class="radio"><input type="radio" name="data.pubLevel" value="0">不可视</label>
                </div>
            </div>
        </div>

    </div>

    <div class="row-fluid">

        <div class="span12">
            <div class="control-group">
                <label class="control-label">机构地址</label>
                <div class="controls">
                    <input name="data.orgAddr"  type="text" class=" span12" maxlength="30" placeholder="请输入机构地址">
                </div>
            </div>
        </div>

    </div>

    </div>
    <div class="modal-footer">
        <button type="submit" class="btn blue saveBtn"><i class="icon-ok"></i>&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>
</form>
<script type="text/javascript">
    App.initUniform();
</script>