package com.zdxj.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zdxj.constant.SystemCacheConstant;
import com.zdxj.core.Result;
import com.zdxj.core.service.impl.BaseServiceImpl;
import com.zdxj.mapper.ManagerRoleRelMapper;
import com.zdxj.model.ManagerRoleRelEntity;
import com.zdxj.service.ManagerRoleRelService;

@Service
@Transactional
public class ManagerRoleRelServiceImpl extends BaseServiceImpl<ManagerRoleRelMapper,ManagerRoleRelEntity> implements ManagerRoleRelService {

	public ManagerRoleRelServiceImpl() {
		super(SystemCacheConstant.MANAGER_ROLE_REL);
	}

	@Autowired
	private ManagerRoleRelMapper myMapper ;
	
	/**
	 * 根据管理员ID获取管理员角色ID
	 * @author zhaodx
	 * @date 2018年12月4日
	 * @param @param id
	 * @param @return
	 * @return Result<Set<Integer>>
	 */
	@Override
	public Result<Set<Integer>> listRoleIdsByManagerId(Integer managerId) {
		return Result.success(myMapper.listRoleIdsByManagerId(managerId));
	}

	/**
	 * 根据管理员id删除关联关系
	 * @param managerId
	 * @return
	 */
	@Override
	public int delRelByManagerIds(List<Integer> list) {
		return myMapper.delRelByManagerIds(list);
	}

	/**
	 * 批量保存关联关系
	 * @param list
	 * @return
	 */
	@Override
	public int saveByBatch(List<ManagerRoleRelEntity> list) {
		return myMapper.saveByBatch(list);
	}
	
	/**
	 * 保存管理员对应角色关系
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	public Result<Object> saveSelectRole(Map<String, Object> maps) {
		int managerId = (Integer)maps.get("managerId");
		Gson gson = new Gson();
		List<Integer> privilegeids= gson.fromJson(gson.toJson(maps.get("roleId")), new TypeToken<List<Integer>>() {}.getType());
		List<ManagerRoleRelEntity> list = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(privilegeids)) {
			for (Integer priv : privilegeids) {
				if(priv != null) {
					ManagerRoleRelEntity  entity = new ManagerRoleRelEntity();
					entity.setDeleted(false);
					entity.setCreateTime(new Date());
					entity.setUpdateTime(new Date());
					entity.setManagerId(managerId);
					entity.setRoleId(priv);
					list.add(entity);
				}
			}
		}
		//删除关联关系
		List<Integer> userIds = Lists.newArrayList();
		userIds.add(managerId);
		myMapper.delRelByManagerIds(userIds);
		//保存新的关联关系
		if (list.size() > 0) {
			myMapper.saveByBatch(list);
		}
		return Result.success();
	}
}