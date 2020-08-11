<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="修改密码">
	<#include "/common/CommonCssImport.ftl">
</head>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-user-resetPwd">
			<input name="id" id="id" type="hidden"  value="${id?c}" />
			<div class="form-group">
				<label class="col-sm-3 control-label">登录名称：</label>
				<div class="col-sm-8">
					${loginName!}
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">输入密码：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="password" id="password" autocomplete="new-password" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">再次输入密码：</label>
				<div class="col-sm-8">
					<input class="form-control" type="password" name="passwordConfirm" id="passwordConfirm" autocomplete="new-password" required>
				</div>
			</div>
		</form>
	</div>
	<#include "/common/CommonJsImport.ftl">
	<script type="text/javascript">
		
		function submitHandler() {
	        if ($.validate.form()) {
	        	$.operate.save("/systemManager/doResetPassword", $('#form-user-resetPwd').serialize());
	        }
	    }
	</script>
</body>

</html>
