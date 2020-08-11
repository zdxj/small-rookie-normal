package com.zdxj.mapper;

import java.util.List;

import com.zdxj.core.mapper.BaseMapper;
import com.zdxj.model.DepartmentEntity;

public interface DepartmentMapper extends BaseMapper<DepartmentEntity>{

	/**
	 * 获取子部门
	 * @author zhaodx
	 * @date 2020-08-04 10:14
	 * @param id
	 * @return
	 */
	List<DepartmentEntity> listChildrenDeptById(Integer id);
	
	/**
	 * 批量更新
	 * @author zhaodx
	 * @date 2020-08-04 10:20
	 * @param list
	 * @return
	 */
	int updateByBatch(List<DepartmentEntity> list);
}	