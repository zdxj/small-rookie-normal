<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title=">系统管理员管理">
	<#include "/common/CommonCssImport.ftl">
	<link href="/libs/jquery-layout/jquery.layout-latest.css" rel="stylesheet"/>
	<link href="/libs/jquery-ztree/3.5/css/metro/zTreeStyle.css" rel="stylesheet"/>
</head>

<body class="gray-bg">
	<div class="ui-layout-west">
		<div class="box box-main">
			<div class="box-header">
				<div class="box-title">
					<i class="fa icon-grid"></i> 组织机构
				</div>
				<div class="box-tools pull-right">
					<button type="button" class="btn btn-box-tool" id="btnExpand" title="展开" style="display:none;"><i class="fa fa-chevron-up"></i></button>
					<button type="button" class="btn btn-box-tool" id="btnCollapse" title="折叠"><i class="fa fa-chevron-down"></i></button>
					<button type="button" class="btn btn-box-tool" id="btnRefresh" title="刷新部门"><i class="fa fa-refresh"></i></button>
				</div>
			</div>
			<div class="ui-layout-content">
				<div id="tree" class="ztree"></div>
			</div>
		</div>
	</div>
	<div class="ui-layout-center">
		<div class="container-div">
			<div class="row">
				<div class="col-sm-12 search-collapse">
					<form id="logininfor-form">
						<input type="hidden" id="parentDeptId" name="parentDeptId">
						<div class="select-list">
							<ul>
								<li><label>登录名：</label><input type="text" name="loginNameLike"/></li>
								<li><label>手机号码：</label><input type="text" name="mobileLike"/></li>
								<li>
									<a class="btn btn-primary  btn-sm" onclick="$.table.search()"><i class="fa fa-search"></i>&nbsp;搜索</a>
									<a class="btn btn-warning  btn-sm" onclick="$.form.reset()"><i class="fa fa-refresh"></i>&nbsp;重置</a>
								</li>
							</ul>
						</div>
					</form>
				</div>
				<div class="btn-group-sm" id="toolbar" role="group">
					<@shiro.hasPermission name="systemManager:add">
					<a class="btn btn-success" onclick="$.operate.add()"><i class="fa fa-plus"></i> 新增</a>
					</@shiro.hasPermission>
					<@shiro.hasPermission name="systemManager:remove">
					<a class="btn btn-danger multiple disabled" onclick="$.operate.removeAll()"><i class="fa fa-remove"></i> 删除</a>
					</@shiro.hasPermission>
		        </div>
		        <div class="col-sm-12 select-table table-striped">
				    <table id="bootstrap-table"></table>
				</div>
			</div>
		</div>
	</div>
		<#include "/common/CommonJsImport.ftl">
		 <script src="/libs/jquery-layout/jquery.layout-latest.js"></script>
		 <script src="/libs/jquery-ztree/3.5/js/jquery.ztree.all-3.5.js"></script>
		<script>
			var _edit = '<@shiro.hasPermission name="systemManager:edit">true</@shiro.hasPermission>';
			var _view = '<@shiro.hasPermission name="systemManager:view">true</@shiro.hasPermission>';
			var _remove = '<@shiro.hasPermission name="systemManager:remove">true</@shiro.hasPermission>';
			var _resetPwd = '<@shiro.hasPermission name="systemManager:resetPwd">true</@shiro.hasPermission>';
			var _selectRole = '<@shiro.hasPermission name="systemManager:selectRole">true</@shiro.hasPermission>';
				$(function() {
					var panehHidden = false;
				    if ($(this).width() < 769) {
				        panehHidden = true;
				    }
				    $('body').layout({ initClosed: panehHidden, west__size: 185 });
				    queryUserList();
				    queryDeptTree();
				});
				
				function queryUserList(){
					var options = {
				        url: "/systemManager/queryList",
				        removeUrl:"/systemManager/removeByIds",
				        createUrl: "/systemManager/add",
				        detailUrl: "/systemManager/view?id={id}",
				        updateUrl: "/systemManager/edit?id={id}",
				        modalName: "系统管理员",
				        escape: true,
				        showPageGo: true,
				        columns: [
				        	{checkbox: true},
					        {field: 'id',title: 'ID',align: 'center',sortable: true},
					        {field: 'loginName',title: '登录名',align: 'center',sortable: true},
					        {field: 'realName',title: '真实姓名',align: 'center',sortable: true},
					        {field: 'deptName',title: '所属部门',align: 'center'},
					        {field: 'mobile',title: '手机号码',align: 'center',sortable: true},
					        {field: 'loginTime',title: '登录时间',align: 'center',sortable: true},
					        {field: 'enable',title: '是否启用',align: 'center',
					        	formatter: function (value, row, index) {
					        		if(_edit && row.id != 1){
					        			return statusTools(row);
					        		}else{
					        			return row.enable?"是":"否"
					        		}
					        	}
					        },
				        	{title: '操作',align: 'center',
					            formatter: function(value, row, index) {
					            	var actions = [];
					            	if(_edit =="true") actions.push('<a class="btn btn-success btn-xs" href="javascript:void(0)" onclick="$.operate.edit(\'' + row.id + '\')"><i class="fa fa-edit"></i>编辑</a> ');
					            	if(_view =="true") actions.push('<a class="btn btn-warning btn-xs" href="javascript:void(0)" onclick="$.operate.detail(\'' + row.id + '\')"><i class="fa fa-search"></i>详情</a> ');
					            	if(_remove =="true" && 1 != row.id) actions.push('<a class="btn btn-danger btn-xs" href="javascript:void(0)" onclick="$.operate.remove(\'' + row.id + '\')"><i class="fa fa-remove"></i>删除</a> ');
					            	var more = [];
					            	if(_resetPwd =="true") more.push("<a class='btn btn-default btn-xs' href='javascript:void(0)' onclick='resetPwd(" + row.id + ")'><i class='fa fa-key'></i>重置密码</a> ");
					            	if(_selectRole =="true" && 1 != row.id) more.push("<a class='btn btn-default btn-xs' href='javascript:void(0)' onclick='authRole(" + row.id + ")'><i class='fa fa-check-square-o'></i>分配角色</a>");
					                if(more.length>0){
						            	actions.push('<a tabindex="0" class="btn btn-info btn-xs" role="button" data-container="body" data-placement="left" data-toggle="popover" data-html="true" data-trigger="hover" data-content="' + more.join('') + '"><i class="fa fa-chevron-circle-right"></i>更多操作</a>');
					                }
					                return actions.join('');
					            }
				        	}
			        	]
			    	};
			    $.table.init(options);
			}
			function queryDeptTree(){
				var url = "/department/listTreeDept";
				var options = {
			        url: url,
			        expandLevel: 2,
			        onClick : zOnClick
			    };
				$.tree.init(options);
				
				function zOnClick(event, treeId, treeNode) {
					$("#parentDeptId").val(treeNode.id);
					$.table.search();
				}
			}
			/* 用户状态显示 */
			function statusTools(row) {
			    if (row.enable == 1) {
	    			return '<i class=\"fa fa-toggle-on text-info fa-2x\" onclick="enableStatus(\'' + row.id + '\',\''+0+'\')"></i> ';
	    		} else {
	    			return '<i class=\"fa fa-toggle-off text-info fa-2x\" onclick="enableStatus(\'' + row.id + '\',\''+1+'\')"></i> ';
	    		}
			}
			function enableStatus(id,status){
				$.modal.confirm("确认要"+(status==0?"停用":"启用")+"用户吗？", function() {
					$.operate.post("/systemManager/doUpdateEnable", { "id": id, "enable": status });
			    })
			}
			$('#btnExpand').click(function() {
				$._tree.expandAll(true);
			    $(this).hide();
			    $('#btnCollapse').show();
			});
			
			$('#btnCollapse').click(function() {
				$._tree.expandAll(false);
			    $(this).hide();
			    $('#btnExpand').show();
			});
			
			$('#btnRefresh').click(function() {
				queryDeptTree();
			});
			function resetPwd(userId) {
			    var url = '/systemManager/resetPassword?id=' + userId;
			    $.modal.open("重置密码", url, '800', '400');
			}
			function authRole(userId) {
			    var url = '/managerRoleRel/selectRole?id=' + userId;
			    $.modal.open("用户分配角色", url);
			}
	</script>
</body>

</html>