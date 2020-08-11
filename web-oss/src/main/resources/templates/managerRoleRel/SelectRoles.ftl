<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="关联角色">
	<#include "/common/CommonCssImport.ftl">
	<link href="/libs/layui/css/layui.css" rel="stylesheet"/>
</head>
<body class="white-bg">
	<div class="wrapper wrapper-content animated fadeInRight ibox-content">
		<form class="form-horizontal m" id="form-add-update">
			<div class="form-group row">
               <label class="col-lg-2 col-form-label text-right" for="val-name">用户名 <span class="text-danger">*</span></label>
               <div class="col-lg-4">
               	<div>${loginName!}</div>
               </div>
           </div>
           <div class="form-group row">
               <label class="col-lg-2 col-form-label text-right">角色</label>
               <div class="col-lg-6 align-self-center">
                   <div id="test4" class="demo-transfer"></div>
               </div>
           </div>
           <input type="hidden" name="id" id="id" value="${userId?c}"/>
		</form>
	</div>
	<#include "/common/CommonJsImport.ftl">
	 <script>
	 var data1 = ${roles1!};
     var data2 = ${roles2!};
     var required = ${required?c};
     var transfer ;
     if (data1 != "") {
         var left = [];
         $.each(data1,function (i, e) {
             left.push({"value":e.id,"title":e.name})
         });
     }
     layui.use(['transfer', 'layer', 'util'], function(){
         var $ = layui.$
             ,layer = layui.layer
             ,util = layui.util;

         transfer = layui.transfer;
         var data = left

         //初始右侧数据
         transfer.render({
             elem: '#test4'
             ,data: data
             ,title: ['未选择角色', '已选择角色']
             ,showSearch: true
             ,value: data2
             ,id:"key123"
         })
		
         //批量办法定事件
         util.event('lay-demoTransferActive', {
             getData: function(othis){
                 var getData = transfer.getData('key123'); //获取右侧数据
                 var ids = "";
                 $.each(getData,function (i, e) {
                     ids+=e.value;
                     ids += ",";
                 })
				 $.operate.save("${action!}", $('#form-add-update').serialize());
             }
         });

     });
     function submitHandler() {
        if ($.validate.form()) {
        	 var getData = transfer.getData('key123'); //获取右侧数据
        	 var ids = [];
        	 $.each(getData,function (i, e) {
        		 ids.push(e.value);
             })
        	$.operate.save("${action!}", {id:$("#id").val(),roleIds:ids});
        }
    }
    </script>
</body>
</html>