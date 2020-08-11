package com.zdxj.shiro;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.TemplateModelException;
/**
 * 使shiro标签支持Freemark
 * @author:zhaodx	
 * @date:2018-11-01 15:31
 */
@Component
public class ShiroTagsFreeMarkerCfg {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @PostConstruct
    public void setSharedVariable() throws TemplateModelException {
        freeMarkerConfigurer.getConfiguration().setSharedVariable("shiro", new CustomShiroTags());
    }
}
