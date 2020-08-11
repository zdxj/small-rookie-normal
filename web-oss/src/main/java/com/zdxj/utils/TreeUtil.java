package com.zdxj.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.CollectionUtils;

import com.google.common.collect.Lists;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.dto.SystemMenuDTO;
import com.zdxj.model.SystemMenuEntity;


/**
 * 菜单分类树帮助类
 * @author zhaodx
 *
 */
public class TreeUtil {

	public static List<SystemMenuDTO> getTreeList(List<SystemMenuEntity> entityList,List<Integer> checkedIds,List<Integer> openIds) {
        List<SystemMenuDTO> resultList = Lists.newArrayList();
        if(CollectionUtils.isEmpty(entityList)) {
        	return resultList ;
        }
        //获取顶层元素集合
        int parentId =GlobalConstants.MENU_ROOT_ID;
        for (SystemMenuEntity entity : entityList) {
        	parentId = entity.getParentId();
            //顶层元素的parentCode==null或者为0
            if (GlobalConstants.MENU_ROOT_ID==parentId) {
            	try {
            		SystemMenuDTO dto = new SystemMenuDTO();
					BeanUtils.copyProperties(dto, entity);
					dto.setName(entity.getMenuName());
					dto.setDataUrl(entity.getUrl());
					if(CollectionUtils.isNotEmpty(checkedIds) && checkedIds.contains(entity.getId())) {
						dto.setChecked(true);
					}
					if(CollectionUtils.isNotEmpty(openIds) && openIds.contains(entity.getId())) {
						dto.setOpen(true);
					}
					resultList.add(dto);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
            }
        }
        //获取每个顶层元素的子数据集合
        for (SystemMenuDTO entity : resultList) {
            entity.setChildren(getSubList(entity.getId(), entityList,checkedIds,openIds));
        }

        return resultList;
    }

    /**
     * 获取子数据集合
     *
     * @param id
     * @param entityList
     */
    private static List<SystemMenuDTO> getSubList(Integer id, List<SystemMenuEntity> entityList,List<Integer> checkedIds,List<Integer> openIds) {
        List<SystemMenuDTO> childList = Lists.newArrayList();
        Integer parentId =GlobalConstants.MENU_ROOT_ID;
        //子集的直接子对象
        for (SystemMenuEntity entity : entityList) {
            parentId = entity.getParentId();
            if (id.intValue() == parentId.intValue()) {
            	try {
            		SystemMenuDTO dto = new SystemMenuDTO();
					BeanUtils.copyProperties(dto, entity);
					dto.setName(entity.getMenuName());
					dto.setDataUrl(entity.getUrl());
					if(CollectionUtils.isNotEmpty(checkedIds) && checkedIds.contains(entity.getId())) {
						dto.setChecked(true);
					}
					if(CollectionUtils.isNotEmpty(openIds) && openIds.contains(entity.getId())) {
						dto.setOpen(true);
					}
					childList.add(dto);
				} catch (IllegalAccessException | InvocationTargetException e) {
					e.printStackTrace();
				}
            }
        }
        //子集的间接子对象
        for (SystemMenuDTO entity : childList) {
            entity.setChildren(getSubList(entity.getId(), entityList,checkedIds,openIds));
        }
        //递归退出条件
        if (CollectionUtils.isEmpty(childList)) {
            return null;
        }
        return childList;
    }
}
