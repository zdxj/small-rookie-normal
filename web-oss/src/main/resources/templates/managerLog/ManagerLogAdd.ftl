<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="(entity==null)?string('增加日志','修改日志')">
	<#include "/common/CommonCssImport.ftl">
</head>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-add-update">
			<input type="hidden" name="id" />
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">登录名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="loginName" id="loginName" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">用户动作：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="actionName" id="actionName" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">IP：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="loginIp" id="loginIp" required>
				</div>
			</div>
		</form>
	</div>
	<#include "/common/CommonJsImport.ftl">
	<script type="text/javascript">
		$.operate.initFormData(${entity!"null"});
		$("#form-add-update").validate({
			focusCleanup: true
		});
		function submitHandler() {
	        if ($.validate.form()) {
	        	$.operate.save("${action!}", $('#form-add-update').serialize());
	        }
	    }
	</script>
</body>
</html>
