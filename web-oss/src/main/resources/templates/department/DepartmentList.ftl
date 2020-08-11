<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="系统部门管理">
	<#include "/common/CommonCssImport.ftl">
</head>

<body class="gray-bg">
	<div class="container-div">
			<div class="row">
				<div class="col-sm-12 search-collapse">
					<form id="logininfor-form">
						<div class="select-list">
							<ul>
								<li><label>部门名称：</label><input type="text" name="nameLike"/></li>
								<li>
									<a class="btn btn-primary  btn-sm" onclick="$.treeTable.search()"><i class="fa fa-search"></i>&nbsp;搜索</a>
									<a class="btn btn-warning  btn-sm" onclick="$.form.reset()"><i class="fa fa-refresh"></i>&nbsp;重置</a>
								</li>
							</ul>
						</div>
					</form>
				</div>
				<div class="btn-group-sm" id="toolbar" role="group">
					<@shiro.hasPermission name="department:add">
					<a class="btn btn-success" onclick="$.operate.add(1)"><i class="fa fa-plus"></i> 新增</a>
					</@shiro.hasPermission>
					<a class="btn btn-info" id="expandAllBtn"><i class="fa fa-exchange"></i> 展开/折叠</a>
		        </div>
		        <div class="col-sm-12 select-table table-striped">
				    <table id="bootstrap-tree-table"></table>
				</div>
			</div>
		</div>
		<#include "/common/CommonJsImport.ftl">
		<script>
			$(function() {
			    var options = {
		    		code: "id",
			        parentCode: "parentId",
			        uniqueId: "id",
			        expandAll: false,
			        expandFirst: false,
			        url: "/department/queryTreeList",
			        removeUrl: "/department/removeByIds",
			        createUrl: "/department/add?id={id}",
			        detailUrl: "/department/view?id={id}",
			        updateUrl: "/department/edit?id={id}",
			        modalName: "系统部门",
			        escape: true,
			        showPageGo: true,
			        columns: [
				        {field: 'id',title: 'ID'},
				        {field: 'name',title: '部门名称'},
				        {field: 'position',title: '位置排序'},
				        {field: 'note',title: '备注'},
				        {title: '操作',
				            formatter: function(value, row, index) {
				            	var actions = [];
				            	var _edit = '<@shiro.hasPermission name="department:edit"><a class="btn btn-success btn-xs" href="javascript:void(0)" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> </@shiro.hasPermission>';
				            	if(_edit !="") actions.push(_edit);
				            	var _add = '<@shiro.hasPermission name="department:add"><a class="btn btn-info btn-xs" href="javascript:void(0)" onclick="$.operate.add(\'' + row.id + '\')"><i class="fa fa-edit"></i>新增</a> </@shiro.hasPermission>';
				            	if(_add !="") actions.push(_add);
				            	var _detail = '<@shiro.hasPermission name="department:view"><a class="btn btn-warning btn-xs" href="javascript:void(0)" onclick="$.operate.detail(\'' + row.id + '\')"><i class="fa fa-search"></i>详情</a> </@shiro.hasPermission>';
				            	if(_detail !="") actions.push(_detail);
				            	var _remove = '<@shiro.hasPermission name="department:remove"><a class="btn btn-danger btn-xs" href="javascript:void(0)" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-trash"></i>删除</a></@shiro.hasPermission>';
								if(_remove !="" && 1 != row.id) actions.push(_remove);
								return actions.join('');
				            }
				        }
			        ]
			    };
			    $.treeTable.init(options);
			});
	</script>
</body>

</html>