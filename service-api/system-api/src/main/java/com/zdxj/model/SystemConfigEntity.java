package com.zdxj.model;

import com.zdxj.core.entity.BaseEntity;

public class SystemConfigEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	
	/* 文件服务器地址 */
	private String fileServer;

	public void setFileServer(String fileServer){
		this.fileServer =fileServer;
	}
	public String getFileServer(){
		return this.fileServer;
	}
	
	
}
