package com.zdxj.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




/**
 * Shiro 配置 Apache Shiro 核心通过 Filter 来实现，就好像SpringMvc 通过DispachServlet 来主控制一样。
 * 既然是使用 Filter 一般也就能猜到，是通过URL规则来进行过滤和权限校验，所以我们需要定义一系列关于URL的规则和访问权限。
 */
@Configuration
public class ShiroConfiguration {

	private final Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);

	public static final String PREMISSION_STRING = "perms[\"{0}\"]";

	@Autowired
	private RedisProperties redis;

	/**
	 * ShiroFilterFactoryBean 处理拦截资源文件问题。 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
	 * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
	 *
	 * Filter Chain定义说明 1、一个URL可以配置多个Filter，使用逗号分隔 2、当设置多个过滤器时，全部验证通过，才视为通过
	 * 3、部分过滤器可指定参数，如perms，roles
	 *
	 */

	@Bean
    public RedisCacheManager redisCacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        // 必须要设置主键名称，shiro-redis 插件用过这个缓存用户信息
        redisCacheManager.setPrincipalIdFieldName("id");
        return redisCacheManager;
    }
	
	/**
	 * RedisSessionDAO shiro sessionDao层的实现 通过redis, 使用的是shiro-redis开源插件
	 * @author zhaodx
	 * @date 2020-08-06 11:49
	 * @return
	 */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
        redisSessionDAO.setExpire(1800);
        return redisSessionDAO;
    }
    
    /**
     * Session ID 生成器
     * @author zhaodx
     * @date 2020-08-06 11:51
     * @return
     */
    @Bean
    public JavaUuidSessionIdGenerator sessionIdGenerator() {
        return new JavaUuidSessionIdGenerator();
    }
    
	private RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(redis.getHost()+":"+redis.getPort());
        redisManager.setTimeout((int)redis.getTimeout().toMillis());
        if(StringUtils.isNotBlank(redis.getPassword())) {
        	redisManager.setPassword(redis.getPassword());
        }
        return redisManager;
    }
	
	// 配置shiro仓库
	@Bean(name = "myShiroRealm")
	public MyShiroRealm myShiroRealm() {
		MyShiroRealm realm = new MyShiroRealm();
		realm.setCacheManager(redisCacheManager());
		realm.setCredentialsMatcher(hashedCredentialsMatcher());
		realm.setCachingEnabled(true);
	    //启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
		realm.setAuthenticationCachingEnabled(false);
		//启用授权缓存，即缓存AuthorizationInfo信息，默认false
		realm.setAuthorizationCachingEnabled(true);
		return realm;
	}
	
	// 把shiro生命周期交给spring boot管理
	@Bean(name = "lifecycleBeanPostProcessor")
	public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	// DefaultAdvisorAutoProxyCreator实现Spring自动代理
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	// 权限认证信息
	@Bean(name = "securityManager")
	public DefaultWebSecurityManager defaultWebSecurityManager(MyShiroRealm realm) {
		System.out.println("shiro~~~~~~~~启动");
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm
		securityManager.setRealm(realm);
		securityManager.setCacheManager(redisCacheManager());
		 // session管理器
        securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

	// shiro核心拦截器
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
		factoryBean.setSecurityManager(securityManager);
		Map<String, Filter> filterMap = new LinkedHashMap<>();
		filterMap.put("perms", customPermissionsAuthorizationFilter());
		filterMap.put("kickout", kickoutSessionControlFilter());
		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		factoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的连接
		factoryBean.setUnauthorizedUrl("/403");
		factoryBean.setFilters(filterMap);
		loadShiroFilterChain(factoryBean);
		logger.info("shiro拦截器工厂类注入成功");
		return factoryBean;
	}

	// 加载ShiroFilter权限控制规则
	private void loadShiroFilterChain(ShiroFilterFactoryBean factoryBean) {
		/** 下面这些规则配置最好配置到配置文件中 */
		Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
		// authc：该过滤器下的页面必须验证后才能访问，它是Shiro内置的一个拦截器
		// anon：它对应的过滤器里面是空的,什么都没做,可以理解为不拦截
		// authc:所有url都必须认证通过才可以访问; anon:所有url都可以匿名访问
		 //默认权限
        for (DefaultPermissionEnum spe : DefaultPermissionEnum.values()) {
        	filterChainMap.put(spe.getName(),spe.getValue());
		}
		factoryBean.setFilterChainDefinitionMap(filterChainMap);
	}

	/**
	 * 密码校验规则HashedCredentialsMatcher 这个类是为了对密码进行编码的 , 防止密码在数据库里明码保存 , 当然在登陆认证的时候 ,
	 * 这个类也负责对form里输入的密码进行编码 处理认证匹配处理器：如果自定义需要实现继承HashedCredentialsMatcher
	 */
	@Bean("hashedCredentialsMatcher")
	public HashedCredentialsMatcher hashedCredentialsMatcher() {
		RetryLimitHashedCredentialsMatcher credentialsMatcher = new RetryLimitHashedCredentialsMatcher();
//		HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
		// 指定加密方式为MD5
		credentialsMatcher.setHashAlgorithmName("MD5");
		// 加密次数
		credentialsMatcher.setHashIterations(50);
		credentialsMatcher.setStoredCredentialsHexEncoded(true);
		return credentialsMatcher;
	}

	/**
     * 并发登录控制
     *
     * @return
     */
    @Bean
    public KickoutSessionControlFilter kickoutSessionControlFilter() {
        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
        //用于根据会话ID，获取会话进行踢出操作的；
        kickoutSessionControlFilter.setSessionManager(sessionManager());
        //使用cacheManager获取相应的cache来缓存用户登录的会话；用于保存用户—会话之间的关系的；
        //是否踢出后来登录的，默认是false；即后者登录的用户踢出前者登录的用户；
        kickoutSessionControlFilter.setKickoutAfter(false);
        //同一个用户最大的会话数，默认1；比如2的意思是同一个用户允许最多同时两个人登录；
        kickoutSessionControlFilter.setMaxSession(1);
        //被踢出后重定向到的地址；
        //这里可以改为登录页（需要清除redis相关用户信息，所以我要跳转退出页）
        kickoutSessionControlFilter.setKickoutUrl("/login?kickout=1");
        return kickoutSessionControlFilter;
    }
    
    /**
     * 会话管理器
     */
    @Bean
    public DefaultWebSessionManager sessionManager(){
    	DefaultWebSessionManager manager = new DefaultWebSessionManager();
    	manager.setSessionDAO(redisSessionDAO());
    	// 加入缓存管理器
        manager.setCacheManager(redisCacheManager());
        // 删除过期的session
        manager.setDeleteInvalidSessions(true);
        // 设置全局session超时时间
        manager.setGlobalSessionTimeout(30 * 60 * 1000);
        // 去掉 JSESSIONID
        manager.setSessionIdUrlRewritingEnabled(false);
        // 是否定时检查session
        manager.setSessionValidationSchedulerEnabled(true);
        return manager;
    }
    
	@Bean
	public CustomPermissionsAuthorizationFilter customPermissionsAuthorizationFilter() {
		return new CustomPermissionsAuthorizationFilter();
	}
	/*
	 * 1.LifecycleBeanPostProcessor，这是个DestructionAwareBeanPostProcessor的子类，负责org.
	 * apache.shiro.util.Initializable类型bean的生命周期的，初始化和销毁。主要是AuthorizingRealm类的子类，
	 * 以及EhCacheManager类。
	 * 2.HashedCredentialsMatcher，这个类是为了对密码进行编码的，防止密码在数据库里明码保存，当然在登陆认证的生活，
	 * 这个类也负责对form里输入的密码进行编码。
	 * 3.ShiroRealm，这是个自定义的认证类，继承自AuthorizingRealm，负责用户的认证和权限的处理，可以参考JdbcRealm的实现。
	 * 4.EhCacheManager，缓存管理，用户登陆成功后，把用户信息和权限信息缓存起来，然后每次用户请求时，放入用户的session中，
	 * 如果不设置这个bean，每个请求都会查询一次数据库。
	 * 5.SecurityManager，权限管理，这个类组合了登陆，登出，权限，session的处理，是个比较重要的类。
	 * 6.ShiroFilterFactoryBean，是个factorybean，为了生成ShiroFilter。它主要保持了三项数据，
	 * securityManager，filters，filterChainDefinitionManager。
	 * 7.DefaultAdvisorAutoProxyCreator，Spring的一个bean，由Advisor决定对哪些类的方法进行AOP代理。
	 * 8.AuthorizationAttributeSourceAdvisor，shiro里实现的Advisor类，
	 * 内部使用AopAllianceAnnotationsAuthorizingMethodInterceptor来拦截用以下注解的方法。
	 */

	public static void main(String[] args) {
		ByteSource salt = ByteSource.Util.bytes("admin");
		String newPs = new SimpleHash("MD5", "FHK12162443", salt, 50).toHex();
		System.out.println(newPs);
	}
}