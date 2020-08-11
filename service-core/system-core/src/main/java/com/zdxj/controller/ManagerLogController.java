package com.zdxj.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseServiceController;
import com.zdxj.model.ManagerLogEntity;
import com.zdxj.service.ManagerLogService;


@RestController
@RequestMapping("/managerLog")
public class ManagerLogController extends BaseServiceController<ManagerLogService,ManagerLogEntity>{

	@Autowired
	private ManagerLogService managerLogService ;
	
	/**
	 * 登录错误
	 * @author zhaodx
	 * @date 2020-07-30 14:26
	 * @param maps
	 * @return
	 */
	@GetMapping("loginError")
	Result<Object> loginError(@RequestBody Map<String,String> maps){
		return managerLogService.loginError(maps);
	}
}
