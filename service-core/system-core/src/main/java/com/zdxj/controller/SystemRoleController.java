package com.zdxj.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseServiceController;
import com.zdxj.model.SystemRoleEntity;
import com.zdxj.service.SystemRoleService;


@RestController
@RequestMapping("/systemRole")
public class SystemRoleController extends BaseServiceController<SystemRoleService,SystemRoleEntity>{

	@Autowired
	private SystemRoleService myService ;
	
	
	/**
	 * 更新角色
	 * @author zhaodx
	 * @date 2020-08-04 13:21
	 * @param entity
	 * @return
	 */
	@PostMapping("updateRole")
    Result<Object> updateRole(@RequestBody SystemRoleEntity entity){
		return myService.updateRole(entity);
	}
	
	/**
	 * 删除角色
	 * @author zhaodx
	 * @date 2020-08-04 14:28
	 */
	@Override
	@PostMapping("removeByIds")
	public Result<Object> removeByIds(@RequestBody List<Integer> list) {
		return myService.removeByIds(list);
	}
}
