package com.zdxj.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
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
import com.zdxj.feignclient.DepartmentFeignClient;
import com.zdxj.feignclient.SystemManagerFeignClient;
import com.zdxj.model.DepartmentEntity;
import com.zdxj.model.SystemManagerEntity;
import com.zdxj.model.ZtreeEntity;
import com.zdxj.util.ToolUtils;

@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController<DepartmentFeignClient,DepartmentEntity>{
	
	public DepartmentController() {
		super("/department/", "Department");
	}
	
	@Autowired
	private DepartmentFeignClient myClient ;
	@Autowired
	private SystemManagerFeignClient systemManagerFeignClient ;
	
	@Override
	@GetMapping("/add")
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("action", "/department/doAdd");
		model.setViewName("/department/DepartmentAdd");
		//菜单父ID
		Integer id = ToolUtils.checkInt(request.getParameter("id"));
		DepartmentEntity entity = new DepartmentEntity();
		entity.setParentId(GlobalConstants.SYSTEM_DEPT_ID);
		if(id != 0) {
			Result<DepartmentEntity> byId = myClient.getById(id);
			DepartmentEntity result = byId.getResult();
			if(result != null) {
				entity.setParentId(result.getId());
				entity.setParentName(result.getName());
			}
		}
		model.addObject("entity", new Gson().toJson(entity));
		return model ;
	}
	
	@Override
	public DepartmentEntity initEdit(ModelAndView model, DepartmentEntity entity) {
		Result<DepartmentEntity> byId = myClient.getById(entity.getParentId());
		DepartmentEntity result = byId.getResult();
		if(result != null) {
			entity.setParentName(result.getName());
		}
		return entity;
	}
	
	
	@Override
	public String checkData(DepartmentEntity entity, HttpServletRequest request) {
		if(null != entity.getId() && entity.getId().equals(entity.getParentId())) {
			return "上级部门不能是自己";
		}
		return null;
	}
	
	@Override
	public String checkDel(List<Integer> ids) {
		Map<String,Object> maps = Maps.newHashMap();
		for(Integer id:ids) {
			if(GlobalConstants.SYSTEM_DEPT_ID.equals(id)) {
				return "系统初始部门，无法删除";
			}
			maps.put("parentId", id);
			Result<List<DepartmentEntity>> listByConditionWithPage = myClient.listByConditionWithPage(maps, 0, 1);
			if(!GlobalConstants.SUCCESS.equals(listByConditionWithPage.getCode()) || CollectionUtils.isNotEmpty(listByConditionWithPage.getResult())) {
				return "该部门下含有子部门，无法删除";
			}
			maps.clear();
			maps.put("departmentId", id);
			Result<List<SystemManagerEntity>> listByConditionWithPage2 = systemManagerFeignClient.listByConditionWithPage(maps, 0, 1);
			if(!GlobalConstants.SUCCESS.equals(listByConditionWithPage2.getCode()) || CollectionUtils.isNotEmpty(listByConditionWithPage2.getResult())) {
				return "该部门下含有用户，无法删除";
			}
		}
		return null;
	}
	
	
	/**
	 * 更新部门
	 * @author zhaodx
	 * @date 2020-08-04 10:05
	 */
	@Override
	@PostMapping(value = "/doEdit", produces = "application/json; charset=utf-8")
	public Result<Object> doEdit(@Validated DepartmentEntity entity, HttpServletRequest request, HttpServletResponse response) {
		String errorMsg = this.checkData(entity,request);
		if (StringUtils.isNotBlank(errorMsg)) {
			return Result.failed(errorMsg);
		}
		return myClient.updateDept(entity);
	}
	
	/**
	 * 菜单树形列表
	 * @author zhaodx
	 * @date 2020-08-03 09:33
	 */
	@PostMapping(value = "/queryTreeList", produces = "application/json; charset=utf-8")
	public @ResponseBody Result<List<DepartmentEntity>> queryTreeList(@RequestParam Map<String, Object> maps, HttpServletRequest request, HttpServletResponse response) {
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
	 * @param excludeId 排除ID
	 * @return
	 */
    @GetMapping("/getById")
    public ModelAndView getById(@RequestParam("id") Integer id,@RequestParam(value="excludeId",required = false) Integer excludeId){
    	ModelAndView model = new ModelAndView();
    	model.setViewName("/department/DeptTree");
    	Result<DepartmentEntity> byId = myClient.getById(id);
    	model.addObject("dept", byId.getResult());
    	model.addObject("excludeId", excludeId==null?0:excludeId);
        return model;
    }
    
    /**
     * 获取菜单分类树
     * @author zhaodx
     * @date 2020-08-03 11:48
     * @param request
     * @param response
     * @param excludeId 排除ID
     * @return
     */
    @GetMapping("/listTreeDept")
	public @ResponseBody List<ZtreeEntity> listTreeDept(@RequestParam(value="excludeId",required = false) Integer excludeId,HttpServletRequest request, HttpServletResponse response) {
    	Map<String,Object> maps = Maps.newHashMap();
    	maps.put("orderBy", "parent_id,asc;position,asc");
    	maps.put("excludeId", excludeId);
		return myClient.listTreeDept(maps).getResult();
	}
}