package com.zdxj.controller;

import java.text.MessageFormat;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.NamedFilterList;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.core.Result;
import com.zdxj.feignclient.ManagerLogFeignClient;
import com.zdxj.feignclient.SystemManagerFeignClient;
import com.zdxj.feignclient.SystemMenuFeignClient;
import com.zdxj.model.ManagerLogEntity;
import com.zdxj.model.SystemManagerEntity;
import com.zdxj.properties.AliyunImgProps;
import com.zdxj.shiro.DefaultPermissionEnum;
import com.zdxj.util.ToolUtils;
import com.zdxj.utils.CommonService;

@Controller
@RequestMapping("/login")
public class LoginController {

	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";
	
	@Autowired
	private AliyunImgProps aliyunImgProps;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ShiroFilterFactoryBean myShiroFilterFactoryBean ;
	@Autowired
	private SystemManagerFeignClient systemManagerFeignClient;
	@Autowired
	private ManagerLogFeignClient managerLogFeignClient;
	@Autowired
	private SystemMenuFeignClient menuFeignClient ;
	
	/**
	 * 跳转至登录页面
	 * @return
	 */
	@RequestMapping("")
	public ModelAndView login(HttpServletRequest request) {
		ModelAndView view = new ModelAndView();
		try {
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length>0) {
				Cookie cookie = request.getCookies()[0];//获取cookie
				cookie.setMaxAge(0);//让cookie过期
			}
			SecurityUtils.getSubject().logout();
			
		}catch(Exception e) {
			logger.error("登录清空cookie失败",e);
		}
		view.setViewName("login");
		return view;
	}
	
	/**
	 * 管理员登录
	 * @author zhaodx
	 * @date 2020-07-29 10:11
	 * @param request
	 * @return
	 */
	@RequestMapping("/doLogin")
	public @ResponseBody Result<String> doLogin(HttpServletRequest request) {
		
		String verifycode = ToolUtils.checkStr(request.getParameter("verifycode"));
		if (!commonService.checkVerifyCode(verifycode)) {
			SecurityUtils.getSubject().logout();
			return Result.failed("验证码错误");
		}
		SecurityUtils.getSubject().logout();
		try {
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length>0) {
				Cookie cookie = request.getCookies()[0];//获取cookie
				cookie.setMaxAge(0);//让cookie过期
			}
		}catch(Exception e) {
			logger.error("登录清空cookie失败",e);
		}
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Map<String, String> maps = Maps.newHashMap();
		maps.put("loginName", username);
		maps.put("password", password);
		maps.put("loginIp", commonService.getIpAddress(request));
		maps.put("actionName", "登录");
		 //验证  
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);  
        //获取当前的Subject  
        Subject currentUser = SecurityUtils.getSubject();
        try {  
            currentUser.login(token);
        }catch(UnknownAccountException uae){
        	//未知账户
        	maps.put("description", "用户名或密码错误");
        	this.loginError(maps);
            return Result.failed("用户名或密码错误");
        }catch(IncorrectCredentialsException ice){
        	//密码不正确
        	maps.put("description", "用户名或密码错误");
        	this.loginError(maps);
        	return Result.failed("用户名或密码错误");
        }catch(LockedAccountException lae){
        	maps.put("description", "账户已锁定");
        	this.loginError(maps);
        	return Result.failed("账户已锁定");
        }catch(ExcessiveAttemptsException eae){
        	maps.put("description", "用户名或密码错误次数大于"+GlobalConstants.MAX_LOGIN_ERROR_COUNT+"次,账户已锁定");
        	if("true".equals(eae.getMessage())) {
        		maps.put("updateLoginErrorTime", "true");
        	}
        	this.loginError(maps);
        	return Result.failed(eae.getCause().getMessage());
        }catch (DisabledAccountException sae){  
        	maps.put("description", "帐号已经禁止登录");
        	this.loginError(maps);
        	return Result.failed("帐号已经禁止登录");
        }catch(AuthenticationException ae){  
            //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景  
        	maps.put("description", "用户名或密码错误");
        	this.loginError(maps);
        	return Result.failed("用户名或密码错误");
        }  
        
        Result<SystemManagerEntity> resultInfo = systemManagerFeignClient.login(maps);
		if (GlobalConstants.FAILURE.equals(resultInfo.getCode())) {
			SecurityUtils.getSubject().logout();
			return Result.failed(resultInfo.getMsg());
		}
		//检测权限是否初始化
		this.checkPrems(false);
		Session shiroSession = SecurityUtils.getSubject().getSession();
		SystemManagerEntity result = resultInfo.getResult();
		result.setLoginPassword(null);
		result.setHeadImage(StringUtils.isBlank(result.getHeadImage())?null:aliyunImgProps.getBucketUrl()+result.getHeadImage());
		shiroSession.setAttribute(GlobalConstants.SESSION_MANAGER_KEY, result);
		return Result.success();
	}
	
	/**
	 * 系统管理员退出
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/outdo")
	public @ResponseBody Result<String> outDo(HttpSession session, HttpServletRequest request) {
		try {
			Subject currentUser = SecurityUtils.getSubject();
			SystemManagerEntity userInfo = (SystemManagerEntity)currentUser.getSession().getAttribute(GlobalConstants.SESSION_MANAGER_KEY);
			if (userInfo != null) {
				ManagerLogEntity log = new ManagerLogEntity();
				log.setLoginName(userInfo.getLoginName());
				log.setLoginIp(commonService.getIpAddress(request));
				log.setActionName("退出");
				log.setDescription("管理员退出成功");
				managerLogFeignClient.saveEntity(log);
			}
			currentUser.logout();
		} catch (Exception e) {
			logger.error("has error", e);
		}
		return Result.success();
	}
	
	/**
	 * 初始化权限
	 * @author:zhaodx	
	 * @date:2018-10-30 17:46
	 */
	private void checkPrems(boolean isInit) {
		AbstractShiroFilter shiroFilter = null;
        try {  
            shiroFilter = (AbstractShiroFilter) myShiroFilterFactoryBean.getObject();

            PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            // 过滤管理器  
            DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
            Map<String, NamedFilterList> filterChains = manager.getFilterChains();
            //如何只是默认的权限，则需要从数据库读取
            if(filterChains== null || isInit || filterChains.size()<=DefaultPermissionEnum.values().length) {
            	// 清除权限配置  
                manager.getFilterChains().clear();
                myShiroFilterFactoryBean.getFilterChainDefinitionMap().clear();  
                // 重新设置权限  
                Map<String, String> chains = myShiroFilterFactoryBean.getFilterChainDefinitionMap();  
                myShiroFilterFactoryBean.setLoginUrl("/login");
                myShiroFilterFactoryBean.setUnauthorizedUrl("/403");
                //重新生成过滤链  
                if (chains != null) {
                	Result<Map<String, String>> listAllPrem = menuFeignClient.listAllPrem();
                	Map<String, String> result = listAllPrem.getResult();
                	if(MapUtils.isNotEmpty(result)) {
                		logger.info("=======================>>>获取权限个数为:"+result.size());
                		for(Map.Entry<String, String> entry : result.entrySet()){
                		    manager.createChain(entry.getKey(), MessageFormat.format(PREMISSION_STRING, entry.getValue()));
                		}
                	}
                }
                //默认权限
                for (DefaultPermissionEnum spe : DefaultPermissionEnum.values()) {
                	manager.createChain(spe.getName(),spe.getValue());
        		}
            }
        } catch (Exception e) {  
        	logger.error("初始化权限错误",e);
        }  
	}
	
	/**
	 * 重新加载权限
	 * @param request
	 * @return
	 */
	@RequestMapping("/initPremission")
	public @ResponseBody Result<String> initPremission(HttpServletRequest request){
		this.checkPrems(true);
		return Result.success("重新加载权限完成",null);
	}
	/**
	 * 登录错误日志
	 * @param maps
	 */
	private void loginError(Map<String,String> maps) {
		SecurityUtils.getSubject().logout();
		managerLogFeignClient.loginError(maps);
	}
}
