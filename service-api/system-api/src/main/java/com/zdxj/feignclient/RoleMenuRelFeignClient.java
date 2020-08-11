package com.zdxj.feignclient;

import java.util.List;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zdxj.core.Result;
import com.zdxj.core.feignclient.BaseFeignClient;
import com.zdxj.hystrix.RoleMenuRelHystrix;
import com.zdxj.model.RoleMenuRelEntity;

@FeignClient(value = "gateway-server",path="/systemApi/roleMenuRel", fallback = RoleMenuRelHystrix.class)
public interface RoleMenuRelFeignClient extends BaseFeignClient<RoleMenuRelEntity>{

	/**
	 * 初始化角色和权限的对应关系
	 * @author zhaodx
	 * @date 2020-07-20 18:13
	 * @return
	 */
	@GetMapping("initRoleMenuRel")
    Result<Object> initRoleMenuRel();
	
	/**
	 * 根据角色id获取所有权限编码
	 * @author zhaodx
	 * @date 2020-07-30 13:26
	 * @param roleList
	 * @return
	 */
	@PostMapping("listUserPermCodesByRoleIds")
	Result<Set<String>> listUserPermCodesByRoleIds(@RequestBody List<Integer> roleList);
}