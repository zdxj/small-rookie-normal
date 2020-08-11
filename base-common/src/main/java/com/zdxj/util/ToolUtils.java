package com.zdxj.util;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ToolUtils {

    /**
     * 处理不安全的字符
     * @param str
     * @return
     */
    public static String checkStr(String str){
        if(StringUtils.isBlank(str)){
            return str ;
        }
        str = str.replaceAll(">", "＞");
        str = str.replaceAll("<", "＜");
        str = str.replaceAll("\'", "＇");
        str = str.replaceAll("\"", "＂");
        str = str.replaceAll("&", "＆");
        str = str.replaceAll("#", "＃");
        return str ;
    }
    /**
     * 处理搜索时不安全的字符
     * @param str
     * @return
     */
    public static String checkSearchStr(String str){
        if(StringUtils.isBlank(str)){
            return str ;
        }
        str = str.replaceAll(">", "＞");
        str = str.replaceAll("<", "＜");
        str = str.replaceAll("\'", "＇");
        str = str.replaceAll("\"", "＂");
        str = str.replaceAll("&", "＆");
        str = str.replaceAll("#", "＃");
        str = str.replaceAll("%", "/%");
        str = str.replaceAll("_", "/_");
        str = str.replaceAll("/", "//");
        return str ;
    }
    
    /**
     * 转换成int
     * @author zhaodx
     * @date 2020-07-27 17:11
     * @param str
     * @return
     */
    public static int checkInt(String str) {
    	
    	if(isInt(str)) {
    		return Integer.valueOf(str);
    	}
    	return 0 ;
	}
    
    /**
     * 是否是int型
     * @author zhaodx
     * @date 2020-07-27 17:11
     * @param str
     * @return
     */
    public static boolean isInt(String str) {
    	if(StringUtils.isBlank(str)) {
    		return false ;
    	}
    	Pattern pattern = Pattern.compile("^[+-]?\\d+$", Pattern.CASE_INSENSITIVE);
    	return pattern.matcher(str).matches();
	}
}
