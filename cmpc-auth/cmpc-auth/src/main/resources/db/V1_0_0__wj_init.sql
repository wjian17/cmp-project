use #{auth};

DROP TABLE IF EXISTS `oauth_access_token`;
DROP TABLE IF EXISTS `oauth_approvals`;
DROP TABLE IF EXISTS `oauth_client_details`;
DROP TABLE IF EXISTS `oauth_client_token`;
DROP TABLE IF EXISTS `oauth_code`;
DROP TABLE IF EXISTS `oauth_refresh_token`;
DROP TABLE IF EXISTS `sys_user`;
DROP TABLE IF EXISTS `authority`;
DROP TABLE IF EXISTS `credentials`;
DROP TABLE IF EXISTS `credentials_authorities`;

CREATE TABLE `oauth_access_token` (
	`token_id` varchar(255) DEFAULT NULL COMMENT '加密的access_token的值',
	`token` longblob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
	`authentication_id` varchar(255) DEFAULT NULL COMMENT '加密过的username,
    client_id,
    scope',
	`user_name` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
	`client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID',
	`authentication` longblob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据',
	`refresh_token` varchar(255) DEFAULT NULL COMMENT '加密的refresh_token的值'
) ENGINE = InnoDB CHARSET = utf8;


CREATE TABLE `oauth_approvals` (
	`userId` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
	`clientId` varchar(255) DEFAULT NULL COMMENT '客户端ID',
	`scope` varchar(255) DEFAULT NULL COMMENT '申请的权限范围',
	`status` varchar(10) DEFAULT NULL COMMENT '状态（Approve或Deny）',
	`expiresAt` datetime DEFAULT NULL COMMENT '过期时间',
	`lastModifiedAt` datetime DEFAULT NULL COMMENT '最终修改时间'
) ENGINE = InnoDB CHARSET = utf8;


CREATE TABLE `oauth_client_details` (
	`client_id` varchar(255) NOT NULL COMMENT '客户端ID',
	`resource_ids` varchar(255) DEFAULT NULL COMMENT '资源ID集合,多个资源时用逗号(,)分隔',
	`client_secret` varchar(255) DEFAULT NULL COMMENT '客户端密匙',
	`scope` varchar(255) DEFAULT NULL COMMENT '客户端申请的权限范围',
	`authorized_grant_types` varchar(255) DEFAULT NULL COMMENT '客户端支持的grant_type',
	`web_server_redirect_uri` varchar(255) DEFAULT NULL COMMENT '重定向URI',
	`authorities` varchar(255) DEFAULT NULL COMMENT '客户端所拥有的Spring Security的权限值，多个用逗号(,)分隔',
	`access_token_validity` int(11) DEFAULT NULL COMMENT '访问令牌有效时间值(单位:秒)',
	`refresh_token_validity` int(11) DEFAULT NULL COMMENT '更新令牌有效时间值(单位:秒)',
	`additional_information` varchar(255) DEFAULT NULL COMMENT '预留字段',
	`autoapprove` varchar(255) DEFAULT NULL COMMENT '用户是否自动Approval操作'
) ENGINE = InnoDB CHARSET = utf8;


CREATE TABLE `oauth_client_token` (
	`token_id` varchar(255) DEFAULT NULL COMMENT '加密的access_token值',
	`token` longblob COMMENT 'OAuth2AccessToken.java对象序列化后的二进制数据',
	`authentication_id` varchar(255) DEFAULT NULL COMMENT '加密过的username,
    client_id,
    scope',
	`user_name` varchar(255) DEFAULT NULL COMMENT '登录的用户名',
	`client_id` varchar(255) DEFAULT NULL COMMENT '客户端ID'
) ENGINE = InnoDB CHARSET = utf8;


CREATE TABLE `oauth_code` (
	`code` varchar(255) DEFAULT NULL COMMENT '授权码(未加密)',
	`authentication` varbinary(255) DEFAULT NULL COMMENT 'AuthorizationRequestHolder.java对象序列化后的二进制数据'
) ENGINE = InnoDB CHARSET = utf8;


CREATE TABLE `oauth_refresh_token` (
	`token_id` varchar(255) DEFAULT NULL COMMENT '加密过的refresh_token的值',
	`token` longblob COMMENT 'OAuth2RefreshToken.java对象序列化后的二进制数据 ',
	`authentication` longblob COMMENT 'OAuth2Authentication.java对象序列化后的二进制数据'
) ENGINE = InnoDB CHARSET = utf8;


CREATE TABLE `sys_user` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT,
	`username` varchar(50) DEFAULT NULL COMMENT '用户名',
	`password` varchar(50) DEFAULT NULL COMMENT '密码',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8 COMMENT '用户信息表';


CREATE TABLE `authority` (
	`id` bigint(11) NOT NULL COMMENT '权限id',
	`authority` varchar(255) DEFAULT NULL COMMENT '权限',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;


CREATE TABLE `credentials` (
	`id` bigint(11) NOT NULL COMMENT '凭证id',
	`enabled` tinyint(1) NOT NULL COMMENT '是否可用',
	`name` varchar(255) NOT NULL COMMENT '用户名',
	`password` varchar(255) NOT NULL COMMENT '密码',
	`version` int(11) DEFAULT NULL COMMENT '版本号',
	PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARSET = utf8;


CREATE TABLE `credentials_authorities` (
	`credentials_id` bigint(20) NOT NULL COMMENT '凭证id',
	`authorities_id` bigint(20) NOT NULL COMMENT '权限id'
) ENGINE = InnoDB CHARSET = utf8;