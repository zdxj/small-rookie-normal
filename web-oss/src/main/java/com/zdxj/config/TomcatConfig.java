package com.zdxj.config;

import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 禁止不安全请求方式
 * 
 * @author:zhaodx
 * @date:2018-11-16 20:30
 */
@Configuration
public class TomcatConfig {
	@Bean
	public ConfigurableServletWebServerFactory configurableServletWebServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addContextCustomizers(context -> {
			SecurityConstraint securityConstraint = new SecurityConstraint();
			securityConstraint.setUserConstraint("CONFIDENTIAL");
			SecurityCollection collection = new SecurityCollection();
			collection.addPattern("/*");
			collection.addMethod("HEAD");
			collection.addMethod("PUT");
			collection.addMethod("DELETE");
			collection.addMethod("OPTIONS");
			collection.addMethod("TRACE");
			collection.addMethod("COPY");
			collection.addMethod("SEARCH");
			collection.addMethod("PROPFIND");
			collection.addMethod("CONNECT");
			collection.addMethod("PROPPATCH");
			collection.addMethod("PATCH");
			collection.addMethod("MKCOL");
			collection.addMethod("MOVE");
			collection.addMethod("LOCK");
			collection.addMethod("UNLOCK");
			securityConstraint.addCollection(collection);
			context.addConstraint(securityConstraint);
		});
		return factory;
	}
}