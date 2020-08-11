package com.zdxj.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseServiceController;
import com.zdxj.model.ManagerRoleRelEntity;
import com.zdxj.service.ManagerRoleRelService;


@RestController
@RequestMapping("/managerRoleRel")
public class ManagerRoleRelController extends BaseServiceController<ManagerRoleRelService,ManagerRoleRelEntity>{

	@Autowired
	private ManagerRoleRelService myService ;
	
	
	/**
	 * 获取管理员角色ID
	 * @author zhaodx
	 * @date 2020-07-17 17:43
	 * @param id
	 * @return
	 */
	@GetMapping("listRoleIdByManagerId")
    Result<List<Integer>> listRoleIdByManagerId(@RequestParam("id") Integer managerId){
		Map<String,Object> maps = Maps.newHashMap();
		maps.put("managerId", managerId);
		List<ManagerRoleRelEntity> listByCondition = myService.listByCondition(maps);
		if(CollectionUtils.isEmpty(listByCondition)) {
			return Result.success();
		}
		List<Integer> ids = Lists.newArrayList();
		for(ManagerRoleRelEntity info:listByCondition) {
			ids.add(info.getRoleId());
		}
		return Result.success(ids);
	}
	
	/**
	 * 根据管理员ID获取管理员角色ID
	 * @author zhaodx
	 * @date 2018年12月4日
	 * @param @param id
	 * @param @return
	 * @return ResultInfo<Set<Integer>>
	 */
	@GetMapping("listRoleIdsByManagerId")
	Result<Set<Integer>> listRoleIdsByManagerId(@RequestParam("id") Integer id){
		return myService.listRoleIdsByManagerId(id);
	}
	
	/**
	 * 保存管理员对应角色关系
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("saveSelectRole")
	Result<Object> saveSelectRole(@RequestBody Map<String, Object> maps){
		return myService.saveSelectRole(maps);
	}
}
