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
    <!-------------------------------------信息异议申请表-------------------------------------------->
    <div class="row-fluid">
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption"><i class="icon-search"></i>信息审核</div>
            </div>
        </div>
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption"><i class="icon-search"></i>信息异议申请表</div>
            </div>
            <div class="portlet-body">
                <table class="table table-striped table-bordered table-hover" id="sample_1">
                    <tbody>
                    <tr>
                        <td>信息源单位：</td>
                        <td colspan="3">工商局</td>
                    </tr>
                    <tr>
                        <td>异议信息企业：</td>
                        <td colspan="3">大理xx有限公司</td>
                    </tr>
                    <tr>
                        <td>统一社会信用代码：</td>
                        <td colspan="3">387895748364838370</td>
                    </tr>
                    <tr>
                        <td>异议信息类名称</td>
                        <td colspan="3">基本信息</td>
                    </tr>
                    <tr>
                        <td>异议信息项</td>
                        <td colspan="3">注册地址</td>
                    </tr>
                    <tr>
                        <td>异议内容说明：</td>
                        <td colspan="3">
                            由于企业地址变更,注册地址需更新。
                        </td>
                    </tr>
                    <tr>
                        <td>申请人：</td>
                        <td>张三</td>
                        <td>联系电话：</td>
                        <td>188xxxx9745</td>
                    </tr>
                    <tr>
                        <td>申请单位：</td>
                        <td colspan="3">大理xx有限公司</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption">详细信息</div>
            </div>
            <div class="portlet-body">
                <table>
                    <tbody>
                    <tr>
                        <th>注册地址：</th>
                        <th colspan="3">大理xx街道38号</th>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption">核实结果</div>
            </div>
            <div class="portlet-body">
                <label>信息无误<input name="result" value="0" type="radio" checked="checked"></label>
                <label>信息需修正<input name="result" value="1" type="radio"></label>
            </div>
        </div>
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption">确认说明</div>
            </div>
            <div class="portlet-body">
                <input type="text">
            </div>
        </div>
        <div class="portlet box boxTheme" id="result1" style="display: none">
            <div class="portlet-title">
                <div class="caption">信息修正</div>
            </div>
            <div class="portlet-body">
                <input type="text">
            </div>
        </div>
        <div class="portlet-body">
            <button type="button" class="btn blue">确定</button>
            <button type="button" class="btn blue"><i class="icon-arrow-left icon-white"></i>&nbsp;&nbsp;返&nbsp;&nbsp;&nbsp;&nbsp;回</button>
        </div>
    </div>
    <!-- END PAGE HEADER-->
</div>
<!-- END PAGE CONTAINER-->


<script>
    $(function () {
        $("input[name='result']").change(function () {
            if($(this).val()==1){
                $("#result1").show();
            }else{
                $("#result1").hide();
            }
        });
    });
</script>
</body>
<!-- END BODY -->

</html>