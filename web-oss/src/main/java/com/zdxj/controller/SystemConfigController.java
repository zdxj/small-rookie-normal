package com.zdxj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zdxj.core.controller.BaseController;
import com.zdxj.feignclient.SystemConfigFeignClient;
import com.zdxj.model.SystemConfigEntity;

@Controller
@RequestMapping("/systemConfig")
public class SystemConfigController extends BaseController<SystemConfigFeignClient,SystemConfigEntity>{
	
	public SystemConfigController() {
		super("/systemConfig/", "SystemConfig");
	}
	
}