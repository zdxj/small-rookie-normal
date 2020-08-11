package com.zdxj.hystrix;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zdxj.core.Result;
import com.zdxj.core.hystrix.BaseHystrix;
import com.zdxj.feignclient.DepartmentFeignClient;
import com.zdxj.model.DepartmentEntity;
import com.zdxj.model.ZtreeEntity;

@Component
public class DepartmentHystrix extends BaseHystrix<DepartmentEntity> implements DepartmentFeignClient {

	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	@Override
	public Result<List<ZtreeEntity>> listTreeDept(Map<String, Object> maps) {
		return Result.failed();
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
		return Result.failed();
	}

}
