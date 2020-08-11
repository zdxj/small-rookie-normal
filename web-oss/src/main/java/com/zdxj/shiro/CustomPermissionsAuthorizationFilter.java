package com.zdxj.shiro;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import com.zdxj.constant.GlobalConstants;
import com.zdxj.utils.ShiroUtils;


public class CustomPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {

	@Override
	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {

		Subject subject = getSubject(request, response);
		//判断是否有超级管理员权限
		boolean hasRole = subject.hasRole("ROLE_"+GlobalConstants.SUPER_ROLE_ID);
		if(hasRole) {
			return true ;
		}
		String[] perms = (String[]) mappedValue;

		boolean isPermitted = true;
		if ((perms != null) && (perms.length > 0)) {
			if (perms.length == 1) {
				if (!this.isOneOfPermitted(perms[0], subject)) {
					isPermitted = false;
				}
			} else if (!subject.isPermittedAll(perms)) {
				isPermitted = false;
			}
		}

		return isPermitted;
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		 Subject subject = getSubject(request, response);
        // If the subject isn't identified, redirect to login URL
        if (subject.getPrincipal() == null) {
        	if(ShiroUtils.isAjax(request)) {
        		Map<String,Object> resultMap = new HashMap<String,Object>();
        		resultMap.put("code", GlobalConstants.NOT_LOGIN);
        		resultMap.put("msg", "您还没有登录，请先登录");
        		ShiroUtils.out(response, resultMap);
        	}else {
        		saveRequestAndRedirectToLogin(request, response);
        	}
        } else {
        	//是否为ajax请求
        	if(ShiroUtils.isAjax(request)) {
        		Map<String,Object> resultMap = new HashMap<String,Object>();
        		resultMap.put("code", GlobalConstants.ERROR_403);
        		resultMap.put("msg", "您没有权限进行此操作");
        		ShiroUtils.out(response, resultMap);
        	}else {
        		// If subject is known but not authorized, redirect to the unauthorized URL if there is one
                // If no unauthorized URL is specified, just return an unauthorized HTTP status code
                String unauthorizedUrl = getUnauthorizedUrl();
                //SHIRO-142 - ensure that redirect _or_ error code occurs - both cannot happen due to response commit:
                if (StringUtils.hasText(unauthorizedUrl)) {
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);
                } else {
                    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                }
        	}
        }
        return false;
	}
	
	/**
	 * 一个url对应多个code时，有一个其中的一个权限即可
	 * @author zhaodx
	 * @date 2020-03-20 18:44:40
	 * @param permStr
	 * @param subject
	 * @return
	 */
	private boolean isOneOfPermitted(String permStr, Subject subject) {
        boolean isPermitted = false;
        String[] permArr = permStr.split("\\|");
        if (permArr.length > 0) {
            for (int index = 0, len = permArr.length; index < len; index++) {
                if (subject.isPermitted(permArr[index])) {
                    isPermitted = true;
                }
            }
        }
        return isPermitted;
    }
}
