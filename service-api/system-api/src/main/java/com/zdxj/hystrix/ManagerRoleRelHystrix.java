package com.zdxj.hystrix;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.zdxj.core.Result;
import com.zdxj.core.hystrix.BaseHystrix;
import com.zdxj.feignclient.ManagerRoleRelFeignClient;
import com.zdxj.model.ManagerRoleRelEntity;

@Component
public class ManagerRoleRelHystrix extends BaseHystrix<ManagerRoleRelEntity> implements ManagerRoleRelFeignClient {

	/**
	 * 获取管理员角色ID
	 * @author zhaodx
	 * @date 2020-07-17 17:43
	 * @param id
	 * @return
	 */
	@Override
	public Result<List<Integer>> listRoleIdByManagerId(Integer managerId) {
		return Result.failed();
	}

	/**
	 * 根据管理员ID获取管理员角色ID
	 * @author zhaodx
	 * @date 2018年12月4日
	 * @param @param id
	 * @param @return
	 * @return ResultInfo<Set<Integer>>
	 */
	@Override
	public Result<Set<Integer>> listRoleIdsByManagerId(Integer id) {
		return Result.failed();
	}

	/**
	 * 保存管理员对应角色关系
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public Result<Object> saveSelectRole(Map<String, Object> maps) {
		return Result.failed();
	}

}
