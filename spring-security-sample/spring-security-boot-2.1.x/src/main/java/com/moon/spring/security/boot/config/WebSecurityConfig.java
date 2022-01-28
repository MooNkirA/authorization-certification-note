package com.moon.spring.security.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Spring Security 的安全配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-27 11:24
 * @description
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 创建 UserDetailsService 实例。
     * 用于定义用户信息服务（查询用户信息）
     *
     * @return
     */
    /*@Bean
    public UserDetailsService userDetailsService() {
        // 正常情况是查询数据库，此示例为了简单直接创建保存在内存中的用户信息服务
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        // 创建用户信息与配置权限标识
        manager.createUser(User.withUsername("admin").password("123").authorities("p1").build());
        manager.createUser(User.withUsername("moon").password("456").authorities("p2").build());
        return manager;
    }*/

    /**
     * 密码编码器，即设置登陆时密码的校验
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // NoOpPasswordEncoder 是直接进行字符串比较，不对密码进行加密比较处理，字符串内容一致则校验通过。
        // return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }

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
                .antMatchers("/check/p1").hasAuthority("p1") // 设置不同权限访问的url
                .antMatchers("/check/p2").hasAuthority("p2") // 设置不同权限访问的url
                .antMatchers("/check/**").authenticated() // 设置所有 /check/** 的请求必须认证通过
                .anyRequest().permitAll()  // 设置除了上面配置的 /check/**，其它的请求可以访问
                .and()
                .formLogin() // 允许表单登录
                .loginPage("/login-view") // 指定自定义登陆页面url
                .loginProcessingUrl("/login") // 指定登陆认证表单提交的url
                .successForwardUrl("/login-success"); // 自定义登录成功的页面地址
    }

}
