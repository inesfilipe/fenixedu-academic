CREATE TABLE ASSIDUOUSNESS_EXEMPTION (
	ID_INTERNAL int(11) unsigned NOT NULL auto_increment,
	YEAR int(4) NOT NULL,
	DESCRIPTION varchar(50) NOT NULL default '',
	KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
	PRIMARY KEY (ID_INTERNAL),
  	UNIQUE KEY U1 (YEAR, DESCRIPTION),
  	KEY `KEY_ROOT_DOMAIN_OBJECT` (`KEY_ROOT_DOMAIN_OBJECT`)
);

CREATE TABLE ASSIDUOUSNESS_EXEMPTION_SHIFT (
	ID_INTERNAL int(11) unsigned NOT NULL auto_increment,
	PARTIAL_SHIFT text NOT NULL,
	KEY_ASSIDUOUSNESS_EXEMPTION  int(11) unsigned NOT NULL,
	KEY_ROOT_DOMAIN_OBJECT int(11) NOT NULL default '1',
	PRIMARY KEY (ID_INTERNAL),
  	KEY `KEY_ROOT_DOMAIN_OBJECT` (`KEY_ROOT_DOMAIN_OBJECT`)
);

