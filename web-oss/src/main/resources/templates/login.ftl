<!DOCTYPE html>
<html  lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>运营管理平台</title>
    <meta name="description" content="运营管理平台">
    <link href="/css/bootstrap.min.css"  rel="stylesheet"/>
    <link href="/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="/css/style.css" rel="stylesheet"/>
    <link href="/css/login/login.css" rel="stylesheet"/>
    <link href="/css/ui/common-ui.css" rel="stylesheet"/>
    <!-- 360浏览器急速模式 -->
    <meta name="renderer" content="webkit">
    <!-- 避免IE使用兼容模式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="/img/favicon.ico"/>
    <style type="text/css">label.error { position:inherit;  }</style>
    <script>
        if(window.top!==window.self){alert('未登录或登录超时。请重新登录');window.top.location=window.location};
    </script>
</head>
<body class="signin">
    <div class="signinpanel">
        <div class="row">
            <div class="col-sm-7">
                <div class="signin-info-left">
                    <img alt="" src="/img/small-rookie.png" width="300px">
                </div>
            </div>
            <div class="col-sm-5">
                <form id="signupForm" autocomplete="off">
                    <h4 class="no-margins">登录：</h4>
                    <input type="text" name="username" id="username" class="form-control uname" placeholder="用户名"/>
                    <input type="password" name="password" class="form-control pword" placeholder="密码"/>
					<div class="row m-t">
						<div class="col-xs-6">
						    <input type="text" name="validateCode" class="form-control code" placeholder="验证码" maxlength="5" />
						</div>
						<div class="col-xs-6">
							<a href="javascript:void(0);" title="点击更换验证码">
								<img src="/img/code_loading.gif" class="imgcode" width="85%"/>
							</a>
						</div>
					</div>
                    <div class="checkbox-custom">
				        <input type="checkbox" id="chkRemember" name="chkRemember"> <label for="chkRemember">记住用户名</label>
				    </div>
                    <button class="btn btn-success btn-block" id="btnSubmit" data-loading="正在验证登录，请稍后...">登录</button>
                </form>
            </div>
        </div>
    </div>
<!-- 全局js -->
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<!-- 验证插件 -->
<script src="/libs/validate/jquery.validate.min.js" ></script>
<script src="/libs/validate/messages_zh.min.js"></script>
<script src="/libs/layer/layer.min.js"></script>
<script src="/libs/blockUI/jquery.blockUI.js"></script>
<script src="/js/ui/common-ui.js"></script>
<script src="/js/login/login.js"></script>
</body>
</html>
