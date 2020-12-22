<div class="top_nav portlet-body">
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    <#list data.modelTree.groupTrees as tree>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="heading${tree_index}">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapse${tree_index}" aria-expanded="false"
                       aria-controls="collapse${tree_index}">
                    ${tree.group.groupName}
                        <span>[${tree.pools?size}]</span>
                    </a>
                </h4>
            </div>
            <div id="collapse${tree_index}" class="panel-collapse collapse " role="tabpanel"
                 aria-labelledby="heading${tree_index}">
                <div class="panel-body ">
                <p class="">
                    <#list tree.pools as value>
                        <div class="col-sm-3">
                            <div class="box-nav" onclick="show_info(${value.id})">
                                <div class="box-text">
                                ${value.poolTitle}
                                </div>
                            </div>
                        </div>
                    </#list>
                    </p>
                </div>
            </div>
        </div>
    </#list>
        <input id="selectedPoolId" type="hidden">
    </div>
</div>

<#if _RESULT_TYPE == "MODEL_TREE">
<div id="md_content" class="portlet-body">

</div>
<script type="application/javascript">
    function show_info(id) {
        $.ajax({
            type: "post",
            url: "${ctx}/npt/grs/query/custom/poolIndex.action",
            data: {parentId:id},
            timeout: 30000,
            beforeSend:function(){
                $(".loadDiv").show();
            },
            success: function (html) {
                $('#selectedPoolId').val(id);
                $('#md_content').html(html);
                $('#md_content').css("border-top","1px solid #ccc");
                pagination.initPagination("${ctx}/npt/grs/query/custom/poolIndexAjax.action",{parentId:id},showSecurityFunction);
                $(".loadDiv").hide();
            },
            error:function () {
                $(".loadDiv").hide();
                returnInfo(false,"操作失败！");
            }
        });
    }
</script>
</#if>