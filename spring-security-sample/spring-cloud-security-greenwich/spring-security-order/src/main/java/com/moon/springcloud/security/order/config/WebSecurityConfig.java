package com.moon.springcloud.security.order.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Spring Security 的安全配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-31 18:21
 * @description
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 安全拦截机制（最重要）
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 屏蔽 CSRF（Cross-site request forgery跨站请求伪造）控制
                .authorizeRequests()
                .antMatchers("/check/**").authenticated() // 设置所有 /check/** 的请求必须认证通过
                .anyRequest().permitAll();  // 设置除了上面配置的 /check/**，其它的请求可以访问
    }

}
