package com.moon.spring.security.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 的安全配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-27 11:24
 * @description
 */
@Configuration
/*
 * @EnableGlobalMethodSecurity 注解配置启用基于注解的安全权限控制
 *  securedEnabled 为 true，代表启用 @Secured 注解
 *  prePostEnabled 为 true，代表启动 @PreAuthorize/@PostAuthorize 注解
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
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
                // 已方法级别的授权控制，这里配置不需要
                // .antMatchers("/check/p1").hasAuthority("p1") // 设置拥有p1权限访问的url
                // .antMatchers("/check/p2").hasAuthority("p2") // 设置拥有p2权限访问的url
                // .antMatchers("/check/p3").access("hasAuthority('p1') and hasAuthority('p2')") // 设置同时拥有p1、p2权限访问的url
                // .antMatchers("/check/**").authenticated() // 设置所有 /check/** 的请求必须认证通过
                .anyRequest().permitAll()  // 设置除了上面配置的 /check/**，其它的请求可以访问
                .and()
                .formLogin() // 允许表单登录
                .loginPage("/login-view") // 指定自定义登陆页面url
                .loginProcessingUrl("/login") // 指定登陆认证表单提交的url
                .successForwardUrl("/login-success") // 自定义登录成功的页面地址
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 会话控制配置
                // .invalidSessionUrl("/login‐view?error=INVALID_SESSION") // 配置会话失效后的跳转url
                .and()
                .logout() // 提供系统退出支持，使用 WebSecurityConfigurerAdapter 会自动被应用
                .logoutUrl("/logout") // 设置触发退出操作的URL, 默认是 /logout
                .logoutSuccessUrl("/login‐view?logout"); // 退出之后跳转的URL。默认是 /login?logout
                // .logoutSuccessHandler(logoutSuccessHandler) // 定制的 LogoutSuccessHandler，用于实现用户退出成功时的处理。如果指定了这个选项那么 logoutSuccessUrl() 的设置会被忽略。
                // .addLogoutHandler(logoutHandler) // 添加一个 LogoutHandler，用于实现用户退出时的清理工作。默认 SecurityContextLogoutHandler 会被添加为最后一个 LogoutHandler
                // .invalidateHttpSession(true); // 指定是否在退出时让 HttpSession 无效。默认设置为 true
    }

}
