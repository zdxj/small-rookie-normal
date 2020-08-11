<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title=">系统角色管理">
	<#include "/common/CommonCssImport.ftl">
</head>

<body class="gray-bg">
	<div class="container-div">
			<div class="row">
				<div class="col-sm-12 search-collapse">
					<form id="logininfor-form">
						<div class="select-list">
							<ul>
								<li><label>角色名称：</label><input type="text" name="nameLike"/></li>
								<li>
									<a class="btn btn-primary  btn-sm" onclick="$.table.search()"><i class="fa fa-search"></i>&nbsp;搜索</a>
									<a class="btn btn-warning  btn-sm" onclick="$.form.reset()"><i class="fa fa-refresh"></i>&nbsp;重置</a>
								</li>
							</ul>
						</div>
					</form>
				</div>
				<div class="btn-group-sm" id="toolbar" role="group">
					<@shiro.hasPermission name="systemRole:add">
					<a class="btn btn-success" onclick="$.operate.add()"><i class="fa fa-plus"></i> 新增</a>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="systemRole:remove">
					<a class="btn btn-danger multiple disabled" onclick="$.operate.removeAll()"><i class="fa fa-remove"></i> 删除</a>
					</@shiro.hasPermission>
		        </div>
		        <div class="col-sm-12 select-table table-striped">
				    <table id="bootstrap-table"></table>
				</div>
			</div>
		</div>
		<#include "/common/CommonJsImport.ftl">
		<script>
			$(function() {
			    var options = {
			        url: "/systemRole/queryList",
			        removeUrl:"/systemRole/removeByIds",
			        createUrl: "/systemRole/add",
			        detailUrl: "/systemRole/view?id={id}",
			        updateUrl: "/systemRole/edit?id={id}",
			        modalName: "系统角色",
			        escape: true,
			        showPageGo: true,
			        columns: [
			        	{checkbox: true},
				        {field: 'id',title: 'ID',align: 'center',sortable: true},
				        {field: 'createTime',title: '创建时间',align: 'center',sortable: true},
				        {field: 'name',title: '角色名称',align: 'center'},
				        {field: 'note',title: '备注',align: 'center'},
			        	{title: '操作',align: 'center',
			            	formatter: function(value, row, index) {
				            	var actions = [];
				            	var _edit = '<@shiro.hasPermission name="systemRole:edit"><a class="btn btn-success btn-xs" href="javascript:void(0)" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> </@shiro.hasPermission>';
				            	if(_edit !="") actions.push(_edit);
				            	var _remove = '<@shiro.hasPermission name="systemRole:remove"><a class="btn btn-danger btn-xs" href="javascript:void(0)" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a> </@shiro.hasPermission>';
				            	if(_remove !="" && 1 != row.id) actions.push(_remove);
				            	var _detail = '<@shiro.hasPermission name="systemRole:view"><a class="btn btn-warning btn-xs" href="javascript:void(0)" onclick="$.operate.detail(\'' + row.id + '\')"><i class="fa fa-search"></i>详情</a></@shiro.hasPermission>';
				            	if(_detail !="") actions.push(_detail);
				                return actions.join('');
			            	}
			        	}
		        	]
		    	};
		    	$.table.init(options);
			});
	</script>
</body>

</html>