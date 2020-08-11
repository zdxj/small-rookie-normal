package com.zdxj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.zdxj.core.controller.BaseController;
import com.zdxj.feignclient.ManagerLogFeignClient;
import com.zdxj.model.ManagerLogEntity;

@Controller
@RequestMapping("/managerLog")
public class ManagerLogController extends BaseController<ManagerLogFeignClient,ManagerLogEntity>{
	
	public ManagerLogController() {
		super("/managerLog/", "ManagerLog");
	}
	
}