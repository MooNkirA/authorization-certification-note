package com.moon.security.session.controller;

import com.moon.security.session.model.AuthenticationRequest;
import com.moon.security.session.model.UserDto;
import com.moon.security.session.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * 登陆控制层
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-23 13:56
 * @description
 */
@RestController
public class LoginController {

    @Autowired
    AuthenticationService authenticationService;

    /**
     * 用户登陆
     *
     * @param authenticationRequest 登陆请求参数
     * @return
     */
    @PostMapping(value = "/login", produces = "text/plain;charset=utf-8")
    public String login(AuthenticationRequest authenticationRequest, HttpSession httpSession) {
        UserDto userDto = authenticationService.authentication(authenticationRequest);
        // 将用户信息存入session中
        httpSession.setAttribute(UserDto.SESSION_USER_KEY, userDto);
        return userDto.getUsername() + "登录成功";
    }

    /**
     * 用户登出方法，将session置为失效
     *
     * @param session
     * @return
     */
    @GetMapping(value = "/logout", produces = {"text/plain;charset=UTF-8"})
    public String logout(HttpSession session) {
        session.invalidate();
        return "退出成功";
    }

    /**
     * 测试当前用户是否有登陆
     *
     * @param session
     * @return
     */
    @GetMapping(value = "/check/p1", produces = {"text/plain;charset=UTF-8"})
    public String checkSession(HttpSession session) {
        String fullname = null;
        // 从 session 中获取用户信息
        Object object = session.getAttribute(UserDto.SESSION_USER_KEY);
        if (object == null) {
            fullname = "匿名"; // 无登陆
        } else {
            UserDto userDto = (UserDto) object;
            fullname = userDto.getFullname(); // 有登陆
        }
        return fullname + "访问资源p1";
    }

    /**
     * 测试当前用户是否有登陆
     *
     * @param session
     * @return
     */
    @GetMapping(value = "/check/p2", produces = {"text/plain;charset=UTF-8"})
    public String checkSession2(HttpSession session) {
        String fullname = null;
        // 从 session 中获取用户信息
        Object object = session.getAttribute(UserDto.SESSION_USER_KEY);
        if (object == null) {
            fullname = "匿名"; // 无登陆
        } else {
            UserDto userDto = (UserDto) object;
            fullname = userDto.getFullname(); // 有登陆
        }
        return fullname + "访问资源p2";
    }
}
