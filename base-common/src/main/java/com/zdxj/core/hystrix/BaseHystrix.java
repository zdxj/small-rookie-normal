package com.zdxj.core.hystrix;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zdxj.core.Page;
import com.zdxj.core.Result;
import com.zdxj.core.entity.BaseEntity;
import com.zdxj.core.feignclient.BaseFeignClient;


public class BaseHystrix<T extends BaseEntity> implements BaseFeignClient<T> {
    @Override
    public Result<T> getById(@RequestParam("id") Integer id) {
        return Result.failed();
    }

    @Override
    public Result<Object> saveEntity(@RequestBody T entity) {
    	return Result.failed();
    }

    @Override
    public Result<Object> updateEntity(@RequestBody T entity) {
    	return Result.failed();
    }


    @Override
    public Result<Object> removeById(@RequestParam("id") Integer id) {
    	return Result.failed();
    }

    @Override
    public Result<Object> removeByIds(@RequestBody List<Integer> ids) {
    	return Result.failed();
    }

    @Override
	public Result<List<T>> listAll() {
    	return Result.failed();
	}
    
    @Override
    public Result<List<T>> listByCondition(@RequestBody Map<String, Object> maps) {
    	return Result.failed();
    }

    @Override
    public Result<List<T>> listByConditionWithPage(@RequestBody Map<String, Object> maps, @RequestParam("startIndex") int startIndex, @RequestParam("pageSize") int pageSize) {
    	return Result.failed();
    }

    @Override
    public Result<Page<List<T>>> listByPage(@RequestBody Map<String, Object> maps, @RequestParam("startIndex") int startIndex, @RequestParam("pageSize") int pageSize) {
    	return Result.failed();
    }

    @Override
    public Result<Object> countByCondition(@RequestBody(required =false) Map<String, Object> maps) {
    	return Result.failed("操作失败",0);
    }

    @Override
    public Result<List<Integer>> listIds(@RequestBody(required =false) Map<String, Object> maps) {
    	return Result.failed();
    }
}
