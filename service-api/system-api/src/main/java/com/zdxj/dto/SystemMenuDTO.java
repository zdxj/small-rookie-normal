package com.zdxj.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单帮助类
 * @author zhaodx
 *
 */
public class SystemMenuDTO implements Serializable{

	private static final long serialVersionUID = -2533596185720444769L;

	//id
    private Integer id;
    
	//菜单名称 
	private String name;
	
	//父ID
	private Integer parentId;
	
	// 打开方式,menuItem页签 menuBlank新窗口 
	private String openType ;
	
	// 菜单路径 
	private String dataUrl;
	
	// 菜单图标
	private String icon;
	
	//是否选中
	private boolean checked ;
	
	//是否展开
	private boolean open ;
	
	//子菜单
	private List<SystemMenuDTO> children ;

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

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
	public String getOpenType() {
		return openType;
	}

	public void setOpenType(String openType) {
		this.openType = openType;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	
	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<SystemMenuDTO> getChildren() {
		return children;
	}

	public void setChildren(List<SystemMenuDTO> children) {
		this.children = children;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}
	
	
}
