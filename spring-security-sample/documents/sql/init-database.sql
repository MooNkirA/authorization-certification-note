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