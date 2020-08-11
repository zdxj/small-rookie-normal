package com.zdxj.service;

import java.util.List;

import com.zdxj.core.Result;
import com.zdxj.core.service.BaseService;
import com.zdxj.model.SystemRoleEntity;

public interface SystemRoleService extends BaseService<SystemRoleEntity>{
	
	/**
	 * 更新角色
	 * @author zhaodx
	 * @date 2020-08-04 13:21
	 * @param entity
	 * @return
	 */
    Result<Object> updateRole(SystemRoleEntity entity);
    
    /**
     * 删除角色
     * @author zhaodx
     * @date 2020-08-04 14:38
     * @param ids
     * @return
     */
    Result<Object> removeByIds(List<Integer> ids) ;
}
	