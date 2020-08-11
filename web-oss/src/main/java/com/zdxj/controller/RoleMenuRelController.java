package com.zdxj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zdxj.core.controller.BaseController;
import com.zdxj.feignclient.RoleMenuRelFeignClient;
import com.zdxj.model.RoleMenuRelEntity;

@Controller
@RequestMapping("/roleMenuRel")
public class RoleMenuRelController extends BaseController<RoleMenuRelFeignClient,RoleMenuRelEntity>{
	
	public RoleMenuRelController() {
		super("/roleMenuRel/", "RoleMenuRel");
	}
	
}