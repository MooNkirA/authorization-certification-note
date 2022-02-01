package com.moon.springcloud.security.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * Spring Security 资源服务配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-02-01 14:27
 * @description
 */
@Configuration
public class ResouceServerConfig {

    /**
     * 服务资源id，与授权服务器中配置类的 clients.resourceIds("res1") 值一致
     */
    public static final String RESOURCE_ID = "res1";

    // uaa 资源服务配置
    @Configuration
    @EnableResourceServer
    public class UAAServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.tokenStore(tokenStore) // 验证令牌的服务（本地验证）
                    .resourceId(RESOURCE_ID) // 配置资源id
                    .stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/uaa/**").permitAll();  // 配置访问的限制规则
        }
    }

    // order 资源服务配置
    @Configuration
    @EnableResourceServer
    public class OrderServerConfig extends ResourceServerConfigurerAdapter {
        @Autowired
        private TokenStore tokenStore;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.tokenStore(tokenStore) // 验证令牌的服务（本地验证）
                    .resourceId(RESOURCE_ID) // 配置资源id
                    .stateless(true);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .antMatchers("/order/**").access("#oauth2.hasScope('ROLE_API')");  // 配置访问的限制规则
        }
    }

    // 配置其它的资源服务...
}
