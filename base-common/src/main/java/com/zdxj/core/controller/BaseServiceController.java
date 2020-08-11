package com.zdxj.core.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zdxj.core.Page;
import com.zdxj.core.Result;
import com.zdxj.core.entity.BaseEntity;
import com.zdxj.core.service.BaseService;

public class BaseServiceController<M extends BaseService<T>,T extends BaseEntity> {

	@Autowired
    private M myService;

	
	public M getMyService() {
		return myService;
	}

	public void setMyService(M myService) {
		this.myService = myService;
	}

	/**
     * 根据id获取对象
     * @param id
     * @return
     */
    @GetMapping("getById")
    public Result<T> getById(@RequestParam("id") Integer id){
        return Result.success(myService.getById(id));
    }

    /**
     * 保存对象
     * @param entity
     * @return
     */
    @PostMapping("saveEntity")
    public Result<Object> saveEntity(@RequestBody T entity){
        return Result.success(myService.saveEntity(entity));
    }

    /**
     * 更新对象
     * @param entity
     * @return
     */
    @PostMapping("updateEntity")
    public Result<Object> updateEntity(@RequestBody T entity){
    	myService.updateEntity(entity);
        return Result.success(entity.getId());
    }

    /**
     * 根据UUID删除对象
     * @param id
     * @return
     */
    @GetMapping("removeById")
    public Result<Object> removeById(@RequestParam("id") Integer id){
        return Result.success(myService.deleteById(id));
    }

    /**
     * 根据UUID集合删除对象
     * @param list
     * @return
     */
    @PostMapping("removeByIds")
    public Result<Object> removeByIds(@RequestBody List<Integer> list){
        return Result.success(myService.deleteByIds(list));
    }

    /**
     * 获取所有对象集合
     * @return
     */
    @PostMapping("listAll")
    public Result<List<T>> listAll(){
    	return Result.success(myService.listAll());
    }
    
    /**
     * 根据条件获取集合
     * @param maps
     * @return
     */
    @PostMapping("listByCondition")
    public Result<List<T>> listByCondition(@RequestBody Map<String, Object> maps){
    	return Result.success(myService.listByCondition(maps));
    }

    /**
     * 根据条件获取集合
     * @param maps
     * @return
     */
    @PostMapping("listByConditionWithPage")
    public Result<List<T>> listByConditionWithPage(@RequestBody Map<String, Object> maps, @RequestParam("startIndex") int startIndex, @RequestParam("pageSize") int pageSize){
        return Result.success(myService.listByConditionWithPage(maps,startIndex,pageSize));
    }
    
    /**
     * 分页获取对象集合
     * @param maps
     * @param startIndex
     * @param pageSize
     * @return
     */
    @PostMapping("listByPage")
    public Result<Page<List<T>>> listByPage(@RequestBody Map<String, Object> maps,@RequestParam("startIndex") Integer startIndex, @RequestParam("pageSize") Integer pageSize){
    	return Result.success(myService.listByPage(maps, startIndex, pageSize));
    }
    
    /**
     * 根据条件获取集合数量
     * @param maps
     * @return
     */
    @PostMapping("countByCondition")
    public Result<Object> countByCondition(@RequestBody(required =false) Map<String, Object> maps){
        return Result.success(myService.countByCondition(maps));
    }
    
    /**
     * 根据条件获取对象UUID集合
     * @param maps
     * @return
     */
    @PostMapping("listIds")
    public Result<List<Integer>> listIds(@RequestBody(required =false) Map<String, Object> maps){
        return Result.success(myService.listIds(maps));
    }
  
}
