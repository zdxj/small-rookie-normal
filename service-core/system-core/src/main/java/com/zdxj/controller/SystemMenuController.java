package com.zdxj.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseServiceController;
import com.zdxj.model.SystemMenuEntity;
import com.zdxj.model.ZtreeEntity;
import com.zdxj.service.SystemMenuService;


@RestController
@RequestMapping("/systemMenu")
public class SystemMenuController extends BaseServiceController<SystemMenuService,SystemMenuEntity>{

	@Autowired
	private SystemMenuService myService ;
	
	/**
	 * 获取url权限
	 * @author zhaodx
	 * @date 2020-03-20 18:51:15
	 * @param maps
	 * @return
	 */
	@PostMapping("listAllPrem")
	Result<Map<String,String>> listAllPrem(){
		Map<String,Object> maps = Maps.newHashMap();
		maps.put("deleted", false);
		maps.put("orderBy", "id,desc");
		List<SystemMenuEntity> list = myService.listByCondition(maps);
		Map<String,String> result = Maps.newHashMap();
		if(CollectionUtils.isEmpty(list)) {
			return Result.success(result);
		}
		for(SystemMenuEntity info:list) {
			if (StringUtils.isBlank(info.getPermUrl()) || StringUtils.isBlank(info.getPermCode())) {
				continue ;
			}
			String[] split = info.getPermUrl().split(",");
			if(split == null || split.length<=0) {
				continue ;
			}
			for(String url:split) {
				if(result.containsKey(url)) {
					result.put(url, result.get(url)+"|"+info.getPermCode());
				}else {
					result.put(url,info.getPermCode());
				}
			}
		}
		return Result.success(result);
	}
	
	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	@PostMapping("listZtreeMenu")
	Result<List<ZtreeEntity>> listZtreeMenu(@RequestBody Map<String, Object> maps){
		return Result.success(myService.listZtreeMenu(maps));
	}
	
	/**
	 * 获取管理员关联的菜单权限
	 * @author zhaodx
	 * @date 2020-08-05 15:02
	 * @param userId
	 * @return
	 */
	@PostMapping("listMenusByUserId")
	Result<List<SystemMenuEntity>> listMenusByUserId(@RequestParam("userId") Integer userId){
		return Result.success(myService.listMenusByUserId(userId));
	}
}
