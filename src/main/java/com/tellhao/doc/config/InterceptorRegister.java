package com.tellhao.doc.config;


//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorRegister extends WebMvcConfigurerAdapter{
//
//    private static final Logger logger = LoggerFactory.getLogger(InterceptorRegister.class);

    /*
     * 注册静态文件的自定义映射路径
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //定义到新文件夹
//        registry.addResourceHandler("/image/**")
//                .addResourceLocations("classpath:/image/");
        //定义到硬盘
        registry.addResourceHandler("/media/**")
                .addResourceLocations("file:G:/JAVA/components-doc/docs/media/");
        super.addResourceHandlers(registry);
    }
}