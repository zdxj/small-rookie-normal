package com.zdxj.mapper;

import java.util.List;
import java.util.Set;

import com.zdxj.core.mapper.BaseMapper;
import com.zdxj.model.ManagerRoleRelEntity;

public interface ManagerRoleRelMapper extends BaseMapper<ManagerRoleRelEntity>{

	/**
	 * 根据管理员ID获取管理员角色ID
	 * @author zhaodx
	 * @date 2018年12月4日
	 * @param @param id
	 * @param @return
	 * @return Set<Integer>
	 */
	Set<Integer> listRoleIdsByManagerId(Integer managerId);
	
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
}	