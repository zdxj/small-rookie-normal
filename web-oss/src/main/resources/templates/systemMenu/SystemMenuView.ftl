<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="系统菜单详情">
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
			<label class="col-sm-2 col-xs-4 control-label">菜单名称：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.menuName!}</div>
			<label class="col-sm-2 col-xs-4 control-label">权限编码：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.permCode!}</div>
		</div>
		<div class="form-group">
			
			<label class="col-sm-2 col-xs-4 control-label">菜单类型</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${(entity.permType==1)?string('按钮',(entity.permType==2)?string('目录','菜单'))}</div>
			<label class="col-sm-2 col-xs-4 control-label">菜单图标：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.icon!}</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 col-xs-4 control-label">打开方式：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${(entity.openType=='menuItem')?string('页签','新窗口')}</div>
			
		</div>
		<div class="form-group">
			<label class="col-sm-2 col-xs-4 control-label">父分类：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${parentName!}</div>
			<label class="col-sm-2 col-xs-4 control-label">位置排序：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.position!}</div>
		</div>
		<div class="form-group">
			<label class="col-sm-2 col-xs-4 control-label">菜单路径：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.url!}</div>
			<label class="col-sm-2 col-xs-4 control-label">权限路径：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.permUrl!}</div>
		</div>
	</form>
    </div>
    <#include "/common/CommonJsImport.ftl">
</body>

</html>