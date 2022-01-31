package com.moon.springcloud.security.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.Arrays;

/**
 * Spring Security OAuth2.0 授权配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-30 8:51
 * @description
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 用来配置客户端详情服务 ClientDetailsService，客户端详情信息在此方法中进行初始化，
     * 这里能够把客户端详情信息使用硬编码或者是通过数据库来存储调取详情信息
     * <p>
     * 此示例目前暂时使用内存的方式存储客户端详情信息
     * </p>
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 使用in‐memory存储
                .withClient("c1") // client_id
                .secret(new BCryptPasswordEncoder().encode("secret")) // 客户端密钥
                .resourceIds("res1") // 资源列表
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token") // 该client允许的授权类型
                .scopes("all") // 允许的授权范围
                .autoApprove(false) // 发放令牌时，是否跳转授权页面，true-代表不跳转，false-代表跳转
                .redirectUris("http://www.baidu.com"); // 验证回调地址
    }

    /**
     * 创建令牌管理服务对象
     *
     * @return
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices service = new DefaultTokenServices();
        service.setClientDetailsService(clientDetailsService); // 客户端详情服务
        service.setSupportRefreshToken(true); // 支持刷新令牌
        service.setTokenStore(tokenStore); // 令牌存储策略
        service.setAccessTokenValiditySeconds(7200); // 令牌默认有效期2小时
        service.setRefreshTokenValiditySeconds(259200); // 刷新令牌默认有效期3天
        return service;
    }

    /**
     * 配置令牌访问端点
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager) // 认证管理器
                .authorizationCodeServices(authorizationCodeServices) // 授权码服务
                .tokenServices(tokenService()) // 令牌管理服务
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * 设置授权码模式的授权码如何存取
     *
     * @return
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices(); // 暂时采用内存方式
    }

    /**
     * 配置令牌端点(Token Endpoint)的安全约束
     *
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()") // oauth/token_key是公开
                .checkTokenAccess("permitAll()") // oauth/check_token公开
                .allowFormAuthenticationForClients(); // 表单认证（申请令牌）
    }
}
