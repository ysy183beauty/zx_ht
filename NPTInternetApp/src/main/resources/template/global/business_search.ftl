<link href="/pub/index/css/bootstrap.min.css" rel="stylesheet">
<link href="/pub/index/css/main.css" rel="stylesheet">
<form class="form-horizontal margin-top-20" action="/npt/global/businessIndex.action"  onsubmit="return showBusIndex(this)">
    <div class="form-group col-xs-5"">
        <label for="busName" class="col-xs-4 control-label">企业名称:</label>
        <div class="col-xs-8">
            <input type="text" class="form-control" id="busName" name="busName">
        </div>
    </div>
    <div class="form-group col-xs-5"">
        <label for="codeName" class="col-xs-4 control-label">统一信用代码:</label>
        <div class="col-xs-8">
            <input type="text" class="form-control" id="codeName" name="codeName">
        </div>
    </div>
    <div class="form-group">
        <div class="col-xs-2 pull-right">
            <button type="submit" class="btn btn-primary btn-default" >查询</button>
        </div>
    </div>
</form>
<script>
    function showBusIndex(form){
        $.post(form.action,$(form).serialize(),function () {

        });
        return false;
    }
</script>