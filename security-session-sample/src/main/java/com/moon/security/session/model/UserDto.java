package com.moon.security.session.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 用户响应实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-23 13:42
 * @description
 */
@Data
@AllArgsConstructor
public class UserDto {
    // 用户session的key
    public static final String SESSION_USER_KEY = "_user";

    // 用户身份信息
    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;

    /**
     * 用户权限
     */
    private Set<String> authorities;
}
