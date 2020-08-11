package com.zdxj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zdxj.interceptor.RefererInterceptor;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
    @Autowired
    private RefererInterceptor refererInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(refererInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**");
    }
    
    @Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//第一个方法设置访问路径前缀，第二个方法设置资源路径
    registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
}
