package com.zdxj.model;

import javax.validation.constraints.NotBlank;

import com.zdxj.core.entity.BaseEntity;

public class DepartmentEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	/* 部门名称 */
	@NotBlank(message = "部门名称不能为空")
	private String name;

	/* 父级ID*/
	private Integer parentId ;
	
	/* 祖级列表*/
	private String ancestors ;
	
	/* 备注 */
	private String note;

	/* 位置排序 */
	private Integer position;
	
	//父级名称
	private String parentName ;
	
	public void setName(String name){
		this.name =name;
	}
	public String getName(){
		return this.name;
	}
	
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public String getAncestors() {
		return ancestors;
	}
	public void setAncestors(String ancestors) {
		this.ancestors = ancestors;
	}
	public void setNote(String note){
		this.note =note;
	}
	public String getNote(){
		return this.note;
	}
	
	public void setPosition(Integer position){
		this.position =position;
	}
	public Integer getPosition(){
		return this.position;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
}
