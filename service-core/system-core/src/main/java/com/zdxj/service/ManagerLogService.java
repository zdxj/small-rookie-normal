package com.zdxj.service;

import java.util.Map;

import com.zdxj.core.Result;
import com.zdxj.core.service.BaseService;
import com.zdxj.model.ManagerLogEntity;

public interface ManagerLogService extends BaseService<ManagerLogEntity>{

	/**
	 * 登录错误
	 * @author zhaodx
	 * @date 2020-07-30 14:26
	 * @param maps
	 * @return
	 */
	Result<Object> loginError(Map<String,String> maps);
}
	