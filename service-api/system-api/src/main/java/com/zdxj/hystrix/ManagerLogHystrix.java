package com.zdxj.hystrix;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.zdxj.core.Result;
import com.zdxj.core.hystrix.BaseHystrix;
import com.zdxj.feignclient.ManagerLogFeignClient;
import com.zdxj.model.ManagerLogEntity;

@Component
public class ManagerLogHystrix extends BaseHystrix<ManagerLogEntity> implements ManagerLogFeignClient {

	/**
	 * 登录错误
	 * @author zhaodx
	 * @date 2020-07-30 14:26
	 * @param maps
	 * @return
	 */
	@Override
	public Result<Object> loginError(Map<String, String> maps) {
		return Result.failed();
	}

}
