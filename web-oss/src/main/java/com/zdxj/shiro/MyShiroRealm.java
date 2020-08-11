package com.zdxj.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.zdxj.core.Result;
import com.zdxj.feignclient.ManagerRoleRelFeignClient;
import com.zdxj.feignclient.RoleMenuRelFeignClient;
import com.zdxj.feignclient.SystemManagerFeignClient;
import com.zdxj.model.SystemManagerEntity;




/**
 * shiro的认证最终是交给了Realm进行执行 所以我们需要自己重新实现一个Realm，此Realm继承AuthorizingRealm
 */
public class MyShiroRealm extends AuthorizingRealm {

	@Autowired
	private SystemManagerFeignClient systemManagerClient;
	@Autowired
	private ManagerRoleRelFeignClient managerRoleRelFeignClient;
	@Autowired
	private RoleMenuRelFeignClient roleMenuRelFeignClient;

	/**
	 * 登录认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// UsernamePasswordToken用于存放提交的登录信息
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		Result<SystemManagerEntity> userResult = systemManagerClient.getByLoginName(token.getUsername());
		SystemManagerEntity user = userResult.getResult();
		if (user != null) {
			if(!user.getEnable()){ 
				throw new DisabledAccountException(); 
			}
			//加密盐
			ByteSource credentialsSalt = ByteSource.Util.bytes(user.getLoginName());
			// 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
			return new SimpleAuthenticationInfo(user.getLoginName(), user.getLoginPassword(), credentialsSalt,getName());
		}
		return null;
	}

	/**
	 * 权限认证（为当前登录的Subject授予角色和权限）
	 * 
	 * 该方法的调用时机为需授权资源被访问时，并且每次访问需授权资源都会执行该方法中的逻辑，这表明本例中并未启用AuthorizationCache，
	 * 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），
	 * 超过这个时间间隔再刷新页面，该方法会被执行
	 * 
	 * doGetAuthorizationInfo()是权限控制， 当访问到页面的时候，使用了相应的注解或者shiro标签才会执行此方法否则不会执行，
	 * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String loginName = (String) super.getAvailablePrincipal(principals);
		Result<SystemManagerEntity> userResult = systemManagerClient.getByLoginName(loginName);
		SystemManagerEntity user = userResult.getResult();
		if (user != null) {
			// 权限信息对象info，用来存放查出的用户的所有的角色及权限
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			// 用户的角色集合
			Result<Set<Integer>> roleIdsResult = managerRoleRelFeignClient.listRoleIdsByManagerId(user.getId());
			List<Integer> roles = null;
			if (CollectionUtils.isNotEmpty(roleIdsResult.getResult())) {
				roles = new ArrayList<Integer>(roleIdsResult.getResult());
				Set<String> roleCodes = roleIdsResult.getResult().stream().map(w -> "ROLE_"+w).collect(Collectors.toSet());
				info.setRoles(roleCodes);
			}
			// 获取用户所有权限
			if(CollectionUtils.isNotEmpty(roles)) {
				Result<Set<String>> permCodeResult = roleMenuRelFeignClient.listUserPermCodesByRoleIds(roles);
				if (CollectionUtils.isNotEmpty(permCodeResult.getResult())) {
					info.addStringPermissions(permCodeResult.getResult());
				}
			}
			return info;
		}
		// 返回null将会导致用户访问任何被拦截的请求时都会自动跳转到unauthorizedUrl指定的地址
		return null;
	}
	
	/**
	 * 重写方法,清除当前用户的的 授权缓存
	 * @param principals
	 */
	@Override
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
	    super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 重写方法，清除当前用户的 认证缓存
	 * @param principals
	 */
	@Override
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
	    super.clearCachedAuthenticationInfo(principals);
	}

	@Override
	public void clearCache(PrincipalCollection principals) {
	    super.clearCache(principals);
	}

	/**
	 * 自定义方法：清除所有 授权缓存
	 */
	public void clearAllCachedAuthorizationInfo() {
	    Cache<Object, AuthorizationInfo> authorizationCache2 = getAuthorizationCache();
	    if(authorizationCache2 != null) {
	    	authorizationCache2.clear();
	    }
	}

	/**
	 * 自定义方法：清除所有 认证缓存
	 */
	public void clearAllCachedAuthenticationInfo() {
	    Cache<Object, AuthenticationInfo> authenticationCache2 = getAuthenticationCache();
	    if(authenticationCache2 != null) {
	    	authenticationCache2.clear();
	    }
	}

	/**
	 * 自定义方法：清除所有的  认证缓存  和 授权缓存
	 */
	public void clearAllCache() {
	    clearAllCachedAuthenticationInfo();
	    clearAllCachedAuthorizationInfo();
	}
}