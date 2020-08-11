package com.zdxj.mapper;

import java.util.List;

import com.zdxj.core.mapper.BaseMapper;
import com.zdxj.model.SystemMenuEntity;

public interface SystemMenuMapper extends BaseMapper<SystemMenuEntity>{

	/**
	 * 根据角色获取关联的菜单id
	 * @author zhaodx
	 * @date 2020-08-04 11:55
	 * @param roleId
	 * @return
	 */
	List<Integer> listMenuIdByRoleId(Integer roleId);
	
	/**
	 * 获取管理员关联的菜单权限
	 * @author zhaodx
	 * @date 2020-08-05 15:02
	 * @param userId
	 * @return
	 */
	List<SystemMenuEntity> listMenusByUserId(Integer userId);
}	