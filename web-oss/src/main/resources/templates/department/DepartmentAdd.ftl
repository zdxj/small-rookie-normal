<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="(entity==null)?string('增加部门','修改部门')">
	<#include "/common/CommonCssImport.ftl">
</head>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-add-update">
			<input id="treeId" name="parentId" type="hidden" value="0"/>
			<input id="id" name="id" type="hidden"/>
			<div class="form-group">
				<label class="col-sm-3 control-label">上级部门：</label>
				<div class="col-sm-8">
				    <div class="input-group">
						<input class="form-control" type="text" onclick="selectDeptTree()" id="treeName" name="parentName" readonly="readonly">
					    <span class="input-group-addon"><i class="fa fa-search"></i></span>
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">部门名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="name" id="name" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">备注：</label>
				<div class="col-sm-8">
					<textarea class="form-control" id="note" name="note" style="height: 60px;" maxlength="128"></textarea>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">排序：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="position" digits="true" required>
				</div>
			</div>
		</form>
	</div>
	<#include "/common/CommonJsImport.ftl">
	<script type="text/javascript">
		$.operate.initFormData(${entity!"null"});
		function submitHandler() {
	        if ($.validate.form()) {
	        	$.operate.save("${action!}", $('#form-add-update').serialize());
	        }
	    }
	
		/*部门管理-新增-选择父部门树*/
		function selectDeptTree() {
			var excludeId = $("input[name='id']").val();
			excludeId = null==excludeId?0:excludeId;
			var options = {
				title: '部门选择',
				width: "380",
				url: "/department/getById?id=" + $("#treeId").val()+"&excludeId="+excludeId,
				callBack: excludeId==null?doSubmit:doEditSubmit
			};
			$.modal.openOptions(options);
		}
		
		function doSubmit(index, layero){
			var body = layer.getChildFrame('body', index);
   			$("#treeId").val(body.find('#treeId').val());
   			$("#treeName").val(body.find('#treeName').val());
   			layer.close(index);
		}
		function doEditSubmit(index, layero){
			var tree = layero.find("iframe")[0].contentWindow.$._tree;
			if ($.tree.notAllowLastLevel(tree)) {
	   			var body = layer.getChildFrame('body', index);
	   			$("#treeId").val(body.find('#treeId').val());
	   			$("#treeName").val(body.find('#treeName').val());
	   			layer.close(index);
			}
		}
	</script>
</body>
</html>