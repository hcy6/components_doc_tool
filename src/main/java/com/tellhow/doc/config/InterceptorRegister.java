package com.tellhow.doc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author: 韩聪寅
 * @create: 2019-11-27
 **/
@Configuration
public class InterceptorRegister implements WebMvcConfigurer {
    @Value("${imgAddress}")
    private String imgAddress;

    /**
     * 注册静态文件的自定义映射路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/media/**")
                .addResourceLocations("file:" + imgAddress);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }
}