/*
 Navicat Premium Data Transfer

 Source Server         : 腾讯云124.220.24.28
 Source Server Type    : MySQL
 Source Server Version : 80020 (8.0.20)
 Source Host           : 124.220.24.28:3306
 Source Schema         : xd

 Target Server Type    : MySQL
 Target Server Version : 80020 (8.0.20)
 File Encoding         : 65001

 Date: 18/08/2023 17:26:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_accounting_detail
-- ----------------------------
DROP TABLE IF EXISTS `t_accounting_detail`;
CREATE TABLE `t_accounting_detail`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NULL DEFAULT NULL,
  `subject_no` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `subject_balance` decimal(10, 2) NULL DEFAULT NULL,
  `detail_balance` decimal(10, 2) NULL DEFAULT NULL,
  `diff_balance` decimal(10, 2) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_accounting_detail
-- ----------------------------
INSERT INTO `t_accounting_detail` VALUES (1, 5, '1', 11.00, 11.00, 0.00);
INSERT INTO `t_accounting_detail` VALUES (2, 5, '2', 22.00, 22.00, 0.00);
INSERT INTO `t_accounting_detail` VALUES (3, 5, '3', 33.00, 33.00, 0.00);
INSERT INTO `t_accounting_detail` VALUES (4, 5, '4', 44.00, 35.00, 9.00);
INSERT INTO `t_accounting_detail` VALUES (5, 5, '5', 0.00, 55.00, -55.00);
INSERT INTO `t_accounting_detail` VALUES (6, 5, '6', 67.00, 0.00, 67.00);
INSERT INTO `t_accounting_detail` VALUES (7, 6, '1', 11.00, 11.00, 0.00);
INSERT INTO `t_accounting_detail` VALUES (8, 6, '2', 22.00, 22.00, 0.00);
INSERT INTO `t_accounting_detail` VALUES (9, 6, '3', 33.00, 33.00, 0.00);
INSERT INTO `t_accounting_detail` VALUES (10, 6, '4', 44.00, 35.00, 9.00);
INSERT INTO `t_accounting_detail` VALUES (11, 6, '5', 0.00, 55.00, -55.00);
INSERT INTO `t_accounting_detail` VALUES (12, 6, '6', 67.00, 0.00, 67.00);
INSERT INTO `t_accounting_detail` VALUES (13, 7, '1', 11.00, 11.00, 0.00);
INSERT INTO `t_accounting_detail` VALUES (14, 7, '2', 22.00, 22.00, 0.00);
INSERT INTO `t_accounting_detail` VALUES (15, 7, '3', 33.00, 33.00, 0.00);
INSERT INTO `t_accounting_detail` VALUES (16, 7, '4', 44.00, 35.00, 9.00);
INSERT INTO `t_accounting_detail` VALUES (17, 7, '5', 0.00, 55.00, -55.00);
INSERT INTO `t_accounting_detail` VALUES (18, 7, '6', 67.00, 0.00, 67.00);

-- ----------------------------
-- Table structure for t_accounting_group
-- ----------------------------
DROP TABLE IF EXISTS `t_accounting_group`;
CREATE TABLE `t_accounting_group`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` int NULL DEFAULT NULL,
  `mark_flag` tinyint NULL DEFAULT NULL,
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_accounting_group
-- ----------------------------
INSERT INTO `t_accounting_group` VALUES (1, 20230818, NULL, NULL);
INSERT INTO `t_accounting_group` VALUES (2, 20230818, NULL, NULL);
INSERT INTO `t_accounting_group` VALUES (3, 20230818, NULL, NULL);
INSERT INTO `t_accounting_group` VALUES (4, 20230818, NULL, NULL);
INSERT INTO `t_accounting_group` VALUES (5, 20230818, NULL, NULL);
INSERT INTO `t_accounting_group` VALUES (6, 20230818, NULL, NULL);
INSERT INTO `t_accounting_group` VALUES (7, 20230818, NULL, NULL);

-- ----------------------------
-- Table structure for t_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict`  (
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `value` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES ('home', 'el-icon-house', 'icon');
INSERT INTO `t_dict` VALUES ('sysemManage', 'el-icon-s-grid', 'icon');
INSERT INTO `t_dict` VALUES ('menu', 'el-icon-menu', 'icon');
INSERT INTO `t_dict` VALUES ('user', 'el-icon-user-solid', 'icon');
INSERT INTO `t_dict` VALUES ('role', 'el-icon-s-custom', 'icon');
INSERT INTO `t_dict` VALUES ('file', 'el-icon-document', 'icon');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` bigint NOT NULL,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `icon` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `pid` bigint NULL DEFAULT NULL,
  `page_path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, '主页', '/home', 'el-icon-house', '1', NULL, 'Home');
INSERT INTO `t_menu` VALUES (2, '系统管理', NULL, 'el-icon-s-grid', '2', NULL, NULL);
INSERT INTO `t_menu` VALUES (3, '用户管理', '/user', 'el-icon-user-solid', '3', 2, 'User');
INSERT INTO `t_menu` VALUES (4, '角色管理', '/role', 'el-icon-s-custom', '4', 2, 'Role');
INSERT INTO `t_menu` VALUES (5, '菜单管理', '/menu', 'el-icon-menu', '5', 2, 'Menu');
INSERT INTO `t_menu` VALUES (6, '文件管理', '/file', 'el-icon-document', '6', 2, 'File');
INSERT INTO `t_menu` VALUES (11, '财务管理', NULL, 'el-icon-s-grid', '11', NULL, NULL);
INSERT INTO `t_menu` VALUES (12, '对比', '/accounting', 'el-icon-s-custom', '12', 11, 'Accounting');
INSERT INTO `t_menu` VALUES (13, '截取', '/split', 'el-icon-s-custom', '13', 11, 'Split');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint NOT NULL,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `description` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `flag` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '管理员', '管理员', 'ROLE_ADMIN');
INSERT INTO `t_role` VALUES (3, '普通用户', '普通用户', 'ROLE_USER');
INSERT INTO `t_role` VALUES (4, '会员用户', '会员用户', 'ROLE_VIP');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `role_id` bigint NULL DEFAULT NULL,
  `menu_id` bigint NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (3, 1);
INSERT INTO `t_role_menu` VALUES (3, 2);
INSERT INTO `t_role_menu` VALUES (3, 6);
INSERT INTO `t_role_menu` VALUES (4, 1);
INSERT INTO `t_role_menu` VALUES (4, 2);
INSERT INTO `t_role_menu` VALUES (4, 5);
INSERT INTO `t_role_menu` VALUES (4, 6);
INSERT INTO `t_role_menu` VALUES (1, 1);
INSERT INTO `t_role_menu` VALUES (1, 2);
INSERT INTO `t_role_menu` VALUES (1, 3);
INSERT INTO `t_role_menu` VALUES (1, 4);
INSERT INTO `t_role_menu` VALUES (1, 5);
INSERT INTO `t_role_menu` VALUES (1, 6);
INSERT INTO `t_role_menu` VALUES (1, 11);
INSERT INTO `t_role_menu` VALUES (1, 12);
INSERT INTO `t_role_menu` VALUES (1, 13);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint NOT NULL,
  `username` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `email` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `phone` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `address` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `avatar_url` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `role` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (2, 'admin', '96e79218965eb72c92a549dd5a330112', '管理员', 'admin@qq.com', '18888888', '上海', '2022-08-31 18:05:55', 'http://localhost:8081/file/571f96608c984f2890eb645c7b47149c.jpg', 'ROLE_ADMIN');

SET FOREIGN_KEY_CHECKS = 1;
