package com.moon.spring.security.boot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 此配置类相当于 spring-mvc.xml 配置文件
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-27 11:16
 * @description
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 配置 url 的与页面的映射关系
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 增加默认 Url 根路径为 '/' 时，跳转到 /login。注：此 url 是 spring security 默认提供的登陆页面
        // registry.addViewController("/").setViewName("redirect:/login");

        // 配置跳转到自定义的认证登陆页面，url 按需定，此示例是 /login-view
        registry.addViewController("/").setViewName("redirect:/login-view");
        // 配置自定义登陆 url 指向自定义的页面 login.jsp
        registry.addViewController("/login-view").setViewName("login");
    }

}
