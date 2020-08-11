package com.zdxj.feignclient;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.zdxj.core.Result;
import com.zdxj.core.feignclient.BaseFeignClient;
import com.zdxj.hystrix.SystemMenuHystrix;
import com.zdxj.model.SystemMenuEntity;
import com.zdxj.model.ZtreeEntity;

@FeignClient(value = "gateway-server",path="/systemApi/systemMenu", fallback = SystemMenuHystrix.class)
public interface SystemMenuFeignClient extends BaseFeignClient<SystemMenuEntity>{

	/**
	 * 获取url权限
	 * @author zhaodx
	 * @date 2020-03-20 18:51:15
	 * @param maps
	 * @return
	 */
	@PostMapping("listAllPrem")
	Result<Map<String,String>> listAllPrem();
	
	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	@PostMapping("listZtreeMenu")
	Result<List<ZtreeEntity>> listZtreeMenu(@RequestBody Map<String, Object> maps);
	
	/**
	 * 获取管理员关联的菜单权限
	 * @author zhaodx
	 * @date 2020-08-05 15:02
	 * @param userId
	 * @return
	 */
	@PostMapping("listMenusByUserId")
	Result<List<SystemMenuEntity>> listMenusByUserId(@RequestParam("userId") Integer userId);
	
}