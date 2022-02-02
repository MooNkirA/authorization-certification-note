package com.moon.springcloud.security.uaa.model;

import lombok.Data;

/**
 * 用户实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-28 16:29
 * @description
 */
@Data
public class UserDO {

    private String id;
    private String username;
    private String password;
    private String fullname;
    private String mobile;

}
