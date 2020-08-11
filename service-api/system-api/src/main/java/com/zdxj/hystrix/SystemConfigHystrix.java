package com.zdxj.hystrix;

import org.springframework.stereotype.Component;
import com.zdxj.core.hystrix.BaseHystrix;
import com.zdxj.feignclient.SystemConfigFeignClient;
import com.zdxj.model.SystemConfigEntity;

@Component
public class SystemConfigHystrix extends BaseHystrix<SystemConfigEntity> implements SystemConfigFeignClient {

}
