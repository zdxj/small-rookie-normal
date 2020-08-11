<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="系统管理员详情">
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
				<label class="col-sm-2 col-xs-4 control-label">登录名：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">${entity.loginName!}</div>
				<label class="col-sm-2 col-xs-4 control-label">真实姓名：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">${entity.realName!}</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-xs-4 control-label">手机号码：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">${entity.mobile!}</div>
				<label class="col-sm-2 col-xs-4 control-label">所属部门：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">${deptName!}</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 col-xs-4 control-label">登录ip地址：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">${entity.loginIp!}</div>
				<label class="col-sm-2 col-xs-4 control-label">登录时间：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">
				<#if entity.loginTime??>					
					 ${entity.loginTime?string("yyyy-MM-dd HH:mm:ss")}
				</#if>		 
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 col-xs-4 control-label">是否启用：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">${(entity.enable)?string('是','否')}</div>
				<label class="col-sm-2 col-xs-4 control-label">备注：</label>
				<div class="col-sm-4 col-xs-8 form-control-static">${entity.note!}</div>
			</div>
	
	</form>
    </div>
    <#include "/common/CommonJsImport.ftl">
</body>

</html>