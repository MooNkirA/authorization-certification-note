package com.moon.spring.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆控制类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-26 22:04
 * @description
 */
@RestController
public class LoginController {

    /**
     * 登陆成功后跳转的请求url
     *
     * @return
     */
    @RequestMapping(value = "/login-success", produces = {"text/plain;charset=UTF-8"})
    public String loginSuccess() {
        return " 登录成功";
    }

    /**
     * 测试当前用户的权限
     *
     * @return
     */
    @GetMapping(value = "/check/p1", produces = {"text/plain;charset=UTF-8"})
    public String checkPrivilege1() {
        return "访问资源p1";
    }

    /**
     * 测试当前用户的权限
     *
     * @return
     */
    @GetMapping(value = "/check/p2", produces = {"text/plain;charset=UTF-8"})
    public String checkPrivilege2() {
        return "访问资源p2";
    }

}
