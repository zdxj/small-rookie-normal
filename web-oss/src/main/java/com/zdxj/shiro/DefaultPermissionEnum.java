package com.zdxj.shiro;

/**
 * 平台后台默认的权限信息
 * @author zhaodx
 *
 */

public enum DefaultPermissionEnum {

	verifycode("/code/verifycode","anon"),
	login("/login", "anon"), 
	doLogin("/login/doLogin","anon"),
	error("/error","anon"),
	logout("/login/outdo","anon"),
	//静态资源
	css("/css/**","anon"),
	js("/js/**","anon"),
	fonts("/fonts/**","anon"),
	img("/img/**","anon"),
	libs("/libs/**","anon"),
	//健康检测
	health("/health","anon"),
	//管理员修改信息
	toEditInfo("/systemManager/profile","authc"),
	editInfo("/systemManager/doUpdateProfile","authc"),
	updatePwd("/systemManager/updatePwd","authc"),
	toHeadImage("/systemManager/headImage","authc"),
	headImage("/systemManager/saveHeadImage","authc"),
	//切换皮肤
	switchSkin("/switchSkin","authc"),
	//首页
	main("/main","authc"),
	noPermission("/403","authc"),
	any("/","kickout,authc"),
	lastAny("/**","kickout,authc,roles[ROLE_1]");
	
	String value;
	String name;
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private DefaultPermissionEnum(String name, String value) {
		this.name = name;
		this.value = value;
	}
	public static String getNameByKey(String value) {
		if(null == value) {
			return "";
		}
		for (DefaultPermissionEnum spe : DefaultPermissionEnum.values()) {
			if ( spe.getValue().equals(value)) {
				return spe.getName();
			}
		}
		return "";
	}
}
