package com.zdxj.core.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.core.DataTableInfo;
import com.zdxj.core.Page;
import com.zdxj.core.Result;
import com.zdxj.core.entity.BaseEntity;
import com.zdxj.core.feignclient.BaseFeignClient;
import com.zdxj.util.ToolUtils;


public class BaseController<M extends BaseFeignClient<T>,T extends BaseEntity> {

	@Autowired
	private M myClient ;
	
	public M getMyClient() {
		return myClient;
	}

	//模块页面路径
	private String modulePath = "";
	//模块页面名称
	private String moduleName = "";

	public BaseController(String modulePath, String moduleName) {
		this.modulePath = modulePath;
		this.moduleName = moduleName;
	}
	
	/**
	 * 跳转至列表页
	 */
	@GetMapping("/list")
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.setViewName(modulePath+moduleName+"List");
		this.initList(model, request);
		return model;
	}
	
	/**
	 * 列表搜索
	 * @param maps
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/queryList", produces = "application/json; charset=utf-8")
	public @ResponseBody DataTableInfo queryList(@RequestParam Map<String,Object> maps ,HttpServletRequest request,HttpServletResponse response) {
		int pageNum = ToolUtils.checkInt((String)maps.get("pageNum"));
		int pageSize = ToolUtils.checkInt((String)maps.get("pageSize"));
		String sortName = ToolUtils.checkStr((String)maps.get("orderByColumn"));
		String sortAsc = ToolUtils.checkStr((String)maps.get("isAsc"));
		Map<String,Object> params = Maps.newHashMap();
		maps.forEach((key, value) -> {
			if("pageNum".equals(key) || "pageSize".equals(key) || "orderByColumn".equals(key) || "isAsc".equals(key)) {
				 return ;
			}
			if(StringUtils.isNotBlank(value.toString())) {
				params.put(key, ToolUtils.checkSearchStr(value.toString()));
			}
        });
		params.put("orderBy", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortName) + "," + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortAsc));
		Result<Page<List<T>>> resultInfo = myClient.listByPage(params, (pageNum-1)*pageSize, pageSize);
		Page<List<T>> pageResult = resultInfo.getResult();
		DataTableInfo result = new DataTableInfo();
		if(pageResult != null) {
			result.setCode(resultInfo.getCode());
			result.setMsg(resultInfo.getMsg());
			List<?> records = this.handleListResult(pageResult.getRecords());
			result.setRows(records);
			result.setTotal(pageResult.getTotalRecords());
		}else {
			result.setCode(GlobalConstants.FAILURE);
			result.setMsg("查询错误，请稍后重试");
		}
		return result ;
		
	}
	
	/**
	 * 跳转至添加页面
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping("/add")
	public ModelAndView add(HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		model.addObject("action", modulePath+"doAdd");
		model.setViewName(modulePath+moduleName+"Add");
		this.initAddAndEdit(model,null);
		return model ;
	}
	
	/**
	 * 保存
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/doAdd", produces = "application/json; charset=utf-8")
	public @ResponseBody Result<Object> doAdd(@Validated T entity,HttpServletRequest request,HttpServletResponse response) {
		String errorMsg = this.checkData(entity,request);
		if (StringUtils.isNotBlank(errorMsg)) {
			return Result.failed(errorMsg);
		}
		return myClient.saveEntity(entity);
	}
	
	
	/**
	 * 跳转至编辑页面
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/edit")
	public ModelAndView edit(@RequestParam("id") Integer id ,HttpServletRequest request,HttpServletResponse response) {
		ModelAndView model = new ModelAndView();
		Result<T> result = myClient.getById(id);
		T info = result.getResult();
		model.addObject("action", modulePath+"doEdit");
		model.setViewName(modulePath+moduleName+"Add");
		T entity = this.initAddAndEdit(model,info);
		Gson gson = new Gson();
		model.addObject("entity", gson.toJson(entity));
		return model;
	}
	
	
	/**
	 * 更新
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/doEdit", produces = "application/json; charset=utf-8")
	public @ResponseBody Result<Object> doEdit(@Validated T entity,HttpServletRequest request,HttpServletResponse response) {
		String errorMsg = this.checkData(entity,request);
		if (StringUtils.isNotBlank(errorMsg)) {
			return Result.failed(errorMsg);
		}
		return myClient.updateEntity(entity);
	}
	
	
	/**
	 * 查看数据
	 * @param request
	 * @param response
	 * @return
	 */
	@GetMapping(value = "/view")
	public ModelAndView view(@RequestParam("id") Integer id,HttpServletRequest request,HttpServletResponse response) {
		
		ModelAndView model = new ModelAndView();
		model.setViewName(modulePath+moduleName+"View");
		T entity = null ;
		if(id != null) {
			Result<T> result = myClient.getById(id);
			entity = result.getResult();
		}
		entity = this.handleView(entity, model);
		model.addObject("entity", entity);
		return model;
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/removeByIds")
	public @ResponseBody Result<Object> removeByIds(@RequestParam("ids") List<Integer> ids,HttpServletRequest request) {
		if(CollectionUtils.isEmpty(ids)) {
			return Result.failed("请选择要操作的数据");
		}
		String errorMsg = this.checkDel(ids);
		if (StringUtils.isNotBlank(errorMsg)) {
			return Result.failed(errorMsg);
		}
		return myClient.removeByIds(ids);
	}
	
	/**
	 * 校验数据
	 * @param maps
	 * @return
	 */
	public String checkData(T entity,HttpServletRequest request) {
		return null;
	}
	
	/**
	 * 删除校验
	 * @param entity
	 * @return
	 */
	public String checkDel(List<Integer> ids) {
		return null;
	}
	
	/**
	 * 处理查看页面
	 * @param entity
	 * @param model
	 */
	public T handleView(T entity,ModelAndView model) {
		return entity ;
	}
	
	/**
	 * 增加和修改时初始化数据
	 * @param model
	 */
	public T initAddAndEdit(ModelAndView model,T entity) {
		this.initAdd(model);
		return this.initEdit(model,entity);
	}
	
	/**
	 * 增加时初始化数据
	 * @param model
	 */
	public void initAdd(ModelAndView model) {}
	
	/**
	 * 修改时初始化数据
	 * @param model
	 */
	public T initEdit(ModelAndView model,T entity) {
		return entity ;
	}
	
	/**
	 * 进入列表时初始化数据
	 * @param model
	 */
	public void initList(ModelAndView model,HttpServletRequest request) {}
	
	/**
	 * 处理列表搜索结果
	 * @param records
	 * @return
	 */
	public List<?> handleListResult(List<T> records) {
		return records;
	}
}
