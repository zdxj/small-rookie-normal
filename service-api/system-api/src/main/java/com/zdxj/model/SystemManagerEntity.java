package com.zdxj.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.zdxj.core.entity.BaseEntity;

public class SystemManagerEntity extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	
	/* 登录名 */
	private String loginName;

	/* 密码 */
	private String loginPassword;

	/* 真实姓名 */
	@NotBlank(message = "真实姓名不能为空")
	private String realName;

	/* 所属部门 */
	@NotNull(message = "所属部门不能为空")
	private Integer departmentId;

	/* 登录ip地址 */
	private String loginIp;

	/* 登录时间 */
	private Date loginTime;

	/* 登录错误时间 */
	private Date loginErrorTime;

	/* 登录错误次数 */
	private Integer loginErrorCount;

	/* 手机号码 */
	@NotBlank(message = "手机号码不能为空")
	private String mobile;

	/* 备注 */
	private String note;
	
	/* 头像 */
	private String headImage;

	/* 是否启用 */
	private Boolean enable;

	//部门名称
	private String deptName ;
	
	public void setLoginName(String loginName){
		this.loginName =loginName;
	}
	public String getLoginName(){
		return this.loginName;
	}
	
	public void setLoginPassword(String loginPassword){
		this.loginPassword =loginPassword;
	}
	public String getLoginPassword(){
		return this.loginPassword;
	}
	
	public void setRealName(String realName){
		this.realName =realName;
	}
	public String getRealName(){
		return this.realName;
	}
	
	public void setDepartmentId(Integer departmentId){
		this.departmentId =departmentId;
	}
	public Integer getDepartmentId(){
		return this.departmentId;
	}
	
	public void setLoginIp(String loginIp){
		this.loginIp =loginIp;
	}
	public String getLoginIp(){
		return this.loginIp;
	}
	
	public void setLoginTime(Date loginTime){
		this.loginTime =loginTime;
	}
	public Date getLoginTime(){
		return this.loginTime;
	}
	
	public void setLoginErrorTime(Date loginErrorTime){
		this.loginErrorTime =loginErrorTime;
	}
	public Date getLoginErrorTime(){
		return this.loginErrorTime;
	}
	
	public void setLoginErrorCount(Integer loginErrorCount){
		this.loginErrorCount =loginErrorCount;
	}
	public Integer getLoginErrorCount(){
		return this.loginErrorCount;
	}
	
	public void setMobile(String mobile){
		this.mobile =mobile;
	}
	public String getMobile(){
		return this.mobile;
	}
	
	public void setNote(String note){
		this.note =note;
	}
	public String getNote(){
		return this.note;
	}
	
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public Boolean getEnable() {
		return enable;
	}
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
}
