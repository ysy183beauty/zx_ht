<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>

</head>
<body class="page-header-fixed">

<div class="container-fluid">

    <!-------------------------------------企业信用信息异议-------------------------------------------->
    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme" id="listPage">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>个人信用信息异议</div>
                </div>
                <div class="portlet-body form">
                    <form action="list.action"  role="form" class="form-horizontal" onsubmit="return query(this)">
                        <div class="row-fluid">
                            <div class="span6">
                                <div class="control-group">
                                    <label class="ow control-label">名称</label>
                                    <div class="ow controls">
                                        <input value="张强">
                                    </div>
                                </div>
                            </div>
                            <div class="span6">
                                <div class="control-group">
                                    <label for="" class="ow control-label">身份证号</label>
                                    <div class="ow controls">
                                        <input value="321284xxxxxxxx7365">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row-fluid">
                            <div class="span12">
                                <div class="control-group">
                                    <label class="ow control-label">异议信息</label>
                                    <div class="ow controls demo">
                                        <textarea>
                                            毕业学校
                                        </textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn blue" ><i class="icon-search icon-white"></i>查询</button>
                            <button type="reset" class="btn"><i class="icon-repeat"></i>重置</button>
                        </div>
                    </form>
                </div>

                <div class="portlet-body">
                    <div class="row-fluid">
                        <div class="span6">
                            <div class="control-group">
                                名称:
                                <span>张强</span>
                            </div>
                        </div>
                        <div class="span6">
                            <div class="control-group">
                                身份证号:
                                <span>321284xxxxxxxx7365</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="portlet-title">
                    <div class="caption"><i class="icon-info-sign"></i>基本信息</div>
                    <div class="pull-right">数据源:  <span>公安局</span></div>
                </div>
                <div class="portlet-body">
                    <table class="table table-striped table-bordered table-hover">
                        <tbody>
                        <tr>
                            <td>毕业学校</td>
                            <td>北京xx大学</td>
                        </tr>
                        </tbody>
                    </table>
                    <div class="form-actions">
                        <button type="submit" class="btn blue"><i class="icon-question-sign"></i> 提出异议</button>
                    </div>
                </div>

            </div>
        </div>
    </div>
    <!-- END PAGE HEADER-->
</div>
<!-- END PAGE CONTAINER-->
<script>
    $(function () {
        App.initUniform();
        FormComponents.init();
    });
</script>
</body>
<!-- END BODY -->
</html>