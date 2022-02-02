package com.moon.spring.security.boot.model;

import lombok.Data;

/**
 * 权限实体类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-01-29 10:35
 * @description
 */
@Data
public class PermissionDO {

    private String id;
    private String code;
    private String description;
    private String url;

}
