package com.zdxj.feignclient;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zdxj.core.Result;
import com.zdxj.core.feignclient.BaseFeignClient;
import com.zdxj.hystrix.ManagerRoleRelHystrix;
import com.zdxj.model.ManagerRoleRelEntity;

@FeignClient(value = "gateway-server",path="/systemApi/managerRoleRel", fallback = ManagerRoleRelHystrix.class)
public interface ManagerRoleRelFeignClient extends BaseFeignClient<ManagerRoleRelEntity>{

	/**
	 * 获取管理员角色ID
	 * @author zhaodx
	 * @date 2020-07-17 17:43
	 * @param id
	 * @return
	 */
	@GetMapping("listRoleIdByManagerId")
    Result<List<Integer>> listRoleIdByManagerId(@RequestParam("id") Integer managerId);
	
	/**
	 * 根据管理员ID获取管理员角色ID
	 * @author zhaodx
	 * @date 2018年12月4日
	 * @param @param id
	 * @param @return
	 * @return ResultInfo<Set<Integer>>
	 */
	@GetMapping("listRoleIdsByManagerId")
	Result<Set<Integer>> listRoleIdsByManagerId(@RequestParam("id") Integer id);
	
	/**
	 * 保存管理员对应角色关系
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("saveSelectRole")
	Result<Object> saveSelectRole(@RequestBody Map<String, Object> maps);
}