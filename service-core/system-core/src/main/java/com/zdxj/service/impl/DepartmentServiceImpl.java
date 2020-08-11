package com.zdxj.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.zdxj.constant.SystemCacheConstant;
import com.zdxj.core.Result;
import com.zdxj.core.service.impl.BaseServiceImpl;
import com.zdxj.mapper.DepartmentMapper;
import com.zdxj.model.DepartmentEntity;
import com.zdxj.model.ZtreeEntity;
import com.zdxj.service.DepartmentService;
import com.zdxj.util.RedisUtils;

@Service
@Transactional
public class DepartmentServiceImpl extends BaseServiceImpl<DepartmentMapper,DepartmentEntity> implements DepartmentService {

	public DepartmentServiceImpl() {
		super(SystemCacheConstant.DEPARTMENT);
	}

	@Autowired
	private RedisUtils redisUtils ;
	@Autowired
	private DepartmentMapper myMapper ;
	
	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	@Override
	public List<ZtreeEntity> listTreeDept(Map<String, Object> maps) {
		//排除的ID
		Integer excludeId = (Integer)maps.get("excludeId");
		maps.remove("excludeId");
		List<DepartmentEntity> list = this.listByCondition(maps); ;
		if(excludeId != null && excludeId.intValue() != 0) {
			Iterator<DepartmentEntity> it = list.iterator();
	        while (it.hasNext()){
	        	DepartmentEntity d = (DepartmentEntity) it.next();
	            if (d.getId().intValue() == excludeId
	                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), excludeId.toString())){
	                it.remove();
	            }
	        }
		}
		return this.initZtree(list);
	}
	
	/**
     * 对象转部门树
     * @param deptList 部门列表
     * @return 树结构列表
     */
    private List<ZtreeEntity> initZtree(List<DepartmentEntity> deptList){

        List<ZtreeEntity> ztrees = Lists.newArrayList();
        ZtreeEntity ztree = null ;
        for (DepartmentEntity dept : deptList){
        	ztree = new ZtreeEntity();
            ztree.setId(dept.getId());
            ztree.setpId(dept.getParentId());
            ztree.setName(dept.getName());
            ztree.setTitle(dept.getName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    @Override
    public Integer saveEntity(DepartmentEntity entity) {
    	//父级
		DepartmentEntity parentDept = this.getById(entity.getParentId());
		entity.setAncestors(parentDept.getAncestors() + "," + entity.getParentId());
    	return super.saveEntity(entity);
    }
    
    /**
	 * 更新部门
	 * @author zhaodx
	 * @date 2020-08-04 10:06
	 * @param entity
	 * @return
	 */
	@Override
	public Result<Object> updateDept(DepartmentEntity entity) {
		//父级
		DepartmentEntity parentDept = this.getById(entity.getParentId());
		//当前部门
		DepartmentEntity dept = this.getById(entity.getId());
		
		if(parentDept != null && dept != null) {
			String newAncestors = parentDept.getAncestors() + "," + parentDept.getId();
            String oldAncestors = dept.getAncestors();
            entity.setAncestors(newAncestors);
            this.updateAncestorsByBatch(entity.getId(), newAncestors, oldAncestors);
		}
		return Result.success(this.updateEntity(entity));
	}
	
	/**
	 * 批量更新级别层级
	 * @author zhaodx
	 * @date 2020-08-04 10:21
	 * @param id
	 * @param newAncestors
	 * @param oldAncestors
	 */
	public void updateAncestorsByBatch(Integer id, String newAncestors, String oldAncestors){
        List<DepartmentEntity> children = myMapper.listChildrenDeptById(id);
        List<String> cacheIds = Lists.newArrayList();
        for (DepartmentEntity child : children){
            child.setAncestors(child.getAncestors().replace(oldAncestors, newAncestors));
            cacheIds.add(SystemCacheConstant.DEPARTMENT+child.getId());
        }
        if (children.size() > 0){
        	redisUtils.del(cacheIds);
        	myMapper.updateByBatch(children);
        }
    }
}