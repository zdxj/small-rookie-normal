package com.zdxj.shiro;

import com.jagregory.shiro.freemarker.AuthenticatedTag;
import com.jagregory.shiro.freemarker.GuestTag;
import com.jagregory.shiro.freemarker.HasAnyRolesTag;
import com.jagregory.shiro.freemarker.HasRoleTag;
import com.jagregory.shiro.freemarker.LacksPermissionTag;
import com.jagregory.shiro.freemarker.LacksRoleTag;
import com.jagregory.shiro.freemarker.NotAuthenticatedTag;
import com.jagregory.shiro.freemarker.PrincipalTag;
import com.jagregory.shiro.freemarker.UserTag;

import freemarker.template.SimpleHash;

/**
 * 自定义freemark校验权限标签
 * 
 * 备注：这里只修改hasPermission的校验方法
 * @author:zhaodx	
 * @date:2018-11-02 13:55
 */
public class CustomShiroTags extends SimpleHash{

	private static final long serialVersionUID = 4333464177616805315L;

	@SuppressWarnings("deprecation")
	public CustomShiroTags() {
        put("authenticated", new AuthenticatedTag());
        put("guest", new GuestTag());
        put("hasAnyRoles", new HasAnyRolesTag());
        put("hasPermission", new CustomHasPermissionTag());
        put("hasRole", new HasRoleTag());
        put("lacksPermission", new LacksPermissionTag());
        put("lacksRole", new LacksRoleTag());
        put("notAuthenticated", new NotAuthenticatedTag());
        put("principal", new PrincipalTag());
        put("user", new UserTag());
    }
}
