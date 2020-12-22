<div class="page-title">
    <h3>各单位信息入库总量统计</h3>
<p><#if STARTDATE?? && ENDDATE??>${STARTDATE?replace('-','')}~${ENDDATE?replace('-','')}</#if></p>
</div>
<div class="portlet-body">
<div class="row-fluid">
    <div class="span12 statistics">
        <div class="groups0">
            <div class="title">
                <div class="title1">单位名称</div>
                <div class="title2">主体</div>
                <div class="title3">信息类型</div>
                <div class="title4">信息量(条)</div>
            </div>
        <#if DATALIST??>
            <#list DATALIST as data>
            <div class="group0" id="${data.id}">
                <div class="groupName0">${data.name}</div>
                <div class="groups1">
                    <@buildNode childs=data.children/>
                </div>
            </#list>
        <#else >
            <div class="page-title">
                <h3>暂无数据</h3>
            </div>
        </#if>
        </div>
        </div>
    </div>
    <!-- END ROW FLUID -->
</div>
<!-- END PORTLET BODY -->

<#macro buildNode childs depth=1>
    <#if childs?? && childs?size gt 0>
        <#list childs as child>
            <#if child_index!=0>
            </div>
            </#if>

            <#if depth==2>
            <div class="group${depth} part${child.id}">
                <div class="groupName${depth} left" title="${child.name}"><a href="javascript:void(0)" onclick="detail(${child.id})">${child.name}</a></div>
            <#elseif depth==4>
            <div class="group${depth}">
                <div class="groupName${depth} progress">
                    <div style="width:${child.name}%" class="bar"></div>
                </div>
            <#else>
            <div class="group${depth}">
                <div class="groupName${depth}">${child.name}</div>
            </#if>

            <#if child.children?? && child.children?size gt 0>
            <div class="groups${depth+1}">
                <@buildNode childs=child.children depth=depth+1/>

            </#if>
            <#if !child_has_next>

            </div>
            </div>
            </#if>
        </#list>
    </#if>
</#macro>
