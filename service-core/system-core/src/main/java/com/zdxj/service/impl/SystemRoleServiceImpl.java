package com.zdxj.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.constant.SystemCacheConstant;
import com.zdxj.core.Result;
import com.zdxj.core.service.impl.BaseServiceImpl;
import com.zdxj.mapper.SystemRoleMapper;
import com.zdxj.model.ManagerRoleRelEntity;
import com.zdxj.model.RoleMenuRelEntity;
import com.zdxj.model.SystemRoleEntity;
import com.zdxj.service.ManagerRoleRelService;
import com.zdxj.service.RoleMenuRelService;
import com.zdxj.service.SystemRoleService;

@Service
@Transactional
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRoleMapper,SystemRoleEntity> implements SystemRoleService {

	public SystemRoleServiceImpl() {
		super(SystemCacheConstant.SYSTEM_ROLE);
	}

	@Autowired
	private RoleMenuRelService roleMenuRelService ;
	@Autowired
	private ManagerRoleRelService managerRoleRelService ;
	
	@Override
	public Integer saveEntity(SystemRoleEntity entity) {
		Integer saveEntity = super.saveEntity(entity);
		this.handleRel(entity);
		return saveEntity;
	}
	
	/**
	 * 更新角色
	 * @author zhaodx
	 * @date 2020-08-04 13:21
	 * @param entity
	 * @return
	 */
	@Override
	public Result<Object> updateRole(SystemRoleEntity entity) {
		this.handleRel(entity);
		return Result.success(super.updateEntity(entity));
	}
	
	/**
	 * 处理关联关系
	 * @author zhaodx
	 * @date 2020-08-04 13:54
	 * @param entity
	 */
	private void handleRel(SystemRoleEntity entity) {
		if(GlobalConstants.SUPER_ROLE_ID.equals(entity.getId())) {
			return ;
		}
		List<RoleMenuRelEntity> list = null ;
		if(entity.getMenuIds() != null && entity.getMenuIds().length>0) {
			list = Lists.newArrayList();
			RoleMenuRelEntity rel = null ;
			for (Integer menuId : entity.getMenuIds()) {
				if(menuId != null) {
					rel = new RoleMenuRelEntity();
					rel.setDeleted(false);
					rel.setCreateTime(new Date());
					rel.setUpdateTime(new Date());
					rel.setMenuId(menuId);
					rel.setRoleId(entity.getId());
					list.add(rel);
				}
			}
		}
		//删除关联关系
		List<Integer> roleIds = Lists.newArrayList();
		roleIds.add(entity.getId());
		roleMenuRelService.delRelByRoleIds(roleIds);
		//保存新的关联关系
		if (CollectionUtils.isNotEmpty(list)) {
			roleMenuRelService.saveByBatch(list);
		}
	}

	/**
     * 删除角色
     * @author zhaodx
     * @date 2020-08-04 14:38
     * @param ids
     * @return
     */
	@Override
	public Result<Object> removeByIds(List<Integer> ids) {
		Map<String,Object> maps = Maps.newHashMap();
		for(Integer roleId:ids) {
			if(GlobalConstants.SUPER_ROLE_ID.equals(roleId)) {
				return Result.failed("系统角色，无法删除");
			}
			maps.put("roleId", roleId);
			List<ManagerRoleRelEntity> listByConditionWithPage = managerRoleRelService.listByConditionWithPage(maps, 0, 1);
			if(CollectionUtils.isNotEmpty(listByConditionWithPage)) {
				SystemRoleEntity role = this.getById(roleId);
				return Result.failed(role.getName()+"已分配，无法删除");
			}
		}
		//删除关联关系
		roleMenuRelService.delRelByRoleIds(ids);
		//删除角色
		return Result.success(this.deleteByIds(ids));
	}
}