package com.moon.security.session.service;

import com.moon.security.session.model.AuthenticationRequest;
import com.moon.security.session.model.UserDto;

/**
 * 登陆校验接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-23 13:44
 * @description
 */
public interface AuthenticationService {

    /**
     * 用户认证
     *
     * @param authenticationRequest 用户认证请求，账号和密码
     * @return 认证成功的用户信息
     */
    UserDto authentication(AuthenticationRequest authenticationRequest);

}
