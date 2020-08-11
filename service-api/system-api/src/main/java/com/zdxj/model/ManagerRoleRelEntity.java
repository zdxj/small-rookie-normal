package com.zdxj.model;

import com.zdxj.core.entity.BaseEntity;

public class ManagerRoleRelEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	
	/* 管理员ID */
	private Integer managerId;

	/* 角色ID */
	private Integer roleId;

	public void setManagerId(Integer managerId){
		this.managerId =managerId;
	}
	public Integer getManagerId(){
		return this.managerId;
	}
	
	public void setRoleId(Integer roleId){
		this.roleId =roleId;
	}
	public Integer getRoleId(){
		return this.roleId;
	}
	
	
}
