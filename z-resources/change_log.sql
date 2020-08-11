-- 系统菜单表
CREATE TABLE system_menu (
	id int unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
	is_deleted tinyint(1) DEFAULT 0 COMMENT '删除标记',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '修改时间',
	menu_name varchar(32) DEFAULT '' COMMENT '菜单名称',
	perm_code varchar(32) DEFAULT '' COMMENT '权限编码',
	perm_type tinyint DEFAULT 0 COMMENT '菜单类型,0:菜单，1:按钮,2:目录',
	open_type varchar(10) DEFAULT '' COMMENT '打开方式,menuItem页签 menuBlank新窗口',
	icon varchar(32) DEFAULT '' COMMENT '菜单图标',
	parent_id int unsigned DEFAULT 0 COMMENT '父ID',
	url varchar(128) DEFAULT '' COMMENT '菜单路径',
	perm_url varchar(512) DEFAULT '' COMMENT '权限路径',
	position int DEFAULT 0 COMMENT '位置排序'
) ENGINE = InnoDB CHARSET = utf8 COMMENT '系统菜单表';
ALTER TABLE system_menu ADD unique(perm_code);

-- 系统管理员
CREATE TABLE system_manager (
	id int unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
	is_deleted tinyint(1) DEFAULT 0 COMMENT '删除标记',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '修改时间',
	login_name varchar(32) DEFAULT '' COMMENT '登录名',
	login_password varchar(32) DEFAULT '' COMMENT '密码',
	real_name varchar(32) DEFAULT '' COMMENT '真实姓名',
	department_id int unsigned DEFAULT 0 COMMENT '所属部门',
	login_ip varchar(32) DEFAULT '' COMMENT '登录ip地址',
	login_time datetime DEFAULT NULL COMMENT '登录时间',
	login_error_time datetime DEFAULT NULL COMMENT '登录错误时间',
	login_error_count integer DEFAULT 0 COMMENT '登录错误次数',
	mobile varchar(16) DEFAULT '' COMMENT '手机号码',
	head_image varchar(128) DEFAULT '' COMMENT '头像',
	note varchar(128) DEFAULT '' COMMENT '备注',
	is_enable tinyint(1) DEFAULT 0 COMMENT '是否启用'
) ENGINE = InnoDB CHARSET = utf8 COMMENT '系统管理员';

INSERT INTO `system_manager` (`id`, `is_deleted`, `create_time`, `update_time`, `login_name`, `login_password`, `real_name`, `department_id`, `login_ip`, `login_time`, `login_error_time`, `login_error_count`, `mobile`, `head_image`, `note`, `is_enable`) VALUES ('1', '0', '2020-07-20 16:00:20', '2020-08-07 14:18:37', 'admin', '84eb004ccd8025243c6b24d2151c9262', '超级管理员', '1', '192.168.0.71', '2020-08-07 14:18:37', NULL, '0', '18888888888', '/manager/2020/08/07/e7216e88d135472eaf94d84ecb265bd2.jpg', '系统超级管理员，不可删除', '1');
-- 管理员角色
CREATE TABLE system_role (
	id int unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
	is_deleted tinyint(1) DEFAULT 0 COMMENT '删除标记',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '修改时间',
	name varchar(32) DEFAULT '' COMMENT '角色名称',
	note varchar(128) DEFAULT '' COMMENT '备注'
) ENGINE = InnoDB CHARSET = utf8 COMMENT '管理员角色';

INSERT INTO `system_role` (`id`, `is_deleted`, `create_time`, `update_time`, `name`, `note`) VALUES ('1', '0', '2020-07-20 17:38:45', '2020-08-04 14:25:38', '超级管理员', '超级管理员角色，默认拥有所有权限');

-- 管理员-角色关系表
CREATE TABLE manager_role_rel (
	id int unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
	is_deleted tinyint(1) DEFAULT 0 COMMENT '删除标记',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '修改时间',
	manager_id int unsigned DEFAULT 0 COMMENT '管理员ID',
	role_id int unsigned DEFAULT 0 COMMENT '角色ID'
) ENGINE = InnoDB CHARSET = utf8 COMMENT '管理员-角色关系表';

INSERT INTO `manager_role_rel` (`id`, `is_deleted`, `create_time`, `update_time`, `manager_id`, `role_id`) VALUES ('1', '0', '2020-07-20 17:39:34', '2020-07-20 17:39:37', '1', '1');

-- 角色-菜单关系表
CREATE TABLE role_menu_rel (
	id int unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
	is_deleted tinyint(1) DEFAULT 0 COMMENT '删除标记',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '修改时间',
	role_id int unsigned DEFAULT 0 COMMENT '角色ID',
	menu_id int unsigned DEFAULT 0 COMMENT '菜单ID'
) ENGINE = InnoDB CHARSET = utf8 COMMENT '角色-菜单关系表';

-- 部门表
CREATE TABLE department (
	id int unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
	is_deleted tinyint(1) DEFAULT 0 COMMENT '删除标记',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '修改时间',
	name varchar(50) DEFAULT '' COMMENT '部门名称',
	parent_id int unsigned DEFAULT 0 COMMENT '父级ID',
	ancestors varchar(128) DEFAULT '' COMMENT '祖级列表',
	note varchar(128) DEFAULT '' COMMENT '备注',
	position int DEFAULT 0 COMMENT '位置排序'
) ENGINE = InnoDB CHARSET = utf8 COMMENT '部门表';

INSERT INTO `department` (`id`, `is_deleted`, `create_time`, `update_time`, `name`, `parent_id`, `ancestors`, `note`, `position`) VALUES ('1', '0', '2020-07-22 15:34:36', '2020-07-22 15:34:38', '总部', '0', '0', '系统初始部门', '1');

-- 管理员登录日志表
CREATE TABLE manager_log (
	id int unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT,
	is_deleted tinyint(1) DEFAULT 0 COMMENT '删除标记',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '修改时间',
	login_name varchar(32) DEFAULT '' COMMENT '登录名',
	action_name varchar(32) DEFAULT '' COMMENT '用户动作',
	description varchar(255) DEFAULT '' COMMENT '工作描述',
	login_ip varchar(32) DEFAULT '' COMMENT '登录ip地址'
) ENGINE = InnoDB CHARSET = utf8 COMMENT '管理员登录日志表';

-- 客户端信息
CREATE TABLE `oauth_client_details` (
  `client_id` varchar(256) NOT NULL,
  `resource_ids` varchar(256) DEFAULT NULL,
  `client_secret` varchar(256) DEFAULT NULL,
  `scope` varchar(256) DEFAULT NULL,
  `authorized_grant_types` varchar(256) DEFAULT NULL,
  `web_server_redirect_uri` varchar(256) DEFAULT NULL,
  `authorities` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  `additional_information` varchar(4096) DEFAULT NULL,
  `autoapprove` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`client_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 系统设置表
CREATE TABLE system_config (
	id int unsigned PRIMARY KEY NOT NULL AUTO_INCREMENT COMMENT 'ID',
	is_deleted tinyint(1) DEFAULT 0 COMMENT '删除标记',
	create_time datetime DEFAULT NULL COMMENT '创建时间',
	update_time datetime DEFAULT NULL COMMENT '修改时间',
	file_server varchar(128) DEFAULT '' COMMENT '文件服务器地址'
) ENGINE = InnoDB CHARSET = utf8 COMMENT '系统设置表';

INSERT INTO `system_config` (`id`, `is_deleted`, `create_time`, `update_time`, `file_server`) VALUES ('1', '0', '2020-08-07 09:58:25', '2020-08-07 10:14:22', 'http://localhost:8083');
