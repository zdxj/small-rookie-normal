package com.zdxj.feignclient;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zdxj.core.Result;
import com.zdxj.core.feignclient.BaseFeignClient;
import com.zdxj.hystrix.ManagerLogHystrix;
import com.zdxj.model.ManagerLogEntity;

@FeignClient(value = "gateway-server",path="/systemApi/managerLog", fallback = ManagerLogHystrix.class)
public interface ManagerLogFeignClient extends BaseFeignClient<ManagerLogEntity>{

	/**
	 * 登录错误
	 * @author zhaodx
	 * @date 2020-07-30 14:26
	 * @param maps
	 * @return
	 */
	@GetMapping("loginError")
	Result<Object> loginError(@RequestBody Map<String,String> maps);
}