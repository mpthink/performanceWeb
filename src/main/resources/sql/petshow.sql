/**
 * 根据Ali的MYSQL规约，对表名、字段都做了相应的限制
 * 详细参考开发手册
 */

-- -----------------------------------------------------
-- Schema petshow
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `petshow`;
CREATE SCHEMA IF NOT EXISTS `petshow` DEFAULT CHARACTER SET UTF8;
USE `petshow`;


-- ----------------------------
--  Table structure for `sys_dept`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;

CREATE TABLE `sys_dept` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `dept_name` varchar(100) NOT NULL COMMENT '部门名称',
  `dept_desc` varchar(300) DEFAULT NULL COMMENT '描述',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门表';

-- ----------------------------
--  Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `user_name` varchar(50) NOT NULL COMMENT '用户',
  `title` varchar(300) DEFAULT NULL COMMENT '日志',
  `url` varchar(300) DEFAULT NULL COMMENT '地址',
  `params` varchar(300) DEFAULT NULL COMMENT '参数',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='日志表';

-- ----------------------------
--  Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '上级ID',
  `perm_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '权限名称',
  `perm_type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '类型 0:菜单 1:功能',
  `perm_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '状态 0:正常 1:禁用',
  `sort` smallint unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `url` varchar(100) DEFAULT NULL COMMENT '地址',
  `perm_code` varchar(30) DEFAULT NULL COMMENT '权限编码',
  `icon` varchar(30) DEFAULT NULL COMMENT '图标',
  `perm_desc` varchar(100) DEFAULT NULL COMMENT '权限描述',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限表';

-- ----------------------------
--  Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `role_name` varchar(30) NOT NULL COMMENT '角色',
  `role_desc` varchar(300) DEFAULT NULL COMMENT '角色描述',
  `role_status` tinyint unsigned DEFAULT '0' COMMENT '状态,0:启用,1:禁用',
  `sort` smallint unsigned NOT NULL DEFAULT '0' COMMENT '排序',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色表';

-- ----------------------------
--  Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `perm_id` bigint(20) unsigned NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限表';

-- ----------------------------
--  Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键ID',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门主键',
  `user_name` varchar(40) NOT NULL COMMENT '用户名',
  `password` varchar(40) NOT NULL COMMENT '密码',
  `user_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT '用户状态,0:启用,1:禁用',
  `user_desc` varchar(300) DEFAULT NULL COMMENT '描述',
  `avatar` varchar(300) DEFAULT '/AdminLTE/img/photo1.png' COMMENT '头像',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
--  Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户角色表';

-- ----------------------------
--  Table structure for `sys_setting`
-- ----------------------------
DROP TABLE IF EXISTS `sys_setting`;

CREATE TABLE `sys_setting` (
  `id` bigint unsigned NOT NULL COMMENT '主键',
  `sys_key` varchar(50) NOT NULL COMMENT 'KEY',
  `sys_name` varchar(50) NOT NULL COMMENT '名称',
  `sys_value` varchar(300) DEFAULT NULL COMMENT '值',
  `sort` int(11) DEFAULT '0' COMMENT '排序',
  `sys_desc` varchar(300) DEFAULT NULL COMMENT '说明',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
--  Table structure for `netact_lab`
-- ----------------------------
DROP TABLE IF EXISTS `netact_lab`;

CREATE TABLE `netact_lab` (
  `id` bigint unsigned NOT NULL COMMENT '主键',
  `lab_name` char(40) NOT NULL COMMENT 'lab 名称，e.g:clab1507',
  `lab_version` varchar(300) DEFAULT NULL COMMENT 'lab 版本',
  `lab_url` varchar(200) DEFAULT NULL COMMENT 'lab link',
  `lab_type` tinyint unsigned NOT NULL DEFAULT '0' COMMENT 'lab 类型, 0:cloudLab, 1:cloud_small, 2: cloud_node manager, 3:cloud_medium, 4:cloud_large, 5:sprintLab, 6:unknown',
  `db_ipv4` char(20) DEFAULT NULL COMMENT 'db node IP address',
  `viis_ipv4` char(20) DEFAULT NULL COMMENT 'viis node IP address',
  `vda1_ipv4` char(20) DEFAULT NULL COMMENT 'node manager VDA-1 IP address',
  `lbwas_ipv4` char(20) DEFAULT NULL COMMENT 'lbwas node IP address',
  `lab_status` tinyint unsigned NOT NULL DEFAULT '0' COMMENT 'lab状态,0:未知,1:可用,2:激活, 激活后会定时更新lab 版本信息,添加favorite后激活',
  `status_info` text COMMENT 'lab 状态详情',
  `lab_desc` varchar(200) DEFAULT NULL COMMENT 'lab 描述/remark等信息',
  `root_password` char(20) DEFAULT 'arthur' COMMENT 'lab root 密码',
  `db_password` char(20) DEFAULT 'omc' COMMENT 'lab db 密码',
  `omc_password` char(20) DEFAULT 'omc' COMMENT 'lab omc 密码',
  `viis_password` char(20) DEFAULT 'nasroot' COMMENT 'viis root 密码',
  `lab_project` varchar(40) DEFAULT NULL COMMENT 'lab所属project',
  `lab_mpp` varchar(40) DEFAULT NULL COMMENT 'lab所在MPP',
  `lab_owner` varchar(60) DEFAULT NULL COMMENT 'lab 所属team',
  `lab_config` varchar(40) DEFAULT NULL COMMENT 'lab 配置情况, e.g: Cloud_Zu v3, Cloud_Zs, Cloud_Xs',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  `gmt_release` datetime DEFAULT NULL COMMENT '过期时间',
  PRIMARY KEY (`id`),
  KEY `idx_lab_name`(`lab_name`),
  KEY `idx_db_ipv4`(`db_ipv4`),
  KEY `idx_viis_ipv4`(`viis_ipv4`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Lab 信息表';

-- ----------------------------
--  Table structure for `user_lab`
-- ----------------------------
DROP TABLE IF EXISTS `user_lab`;
CREATE TABLE `user_lab` (
	`id` bigint(20) unsigned NOT NULL COMMENT '主键',
	`user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
	`lab_id` bigint(20) unsigned NOT NULL COMMENT 'Lab ID',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户lab表';

-- ----------------------------
--  Table structure for `pet_product_class`
-- ----------------------------
DROP TABLE IF EXISTS `pet_product_class`;
CREATE TABLE `pet_product_class` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `pid` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '上级ID',
  `class_name` varchar(30) COLLATE utf8_bin NOT NULL COMMENT '产品分类名称',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品分类表';

-- ----------------------------
--  Table structure for `pet_product`
-- ----------------------------
DROP TABLE IF EXISTS `pet_product`;
CREATE TABLE `pet_product` (
  `id` bigint(20) unsigned NOT NULL COMMENT '主键',
  `product_name` varchar(30) NOT NULL COMMENT '产品名称',
  `product_class` bigint(20) unsigned NOT NULL COMMENT '产品类别',
  `product_release` varchar(20) DEFAULT NULL COMMENT '产品版本',
  `product_desc` varchar(300) DEFAULT NULL COMMENT '产品描述',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

-- ----------------------------
--  Table structure for `pet_cpu_raw`
-- ----------------------------
DROP TABLE IF EXISTS `pet_cpu_raw`;
CREATE TABLE `pet_cpu_raw` (
	`id` bigint(20) unsigned NOT NULL COMMENT '主键',
	`product_id` bigint(20) unsigned NOT NULL COMMENT '测试对象',
	`gmt_generate` datetime DEFAULT NULL COMMENT '产生时间',
	`cpu_utilization` double(6,2) DEFAULT NULL COMMENT 'CPU利用率',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CPU 性能原始数据表';

-- ----------------------------
--  Table structure for `pet_cpu_hour`
-- ----------------------------
DROP TABLE IF EXISTS `pet_cpu_hour`;
CREATE TABLE `pet_cpu_hour` (
	`id` bigint(20) unsigned NOT NULL COMMENT '主键',
	`product_id` bigint(20) unsigned NOT NULL COMMENT '测试对象',
	`gmt_generate` datetime DEFAULT NULL COMMENT '小时时间',
	`cpu_utilization` double(6,2) DEFAULT NULL COMMENT 'CPU利用率',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='CPU 性能聚合表-小时';
