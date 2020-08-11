package com.zdxj.dto;

import java.io.Serializable;

public class SystemRoleDTO implements Serializable{

	private static final long serialVersionUID = 1505831859240953580L;

	//id
    private Integer id;
    
    //角色名称
	private String name;
	
	//是否选中
	private boolean selected ;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	
}
