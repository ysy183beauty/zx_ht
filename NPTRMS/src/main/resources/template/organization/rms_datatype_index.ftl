<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<head>
    <script src="${wctx}/pub/CreditStyle/tabledNd/jquery.min.js"></script>
    <script src="${wctx}/pub/CreditStyle/tabledNd/jquery.tablednd.js"></script>
    <script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/colResizable-1.5.min.js"></script>
</head>

<body>
<div class="widget-box">
    <div class="widget-content nopadding" id="indexPage">
        <div class="row-fluid">
            <div class="span12">
                <div class="portlet box boxTheme">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="icon-globe"></i>
                            数据库表管理
                        </div>

                        <div class="tools">
                            <button class="btn  purple mini" onclick="scanTable()">扫描表</button>
                            <a href="javascript:;" class="collapse"></a>

                        </div>

                    </div>
                    <div class="portlet-body" id="indexPage">

                        <div class="row-fluid">
                            <label for="" class="pull-left">机构列表 ：<select name="" id="" class="orgList medium m-wrap"></select></label>
                        </div>
                        <table id="dataTypeList"  class="table table-bordered data-table dataTable table-hover">
                            <thead>
                            <tr>
                                <th width="5%">序号</th>
                                <th width="15%">名称</th>
                                <th width="15%">别名</th>
                                <th width="15%">所属表</th>
                                <th width="13%">创建时间</th>
                                <th width="12%">数据主键</th>
                                <th width="10%">状态</th>
                                <th width="15%">操作管理</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#include "rms_datatype_list.ftl">
                            </tbody>
                        </table>
                    </div>
                <#--<div id="editDiv" class="portlet-body"></div>-->
                </div>
            </div>
        <#--<div class="span1"></div>-->
        </div>
        <div class="row-fluid">

            <div class="span12">
                <div class="portlet box boxTheme">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="icon-sitemap"></i>
                            <a name="columnDiv"></a>
                            数据库字段管理
                        </div>
                        <div class="tools">
                            <button class="btn yellow mini" onclick="setBusinessCode()"><i class="icon-pushpin"></i>设置码值</button>
                            <button class="btn yellow mini" onclick="setDataTypeUField()"><i class="icon-pushpin"></i>设为数据主键</button>
                            <button class="btn blue mini" onclick="batchUpdate()">批量修改</button>
                            <button class="btn green mini" onclick="dragSort()">激活拖动排序</button>
                            <button class="btn yellow mini" onclick="saveSort()">更新顺序</button>
                            <button class="btn purple mini" onclick="scanColumn()">扫描列</button>
                            <button class="btn purple mini" onclick="updateSelectCondtion()">修改查询条件</button>
                            <button class="btn blue mini" onclick="公开级别()" style="display: none;">公开级别</button>
                            <button class="btn green mini" onclick="显示方式()" style="display: none;">显示方式</button>
                            <a href="javascript:;" class="collapse"></a>
                        </div>
                    </div>
                    <div class="portlet-body" >
                        <table id="dataFieldList"  class="table table-bordered data-table dataTable table-hover">
                            <thead>
                            <tr>
                                <th width="5%">序号</th>
                                <th width="18%">名称</th>
                                <th width="17%">别名</th>
                                <th width="15%">数据库表名</th>
                                <th width="15%">创建时间</th>
                                <th width="10%">状态</th>
                                <th width="20%">操作管理</th>
                            </tr>
                            </thead>
                            <tbody id="dataFieldListTBody">

                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        <#--<div class="span1"></div>-->
        </div>
    </div>
</div>
<div id="editField" class="modal hide fade" tabindex="-1" data-width="560"></div>
<div id="scanDiv" class="modal hide fade" tabindex="-1" data-width="560">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
        <h4><i class="icon-legal"></i> 扫描</h4>
    </div>
    <div class="modal-body ">
        <table id="scanTb" class="table table-bordered data-table dataTable table-hover">
            <tbody>

            </tbody>
        </table>

    </div>
    <div class="modal-footer">
        <button type="button" class="btn" data-dismiss="modal"><i class="icon-remove"></i>&nbsp;&nbsp;关&nbsp;&nbsp;&nbsp;&nbsp;闭
        </button>
    </div>

</div>
<script type="text/javascript">
    $(function () {
        TableManaged.init("dataTypeList");
        $("#dataTypeList").colResizable({
            liveDrag:true,
            draggingClass:"dragging"
        });
        TableManaged.simpleInit("dataFieldList");
        $("#dataFieldList").colResizable({
            liveDrag:true,
            draggingClass:"dragging"
        });
    });
    showOrgList();
    /**
     * 加载机构列表
     * */
    function showOrgList() {
        $.post("${ctx}/npt/rms/org/selectUnitInfoForSelect.action",function (data) {
            var html="<option value=''>全部</option>";
            for (var i=0;i<data.length;i++){
                html+='<option value="'+data[i].id+'">'+data[i].name+'</option>';
            }
            $(".orgList").html(html);
        });
    }
    $(".orgList").change(function () {
        $.post("list.action",{parentId:$(this).val()},function (data) {
            TableManaged.draw("dataTypeList",data);
        })
    });
    /***
     *查看字段
     * */
    var pid;
    function showColumn(id) {
        pid=id;
        $.post("${ctx}/npt/rms/datafield/list.action",{parentId:id},function (data) {
            TableManaged.simpleDraw("dataFieldList",data)
            $("#dataFieldList").dataTable().api().column(0)
                    .nodes()
                    .to$()
                    .text(function (i, t) {
                        $(this).empty().append("<input name='dataField' value='" + $(this).parent().attr("id") + "' type='checkbox'>" + t)
                    });
            App.initUniform();
        })
    }
    /***
     * 扫描表
     * */
    function scanTable(){
        $.ajax({
            url:"${ctx}/npt/dsp/scan/scanTables.action",
            timeout:30000,
            beforeSend:function(){
                $(".loadDiv").show();
            },
            success:function (data) {
                $(".loadDiv").hide();
                showScan(data);

            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                alert(XMLHttpRequest+","+textStatus+","+errorThrown);
            },
        });
    }
    /***
     * 扫描列
     * */
    function scanColumn(){
        $.ajax({
            url:"${ctx}/npt/dsp/scan/scanColumns.action",
            timeout:60000,
            beforeSend:function(){
                $(".loadDiv").show();
            },
            success:function (data) {
                $(".loadDiv").hide();
                showScan(data);
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                returnInfo(false,XMLHttpRequest+","+textStatus+","+errorThrown);
            },
        });
    }
    /***
     *显示扫描信息
     * */
    function showScan(data){
        $("#scanDiv").modal("show");
        var tbody="<tr><td width='40% '>新增数量</td><td>"+data.insertCount+"</td> </tr>" +
                "<tr><td>保持数量</td><td>"+data.updateCount+"</td> </tr>" +
                "<tr><td>删除数量</td><td>"+data.deletedCount+"</td></tr>" +
                "<tr><td>未知数量</td><td>"+data.unknowCount+"</td></tr>";
        $("#scanTb  tbody").html(tbody);
    }
    /***
     *修改数据类别
     * */
    function editType(obj,id) {
        $(obj).attr("onclick","saveType(this,'"+id+"')").html("<i class='icon-ok '></i> 保存");
        var tr=$(obj).parent().parent().parent().find("td");

        var select=false;
        if (tr[5].innerHTML=="启用"){
            select=true;
        }
        tr[2].innerHTML = '<input type="text" name="alias" maxlength="15" value="' + tr[2].innerHTML + '"   style="width:90%;"  >';
        tr[6].innerHTML = '<select name="status" style="width:100%">' +
                '<option '+(select?"selected":"")+' value="1">启用</option>' +
                '<option '+(select?"":"selected")+' value="0">禁用</option>' +
                '</select>';
    }
    /***
     *保存类别修改
     * */
    function saveType(obj,id){
        var $input=$(obj).parent().parent().parent().find("td input");
        var $select= $(obj).parent().parent().parent().find("td select");
        $.post("edit.action",{id:id,alias:stripscript($("input[name='alias']").val()),status:$("select[name='status']").val()},function (data) {
            if(data.result){
                $input.parent()[0].innerHTML=$input.val();
                $select.parent()[0].innerHTML=($select.val()==1?"启用":"禁用");
                $(obj).attr("onclick","editType(this,'"+id+"')").html("<i class='icon-edit '></i> 编辑");
                returnInfo(true,data.message||"操作成功！");
            } else {
                returnInfo(false,data.message || "操作失败！");
            }
        });

    }
    /***
     *字符串校验
     * */
    function stripscript(value) {
        var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]")
        var rs = "";
        for (var i = 0; i < value.length; i++) {
            rs = rs+value.substr(i, 1).replace(pattern, '');
        }
        return rs;
    }
    /***
     *修改字段
     * */
    function editField(id) {
        $.post("${ctx}/npt/rms/datafield/editPage.action",{id:id},function (data) {
            $("#editField").html(data);
            $("#editField").modal("show");
        })
    }
    /***
     *保存字段修改
     * */
    function submitForm(form) {
        $.post(form.action, $(form).serialize(), function (data) {
            if (data.result) {
                $("#editField").modal("hide");
                showColumn(pid);
                returnInfo(true,data.message||"操作成功！");
            } else {
                returnInfo(false,data.message || "操作失败！");
            }
        });
        return false;
    }

    /**
     * 拖拽排序
     */
    function dragSort() {
        /**
         * 表格拖拽排序
         * */
        //var dataFieldTrList = document.getElementById("dataFieldListTBody");
        //Sortable.create(dataFieldTrList, { group: "omega" });
        $("#dataFieldList").tableDnD();
    }
    /**
     * 更新字段显示顺序
     */
    function saveSort() {
        var sortedIdList = [];
        $('#dataFieldListTBody>tr').each(function () {
            sortedIdList.push($(this).attr('id'));
        });
        $.post(
                "${ctx}/npt/rms/datafield/sortField.action",
                {sortedIdList: sortedIdList.join(",")},
                function (data) {
                    if (data.result) {
                        returnInfo(true,data.message||"操作成功！");
                    }else{
                        returnInfo(false,data.message || "操作失败！");
                    }
                }
        )
    }

    /**
     * 设置查询条件信息
     * @param id
     */
    function updateSelectCondtion(){
        if ($("input[name='dataField']:checked").length != 1) {
            returnInfo(false, "请（只）选中一个字段！")
        }
        $.post("${ctx}/npt/rms/datafield/updateSelectCondition.action",
                {
                    typeId: pid,
                    fieldId: $("input[name='dataField']:checked").val(),
                    fieldName:$("input[name='dataField']:checked").parents("td").next().html(),
                    fieldEngName:$("input[name='dataField']:checked").parents("td").next().next().next().html()
                },
                function (data) {
                    if (data.result){
                        returnInfo(true,data.message||"操作成功！");
                    }else {
                        returnInfo(false,data.message||"操作失败！");
                    }
                })
    }

    /**
     * 设为数据主键
     * @param id
     */
    function setDataTypeUField() {
        if ($("input[name='dataField']:checked").length != 1) {
            returnInfo(false, "请（只）选中一个字段！")
        }
        $.post("${ctx}/npt/rms/datafield/setDataTypeUField.action",
                {
                    typeId: pid,
                    fieldId: $("input[name='dataField']:checked").val()
                },
                function (data) {
            if (data.result){
                // $.post("list.action",{parentId:$(".orgList").val()},function (data) {
                //     TableManaged.draw("dataTypeList",data);
                // })
                returnInfo(true,data.message||"操作成功！");
            }else {
                returnInfo(false,data.message||"操作失败！");
            }
        })
    }

    /**
     * 作者: 张磊
     * 日期: 2017/03/14 下午07:42
     * 备注: 设置码值
     */
    function setBusinessCode() {
        if ($("input[name='dataField']:checked").length != 1) {
            returnInfo(false, "请（只）选中一个字段！")
        }
        $.post("${ctx}/npt/rms/dict/list.action",
                {
                    fieldId: $("input[name='dataField']:checked").val()
                },
                function (data) {
                    $("#editField").html(data);
                    $("#editField").modal("show");
                })
    }

    /**
     * 作者: 张磊
     * 日期: 17/2/23 下午3:24
     * 备注: 批量修改
     */
    function batchUpdate(){
        var ids = [];
        $("input[name='dataField']:checked").each(function () {
            ids.push($(this).val());
        });
        $.post("${ctx}/npt/rms/datafield/batchUpdatePage.action",{ids:ids.join(",")},function (data) {
            $("#editField").html(data);
            $("#editField").modal("show");
        });
    }
</script>
</body>
</html>


