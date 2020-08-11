package com.zdxj.hystrix;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zdxj.core.Result;
import com.zdxj.core.hystrix.BaseHystrix;
import com.zdxj.feignclient.SystemMenuFeignClient;
import com.zdxj.model.SystemMenuEntity;
import com.zdxj.model.ZtreeEntity;

@Component
public class SystemMenuHystrix extends BaseHystrix<SystemMenuEntity> implements SystemMenuFeignClient {

	/**
	 * 获取url权限
	 * @author zhaodx
	 * @date 2020-03-20 18:51:15
	 * @param maps
	 * @return
	 */
	@Override
	public Result<Map<String, String>> listAllPrem() {
		return Result.failed();
	}

	/**
	 * 获取树形结构分类
	 * @author zhaodx
	 * @date 2020-08-03 11:40
	 * @param maps
	 * @return
	 */
	@Override
	public Result<List<ZtreeEntity>> listZtreeMenu(Map<String, Object> maps) {
		return Result.failed();
	}
	
	/**
	 * 获取管理员关联的菜单权限
	 * @author zhaodx
	 * @date 2020-08-05 15:02
	 * @param userId
	 * @return
	 */
	@Override
	public Result<List<SystemMenuEntity>> listMenusByUserId(Integer userId) {
		return Result.failed();
	}
}
