package com.zdxj.service;

import java.util.List;
import java.util.Set;

import com.zdxj.core.Result;
import com.zdxj.core.service.BaseService;
import com.zdxj.model.RoleMenuRelEntity;

public interface RoleMenuRelService extends BaseService<RoleMenuRelEntity>{

	/**
	 * 初始化角色和权限的对应关系
	 * @author zhaodx
	 * @date 2020-07-20 18:13
	 * @return
	 */
	Result<Object> initRoleMenuRel(boolean isReload);
	
	/**
	 * 根据角色id获取所有权限编码
	 * @author zhaodx
	 * @date 2020-07-30 13:26
	 * @param roleList
	 * @return
	 */
	Set<String> listUserPermCodesByRoleIds(List<Integer> roleList);
	
	/**
	 * 根据角色id删除关联关系
	 * @param managerId
	 * @return
	 */
	int delRelByRoleIds(List<Integer> list);
	
	/**
	 * 批量保存关联关系
	 * @param list
	 * @return
	 */
	int saveByBatch(List<RoleMenuRelEntity> list);
}
	