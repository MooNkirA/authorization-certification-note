package com.moon.spring.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * 此配置类相当于 spring-mvc.xml 配置文件，相应 DispatcherServlet 的配置。
 * 在此类中配置 Spring MVC 的视频解析器
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-25 16:38
 * @description
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.moon.spring.security",
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class WebConfig implements WebMvcConfigurer {

    // 视图解析器
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }


    /**
     * 配置 url 的与页面的映射关系
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 增加默认 Url 根路径为 '/' 时，跳转到 /login。注：此 url 是 spring security 默认提供的登陆页面
        registry.addViewController("/").setViewName("redirect:/login");
    }
}
