package com.zdxj.shiro;

import java.util.Date;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import com.zdxj.constant.GlobalConstants;
import com.zdxj.core.Result;
import com.zdxj.feignclient.SystemManagerFeignClient;
import com.zdxj.model.SystemManagerEntity;
import com.zdxj.util.DateUtils;


public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Autowired
	private SystemManagerFeignClient systemManagerClient;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {

        //获取用户名
        String username = (String)token.getPrincipal();
        //判断用户账号和密码是否正确
        boolean matches = super.doCredentialsMatch(token, info);
        Result<SystemManagerEntity> userResult = systemManagerClient.getByLoginName(username);
		SystemManagerEntity user = userResult.getResult();
		if (user != null) {
			if(user.getLoginErrorCount().intValue()>=GlobalConstants.MAX_LOGIN_ERROR_COUNT) {
				String updateLoginErrorTime = "false" ;
				String dateDiff = DateUtils.dateDiff(new Date(),DateUtils.addMinute(user.getLoginErrorTime(), 20));
				if(!matches && user.getLoginErrorCount().intValue()>GlobalConstants.MAX_LOGIN_ERROR_COUNT && "0".equals(dateDiff)) {
					updateLoginErrorTime="true";
					Date newDate = new Date();
					dateDiff = DateUtils.dateDiff(newDate,DateUtils.addMinute(newDate, 20));
				}
				if(!"0".equals(dateDiff)) {
					throw new ExcessiveAttemptsException(updateLoginErrorTime,new Throwable("您登录错误次数过多，请"+dateDiff+"后再次登录。"));
				}
			}
		}
        return matches;
    }

}
