package com.zdxj.hystrix;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.zdxj.core.Result;
import com.zdxj.core.hystrix.BaseHystrix;
import com.zdxj.feignclient.SystemManagerFeignClient;
import com.zdxj.model.SystemManagerEntity;

@Component
public class SystemManagerHystrix extends BaseHystrix<SystemManagerEntity> implements SystemManagerFeignClient {

	/**
	 * 根据用户名获取管理员
	 * @author zhaodx
	 * @date 2020-07-16 18:42
	 * @param loginName
	 * @return
	 */
	@Override
	public Result<SystemManagerEntity> getByLoginName(String loginName) {
		return Result.failed();
	}

	/**
	 * 登录
	 * @author zhaodx
	 * @date 2020-07-30 14:20
	 * @param maps
	 * @return
	 */
	@Override
	public Result<SystemManagerEntity> login(Map<String, String> maps) {
		return Result.failed();
	}

}
