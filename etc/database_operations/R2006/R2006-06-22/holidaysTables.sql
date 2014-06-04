DROP TABLE IF EXISTS `LOCALITY`;
CREATE TABLE `LOCALITY` (
  `ID_INTERNAL` int(11) unsigned NOT NULL auto_increment,
  `NAME` VARCHAR(50) NOT NULL,
   KEY_ROOT_DOMAIN_OBJECT int(11) not null default 1,
   PRIMARY KEY  (`ID_INTERNAL`),
   UNIQUE KEY `u1` (`NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

ALTER TABLE SPACE_INFORMATION ADD COLUMN KEY_LOCALITY int(11) DEFAULT NULL;

DROP TABLE IF EXISTS `ASSIDUOUSNESS_CAMPUS_HISTORY`;
CREATE TABLE `ASSIDUOUSNESS_CAMPUS_HISTORY` (
  `ID_INTERNAL` int(11) unsigned NOT NULL auto_increment,
  `BEGIN_DATE` VARCHAR(10) NOT NULL,
  `END_DATE` VARCHAR(10),
  `KEY_CAMPUS` int(11) unsigned NOT NULL,
  `KEY_ASSIDUOUSNESS` int(11) unsigned NOT NULL,
  `LAST_MODIFIED_DATE` TIMESTAMP,
  `KEY_MODIFIED_BY` int(11) unsigned,
   KEY_ROOT_DOMAIN_OBJECT int(11) not null default 1,
   PRIMARY KEY  (`ID_INTERNAL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `HOLIDAY`;
CREATE TABLE `HOLIDAY` (
  `ID_INTERNAL` int(11) unsigned NOT NULL auto_increment,
  `DATE` VARCHAR(10) NOT NULL,
  `KEY_LOCALITY` int(11) unsigned default NULL,
   KEY_ROOT_DOMAIN_OBJECT int(11) not null default 1,
   PRIMARY KEY  (`ID_INTERNAL`),
   UNIQUE KEY `u1` (`DATE`, `KEY_LOCALITY`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
