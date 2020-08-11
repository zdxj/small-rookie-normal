package com.zdxj.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.constant.SystemCacheConstant;
import com.zdxj.core.Result;
import com.zdxj.core.service.impl.BaseServiceImpl;
import com.zdxj.mapper.RoleMenuRelMapper;
import com.zdxj.model.RoleMenuRelEntity;
import com.zdxj.model.SystemMenuEntity;
import com.zdxj.service.RoleMenuRelService;
import com.zdxj.service.SystemMenuService;
import com.zdxj.util.RedisUtils;

@Service
@Transactional
public class RoleMenuRelServiceImpl extends BaseServiceImpl<RoleMenuRelMapper,RoleMenuRelEntity> implements RoleMenuRelService {

	public RoleMenuRelServiceImpl() {
		super(SystemCacheConstant.ROLE_MENU_REL);
	}

	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private RoleMenuRelMapper myMapper ;
	@Autowired
	private SystemMenuService systemMenuService ;
	
	/**
	 * 初始化权限
	 * @author zhaodx
	 * @date 2020-07-21 13:07
	 */
	@PostConstruct
    public void initResourceUrl() {
		if(redisUtils.opsForHashSize(GlobalConstants.RESOURCE_ROLES_MAP)>0) {
			return ;
		}
		this.initRoleMenuRel(false);
    }
	
	/**
	 * 初始化角色和权限的对应关系
	 * @author zhaodx
	 * @date 2020-07-20 18:13
	 * @return
	 */
	@Override
	public Result<Object> initRoleMenuRel(boolean isReload) {
		
		if(!isReload && redisUtils.opsForHashSize(GlobalConstants.RESOURCE_ROLES_MAP)>0) {
			return Result.success();
		}
		//获取所有菜单信息
		Map<String,Object> maps = Maps.newHashMap();
		maps.put("deleted", false);
		List<SystemMenuEntity> menuList = systemMenuService.listByCondition(maps);
		if(CollectionUtils.isEmpty(menuList)) {
			return null ;
		}
		//处理成map形式避免二次查库
		Map<Integer,String> menuMap = Maps.newHashMap();
		for(SystemMenuEntity menu:menuList) {
			if(StringUtils.isBlank(menu.getPermUrl())) {
				continue ;
			}
			menuMap.put(menu.getId(), menu.getPermUrl());
		}
		//获取所有角色和菜单的关联信息
		List<RoleMenuRelEntity> listAll = this.listAll();
		if(CollectionUtils.isEmpty(listAll)) {
			return null ;
		}
		Map<String, List<String>> result = Maps.newHashMap();
		for(RoleMenuRelEntity info:listAll) {
			this.handlerRoleUrlMap(result, menuMap, info);
		}
		if(result != null) {
    		redisUtils.hmset(GlobalConstants.RESOURCE_ROLES_MAP, result);
    	}
		return Result.success();
	}
	
	/**
	 * 处理角色和url的关系
	 * @author zhaodx
	 * @date 2020-07-21 10:36
	 * @param result
	 * @param menuMap
	 * @param info
	 */
	private void handlerRoleUrlMap(Map<String, List<String>> result,Map<Integer,String> menuMap,RoleMenuRelEntity info) {
		if(StringUtils.isBlank(menuMap.get(info.getMenuId()))) {
			return ;
		}
		String[] permUrls = menuMap.get(info.getMenuId()).split(",");
		for(String url :permUrls) {
			if(result.containsKey(url)) {
				List<String> roleIds = result.get(url);
				if(roleIds.contains(GlobalConstants.AUTHORITY_PREFIX+info.getRoleId())) {
					continue ;
				}
				//追加角色
				roleIds.add(GlobalConstants.AUTHORITY_PREFIX+info.getRoleId());
				result.put(url,roleIds);
			}else {
				//创建新的角色关系
				List<String> roleIds = Lists.newArrayList();
				//超级管理员拥有所有权限
				roleIds.add(GlobalConstants.AUTHORITY_PREFIX+GlobalConstants.SUPER_ROLE_ID);
				roleIds.add(GlobalConstants.AUTHORITY_PREFIX+info.getRoleId());
				result.put(url,roleIds);
			}
		}
	}

	/**
	 * 根据角色id获取所有权限编码
	 * @author zhaodx
	 * @date 2020-07-30 13:26
	 * @param roleList
	 * @return
	 */
	@Override
	public Set<String> listUserPermCodesByRoleIds(List<Integer> roleList) {
		return myMapper.listUserPermCodesByRoleIds(roleList);
	}

	/**
	 * 根据角色id删除关联关系
	 * @param roleIds
	 * @return
	 */
	@Override
	public int delRelByRoleIds(List<Integer> roleIds) {
		return myMapper.delRelByRoleIds(roleIds);
	}

	/**
	 * 批量保存关联关系
	 * @param list
	 * @return
	 */
	@Override
	public int saveByBatch(List<RoleMenuRelEntity> list) {
		return myMapper.saveByBatch(list);
	}
}