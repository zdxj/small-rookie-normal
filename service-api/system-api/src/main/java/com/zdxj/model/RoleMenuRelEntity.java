package com.zdxj.model;

import com.zdxj.core.entity.BaseEntity;

public class RoleMenuRelEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/* 角色ID */
	private Integer roleId;

	/* 菜单ID */
	private Integer menuId;

	public void setRoleId(Integer roleId){
		this.roleId =roleId;
	}
	public Integer getRoleId(){
		return this.roleId;
	}
	
	public void setMenuId(Integer menuId){
		this.menuId =menuId;
	}
	public Integer getMenuId(){
		return this.menuId;
	}
	
	
}
