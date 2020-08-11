package com.zdxj.interceptor;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 验证referer头白名单
 * @author zhaodx
 *
 */
@Component
@ConfigurationProperties(prefix = "referer")
public class RefererProperties {
	// 白名单域名
    private List<String> refererDomain;

	public List<String> getRefererDomain() {
		return refererDomain;
	}

	public void setRefererDomain(List<String> refererDomain) {
		this.refererDomain = refererDomain;
	}
    
    
}
