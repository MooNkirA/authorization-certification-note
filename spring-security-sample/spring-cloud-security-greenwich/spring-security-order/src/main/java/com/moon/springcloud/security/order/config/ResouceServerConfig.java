package com.moon.springcloud.security.order.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Spring Security 资源服务配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-31 17:36
 * @description
 */
@Configuration
@EnableResourceServer
public class ResouceServerConfig extends ResourceServerConfigurerAdapter {

    /**
     * 服务资源id，与授权服务器中配置类的 clients.resourceIds("res1") 值一致
     */
    public static final String RESOURCE_ID = "res1";

    // 令牌的存储策略实例
    @Autowired
    private TokenStore tokenStore;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID) // 配置资源id
                // .tokenServices(tokenService()) // 验证令牌的服务(远程请求)
                .tokenStore(tokenStore) // 验证令牌的服务（本地验证）
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**").access("#oauth2.hasScope('ROLE_ADMIN')") // 配置访问的限制规则
                .and()
                .csrf().disable() // 设置不再限制 CSRF
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 配置不生成本地 session
    }

    /**
     * 资源服务令牌解析服务
     *
     * @return
     */
    /*@Bean
    public ResourceServerTokenServices tokenService() {
        //使 用远程服务请求授权服务器校验token,必须指定校验token 的url、client_id，client_secret
        RemoteTokenServices service = new RemoteTokenServices();
        service.setCheckTokenEndpointUrl("http://localhost:53020/uaa/oauth/check_token");
        service.setClientId("c1");
        service.setClientSecret("secret");
        return service;
    }*/
}
