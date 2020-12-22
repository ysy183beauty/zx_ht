<#assign result = globalSearchResult>
<table class="table table-striped table-bordered table-hover" id="searchResultTable">
    <thead>
    <tr>
        <th width="5%">序号</th>
        <th width="30%">机构及信息类</th>
        <th width="65%">匹配的信息项及其值</th>
    </tr>
    </thead>
    <tbody>
    <#list result as rs>
    <tr>
        <td>${rs_index + 1}</td>
        <td align="left">[${rs.orgTitle!}]===>[${rs.dataTypeTitle!}]</td>
        <td align="left">
            <#if rs.searchDataList?size gt 0>
                <#list rs.searchDataList as onePKList>
                    <#if onePKList.dataList?size gt 0>
                        <#list onePKList.dataList as realData>
                            <table class="table table-bordered ">
                                <#list realData?keys as key>
                                    <tr>
                                        <td width="20%" align="right">${key!}</td>
                                        <td width="50%" align="left">${realData.get(key)!}</td>
                                        <#if onePKList.pkFieldId??&&key_index == 0>
                                            <td rowspan="${realData?size}" width="30%">
                                                <p>
                                                    <button class="btn mini purple-stripe"
                                                            onclick="showAppealInfo('${rs.dataTypeId?string("#")}','${onePKList.pkFieldId?string("#")}','${realData.get(key)!}')">
                                                        异议
                                                    </button>
                                                    &nbsp;&nbsp;
                                                    <button class="btn mini blue-stripe" onclick="owenTest()">详情
                                                    </button>
                                                    &nbsp;&nbsp;
                                                </p>
                                                <div>
                                                    <button class="btn mini green-stripe" onclick="owenTest()">分析
                                                    </button>
                                                    &nbsp;&nbsp;
                                                    <button class="btn mini red-stripe" onclick="owenTest()">举报</button>
                                                    &nbsp;&nbsp;
                                                </div>

                                            </td>
                                        </#if>
                                    </tr>
                                </#list>
                            </table>
                        </#list>
                    </#if>
                </#list>
            <#else >
                无匹配数据
            </#if>
        </td>
    </tr>
    </#list>
    </tbody>
</table>
</script>