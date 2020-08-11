package com.zdxj.hystrix;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.zdxj.feignclient.AuthorizeFeignClient;


@Component
public class AuthorizeHystrix implements AuthorizeFeignClient{

	/**
	 * 后端管理员用户名密码获取token
	 * @author zhaodx
	 * @date 2020-07-24 18:12
	 * @param maps
	 * @return
	 */
	@Override
	public Map<String,Object> token(Map<String, String> maps) {
		return null;
	}

}
