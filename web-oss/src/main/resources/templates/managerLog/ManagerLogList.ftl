<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="登录日志管理">
	<#include "/common/CommonCssImport.ftl">
</head>
<body class="gray-bg">
	<div class="container-div">
		<div class="row">
			<div class="col-sm-12 search-collapse">
				<form id="logininfor-form">
					<div class="select-list">
						<ul>
							<li><label>登录名称：</label><input type="text" name="loginNameLike"/></li>
							<li>
								<a class="btn btn-primary  btn-sm" onclick="$.table.search()"><i class="fa fa-search"></i>&nbsp;搜索</a>
								<a class="btn btn-warning  btn-sm" onclick="$.form.reset()"><i class="fa fa-refresh"></i>&nbsp;重置</a>
							</li>
						</ul>
					</div>
				</form>
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
		        url: "/managerLog/queryList",
		        detailUrl: "/managerLog/view?id={id}",
		        modalName: "登录日志",
		        escape: true,
		        showPageGo: true,
		        columns: [
		        	{checkbox: true},
			        {field: 'id',title: 'ID',align: 'center',sortable: true},
			        {field: 'loginName',title: '用户名',align: 'center' },
			        {field: 'loginIp',title: 'IP地址',align: 'center',sortable: true},
			        {field: 'actionName',title: '用户动作',align: 'center' },
			        {field: 'description',title: '描述',align: 'center' },
			        {title: '操作',align: 'center',
			            formatter: function(value, row, index) {
			            	var actions = [];
			            	var _detail = '<@shiro.hasPermission name="managerLog:view"><a class="btn btn-warning btn-xs" href="javascript:void(0)" onclick="$.operate.detail(\'' + row.id + '\')"><i class="fa fa-search"></i>详情</a></@shiro.hasPermission>';
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