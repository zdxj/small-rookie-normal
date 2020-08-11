package com.zdxj.constant;

public class SystemCacheConstant {

	 //系统管理缓存前缀
    public static final String SYSTEM_SYSTEM_NAME = "system";
    //系统管理员缓存key-ID
    public static final String SYSTEM_MANAGER = SYSTEM_SYSTEM_NAME+":manager:";
    //系统角色缓存key-ID
    public static final String SYSTEM_ROLE = SYSTEM_SYSTEM_NAME+":role:";
    //系统菜单缓存key-ID
    public static final String SYSTEM_MENU = SYSTEM_SYSTEM_NAME+":menu:";
    //系统管理员和角色关系缓存key-ID
    public static final String MANAGER_ROLE_REL = SYSTEM_SYSTEM_NAME+":manRR:";
    //系统部门缓存key-ID
    public static final String DEPARTMENT = SYSTEM_SYSTEM_NAME+":dept:";
    //系统部门缓存key-ID
    public static final String ROLE_MENU_REL = SYSTEM_SYSTEM_NAME+":roleMR:";
   
}
