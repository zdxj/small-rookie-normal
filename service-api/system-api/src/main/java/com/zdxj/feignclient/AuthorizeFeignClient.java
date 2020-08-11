package com.zdxj.feignclient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zdxj.hystrix.AuthorizeHystrix;

@FeignClient(value = "gateway-server",path="/authApi/oauth", fallback = AuthorizeHystrix.class)
public interface AuthorizeFeignClient {

	/**
	 * 后端管理员用户名密码获取token
	 * @author zhaodx
	 * @date 2020-07-24 18:12
	 * @param maps
	 * @return
	 */
	@PostMapping("token")
	Map<String,Object> token(@RequestParam Map<String,String> maps);
}
