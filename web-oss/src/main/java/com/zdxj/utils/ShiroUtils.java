package com.zdxj.utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.shiro.MyShiroRealm;

public class ShiroUtils {

	public static Logger logger = LoggerFactory.getLogger(ShiroUtils.class);
	
	/**
	 * 判断是否有权限
	 * @author:zhaodx	
	 * @date:2018-11-01 15:57	
	 * @param name
	 * @return
	 */
	public static boolean hasPermission(String name) {
		boolean flag = false ;
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser != null && (currentUser.hasRole("ROLE_"+GlobalConstants.SUPER_ROLE_ID) || currentUser.isPermitted(name))) {
			flag = true ;
		}
		return flag ;
	}
	
	/**
	 * 清除shiro权限
	 * @author:zhaodx	
	 * @date:2018-11-02 09:41
	 */
	public static void clearShiroCache() {
		try {
			//添加成功之后 清除缓存
	        DefaultWebSecurityManager securityManager = (DefaultWebSecurityManager)SecurityUtils.getSecurityManager();
	        MyShiroRealm shiroRealm = (MyShiroRealm) securityManager.getRealms().iterator().next();
	        //清除权限 相关的缓存
	        shiroRealm.clearAllCache();
		}catch(Exception e) {
			logger.error("清除shiro权限失败",e);
		}
	}

	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	/**
	 * response 输出JSON
	 * @param hresponse
	 * @param resultMap
	 * @throws IOException
	 */
	public static void out(ServletResponse response, Map<String, Object> resultMap){
		
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			out.println(mapper.writeValueAsString(resultMap));
		} catch (Exception e) {
			logger.error("输出JSON失败",e);
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
