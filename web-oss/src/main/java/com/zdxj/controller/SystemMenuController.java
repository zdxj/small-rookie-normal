package com.zdxj.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.core.Result;
import com.zdxj.core.controller.BaseController;
import com.zdxj.feignclient.SystemMenuFeignClient;
import com.zdxj.model.SystemMenuEntity;
import com.zdxj.model.ZtreeEntity;
import com.zdxj.util.ToolUtils;

@Controller
@RequestMapping("/systemMenu")
public class SystemMenuController extends BaseController<SystemMenuFeignClient,SystemMenuEntity>{
	
	public SystemMenuController() {
		super("/systemMenu/", "SystemMenu");
	}
	
	@Autowired
	private SystemMenuFeignClient myClient ;
	
	
	@Override
	@GetMapping("/add")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("action", "/systemMenu/doAdd");
		model.setViewName("/systemMenu/SystemMenuAdd");
		//菜单父ID
		Integer id = ToolUtils.checkInt(request.getParameter("id"));
		SystemMenuEntity entity = new SystemMenuEntity();
		entity.setParentId(0);
		entity.setParentName("根目录");
		if(id != 0) {
			Result<SystemMenuEntity> byId = myClient.getById(id);
			SystemMenuEntity result = byId.getResult();
			if(result != null) {
				entity.setParentId(result.getId());
				entity.setParentName(result.getMenuName());
			}
		}
		model.addObject("entity", new Gson().toJson(entity));
		return model ;
	}
	
	
	@Override
	public String checkData(SystemMenuEntity entity, HttpServletRequest request) {
		//新增时校验权限编码
		if(entity.getId()== null) {
			if(StringUtils.isBlank(entity.getPermCode())) {
				return "请输入权限编码";
			}
			Map<String,Object> checkMaps = Maps.newHashMap();
			checkMaps.put("permCode", ToolUtils.checkStr(entity.getPermCode()));
			Result<List<SystemMenuEntity>> listByCondition = myClient.listByCondition(checkMaps);
			if(GlobalConstants.FAILURE.equals(listByCondition.getCode()) || CollectionUtils.isNotEmpty(listByCondition.getResult())) {
				return "权限编码已存在";
			}
		}
		return null ;
	}
	
	@Override
	public SystemMenuEntity initEdit(ModelAndView model, SystemMenuEntity entity) {
		Result<SystemMenuEntity> byId = myClient.getById(entity.getParentId());
		SystemMenuEntity result = byId.getResult();
		entity.setParentName("根目录");
		if(result != null) {
			entity.setParentName(result.getMenuName());
		}
		return entity;
	}
	
	@Override
	public SystemMenuEntity handleView(SystemMenuEntity entity, ModelAndView model) {
		if(entity != null) {
			Result<SystemMenuEntity> byId = myClient.getById(entity.getId());
			SystemMenuEntity result = byId.getResult();
			model.addObject("parentName", result==null?"根目录":result.getMenuName());
		}
		return entity;
	}
	
	/**
	 * 菜单树形列表
	 * @author zhaodx
	 * @date 2020-08-03 09:33
	 */
	@PostMapping(value = "/queryTreeList", produces = "application/json; charset=utf-8")
	public @ResponseBody Result<List<SystemMenuEntity>> queryTreeList(@RequestParam Map<String, Object> maps, HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> params = Maps.newHashMap();
		maps.forEach((key, value) -> {
			if("pageNum".equals(key) || "pageSize".equals(key) || "orderByColumn".equals(key) || "isAsc".equals(key)) {
				 return ;
			}
			if(StringUtils.isNotBlank(value.toString())) {
				params.put(key, ToolUtils.checkSearchStr(value.toString()));
			}
        });
		params.put("orderBy", "parent_id,asc;position,asc");
		return myClient.listByCondition(params);
	}
	
	/**
	 * 选择菜单树
	 * @author zhaodx
	 * @date 2020-08-03 10:51
	 * @param id
	 * @return
	 */
    @GetMapping("/getById")
    public ModelAndView getById(@RequestParam("id") Integer id){
    	ModelAndView model = new ModelAndView();
    	model.setViewName("/systemMenu/MenuTree");
    	Result<SystemMenuEntity> byId = myClient.getById(id);
    	model.addObject("menu", byId.getResult());
        return model;
    }
    
    /**
     * 获取菜单分类树
     * @author zhaodx
     * @date 2020-08-03 11:48
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/listZtreeMenu")
	public @ResponseBody List<ZtreeEntity> listZtreeMenu(HttpServletRequest request, HttpServletResponse response) {
    	Map<String,Object> maps = Maps.newHashMap();
    	maps.put("orderBy", "parent_id,asc;position,asc");
    	//角色id(角色关联菜单权限时用到)
    	int roleId = ToolUtils.checkInt(request.getParameter("roleId"));
    	if(roleId != 0) {
    		maps.put("roleId", roleId);
    	}
		return myClient.listZtreeMenu(maps).getResult();
	}
}