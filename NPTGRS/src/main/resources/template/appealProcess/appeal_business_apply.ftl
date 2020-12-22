<#include "/template/Credit_Common/common.ftl">
<div class="btn-group">
    <button type="button" class="btn" onclick="backToIndex()">返回</button>
</div>

    <!-------------------------------------企业信用信息异议-------------------------------------------->
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-comment-alt"></i>企业信用信息异议</div>
                </div>
                <div class="portlet-body form">
                    <form action="" class="form-horizontal">
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label for="" class="control-label">企业名称：</label>
                                    <div class="controls">
                                        <input type="text" class="m-wrap span12"></div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="control-group">
                                    <label for="" class="control-label">统一社会信用代码：</label>
                                    <div class="controls">
                                        <input type="text" class="m-wrap span12"></div>
                                </div>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span12">
                                <div class="control-group">
                                    <label for="" class="control-label">异议信息：</label>
                                    <div class="controls">
                                        <textarea name="" id="" rows="3" class="m-wrap span12"
                                                  placeholder="请输入您觉得有异议的字段"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="button" class="btn blue"><i class="icon-search"></i> 查询</button>
                            <button type="reset" class="btn"><i class="icon-undo"></i> 重置</button>
                        </div>
                    </form>
                    <h3 class="form-section"></h3>
                    <div class="row-fluid">
                        <div class="span12"><span class="pull-left">企业名称：</span> <span
                                class="pull-right">统一社会信用代码：</span>
                            <div class="portlet box boxTheme">
                                <div class="portlet-title">
                                    <div class="caption">
                                        <label class="checkbox">
                                            <input type="checkbox"> 基本信息
                                        </label>
                                    </div>
                                    <div class="tools">
                                        数据源：XXX
                                    </div>
                                </div>
                                <div class="portlet-body form">
                                    <table class="table table-bordered table-hover tbList2">
                                        <tbody>
                                        <tr>
                                            <td>
                                                <label class="checkbox">
                                                    <input type="checkbox"> <b>注册地址</b>
                                                </label>
                                            </td>
                                            <td>大理xx街道xx号</td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="checkbox">
                                                    <input type="checkbox"> <b>注册地址</b>
                                                </label>
                                            </td>
                                            <td>大理xx街道xx号</td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="checkbox">
                                                    <input type="checkbox"> <b>注册地址</b>
                                                </label>
                                            </td>
                                            <td>大理xx街道xx号</td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <label class="checkbox">
                                                    <input type="checkbox"> <b>注册地址</b>
                                                </label>
                                            </td>
                                            <td>大理xx街道xx号</td>
                                        </tr>
                                        </tbody>
                                    </table>
                                    <div class="form-actions">
                                        <button type="button" class="btn blue" onclick="showAppeal()"><i class=" icon-external-link"></i> 提出异议</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

<div id="appealDiv" class="modal hide fade " tabindex="-1" data-width="760" >
    <#include "appeal_handle_apply.ftl">
</div>

<script>
    $(function () {
        App.initUniform();
        FormComponents.init();
    });
    function showAppeal(){
        $("#appealDiv").modal("show");
    }
</script>
