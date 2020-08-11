package com.zdxj.model;

import com.zdxj.core.entity.BaseEntity;

public class ManagerLogEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	
	/* 登录名 */
	private String loginName;

	/* 用户动作 */
	private String actionName;

	/* 工作描述 */
	private String description;

	/* 登录ip地址 */
	private String loginIp;

	public void setLoginName(String loginName){
		this.loginName =loginName;
	}
	public String getLoginName(){
		return this.loginName;
	}
	
	public void setActionName(String actionName){
		this.actionName =actionName;
	}
	public String getActionName(){
		return this.actionName;
	}
	
	public void setDescription(String description){
		this.description =description;
	}
	public String getDescription(){
		return this.description;
	}
	
	public void setLoginIp(String loginIp){
		this.loginIp =loginIp;
	}
	public String getLoginIp(){
		return this.loginIp;
	}
	
	
}
