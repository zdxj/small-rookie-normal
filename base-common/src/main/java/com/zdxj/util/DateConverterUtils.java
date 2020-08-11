package com.zdxj.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import java.util.Date;
import java.util.Map;

/**
 * 解决map转换bean时日期格式转换错误问题
 * @author zdx
 * @2020-6-26 11:13:49
 *
 */
public class DateConverterUtils {

	private DateConverterUtils(){}

    public static void populate(Object bean, Map<String, String> map){

        try {
            //处理时间格式
            DateConverter dateConverter = new DateConverter();
            //设置日期格式
            dateConverter.setPatterns(new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss.SSS"});
            //注册格式
            ConvertUtils.register(dateConverter, Date.class);
            //封装数据
            BeanUtils.populate(bean, map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
