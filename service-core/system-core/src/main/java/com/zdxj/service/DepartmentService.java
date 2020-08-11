package com.zdxj.service;

import java.util.List;
import java.util.Map;

import com.zdxj.core.Result;
import com.zdxj.core.service.BaseService;
import com.zdxj.model.DepartmentEntity;
import com.zdxj.model.ZtreeEntity;

public interface DepartmentService extends BaseService<DepartmentEntity>{

	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	List<ZtreeEntity> listTreeDept(Map<String, Object> maps);
	
	/**
	 * 更新部门
	 * @author zhaodx
	 * @date 2020-08-04 10:06
	 * @param entity
	 * @return
	 */
    Result<Object> updateDept(DepartmentEntity entity);
}
	