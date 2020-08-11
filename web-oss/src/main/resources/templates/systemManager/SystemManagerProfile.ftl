<!DOCTYPE html>
<html lang="zh">
<head>
	<#assign title="管理员信息">
	<#include "/common/CommonCssImport.ftl">
    <style type="text/css">.user-info-head{position:relative;display:inline-block;}.user-info-head:hover:after{content:'\f030';position:absolute;left:0;right:0;top:0;bottom:0;color:#eee;background:rgba(0,0,0,0.5);font-family:FontAwesome;font-size:24px;font-style:normal;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale;cursor:pointer;line-height:110px;border-radius:50%;}</style>
</head>

<body class="gray-bg" style="font: 14px Helvetica Neue, Helvetica, PingFang SC, 微软雅黑, Tahoma, Arial, sans-serif !important;">
    <section class="section-content">
    <div class="row">
        <div class="col-sm-3 pr5">
            <div class="ibox float-e-margins">
                <div class="ibox-title ibox-title-gray dashboard-header gray-bg">
                    <h5>个人资料</h5>
                </div>
                <div class="ibox-content">
                    <div class="text-center">
                        <p class="user-info-head" onclick="avatar()">
                        <#if user.headImage?? && user.headImage !="">
							 <img class="img-circle img-lg" src="${user.headImage}">
						<#else>
							 <img class="img-circle img-lg" src="/img/profile.jpg">
						</#if>
                        </p>
                        <p><a href="javascript:avatar()">修改头像</a></p>
                    </div>
                    <ul class="list-group list-group-striped">
                        <li class="list-group-item"><i class="fa fa-user"></i>
                            <b class="font-noraml">登录名称：</b>
                            <p class="pull-right">${user.loginName!}</p>
                        </li>
                        <li class="list-group-item"><i class="fa fa-user"></i>
                            <b class="font-noraml">真实姓名：</b>
                            <p class="pull-right">${user.realName!}</p>
                        </li>
                        <li class="list-group-item"><i class="fa fa-phone"></i>
                            <b  class="font-noraml">手机号码：</b>
                            <p class="pull-right">${user.mobile!}</p>
                        </li>
                        <li class="list-group-item"><i class="fa fa-group"></i>
                            <b  class="font-noraml">所属部门：</b>
                            <p class="pull-right">${user.deptName!}</p>
                        </li>
                        <li class="list-group-item"><i class="fa fa-calendar"></i>
                            <b  class="font-noraml">创建时间：</b>
                            <p class="pull-right">${user.createTime?string("yyyy-MM-dd HH:mm:ss")}</p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        
        <div class="col-sm-9 about">
            <div class="ibox float-e-margins">
                <div class="ibox-title ibox-title-gray dashboard-header">
                    <h5>基本资料</h5>
                </div>
                <div class="ibox-content">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#user_info" data-toggle="tab" aria-expanded="true">基本资料</a></li>
                            <li><a href="#modify_password" data-toggle="tab" aria-expanded="false">修改密码</a></li>
                        </ul>
                        <div class="tab-content">
                            <!--用户信息-->
                            <div class="tab-pane active" id="user_info">
                                <form class="form-horizontal" id="form-user-edit">
                                    <!--隐藏ID-->
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">真实姓名：</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" name="realName" value="${user.realName!}" placeholder="请输入真实姓名">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">手机号码：</label>
                                        <div class="col-sm-10">
                                            <input type="text" class="form-control" name="mobile" isPhone="true" value="${user.mobile!}" maxlength="11" placeholder="请输入手机号码">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="button" class="btn btn-sm btn-primary" onclick="submitUserInfo()"><i class="fa fa-check"></i>保 存</button>&nbsp;
                                            <button type="button" class="btn btn-sm btn-danger" onclick="closeItem()"><i class="fa fa-reply-all"></i>关 闭 </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            
                            <!--修改密码-->
                            <div class="tab-pane" id="modify_password">
                                <form class="form-horizontal" id="form-user-resetPwd">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">旧密码：</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" name="oldPassword" placeholder="请输入旧密码">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">新密码：</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" name="newPassword" id="newPassword" placeholder="请输入新密码">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label">确认密码：</label>
                                        <div class="col-sm-10">
                                            <input type="password" class="form-control" name="confirmPassword" placeholder="请确认密码">
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-10">
                                            <button type="button" class="btn btn-sm btn-primary" onclick="submitChangPassword()"><i class="fa fa-check"></i>保 存</button>&nbsp;
                                            <button type="button" class="btn btn-sm btn-danger" onclick="closeItem()"><i class="fa fa-reply-all"></i>关 闭 </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	</section>
    
    <#include "/common/CommonJsImport.ftl">
    <script>
	    /*用户管理-头像*/
	    function avatar() {
	        var url = '/systemManager/headImage';
	        top.layer.open({
        		type: 2,
        		area: [$(window).width() + 'px', $(window).height() + 'px'],
        		fix: false,
        		//不固定
        		maxmin: true,
        		shade: 0.3,
        		title: "修改头像",
        		content: url,
        		btn: ['确定', '关闭'],
        	    // 弹层外区域关闭
        		shadeClose: true,
        		yes: function(index, layero) {
                    var iframeWin = layero.find('iframe')[0];
                    iframeWin.contentWindow.submitHandler(index, layero);
                },
        	    cancel: function(index) {
        	        return true;
        	    }
        	});
	    }
		
		function submitUserInfo() {
	        if ($.validate.form()) {
	        	$.operate.saveModal("/systemManager/doUpdateProfile", $('#form-user-edit').serialize());
	        }
	    }
	    
		function submitChangPassword () {
	        if ($.validate.form("form-user-resetPwd")) {
	        	$.operate.saveModal("/systemManager/updatePwd", $('#form-user-resetPwd').serialize(),updatePwdCallback);
	        }
	    }
		function updatePwdCallback(result){
			if(200==result.code){
				setTimeout(function() {
					window.top.location="/login" ;
				}, 2000);
				
			}
		}
	</script>
</body>
</html>
