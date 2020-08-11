<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="系统菜单管理">
	<#include "/common/CommonCssImport.ftl">
</head>

<body class="gray-bg">
	<div class="container-div">
			<div class="row">
				<div class="col-sm-12 search-collapse">
					<form id="logininfor-form">
						<div class="select-list">
							<ul>
								<li><label>菜单名称：</label><input type="text" name="menuNameLike"/></li>
								<li><label>权限编码：</label><input type="text" name="permCodeLike"/></li>
								<li>
									<a class="btn btn-primary  btn-sm" onclick="$.treeTable.search()"><i class="fa fa-search"></i>&nbsp;搜索</a>
									<a class="btn btn-warning  btn-sm" onclick="$.form.reset()"><i class="fa fa-refresh"></i>&nbsp;重置</a>
								</li>
							</ul>
						</div>
					</form>
				</div>
				<div class="btn-group-sm" id="toolbar" role="group">
					<@shiro.hasPermission name="systemMenu:add">
					<a class="btn btn-success" onclick="$.operate.add()"><i class="fa fa-plus"></i> 新增</a>
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
			        url: "/systemMenu/queryTreeList",
			        createUrl: "/systemMenu/add?id={id}",
			        detailUrl: "/systemMenu/view?id={id}",
			        updateUrl: "/systemMenu/edit?id={id}",
			        modalName: "系统菜单",
			        escape: true,
			        showPageGo: true,
			        columns: [
				        {field: 'id',title: 'ID'},
				        {field: 'menuName',title: '菜单名称'},
				        {field: 'permCode',title: '权限编码'},
				        {field: 'permType',title: '菜单类型',
				        	formatter: function(value, item, index) {
				                if (item.permType == '0') {
				                    return '<span class="label label-primary">菜单</span>';
				                }else if (item.permType == '1') {
				                    return '<span class="label label-warning">按钮</span>';
				                }else if (item.permType == '2') {
				                    return '<span class="label label-success">目录</span>';
				                }
				            }
				        },
				        {field: 'position',title: '位置排序'},
				        {title: '操作',
				            formatter: function(value, row, index) {
				            	var actions = [];
				            	var _edit = '<@shiro.hasPermission name="systemMenu:edit"><a class="btn btn-success btn-xs" href="javascript:void(0)" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> </@shiro.hasPermission>';
				            	if(_edit !="") actions.push(_edit);
				            	var _add = '<@shiro.hasPermission name="systemMenu:add"><a class="btn btn-info btn-xs" href="javascript:void(0)" onclick="$.operate.add(\'' + row.id + '\')"><i class="fa fa-edit"></i>新增</a> </@shiro.hasPermission>';
				            	if(_add !="") actions.push(_add);
				            	var _detail = '<@shiro.hasPermission name="systemMenu:view"><a class="btn btn-warning btn-xs" href="javascript:void(0)" onclick="$.operate.detail(\'' + row.id + '\')"><i class="fa fa-search"></i>详情</a></@shiro.hasPermission>';
				            	if(_detail !="") actions.push(_detail);
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