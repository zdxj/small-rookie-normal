package com.zdxj.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.zdxj.constant.SystemCacheConstant;
import com.zdxj.core.Result;
import com.zdxj.core.service.impl.BaseServiceImpl;
import com.zdxj.mapper.SystemManagerMapper;
import com.zdxj.model.ManagerLogEntity;
import com.zdxj.model.SystemManagerEntity;
import com.zdxj.service.ManagerLogService;
import com.zdxj.service.ManagerRoleRelService;
import com.zdxj.service.SystemManagerService;

@Service
@Transactional
public class SystemManagerServiceImpl extends BaseServiceImpl<SystemManagerMapper,SystemManagerEntity> implements SystemManagerService {

	public SystemManagerServiceImpl() {
		super(SystemCacheConstant.SYSTEM_MANAGER);
	}

	@Autowired
	private ManagerLogService managerLogService ;
	@Autowired
	private ManagerRoleRelService managerRoleRelService ;
	
	/**
	 * 根据用户名获取管理员
	 * @author zhaodx
	 * @date 2020-07-16 18:42
	 * @param loginName
	 * @return
	 */
	@Override
	public SystemManagerEntity getByLoginName(String loginName) {
		Map<String,Object> maps = Maps.newHashMap();
		maps.put("loginName", loginName);
		List<SystemManagerEntity> list = this.listByConditionWithPage(maps, 0, 1);
		if(CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
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
		String loginName = maps.get("loginName");
		SystemManagerEntity manager = this.getByLoginName(loginName);
		if(manager == null) {
			return Result.failed("管理员不存在");
		}
		//更新管理员登录信息
		SystemManagerEntity updateManager = new SystemManagerEntity();
		updateManager.setId(manager.getId());
		updateManager.setLoginIp(maps.get("loginIp"));
		updateManager.setLoginTime(new Date());
		updateManager.setLoginErrorCount(0);
		this.updateEntity(updateManager);
		//添加登录日志
		ManagerLogEntity log = new ManagerLogEntity();
		log.setLoginName(loginName);
		log.setLoginIp(maps.get("loginIp"));
		log.setActionName(maps.get("actionName"));
		log.setDescription("管理员登录成功");
		managerLogService.saveEntity(log);
		
		//添加返回数据
		manager.setLoginIp(updateManager.getLoginIp());
		manager.setLoginTime(updateManager.getLoginTime());
		manager.setLoginErrorCount(0);
		return Result.success(manager);
	}
	
	@Override
	public Integer deleteByIds(List<Integer> list) {
		//删除关联的角色关系
		managerRoleRelService.delRelByManagerIds(list);
		return super.deleteByIds(list);
	}
}