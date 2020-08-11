<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="(entity==null)?string('增加系统设置','修改系统设置')">
	<#include "/common/CommonCssImport.ftl">
</head>

<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-add-update">
			<input type="hidden" name="id" />
				<div class="form-group">
					<label class="col-sm-3 control-label is-required">文件服务器地址：</label>
					<div class="col-sm-6">
						<input type="text" required="required" class="form-control" id="fileServer" name="fileServer" maxlength="128" title="请输入文件服务器地址">
					</div>
				</div>
				<div class="col-md-12">
				    <div class="form-group">
				        <div class="col-sm-12 col-sm-offset-3">
				            <button type="button" onclick="submitConfig()" class="btn btn-primary">提交</button>
				        </div>
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
		function submitConfig() {
	        if ($.validate.form()) {
	        	$.operate.save("${action!}", $('#form-add-update').serialize());
	        }
	    }
	</script>
</body>

</html>