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
            color: blue;
        }
    </style>
</head>
<body class="page-header-fixed">

<div class="container-fluid">

    <div class="row-fluid">
        <div class="span12">
            <div class="portlet box boxTheme">
                <div class="portlet-title">
                    <div class="caption"><i class="icon-search"></i>企业信用报告</div>
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
                            <tr><th colspan="8">企业信用报告</th></tr>
                        </table>
                        <table border="1">
                            <tr ><th colspan="8" style="text-align: left">● 基础信息：基本信息 · 资质认证信息 · 企业参保信息 · 股东出资信息 · 主要人员信息 · 变更信息</th></tr>
                            <tr ><th colspan="8" style="text-align: left">● 企业关联信息：分支机构列表 · 企业对外投资 · 法定代表人对外投资信息 · 法定代表人对外任职信息</th></tr>
                            <tr ><th colspan="8" style="text-align: left">● 法律诉讼：被执行人信息 · 失信被执行人信息 · 裁判文书信息 · 法院公告信息</th></tr>
                            <tr ><th colspan="8" style="text-align: left">● 企业风险：经营异常信息 · 动产抵押登记信息 · 股权出质信息 · 行政处罚信息 · 欠税信息</th></tr>
                            <tr ><th colspan="8" style="text-align: left">● 年报信息</th></tr>
                            <tr ><th colspan="8" style="text-align: left">● 知识产权：商标信息 · 专利信息 · 著作权信息 · 网站信息</th></tr>
                            <tr ><th colspan="8" style="text-align: left">● 经营信息：融资信息 · 招聘信息</th></tr>
                            <tr ><th colspan="8" style="text-align: left">● 企业荣誉信息</th></tr>
                            <tr ><th colspan="8" style="text-align: center;"></th></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">重要声明：</td></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">1、本报告生成时间为<span id="currentTime"></span>，你所看到的内容为截止时间点的数据快照。</td></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">2、大理州企业信用报告信息来源于有关政府部门已提供的信息。该报告仅供参考。</td></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;">3、您有权对本报告中的内容提出异议，如有异议，可联系数据提供单位，也可在异议处理模块提出异议。</td></tr>
                            <tr ><th colspan="8" style="text-align: center;"></th></tr>
                            <tr ><th colspan="8" style="text-align: center;" class="btCls">基础信息</th></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;font-size: 14px;" class="btCls">1.1 基本信息</td></tr>
                            <tr>
                                <td>机构代码</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="ZZJGDM"></td>
                                <td>机构名称</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="FRMC"></td>
                            </tr>
                            <tr>
                                <td>机构类型</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="FRLX"></td>
                                <td>法人代表</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="FDDBR"></td>
                            </tr>
                            <tr>
                                <td>法人身份证号码</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="FRSFZH"></td>
                                <td>法人住所</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="FRZS"></td>
                            </tr>
                            <tr>
                                <td>成立日期</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="CLRQ"></td>
                                <td>工商注册号</td>
                                <td colspan="3" style="text-align: left;padding:10px;" id="GSZCH"></td>
                            </tr>
                            <tr>
                                <td>法人状态</td>
                                <td colspan="7" style="text-align: left;padding:10px;" id="FRZT"></td>
                            </tr>
                            <tr ><th colspan="8" style="text-align: center;"></th></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;font-size: 14px;" class="btCls">1.2 资质认证信息</td></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;font-size: 10px;" class="btCls">许可证</td></tr>
                            <tr>
                                <td>许可证名称</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>许可证编号</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>许可证类型</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>许可时间</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>有限期限</td>
                                <td colspan="7" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;font-size: 10px;" class="btCls">营业执照</td></tr>
                            <tr>
                                <td>注册号</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>名称</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>住所</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>法定代表人</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>注册资金</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>企业类型</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>经营范围</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>营业期限</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>成立日期</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>企业法人年检情况</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>登记机关</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>登记时间</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;font-size: 14px;" class="btCls">1.3 企业参保信息</td></tr>
                            <tr><td colspan="8" style="text-align: left;padding:10px;font-size: 10px;" class="btCls">企业参保信息</td></tr>
                            <tr>
                                <td>登记证号</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>单位名称</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>组织机构代码</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>证件种类</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
                            <tr>
                                <td>执照号码</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                                <td>单位性质</td>
                                <td colspan="3" style="text-align: left;padding:10px;"></td>
                            </tr>
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
    //页面加载完成事件
    $(function () {
        //设置当前时间
        loadCurrentTime();
        //加载数据信息
        loadDataBase();
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
    //加载数据信息
    function loadDataBase() {
        var qyxyInfo='${qyxyInfo}';
        var obj= JSON.parse(qyxyInfo);
        $("#ZZJGDM").text(obj[0].ZZJGDM);
        $("#FRMC").text(obj[0].FRMC);
        $("#FRLX").text(obj[0].FRLX);
        $("#FDDBR").text(obj[0].FDDBR);
        $("#FRSFZH").text(obj[0].FRSFZH);
        $("#FRZS").text(obj[0].FRZS);
        $("#CLRQ").text(obj[0].CLRQ);
        $("#GSZCH").text(obj[0].GSZCH);
        $("#FRZT").text(obj[0].FRZT);
    }
</script>
<!-- END PAGE CONTAINER-->
</body>
<!-- END BODY -->
</html>