package com.moon.security.session.model;

import lombok.Data;

/**
 * 请求参数接收实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-23 13:42
 * @description
 */
@Data
public class AuthenticationRequest {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

}
