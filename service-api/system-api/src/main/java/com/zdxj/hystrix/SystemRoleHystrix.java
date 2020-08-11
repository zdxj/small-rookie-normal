package com.zdxj.hystrix;

import org.springframework.stereotype.Component;

import com.zdxj.core.Result;
import com.zdxj.core.hystrix.BaseHystrix;
import com.zdxj.feignclient.SystemRoleFeignClient;
import com.zdxj.model.SystemRoleEntity;

@Component
public class SystemRoleHystrix extends BaseHystrix<SystemRoleEntity> implements SystemRoleFeignClient {

	/**
	 * 更新角色
	 * @author zhaodx
	 * @date 2020-08-04 13:21
	 * @param entity
	 * @return
	 */
	@Override
	public Result<Object> updateRole(SystemRoleEntity entity) {
		return Result.failed();
	}

}
