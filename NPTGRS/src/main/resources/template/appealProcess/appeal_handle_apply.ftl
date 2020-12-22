<#include "/template/Credit_Common/common.ftl">
    <!-------------------------------------企业信用信息异议申请表-------------------------------------------->
    <form action="${ctx}/npt/grs/appeal/addAppeal.action" onsubmit="return addAppeal(this)" class=" no-bottom-space"  >
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-legal"></i> 信用信息异议申请表</h4>
    </div>
    <div class="modal-body">
        <div class="scroller" data-height="500px">
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption"><i class="icon-search"></i> 异议申请表</div>
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
                        <td align="left"  ><input type="text" name="appealUser" required class="span2" placeholder="请输入申请人姓名"></td>
                        <td align="right"  >联系电话：</td>
                        <td align="left"  ><input type="text"  class="span2" maxlength="11" required name="appealUserTel" placeholder="请输入申请人联系电话"> </td>
                    </tr>
                    <tr>
                        <td align="right"  >邮箱：</td>
                        <td align="left" ><input type="text" class="span2" name="appealUserEmail" placeholder="请输入申请人邮箱"> </td>
                        <td align="right"  >登记人：</td>
                        <td align="left">${currUserName}</td>
                    </tr>

                    <tr>
                        <td align="right">说明：</td>
                        <td  colspan="3" align="left"> <textarea style="width: 98%;" name="appeal.desc" rows="3"></textarea></td>
                    </tr>
                    <tr>
                        <td align="right">附件：</td>
                        <td  colspan="3" align="left">
                            <div id="upload_file"></div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption"><i class="icon-search"></i> 异议修改建议</div>
            </div>
            <div class="portlet-body">
                <table class="table table-bordered table-condensed margin-bottom-10">
                    <caption style="color: red"><h4>请选择需要修改的异议项</h4></caption>
                    <tr>
                        <td width="11%">
                            <div class="controls  pull-right">
                                <label class="checkbox"><input type="checkbox"  id="checkBtn" onclick="checkAll()">全选：</label>
                            </div>
                        </td>
                        <td>
                            <div class="controls scroller fieldScroller" id=""  data-height="112px">
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
                <table class="table table-bordered table-condensed"  >
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
                                    <td><input type="text" class="m-wrap field" id="${field.id}"></td>
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
        <button type="submit" class="btn blue"  ><i class="icon-ok"></i> 确认申请</button>
        <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i>
            &nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
    </div>
</form>

    <script>
        App.initUniform();
        App.handleScrollers();

        $(function () {
            $("#upload_file").initUploader({
                contextPath : "${ctx}",
                name : "upload_file",
                file_size_limit : "2MB", //每次上传的文件大小，必须小于struts中设置的值
                file_types : "*.jpg;*.png;*.zip;*.rar", //限制上传文件的类型，如:"*.txt",多种文件类型:"*.xls;*.xlsx"
                file_types_description : "图片和压缩包", //在文件选择对话框中显示的允许上传的文件类型
                file_upload_limit: 1
            });
        });

        function checkAll(){
            $(".fieldList").attr("checked",true).parent().addClass("checked");
            $("#checkBtn").attr("onclick","unCheckAll()");
            $("#dataList tr").show();
        }
        function unCheckAll(){
            $(".fieldList").attr("checked",false).parent().removeClass("checked");
            $("#checkBtn").attr("onclick","checkAll()");
            $("#dataList tr").hide()
        }
        $(".fieldList").each(function () {
            $(this).click(function () {
                if ($(this).prop("checked")){
                    $("#tr-"+$(this).val()).show();
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
    </script>