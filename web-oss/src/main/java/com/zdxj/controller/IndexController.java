package com.zdxj.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.zdxj.constant.GlobalConstants;
import com.zdxj.core.Result;
import com.zdxj.dto.SystemMenuDTO;
import com.zdxj.feignclient.SystemMenuFeignClient;
import com.zdxj.model.SystemMenuEntity;
import com.zdxj.utils.CommonService;
import com.zdxj.utils.TreeUtil;

@Controller
@RequestMapping("/")
public class IndexController {


	@Autowired
	private CommonService commonService;
	@Autowired
	private SystemMenuFeignClient systemMenuFeignClient;
	
	/**
	 * 首页
	 * @author zhaodx
	 * @date 2020-07-29 10:07
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@GetMapping("")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws Exception{
		ModelAndView model = new ModelAndView();
		model.setViewName("index");
		Subject currentUser = SecurityUtils.getSubject();
		List<SystemMenuEntity> result = null ;
		if(currentUser.hasRole("ROLE_"+GlobalConstants.SUPER_ROLE_ID)) {
			//菜单
			Map<String,Object> maps = Maps.newHashMap(); 
			maps.put("notEqPermType", SystemMenuEntity.premTypeButton);
			maps.put("orderBy", "position,asc;id,desc");
			maps.put("deleted", false);
			Result<List<SystemMenuEntity>> listByCondition = systemMenuFeignClient.listByCondition(maps);
			result = listByCondition.getResult();
		}else {
			Result<List<SystemMenuEntity>> listMenusByUserId = systemMenuFeignClient.listMenusByUserId(commonService.getLoginUser().getId());
			result = listMenusByUserId.getResult();
		}
		// 所有分类菜单
		List<SystemMenuDTO> treeList = TreeUtil.getTreeList(result,null,null);
		model.addObject("menuList", treeList);
		return model ;
	}

	/**
	 * 控制台
	 * @param request
	 * @param session
	 * @return
	 */
	@GetMapping("main")
	public ModelAndView dashboard(HttpServletRequest request,HttpSession session) {
		
		ModelAndView model = new ModelAndView("/main");
		return model;
	}

	/**
	 * 切换主题
	 * @author zhaodx
	 * @date 2020-08-05 14:31
	 * @param mmap
	 * @return
	 */
    @GetMapping("switchSkin")
    public ModelAndView switchSkin(HttpServletRequest request,HttpSession session){
    	ModelAndView model = new ModelAndView("/skin");
		return model;
    }
    
	/**
	 * 无权限页面
	 * @return
	 */
	@GetMapping("/403")  
    public String unauthorizedRole(){
        return "error/Unauthorized";
    }
    
}
