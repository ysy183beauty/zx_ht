<#include "/template/Credit_Common/common.ftl">
<form id="form_props" action="editSubDmsProps.${urlExt}" method="post" onsubmit="return editProps(this, refreshPoolList)" class="form-horizontal no-bottom-space" >
<div class="modal-header">
    <button data-dismiss="modal" class="close" type="button"></button>
    <h3><i class="icon-exclamation-sign"></i> 编辑属性</h3>
</div>
<div class="modal-body">
    <div class="alert alert-error hide">
        <button class="close" data-dismiss="alert"></button>
        You have some form errors. Please check below.
    </div>
    <div class="alert alert-success hide">
        <button class="close" data-dismiss="alert"></button>
        Your form validation is successful!
    </div>
    <input type="hidden" name="subDmsProps.id" value="${(subDmsProps.id)!}">
    <input type="hidden" name="subDmsProps.poolId" value="${(subDmsProps.poolId)!}">
    <input type="hidden" name="subDmsProps.uFieldId" value="${(subDmsProps.uFieldId)!}">
    <#--<div class="control-group">-->
        <#--<label class="control-label">预警上限值<span class="required">*</span></label>-->
        <#--<div class="controls pointFa">-->
            <#--<input type="number" min="0" max="100" class="span3 m-wrap plus_width" name="subDmsProps.upLimit" value="${(subDmsProps.upLimit)!}">-->
            <#--<p class="point-out">这是一段测试文字</p>-->
        <#--</div>-->
    <#--</div>-->
    <#--<div class="control-group">-->
        <#--<label class="control-label">预警下限值<span class="required">*</span></label>-->
        <#--<div class="controls pointFa">-->
            <#--<input type="number" min="0" max="100" class="span3 m-wrap plus_width" name="subDmsProps.lowLimit" value="${(subDmsProps.lowLimit)!}">-->
            <#--<p class="point-out">这是一段测试文字</p>-->
        <#--</div>-->
    <#--</div>-->
    <div class="control-group">
        <label class="control-label">预警值权重<span class="required">*</span></label>
        <div class="controls pointFa" id="percentBox">
            <input type="number" min="-100" max="100" class="span3 m-wrap plus_width qz_height" name="subDmsProps.disCount" value="${(subDmsProps.disCount)!}">
            <span id="percent">%</span>
            <p class="point-out">这是一段测试文字</p>
        </div>
    </div>
    <#--<div class="control-group">-->
        <#--<label class="control-label">计算方式<span class="required">*</span></label>-->
        <#--<div class="controls pointFa">-->
            <#--<select class="span3 plus_width" name="subDmsProps.computeType">-->
            <#--<#list CES_CPM_LIST as c>-->
                <#--<option value="${c.code}" <#if (subDmsProps.computeType)?? && (subDmsProps.computeType)?int == c.code>selected</#if>>${c.title}</option>-->
            <#--</#list>-->
            <#--</select>-->
            <#--<p class="point-out">这是一段测试文字</p>-->
        <#--</div>-->
    <#--</div>-->
    <div class="control-group">
        <label class="control-label">事件时间</label>
        <div class="controls chzn-controls pointFa">
            <input id="timeInputId" name="subDmsProps.whenFieldId" type="hidden" value="${(subDmsProps.whenFieldId)!}">
            <select id="timeStar" class="span3 chosen" data-placeholder="选择一个字段" data-with-diselect="1" onchange="time_Over()">
                <option value="-1"></option>
            <#if _POOL_FIELD_LIST??&&_POOL_FIELD_LIST?size gt 0>
                <#list _POOL_FIELD_LIST as c>
                    <option value="${c.id}" <#if (subDmsProps.whenFieldId)?? && (subDmsProps.whenFieldId)?string == c.id?string>selected</#if>>${c.alias}</option>
                </#list>
            </#if>
            </select>
            <span class="kg span1"></span>
            <select id="timeOver" class="span3 chosen" data-placeholder="选择一个字段" data-with-diselect="1" onchange="time_Over()">
                <option value="-1"></option>
            <#if _POOL_FIELD_LIST??&&_POOL_FIELD_LIST?size gt 0>
                <#list _POOL_FIELD_LIST as c>
                    <option value="${c.id}" <#if (subDmsProps.whenFieldId)?? && (subDmsProps.whenFieldId)?string == c.id?string>selected</#if>>${c.alias}</option>
                </#list>
            </#if>
            </select>
            <p class="point-out">这是一段测试文字</p>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">事件地点</label>
        <div class="controls chzn-controls pointFa">
            <select class="span3 chosen" data-placeholder="选择一个字段" data-with-diselect="1" name="subDmsProps.whereFieldId">
                <option value="-1"></option>
            <#if _POOL_FIELD_LIST??&&_POOL_FIELD_LIST?size gt 0>
                <#list _POOL_FIELD_LIST as c>
                    <option value="${c.id}" <#if (subDmsProps.whereFieldId)?? && (subDmsProps.whereFieldId)?string == c.id?string>selected</#if>>${c.alias}</option>
                </#list>
            </#if>
            </select>
            <p class="point-out">这是一段测试文字</p>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">事件内容</label>
        <div class="controls chzn-controls pointFa">
            <select class="span3 chosen" data-placeholder="选择一个字段" data-with-diselect="1" name="subDmsProps.whatFieldId">
                <option value="-1"></option>
            <#if _POOL_FIELD_LIST??&&_POOL_FIELD_LIST?size gt 0>
                <#list _POOL_FIELD_LIST as c>
                    <option value="${c.id}" <#if (subDmsProps.whatFieldId)?? && (subDmsProps.whatFieldId)?string == c.id?string>selected</#if>>${c.alias}</option>
                </#list>
            </#if>
            </select>
            <p class="point-out">这是一段测试文字</p>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">事件金额</label>
        <div class="controls chzn-controls pointFa">
            <input type="hidden"  id="moneyInputId" name="subDmsProps.amountFieldId" value="${(subDmsProps.amountFieldId)!}">
            <select id="monFirst" class="span2 chosen" data-placeholder="选择一个字段" data-with-diselect="1" onchange="mon_Last()">
                <option value="-1"></option>
            <#if _POOL_FIELD_LIST??&&_POOL_FIELD_LIST?size gt 0>
                <#list _POOL_FIELD_LIST as c>
                    <option value="${c.id}" <#if (subDmsProps.amountFieldId)?? && (subDmsProps.amountFieldId)?string == c.id?string>selected</#if>>${c.alias}</option>
                </#list>
            </#if>
            </select>
            <span class="monkg span1"></span>
            <select id="monSecond" class="span2 chosen" data-placeholder="选择一个字段" data-with-diselect="1" onchange="mon_Last()">
                <option value="-1"></option>
            <#if _POOL_FIELD_LIST??&&_POOL_FIELD_LIST?size gt 0>
                <#list _POOL_FIELD_LIST as c>
                    <option value="${c.id}" <#if (subDmsProps.amountFieldId)?? && (subDmsProps.amountFieldId)?string == c.id?string>selected</#if>>${c.alias}</option>
                </#list>
            </#if>
            </select>
            <span class="monkg span1"></span>
            <select id="monLast" class="span2 chosen" data-placeholder="选择一个字段" data-with-diselect="1" onchange="mon_Last()" >
                <option value="-1"></option>
            <#if _POOL_FIELD_LIST??&&_POOL_FIELD_LIST?size gt 0>
                <#list _POOL_FIELD_LIST as c>
                    <option value="${c.id}" <#if (subDmsProps.amountFieldId)?? && (subDmsProps.amountFieldId)?string == c.id?string>selected</#if>>${c.alias}</option>
                </#list>
            </#if>
            </select>
            <p class="point-out">这是一段测试文字</p>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">金额的计量单位</label>
        <div class="controls pointFa">
            <select class="span3 plus_width" name="subDmsProps.amountMU">
            <#list CES_MU_LIST as c>
                <option value="${c.code}" <#if (subDmsProps.amountMU)?? && (subDmsProps.amountMU)?string == c.code?string>selected</#if>>${c.title}</option>
            </#list>
            </select>
            <p class="point-out">这是一段测试文字</p>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">定性计算数据条数区间及区间值<span class="required">*</span></label>
        <div class="controls pointFa">
            <div class="inputBox" id="inputContentQjz">
                <input id="baseValue" type="text" class="span4 m-wrap qjz_input" name="subDmsProps.countIntervals" value="${(subDmsProps.countIntervals)!}" onchange="hideInput('inputEmptyQjz_select','baseValue')">
                <a class="box_a btn mini green tooltips" href="javascript:void(0)" onclick="giveValue('tableBox','plusQjz','baseValue')" data-placement="bottom" data-original-title="编辑区间值"><i class="icon-edit "></i></a>

            </div>
            <div id="inputEmptyQjz">
                <select name="" id="inputEmptyQjz_select" class="span3 plus_width qjz_select" onchange="showInput('inputEmptyQjz_select','baseValue')">
                    <option value="">无</option>
                </select>
            </div>
            <p class="point-out">这是一段测试文字</p>
            <div class="tableBox" id="tableBox">
                <table id="plusQjz" class="table table-bordered span4">
                    <tbody>
                        <#--<tr class="trInner" onchange="changeValue(plusQjz,baseValue)">-->
                            <#--<td><input type="text" class="min"></td>-->
                            <#--<td><input type="text" class="max" onchange="compareTd(this)"></td>-->
                            <#--<td><input type="text" class="intervalValue"></td>-->
                        <#--</tr>-->
                    </tbody>

                </table>
                <a class="btn mini blue tooltips" onclick="plusTd('plusQjz','plusQjz','baseValue')" data-placement="bottom" data-original-title="添加区间值"><i class="icon-plus "></i></a>
                <a id="minusQjz" class="btn mini red tooltips" onclick="minusTd('plusQjz',plusQjz,baseValue)" data-placement="bottom" data-original-title="添加区间值"><i class="icon-minus "></i></a>
            </div>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">定量计算功效标准值与阈值</label>
        <div class="controls pointFa">
            <div class="inputBox" id="inputContentFz">
                <input id="fzValue" type="text" class="span4 m-wrap qjz_input" name="subDmsProps.amountGear" value="${(subDmsProps.amountGear)!}" onchange="hideInput('inputEmptyFz_select','fzValue')">
                <a class="box_a btn mini green tooltips" href="javascript:void(0)" onclick="giveValue('tableBoxFz','plusFz','fzValue')" data-placement="bottom" data-original-title="编辑区间值"><i class="icon-edit "></i></a>
            </div>
            <div id="inputEmptyFz">
                <select name="" id="inputEmptyFz_select" class="span3 plus_width qjz_select" onchange="showInput('inputEmptyFz_select','fzValue')">
                    <option value="">无</option>
                </select>
            </div>
            <p class="point-out">这是一段测试文字</p>
            <div class="tableBox" id="tableBoxFz">
                <table id="plusFz" class="table table-bordered span4">
                    <tbody>

                    </tbody>

                </table>
                <a class="btn mini blue tooltips" onclick="plusTd('plusFz','plusFz','fzValue')" data-placement="bottom" data-original-title="添加区间值"><i class="icon-plus "></i></a>
                <a id="minusFz" class="btn mini red tooltips" onclick="minusTd('plusFz',plusFz,fzValue)" data-placement="bottom" data-original-title="添加区间值"><i class="icon-minus "></i></a>
            </div>
        </div>
    </div>
</div>
<div class="modal-footer">
    <button class="btn blue ok" type="submit"><i class="icon-ok"></i> 保 存 </button>
    <button class="btn" data-dismiss="modal" aria-hidden="true"><i class="icon-remove"></i> 取 消 </button>
</div>
</form>
<script type="text/javascript" src="${wctx}/pub/ces/cw/addTable.js"></script>
<#--判断是否有区间值-->
<script>
    $(function(){
        showHide("fzValue","inputContentFz","inputEmptyFz");
        showHide("baseValue","inputContentQjz","inputEmptyQjz");
    });
    $(".inputContent_td").each(function(){
       var txt=$(this).text();
       $("#inputEmptyFz_select").append("<option value=''>"+txt+"</option>");
       $("#inputEmptyQjz_select").append("<option value=''>"+txt+"</option>");
    });
//    显示与否
    function showHide(inp,inpContent,inpEmpty){
        var txt=$("#"+inp).val();
        if(txt == ""){
            $("#"+inpContent).hide();
            $("#"+inpEmpty).show();
        }
        else{
            $("#"+inpContent).show();
            $("#"+inpEmpty).hide();
        }
    }

    function showInput(select,inp){
        var sel_value=$("#"+select+">option:selected").text();

        if(sel_value != "无"){
            $("#"+inp).val(sel_value);
            $("#"+select).parent().hide();
            $("#"+inp).parent().show();
            $("#"+select).find("option").each(function(){
               var txt=$(this).text();
               if(txt == "无"){
                   $(this).attr("selected","selected")
               }
            });
        }
    }
    function hideInput(select,inp){
        var inp_value=$("#"+inp).val();
        console.log(inp_value);
        if(inp_value == ""){
            $("#"+inp).parent().hide();
            $("#"+select).parent().show();
        }
    }
    $("#minusFz").click(function(){
        hideInput('inputEmptyFz_select','fzValue');
    });
    $("#minusQjz").click(function(){
        hideInput('inputEmptyQjz_select','baseValue')
    });
</script>
<script>
    $(function(){
        backTime();
        backMoney();
    });
//    时间
    function backTime(){
        var timeValue=$("#timeInputId").attr("value");
        if(timeValue != ""){
            if( timeValue.indexOf("#") >= 0 ){
                var arr=timeValue.split("#");
                console.log(arr);
                $("#timeStar option[value="+arr[0]+"]").attr("selected","selected");
                $("#timeOver option[value="+arr[1]+"]").attr("selected","selected");
            }
            else{
                $("#timeStar option[value="+timeValue+"]").attr("selected","selected");
            }
        }
    }

    function time_Over(){
        var starVal=$("#timeStar option:selected").attr("value");
        var overVal=$("#timeOver option:selected").attr("value");

        $("#timeInputId").attr("value",starVal+"#"+overVal);
        console.log($("#timeInputId").attr("value"));
    }
//    金额
    function backMoney(){
        var monValue=$("#moneyInputId").attr("value");
        console.log(monValue);
        if( monValue != ""){
            console.log(monValue);
            if( monValue.indexOf("#") >= 0 ){
                var arr=monValue.split("#");
                console.log(arr);
                $("#monFirst option[value="+arr[0]+"]").attr("selected","selected");
                $("#monSecond option[value="+arr[1]+"]").attr("selected","selected");
                $("#monLast option[value="+arr[2]+"]").attr("selected","selected");
            }
            else{
                $("#monFirst option[value="+monValue+"]").attr("selected","selected");
            }
        }
    }

    function mon_Last(){
        var starVal=$("#monFirst option:selected").attr("value");
        var secondVal=$("#monSecond option:selected").attr("value");
        var overVal=$("#monLast option:selected").attr("value");
        $("#moneyInputId").attr("value",starVal+"#"+secondVal+"#"+overVal);
        console.log($("#moneyInputId").attr("value"));
    }
</script>