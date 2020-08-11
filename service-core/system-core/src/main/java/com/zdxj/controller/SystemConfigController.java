package com.zdxj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zdxj.core.controller.BaseServiceController;
import com.zdxj.model.SystemConfigEntity;
import com.zdxj.service.SystemConfigService;


@RestController
@RequestMapping("/systemConfig")
public class SystemConfigController extends BaseServiceController<SystemConfigService,SystemConfigEntity>{

}
