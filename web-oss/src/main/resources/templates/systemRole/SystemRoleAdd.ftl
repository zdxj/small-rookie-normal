<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="(entity==null)?string('增加系统角色','修改系统角色')">
	<#include "/common/CommonCssImport.ftl">
	<link href="/libs/jquery-ztree/3.5/css/metro/zTreeStyle.css" rel="stylesheet"/>
</head>

<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-add-update">
			<input type="hidden" name="id" id="id"/>
				<div class="form-group">
					<label class="col-sm-3 control-label is-required">角色名称：</label>
					<div class="col-sm-8">
						<input type="text" required="required" class="form-control" id="name" name="name" maxlength="32" title="请输入角色名称">
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-3 control-label">备注：</label>
					<div class="col-sm-8">
						<textarea class="form-control" id="note" name="note" style="height: 60px;" maxlength="128"></textarea>
					</div>
				</div>
				<div class="form-group" id="menu_tree">
					<label class="col-sm-3 control-label">菜单权限</label>
					<div class="col-sm-8">
						<div id="menuTrees" class="ztree"></div>
					</div>
				</div>
		</form>
	</div>
	<#include "/common/CommonJsImport.ftl">
	<script src="/libs/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
	<script type="text/javascript">
		var _data = ${entity!"null"};
		$.operate.initFormData(_data);
		$(function() {
			var url = "/systemMenu/listZtreeMenu?roleId="+$("#id").val();
			var options = {
				id: "menuTrees",
		        url: url,
		        check: { enable: true,chkboxType: { "Y": "ps", "N": "s" } },
		        expandLevel: 0
		    };
			if(_data == null || (_data!= null && _data.id != 1)){
				$.tree.init(options);
			}else{
				$("#menu_tree").hide();
			}
		});
		$("#form-add-update").validate({
			focusCleanup: true
		});
		function submitHandler() {
	        if ($.validate.form()) {
	        	add();
	        }
	    }
		function add() {
			var roleName = $("input[name='name']").val();
			var note = $("#note").val();
			var menuIds = (_data!= null && _data.id != 1) ?$.tree.getCheckedNodes():[];
			$.ajax({
				cache : true,
				type : "POST",
				url : "${action!}",
				data : {
					"id": $("#id").val(),
					"name": roleName,
					"note": note,
					"menuIds": menuIds
				},
				async : false,
				error : function(request) {
					$.modal.alertError("系统错误");
				},
				success : function(data) {
					$.operate.successCallback(data);
				}
			});
		}
	</script>
</body>

</html>