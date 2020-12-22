<form action="updateGroupool.action" onsubmit="return editPool(this)" class="form-horizontal">
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
                <div class="caption"><i class="icon-edit"></i>编辑数据池</div>
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
                                <input type="text" name="pool.alias" value="${_POOL_INFO.alias!}">
                            </div>
                        </div>
                    </div>
                    <div class="span3">
                        <div class="control-group">
                            <label for="" class="ow control-label">加锁级别</label>
                            <div class="ow controls">
                                <select class=" m-wrap" name="pool.lockLevel">
                                <#list _POOL_LOCKLEVELS as c>
                                    <option value="${c.code}"
                                            <#if _POOL_INFO.lockLevel == c.code>selected</#if>>${c.title}</option>
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

                                <#list _ORG_LIST as c>
                                    <option value="${c.id}"
                                            <#if _POOL_ORG.id == c.id>selected</#if>>${c.orgName!}</option>
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
                                <#list _ORG_DATA_TYPE_LIST as c>
                                    <option value="${c.id}"
                                            <#if _POOL_DATA_TYPE.id == c.id>selected</#if>>${c.typeName!}</option>
                                </#list>
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
                                <#if _POOL_INFO.mainPool == 0>
                                    <option value="CLD_MAIN" selected>核心数据池</option>
                                    <option value="CLD_ADDITION">普通数据池</option>
                                <#else >
                                    <option value="CLD_MAIN">核心数据池</option>
                                    <option value="CLD_ADDITION" selected>普通数据池</option>
                                </#if>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="span3">
                        <div class="control-group">
                            <label for="scoreWeight" class="ow control-label">评分权重</label>
                            <div class="ow  controls">
                                <input type="number" max="100" min="-100" id="scoreWeight" name="pool.scoreWeight" value="${_POOL_INFO.scoreWeight!0}">
                            </div>
                        </div>
                    </div>
                    <div class="span3">
                        <div class="control-group">
                            <label for="dataHost" class="ow control-label">数据主体</label>
                            <div class="ow controls">
                                <select id="dataHost" class=" m-wrap" name="pool.dataHost">
                                <#list _POOL_DATAHOSTS as c>
                                    <option value="${c.code!}" <#if _POOL_INFO.dataHost == c.code>selected</#if>>${c.title!}</option>
                                </#list>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <table class="table table-striped table-hover table-bordered" id="dataField">
                    <thead>
                    <tr>
                        <th width="5%">序号</th>
                        <th width="20%">字段名称</th>
                        <th width="10%">访问权限</th>
                        <th width="10%">数据类型</th>
                        <th width="10%">当前状态</th>
                        <th width="10%">业务主键</th>
                        <th width="10%">查询条件</th>
                        <th width="10%">索引字段</th>
                        <th width="10%">标题字段</th>
                        <th width="10%">排序字段</th>
                        <th width="15%">关联模型</th>

                    </tr>
                    </thead>
                    <tbody id="modelFieldListTBody">
                    <#if _POOL_FIELD_LIST??&&_POOL_FIELD_LIST?size gt 0>
                            <#list _POOL_FIELD_LIST as c>
                            <tr>
                                <td>${c_index+1}</td>
                                <td align="left">${c.fieldName!}</td>
                                <td>
                                    <#if c.pubLevel == 0>
                                        授权访问
                                    <#elseif c.pubLevel == 1 >
                                        政务公开
                                    <#else >
                                        社会公开
                                    </#if>
                                </td>
                                <td>
                                    <#if c.showStyle == 'FSS_COMMON_TEXT'>
                                        普通文本
                                    <#elseif c.showStyle == 'FSS_PARTHIDE_TEXT'>
                                        半隐藏文本
                                    <#elseif c.showStyle == 'FSS_DATE'>
                                        日期时间
                                    <#elseif c.showStyle == 'FSS_CODE'>
                                        码表转换
                                    <#elseif c.showStyle == 'FSS_IMG_DATE'>
                                        图片
                                    <#elseif c.showStyle == 'FSS_IMG_PATH'>
                                        图片路径
                                    <#else>
                                        未知类型
                                    </#if>
                                </td>
                                <td>
                                    <#if c.status == 1>
                                        启用
                                    <#else >
                                        禁用
                                    </#if>
                                </td>
                                <td>
                                    <input type="radio" name="pFieldId" <#if c.id == _POOL_PK_FIELD.id>checked</#if>
                                           value="${c.id}">&nbsp;&nbsp;&nbsp;&nbsp;
                                </td>
                                <td>
                                    <input type="checkbox" name="pConFieldIds"
                                        <#list _POOL_CONDITIONS as cdt>
                                            <#if c.id == cdt.fieldId>
                                           checked
                                            </#if>
                                        </#list>
                                           value="${c.id}">&nbsp;&nbsp;&nbsp;&nbsp;
                                </td>
                                <td>
                                    <input type="checkbox" name="pIdxFieldIds"
                                        <#list _POOL_INDEXS as idx>
                                            <#if c.id == idx.fieldId>
                                           checked
                                            </#if>
                                        </#list>
                                           value="${c.id}">&nbsp;&nbsp;&nbsp;&nbsp;
                                </td>
                                <td>
                                    <input type="radio" name="pool.titleFieldId" <#if c.id == _POOL_INFO.titleFieldId>checked</#if>
                                           value="${c.id}">&nbsp;&nbsp;&nbsp;&nbsp;
                                </td>
                                <td>
                                    <input type="radio" name="pool.orderFieldId" value="${c.id}"
                                           <#if _POOL_INFO.orderFieldId?? && c.id == _POOL_INFO.orderFieldId>checked</#if>
                                    ><i class="icon-arrow-up"></i>
                                    <input type="radio" name="pool.orderFieldId" value= ${"-${c.id}"}
                                            <#if _POOL_INFO.orderFieldId?? && c.id?string == _POOL_INFO.orderFieldId?substring(1)>checked</#if>
                                    ><i class="icon-arrow-down"></i>
                                </td>
                                <td>
                                    <input type="button" class="btn blue mini" onclick="showGroupLink('${c.id}')" value="模型关联">
                                    <input type="button" class="btn green mini"
                                           onclick="showGroupFreeLink(${_POOL_INFO.id},${c.id})" value="自由关联">
                                </td>

                            </tr>
                            </#list>
                    </#if>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <input type="hidden" id="poolId" value="${_POOL_INFO.id}">
    <input type="hidden" name="paramIdxFields">
    <input type="hidden" name="paramCdtFields">
    <input type="hidden" name="poolId" value="${_POOL_INFO.id}">
</form>
<script>
    initOrderBtn();
    App.handleTooltips();

    $("select[name='poolType']").change(function () {

    });

    $("input[name='mFields']").click(function () {

    })
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
                            if ($("#dataField th:last-child").text().includes("关联模型")) {
                                $("#dataField th:last-child").hide();
                            }
                        }
                    });
                } else {
                    $("select[name='dataTypeId']").empty();
                    $("#dataField tbody").empty();
                }
            }
        });
    });

    $("#orgListFree").live("change", function () {
        $.ajax({
            url: "listDSPools.action",
            data: {
                groupId: $(this).val()
            },
            timeout: 30000,
            success: function (data) {
                $("#poolListFree").html(data);
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
                $(".link").show();
            }
        });
    });

    $("#poolListFree").live("change", function () {
        $.ajax({
            url: "listDSDataFields.action",
            data: {
                toPoolId: $(this).val(),
                fromPoolId: $("input[name='fromPoolId'").val(),
                fkFieldId: $("input[name='fkFieldId'").val()
            }
            , timeout: 30000,
            success: function (data) {
                $("#modelFieldListTBodyFree").html(data);
                $(".link").show();
            }
        });
    });
</script>