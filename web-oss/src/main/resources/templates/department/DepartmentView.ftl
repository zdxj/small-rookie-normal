<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="部门详情">
	<#include "/common/CommonCssImport.ftl">
</head>

<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
	<form class="form-horizontal m-t" id="signupForm">
		<div class="form-group">
			<label class="col-sm-2 col-xs-4 control-label">ID：</label>
			<div class="col-sm-10 col-xs-8 form-control-static">${entity.id?c}</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 col-xs-4 control-label">创建时间：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.createTime?string("yyyy-MM-dd HH:mm:ss")}</div>
			<label class="col-sm-2 col-xs-4 control-label">修改时间：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.updateTime?string("yyyy-MM-dd HH:mm:ss")}</div>
		</div>
			<div class="form-group">
				<label class="col-sm-2 col-xs-4 control-label">部门名称：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">${entity.name!}</div>
				<label class="col-sm-2 col-xs-4 control-label">位置排序：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">${entity.position!}</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-xs-4 control-label">备注：</label>
				<div class="col-sm-10 col-xs-8 form-control-static">${entity.note!}</div>
			</div>
	
	</form>
    </div>
    <#include "/common/CommonJsImport.ftl">
</body>

</html>