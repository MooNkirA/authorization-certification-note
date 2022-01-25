package com.moon.security.session.service;

import com.moon.security.session.model.AuthenticationRequest;
import com.moon.security.session.model.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 用户校验处理逻辑
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-23 13:49
 * @description
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    /**
     * 用户认证
     *
     * @param authenticationRequest 用户认证请求，账号和密码
     * @return 认证成功的用户信息
     */
    @Override
    public UserDto authentication(AuthenticationRequest authenticationRequest) {
        // 校验参数是否为空
        if (authenticationRequest == null
                || StringUtils.isEmpty(authenticationRequest.getUsername())
                || StringUtils.isEmpty(authenticationRequest.getPassword())) {
            throw new RuntimeException("账号和密码为空");
        }

        // 根据账号去查询数据库，这里测试程序采用模拟方法
        UserDto user = getUserDto(authenticationRequest.getUsername());
        // 判断用户是否为空
        if (user == null) {
            throw new RuntimeException("查询不到该用户");
        }
        // 校验密码
        if (!authenticationRequest.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("账号或密码错误");
        }
        // 认证通过，返回用户身份信息
        return user;
    }


    // 模拟根据账号查询用户信息
    private UserDto getUserDto(String userName) {
        return userMap.get(userName);
    }

    // 保存用户信息的map
    private final Map<String, UserDto> userMap = new HashMap<>();

    // 初始化一些测试使用的用户信息到内存中
    {
        Set<String> authorities1 = new HashSet<>();
        authorities1.add("p1"); // 模拟增加权限1
        Set<String> authorities2 = new HashSet<>();
        authorities2.add("p2"); // 模拟增加权限2
        // 分别给两个用户设置不同的权限标识
        userMap.put("admin", new UserDto("1000", "admin", "123", "管理员", "133443", authorities1));
        userMap.put("moon", new UserDto("1011", "moon", "456", "月之哀伤", "144553", authorities2));
    }

}
