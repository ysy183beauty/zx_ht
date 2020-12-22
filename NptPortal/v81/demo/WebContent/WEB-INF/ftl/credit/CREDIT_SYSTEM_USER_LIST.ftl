<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=10"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
    <title>用户管理 - 信用大理</title>
</head>
<body>
<#include "CommonUtil.ftl"/>
<#include "include/header.ftl"/>
<!--内容开始-->
<div class="w" style="min-height: 500px">
<#if page?? && page.list??>
    <div class="pull-left w240">
        <div class="height_auto m-t-md">
            <div class="left_nav">
                <span>用户中心</span>
                <ul>
                    <li>
                        <a href="/yhxx/index.jhtml">用户信息</a>
                    </li>
                    <li>
                        <a href="/smrz/index.jhtml">实名认证</a>
                    </li>
                    <li>
                        <a href="/mmxg/index.jhtml">密码修改</a>
                    </li>
                    <li>
                        <a href="/member/changeMobile.jspx">电话修改</a>
                    </li>
                    <li>
                        <a href="/credit/sys/user/userList.do" class="active">用户管理</a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
<div class="pull-right" style="width:810px;">
    <div class="height_auto m-t-md">
        <div class="g_tit m-b-md">
            <span>用户管理</span>
        </div>
            <form method="post" onsubmit="showloading()"
                  action="/credit/sys/user/userList.do?currentPage=${page.pageNo!}&pageNum=${page.pageSize!}">
                <div class="row">
                    <div class="form-group col-lg-6" >
                        <div class="col-lg-3 control-label">用户类别:</div>
                        <div class="col-lg-8">
                            <select name="type" type="text" class="form-control">
                                <option value="0" selected="selected">默认</option>
                                <option value="person">个人账户</option>
                                <option value="company">企业账户</option>
                                <option value="system">管理员账户</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group col-lg-6">
                        <div class="col-lg-3 control-label">状态:</div>
                        <div class="col-lg-8">
                            <select name="flag" type="text" class="form-control">
                                <option value="0" selected="selected">默认</option>
                                <option value="1">未认证</option>
                                <option value="2">认证中</option>
                                <option value="3">已认证</option>
                                <option value="4">认证失败</option>
                                <option value="9">禁用</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-lg-6">
                        <div class="col-lg-3 control-label">登录名:</div>
                        <div class="col-lg-8">
                            <input name="loginName" value="${quser.loginName!}" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group col-lg-6">
                        <div class="col-lg-3 control-label">姓名:</div>
                        <div class="col-lg-8">
                            <input name="userName" value="${quser.userName!}" type="text" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group col-lg-6">
                        <div class="col-lg-3 control-label">身份证号:</div>
                        <div class="col-lg-8">
                            <input name="idCard" value="${quser.idCard!}" type="text" class="form-control">
                        </div>
                    </div>
                    <div class="form-group col-lg-6">
                        <div class="col-lg-3 control-label">电话:</div>
                        <div class="col-lg-8">
                            <input name="phone" value="${quser.phone!}" type="text" class="form-control">
                        </div>
                    </div>
                </div>
                <div class="form-group text-center" style="margin-top: 15px;">
                    <div class="text-center" style="display: none"  id="loading">
                        <img style="margin-left: -10px;" src="/r/cms/www/red/img/loading.gif">
                    </div>
                    <button type="submit" class="btn_sytle" id="btnsub"
                            style="margin-top: 15px;width: 300px;">查询
                    </button>
                </div>
            </form>
        <div id="disableMsg"></div>
        <table class="table table-bordered m-t-md">
            <thead>
            <tr>
                <th class="text-center">登录名</th>
                <th class="text-center">姓名</th>
                <th class="text-center">用户类别</th>
                <th class="text-center">电话</th>
                <th class="text-center">身份证号</th>
                <th class="text-center">状态</th>
                <th class="text-center">编辑</th>
            </tr>
            </thead>
            <tbody>
                <#list page.list as u>
                <tr>
                    <td class="text-center">${u.loginName!}</td>
                    <td class="text-center">${u.userName!}</td>

                    <#if u.type??>
                        <#if u.type=='person'>
                            <td class="text-center">个人账户</td>
                        <#elseif u.type=='company'>
                            <td class="text-center">企业账户</td>
                        <#elseif u.type=='system'>
                            <td class="text-center">管理员账户</td>
                        <#else>
                            <td class="text-center">其他</td>
                        </#if>
                    <#else>
                        <td class="text-center"></td>
                    </#if>
                    <td class="text-center">${u.phone!}</td>
                    <td class="text-center">${u.idCard!}</td>

                    <#if u.flag=='3'>
                        <td id="flag_${u_index}" class="text-center text-success">已认证</td>
                        <td class="text-center"><a id="${u_index}" onClick="disableUser('${u.id}','${u_index}');"
                                                   style="color: #00a2ff;text-decoration:underline;">禁用</a></td>
                    <#elseif u.flag=='4'>
                        <td id="flag_${u_index}" class="text-center text-danger">认证失败</td>
                        <td class="text-center"><a id="${u_index}" onClick="disableUser('${u.id}','${u_index}');"
                                                   style="color: #00a2ff;text-decoration:underline;">禁用</a></td>
                    <#elseif u.flag=='1'>
                        <td id="flag_${u_index}" class="text-center text-danger">未认证</td>
                        <td class="text-center"><a id="${u_index}" onClick="disableUser('${u.id}','${u_index}');"
                                                   style="color: #00a2ff;text-decoration:underline;">禁用</a></td>
                    <#elseif u.flag=='2'>
                        <td id="flag_${u_index}" class="text-center">认证中</td>
                        <td class="text-center"><a id="${u_index}" onClick="disableUser('${u.id}','${u_index}');"
                                                   style="color: #00a2ff;text-decoration:underline;">禁用</a></td>
                    <#elseif u.flag =='9'>
                        <td id="flag_${u_index}" class="text-center text-danger">已禁用</td>
                        <td class="text-center">
                            <a id="${u_index}" onClick="enableUser('${u.id}','${u_index}');"
                               style="color: #00a2ff;text-decoration:underline;">
                                启用
                            </a>
                        </td>
                    <#else>
                        <td id="flag_${u_index}" class="text-center">其他</td>
                        <td class="text-center"><a id="${u_index}" onClick="disableUser('${u.id}','${u_index}');"
                                                   style="color: #00a2ff;text-decoration:underline;">禁用</a></td>
                    </#if>
                </tr>
                </#list>
            </tbody>
        </table>
        <div id="kkpager" class="bot_bot kkpager" style="margin-bottom: 50px;">
        </div>
    </div>
</div>
</#if>
</div>
<#include "include/footer.ftl"/>
    <script type="text/javascript">

        $(function () {
        <#if user??>
        <#else>
            location.href = "/yhdl/index.jhtml";
        </#if>
            fykj();
        <#if quser??>
            <#if quser.flag??>
            $("select[name='flag']").val('${quser.flag!}');
            </#if>
            <#if quser.type??>
            $("select[name='type']").val('${quser.type!}');
            </#if>
        </#if>
        });

        function showloading(){
            $("#loading").show();
            return true;
        }

        function fykj() {
        <#if page?? && (page.totalCount>10)>
            //生成分页控件
            kkpager.generPageHtml({
                pagerid: "kkpager",
                pno: ${page.pageNo},
                mode: 'click',
                //总页码
                total: Math.ceil(${page.totalCount/page.pageSize}),
                //总数据条数
                totalRecords: ${page.totalCount},
                //链接算法
                click: function (n) {

                    var condition ="";
                  var flag =  $("select[name='flag']").val();
                  if(!flag && flag!=0){
                      condition=condition+"&flag="+flag
                  }
                  var type = $("select[name='type']").val();
                    if(!type && type!='0'){
                        condition=condition+"&type="+type
                    }
                  var userName = $("input[name='userName']").val().trim();
                    if(!userName && userName!=''){
                        condition=condition+"&userName="+userName
                    }
                  var loginName =  $("input[name='loginName']").val().trim();
                    if(!loginName && loginName!=''){
                        condition=condition+"&loginName="+loginName
                    }
                  var idCard=  $("input[name='idCard']").val().trim();
                    if(!idCard && idCard!=''){
                        condition=condition+"&idCard="+idCard
                    }
                  var phone =  $("input[name='phone']").val().trim();
                    if(!phone && phone!=''){
                        condition=condition+"&phone="+phone
                    }

                    //获取第n页数据
                    location.href= "/credit/sys/user/userList.do?currentPage=" + n +condition+ "&pageNum=${page.pageSize}";
                }
            }, true);
        </#if>
        }

        function disableUser(dataId, eleId) {

            $.ajax({
                url: encodeURI("/credit/sys/user/disableUser.do"),
                dataType: 'json',
                data: {id: dataId},
                success: function (data) {
                    var p = $("#" + eleId).parent();
                    var flag = $("#flag_" + eleId);
                    var msg = $("#disableMsg");
                    if (data) {
                        if (data.flag == true) {
                            flag.removeClass("text-success");
                            flag.addClass("text-danger");
                            flag.html("已禁用");
                            p.empty();
                            p.append('<a id="' + eleId + '" onClick="enableUser(\'' + dataId + '\',\'' + eleId + '\');" style="color: #00a2ff;text-decoration:underline;">启用 </a>');
                            msg.css("color", "#28ff28");
                            msg.html(data.msg);
                        } else {
                            msg.css("color", "red");
                            msg.html(data.msg);
                        }
                    } else {
                        msg.css("color", "red");
                        msg.html(data.msg);
                    }
                },
                error: function (response) {
                    var s = response;
                    $("#disableMsg").html(s.statusText);
                },
                timeout: 60000
            });
        }

        function enableUser(dataId, eleId) {
            var a = $("#" + eleId);
            ;
            $.ajax({
                url: encodeURI("/credit/sys/user/enableUser.do"),
                dataType: 'json',
                data: {id: dataId},
                success: function (data) {
                    var p = $("#" + eleId).parent();
                    var flag = $("#flag_" + eleId);
                    var msg = $("#disableMsg");
                    if (data) {
                        var text = "";
                        var clazz = "";
                        if (data.flag == true) {
                            switch (data.userFlag) {
                                case "1":
                                    text = "未认证";
                                    break;
                                case "2":
                                    text = "认证中";
                                    break;
                                case "3":
                                    text = "已认证";
                                    clazz = "text-success";
                                    break;
                                case "4":
                                    text = "认证失败";
                                    clazz = "text-danger";
                                    break;
                                case "9":
                                    text = "已禁用";
                                    clazz = "text-danger";
                                    break;
                                default:
                                    ;
                            }
                            flag.removeClass("text-danger");
                            flag.removeClass("text-success");
                            flag.addClass(clazz);
                            flag.html(text);
                            p.empty();
                            p.append('<a id="' + eleId + '" onClick="disableUser(\'' + dataId + '\',\'' + eleId + '\');" style="color: #00a2ff;text-decoration:underline;">禁用</a>');
                            msg.css("color", "#28ff28");
                            msg.html(data.msg);
                        } else {
                            msg.css("color", "red");
                            msg.html(data.msg);
                        }
                    } else {
                        msg.css("color", "red");
                        msg.html(data.msg);
                    }
                },
                error: function (response) {
                    var s = response;
                    $("#disableMsg").html(s.statusText);
                },
                timeout: 60000
            });
        }

    </script>
</body>
</html>