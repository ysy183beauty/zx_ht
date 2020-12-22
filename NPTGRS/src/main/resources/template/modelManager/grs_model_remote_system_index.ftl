<input type="button" value="同步依赖" onclick="syncProvider()">
<br>
<input type="button" value="同步模型" onclick="syncBaseModel()">
<br>
<input type="button" value="同步模型数据" onclick="syncBaseModelData()">


<script type="text/javascript">

    /**
     *作者：OWEN
     *时间：2016/12/2 15:12
     *描述:
     *      向外系统同步机构信息
     */
    function syncProvider(){
        $.post("3synchronizeDependency.action"),{},function (data) {
            
        }
    }

    /**
     *作者：OWEN
     *时间：2016/12/2 15:12
     *描述:
     *      向外系统同步基础查询模型信息
     */
    function syncBaseModel(){
        $.post("synchronizeBaseModel.action"),{},function (data) {

        }
    }

    /**
     *作者：OWEN
     *时间：2016/12/2 15:12
     *描述:
     *      向外系统同步基础查询模型信息
     */
    function syncBaseModelData(){
        $.post("synchronizeBaseModelData.action"),{},function (data) {

        }
    }

</script> 