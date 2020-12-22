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
                <div class="caption"><i class="icon-search"></i>申请信息</div>
            </div>
        </div>
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption"><i class="icon-search"></i>申请表</div>
            </div>
            <div class="portlet-body">
                <table class="table table-striped table-bordered table-hover" id="sample_1">
                    <tbody>
                    <tr>
                        <td>信息源单位：</td>
                        <td colspan="3">公安局</td>
                    </tr>
                    <tr>
                        <td>名称：</td>
                        <td colspan="3">张林</td>
                    </tr>
                    <tr>
                        <td>身份证号：</td>
                        <td colspan="3">321284xxxxxxxx5634</td>
                    </tr>
                    <tr>
                        <td>异议信息类名称</td>
                        <td colspan="3">个人基础信息</td>
                    </tr>
                    <tr>
                        <td>异议信息项</td>
                        <td colspan="3">学历</td>
                    </tr>
                    <tr>
                        <td>异议内容说明：</td>
                        <td colspan="3">
                            学历证件有问题。
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
                    <tr>
                        <td>登记人</td>
                        <td colspan="3">张三</td>
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
                        <th>学历：</th>
                        <th colspan="3">硕士</th>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption">处理结果</div>
            </div>
            <div class="portlet-body">
                <label>同意受理<input name="result" value="0" type="radio" checked="checked"></label>
                <label>拒绝申请<input name="result" value="1" type="radio"></label>
            </div>
        </div>
        <div class="portlet box boxTheme" id="result0">
            <div class="portlet-title">
                <div class="caption">异议信息企业/个人信息冻结时间</div>
            </div>
            <div class="portlet-body">
                <input type="date" value="2016-10-05">至<input type="date" value="2016-10-25">
            </div>
        </div>
        <div class="portlet box boxTheme" id="result1" style="display: none">
            <div class="portlet-title">
                <div class="caption">拒绝原因</div>
            </div>
            <div class="portlet-body">
                <input type="text">
            </div>
        </div>
        <div class="portlet-body">
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
                $("#result0").hide();
            }else{
                $("#result0").show();
                $("#result1").hide();
            }
        });
    });
</script>
</body>
<!-- END BODY -->

</html>