
-- CLEANING DATA 
DELETE FROM ANNOUNCEMENT WHERE KEY_ANNOUNCEMENT_BOARD NOT IN (SELECT ID_INTERNAL FROM ANNOUNCEMENT_BOARD);

alter table CONTENT add column AUTHOR text;
alter table CONTENT add column AUTHOR_EMAIL text;
alter table CONTENT add column EXCERPT longtext;
alter table CONTENT add column KEYWORDS longtext;
alter table CONTENT add column KEY_ANNOUNCEMENT_BOARD int(11);
alter table CONTENT add column LAST_MODIFICATION timestamp NULL default NULL;
alter table CONTENT add column PLACE text;
alter table CONTENT add column PUBLICATION_BEGIN timestamp NULL default NULL;
alter table CONTENT add column PUBLICATION_END timestamp NULL default NULL;
alter table CONTENT add column REFERED_SUBJECT_BEGIN timestamp NULL default NULL;
alter table CONTENT add column REFERED_SUBJECT_END timestamp NULL default NULL;
alter table CONTENT add column SUBJECT longtext;
alter table CONTENT add column VISIBLE tinyint(1);
alter table CONTENT add index (KEY_ANNOUNCEMENT_BOARD);
alter table CONTENT add index (KEY_CREATOR);
alter table CONTENT add index (KEY_ROOT_DOMAIN_OBJECT);


alter table CONTENT add column ANNOUNCEMENT_ID_INTERNAL int(11);
alter table CONTENT add index (ANNOUNCEMENT_ID_INTERNAL);

alter table ANNOUNCEMENT change column ID_INTERNAL ANNOUNCEMENT_ID_INTERNAL int(11) NOT NULL auto_increment;

insert into CONTENT (AUTHOR, AUTHOR_EMAIL, BODY, CREATION_DATE, EXCERPT, KEYWORDS,
                     KEY_ANNOUNCEMENT_BOARD, ANNOUNCEMENT_ID_INTERNAL, KEY_CREATOR, KEY_ROOT_DOMAIN_OBJECT,
                     LAST_MODIFICATION, PLACE, PUBLICATION_BEGIN, PUBLICATION_END, REFERED_SUBJECT_BEGIN,
                     REFERED_SUBJECT_END, SUBJECT, VISIBLE, OJB_CONCRETE_CLASS)
    select AUTHOR, AUTHOR_EMAIL, BODY, CREATION_DATE, EXCERPT, KEYWORDS, KEY_ANNOUNCEMENT_BOARD, ANNOUNCEMENT_ID_INTERNAL,
           KEY_CREATOR, KEY_ROOT_DOMAIN_OBJECT, LAST_MODIFICATION, PLACE, PUBLICATION_BEGIN, PUBLICATION_END,
           REFERED_SUBJECT_BEGIN, REFERED_SUBJECT_END, SUBJECT, VISIBLE,
           'net.sourceforge.fenixedu.domain.messaging.Announcement'
    from ANNOUNCEMENT;

-- No table holds keys to the ANNOUNCEMENT table... so we can now drop the old id column.

alter table CONTENT drop column ANNOUNCEMENT_ID_INTERNAL;

alter table ANNOUNCEMENT_BOARD change column NAME OLD_NAME varchar(255) default NULL;
update ANNOUNCEMENT_BOARD set OLD_NAME = '__xpto__' where OLD_NAME is null or OLD_NAME = '';
alter table ANNOUNCEMENT_BOARD add column NAME longtext;
update ANNOUNCEMENT_BOARD set ANNOUNCEMENT_BOARD.NAME = concat('pt', length(replace(ANNOUNCEMENT_BOARD.OLD_NAME, "__xpto__", "")), ':', replace(ANNOUNCEMENT_BOARD.OLD_NAME, "__xpto__", ""));
update ANNOUNCEMENT_BOARD set NAME = NULL WHERE NAME = "pt0:";
update ANNOUNCEMENT_BOARD set NAME = replace(ANNOUNCEMENT_BOARD.NAME, "pt0:", "");


alter table CONTENT add column EXECUTION_COURSE_PERMITTED_MANAGEMENT_GROUP_TYPE text;
alter table CONTENT add column EXECUTION_COURSE_PERMITTED_READ_GROUP_TYPE text;
alter table CONTENT add column EXECUTION_COURSE_PERMITTED_WRITE_GROUP_TYPE text;
alter table CONTENT add column KEY_EXECUTION_COURSE int(11);
alter table CONTENT add column KEY_PARTY int(11);
alter table CONTENT add column MANAGERS text;
alter table CONTENT add column MANDATORY tinyint(1);
alter table CONTENT add column READERS text;
alter table CONTENT add column UNIT_PERMITTED_MANAGEMENT_GROUP_TYPE text;
alter table CONTENT add column UNIT_PERMITTED_READ_GROUP_TYPE text;
alter table CONTENT add column UNIT_PERMITTED_WRITE_GROUP_TYPE text;
alter table CONTENT add column WRITERS text;
alter table CONTENT add index (KEY_EXECUTION_COURSE);
alter table CONTENT add index (KEY_PARTY);



alter table CONTENT add column ANNOUNCEMENT_BOARD_ID_INTERNAL int(11);
alter table CONTENT add index (ANNOUNCEMENT_BOARD_ID_INTERNAL);

alter table ANNOUNCEMENT_BOARD change column ID_INTERNAL ANNOUNCEMENT_BOARD_ID_INTERNAL int(11) NOT NULL auto_increment;

insert into CONTENT (ANNOUNCEMENT_BOARD_ID_INTERNAL, CREATION_DATE, EXECUTION_COURSE_PERMITTED_WRITE_GROUP_TYPE,
        EXECUTION_COURSE_PERMITTED_READ_GROUP_TYPE, EXECUTION_COURSE_PERMITTED_MANAGEMENT_GROUP_TYPE,
        UNIT_PERMITTED_WRITE_GROUP_TYPE, UNIT_PERMITTED_READ_GROUP_TYPE, UNIT_PERMITTED_MANAGEMENT_GROUP_TYPE,
        KEY_ROOT_DOMAIN_OBJECT, KEY_PARTY, KEY_EXECUTION_COURSE, MANDATORY, READERS, WRITERS, MANAGERS, NAME,
        OJB_CONCRETE_CLASS)
    select ANNOUNCEMENT_BOARD_ID_INTERNAL, CREATION_DATE, EXECUTION_COURSE_PERMITTED_WRITE_GROUP_TYPE,
        EXECUTION_COURSE_PERMITTED_READ_GROUP_TYPE, EXECUTION_COURSE_PERMITTED_MANAGEMENT_GROUP_TYPE,
        UNIT_PERMITTED_WRITE_GROUP_TYPE, UNIT_PERMITTED_READ_GROUP_TYPE, UNIT_PERMITTED_MANAGEMENT_GROUP_TYPE,
        KEY_ROOT_DOMAIN_OBJECT, KEY_PARTY, KEY_EXECUTION_COURSE, MANDATORY, READERS, WRITERS, MANAGERS, NAME,
        OJB_CONCRETE_CLASS
    from ANNOUNCEMENT_BOARD;

update CONTENT as C1, CONTENT as C2 set C1.KEY_ANNOUNCEMENT_BOARD = C2.ID_INTERNAL
    where C1.KEY_ANNOUNCEMENT_BOARD = C2.ANNOUNCEMENT_BOARD_ID_INTERNAL
        and C1.OJB_CONCRETE_CLASS = 'net.sourceforge.fenixedu.domain.messaging.Announcement';

update CONTENT as C1, EXECUTION_COURSE set EXECUTION_COURSE.KEY_BOARD = C1.ID_INTERNAL
    where EXECUTION_COURSE.KEY_BOARD = C1.ANNOUNCEMENT_BOARD_ID_INTERNAL;


insert into NODE (KEY_CHILD, KEY_PARENT, NODE_ORDER, VISIBLE, KEY_ROOT_DOMAIN_OBJECT, OJB_CONCRETE_CLASS)
    select ID_INTERNAL, KEY_ANNOUNCEMENT_BOARD, null, 1, 1, 'net.sourceforge.fenixedu.domain.messaging.AnnouncementNode'
        from CONTENT
        where OJB_CONCRETE_CLASS = 'net.sourceforge.fenixedu.domain.messaging.Announcement';

alter table CONTENT drop column ANNOUNCEMENT_BOARD_ID_INTERNAL;
