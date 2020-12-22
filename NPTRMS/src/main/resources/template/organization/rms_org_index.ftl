<#include "/template/Credit_Common/common.ftl">

<!doctype html>
<head>
    <#--<link rel="stylesheet" href="${wctx}/pub/CreditStyle/ztree/css/zTreeStyle/zTreeStyle.css"
          type="text/css"/>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/ztree/js/jquery.ztree.core.js"></script>-->
    <link rel="stylesheet" href="${wctx}/pub/CreditStyle/ztreeTest/css/bootstrapStyle/bootstrapStyle.css" type="text/css">
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/ztreeTest/js/jquery.min.js"></script>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/ztreeTest/js/jquery.ztree.core.js"></script>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/ztreeTest/js/jquery.ztree.excheck.js"></script>
    <script type="text/javascript" src="${wctx}/pub/CreditStyle/ztreeTest/js/jquery.ztree.exedit.js"></script>
    <style type="text/css">
        /*.ztree li span.button.add {
            margin-left: 2px;
            margin-right: -1px;
            background-position: -144px 0;
            vertical-align: top;
        }

        .ztree li span.button.pIcon01_ico_open {
            margin-right: 3px;
            background: url(${wctx}/pub/CreditStyle/resource/img/open.png) no-repeat scroll 0 0 transparent;
            background-size: 16px ;
            vertical-align: top;
            *vertical-align: middle
        }

        .ztree li span.button.pIcon01_ico_close {
            margin-right: 3px;
            background: url(${wctx}/pub/CreditStyle/resource/img/close.png) no-repeat scroll 0 0 transparent;
            background-size: 16px ;
            vertical-align: top;
            *vertical-align: middle
        }*/
    </style>
</head>
<body>
<div class="row-fluid">
    <div class=" clearfix bgWhite">
        <div class="portlet box boxTheme">
            <div class="portlet-title" role="grid">
                <div class="caption">
                    <i class="icon-cogs"></i>
                    组织机构
                </div>
                <div class="tools">
                    <button type="button" onclick="addInitOrg()" class="btn yellow mini hide" id="initOrg"><i class="icon-refresh"></i>  初始化机构</button>
                    <a href="javascript:;" class="collapse"></a>
                </div>
            </div>
            <div class="portlet-body">
                <div class="row-fluid">
                    <div class="span3">
                        <div class="treeDiv scroller margin-bottom-10" data-height="720px">
                            <ul id="orgTree" class="ztree"></ul>
                        </div>
                    </div>
                    <div class="span9">
                        <div id="indexPage">
                        <#include "rms_org_list.ftl">
                        </div>
                        <div id="editDiv"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>

<div id="addOrgDiv" class="modal hide fade" tabindex="-1" data-width="760"></div>
<script type="text/javascript">
    $(function () {
        App.handleScrollers();
    });

    /**
     * 初始化机构
     */
    function addInitOrg() {
        addOrg();
        $("#addOrgDiv").on("shown.bs.modal",function () {
            $("#addOrgDiv").find("form").attr("action","initRootOrg.action");
        });
    }

    /***
     * 新增机构
     */
    function addOrg() {
        $.post("addPage.${urlExt}", function (html) {
            $("#addOrgDiv").modal("show").html(html);
        });
    }
    /**
     * 提交表单
     */
    function submitForm(form) {
        $.post(form.action, $(form).serialize(), function (data) {
            if (data.result) {
                refreshNode();
                $("#addOrgDiv").modal("hide");
                returnInfo(true, data.message || "操作成功！")
            } else {
                returnInfo(false, data.message || "操作失败！");
            }
        });
        return false;
    }
    var num;

    var page = {
        indexDiv: $("#indexPage"),
        editDiv: $("#editDiv"),
        showData: function (data) {
            page.indexDiv.hide();
            page.editDiv.html(data).show();
        }
    };

    /***
     * 打开编辑页面
     * **/
    function editData(id) {
        $.post("editPage.${urlExt}", {id: id}, function (html) {
            page.showData(html);
        });

    }


    /***
     * 返回主页
     * **/
    function backToContent() {
        page.editDiv.hide();
        page.indexDiv.show();
    }

    /***
     * 机构树配置
     * */

    var setting = {
        async: {
            enable: true,
            url: getUrl,
            autoParam: ["id"],
            dataFilter: filter
        },
        edit: {
            enable: false,
            showRemoveBtn: false,
            showRenameBtn: false
        },
        data: {
            simpleData: {
                enable: true
            }
        },
        callback: {
            beforeClick: beforeClick,
            beforeEditName: beforeEditName,
            onAsyncSuccess: zTreeOnAsyncSuccess,
            beforeDrag: beforeDrag,

        }
    };
    function getUrl(treeId, treeNode) {
        var param = "parentId=" + (treeNode == undefined ? '-1' : treeNode.id);
        return "showOrgTreeByParentId.action?" + param;
    }
    function beforeDrag() {
        return false;
    }
    var firstAsyncSuccessFlag = true;
    function zTreeOnAsyncSuccess(event, treeId,treeNode, msg) {

        if ($("#orgTree").html() == ""){
            $("#initOrg").show();
        }
        var zTree = $.fn.zTree.getZTreeObj("orgTree");
        if (firstAsyncSuccessFlag) {
            try {
                //调用默认展开第一个结点
                var selectedNode = zTree.getSelectedNodes();
                var nodes = zTree.getNodes();
                zTree.expandNode(nodes[0], true);

                firstAsyncSuccessFlag = false;
            } catch (err) {

            }
        }
    }

    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i = 0, l = childNodes.length; i < l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }
    function beforeEditName(treeId, treeNode) {
        editData(treeNode.id);
        return false;
    }


    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");
        if (treeNode.editNameFlag || $("#addBtn_" + treeNode.tId).length > 0) return;
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
                + "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_" + treeNode.tId);
        if (btn) btn.bind("click", function () {
            $.post("addPage.${urlExt}", function (html) {
                page.showData(html);
                $("#parentName").html(treeNode.name);
                $("#parentList>input").attr("name", "data.parentId").val(treeNode.id);
            });
            return false;
        });
    }
    ;
    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_" + treeNode.tId).unbind().remove();
    }
    ;
    function beforeClick(treeId, treeNode, clickFlag) {
        editData(treeNode.id);
        return (treeNode.click != false);
    }
    function refreshNode() {
        /*根据 treeId 获取 zTree 对象*/
        var zTree = $.fn.zTree.getZTreeObj("orgTree");
        var type = "refresh";
        var silent = false;
        var nodes = zTree.getSelectedNodes();
        /*根据 zTree 的唯一标识 tId 快速获取节点 JSON 数据对象*/
//        var parentNode = zTree.getNodeByTId(nodes[0].parentTId);
        /*选中指定节点*/
        zTree.reAsyncChildNodes(nodes[0], type);
        zTree.selectNode(nodes[0],true);
        zTree.expandNode(nodes[0],true,false);
        if ($("#orgTree").html() == ""){
            $("#initOrg").show();
        }
    }

    $.fn.zTree.init($("#orgTree"), setting);

</script>
</body>
</html>


