package com.moon.springcloud.security.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring Security 的安全配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-30 22:09
 * @description
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 创建认证管理器实例。
     *
     * @return
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 密码编码器，即设置登陆时密码的校验
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
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
                .antMatchers("/check/p1").hasAuthority("p1") // 设置拥有p1权限访问的url
                .antMatchers("/check/p2").hasAuthority("p2") // 设置拥有p2权限访问的url
                .antMatchers("/login*").permitAll() // 设置所有 /login 开头的请求均可以访问
                .anyRequest().authenticated()  // 设置除了上面配置的url，其它的请求可以访问
                .and()
                .formLogin(); // 允许表单登录
    }

}
