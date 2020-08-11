<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="系统角色详情">
	<#include "/common/CommonCssImport.ftl">
	<link href="/libs/jquery-ztree/3.5/css/metro/zTreeStyle.css" rel="stylesheet"/>
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
			<label class="col-sm-2 col-xs-4 control-label">角色名称：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.name!}</div>
			<label class="col-sm-2 col-xs-4 control-label">备注：</label>
			<div class="col-sm-4 col-xs-8 form-control-static">${entity.note!}</div>
		</div>
		<div class="form-group" id="menu_tree">
			<label class="col-sm-2 control-label">菜单权限：</label>
			<div class="col-sm-10 form-control-static">
				<#if entity.id ==1>系统全部权限</#if>
				<div id="menuTrees" class="ztree"></div>
			</div>
		</div>
	
	</form>
    </div>
    <#include "/common/CommonJsImport.ftl">
    <script src="/libs/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
    <script>
	    $(function() {
			var url = "/systemRole/listSelectMenu?roleId=${entity.id?c}";
			var options = {
				id: "menuTrees",
		        url: url,
		        check: { enable: false},
		        expandLevel: 0
		    };
			$.tree.init(options);
		});
    </script>
</body>

</html>