package com.zdxj.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jagregory.shiro.freemarker.HasPermissionTag;
import com.zdxj.constant.GlobalConstants;


/**
 * 重写freemark校验权限方法，加入角色判断
 * @author:zhaodx	
 * @date:2018-11-02 13:54
 */
public class CustomHasPermissionTag extends  HasPermissionTag{

	@Override
	protected boolean showTagBody(String p) {
		Subject currentUser = SecurityUtils.getSubject();
		if(currentUser != null && currentUser.hasRole("ROLE_"+GlobalConstants.SUPER_ROLE_ID)) {
			return true;
		}
		for(String perm :p.split(",")) {
			if(super.showTagBody(perm)) {
				return true ;
			}
		}
		return false;
	}
	
}
