/*
 Navicat Premium Data Transfer

 Source Server         : 本地Mysql
 Source Server Type    : MySQL
 Source Server Version : 80030
 Source Host           : localhost:3306
 Source Schema         : goodnight2022

 Target Server Type    : MySQL
 Target Server Version : 80030
 File Encoding         : 65001

 Date: 29/12/2024 15:57:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for blessing
-- ----------------------------
DROP TABLE IF EXISTS `blessing`;
CREATE TABLE `blessing`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `blessing` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'IP所在地',
  `date` datetime NOT NULL,
  `status` int NULL DEFAULT NULL COMMENT '当为0时不能被随机到',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uuid`(`uid` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for like
-- ----------------------------
DROP TABLE IF EXISTS `like`;
CREATE TABLE `like`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `bid` int NOT NULL COMMENT '祝福ID',
  `uid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `bid`(`bid` ASC, `uid`(35) ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
