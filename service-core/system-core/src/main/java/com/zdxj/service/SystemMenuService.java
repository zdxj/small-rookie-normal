package com.zdxj.service;

import java.util.List;
import java.util.Map;

import com.zdxj.core.service.BaseService;
import com.zdxj.model.SystemMenuEntity;
import com.zdxj.model.ZtreeEntity;

public interface SystemMenuService extends BaseService<SystemMenuEntity>{

	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	List<ZtreeEntity> listZtreeMenu(Map<String, Object> maps);
	
	/**
	 * 获取管理员关联的菜单权限
	 * @author zhaodx
	 * @date 2020-08-05 15:02
	 * @param userId
	 * @return
	 */
	List<SystemMenuEntity> listMenusByUserId(Integer userId);
}
	