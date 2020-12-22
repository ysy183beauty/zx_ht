<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
</head>
<body class="page-header-fixed">

<!-- BEGIN SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<!-- END SAMPLE PORTLET CONFIGURATION MODAL FORM-->
<!-- BEGIN PAGE CONTAINER-->
<div class="container-fluid">
    <!-- BEGIN PAGE HEADER-->
    <!-------------------------------------企业信用信息异议申请表-------------------------------------------->
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>企业信用信息异议申请表</div>
                </div>

                <div class="portlet-body">
                    <table class="table table-striped table-bordered table-hover" id="sample_1">
                        <tbody>
                        <tr>
                            <td width="30%">信息源单位</td>
                            <td colspan="3" width="70%">工商局</td>
                        </tr>
                        <tr>
                            <td>异议信息企业</td>
                            <td colspan="3">大理xx有限公司</td>
                        </tr>
                        <tr>
                            <td>统一社会信用代码</td>
                            <td colspan="3">56479975895368087</td>
                        </tr>
                        <tr>
                            <td>信异议息类名称</td>
                            <td colspan="3">基本信息</td>
                        </tr>
                        <tr>
                            <td>异议项</td>
                            <td colspan="3">注册地址</td>
                        </tr>
                        <tr>
                            <td>异议内容说明</td>
                            <td colspan="3"><textarea>由于企业地址变更,注册地址需更新。</textarea></td>
                        </tr>
                        <tr>
                            <td>申请人</td>
                            <td><input type="text" value="张三"></td>
                            <td>联系电话</td>
                            <td><input type="tel" value="188xxxx9745"></td>
                        </tr>
                        <tr>
                            <td>申请单位</td>
                            <td colspan="3">公安局</td>
                        </tr>
                        <tr>
                            <td>登记人</td>
                            <td colspan="3">张三</td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="pull-right">
                    <input type="file" class="filestyle" data-classButton="btn btn-primary" data-input="false" data-classIcon="icon-plus" data-buttonText="添加附件">
                </div>
                <div class="form-actions">
                    <button type="submit" class="btn blue">确认申请</button>
                    <button type="button" class="btn blue"><i class="icon-arrow-left icon-white"></i>
                        返回</button>
                </div>
            </div>
        </div>
    </div>
    <!-- END PAGE HEADER-->
</div>
<!-- END PAGE CONTAINER-->

<div id="successDiv" class="modal hide fade" tabindex="-1" data-width="760">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
    </div>
    <div class="modal-body">
        <div class="row-fluid">
            <div class="span12">
                李四，您好：
                您于2016年4月23日对大理xx有限公司注册地址提出异议申请，经过信用中心审核，目前申请已进行受理，预计4月30日完成信息核实，感谢您对征信工作的支持！

                登记人：张三
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn blue">打印</button>
        <button type="button" data-dismiss="modal" class="btn">返回</button>
    </div>
</div>

<script>
</script>
</body>
<!-- END BODY -->

</html>