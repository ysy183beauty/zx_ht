<#include "/template/Credit_Common/common.ftl">
<style>
    .modal-body .row-fluid .control-label{
        display: none;
    }
    .modal-body .row-fluid:first-child .control-label{
        display: block;
    }
    .middle{
        padding: 7px 11px;
    }
</style>
<form action="${ctx}/npt/rms/dict/edit.action" class="horizontal-form no-bottom-space" role="form" onsubmit="return submitForm(this)" autocomplete="off"
>
    <input name="fieldId" type="hidden" value="${_FIELD_ID?string("#")}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4>修改码值</h4>
    </div>
    <div class="modal-body">
        <#if _CODES?size gt 0>
            <#list _CODES as code>
                <div class="row-fluid">
                    <div class="span6">
                        <div class="control-group">
                            <label class="control-label">码名称</label>
                            <div class="controls">
                                <input name="codeName[]" type="text" class="m-wrap medium" value="${code.codeName}">
                            </div>
                        </div>
                    </div>
                    <div class="span6">
                        <div class="control-group">
                            <label class="control-label">码值</label>
                            <div class="controls">
                                <input name="codeValue[]" type="text" class="m-wrap medium" value="${code.codeValue}">
                                 <#if code?has_next>
                                     <a class="btn btn-remove middle red tooltips"><i class="icon-remove"></i></a>
                                 <#else>
                                     <a class="btn btn-add middle green tooltips"><i class="icon-plus"></i></a>
                                 </#if>
                            </div>
                        </div>
                    </div>
                </div>
            </#list>
        <#else>
            <div class="row-fluid">
                <div class="span6">
                    <div class="control-group">
                        <label class="control-label">码名称</label>
                        <div class="controls">
                            <input name="codeName[]" type="text" class="m-wrap medium" value="">
                        </div>
                    </div>
                </div>
                <div class="span6">
                    <div class="control-group">
                        <label class="control-label">码值</label>
                        <div class="controls">
                            <input name="codeValue[]" type="text" class="m-wrap medium" value="">
                            <a class="btn btn-add middle green tooltips"><i class="icon-plus"></i></a>
                        </div>
                    </div>
                </div>
            </div>
        </#if>

    </div>
    <div class="modal-footer">
        <button type="submit" class="btn blue saveBtn"><i class="icon-ok"></i>&nbsp;&nbsp;保&nbsp;&nbsp;&nbsp;&nbsp;存</button>
        <button type="button" data-dismiss="modal" class="btn"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>
</form>
<script>
    App.initUniform();
    $(document).ready(function () {
        $(document).off("click", ".btn-add").off("click", ".btn-remove");
        $(document).on("click", ".btn-add", function () {
            var controlForm = $('.modal-body'),
                    currentEntry = $(this).parents('.modal-body .row-fluid:first'),
                    newEntry = $(currentEntry.clone()).appendTo(controlForm);

            newEntry.find('input').val('');
            controlForm.find('.row-fluid:not(:last) .btn-add')
                    .removeClass('btn-add').addClass('btn-remove')
                    .removeClass('green').addClass('red')
                    .html('<i class="icon-remove">');
        }).on("click", ".btn-remove", function () {
            $(this).parents('.modal-body .row-fluid:first').remove();
        });
    })
</script>