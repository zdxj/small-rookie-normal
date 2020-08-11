<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="(entity==null)?string('增加菜单','修改菜单')">
	<#include "/common/CommonCssImport.ftl">
</head>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-add-update">
			<input id="treeId" name="parentId" type="hidden" value="0"/>
			<input id="id" name="id" type="hidden"/>
			<div class="form-group">
				<label class="col-sm-3 control-label">上级菜单：</label>
				<div class="col-sm-8">
				    <div class="input-group">
					    <input class="form-control" type="text" onclick="selectMenuTree()" id="treeName" name="parentName" readonly="readonly"  required>
				        <span class="input-group-addon"><i class="fa fa-search"></i></span>
				    </div>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">菜单类型：</label>
				<div class="col-sm-8">
					<label class="radio-box"> <input type="radio" name="permType" value="2" required/> 目录 </label> 
					<label class="radio-box"> <input type="radio" name="permType" value="0" required/> 菜单 </label> 
					<label class="radio-box"> <input type="radio" name="permType" value="1" required/> 按钮 </label>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">菜单名称：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="menuName" id="menuName" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">权限编码：</label>
				<div class="col-sm-8">
					<input id="permCode" name="permCode" class="form-control" type="text" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">请求地址：</label>
				<div class="col-sm-8">
					<input id="url" name="url" class="form-control" type="text">
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">权限路径：</label>
				<div class="col-sm-8">
					<textarea class="form-control" id="permUrl" name="permUrl" style="height: 60px;" maxlength="512"></textarea>
					<span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 多个url间用英文逗号分隔</span>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">打开方式：</label>
				<div class="col-sm-8">
					<select id="openType" name="openType" class="form-control m-b">
	                    <option value="menuItem">页签</option>
	                    <option value="menuBlank">新窗口</option>
	                </select>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label is-required">显示排序：</label>
				<div class="col-sm-8">
					<input class="form-control" type="text" name="position" id="position" required>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-3 control-label">图标：</label>
				<div class="col-sm-8">
					<input id="icon" name="icon" class="form-control" type="text" placeholder="选择图标">
                    <div class="ms-parent" style="width: 100%;">
                        <div class="icon-drop animated flipInX" style="display: none;max-height:200px;overflow-y:auto">
                            <#include "/systemMenu/Icon.ftl">
                        </div>
                    </div>
				</div>
			</div>
		</form>
	</div>
	<#include "/common/CommonJsImport.ftl">
	 <script>
	 	$.operate.initFormData(${entity!"null"},["permType"]);
        function submitHandler() {
	        if ($.validate.form()) {
	            $.operate.save("${action!}", $('#form-add-update').serialize());
	        }
	    }

        $(function() {
        	var menuType = $('input[name="permType"]:checked').val();
            menuVisible(menuType);
        	$("input[name='icon']").focus(function() {
                $(".icon-drop").show();
            });
        	$("#form-add-update").click(function(event) {
        	    var obj = event.srcElement || event.target;
        	    if (!$(obj).is("input[name='icon']")) {
        	    	$(".icon-drop").hide();
        	    }
        	});
        	$(".icon-drop").find(".ico-list i").on("click", function() {
        		$('#icon').val($(this).attr('class'));
            });
        	$('input').on('ifChecked', function(event){  
        		var menuType = $(event.target).val();
        		menuVisible(menuType);
        	});  
        });

        function menuVisible(menuType){
        	if (menuType == "2") {
                $("#url").parents(".form-group").hide();
                $("#permUrl").parents(".form-group").hide();
                $("#icon").parents(".form-group").show();
                $("#openType").parents(".form-group").hide();
            } else if (menuType == "0") {
            	$("#url").parents(".form-group").show();
                $("#permUrl").parents(".form-group").show();
                $("#icon").parents(".form-group").show();
                $("#openType").parents(".form-group").show();
            } else if (menuType == "1") {
            	$("#url").parents(".form-group").hide();
                $("#permUrl").parents(".form-group").show();
                $("#icon").parents(".form-group").hide();
                $("#openType").parents(".form-group").hide();
            }
        }
        /*菜单管理-新增-选择菜单树*/
        function selectMenuTree() {
        	var treeId = $("#parentId").val();
        	var menuId = treeId > 0 ? treeId : 1;
        	var url = "/systemMenu/getById?id=" + menuId;
			var options = {
				title: '菜单选择',
				width: "380",
				url: url,
				callBack: doSubmit
			};
			$.modal.openOptions(options);
		}
		
		function doSubmit(index, layero){
			var body = layer.getChildFrame('body', index);
   			$("#treeId").val(body.find('#treeId').val());
   			$("#treeName").val(body.find('#treeName').val());
   			layer.close(index);
		}
    </script>
</body>
</html>
