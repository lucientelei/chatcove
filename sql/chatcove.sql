/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80020
 Source Host           : localhost:3306
 Source Schema         : chatcove

 Target Server Type    : MySQL
 Target Server Version : 80020
 File Encoding         : 65001

 Date: 28/05/2023 16:41:56
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ch_chat_message
-- ----------------------------
DROP TABLE IF EXISTS `ch_chat_message`;
CREATE TABLE `ch_chat_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message_uuid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息唯一UUID',
  `sender_id` bigint UNSIGNED NOT NULL COMMENT '发送者ID',
  `receiver_id` bigint UNSIGNED NOT NULL COMMENT '接收者ID',
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `message_type_id` bigint NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `friend_id`(`receiver_id`) USING BTREE,
  INDEX `message_type_id`(`message_type_id`) USING BTREE,
  INDEX `idx_user_friend_message`(`sender_id`, `receiver_id`, `message`(100)) USING BTREE,
  INDEX `idx_user_friend_message_time_type`(`sender_id`, `receiver_id`, `message`(100), `create_time`, `message_type_id`) USING BTREE,
  CONSTRAINT `ch_chat_message_ibfk_1` FOREIGN KEY (`sender_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_chat_message_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_chat_message_ibfk_3` FOREIGN KEY (`message_type_id`) REFERENCES `ch_message_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_chat_message
-- ----------------------------

-- ----------------------------
-- Table structure for ch_friend_relationship
-- ----------------------------
DROP TABLE IF EXISTS `ch_friend_relationship`;
CREATE TABLE `ch_friend_relationship`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `friend_id` bigint UNSIGNED NOT NULL,
  `friend_nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '好友备注名称',
  `status_id` bigint NOT NULL COMMENT '添加状态',
  `is_top` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '0' COMMENT '是否置顶联系人 1：是 0：否',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `friend_id`(`friend_id`) USING BTREE,
  INDEX `status_id`(`status_id`) USING BTREE,
  INDEX `idx_user_friend_status`(`user_id`, `friend_id`, `status_id`) USING BTREE,
  CONSTRAINT `ch_friend_relationship_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_friend_relationship_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_friend_relationship_ibfk_3` FOREIGN KEY (`status_id`) REFERENCES `ch_relation_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_friend_relationship
-- ----------------------------

-- ----------------------------
-- Table structure for ch_friendship_apply
-- ----------------------------
DROP TABLE IF EXISTS `ch_friendship_apply`;
CREATE TABLE `ch_friendship_apply`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL COMMENT '发起好友申请的用户ID',
  `friend_id` bigint NOT NULL COMMENT '接收好友申请的用户ID',
  `status_id` bigint NOT NULL COMMENT '状态ID',
  `message` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '申请留言',
  `create_time` datetime NULL DEFAULT NULL COMMENT '申请时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `ch_friendship_apply_ibfk_1`(`status_id`) USING BTREE,
  CONSTRAINT `ch_friendship_apply_ibfk_1` FOREIGN KEY (`status_id`) REFERENCES `ch_relation_status` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_friendship_apply
-- ----------------------------

-- ----------------------------
-- Table structure for ch_group_members
-- ----------------------------
DROP TABLE IF EXISTS `ch_group_members`;
CREATE TABLE `ch_group_members`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL,
  `member_id` bigint UNSIGNED NOT NULL,
  `group_nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '群昵称',
  `join_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_member`(`group_id`, `member_id`, `join_time`) USING BTREE,
  INDEX `member_id`(`member_id`) USING BTREE,
  CONSTRAINT `ch_group_members_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_group_members_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `ch_groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_group_members
-- ----------------------------
INSERT INTO `ch_group_members` VALUES (1, 1, 426809250224799813, 'ambisiss', '2023-05-28 16:40:58', NULL);
INSERT INTO `ch_group_members` VALUES (2, 1, 426809436573532229, 'lucienxxx', '2023-05-28 16:40:58', NULL);
INSERT INTO `ch_group_members` VALUES (3, 1, 432545192306802757, '阿斯顿', '2023-05-28 16:40:58', NULL);
INSERT INTO `ch_group_members` VALUES (4, 1, 432545225664102469, '阿萨德', '2023-05-28 16:40:58', NULL);

-- ----------------------------
-- Table structure for ch_group_message
-- ----------------------------
DROP TABLE IF EXISTS `ch_group_message`;
CREATE TABLE `ch_group_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `message_uuid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '消息唯一UUID',
  `group_id` bigint NOT NULL,
  `sender_id` bigint UNSIGNED NOT NULL,
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `message_type` bigint NOT NULL,
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `sender_id`(`sender_id`) USING BTREE,
  INDEX `message_type`(`message_type`) USING BTREE,
  INDEX `idx_group_message`(`group_id`, `sender_id`, `message`(100), `message_type`, `create_time`) USING BTREE,
  CONSTRAINT `ch_group_message_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `ch_groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_group_message_ibfk_2` FOREIGN KEY (`sender_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_group_message_ibfk_3` FOREIGN KEY (`message_type`) REFERENCES `ch_message_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_group_message
-- ----------------------------

-- ----------------------------
-- Table structure for ch_groups
-- ----------------------------
DROP TABLE IF EXISTS `ch_groups`;
CREATE TABLE `ch_groups`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ids`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_groups
-- ----------------------------
INSERT INTO `ch_groups` VALUES (1, '相亲相爱一家人', '2023-05-28 16:40:58', NULL);

-- ----------------------------
-- Table structure for ch_groups_admin_relation
-- ----------------------------
DROP TABLE IF EXISTS `ch_groups_admin_relation`;
CREATE TABLE `ch_groups_admin_relation`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `group_id` bigint NOT NULL COMMENT '群组ID',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '管理员ID',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ids`(`id`) USING BTREE,
  INDEX `ch_groups_admin_relation_ibfk_1`(`group_id`) USING BTREE,
  INDEX `ch_groups_admin_relation_ibfk_2`(`user_id`) USING BTREE,
  CONSTRAINT `ch_groups_admin_relation_ibfk_1` FOREIGN KEY (`group_id`) REFERENCES `ch_groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_groups_admin_relation_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_groups_admin_relation
-- ----------------------------
INSERT INTO `ch_groups_admin_relation` VALUES (1, 1, 426809250224799813, '2023-05-28 16:40:58');

-- ----------------------------
-- Table structure for ch_message_status
-- ----------------------------
DROP TABLE IF EXISTS `ch_message_status`;
CREATE TABLE `ch_message_status`  (
  `id` bigint NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_message_status
-- ----------------------------
INSERT INTO `ch_message_status` VALUES (1, 'unread', '消息未读.');
INSERT INTO `ch_message_status` VALUES (2, 'read', '消息已读.');
INSERT INTO `ch_message_status` VALUES (3, 'deleted', '消息删除.');

-- ----------------------------
-- Table structure for ch_message_type
-- ----------------------------
DROP TABLE IF EXISTS `ch_message_type`;
CREATE TABLE `ch_message_type`  (
  `id` bigint NOT NULL,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_message_type
-- ----------------------------
INSERT INTO `ch_message_type` VALUES (1, 'TEXT', '文本消息');
INSERT INTO `ch_message_type` VALUES (2, 'PICTURE', '图片消息');
INSERT INTO `ch_message_type` VALUES (3, 'VIDEO', '视频消息');

-- ----------------------------
-- Table structure for ch_relation_status
-- ----------------------------
DROP TABLE IF EXISTS `ch_relation_status`;
CREATE TABLE `ch_relation_status`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_relation_status
-- ----------------------------
INSERT INTO `ch_relation_status` VALUES (1, '未确认', '表示一个用户向另一个用户发出了好友请求，但该请求还未被接受', '2023-04-19 23:58:51', NULL);
INSERT INTO `ch_relation_status` VALUES (2, '已确认', '表示双方用户已经互相确认了好友关系', '2023-04-19 23:58:51', NULL);
INSERT INTO `ch_relation_status` VALUES (3, '已拒绝', '表示一个用户拒绝了另一个用户的好友请求或者解除好友关系', '2023-04-19 23:58:51', NULL);
INSERT INTO `ch_relation_status` VALUES (4, '已屏蔽', '表示一个用户屏蔽了另一个用户，不再接收其消息或其他活动', '2023-04-19 23:58:51', NULL);
INSERT INTO `ch_relation_status` VALUES (5, '黑名单', '表示一个用户将另一个用户添加到了黑名单中，不能再进行任何交互', '2023-04-19 23:58:51', NULL);
INSERT INTO `ch_relation_status` VALUES (6, '已删除', '表示一个用户将另一个用户从好友列表中删除', '2023-04-19 23:58:51', NULL);

-- ----------------------------
-- Table structure for ch_user
-- ----------------------------
DROP TABLE IF EXISTS `ch_user`;
CREATE TABLE `ch_user`  (
  `id` bigint UNSIGNED NOT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` char(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gender` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `signature` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_user
-- ----------------------------
INSERT INTO `ch_user` VALUES (426809250224799813, 'ambisiss', '$2a$10$OgzwXUyRX.3enbKVt9z0IuPuyB/hYqL1fiqjzde/rX7XffOmQtqRW', '1', NULL, '13556787083', 'q799774821@163.com', NULL, '2023-05-12 20:47:37', NULL);
INSERT INTO `ch_user` VALUES (426809436573532229, 'lucienxxx', '$2a$10$bEZcfVhfal2OkMBV2beQWOksraz/G1yTN73kFWxTKLFMcY8Xe4Y2i', NULL, NULL, NULL, NULL, NULL, '2023-05-12 20:48:22', NULL);
INSERT INTO `ch_user` VALUES (432545192306802757, '阿斯顿', '$2a$10$YwqIs1sCxeOB3aAGpoKHT.pT22B6goj2/PJiLTlLL3HaZg/SCiKdy', NULL, NULL, NULL, NULL, NULL, '2023-05-28 16:40:12', NULL);
INSERT INTO `ch_user` VALUES (432545225664102469, '阿萨德', '$2a$10$8VeYru.OC0V4W.xeLoI.3uH4wWJJsjXFqZDRn5Vcc5X/uSNg77t/6', NULL, NULL, NULL, NULL, NULL, '2023-05-28 16:40:20', NULL);

-- ----------------------------
-- Table structure for ch_user_friend
-- ----------------------------
DROP TABLE IF EXISTS `ch_user_friend`;
CREATE TABLE `ch_user_friend`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `friend_id` bigint UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `friend_id`(`friend_id`) USING BTREE,
  INDEX `idx_user_friend_id`(`user_id`, `friend_id`) USING BTREE,
  CONSTRAINT `ch_user_friend_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_user_friend_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_user_friend
-- ----------------------------

-- ----------------------------
-- Table structure for quartz_job
-- ----------------------------
DROP TABLE IF EXISTS `quartz_job`;
CREATE TABLE `quartz_job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 100 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '定时任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of quartz_job
-- ----------------------------
INSERT INTO `quartz_job` VALUES (1, '系统默认（无参）', 'DEFAULT', 'ryTask.ryNoParams', '0/10 * * * * ?', '3', '1', '1', 'admin', '2023-05-20 17:13:04', '', NULL, '');
INSERT INTO `quartz_job` VALUES (2, '系统默认（有参）', 'DEFAULT', 'ryTask.ryParams(\'ry\')', '0/15 * * * * ?', '3', '1', '1', 'admin', '2023-05-20 17:13:04', '', NULL, '');
INSERT INTO `quartz_job` VALUES (3, '系统默认（多参）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)', '0/20 * * * * ?', '3', '1', '1', 'admin', '2023-05-20 17:13:04', '', NULL, '');

-- ----------------------------
-- Table structure for quartz_job_log
-- ----------------------------
DROP TABLE IF EXISTS `quartz_job_log`;
CREATE TABLE `quartz_job_log`  (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '定时任务调度日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of quartz_job_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
