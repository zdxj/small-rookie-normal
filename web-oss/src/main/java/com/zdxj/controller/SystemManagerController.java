package com.zdxj.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseController;
import com.zdxj.feignclient.DepartmentFeignClient;
import com.zdxj.feignclient.SystemManagerFeignClient;
import com.zdxj.model.DepartmentEntity;
import com.zdxj.model.SystemConfigEntity;
import com.zdxj.model.SystemManagerEntity;
import com.zdxj.properties.AliyunImgProps;
import com.zdxj.util.ToolUtils;
import com.zdxj.utils.CommonService;

@Controller
@RequestMapping("/systemManager")
public class SystemManagerController extends BaseController<SystemManagerFeignClient,SystemManagerEntity>{
	
	public SystemManagerController() {
		super("/systemManager/", "SystemManager");
	}
	
	@Autowired
	private AliyunImgProps aliyunImgProps;
	@Autowired
	private CommonService commonService ;
	@Autowired
	private SystemManagerFeignClient myClient ;
	@Autowired
	private DepartmentFeignClient departmentFeignClient ;
	
	
	@Override
	public SystemManagerEntity initAddAndEdit(ModelAndView model, SystemManagerEntity entity) {
		
		boolean isAdd = true ;
		if(entity != null && entity.getId() != null) {
			isAdd = false ;
			entity.setLoginPassword(null);
			DepartmentEntity department = this.getDepartmentById(entity.getDepartmentId());
			entity.setDeptName(department==null?"":department.getName());
		}
		model.addObject("isAdd", isAdd);
		return entity;
	}
	
	@Override
	public String checkData(SystemManagerEntity entity, HttpServletRequest request) {
		
		if(entity.getId() == null) {
			if(StringUtils.isBlank(entity.getLoginName())) {
				return "请输入用户名";
			}
			if(this.checkLoginNameExist(entity.getLoginName())) {
				return "用户名已存在";
			}
			if(StringUtils.isBlank(entity.getLoginPassword())) {
				return "请输入密码";
			}
			String password =this.shiroPassword(entity.getLoginName(),entity.getLoginPassword());
			entity.setLoginPassword(password);
		}
		if(entity.getId() != null && GlobalConstants.SUPER_MANAGER_ID.equals(entity.getId())) {
			if(entity.getEnable() == null || !entity.getEnable()) {
				return "系统默认管理员必须为启用状态";
			}
		}
		entity.setEnable(entity.getEnable()==null?false:entity.getEnable());
		return null;
	}
	
	@Override
	public List<?> handleListResult(List<SystemManagerEntity> records) {
		if(CollectionUtils.isEmpty(records)) {
			return records ;
		}
		for(SystemManagerEntity info:records) {
			DepartmentEntity department = this.getDepartmentById(info.getDepartmentId());
			info.setDeptName(department==null?"":department.getName());
		}
		return records ;
	}
	
	@Override
	public String checkDel(List<Integer> ids) {
		for(Integer id:ids) {
			if(GlobalConstants.SUPER_MANAGER_ID.equals(id)) {
				return "系统管理员无法删除";
			}
		}
		return null;
	}
	
	@Override
	public SystemManagerEntity handleView(SystemManagerEntity entity, ModelAndView model) {
		if(entity != null) {
			DepartmentEntity department = this.getDepartmentById(entity.getDepartmentId());
			model.addObject("deptName", department==null?"":department.getName());
		}
		return entity;
	}
	
	/**
	 * 重置密码（列表处）
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/resetPassword")
	public ModelAndView resetPassword(@RequestParam("id") Integer id ,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("id",id);
		Result<SystemManagerEntity> byId = myClient.getById(id);
		SystemManagerEntity user = byId.getResult();
		model.addObject("loginName", user==null?"":user.getLoginName());
		model.setViewName("/systemManager/ResetPassword");
		return model;
	}
	
	/**
	 * 重置管理员密码
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("doUpdateEnable")
	public @ResponseBody Result<Object> doUpdateEnable(@RequestParam("id") Integer id ,@RequestParam("enable") Boolean enable ,HttpServletRequest request, HttpServletResponse response) {
		if(id != null && GlobalConstants.SUPER_MANAGER_ID.equals(id)) {
			if(enable == null || !enable) {
				return Result.failed("系统默认管理员必须为启用状态");
			}
		}
		SystemManagerEntity updateEntity = new SystemManagerEntity();
		updateEntity.setId(id);
		updateEntity.setEnable(enable);
		return myClient.updateEntity(updateEntity);
	}
	
	/**
	 * 重置管理员密码
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("doResetPassword")
	public @ResponseBody Result<Object> doResetPassword(@RequestParam("id") Integer id ,HttpServletRequest request, HttpServletResponse response) {
		
		//新密码
		String password =request.getParameter("password");
		//再次确认
		String passwordConfirm =request.getParameter("passwordConfirm");
		if(StringUtils.isBlank(password)) {
			return new Result<Object>(GlobalConstants.FAILURE, "请输入密码", null);
		}
		if(!password.equals(passwordConfirm)) {
			return new Result<Object>(GlobalConstants.FAILURE, "两次密码不一致", null);
		}
		Result<SystemManagerEntity> byId = myClient.getById(id);
		SystemManagerEntity result = byId.getResult();
		if(GlobalConstants.FAILURE.equals(byId.getCode()) || null== result) {
			return new Result<Object>(GlobalConstants.FAILURE, "管理员不存在", null);
		}
		String name = result.getLoginName();
		String loginPassword =shiroPassword(name, password);
		SystemManagerEntity updateEntity = new SystemManagerEntity();
		updateEntity.setId(id);
		updateEntity.setLoginPassword(loginPassword);
		Result<Object> editPassword = myClient.updateEntity(updateEntity);
		return editPassword;
	}
	
	/**
	 * 个人中心
	 * @author zhaodx
	 * @date 2020-08-05 13:48
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/profile")
	public ModelAndView profile(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		SystemManagerEntity user = commonService.getLoginUser();
		DepartmentEntity dept = this.getDepartmentById(user.getDepartmentId());
		user.setDeptName(dept==null?"":dept.getName());
		user.setLoginPassword(null);
		model.addObject("user",user);
		model.setViewName("/systemManager/SystemManagerProfile");
		return model;
	}
	
	/**
	 * 修改管理员信息
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("doUpdateProfile")
	public @ResponseBody Result<Object> doUpdateProfile(@RequestParam("realName") String realName ,@RequestParam("mobile") String mobile ,HttpServletRequest request, HttpServletResponse response) {
		
		if(StringUtils.isBlank(realName)) {
			return Result.failed("请输入真实姓名");
		}
		if(StringUtils.isBlank(mobile)) {
			return Result.failed("请输入手机号");
		}
		SystemManagerEntity updateEntity = new SystemManagerEntity();
		updateEntity.setId(commonService.getLoginUser().getId());
		updateEntity.setRealName(realName);
		updateEntity.setMobile(mobile);
		Result<Object> updateEntity2 = myClient.updateEntity(updateEntity);
		if(GlobalConstants.SUCCESS.equals(updateEntity2.getCode())) {
			Result<SystemManagerEntity> byId = myClient.getById(updateEntity.getId());
			SystemManagerEntity user = byId.getResult();
			Session shiroSession = SecurityUtils.getSubject().getSession();
			shiroSession.setAttribute(GlobalConstants.SESSION_MANAGER_KEY, user);
		}
		return updateEntity2;
	}
	
	/**
	 * 跳转至修改头像页面
	 * @author zhaodx
	 * @date 2020-08-06 18:07
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/headImage")
	public ModelAndView headImage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		SystemManagerEntity loginUser = commonService.getLoginUser();
		loginUser.setLoginPassword(null);
		model.addObject("user",loginUser);
		SystemConfigEntity systemConfig = commonService.getSystemConfig();
		model.addObject("fileServer", systemConfig.getFileServer());
		model.setViewName("/systemManager/HeadImage");
		return model;
	}
	
	/**
	 * 保存头像
	 * @author zhaodx
	 * @date 2020-08-06 18:08
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("saveHeadImage")
	public @ResponseBody Result<Object> saveHeadImage(HttpServletRequest request, HttpServletResponse response) {
		
		String headImage = ToolUtils.checkStr(request.getParameter("headImage"));
		if(StringUtils.isBlank(headImage)) {
			return Result.failed("请上传头像");
		}
		SystemManagerEntity updateEntity = new SystemManagerEntity();
		updateEntity.setHeadImage(headImage);
		updateEntity.setId(commonService.getLoginUser().getId());
		Result<Object> editPassword = myClient.updateEntity(updateEntity);
		if(GlobalConstants.SUCCESS.equals(editPassword.getCode())) {
			Result<SystemManagerEntity> byId = myClient.getById(updateEntity.getId());
			SystemManagerEntity result = byId.getResult();
			result.setLoginPassword(null);
			result.setHeadImage(StringUtils.isBlank(result.getHeadImage())?null:aliyunImgProps.getBucketUrl()+result.getHeadImage());
			Session shiroSession = SecurityUtils.getSubject().getSession();
			shiroSession.setAttribute(GlobalConstants.SESSION_MANAGER_KEY, result);
		}
		return editPassword ;
	}
	
	/**
	 * 管理员修改密码
	 * @author zhaodx
	 * @date 2020-08-05 14:09
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("updatePwd")
	public @ResponseBody Result<Object> updatePwd(HttpServletRequest request, HttpServletResponse response) {
		
		//旧密码
		String oldPassword =request.getParameter("oldPassword");
		//新密码
		String password =request.getParameter("newPassword");
		//再次确认
		String passwordConfirm =request.getParameter("confirmPassword");
		if(StringUtils.isBlank(oldPassword)) {
			return Result.failed("请输入旧密码");
		}
		if(StringUtils.isBlank(password)) {
			return Result.failed("请输入新密码");
		}
		if(!password.equals(passwordConfirm)) {
			return Result.failed("两次密码不一致");
		}
		Result<SystemManagerEntity> byId = myClient.getById(commonService.getLoginUser().getId());
		SystemManagerEntity result = byId.getResult();
		if(GlobalConstants.FAILURE.equals(byId.getCode()) || null== result) {
			return Result.failed("管理员不存在");
		}
		//验证旧密码
		if(!result.getLoginPassword().equals(this.shiroPassword(result.getLoginName(), oldPassword))) {
			return Result.failed("旧密码错误");
		}
		String name = result.getLoginName();
		String loginPassword =this.shiroPassword(name, password);
		SystemManagerEntity updateEntity = new SystemManagerEntity();
		updateEntity.setId(result.getId());
		updateEntity.setLoginPassword(loginPassword);
		Result<Object> editPassword = myClient.updateEntity(updateEntity);
		if(GlobalConstants.SUCCESS.equals(editPassword.getCode())) {
			SecurityUtils.getSubject().logout();
		}
		return editPassword;
	}
	
	/**
	 * shiro密码加密
	 * @author zhaodx
	 * @date 2018年12月4日
	 * @param @param salt
	 * @param @param password
	 * @param @return
	 * @return String
	 */
	private String shiroPassword(String salt,String password) {
		ByteSource salts = ByteSource.Util.bytes(salt);
		String newPs = new SimpleHash("MD5", password, salts, 50).toHex();
		return newPs;
	}
	
	/**
     * 校验用户名是否存在
     * @param loginName
     * @return
     */
    private boolean checkLoginNameExist(String loginName) {
    	Map<String,Object> maps = Maps.newHashMap();
    	maps.put("loginName", loginName);
    	Result<Object> countByCondition = myClient.countByCondition(maps);
    	if(GlobalConstants.FAILURE.equals(countByCondition.getCode())) {
    		return true ;
    	}
    	return (int)countByCondition.getResult()>0?true:false ;
    }
    
    /**
     * 获取部门
     * @author zhaodx
     * @date 2020-08-04 17:00
     * @param id
     * @return
     */
    private DepartmentEntity getDepartmentById(Integer id) {
    	if(id == null || id==0) {
    		return null ;
    	}
    	Result<DepartmentEntity> byId = departmentFeignClient.getById(id);
    	return byId.getResult();
    }
}