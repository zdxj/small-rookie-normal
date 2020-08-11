package com.zdxj.core;

import java.util.List;

public class DataTableInfo {
	
	//总记录数
	private int total;
	
	//数据
	private List<?> rows;
	
	//结果状态
	private Integer code  ;
	
	//结果提示
	private String msg  ;
	
	public DataTableInfo() {
		
	}
	public DataTableInfo(List<?> list, int total) {
		this.rows = list;
        this.total = total;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
