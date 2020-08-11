package com.zdxj.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zdxj.core.Result;
import com.zdxj.core.feignclient.BaseFeignClient;
import com.zdxj.hystrix.SystemRoleHystrix;
import com.zdxj.model.SystemRoleEntity;

@FeignClient(value = "gateway-server",path="/systemApi/systemRole", fallback = SystemRoleHystrix.class)
public interface SystemRoleFeignClient extends BaseFeignClient<SystemRoleEntity>{

	/**
	 * 更新角色
	 * @author zhaodx
	 * @date 2020-08-04 13:21
	 * @param entity
	 * @return
	 */
	@PostMapping("updateRole")
    Result<Object> updateRole(@RequestBody SystemRoleEntity entity);
}