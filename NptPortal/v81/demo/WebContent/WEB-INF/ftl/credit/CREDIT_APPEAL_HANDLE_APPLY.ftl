<#--<#include "/template/Credit_Common/common.ftl">-->
<!-------------------------------------企业信用信息异议申请表-------------------------------------------->
<form action="/credit/srv/addAppeal.do" onsubmit="return addAppeal(this)" class=" no-bottom-space"  >
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModal-list"><i class="glyphicon glyphicon-pencil"></i> 信用信息异议申请表</h4>
            </div>
            <div class="modal-body">
                <div class="scroller" data-height="500px">
                    <div class="portlet boxTheme">
                        <div class="portlet-title">
                            <div class="caption"><i class="glyphicon glyphicon-search"></i> 异议申请表</div>
                        </div>
                        <div class="portlet-body">
                            <table class="table table-bordered"  >
                                <tbody>
                                <tr>
                                    <td width="15%" align="right" title="${byPKField.fieldName}">异议${byPKField.fieldName}：</td>
                                    <td colspan="3" width="85%" align="left" class="bold" title="${byPKField.fieldValue}">${byPKField.fieldValue}</td>
                                </tr>
                                <tr>
                                    <td width="15%" align="right" >信息源单位：</td>
                                    <td width="35%" align="left" class="bold"  name="org">${orgInfo.orgName!}</td>
                                    <td width="15%" align="right"  >异议信息类名称：</td>
                                    <td width="35%" align="left" class="bold"  >${dataTypeInfo.alias!}</td>
                                </tr>
                                <tr>
                                    <td align="right"  >异议申请人：</td>
                                    <td align="left"  ><input type="text" name="appealUser" required class="form-control" placeholder="请输入申请人姓名" value="${userExt.realname}"></td>
                                    <td align="right"  >联系电话：</td>
                                    <td align="left"  ><input type="text"  class="form-control" maxlength="11" required name="appealUserTel" placeholder="请输入申请人联系电话" value="${user.mobile!}"> </td>
                                </tr>
                                <tr>
                                    <td align="right"  >邮箱：</td>
                                    <td align="left" ><input type="text" class="form-control" name="appealUserEmail" placeholder="请输入申请人邮箱" value="${user.email!}"> </td>
                                    <td align="right"  >登记人：</td>
                                    <td align="left">${userExt.realname}</td>
                                </tr>

                                <tr  >
                                    <td  colspan="4" align="left"> <input type="file" name="attachmentDir" class="filestyle" data-classButton="btn btn-primary" data-input="false" data-classIcon="icon-plus" data-buttonText="添加附件"></td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="portlet boxTheme">
                        <div class="portlet-title">
                            <div class="caption"><i class="glyphicon glyphicon-search"></i> 异议修改建议</div>
                        </div>
                        <div class="portlet-body">
                            <table class="table table-bordered table-condensed margin-bottom-10">
                                <caption style="color: red"><h4>请选择需要修改的异议项</h4></caption>
                                <tr>
                                    <td class="allChose" width="11%">
                                        <div class="controls  pull-right">
                                            <label class="checkbox"><input type="checkbox"  id="checkBtn" onclick="checkAll()">全选：</label>
                                        </div>
                                    </td>
                                    <td>
                                        <div class="controls scroller fieldScroller" id=""  data-height="112px" style=" height: 112px;">
                                        <#if fieldList??>
                                            <#list fieldList as c>
                                                <label class="checkbox">
                                                    <input type="checkbox" name="field" class="fieldList" value="${c.id}" >${c.fieldName!}
                                                </label>
                                            </#list>
                                        </#if>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <table class="tableYiyi table table-bordered table-condensed">
                                <thead>
                                <tr>
                                    <th width="30%">异议项</th>
                                    <th width="40%">系统信息</th>
                                    <th width="40%">异议信息</th>
                                </tr>
                                </thead>
                                <tbody id="dataList" >
                                <#if fieldList??>
                                    <#list fieldList as field>
                                    <tr id="tr-${field.id}" class="hide">
                                        <td title="${field.alias!}">${field.alias!}</td>
                                        <td title="${field.fieldValue!}">${field.fieldValue!}</td>
                                        <td><input type="text" class="m-wrap field form-control" id="${field.id}"></td>
                                    </tr>
                                    </#list>
                                <#else >
                                暂无字段
                                </#if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <input type="hidden" name="key" value="${key!}">
                <input type="hidden" name="orgName" value="${orgInfo.orgName!}">
                <input type="hidden" name="dtTitle" value="${dataTypeInfo.typeName!}">
                <input type="hidden" name="orgId" value="${orgInfo.id}">
                <input type="hidden" name="byPKFieldId" value="${byPKField.id}">
                <input type="hidden" name="appealDTID"  value="${dataTypeInfo.id}">
                <input type="hidden" name="appealFields" >
                <button type="submit" class="btn btn-primary" ><i class="glyphicon glyphicon-ok"></i> 确认申请</button>
                <button type="button" class="btn" data-dismiss="modal"><i class="glyphicon glyphicon-remove"></i>
                    &nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
            </div>
        </div>
    </div>
</form>

<script>

    function checkAll(){
        console.log("1");
        $(".fieldList").stop().prop("checked",true);
        $("#checkBtn").attr("onclick","unCheckAll()");
        $("#dataList tr").removeClass("hide").show();
    }
    function unCheckAll(){
        $(".fieldList").removeAttr("checked");
        $("#checkBtn").attr("onclick","checkAll()");
        $("#dataList tr").hide()
    }
    $(".fieldList").each(function () {
        $(this).click(function () {
            if ($(this).prop("checked")){
                $("#tr-"+$(this).val()).removeClass("hide").show();
                if($(".fieldList:checked").length == $(".fieldList").length){
                    $("#checkBtn").attr("onclick","unCheckAll()").attr("checked",true).parent().addClass("checked");
                }
            }else {
                $("#tr-"+$(this).val()).hide();
                $("#tr-"+$(this).val()).find("input").val("");
                if($(".fieldList:checked").length != $(".fieldList").length){
                    $("#checkBtn").attr("onclick","checkAll()").attr("checked",false).parent().removeClass("checked");
                }
            }
        });
    });
    function addAppeal(form) {
        if (handelFields()){
            $.post(form.action,$(form).serialize(),function(data){
                if (data.result){
                    $("#appealDiv").modal("hide");
                    returnInfo(true,data.message||"操作成功！");
                }else {
                    returnInfo(false,data.message||"操作失败！");
                }
            });
        } else {
            returnInfo(false,"请至少提交一个异议信息！");
        }
        return false;
    }
    function handelFields(){
        var fields=[];
        $(".field").each(function () {
            if($(this).val() != ""){
                var field={};
                field.id = $(this).attr("id");
                field.value = $(this).val();
                field.default=  $(this).parent().prev().text();
                fields.push(field);
            }
        });
        $("input[name='appealFields']").val(JSON.stringify(fields));
        if (fields.length == 0){
            return false;
        }
        return true;
    }
</script>