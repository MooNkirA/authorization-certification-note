package com.moon.security.session.init;

import com.moon.security.session.config.ApplicationConfig;
import com.moon.security.session.config.WebConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * 实现 WebApplicationInitializer 接口。用于初始化 Spring 容器。
 * 此类相当于 web.xml 配置文件，使用了 servlet3.0 开发则不需要再定义 web.xml
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-23 11:11
 * @description
 */
public class SpringApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /* 指定rootContext的配置类 */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{ApplicationConfig.class};
    }

    /* 指定servletContext的配置类 */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    /* 配置 servlet 访问地址映射 */
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
