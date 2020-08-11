package com.zdxj.model;

import javax.validation.constraints.NotBlank;

import com.zdxj.core.entity.BaseEntity;

public class SystemRoleEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/* 角色名称 */
	@NotBlank(message =  "角色名称不能为空")
	private String name;

	/* 备注 */
	private String note;

	//权限菜单id
	private Integer[] menuIds;
	
	public void setName(String name){
		this.name =name;
	}
	public String getName(){
		return this.name;
	}
	
	public void setNote(String note){
		this.note =note;
	}
	public String getNote(){
		return this.note;
	}
	public Integer[] getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(Integer[] menuIds) {
		this.menuIds = menuIds;
	}
	
	
}
