package com.moon.spring.security.init;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * Spring Security 初始化类，
 * 因为在 SpringApplicationInitializer 类中已经将 Spring Security 配置类注册到 RootContext 中。
 * 所以此配置类中可以什么都不做
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-26 21:47
 * @description
 */
public class SpringSecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

    public SpringSecurityApplicationInitializer() {
        //super(WebSecurityConfig.class);
    }

}
