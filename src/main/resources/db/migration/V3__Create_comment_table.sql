CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) NOT NULL,
  `commentator` bigint(20) NOT NULL,
  `type` int(11) NOT NULL,
  `gmt_create` bigint(20) NOT NULL,
  `gmt_modified` bigint(20) NOT NULL,
  `comment` varchar(1024) DEFAULT NULL,
  `comment_count` int(11) DEFAULT '0',
  `like_count` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
);