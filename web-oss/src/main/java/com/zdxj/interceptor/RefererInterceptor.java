package com.zdxj.interceptor;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RefererInterceptor extends HandlerInterceptorAdapter {
	
    @Autowired
    private RefererProperties properties;
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
        String referer = req.getHeader("referer");
        String host = req.getServerName();
        String method = req.getMethod();
        if(!"POST".equals(method) && !"GET".equals(method)) {
        	resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }
        // 只验证POST请求
        if ("POST".equals(method) || ("GET".equals(method) && StringUtils.isNotBlank(referer))) {
            if (referer == null) {
                // 状态置为404
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return false;
            } 
            java.net.URL url = null;
            try {
                url = new java.net.URL(referer);
            } catch (MalformedURLException e) {
                // URL解析异常，也置为404
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                return false;
            }
            // 首先判断请求域名和referer域名是否相同
            if (!host.equals(url.getHost())) {
                // 如果不等，判断是否在白名单中
                if (properties.getRefererDomain() != null) {
                    for (String s : properties.getRefererDomain()) {
                        if (s.equals(url.getHost())) {
                            return true;
                        }
                    }
                }
                return false;
           }
        }
      return true;
   }
}
