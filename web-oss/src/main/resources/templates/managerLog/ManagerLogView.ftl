<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="登录日志详情">
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
			<label class="col-sm-2 col-xs-4 control-label">用户名：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.loginName!}</div>
			<label class="col-sm-2 col-xs-4 control-label">IP地址：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.loginIp!}</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 col-xs-4 control-label">操作动作：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.actionName!}</div>
			<label class="col-sm-2 col-xs-4 control-label">描述：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.description!}</div>
		</div>
	</form>
    </div>
    <#include "/common/CommonJsImport.ftl">
</body>
</html>