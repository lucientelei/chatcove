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

 Date: 27/04/2023 11:51:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for ch_chat_records
-- ----------------------------
DROP TABLE IF EXISTS `ch_chat_records`;
CREATE TABLE `ch_chat_records`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `friend_id` bigint UNSIGNED NOT NULL,
  `message` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `message_type_id` bigint NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `friend_id`(`friend_id`) USING BTREE,
  INDEX `message_type_id`(`message_type_id`) USING BTREE,
  INDEX `idx_user_friend_message`(`user_id`, `friend_id`, `message`(100)) USING BTREE,
  INDEX `idx_user_friend_message_time_type`(`user_id`, `friend_id`, `message`(100), `create_time`, `message_type_id`) USING BTREE,
  CONSTRAINT `ch_chat_records_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_chat_records_ibfk_2` FOREIGN KEY (`friend_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_chat_records_ibfk_3` FOREIGN KEY (`message_type_id`) REFERENCES `ch_message_type` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_chat_records
-- ----------------------------

-- ----------------------------
-- Table structure for ch_friend_relationship
-- ----------------------------
DROP TABLE IF EXISTS `ch_friend_relationship`;
CREATE TABLE `ch_friend_relationship`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` bigint UNSIGNED NOT NULL,
  `friend_id` bigint UNSIGNED NOT NULL,
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
  `join_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_group_member`(`group_id`, `member_id`, `join_time`) USING BTREE,
  INDEX `member_id`(`member_id`) USING BTREE,
  CONSTRAINT `ch_group_members_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT `ch_group_members_ibfk_2` FOREIGN KEY (`group_id`) REFERENCES `ch_groups` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_group_members
-- ----------------------------

-- ----------------------------
-- Table structure for ch_group_message
-- ----------------------------
DROP TABLE IF EXISTS `ch_group_message`;
CREATE TABLE `ch_group_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

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
  `description` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `admin_id` bigint UNSIGNED NOT NULL,
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_ids`(`id`) USING BTREE,
  INDEX `admin_id`(`admin_id`) USING BTREE,
  CONSTRAINT `ch_groups_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `ch_user` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_groups
-- ----------------------------

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
INSERT INTO `ch_user` VALUES (1100717328552689664, 'ambisiss', '$2a$10$a5IJ5v6lS9zJnvfLuJgsI.6dEBtzofqnweQ6wgHdXn5Yjxriki2Ki', NULL, NULL, NULL, NULL, NULL, '2023-04-26 09:37:42', NULL);

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
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of ch_user_friend
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
