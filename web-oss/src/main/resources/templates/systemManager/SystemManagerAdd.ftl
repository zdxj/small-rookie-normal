<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="(entity==null)?string('增加系统管理员','修改系统管理员')">
	<#include "/common/CommonCssImport.ftl">
	<link href="/libs/select2/select2.min.css" rel="stylesheet"/>
    <link href="/libs/select2/select2-bootstrap.css" rel="stylesheet"/>
</head>

<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-add-update">
			<input type="hidden" name="id" />
			<input name="departmentId" type="hidden" id="departmentId"/>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">登录名：</label>
				<div class="col-sm-8">
					<#if isAdd>
					<input type="text" required="required" class="form-control" id="loginName" name="loginName" maxlength="32" title="请输入登录名">
					<#else>
						<div id="userName" style='line-height:33px;'></div>
					</#if>
				</div>
			</div>
			<#if isAdd>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">密码：</label>
				<div class="col-sm-8">
					<input type="password" required="required" class="form-control" id="loginPassword" name="loginPassword" maxlength="64" title="请输入密码">
				</div>
			</div>
			</#if>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">真实姓名：</label>
				<div class="col-sm-8">
					<input type="text" required="required" class="form-control" id="realName" name="realName" maxlength="32" title="请输入真实姓名">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">所属部门：</label>
				<div class="col-sm-8">
					<div class="input-group">
					<input name="deptName" onclick="selectDeptTree()" id="deptName" type="text" placeholder="请选择归属部门" class="form-control" required>
                    <span class="input-group-addon"><i class="fa fa-search"></i></span>
                    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">手机号码：</label>
				<div class="col-sm-8">
					<input type="text" required="required" class="form-control" id="mobile" isPhone="true" name="mobile" maxlength="11" title="请输入手机号码">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">是否启用：</label>
				<div class="col-sm-8">
					 <label class="toggle-switch switch-solid">
                           <input type="checkbox" id="enable" name="enable" checked="checked">
                           <span></span>
                     </label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注：</label>
				<div class="col-sm-8">
					<textarea class="form-control" id="note" name="note" style="height: 60px;" maxlength="128"></textarea>
				</div>
			</div>
		</form>
	</div>
	<#include "/common/CommonJsImport.ftl">
	<script src="/libs/select2/select2.min.js"></script>
	<script type="text/javascript">
		var _data = ${entity!"null"};
		$.operate.initFormData(_data);
		$(function(){
			if("${isAdd?c}"=="false"){
				$("#userName").html( _data['loginName']);
				if(!_data['enable']){
					$("#enable").removeAttr("value");
					$("#enable").removeAttr("checked");
				}
			}
		})
		$("#form-add-update").validate({
			focusCleanup: true
		});
		function submitHandler() {
	        if ($.validate.form()) {
	        	$.operate.save("${action!}", $('#form-add-update').serialize());
	        }
	    }
		function selectDeptTree() {
        	var treeId = $("#departmentId").val();
        	var deptId = $.common.isEmpty(treeId) ? "1" : $("#treeId").val();
        	var url = "/department/getById?id=" + deptId;
			var options = {
				title: '选择部门',
				width: "380",
				url: url,
				callBack: doSubmit
			};
			$.modal.openOptions(options);
		}
		function doSubmit(index, layero){
			var tree = layero.find("iframe")[0].contentWindow.$._tree;
			if ($.tree.notAllowParents(tree)) {
				var body = layer.getChildFrame('body', index);
    			$("#departmentId").val(body.find('#treeId').val());
    			$("#deptName").val(body.find('#treeName').val());
    			layer.close(index);
			}
		}
	</script>
</body>

</html>