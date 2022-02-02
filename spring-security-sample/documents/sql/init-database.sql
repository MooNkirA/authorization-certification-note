CREATE DATABASE `user_db` CHARACTER SET 'utf8' COLLATE 'utf8_general_ci';

use `user_db`;

-- 用户表
CREATE TABLE `t_user` (
	`id` BIGINT ( 20 ) NOT NULL COMMENT '用户id',
	`username` VARCHAR ( 64 ) NOT NULL,
	`password` VARCHAR ( 64 ) NOT NULL,
	`fullname` VARCHAR ( 255 ) NOT NULL COMMENT '用户姓名',
	`mobile` VARCHAR ( 11 ) DEFAULT NULL COMMENT '手机号',
PRIMARY KEY ( `id` ) USING BTREE 
) ENGINE = INNODB DEFAULT CHARSET = utf8 ROW_FORMAT = DYNAMIC;

INSERT INTO t_user ( `id`, `username`, `password`, `fullname` )
VALUES
	( 1, 'admin', '123', '管理员' ),( 2, 'moon', '123', '天锁斩月' );

-- 角色表
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
	`id` VARCHAR ( 32 ) NOT NULL,
	`role_name` VARCHAR ( 255 ) DEFAULT NULL,
	`description` VARCHAR ( 255 ) DEFAULT NULL,
	`create_time` datetime DEFAULT NULL,
	`update_time` datetime DEFAULT NULL,
	`status` CHAR ( 1 ) NOT NULL,
	PRIMARY KEY ( `id` ),
	UNIQUE KEY `unique_role_name` ( `role_name` ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8;

TRUNCATE TABLE `t_role`;
INSERT INTO `t_role` ( `id`, `role_name`, `description`, `create_time`, `update_time`, `status` ) VALUES( '1', '管理员', NULL, NULL, NULL, '' );

-- 用户角色关系表
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role` (
	`user_id` VARCHAR ( 32 ) NOT NULL,
	`role_id` VARCHAR ( 32 ) NOT NULL,
	`create_time` datetime DEFAULT NULL,
	`creator` VARCHAR ( 255 ) DEFAULT NULL,
	PRIMARY KEY ( `user_id`, `role_id` ) 
) ENGINE = INNODB DEFAULT CHARSET = utf8;

TRUNCATE TABLE `t_user_role`;
INSERT INTO `t_user_role` ( `user_id`, `role_id`, `create_time`, `creator` ) VALUES ( '1', '1', NULL, NULL );

-- 权限表
DROP TABLE IF EXISTS `t_permission`;
CREATE TABLE `t_permission` (
`id` varchar(32) NOT NULL,
`code` varchar(32) NOT NULL COMMENT '权限标识符',
`description` varchar(64) DEFAULT NULL COMMENT '描述',
`url` varchar(128) DEFAULT NULL COMMENT '请求地址',
PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `t_permission`;
INSERT INTO `t_permission`(`id`,`code`,`description`,`url`) VALUES ('1','p1','测试资源1','/check/p1'),('2','p3','测试资源2','/check/p2');

-- 角色权限关系表
DROP TABLE IF EXISTS `t_role_permission`;
CREATE TABLE `t_role_permission` (
	`role_id` varchar(32) NOT NULL,
	`permission_id` varchar(32) NOT NULL,
	PRIMARY KEY (`role_id`,`permission_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

TRUNCATE TABLE `t_role_permission`;
INSERT INTO `t_role_permission` ( `role_id`, `permission_id` ) VALUES ( '1', '1' ),( '1', '2' );

-- 客户端信息表
DROP TABLE IF EXISTS `oauth_client_details`;
CREATE TABLE `oauth_client_details` (
	`client_id` VARCHAR ( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '客户端标识',
	`resource_ids` VARCHAR ( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '接入资源列表',
	`client_secret` VARCHAR ( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '客户端秘钥',
	`scope` VARCHAR ( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	`authorized_grant_types` VARCHAR ( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	`web_server_redirect_uri` VARCHAR ( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	`authorities` VARCHAR ( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	`access_token_validity` INT ( 11 ) NULL DEFAULT NULL,
	`refresh_token_validity` INT ( 11 ) NULL DEFAULT NULL,
	`additional_information` LONGTEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
	`create_time` TIMESTAMP ( 0 ) NOT NULL DEFAULT CURRENT_TIMESTAMP ( 0 ) ON UPDATE CURRENT_TIMESTAMP ( 0 ),
	`archived` TINYINT ( 4 ) NULL DEFAULT NULL,
	`trusted` TINYINT ( 4 ) NULL DEFAULT NULL,
	`autoapprove` VARCHAR ( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	PRIMARY KEY ( `client_id` ) USING BTREE 
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '接入客户端信息' ROW_FORMAT = Dynamic;

INSERT INTO `oauth_client_details` VALUES ('c1', 'res1', '$2a$10$jq8jJKV/tz0b8k7vodrmPO.WGmhGe1lEjVhoS8DUSY185Puhil9IS', 'ROLE_ADMIN,ROLE_USER,ROLE_API', 'client_credentials,password,authorization_code,implicit,refresh_token', 'http://www.baidu.com', NULL, 7200, 259200, NULL, NOW(), 0, 0, 'false');
INSERT INTO `oauth_client_details` VALUES ('c2', 'res2', '$2a$10$jq8jJKV/tz0b8k7vodrmPO.WGmhGe1lEjVhoS8DUSY185Puhil9IS', 'ROLE_API', 'client_credentials,password,authorization_code,implicit,refresh_token', 'http://www.baidu.com', NULL, 31536000, 2592000, NULL, NOW(), 0, 0, 'false');

-- Spring Security OAuth2 授权码
DROP TABLE IF	EXISTS `oauth_code`;
CREATE TABLE `oauth_code` (
	`create_time` TIMESTAMP ( 0 ) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`code` VARCHAR ( 255 ) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
	`authentication` BLOB NULL,
	INDEX `code_index` ( `code` ) USING BTREE
) ENGINE = INNODB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;