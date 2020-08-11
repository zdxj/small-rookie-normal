package com.zdxj.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zdxj.core.Result;
import com.zdxj.core.service.impl.BaseServiceImpl;
import com.zdxj.mapper.ManagerLogMapper;
import com.zdxj.model.ManagerLogEntity;
import com.zdxj.model.SystemManagerEntity;
import com.zdxj.service.ManagerLogService;
import com.zdxj.service.SystemManagerService;

@Service
@Transactional
public class ManagerLogServiceImpl extends BaseServiceImpl<ManagerLogMapper,ManagerLogEntity> implements ManagerLogService {

	public ManagerLogServiceImpl() {
		super("ManagerLog");
	}

	private SystemManagerService systemManagerService ;
	/**
	 * 登录错误
	 * @author zhaodx
	 * @date 2020-07-30 14:26
	 * @param maps
	 * @return
	 */
	@Override
	public Result<Object> loginError(Map<String, String> maps) {
		String loginName = maps.get("loginName");
		SystemManagerEntity manager = systemManagerService.getByLoginName(loginName);
		if(manager != null) {
			//增加登录错误次数
			SystemManagerEntity info = new SystemManagerEntity();
			info.setId(manager.getId());
			info.setLoginErrorCount(manager.getLoginErrorCount()+1);
			if(manager.getLoginErrorCount().intValue() ==4) {
				info.setLoginErrorTime(new Date());
			}
			if(maps.containsKey("updateLoginErrorTime")) {
				info.setLoginErrorTime(new Date());
			}
			systemManagerService.updateEntity(info);
		}
		ManagerLogEntity log = new ManagerLogEntity();
		log.setLoginName(loginName);
		log.setLoginIp(maps.get("loginIp"));
		log.setActionName(maps.get("actionName"));
		log.setDescription(maps.get("description"));
		return Result.success(this.saveEntity(log));
	}
}