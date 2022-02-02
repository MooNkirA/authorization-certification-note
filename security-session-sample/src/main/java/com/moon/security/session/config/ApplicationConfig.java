package com.moon.security.session.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * 替代 applicationContext.xml 配置文件
 * 在此类中配置除了Controller的其它bean，比如：数据库链接池、事务管理器、业务bean等。
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-23 10:44
 * @description
 */
@Configuration
@ComponentScan(basePackages = "com.moon.security.session",
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, value = Controller.class)})
public class ApplicationConfig {
}
