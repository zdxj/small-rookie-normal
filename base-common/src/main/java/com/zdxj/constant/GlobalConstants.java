package com.zdxj.constant;

/**
 * @Author zhaodx
 * @Date 2019/6/22 17:52
 */
public class GlobalConstants {
	
    /* 返回结果成功编码*/
    public static final Integer SUCCESS = 200;

    /* 返回结果失败编码*/
    public static final Integer FAILURE = 500;

    /* 错误编码：401未登录*/
    public static final Integer NOT_LOGIN = 401;

    /* 错误编码：403无权限*/
    public static final Integer ERROR_403 = 403;

    /* 系统默认超级管理员ID*/
    public static final Integer SUPER_MANAGER_ID= 1 ;
    
    /* 系统默认超级管理员角色ID*/
    public static final Integer SUPER_ROLE_ID= 1 ;
    
    /* 系统默认部门ID*/
    public static final Integer SYSTEM_DEPT_ID= 1 ;

    /* 是*/
    public static final Integer YES = 1 ;
    
    /* 否*/
    public static final Integer NO = 0 ;

    /* 登录方式-自定义*/
    public static final String OAUTH_GRANT_TYPE_CUSTOM="custom";
    
    /* 系统权限集合*/
    public static final String RESOURCE_ROLES_MAP = "AUTH:RESOURCE_ROLES_MAP";
    
    /* 系统权限角色前缀*/
    public static final String AUTHORITY_PREFIX = "ROLE_";
    
    public static final String AUTHORITY_CLAIM_NAME = "authorities";
    
    /* 系统菜单顶级分类id*/
    public static final Integer MENU_ROOT_ID = 0 ;
    
    /* 后台登录最大错误次数*/
    public static final Integer MAX_LOGIN_ERROR_COUNT = 5 ;
    
    /* 图片验证码key*/
    public static final String PICTURE_VERIFICATION_CODE_KEY = "PICTURE_CODE_KEY" ;
    
    /* 后台管理员key*/
    public static final String SESSION_MANAGER_KEY = "SESSION_MANAGER_KEY" ;
    
    /* 系统设置缓存key*/
    public static final String SYSTEN_CONFIG_KEY = "system:congfig:" ;
}
