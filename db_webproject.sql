/*
Navicat MySQL Data Transfer

Source Server         : db_webpeoject
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : db_webproject

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2018-11-27 11:17:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_account`
-- ----------------------------
DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE `tb_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(32) DEFAULT NULL,
  `password` varchar(32) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_account
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_message`
-- ----------------------------
DROP TABLE IF EXISTS `tb_message`;
CREATE TABLE `tb_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `broadcastMessage` tinyint(4) NOT NULL DEFAULT '1',
  `from_account_id` int(11) NOT NULL,
  `to_account_id` int(11) DEFAULT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_message
-- ----------------------------
