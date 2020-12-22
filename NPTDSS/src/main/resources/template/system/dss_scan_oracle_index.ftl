<#include "/template/Credit_Common/common.ftl">
<!doctype html>
<html>
<head>
</head>
<body>
<div class="widget-box">

	<div class="widget-content nopadding" >
		<div class="portlet box boxTheme">
			<div class="portlet-title">
				<div class="caption">
					<i class="icon-globe"></i>
					表
				</div>
                <div class="pull-right">
                    <button class="btn blue" onclick="scanTable()">扫描表</button>
                </div>
			</div>
			<div class="portlet-body" >
                <table id="scanTable" class="table table-bordered data-table dataTable table-hover">
                    <thead>
                    <tr>
                        <th>插入数量</th>
                        <th>更新数量</th>
                        <th>禁用数量</th>
                    </tr>
                    </thead>
                    <tbody >
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
			</div>
		</div>
        <div class="portlet box boxTheme">
            <div class="portlet-title">
                <div class="caption">
                    <i class="icon-globe"></i>
                    列
                </div>
                <div class="pull-right">
                    <button class="btn blue" onclick="scanColumn()">扫描列</button>
                </div>
            </div>
            <div class="portlet-body" >
                <table id="scanColumn" class="table table-bordered data-table dataTable table-hover">
                    <thead>
                    <tr>
                        <th>插入数量</th>
                        <th>更新数量</th>
                        <th>禁用数量</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

	</div>

</div>

	<script>
        function scanTable(){

            $.post("scanTables.action",function (data) {
                var tds=$("#scanTable td");
                tds[0].innerHTML=data.insertCount;
                tds[1].innerHTML=data.updateCount;
                tds[2].innerHTML=data.deletedCount;
            })
        }
        function scanColumn(){
            $.post("scanColumns.action",function (data) {
                var tds=$("#scanColumn td");
                tds[0].innerHTML=data.insertCount;
                tds[1].innerHTML=data.updateCount;
                tds[2].innerHTML=data.deletedCount;
            })
        }

    </script>
</body>
</html>