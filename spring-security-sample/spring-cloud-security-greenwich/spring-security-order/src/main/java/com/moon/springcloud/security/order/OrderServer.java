package com.moon.springcloud.security.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 项目启动类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-30 8:36
 * @description
 */
@SpringBootApplication
// @EnableDiscoveryClient
public class OrderServer {

    public static void main(String[] args) {
        SpringApplication.run(OrderServer.class, args);
    }

}
