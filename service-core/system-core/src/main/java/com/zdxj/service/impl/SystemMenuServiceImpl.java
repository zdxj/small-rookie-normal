package com.zdxj.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zdxj.constant.SystemCacheConstant;
import com.zdxj.core.service.impl.BaseServiceImpl;
import com.zdxj.mapper.SystemMenuMapper;
import com.zdxj.model.SystemMenuEntity;
import com.zdxj.model.ZtreeEntity;
import com.zdxj.service.SystemMenuService;

@Service
@Transactional
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenuMapper,SystemMenuEntity> implements SystemMenuService {

	public SystemMenuServiceImpl() {
		super(SystemCacheConstant.SYSTEM_MENU);
	}

	@Autowired
	private SystemMenuMapper myMapper ;
	
	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	@Override
	public List<ZtreeEntity> listZtreeMenu(Map<String, Object> maps) {
		//角色id(角色关联菜单权限时用到)
		List<Integer> selectMenuIds = null ;
		if(maps.containsKey("roleId")) {
			selectMenuIds = myMapper.listMenuIdByRoleId((Integer)maps.get("roleId"));
			maps.remove("roleId");
		}
		boolean isOnlyShowSelect = false ;
		if(maps.containsKey("isOnlyShowSelect")) {
			isOnlyShowSelect = (Boolean)maps.get("isOnlyShowSelect");
			maps.remove("isOnlyShowSelect");
		}
		List<SystemMenuEntity> list = this.listByCondition(maps);
		return this.initZtree(list,selectMenuIds,isOnlyShowSelect);
	}
	
	 /**
     * 对象转菜单树
     * @param menuList 菜单列表
     * @param selectMenuIds 角色已选择的菜单列表
     * @param isOnlyShowSelect 只展示选择的
     * @return 树结构列表
     */
    private List<ZtreeEntity> initZtree(List<SystemMenuEntity> menuList,List<Integer> selectMenuIds,boolean isOnlyShowSelect){
        List<ZtreeEntity> ztrees = Lists.newArrayList();
        ZtreeEntity ztree = null ;
        boolean isCheck = CollectionUtils.isNotEmpty(selectMenuIds);
        for (SystemMenuEntity menu : menuList){
        	ztree = new ZtreeEntity();
            ztree.setId(menu.getId());
            ztree.setpId(menu.getParentId());
            ztree.setName(menu.getMenuName());
            ztree.setTitle(menu.getMenuName());
            if(isCheck) {
            	ztree.setChecked(selectMenuIds.contains(menu.getId()));
            }
            //只显示已选中的
            if(isOnlyShowSelect) {
            	if(ztree.isChecked()) {
            		ztrees.add(ztree);
            	}
            	continue ;
            }
            ztrees.add(ztree);
        }
        return ztrees;
    }

    /**
	 * 获取管理员关联的菜单权限
	 * @author zhaodx
	 * @date 2020-08-05 15:02
	 * @param userId
	 * @return
	 */
	@Override
	public List<SystemMenuEntity> listMenusByUserId(Integer userId) {
		return myMapper.listMenusByUserId(userId);
	}

}