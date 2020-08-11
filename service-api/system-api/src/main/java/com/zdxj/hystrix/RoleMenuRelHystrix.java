package com.zdxj.hystrix;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.zdxj.core.Result;
import com.zdxj.core.hystrix.BaseHystrix;
import com.zdxj.feignclient.RoleMenuRelFeignClient;
import com.zdxj.model.RoleMenuRelEntity;

@Component
public class RoleMenuRelHystrix extends BaseHystrix<RoleMenuRelEntity> implements RoleMenuRelFeignClient {

	/**
	 * 初始化角色和权限的对应关系
	 * @author zhaodx
	 * @date 2020-07-20 18:13
	 * @return
	 */
	@Override
	public Result<Object> initRoleMenuRel() {
		return Result.failed();
	}

	/**
	 * 根据角色id获取所有权限编码
	 * @author zhaodx
	 * @date 2020-07-30 13:26
	 * @param roleList
	 * @return
	 */
	@Override
	public Result<Set<String>> listUserPermCodesByRoleIds(List<Integer> roleList) {
		return Result.failed();
	}

}
