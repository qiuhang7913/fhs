/*
SQLyog Ultimate v12.08 (32 bit)
MySQL - 5.6.38-log : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`test` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `test`;

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT '0',
  `user_agent` varchar(128) DEFAULT '' COMMENT '浏览器标识',
  `ip_address` varchar(128) DEFAULT '' COMMENT 'IP地址',
  `type` int(11) DEFAULT '1' COMMENT '1：网页登录\r\n2：OpenId登录',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `modified_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT '1' COMMENT '1：正常',
  `is_delete` tinyint(4) DEFAULT '0' COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `login` */

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `id` varchar(64) NOT NULL,
  `icon` varchar(255) DEFAULT NULL COMMENT '资源图标',
  `name` varchar(24) DEFAULT NULL COMMENT '资源名字',
  `url` varchar(255) NOT NULL COMMENT '资源路径',
  `type` int(1) DEFAULT '1' COMMENT '资源类型（1:一级菜单，2:二级菜单，3:链接）',
  `parent_id` bigint(64) DEFAULT '0' COMMENT '父资源ID',
  `sort` int(11) DEFAULT '0' COMMENT '资源顺序',
  `status` int(1) DEFAULT '0' COMMENT '0：启用',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_time` varchar(20) DEFAULT NULL COMMENT '修改时间',
  `update_user` varchar(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `menu` */

insert  into `menu`(`id`,`icon`,`name`,`url`,`type`,`parent_id`,`sort`,`status`,`create_time`,`create_user`,`update_time`,`update_user`) values ('ssssdffee',NULL,'test','/aa.html',1,0,0,0,NULL,NULL,NULL,NULL);

/*Table structure for table `menu_func` */

DROP TABLE IF EXISTS `menu_func`;

CREATE TABLE `menu_func` (
  `id` varchar(64) CHARACTER SET utf8mb4 NOT NULL,
  `menu_id` varchar(64) CHARACTER SET utf8mb4 NOT NULL COMMENT '菜单主键',
  `func_type` int(1) NOT NULL COMMENT '0:增加 1:修改 2:删除 3:查询',
  `func_name` varchar(32) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '名字',
  `func_des` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL COMMENT '备注描述',
  `create_time` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  `create_user` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  `update_time` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  `update_user` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `MENU_FUNC_FOREIGN_KEY_MENU_ID` (`menu_id`),
  CONSTRAINT `MENU_FUNC_FOREIGN_KEY_MENU_ID` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `menu_func` */

insert  into `menu_func`(`id`,`menu_id`,`func_type`,`func_name`,`func_des`,`create_time`,`create_user`,`update_time`,`update_user`) values ('sss111','ssssdffee',0,'增加',NULL,NULL,NULL,NULL,NULL),('sss222','ssssdffee',1,'更新',NULL,NULL,NULL,NULL,NULL),('sss333','ssssdffee',2,'删除',NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `role` */

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` varchar(64) NOT NULL,
  `role_name` varchar(24) DEFAULT '' COMMENT '角色名字(英文）',
  `role_des` varchar(24) DEFAULT '' COMMENT '角色描述',
  `status` int(1) DEFAULT '1' COMMENT '1：正常',
  `is_delete` int(1) DEFAULT '0' COMMENT '是否删除',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '修改时间',
  `update_time` varchar(20) DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `role` */

insert  into `role`(`id`,`role_name`,`role_des`,`status`,`is_delete`,`create_time`,`create_user`,`update_time`,`update_user`) values ('eeesssff','test','测试',1,0,NULL,NULL,NULL,NULL);

/*Table structure for table `role_menu` */

DROP TABLE IF EXISTS `role_menu`;

CREATE TABLE `role_menu` (
  `id` varchar(64) NOT NULL,
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色ID',
  `menu_id` varchar(64) DEFAULT NULL COMMENT '菜单ID',
  `create_time` varchar(20) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `ROLE_MENU_FOREIGN_KEY_MENU_ID` (`menu_id`),
  KEY `ROLE_MENU_FOREIGN_KEY_ROLE_ID` (`role_id`),
  CONSTRAINT `ROLE_MENU_FOREIGN_KEY_MENU_ID` FOREIGN KEY (`menu_id`) REFERENCES `menu` (`id`),
  CONSTRAINT `ROLE_MENU_FOREIGN_KEY_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `role_menu` */

insert  into `role_menu`(`id`,`role_id`,`menu_id`,`create_time`,`create_user`,`update_time`,`update_user`) values ('aaaassssdddd','eeesssff','ssssdffee',NULL,NULL,NULL,NULL);

/*Table structure for table `test_table` */

DROP TABLE IF EXISTS `test_table`;

CREATE TABLE `test_table` (
  `id` varchar(255) DEFAULT NULL,
  `des` varchar(255) DEFAULT NULL,
  `create_time` varchar(20) DEFAULT NULL,
  `create_user` varchar(20) DEFAULT NULL,
  `update_time` varchar(20) DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `test_table` */

insert  into `test_table`(`id`,`des`,`create_time`,`create_user`,`update_time`,`update_user`) values ('40289f6467a6bc1b0167a6bc37c90000','alter info',NULL,'qiuhang','2018-12-14 16:00:28',NULL),('40289f6467a6d1660167a6d1a3c30000','bbbb',NULL,NULL,NULL,NULL),('40289f6467a705470167a70677e70000','我的测试',NULL,NULL,NULL,NULL);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` varchar(64) NOT NULL,
  `real_name` varchar(32) DEFAULT '' COMMENT '真实姓名',
  `login_name` varchar(24) DEFAULT NULL COMMENT '用户名',
  `password` varchar(32) DEFAULT '' COMMENT '密码',
  `sex` int(1) DEFAULT '0' COMMENT '0:男 1:女',
  `birthday` varchar(20) DEFAULT NULL COMMENT '生日',
  `address` varchar(64) DEFAULT NULL COMMENT '用户详细地址',
  `province` varchar(10) DEFAULT NULL COMMENT '省',
  `city` varchar(10) DEFAULT NULL COMMENT '市',
  `area` varchar(10) DEFAULT NULL COMMENT '区',
  `status` int(11) DEFAULT '1' COMMENT '1：正常\r\n2：停用',
  `type` int(11) DEFAULT '1' COMMENT '1：普通\r\n2：管理员',
  `createTime` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `updateTime` varchar(20) DEFAULT NULL COMMENT '更新时间',
  `is_delete` int(1) DEFAULT '0' COMMENT '是否删除',
  `create_time` varchar(255) DEFAULT NULL,
  `create_user` varchar(255) DEFAULT NULL,
  `update_time` varchar(255) DEFAULT NULL,
  `update_user` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `LOGIN_NAME_UNIQUE` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `user` */

insert  into `user`(`id`,`real_name`,`login_name`,`password`,`sex`,`birthday`,`address`,`province`,`city`,`area`,`status`,`type`,`createTime`,`updateTime`,`is_delete`,`create_time`,`create_user`,`update_time`,`update_user`) values ('1231231231','邱航','qiuhang','123456',0,NULL,NULL,NULL,NULL,NULL,1,1,NULL,NULL,0,NULL,NULL,NULL,NULL);

/*Table structure for table `user_role` */

DROP TABLE IF EXISTS `user_role`;

CREATE TABLE `user_role` (
  `id` varchar(64) NOT NULL,
  `user_id` varchar(64) DEFAULT NULL COMMENT '用户ID',
  `role_id` varchar(64) DEFAULT NULL COMMENT '角色ID',
  `create_time` varchar(20) DEFAULT NULL COMMENT '创建时间',
  `create_user` varchar(20) DEFAULT NULL COMMENT '创建人',
  `update_time` varchar(20) DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  KEY `USER_ROLE_FOREIGN_KEY_ROLE_ID` (`role_id`),
  KEY `USER_ROLE_FOREIGN_KEY_USER_ID` (`user_id`),
  CONSTRAINT `USER_ROLE_FOREIGN_KEY_ROLE_ID` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `USER_ROLE_FOREIGN_KEY_USER_ID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT;

/*Data for the table `user_role` */

insert  into `user_role`(`id`,`user_id`,`role_id`,`create_time`,`create_user`,`update_time`,`update_user`) values ('jjjdddffff','1231231231','eeesssff',NULL,NULL,NULL,NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
