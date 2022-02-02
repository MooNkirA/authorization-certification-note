package com.moon.springcloud.security.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * 令牌管理配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-31 23:31
 * @description
 */
@Configuration
public class TokenConfig {

    // 定义生成 token 的秘钥
    private final String SIGNING_KEY = "uaa123";

    /**
     * 创建令牌的存储策略实例
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        // 使用 JWT 令牌存储方案
        return new JwtTokenStore(accessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(SIGNING_KEY); // 对称秘钥，授权服务器也使用该秘钥来验证
        return converter;
    }

}
