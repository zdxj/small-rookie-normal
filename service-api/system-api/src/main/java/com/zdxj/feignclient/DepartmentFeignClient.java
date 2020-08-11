package com.zdxj.feignclient;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.zdxj.core.Result;
import com.zdxj.core.feignclient.BaseFeignClient;
import com.zdxj.hystrix.DepartmentHystrix;
import com.zdxj.model.DepartmentEntity;
import com.zdxj.model.ZtreeEntity;

@FeignClient(value = "gateway-server",path="/systemApi/department", fallback = DepartmentHystrix.class)
public interface DepartmentFeignClient extends BaseFeignClient<DepartmentEntity>{

	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	@PostMapping("listTreeDept")
	Result<List<ZtreeEntity>> listTreeDept(@RequestBody Map<String, Object> maps);
	
	/**
	 * 更新部门
	 * @author zhaodx
	 * @date 2020-08-04 10:06
	 * @param entity
	 * @return
	 */
	@PostMapping("updateDept")
    Result<Object> updateDept(@RequestBody DepartmentEntity entity);
}