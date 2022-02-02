package com.moon.springcloud.security.order.model;

import lombok.Data;

/**
 * 用户信息类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-02-01 15:22
 * @description
 */
@Data
public class UserDTO {

    /**
     * 用户id
     */
    private String id;
    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 姓名
     */
    private String fullname;

}
