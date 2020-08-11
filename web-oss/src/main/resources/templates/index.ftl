<!DOCTYPE html>
<html  lang="zh">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">
    <title>首页</title>
    <!-- 避免IE使用兼容模式 -->
 	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <link rel="shortcut icon" href="/img/favicon.ico" rel="stylesheet"/>
    <link href="/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="/css/jquery.contextMenu.min.css" rel="stylesheet"/>
    <link href="/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="/css/main/animate.css" rel="stylesheet"/>
    <link href="/css/style.css" rel="stylesheet"/>
    <link href="/css/skins.css" rel="stylesheet"/>
    <link href="/css/ui/common-ui.css" rel="stylesheet"/>
</head>
<body class="fixed-sidebar full-height-layout gray-bg" style="overflow: hidden">
<div id="wrapper">

    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation">
        <div class="nav-close">
            <i class="fa fa-times-circle"></i>
        </div>
        <a href="/">
            <li class="logo hidden-xs">
                <span class="logo-lg">Small-Rookie</span>
            </li>
         </a>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
            	<li>
            		<div class="user-panel">
            			<a class="menuItem noactive" title="个人中心" href="/">
            				<div class="hide" text="个人中心"></div>
					        <div class="pull-left image">
					        	<#if SESSION_MANAGER_KEY.headImage?? && SESSION_MANAGER_KEY.headImage !="">
									<img src="${SESSION_MANAGER_KEY.headImage}" class="img-circle" alt="User Image">
								<#else>
									<img src="/img/profile.jpg" class="img-circle" alt="User Image">
								</#if>
					        </div>
				        </a>
				        <div class="pull-left info">
				          <p>${SESSION_MANAGER_KEY.realName!}</p>
				          <a href="javascript:;"><i class="fa fa-circle text-success"></i> 在线</a>
				          <a href="javascript:;" onclick="logout()" style="padding-left:5px;"><i class="fa fa-sign-out text-danger"></i> 注销</a>
				        </div>
				    </div>
            	</li>
                <li>
                    <a class="menuItem" href="/main"><i class="fa fa-home"></i> <span class="nav-label">首页</span> </a>
                </li>
                <#if menuList??>
                <#list menuList as info>
                <li>
                	<a <#if info.dataUrl?? && info.dataUrl !=""> class="${info.openType!}"</#if> href="<#if info.dataUrl?? && info.dataUrl !=""> ${info.dataUrl!}<#else>#</#if>">
                		<i class="fa ${info.icon!}"></i>
                    	<span class="nav-label">${info.name!}</span>
                    	<span <#if info.dataUrl?? && (info.dataUrl !="")> <#else>class="fa arrow"</#if>></span>
                	</a>
                    <ul class="nav nav-second-level collapse">
                    	<#if info.children??>
                		<#list info.children as second>
						<li>
							<#if second.children?? && (second.children?size>0)>
								<a href="#">${second.name!}<span class="fa arrow"></span></a>
							<#else>
								<a class="${second.openType!'menuItem'}" href="${second.dataUrl!}">${second.name!}</a>
							</#if>
							<#if second.children?? && (second.children?size>0)>
								<ul class="nav nav-third-level">
									<#list second.children as third>
									<li>
									    <#if third.children?? && (third.children?size>0)>
											<a href="#">${third.name!}<span class="fa arrow"></span></a>
										<#else>
											<a class="${third.openType!'menuItem'}" href="${third.dataUrl!}">${third.name!}</a>
										</#if>
										<#if third.children?? && (third.children?size>0)>
										    <ul class="nav nav-four-level">
										    	<#list third.children as four>
										      		<li><a class="${four.openType!'menuItem'}" href="${four.dataUrl!}">${four.name!}</a></li>
										    	</#list>
										    </ul>
									    </#if>
									</li>
									</#list>
								</ul>
							</#if>
						</li>
						</#list>
                		</#if>
					</ul>
                </li>
                </#list>
                </#if>
            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->

    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header">
                    <a class="navbar-minimalize minimalize-styl-2" style="color:#FFF;" href="#" title="收起菜单">
                    	<i class="fa fa-bars"></i>
                    </a>
                </div>
                <ul class="nav navbar-top-links navbar-right welcome-message">
	                <li><a title="全屏显示" href="javascript:void(0)" id="fullScreen"><i class="fa fa-arrows-alt"></i> 全屏显示</a></li>
                    <li class="dropdown user-menu">
						<a href="javascript:void(0)" class="dropdown-toggle" data-hover="dropdown">
							<#if SESSION_MANAGER_KEY.headImage?? && SESSION_MANAGER_KEY.headImage !="">
								<img src="${SESSION_MANAGER_KEY.headImage}" class="user-image">
							<#else>
								<img src="/img/profile.jpg" class="user-image">
							</#if>
							<span class="hidden-xs">${SESSION_MANAGER_KEY.realName!}</span>
						</a>
						<ul class="dropdown-menu">
							<li class="mt5">
								<a href="/systemManager/profile" class="menuItem">
								<i class="fa fa-user"></i> 个人中心</a>
							</li>
							<li>
								<a onclick="switchSkin()">
								<i class="fa fa-dashboard"></i> 切换主题</a>
							</li>
							<li class="divider"></li>
							<li>
								<a href="javascript:;" onclick="logout()">
								<i class="fa fa-sign-out"></i> 退出登录</a>
							</li>
						</ul>
					</li>
                </ul>
                <ul class="nav navbar-top-links navbar-right" style="float: left !important;">
                	<#if topList?? && (topList?size>0)>
                	<#list topList as top>
                    	<li><a title="${top.menuName!}" href="/"><i class="fa ${top.icon!}"></i> ${top.menuName!}</a></li>
                    </#list>
                    </#if>
                </ul>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left tabLeft">
                <i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active menuTab" data-id="/main">首页</a>
                </div>
            </nav>
            <button class="roll-nav roll-right tabRight">
                <i class="fa fa-forward"></i>
            </button>
            <a href="javascript:void(0);" class="roll-nav roll-right tabReload"><i class="fa fa-refresh"></i> 刷新</a>
        </div>

        <a id="ax_close_max" class="ax_close_max" href="#" title="关闭全屏"> <i class="fa fa-times-circle-o"></i> </a>

        <div class="row mainContent" id="content-main">
            <iframe class="RuoYi_iframe" name="iframe0" width="100%" height="100%" data-id="/main"
                    src="/main" frameborder="0" seamless></iframe>
        </div>
    </div>
    <!--右侧部分结束-->
</div>
<!-- 全局js -->
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap/bootstrap.min.js"></script>
<script src="/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="/js/jquery-contextMenu/jquery.contextMenu.min.js"></script>
<script src="/libs/blockUI/jquery.blockUI.js"></script>
<script src="/libs/layer/layer.min.js"></script>
<script src="/js/ui/common.js"></script>
<script src="/js/ui/common-ui.js"></script>
<script src="/js/index.js"></script>
<script src="/libs/fullscreen/jquery.fullscreen.js"></script>
<script>
// 皮肤缓存
var skin = storage.get("skin");
// history（表示去掉地址的#）否则地址以"#"形式展示
var mode = "history";
// 历史访问路径缓存
var historyPath = storage.get("historyPath");
// 是否页签与菜单联动
var isLinkage = true;

// 本地主题优先，未设置取系统配置
if($.common.isNotEmpty(skin)){
	$("body").addClass(skin.split('|')[0]);
	$("body").addClass(skin.split('|')[1]);
} else {
	$("body").addClass("theme-dark");
	$("body").addClass("skin-blue");
}

/* 切换主题 */
function switchSkin() {
    layer.open({
		type : 2,
		shadeClose : true,
		title : "切换主题",
		area : ["530px", "386px"],
		content : [ "/switchSkin", 'no']
	})
}

/** 刷新时访问路径页签 */
function applyPath(url) {
	$('a[href$="' + decodeURI(url) + '"]').click();
	if (!$('a[href$="' + url + '"]').hasClass("noactive")) {
	    $('a[href$="' + url + '"]').parent("li").addClass("selected").parents("li").addClass("active").end().parents("ul").addClass("in");
	}
}

$(function() {
	if($.common.equals("history", mode) && window.performance.navigation.type == 1) {
		var url = storage.get('publicPath');
	    if ($.common.isNotEmpty(url)) {
	    	applyPath(url);
	    }
	} else {
		var hash = location.hash;
	    if ($.common.isNotEmpty(hash)) {
	        var url = hash.substring(1, hash.length);
	        applyPath(url);
	    }
	}
});
function logout(){
	$.getJSON("/login/outdo?t="+ Math.random(), function(data) {
		if (data.code == 200) {
			location = '/login';
		}
	});
}
</script>
</body>
</html>
