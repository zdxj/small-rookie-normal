package com.zdxj.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseController;
import com.zdxj.dto.SystemRoleDTO;
import com.zdxj.feignclient.ManagerRoleRelFeignClient;
import com.zdxj.feignclient.SystemManagerFeignClient;
import com.zdxj.feignclient.SystemRoleFeignClient;
import com.zdxj.model.ManagerRoleRelEntity;
import com.zdxj.model.SystemManagerEntity;
import com.zdxj.model.SystemRoleEntity;
import com.zdxj.util.ToolUtils;
import com.zdxj.utils.ShiroUtils;

@Controller
@RequestMapping("/managerRoleRel")
public class ManagerRoleRelController extends BaseController<ManagerRoleRelFeignClient,ManagerRoleRelEntity>{
	
	public ManagerRoleRelController() {
		super("/managerRoleRel/", "ManagerRoleRel");
	}
	
	@Autowired
	private ManagerRoleRelFeignClient myClient ;
	@Autowired
	private SystemManagerFeignClient systemManagerClient ;
	@Autowired
	private SystemRoleFeignClient systemRoleFeignClient ;
	
	/**
	 * 关联角色
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/selectRole")
	public ModelAndView selectRole(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		SystemManagerEntity entity = null;
		model.addObject("required", true);
		Result<SystemManagerEntity> resultInfo = systemManagerClient.getById(ToolUtils.checkInt(request.getParameter("id")));
		entity = resultInfo.getResult();
		if(entity != null) {
			Gson gson = new Gson();
			model.addObject("loginName", entity.getLoginName());
			//获取所有角色
			Result<List<SystemRoleEntity>> listAll = systemRoleFeignClient.listAll();
			List<SystemRoleEntity> roles = listAll.getResult();
			model.addObject("roles1", gson.toJson(roles));
			if(CollectionUtils.isNotEmpty(roles)) {
				List<SystemRoleDTO> result = Lists.newArrayList();
				// 关联的角色关系
				Result<Set<Integer>> roleIdsByManagerId = myClient.listRoleIdsByManagerId(entity.getId());
				Set<Integer> selectedIds = roleIdsByManagerId.getResult();
				for(SystemRoleEntity role:roles) {
					SystemRoleDTO dto = new SystemRoleDTO();
					dto.setId(role.getId());
					dto.setName(role.getName());
					if(selectedIds != null &&  selectedIds.contains(role.getId())) {
						dto.setSelected(true);
					}
					result.add(dto);
				}
				model.addObject("roles",result);
				model.addObject("roles2",gson.toJson(selectedIds));
			}
		}
		model.addObject("action", "/managerRoleRel/saveSelectRole");
		model.addObject("userId", entity==null?0:entity.getId());
		model.setViewName("/managerRoleRel/SelectRoles");
		return model;
	}
	
	/**
	 * 保存管理员对应角色关系
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/saveSelectRole")
	public @ResponseBody Result<Object> saveSelectRole(@RequestParam("id") Integer managerId ,@RequestParam(value ="roleIds",required =false) List<Integer> roleIds ,HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> maps = Maps.newHashMap();
		Result<SystemManagerEntity> byId = systemManagerClient.getById(managerId);
		SystemManagerEntity manager = byId.getResult();
		if(manager == null || GlobalConstants.SUPER_MANAGER_ID.equals(managerId)) {
			return Result.failed("系统初始管理员无需关联角色");
		}
		maps.put("managerId", managerId);
		maps.put("roleId", roleIds);
		Result<Object> userRelRole = myClient.saveSelectRole(maps);
		if(userRelRole.isOk()) {
			ShiroUtils.clearShiroCache();
		}
		return userRelRole;
	}
}