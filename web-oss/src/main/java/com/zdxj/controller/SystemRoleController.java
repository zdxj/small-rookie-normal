package com.zdxj.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseController;
import com.zdxj.feignclient.SystemMenuFeignClient;
import com.zdxj.feignclient.SystemRoleFeignClient;
import com.zdxj.model.SystemRoleEntity;
import com.zdxj.model.ZtreeEntity;
import com.zdxj.util.ToolUtils;
import com.zdxj.utils.ShiroUtils;

@Controller
@RequestMapping("/systemRole")
public class SystemRoleController extends BaseController<SystemRoleFeignClient,SystemRoleEntity>{
	
	public SystemRoleController() {
		super("/systemRole/", "SystemRole");
	}
	
	@Autowired
	private SystemRoleFeignClient myClient ;
	@Autowired
	private SystemMenuFeignClient systemMenuFeignClient ;
	
	/**
	 * 更新角色信息
	 * @author zhaodx
	 * @date 2020-08-04 13:57
	 */
	@Override
	@PostMapping("doEdit")
	public @ResponseBody Result<Object> doEdit(@Validated SystemRoleEntity entity, HttpServletRequest request, HttpServletResponse response) {
		String errorMsg = this.checkData(entity,request);
		if (StringUtils.isNotBlank(errorMsg)) {
			return Result.failed(errorMsg);
		}
		Result<Object> updateRole = myClient.updateRole(entity);
		if(updateRole.isOk()) {
			ShiroUtils.clearShiroCache();
		}
		return updateRole;
	}
	
	/**
     * 获取菜单分类树
     * @author zhaodx
     * @date 2020-08-03 11:48
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/listSelectMenu")
	public @ResponseBody List<ZtreeEntity> listZtreeMenu(HttpServletRequest request, HttpServletResponse response) {
    	Map<String,Object> maps = Maps.newHashMap();
    	maps.put("orderBy", "parent_id,asc;position,asc");
    	//角色id
    	int roleId = ToolUtils.checkInt(request.getParameter("roleId"));
    	maps.put("roleId", roleId);
    	// 是否只展示选中的
    	maps.put("isOnlyShowSelect", true);
		return systemMenuFeignClient.listZtreeMenu(maps).getResult();
	}
}