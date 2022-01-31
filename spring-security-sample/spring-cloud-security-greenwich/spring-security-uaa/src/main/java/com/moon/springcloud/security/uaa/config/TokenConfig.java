package com.moon.springcloud.security.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * 令牌管理配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-30 11:12
 * @description
 */
@Configuration
public class TokenConfig {

    /**
     * 创建令牌的存储策略实例
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        // 使用内存存储令牌（普通令牌）
        return new InMemoryTokenStore();
    }

}
