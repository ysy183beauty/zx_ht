<form action="addGroupool.action" onsubmit="return addPool(this)"  class="form-horizontal">
<div class="alert alert-info">
    <button class="close" data-dismiss="alert"></button>
    <strong>提示：</strong>每个分组的数据池为【一主多副】，模型的每个分组要围绕着分组的主数据池，配以副数据池的扩展信息，以便将该分组设计的更科学全面。
    <br>
    PK（primaryKey-模型主键，用于将模型中的数据表“串”起来，比如身份证号码，统一信用代码等）
    <br>
    <strong>根据数据量的实际大小[这里推荐100000为分界线]为数据池的业务主键创建索引，以提高查询效率</strong>
</div>
<div class="row-fluid">
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption"><i class="icon-edit"></i>机构-->数据类别-->字段</div>
                <div class="tools">
                    <button type="submit" class="btn green mini"><i class="icon-save"></i> 保存</button>
                    <a href="javascript:;" class="collapse"></a>
                </div>
            </div>
            <div class="portlet-body ">
                <div class="row-fluid">
                    <div class="span3">
                        <div class="control-group">
                            <label for="" class="ow control-label">别名</label>
                            <div class="ow controls">
                                <input type="text" name="pool.alias">
                            </div>
                        </div>
                    </div>
                    <div class="span3">
                        <div class="control-group">
                            <label for="" class="ow control-label">加锁级别</label>
                            <div class="ow controls">
                                <select class=" m-wrap" name="pool.lockLevel">
                                    <option value="0">请选择</option>
                                <#list _POOL_LOCKLEVELS as c>
                                    <option value="${c.code}">${c.title}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="span3">
                        <div class="control-group">
                            <label for="" class="ow control-label">机构列表</label>
                            <div class="ow controls">
                                <select class=" m-wrap" id="orgList">
                                    <option value="-1">请选择</option>
                                <#list _ORG_LIST as c>
                                    <option value="${c.id?string("#")}">${c.orgName!}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="span3">
                        <div class="control-group">
                            <label for="" class="ow control-label">数据类别</label>
                            <div class="ow  controls">
                                <select class=" m-wrap" name="dataTypeId">
                                    <option value="-1">请选择</option>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="row-fluid">
                    <div class="span3">
                        <div class="control-group">
                            <label for="" class="ow control-label">数据池类别</label>
                            <div class="ow  controls">
                                <select class=" m-wrap" name="poolType">
                                    <option value="CLD_ADDITION">普通数据池</option>
                                    <option value="CLD_MAIN">核心数据池</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="span3">
                        <div class="control-group">
                            <label for="scoreWeight" class="ow control-label">评分权重</label>
                            <div class="ow  controls">
                                <input type="number" max="100" min="-100" id="scoreWeight" name="pool.scoreWeight" value="0">
                            </div>
                        </div>
                    </div>
                    <div class="span3">
                        <div class="control-group">
                            <label for="dataHost" class="ow control-label">数据主体</label>
                            <div class="ow controls">
                                <select id="dataHost" class=" m-wrap" name="pool.dataHost">
                                    <#list _POOL_DATAHOSTS as c>
                                    <option value="${c.code!}">${c.title!}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="scroller" data-height="300px">
                    <table class="table table-striped table-hover table-bordered" id="dataField">
                        <thead>
                        <tr>
                            <th width="5%">序号</th>
                            <th width="15%">字段名称</th>
                            <th width="10%">访问权限</th>
                            <th width="10%">数据类型</th>
                            <th width="10%">当前状态</th>
                            <th width="10%">业务主键</th>
                            <th width="10%">查询条件</th>
                            <th width="10%">索引字段</th>
                            <th width="10%">标题字段</th>
                            <th width="10%">排序字段</th>
                        </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
</div>
    <input type="hidden" name="paramIdxFields">
    <input type="hidden" name="paramCdtFields">
    <input type="hidden" name="groupId" value="${_THIS_GROUP_ID}">
</form>
<script>
    App.handleTooltips();
    $("#orgList").live("change", function () {
        initPoolType();
        $.ajax({
            url: "listOrgDataTypes.action",
            data: {
                parentId: $(this).val()
            }
            , timeout: 30000,
            success: function (data) {
                var option;
                if (null != data && data.length != 0) {
                    var firstElementId = data[0].id;
                    for (var i = 0; i < data.length; i++) {
                        option += '<option value="' + data[i].id + '">' + data[i].typeName + '</option>';
                    }
                    $("select[name='dataTypeId']").html(option);

                    $.ajax({
                        url: "listDataTypeFields.action",
                        data: {
                            parentId: firstElementId
                        }
                        , timeout: 30000,
                        success: function (data) {
                            $("#dataField tbody").html(data);
                        }
                    });
                }else {
                    $("select[name='dataTypeId']").empty();
                    $("#dataField tbody").empty();
                }
            }
        });
    });

    $("select[name='dataTypeId']").live("change", function () {
        initPoolType();
        $.ajax({
            url: "listDataTypeFields.action",
            data: {
                parentId: $(this).val()
            }
            , timeout: 30000,
            success: function (data) {
                $("#dataField tbody").html(data);
            }
        });
    });

</script>