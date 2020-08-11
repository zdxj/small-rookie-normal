package com.zdxj.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zdxj.core.Result;
import com.zdxj.core.service.BaseService;
import com.zdxj.model.ManagerRoleRelEntity;

public interface ManagerRoleRelService extends BaseService<ManagerRoleRelEntity>{

	/**
	 * 根据管理员ID获取管理员角色ID
	 * @author zhaodx
	 * @date 2018年12月4日
	 * @param @param id
	 * @param @return
	 * @return Result<Set<Integer>>
	 */
	Result<Set<Integer>> listRoleIdsByManagerId(Integer managerId);
	
	/**
	 * 根据管理员id删除关联关系
	 * @param managerId
	 * @return
	 */
	int delRelByManagerIds(List<Integer> list);
	
	/**
	 * 批量保存关联关系
	 * @param list
	 * @return
	 */
	int saveByBatch(List<ManagerRoleRelEntity> list);
	
	/**
	 * 保存管理员对应角色关系
	 * @param request
	 * @param response
	 * @return
	 */
	Result<Object> saveSelectRole(Map<String, Object> maps);
}
	