package com.zdxj.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseServiceController;
import com.zdxj.model.SystemManagerEntity;
import com.zdxj.service.SystemManagerService;


@RestController
@RequestMapping("/systemManager")
public class SystemManagerController extends BaseServiceController<SystemManagerService,SystemManagerEntity>{

	@Autowired
	private SystemManagerService myService ;
	
	/**
	 * 根据用户名获取管理员
	 * @author zhaodx
	 * @date 2020-07-16 18:42
	 * @param loginName
	 * @return
	 */
	@GetMapping("getByLoginName")
    Result<SystemManagerEntity> getByLoginName(@RequestParam("loginName") String loginName){
		return Result.success(myService.getByLoginName(loginName));
	}
	
	/**
	 * 登录
	 * @author zhaodx
	 * @date 2020-07-30 14:20
	 * @param maps
	 * @return
	 */
	@PostMapping("login")
	Result<SystemManagerEntity> login(@RequestBody Map<String,String> maps){
		return myService.login(maps);
	}
}
