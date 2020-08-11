package com.zdxj.service;

import java.util.Map;

import com.zdxj.core.Result;
import com.zdxj.core.service.BaseService;
import com.zdxj.model.SystemManagerEntity;

public interface SystemManagerService extends BaseService<SystemManagerEntity>{

	/**
	 * 根据用户名获取管理员
	 * @author zhaodx
	 * @date 2020-07-16 18:42
	 * @param loginName
	 * @return
	 */
	SystemManagerEntity getByLoginName(String loginName);
	
	/**
	 * 登录
	 * @author zhaodx
	 * @date 2020-07-30 14:20
	 * @param maps
	 * @return
	 */
	Result<SystemManagerEntity> login(Map<String,String> maps);
}
	