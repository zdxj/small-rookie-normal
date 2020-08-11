package com.zdxj.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zdxj.core.entity.BaseEntity;

public class SystemMenuEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	public static final Integer premTypeMenu = 0 ;
	public static final Integer premTypeButton = 1 ;
	public static final Integer premTypeCata = 2 ;
	
	public static final String OPEN_TYPE_ITEM = "menuItem";
	public static final String OPEN_TYPE_BLANK = "menuBlank";
	
	/* 菜单名称 */
	@NotBlank(message = "菜单名称不能为空")
	private String menuName;

	/* 权限编码 */
	@NotBlank(message = "权限编码不能为空")
	private String permCode;

	/* 菜单类型,0:菜单，1:按钮,2:目录 */
	@NotNull(message = "菜单类型不能为空")
	private Integer permType;
	
	/* 打开方式,menuItem页签 menuBlank新窗口 */
	@NotBlank(message = "打开方式不能为空")
	private String openType ;
	
	/* 菜单图标 */
	private String icon ;

	/* 父ID */
	private Integer parentId;

	/* 菜单路径 */
	private String url;

	/* 权限路径 */
	private String permUrl;

	/* 位置排序 */
	private Integer position;

	//父级名称
	private String parentName ;
	
	public void setMenuName(String menuName){
		this.menuName =menuName;
	}
	public String getMenuName(){
		return this.menuName;
	}
	
	public void setPermCode(String permCode){
		this.permCode =permCode;
	}
	public String getPermCode(){
		return this.permCode;
	}
	
	public void setPermType(Integer permType){
		this.permType =permType;
	}
	public Integer getPermType(){
		return this.permType;
	}
	
	public String getOpenType() {
		return openType;
	}
	public void setOpenType(String openType) {
		this.openType = openType;
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public void setParentId(Integer parentId){
		this.parentId =parentId;
	}
	public Integer getParentId(){
		return this.parentId;
	}
	
	public void setUrl(String url){
		this.url =url;
	}
	public String getUrl(){
		return this.url;
	}
	
	public void setPermUrl(String permUrl){
		this.permUrl =permUrl;
	}
	public String getPermUrl(){
		return this.permUrl;
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
