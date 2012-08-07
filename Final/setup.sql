DROP TABLE Kitchen;
DROP TABLE Request;
DROP TABLE Sacrifice;
DROP TABLE RequestSacrifice;
DROP TABLE Vote;

CREATE TABLE `Kitchen` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `Kitchen` (`id`, `name`)
VALUES
	('13', '14th Floor Kitchen'),
	('14', '11th Floor Kitchen');

CREATE TABLE `Request` (
  `id` varchar(255) NOT NULL,
  `creationTime` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `frozen` tinyint(1) NOT NULL,
  `hidden` tinyint(1) NOT NULL,
  `minusVotes` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `plusVotes` int(11) NOT NULL,
  `whyFrozen` varchar(255) DEFAULT NULL,
  `kitchen_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKA4878A6F9CAE1B99` (`kitchen_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `Request` (`id`, `creationTime`, `description`, `frozen`, `hidden`, `minusVotes`, `name`, `plusVotes`, `whyFrozen`, `kitchen_id`)
VALUES
	('130', 'Sat Aug 04 21:39:34 EDT 2012', 'So good...', 0, 0, 1, 'Hazelnuts', 4, NULL, '13'),
	('135', 'Sat Aug 04 21:40:12 EDT 2012', 'Crunchy!', 0, 0, 2, 'Triscuits', 4, NULL, '13'),
	('155', 'Sat Aug 04 21:42:07 EDT 2012', 'Colorful!', 0, 0, 0, 'Fruit Loops', 6, NULL, '13'),
	('163', 'Sat Aug 04 21:44:33 EDT 2012', 'Yay!', 0, 0, 0, 'Pizza!', 1, NULL, '14'),
	('169', 'Sat Aug 04 21:48:41 EDT 2012', 'Need the salt...', 0, 0, 1, 'Salted Peanuts', 6, NULL, '13'),
	('190', 'Sun Aug 05 22:53:57 EDT 2012', 'For a bean', 0, 0, 0, 'Hummus', 7, NULL, '13'),
	('201', 'Tue Aug 07 13:46:22 EDT 2012', 'Stonyfield if possible', 0, 0, 0, 'Plain Yogurt', 8, NULL, '13'),
	('236', 'Tue Aug 07 14:21:35 EDT 2012', 'Yay!', 0, 0, 0, 'Nutella', 1, NULL, '13');

CREATE TABLE `Sacrifice` (
  `id` varchar(255) NOT NULL,
  `creationTime` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `hidden` tinyint(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `kitchen_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKAC0AF7119CAE1B99` (`kitchen_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `Sacrifice` (`id`, `creationTime`, `description`, `hidden`, `name`, `kitchen_id`)
VALUES
	('132', 'Sat Aug 04 21:39:56 EDT 2012', 'Too high in Fat', 0, 'Cashews', '13'),
	('137', 'Sat Aug 04 21:40:43 EDT 2012', 'Too Artificial', 0, 'Cheezits', '13'),
	('141', 'Sat Aug 04 21:41:13 EDT 2012', 'Boring...', 0, 'Wheat Thins', '13'),
	('159', 'Sat Aug 04 21:42:39 EDT 2012', 'Hate \'em', 0, 'Cheerios', '13'),
	('165', 'Sat Aug 04 21:44:50 EDT 2012', 'Boo...', 0, 'Plain Yogurt', '14'),
	('196', 'Sun Aug 05 22:55:01 EDT 2012', 'All the candy...', 0, 'Candy', '13'),
	('212', 'Tue Aug 07 13:51:45 EDT 2012', 'These are too tempting!  I am trying to stop eating them, but I just can\'t.  Please remove them from the kitchen if at all possible!', 0, 'Peanut M&Ms', '13'),
	('222', 'Tue Aug 07 14:20:02 EDT 2012', 'Too many varieties', 0, 'Strawberry Jam', '13');

CREATE TABLE `RequestSacrifice` (
  `id` varchar(255) NOT NULL,
  `creationTime` varchar(255) DEFAULT NULL,
  `frozen` tinyint(1) NOT NULL,
  `hidden` tinyint(1) NOT NULL,
  `minusVotes` int(11) NOT NULL,
  `plusVotes` int(11) NOT NULL,
  `whyFrozen` varchar(255) DEFAULT NULL,
  `request_id` varchar(255) DEFAULT NULL,
  `sacrifice_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKB236B982E12AAFB9` (`request_id`),
  KEY `FKB236B982C50C38B9` (`sacrifice_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `RequestSacrifice` (`id`, `creationTime`, `frozen`, `hidden`, `minusVotes`, `plusVotes`, `whyFrozen`, `request_id`, `sacrifice_id`)
VALUES
	('133', 'Sat Aug 04 21:39:56 EDT 2012', 0, 0, 0, 1, NULL, '130', '132'),
	('138', 'Sat Aug 04 21:40:43 EDT 2012', 0, 0, 2, 3, NULL, '135', '137'),
	('142', 'Sat Aug 04 21:41:13 EDT 2012', 0, 0, 0, 2, NULL, '135', '141'),
	('157', 'Sat Aug 04 21:42:18 EDT 2012', 0, 0, 0, 1, NULL, '155', '141'),
	('160', 'Sat Aug 04 21:42:39 EDT 2012', 0, 0, 0, 2, NULL, '155', '159'),
	('166', 'Sat Aug 04 21:44:50 EDT 2012', 0, 0, 0, 1, NULL, '163', '165'),
	('174', 'Sat Aug 04 21:49:09 EDT 2012', 0, 0, 2, 5, NULL, '169', '132'),
	('197', 'Sun Aug 05 22:55:02 EDT 2012', 0, 0, 0, 1, NULL, '190', '196'),
	('213', 'Tue Aug 07 13:51:45 EDT 2012', 0, 0, 0, 2, NULL, '201', '212'),
	('216', 'Tue Aug 07 14:18:14 EDT 2012', 0, 0, 0, 1, NULL, '201', '132'),
	('218', 'Tue Aug 07 14:19:35 EDT 2012', 0, 0, 0, 1, NULL, '201', '137'),
	('220', 'Tue Aug 07 14:19:40 EDT 2012', 0, 0, 0, 1, NULL, '201', '141'),
	('223', 'Tue Aug 07 14:20:02 EDT 2012', 0, 0, 0, 3, NULL, '201', '222'),
	('228', 'Tue Aug 07 14:20:22 EDT 2012', 0, 0, 0, 1, NULL, '190', '132'),
	('230', 'Tue Aug 07 14:20:27 EDT 2012', 0, 0, 0, 1, NULL, '190', '212'),
	('238', 'Tue Aug 07 14:21:46 EDT 2012', 0, 0, 0, 1, NULL, '236', '222'),
	('249', 'Tue Aug 07 14:26:27 EDT 2012', 0, 0, 0, 1, NULL, '135', '132'),
	('254', 'Tue Aug 07 14:31:21 EDT 2012', 0, 0, 0, 1, NULL, '201', '159');

CREATE TABLE `Vote` (
  `id` varchar(255) NOT NULL,
  `time` varchar(255) DEFAULT NULL,
  `value` int(11) NOT NULL,
  `request_id` varchar(255) DEFAULT NULL,
  `requestSacrifice_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK28C70AE12AAFB9` (`request_id`),
  KEY `FK28C70AA45988DB` (`requestSacrifice_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

INSERT INTO `Vote` (`id`, `time`, `value`, `request_id`, `requestSacrifice_id`)
VALUES
	('131', 'Sat Aug 04 21:39:34 EDT 2012', 1, '130', NULL),
	('134', 'Sat Aug 04 21:39:56 EDT 2012', 1, NULL, '133'),
	('136', 'Sat Aug 04 21:40:12 EDT 2012', 1, '135', NULL),
	('139', 'Sat Aug 04 21:40:43 EDT 2012', 1, NULL, '138'),
	('140', 'Sat Aug 04 21:40:50 EDT 2012', 1, '135', NULL),
	('143', 'Sat Aug 04 21:41:13 EDT 2012', 1, NULL, '142'),
	('144', 'Sat Aug 04 21:41:18 EDT 2012', 1, NULL, '138'),
	('145', 'Sat Aug 04 21:41:18 EDT 2012', 1, NULL, '138'),
	('146', 'Sat Aug 04 21:41:19 EDT 2012', 1, NULL, '142'),
	('147', 'Sat Aug 04 21:41:21 EDT 2012', -1, NULL, '138'),
	('148', 'Sat Aug 04 21:41:23 EDT 2012', -1, NULL, '138'),
	('149', 'Sat Aug 04 21:41:33 EDT 2012', 1, '135', NULL),
	('150', 'Sat Aug 04 21:41:33 EDT 2012', 1, '135', NULL),
	('151', 'Sat Aug 04 21:41:34 EDT 2012', -1, '135', NULL),
	('152', 'Sat Aug 04 21:41:35 EDT 2012', -1, '135', NULL),
	('153', 'Sat Aug 04 21:41:36 EDT 2012', 1, '130', NULL),
	('154', 'Sat Aug 04 21:41:39 EDT 2012', -1, '130', NULL),
	('156', 'Sat Aug 04 21:42:07 EDT 2012', 1, '155', NULL),
	('158', 'Sat Aug 04 21:42:18 EDT 2012', 1, NULL, '157'),
	('161', 'Sat Aug 04 21:42:39 EDT 2012', 1, NULL, '160'),
	('162', 'Sat Aug 04 21:42:42 EDT 2012', 1, NULL, '160'),
	('164', 'Sat Aug 04 21:44:33 EDT 2012', 1, '163', NULL),
	('167', 'Sat Aug 04 21:44:50 EDT 2012', 1, NULL, '166'),
	('168', 'Sat Aug 04 21:48:16 EDT 2012', 1, '130', NULL),
	('170', 'Sat Aug 04 21:48:41 EDT 2012', 1, '169', NULL),
	('175', 'Sat Aug 04 21:49:09 EDT 2012', 1, NULL, '174'),
	('178', 'Sat Aug 04 21:49:18 EDT 2012', -1, NULL, '174'),
	('179', 'Sat Aug 04 21:49:20 EDT 2012', 1, '169', NULL),
	('180', 'Sat Aug 04 21:49:22 EDT 2012', 1, '169', NULL),
	('181', 'Sat Aug 04 21:49:25 EDT 2012', -1, '169', NULL),
	('182', 'Sat Aug 04 21:49:27 EDT 2012', 1, '169', NULL),
	('183', 'Sun Aug 05 22:52:56 EDT 2012', 1, '130', NULL),
	('184', 'Sun Aug 05 22:53:01 EDT 2012', 1, '169', NULL),
	('191', 'Sun Aug 05 22:53:57 EDT 2012', 1, '190', NULL),
	('192', 'Sun Aug 05 22:54:02 EDT 2012', 1, '190', NULL),
	('193', 'Sun Aug 05 22:54:04 EDT 2012', 1, '190', NULL),
	('194', 'Sun Aug 05 22:54:06 EDT 2012', 1, '190', NULL),
	('195', 'Sun Aug 05 22:54:07 EDT 2012', 1, '190', NULL),
	('198', 'Sun Aug 05 22:55:02 EDT 2012', 1, NULL, '197'),
	('199', 'Tue Aug 07 13:44:51 EDT 2012', 1, '169', NULL),
	('200', 'Tue Aug 07 13:44:53 EDT 2012', 1, '190', NULL),
	('202', 'Tue Aug 07 13:46:22 EDT 2012', 1, '201', NULL),
	('206', 'Tue Aug 07 13:47:07 EDT 2012', 1, '201', NULL),
	('207', 'Tue Aug 07 13:47:09 EDT 2012', 1, '201', NULL),
	('208', 'Tue Aug 07 13:47:10 EDT 2012', 1, '201', NULL),
	('209', 'Tue Aug 07 13:47:11 EDT 2012', 1, '201', NULL),
	('210', 'Tue Aug 07 13:47:14 EDT 2012', 1, '201', NULL),
	('211', 'Tue Aug 07 13:47:15 EDT 2012', 1, '201', NULL),
	('214', 'Tue Aug 07 13:51:45 EDT 2012', 1, NULL, '213'),
	('215', 'Tue Aug 07 13:51:58 EDT 2012', 1, NULL, '213'),
	('217', 'Tue Aug 07 14:18:14 EDT 2012', 1, NULL, '216'),
	('219', 'Tue Aug 07 14:19:35 EDT 2012', 1, NULL, '218'),
	('221', 'Tue Aug 07 14:19:40 EDT 2012', 1, NULL, '220'),
	('224', 'Tue Aug 07 14:20:02 EDT 2012', 1, NULL, '223'),
	('226', 'Tue Aug 07 14:20:07 EDT 2012', 1, NULL, '223'),
	('227', 'Tue Aug 07 14:20:11 EDT 2012', 1, NULL, '223'),
	('229', 'Tue Aug 07 14:20:22 EDT 2012', 1, NULL, '228'),
	('231', 'Tue Aug 07 14:20:27 EDT 2012', 1, NULL, '230'),
	('232', 'Tue Aug 07 14:20:34 EDT 2012', 1, '190', NULL),
	('237', 'Tue Aug 07 14:21:35 EDT 2012', 1, '236', NULL),
	('239', 'Tue Aug 07 14:21:46 EDT 2012', 1, NULL, '238'),
	('240', 'Tue Aug 07 14:23:37 EDT 2012', 1, '155', NULL),
	('241', 'Tue Aug 07 14:23:38 EDT 2012', 1, '155', NULL),
	('242', 'Tue Aug 07 14:23:39 EDT 2012', 1, '155', NULL),
	('243', 'Tue Aug 07 14:23:41 EDT 2012', 1, '155', NULL),
	('244', 'Tue Aug 07 14:23:42 EDT 2012', 1, '155', NULL),
	('245', 'Tue Aug 07 14:23:43 EDT 2012', 1, '201', NULL),
	('250', 'Tue Aug 07 14:26:27 EDT 2012', 1, NULL, '249'),
	('255', 'Tue Aug 07 14:31:21 EDT 2012', 1, NULL, '254');
