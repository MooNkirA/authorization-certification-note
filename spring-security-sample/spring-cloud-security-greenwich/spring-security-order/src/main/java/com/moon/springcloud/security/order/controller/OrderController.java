package com.moon.springcloud.security.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单资源访问控制层
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-31 18:14
 * @description
 */
@RestController
public class OrderController {

    @GetMapping(value = "/check/p1")
    @PreAuthorize("hasAuthority('p1')") // 使用方法授权配置，拥有 p1 权限方可访问此url
    public String p1() {
        return "访问资源1";
    }

}