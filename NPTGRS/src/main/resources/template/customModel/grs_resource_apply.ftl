<#assign data =  NPT_ACTION_RETURNED_JSON>
<#if data.dataList??>

<form  onsubmit="return applyCol(this)" role="form" class="no-bottom-space">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-legal"></i> 信用信息申请表</h4>
    </div>
    <div class="modal-body">
        <table class="table table-bordered"  >
            <tbody>
            <tr>
                <td  width="15%" align="right" >申请查看信息：</td>
                <td align="left" colspan="3" class="bold" title="${data.primaryKeyValue!}">${data.primaryKeyValue!}</td>
             </tr>
            <tr>
                <td  align="right"  >信息源单位：</td>
                <td width="35%" align="left" class="bold" title="${data.dataList.parentInfo.orgName!}">${data.dataList.parentInfo.orgName!}</td>
                <td width="10%" align="right"  >信息类名称：</td>
                <td width="40%" align="left" class="bold" title="${data.dataList.blockInfo.poolTitle!}">${data.dataList.blockInfo.poolTitle!}</td>
            </tr>
            <tr <#if data.dataList.readOnly==1>class="disabled" </#if>>
                <td align="right"  >
                    <div class="controls">
                        <label class="checkbox"><input type="checkbox" id="checkBtn" <#if data.dataList.readOnly==1>disabled</#if> onclick="checkAll()">信息项：</label>
                    </div>
                </td>
                <td colspan="3" align="left">
                    <div class="controls scroller" id="fields"  data-height="112px">
                        <#if data.dataList.columnList??>
                            <#list data.dataList.columnList as col>
                                <label class="checkbox">
                                    <input type="checkbox" name="field"  class="field" value="${col.id?string("#")}" <#if data.dataList.readOnly==1>disabled</#if>  <#if col.bFlag==true> checked</#if>>${col.fieldName}
                                </label>
                            </#list>
                        <#else >
                            <label>当前暂无字段可申请</label>
                        </#if>
                    </div>

                    <input type="hidden" name="fieldIds" >
                </td>
            </tr>
            <tr <#if data.dataList.readOnly==1>class="disabled" </#if>>
                <td align="right"  >联系电话：</td>
                <td align="left" ><input type="text" required name="applyUserTel" <#if data.dataList.readOnly==1>disabled</#if>></td>
                <td align="right"  >计划查看时间：</td>
                <td align="left" >
                    <div class="controls demo">
                        <i class="icon-calendar"></i>
                        <#if data.dataList.readOnly==1>
                            <input type="text" disabled value="${data.dataList.startTime?string('yyyy/MM/dd')!} - ${data.dataList.endTime?string('yyyy/MM/dd')!}">
                            <#else >
                                <input id="form-date-range"  type="text" >
                        </#if>
                        <input type="hidden" name="startDate">
                        <input type="hidden" name="endDate">
                    </div>
                </td>
            </tr>
            <tr <#if data.dataList.readOnly==1>class="disabled" </#if>>
                <td align="right"  >申请原因：</td>
                <td colspan="3" align="left">
                    <div class="controls">
                        <textarea name="applyReason" required  rows="3" class="span6 m-wrap" <#if data.dataList.readOnly==1>disabled</#if>></textarea>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="right"  >申请单位：</td>
                <td id="applyUserOrg" align="left"  >${data.dataList.strTempThree}</td>
                <td align="right"  >申请人：</td>
                <td align="left" ><input type="hidden" name="applyUserName" value="${currUserName}" >${currUserName}</td>
            </tr>
            <tr>
                <td align="right"  >申请状态：</td>
                <td align="left" ><#if data.dataList.strTempOne??>${data.dataList.strTempOne}</#if></td>
                <td align="right"  >申请结果：</td>
                <td align="left" ><#if data.dataList.applyStatus?? && data.dataList.applyStatus == 4 && data.dataList.strTempTwo??>${data.dataList.strTempTwo}</#if></td>
            </tr>
            <tr>
                <td colspan="4" class="error"><div class="control-label"><span class="required">请选择一个信息项</span></div></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="modal-footer">
        <input type="hidden" name="applyPoolId" value="${data.parentId}">
        <input type="hidden" name="applyBusinessKey" value="${data.primaryKeyValue}">
        <input type="hidden" name="providerId" value="${data.dataList.parentInfo.id}">
        <input type="hidden" name="providerTitle" value="${data.dataList.parentInfo.orgName}">
        <input type="hidden" name="applyPoolTitle" value="${data.dataList.blockInfo.poolTitle}">
        <#if !(data.dataList.readOnly==1)>
            <button type="submit" class="btn blue"><i class="icon-ok icon-white"></i>&nbsp;&nbsp;确认申请</button>
        </#if>
        <#--<button type="button" class="btn"><i class="icon-save"></i>&nbsp;&nbsp;暂&nbsp;&nbsp;&nbsp;&nbsp;存</button>-->
        <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i>&nbsp;&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消</button>
    </div>
</form>
</#if>
<script>
    App.initUniform();
    App.handleScrollers();
    $('#form-date-range').daterangepicker({
        opens:'left'
        , startDate: Date.today()
        , endDate: Date.today().add({
            days: +7
        })
        ,showDropdowns:true
    }, function (start, end,label) {
        $("input[name='startDate']").val(start.format('YYYY-MM-DD'));
        $("input[name='endDate']").val(end.format('YYYY-MM-DD'));
    });
     $("input[name='startDate']").val( Date.today().toString('yyyy-MM-dd'));
     $("input[name='endDate']").val( Date.today().add({days: +7}).toString('yyyy-MM-dd'));

    $(".field").each(function () {
        $(this).click(function () {
            if ($(this).prop("checked")){
                $("#tr-"+$(this).val()).show();
                if($(".field:checked").length == $(".field").length){
                    $("#checkBtn").attr("onclick","unCheckAll()").attr("checked",true).parent().addClass("checked");
                }
            }else {
                $("#tr-"+$(this).val()).hide();
                $("#tr-"+$(this).val()).find("input").val("");
                if($(".field:checked").length != $(".field").length){
                    $("#checkBtn").attr("onclick","checkAll()").attr("checked",false).parent().removeClass("checked");
                }
            }
        });
    });
</script>