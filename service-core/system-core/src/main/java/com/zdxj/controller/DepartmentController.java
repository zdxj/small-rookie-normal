package com.zdxj.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseServiceController;
import com.zdxj.model.DepartmentEntity;
import com.zdxj.model.ZtreeEntity;
import com.zdxj.service.DepartmentService;


@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseServiceController<DepartmentService,DepartmentEntity>{

	@Autowired
	private DepartmentService myService ;
	
	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	@PostMapping("listTreeDept")
	Result<List<ZtreeEntity>> listTreeDept(@RequestBody Map<String, Object> maps){
		return Result.success(myService.listTreeDept(maps));
	}
	
	/**
	 * 更新部门
	 * @author zhaodx
	 * @date 2020-08-04 10:06
	 * @param entity
	 * @return
	 */
	@PostMapping("updateDept")
    Result<Object> updateDept(@RequestBody DepartmentEntity entity){
		return Result.success(myService.updateDept(entity));
	}
}
