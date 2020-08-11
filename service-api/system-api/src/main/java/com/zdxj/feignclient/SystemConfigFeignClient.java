package com.zdxj.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import com.zdxj.core.feignclient.BaseFeignClient;
import com.zdxj.hystrix.SystemConfigHystrix;
import com.zdxj.model.SystemConfigEntity;

@FeignClient(value = "gateway-server",path="/systemApi/systemConfig", fallback = SystemConfigHystrix.class)
public interface SystemConfigFeignClient extends BaseFeignClient<SystemConfigEntity>{

}