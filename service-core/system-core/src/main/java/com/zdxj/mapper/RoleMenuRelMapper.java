package com.zdxj.mapper;

import java.util.List;
import java.util.Set;

import com.zdxj.core.mapper.BaseMapper;
import com.zdxj.model.RoleMenuRelEntity;

public interface RoleMenuRelMapper extends BaseMapper<RoleMenuRelEntity>{

	/**
	 * 根据角色id获取所有权限编码
	 * @author zhaodx
	 * @date 2020-07-30 13:26
	 * @param list
	 * @return
	 */
	Set<String> listUserPermCodesByRoleIds(List<Integer> list);
	
	/**
	 * 根据角色id删除关联关系
	 * @param roleIds
	 * @return
	 */
	int delRelByRoleIds(List<Integer> roleIds);
	
	/**
	 * 批量保存关联关系
	 * @param list
	 * @return
	 */
	int saveByBatch(List<RoleMenuRelEntity> list);
}	