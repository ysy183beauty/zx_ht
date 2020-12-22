<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
    <script src="${wctx}/pub/CreditStyle/bootstrap/frame/media/js/colResizable-1.5.min.js"></script>
</head>
<body class="page-header-fixed">

<!-------------------------------------外部系统信用信息管理-------------------------------------------->
<div id="indexDiv">
    <div class="portlet box boxTheme">
        <div class="portlet-title">
            <div class="caption"><i class="icon-search"></i>外部专题模型管理</div>
            <div class="tools">
                <a type="button" class="btn yellow mini" onclick="editRemoteServer()"><i class="icon-cog"></i> 设置IP、端口号</a>
                <a type="button" class="btn purple mini" onclick="syncProvider()"><i class="icon-refresh"></i> 同步依赖</a>
                <a type="button" class="btn green mini" onclick="syncTest()"><i class="icon-refresh"></i> 测试同步功能</a>
            </div>
        </div>

        <div class="portlet-body">
            <table class="table table-striped table-bordered table-hover" id="internetModel">
                <thead>
                <tr>
                    <th width="5%">序号</th>
                    <th width="10%">模型名称</th>
                    <th width="10%">类别名称</th>
                    <th width="10%">绑定实体</th>
                    <th width="10%">创建时间</th>
                    <th width="5%">状态</th>
                    <th width="15%">操作管理</th>
                </tr>
                </thead>
                <tbody>
                <#if _MODEL_LIST??>
                    <#list _MODEL_LIST as c>
                    <tr>
                        <td>${c?index+1}</td>
                        <td align="left" >${c.modelName!}</td>
                        <td align="left" >${c.cateTitle!}</td>
                        <td  >${c.hostTitle!}</td>
                        <td>${c.createTime?string('yyyy-MM-dd')!}</td>
                        <td  >${(c.status==1)?string('启用','禁用')!}</td>
                        <td>
                            <div class="controls">
                                <a class="btn mini green tooltips" href="javascript:void (0);" onclick="syncBaseModel(${c.id})" data-placement="bottom" data-original-title="同步模型"><i class="icon-refresh "></i> 同步模型</a>
                                <a class="btn mini blue tooltips" href="javascript:void (0);" onclick="syncBaseModelData(${c.id})" data-placement="bottom" data-original-title="同步模型数据"><i class="icon-refresh"></i> 同步模型数据</a>
                                <a class="btn mini blue tooltips" href="javascript:void (0);" onclick="listPools(${c.id})" data-placement="bottom" data-original-title="同步指定数据池数据"><i class="icon-refresh"></i> 同步指定数据池数据</a>
                            </div>
                        </td>
                    </tr>
                    </#list>
                </#if>
                </tbody>
            </table>
        </div>
    </div>
</div>
<div id="addIp" class="hide modal fade" abindex="-1" data-width="560">

</div>


<div id="progressWindow" style="width: 100%;height: 100px;text-align: center;font-size: large;">
</div>
<div id="poolListDiv" class="modal hide fade" tabindex="-1" data-width="760">
    <form id="poolListForm" action="synchronizeSelectedPoolData.${urlExt}" method="post" class="no-bottom-space">
    </form>
</div>

<script type="text/javascript">
    $(function () {
        TableManaged.init("internetModel");
        $("#internetModel").colResizable({
            liveDrag:true,
            draggingClass:"dragging"
        });
        App.handleTooltips();
    });

    function editRemoteServer() {
        $.post("editRemoteServer.action",function (data) {
            $("#addIp").modal("show").html(data);
        });
    }
    /**
     *作者：xqy
     *时间：2016/12/15 11:12
     *描述:
     *      向外系统同步IP和端口号
     */ 
    function updateRemoteServer(form) {
        $.post(form.action,$(form).serialize(),function (data) {
            if (data.result){
                $("#addIp").modal("hide");
                returnInfo(true,data.message||"操作成功！");
            }else {
                returnInfo(false,data.message||"操作失败！");
            }
        });
        return false;
    }
    /**
     *作者：OWEN
     *时间：2016/12/2 15:12
     *描述:
     *      向外系统同步机构信息
     */
    function syncProvider(id){
        $.ajax({
            url:"synchronizeDependency.action",
            data:{modelId:id},
            timeout:10000000,
            beforeSend:function(){
                $(".loadDiv").show();
                $('#progressWindow').html("开始准备同步模型的依赖数据，请耐心等待");
            },
            success:function (data) {
                ajaxTaskLoader("SYNC_MODEL_DEPE_TASKID");
                $(".loadDiv").hide();
                clearInterval(int);
            },
            error:function () {
                ajaxTaskLoader("SYNC_MODEL_DEPE_TASKID");
                $(".loadDiv").hide();
                clearInterval(int);
            }
        });

        var int = setInterval(function () {
            ajaxTaskLoader("SYNC_MODEL_DEPE_TASKID");
        },1000);
    }

    /**
     *作者：jss
     *时间：2018年4月23日16:55:32
     *描述:
     *      测试同步功能
     */
    function syncTest(){
        $.ajax({
            url:"syncTest.action",
            timeout:10000000,
            beforeSend:function(){
                $(".loadDiv").show();
                $('#progressWindow').html("开始测试同步功能，请耐心等待");
            },
            success:function (data) {
                $('#progressWindow').html(data);
                $(".loadDiv").hide();

            },
            error:function () {
                $('#progressWindow').html("测试出现错误");
                $(".loadDiv").hide();
            }
        });
    }

    /**
     *作者：OWEN
     *时间：2016/12/2 15:12
     *描述:
     *      向外系统同步基础查询模型信息
     */
    function syncBaseModel(id){
        $.ajax({
            url:"synchronizeBaseModel.action",
            data:{modelId:id},
            timeout:10000000000,
            beforeSend:function(){
                $(".loadDiv").show();
                $('#progressWindow').html("开始准备同步模型结构，请耐心等待");
            },
            success:function (data) {
                $(".loadDiv").hide();
                ajaxTaskLoader("SYNC_MODEL_STRC_TASKID");
                clearInterval(int);
            },
            error:function () {
                $(".loadDiv").hide();
                ajaxTaskLoader("SYNC_MODEL_STRC_TASKID");
                clearInterval(int);
            }
        });

        var int = setInterval(function () {
            ajaxTaskLoader("SYNC_MODEL_STRC_TASKID");
        },300);
    }

    /**
     *作者：OWEN
     *时间：2016/12/2 15:12
     *描述:
     *      向外系统同步基础查询模型信息
     */
    function syncBaseModelData(id){

        $.ajax({
            url:"synchronizeBaseModelData.action",
            data:{modelId:id},
            timeout:100000000,
            beforeSend:function(){
                $(".loadDiv").show();
                $('#progressWindow').html("开始准备同步模型数据，请耐心等待");
            },
            success:function (data) {
                ajaxTaskLoader("SYNC_MODEL_DATA_TASKID");
                clearInterval(int);
                $(".loadDiv").hide();
            },
            error:function () {
                ajaxTaskLoader("SYNC_MODEL_DATA_TASKID");
                clearInterval(int);
                $(".loadDiv").hide();
            }
        });

        var int = setInterval(function () {
            ajaxTaskLoader("SYNC_MODEL_DATA_TASKID");
        },1000);
    }

    function ajaxTaskLoader(tid){
        $.ajax({
            url:"ajaxTaskLoader.action",
            data:{taskId:tid},
            success:function (task) {
                $('#progressWindow').html(task.textInfo);
            }
        })
    }


    function listPools(id){
        $.ajax({
            url:"listPools.action",
            data:{id:id},
            timeout:30000,
            beforeSend:function(){
                $("#poolListForm").html("");
                $(".loadDiv").show();
            },
            success: function (data) {
                $("#poolListForm").html(data);
                $("#poolListDiv").modal("show");
                $(".loadDiv").hide();
            },
            error: function () {
                $(".loadDiv").hide();
                returnInfo(false, "操作失败！");
            }
        });
    }

    $("#poolListForm").submit(function (event) {
        event.preventDefault ? event.preventDefault() : (event.returnValue = false);
        $.ajax({
            type: "post",
            url: "synchronizeSelectedPoolData.${urlExt}",
            data: $("#poolListForm").serializeArray(),
            timeout:100000000,
            beforeSend:function(){
                $("#poolListDiv").modal("hide");
                $(".loadDiv").show();
                $('#progressWindow').html("开始准备同步指定数据池数据，请耐心等待");
            },
            success: function () {
                ajaxTaskLoader("SYNC_POOL_DATA_TASKID");
                clearInterval(int);
                $(".loadDiv").hide();
            },
            error:function () {
                ajaxTaskLoader("SYNC_POOL_DATA_TASKID");
                clearInterval(int);
                $(".loadDiv").hide();
            }
        });

        var int = setInterval(function () {
            ajaxTaskLoader("SYNC_POOL_DATA_TASKID");
        },1000);
    })
</script>
</body>
</html>