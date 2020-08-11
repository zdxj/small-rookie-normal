package com.zdxj.model;

import java.io.Serializable;

/**
 * ztree插件帮助类
 * @author zhaodx
 * @date 2020-08-03 11:21
 *
 */
public class ZtreeEntity implements Serializable{

	private static final long serialVersionUID = 5622277423820164233L;

	/* 节点ID */
    private Integer id;

    /* 节点父ID */
    private Integer pId;

    /* 节点名称 */
    private String name;

    /* 节点标题 */
    private String title;

    /* 是否勾选 */
    private boolean checked = false;

    /* 是否展开 */
    private boolean open = false;

    /* 是否能勾选 */
    private boolean nocheck = false;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public boolean isNocheck() {
		return nocheck;
	}

	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}
    
    
}
