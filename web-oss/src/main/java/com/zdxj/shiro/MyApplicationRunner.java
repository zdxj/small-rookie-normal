package com.zdxj.shiro;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.zdxj.core.Result;
import com.zdxj.feignclient.SystemMenuFeignClient;


/**
 * 由于在启动中无法初始化全部权限，故使用此方法在项目启动完毕后进行初始化
 * 在项目启动完毕后初始化权限
 * @author:zhaodx	
 * @date:2018-11-01 15:31
 */
@Component
public class MyApplicationRunner implements ApplicationRunner{

	private static Logger logger = LoggerFactory.getLogger(MyApplicationRunner.class);
	
	@Autowired
	private SystemMenuFeignClient menuFeignClient ;
	@Autowired
	private ShiroFilterFactoryBean myShiroFilterFactoryBean ;
	public static final String PREMISSION_STRING = "perms[\"{0}\"]";
	@Override
    public void run(ApplicationArguments var1) throws Exception{
        AbstractShiroFilter shiroFilter = null;
        try {  
            shiroFilter = (AbstractShiroFilter) myShiroFilterFactoryBean.getObject();

            PathMatchingFilterChainResolver resolver = (PathMatchingFilterChainResolver) shiroFilter.getFilterChainResolver();
            // 过滤管理器  
            DefaultFilterChainManager manager = (DefaultFilterChainManager) resolver.getFilterChainManager();
            // 清除权限配置  
            manager.getFilterChains().clear();
            myShiroFilterFactoryBean.getFilterChainDefinitionMap().clear();  
            // 重新设置权限  
            Map<String, String> chains = myShiroFilterFactoryBean.getFilterChainDefinitionMap();  
            myShiroFilterFactoryBean.setLoginUrl("/login");
            myShiroFilterFactoryBean.setUnauthorizedUrl("/403");
            //重新生成过滤链  
            if (chains != null) {
            	Result<Map<String, String>> listAllPrem = menuFeignClient.listAllPrem();
            	Map<String, String> result = listAllPrem.getResult();
            	if(MapUtils.isNotEmpty(result)) {
            		logger.info("=======================>>>获取权限个数为:"+result.size());
            		for(Map.Entry<String, String> entry : result.entrySet()){
            		    manager.createChain(entry.getKey(), MessageFormat.format(PREMISSION_STRING, entry.getValue()));
            		}
            	}
            }
            //默认权限
            for (DefaultPermissionEnum spe : DefaultPermissionEnum.values()) {
            	manager.createChain(spe.getName(),spe.getValue());
    		}
        } catch (Exception e) {  
        	logger.error("初始化权限错误:",e);
        }  
    }
}
