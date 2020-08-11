
$(function() {
	validateKickout();
    validateRule();
    $(".imgcode").attr("src", "/code/verifycode?v=" + Math.random());
	$('.imgcode').click(function() {
		var url = "/code/verifycode?v=" + Math.random();
		$(".imgcode").attr("src", url);
	});
	var username = getCookie("l_username");
	if(username){
		$("#username").val(username);
		$("#chkRemember").prop("checked",true);
	}
});

$.validator.setDefaults({
    submitHandler: function() {
		login();
    }
});

function login() {
	var username = $.common.trim($("input[name='username']").val());
	if("" == username){
		$.modal.msg("用户名不能为空");
		return ;
	}
    var password = $.common.trim($("input[name='password']").val());
    if("" == password){
		$.modal.msg("密码不能为空");
		return ;
	}
    var validateCode = $("input[name='validateCode']").val();
    if("" == validateCode){
		$.modal.msg("验证码不能为空");
		return ;
	}
    $.modal.loading($("#btnSubmit").data("loading"));
    $.ajax({
        type: "post",
        url: "/login/doLogin",
        data: {
            "username": username,
            "password": password,
            "verifycode": validateCode
        },
        success: function(r) {
            if (r.code == "200") {
            	if ($("#chkRemember").prop("checked")) {
					SetCookie("l_username", $("#username").val());
				} else {
					SetCookie("l_username", "");
				}
                location.href =  '/';
            } else {
            	$.modal.closeLoading();
            	$('.imgcode').click();
            	$(".code").val("");
            	$.modal.msg(r.msg);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            username: {
                required: true
            },
            password: {
                required: true
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
            },
            password: {
                required: icon + "请输入您的密码",
            }
        }
    })
}

function validateKickout() {
	if (getParam("kickout") == 1) {
	    layer.alert("<font color='red'>您已在别处登录，请您修改密码或重新登录</font>", {
	        icon: 0,
	        title: "系统提示"
	    },
	    function(index) {
	        //关闭弹窗
	        layer.close(index);
	        if (top != self) {
	            top.location = "/";
	        } else {
	            var url  =  location.search;
	            if (url) {
	                self.location  = "/";
	            }
	        }
	    });
	}
}

function getParam(paramName) {
    var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return decodeURI(r[2]);
    return null;
}

function SetCookie(name, value) {
    var hours = 30 * 24;
    var exp = new Date();
    exp.setTime(exp.getTime() + hours * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";path=/;expires=" + exp.toGMTString();
}

function getCookie(name) {
	var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
}