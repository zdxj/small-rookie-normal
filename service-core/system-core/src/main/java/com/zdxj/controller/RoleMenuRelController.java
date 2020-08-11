package com.zdxj.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseServiceController;
import com.zdxj.model.RoleMenuRelEntity;
import com.zdxj.service.RoleMenuRelService;


@RestController
@RequestMapping("/roleMenuRel")
public class RoleMenuRelController extends BaseServiceController<RoleMenuRelService,RoleMenuRelEntity>{

	@Autowired
	private RoleMenuRelService myService ;
	
	/**
	 * 初始化角色和权限的对应关系
	 * @author zhaodx
	 * @date 2020-07-20 18:13
	 * @return
	 */
	@GetMapping("initRoleMenuRel")
    Result<Object> initRoleMenuRel(){
		return myService.initRoleMenuRel(true);
	}
	
	/**
	 * 根据角色id获取所有权限编码
	 * @author zhaodx
	 * @date 2020-07-30 13:26
	 * @param roleList
	 * @return
	 */
	@PostMapping("listUserPermCodesByRoleIds")
	Result<Set<String>> listUserPermCodesByRoleIds(@RequestBody List<Integer> roleList){
		return Result.success(myService.listUserPermCodesByRoleIds(roleList));
	}
}
