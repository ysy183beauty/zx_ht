<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/echarts/echarts.min.js"></script>
    <script type="text/javascript" src="${wctx}/pub/statistics/js/dataFastSelecter.js"></script>
    <style type="text/css">
        table
        {
            border-collapse: collapse;
            text-align: center;
            table-layout: fixed;
            width: 100%;
        }
        table td, table th
        {
            border: 1px solid #cad9ea;
            color: #666;
            height: 20px;
        }
        table thead th
        {
            background-color: #CCE8EB;
            width: 200px;
        }
        table tr
        {
            background: #fff;
        }
        /*        table tr:nth-child(even)
                {

                }*/
        .btCls{
            background: #F5FAFA;
            font-size: 18px;
        }
        #topInfo tr td{
            border: none;
        }
        #titleInfo{
            margin-bottom: 8px;
        }
        #titleInfo th{
            text-align: center;
            border: none;
            background:none;
            font-size: 25px;
            color: red;
        }
    </style>
</head>
<body class="page-header-fixed">

<div class="container-fluid">

    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>个人信用报告</div>
                </div>
                <div class="portlet-body ">
                    <div id="main" style="overflow:auto;">
                        <table id="topInfo">
                            <tr>
                                <td colspan="3"></td>
                                <td>大理州公共信用信息中心</td>
                            </tr>
                            <tr>
                                <td colspan="3"></td>
                                <td>联系电话: 883xxxxx</td>
                            </tr>
                            <tr>
                                <td colspan="3"></td>
                                <td>邮箱:</td>
                            </tr>
                            <tr>
                                <td colspan="3"></td>
                                <td>报告编号: dlgrxybg_<span id="bhCurrentTime"></span></td>
                            </tr>
                        </table>
                        <table id="titleInfo">
                            <tr><th colspan="8">个人信用报告</th></tr>
                        </table>
                        <table border="1">
                            <tr><td colspan="8" style="text-align: left;padding:10px;">重要声明：</td></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">1、本报告生成时间为<span id="currentTime"></span>，你所看到的内容为截止时间点的数据快照。</td></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">2、大理州企业信用报告信息来源于有关政府部门已提供的信息。该报告仅供参考。</td></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">3、您有权对本报告中的内容提出异议，如有异议，可联系数据提供单位，也可在异议处理模块提出异议。</td></tr>
                            <tr ><th colspan="8" style="text-align: center;"></th></tr>
                            <tr ><th colspan="8" style="text-align: center;" class="btCls">基础信息</th></tr>
                            <tr>
                                <td>姓名：</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="XM"></td>
                                <td>民族：</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="MZMC"></td>
                            </tr>
                            <tr>
                                <td>性别：</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="XB"></td>
                                <td>出生年月：</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="CSRQ"></td>
                            </tr>
                            <tr>
                                <td>身份证地址：</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="MLXZ"></td>
                                <td>身份证号：</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="SFZH"></td>
                            </tr>
                            <tr ><th colspan="8" style="text-align: center;"></th></tr>
                            <tr ><th colspan="8" style="text-align: center;" class="btCls">信贷记录</th></tr>
                            <tr ><th colspan="8" style="text-align: center;">这部分包含您的信用卡、贷款和其他信贷记录，金额类数据均以人民币计算，精确到元。</th></tr>
                            <tr>
                                <td>信息概要</td>
                                <td colspan="7">逾期记录可能影响对您的信用评价。</td>
                            </tr>
                            <tr>
                                <td colspan="2"></td>
                                <td>信用卡</td>
                                <td>住房贷款</td>
                                <td colspan="2">其他贷款</td>
                                <td colspan="2" rowspan="6" style="white-space:normal;">
                                    发生过逾期的信用卡账户，指曾经“未按时还最低还款额”的贷记卡账户和“透支超过60天”的准贷记卡账户。
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">账户数</td>
                                <td></td>
                                <td></td>
                                <td colspan="2"></td>
                            </tr>
                            <tr>
                                <td colspan="2">未结清、未销户账户数</td>
                                <td></td>
                                <td></td>
                                <td colspan="2"></td>
                            </tr>
                            <tr>
                                <td colspan="2">发生过逾期的账户数</td>
                                <td></td>
                                <td></td>
                                <td colspan="2"></td>
                            </tr>
                            <tr>
                                <td colspan="2">发生过90天以上逾期的账户数</td>
                                <td></td>
                                <td></td>
                                <td colspan="2"></td>
                            </tr>
                            <tr>
                                <td colspan="2">为他人担保笔数</td>
                                <td></td>
                                <td></td>
                                <td colspan="2"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;" class="btCls">信用卡</td></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">发生过逾期的贷记卡账户明细如下：</td></tr>
                            <tr>
                                <td colspan="8" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td colspan="8" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">透支超过60天的准贷记卡账户明细如下：</td></tr>
                            <tr>
                                <td colspan="8" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td colspan="8" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">从未逾期过的贷记卡及透支未超过60天的准贷记卡账户明细如下：</td></tr>
                            <tr>
                                <td colspan="8" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td colspan="8" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;" class="btCls">住房贷款</td></tr>
                            <tr>
                                <td colspan="8" style="text-align: left;padding:10px;">从未逾期过的账户明细如下：</td>
                            </tr>
                            <tr>
                                <td colspan="8" style="text-align: left;padding:10px;"></td>
                            </tr>
                        </table>
                        <table border="1" id="commTb">
                            <!--需要隐藏部分 -->
                            <tr ><th colspan="8" style="text-align: center;" class="btCls">公共记录</th></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;" class="btCls">欠税记录</td></tr>
                            <tr>
                                <td>主管税务机关：</td>
                                <td colspan="3" style="text-align: left;padding:10px;">大理州xx税务局</td>
                                <td>欠税统计时间：</td>
                                <td colspan="3" style="text-align: left;padding:10px;">2016年8月份</td>
                            </tr>
                            <tr>
                                <td>欠税总额：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>纳税人识别号：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;" class="btCls">民事判决记录</td></tr>
                            <tr>
                                <td>立案法院：</td>
                                <td colspan="3" style="text-align: left;padding:10px;">大理州xx法院</td>
                                <td>案号：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>案由：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>结案方式：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>立案时间：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>判决/调解结果：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>诉讼标的：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>判决/调节生效时间：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>诉讼标的金额：</td>
                                <td colspan="7" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;" class="btCls">强制执行记录</td></tr>
                            <tr>
                                <td>执行法院：</td>
                                <td colspan="3" style="text-align: left;padding:10px;">大理州xx法院</td>
                                <td>案号：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>执行案由： </td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>执行方式：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>立案时间：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>案件状态：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>申请执行标的：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>已执行标的：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>申请执行标金额：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>已执行标的金额：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>结案时间：</td>
                                <td colspan="7" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;" class="btCls">行政处罚记录</td></tr>
                            <tr>
                                <td>处罚机构：</td>
                                <td colspan="3" style="text-align: left;padding:10px;">大理州xx法院</td>
                                <td>文书编号：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>处罚内容：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>是否行政复议：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>处罚金额：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>行政复议结果：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>处罚生效时间：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>行政复议结果：</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;" class="btCls">电信欠费记录</td></tr>
                            <tr>
                                <td>电信运营商：</td>
                                <td style="text-align: left;padding:10px;">中国移动</td>
                                <td>业务类型：</td>
                                <td colspan="2" style="text-align: left;padding:10px;">固定电话</td>
                                <td>记账年月：</td>
                                <td colspan="2" style="text-align: left;padding:10px;">2016年8月</td>
                            </tr>
                            <tr>
                                <td>是否行政复议：</td>
                                <td colspan="2" style="text-align: left;padding:10px;"></td>
                                <td>处罚内容：</td>
                                <td colspan="4" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr ><th colspan="8" style="text-align: center;" class="btCls">荣誉信息</th></tr>
                            <tr>
                                <td style="text-align: left;padding:10px;">序号</td>
                                <td style="text-align: left;padding:10px;">类别</td>
                                <td colspan="4" style="text-align: left;padding:10px;">主要内容</td>
                                <td colspan="2" style="text-align: left;padding:10px;">时间</td>
                            </tr>
                            <tr>
                                <td style="text-align: left;padding:10px;">1</td>
                                <td style="text-align: left;padding:10px;">技术类</td>
                                <td colspan="4" style="text-align: left;padding:10px;">获得本赛季一等奖</td>
                                <td colspan="2" style="text-align: left;padding:10px;">2019-12-23</td>
                            </tr>
                            <tr ><th colspan="8" style="text-align: center;" class="btCls">查询记录</th></tr>
                            <tr ><th colspan="8" style="text-align: center;">这部分包含您的信用报告最近两年内被查询的记录。</th></tr>
                            <tr>
                                <td style="text-align: left;padding:10px;">编号</td>
                                <td style="text-align: left;padding:10px;">查询日期</td>
                                <td colspan="4" style="text-align: left;padding:10px;">查询操作员</td>
                                <td colspan="2" style="text-align: left;padding:10px;">查询原因</td>
                            </tr>
                            <tr>
                                <td style="text-align: left;padding:10px;">1</td>
                                <td style="text-align: left;padding:10px;">2015年9月5日</td>
                                <td colspan="4" style="text-align: left;padding:10px;">本人</td>
                                <td colspan="2" style="text-align: left;padding:10px;">本人查询</td>
                            </tr>
                            <tr ><th colspan="8" style="text-align: center;">此外，2015年您通过互联网共进行了0次查询。</th></tr>
                            <tr ><th colspan="8" style="text-align: center;"></th></tr>
                            <tr ><th colspan="8" style="text-align: center;" class="btCls">说明</th></tr>
                            <tr ><th colspan="8" style="text-align: center;white-space:normal;">1、除查询记录之外，本报告中的信息是依据截至报告时间个人征信系统记录的信息生成，
                                    本平台不确保其真实性和准确性，但承诺在信息汇总、加工、整合的全过程中保持客观、中立的地位。</th></tr>
                            <tr ><th colspan="8" style="text-align: center;white-space:normal;">
                                    2、您有权对本报告中的内容提出异议，如有异议，可联系数据提供单位，也可在异议处理模块提出异议。</th></tr>
                        </table>
                    </div>
                </div>
                <div class="form-actions">
                    <button type="button" onclick="backIndex()" class="btn blue"><i class="icon-arrow-left icon-white"></i>返回</button>
                </div>
            </div>
        </div>
    </div>
    <!-- END PAGE HEADER-->
</div>
<script>
    //返回页面功能
    function backIndex() {
        window.history.back(-1);
    }
    $(function () {
        //1、隐藏暂时没有内容的
        $("#commTb").css("display","none");
        //设置当前时间
        loadCurrentTime();
        //加载基本信息
        loadBaseInfo();
    });
    //设置当前时间相关信息
    function loadCurrentTime() {
        var date = new Date();
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var hours=date.getHours();
        var minutes=date.getMinutes();
        var seconds=date.getSeconds();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        if(hours>=0&&hours<=9){
            hours="0"+hours;
        }
        if(minutes>=0&&minutes<=9){
            minutes="0"+minutes;
        }
        if(seconds>=0&&seconds<=9){
            seconds="0"+seconds;
        }
        var currentdate = date.getFullYear()+ month+ strDate
            +hours+minutes+seconds;
        var currentDt=date.getFullYear()+"年"+month+"月"+strDate+"日" +" "+hours+"时"+minutes+"分"+seconds+"秒";
        $("#bhCurrentTime").text(currentdate);
        $("#currentTime").text(currentDt);
    }
    //加载基本信息
    function loadBaseInfo() {
        var grbgInfo='${grbgInfo}';
        var obj= JSON.parse(grbgInfo);
        $("#XM").text(obj[0].XM);
        $("#MZMC").text(obj[0].MZMC);
        $("#XB").text(obj[0].XB);
        $("#CSRQ").text(obj[0].CSRQ);
        $("#SFZH").text(obj[0].SFZH);
        $("#MLXZ").text(obj[0].MLXZ);
    }
</script>
<!-- END PAGE CONTAINER-->
</body>
<!-- END BODY -->
</html>