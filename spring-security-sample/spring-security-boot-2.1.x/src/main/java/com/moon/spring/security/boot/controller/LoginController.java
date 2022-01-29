package com.moon.spring.security.boot.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆控制类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-27 11:27
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
        return getUsername() + "登录成功";
    }

    /**
     * 测试当前用户的权限
     *
     * @return
     */
    @GetMapping(value = "/check/p1", produces = {"text/plain;charset=UTF-8"})
    @PreAuthorize("hasAuthority('p1')") // 拥有p1权限才可以访问
    public String checkPrivilege1() {
        return getUsername() + "访问资源p1";
    }

    /**
     * 测试当前用户的权限
     *
     * @return
     */
    @GetMapping(value = "/check/p2", produces = {"text/plain;charset=UTF-8"})
    @PreAuthorize("hasAuthority('p2')") // 拥有p2权限才可以访问
    public String checkPrivilege2() {
        return getUsername() + "访问资源p2";
    }

    /**
     * 测试当前用户的权限
     *
     * @return
     */
    @GetMapping(value = "/check/p3", produces = {"text/plain;charset=UTF-8"})
    @PreAuthorize("hasAuthority('p1') and hasAuthority('p3')") // 拥有p1和p3权限才可以访问
    public String checkPrivilege3() {
        return getUsername() + "访问资源p3";
    }

    /**
     * 测试当前用户的权限
     *
     * @return
     */
    @GetMapping(value = "/check/p4", produces = {"text/plain;charset=UTF-8"})
    @PreAuthorize("isAnonymous()") // 代表方法可匿名访问
    public String checkPrivilege4() {
        return getUsername() + "访问资源p4";
    }

    /**
     * 通过 Spring Security 框架的 SecurityContext 对象获取当前用户信息
     *
     * @return
     */
    private String getUsername() {
        String username = null;
        // 获取当前认证通过的用户身份信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 用户身份
        Object principal = authentication.getPrincipal();

        if (principal == null) {
            username = "匿名";
        }

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            username = userDetails.getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }

}