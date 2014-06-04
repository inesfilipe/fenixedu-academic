
-- Inserted at 2008-09-08T16:37:43.959+01:00

alter table ASSIDUOUSNESS_RECORD add column KEY_ASSIDUOUSNESS_RECORD_MONTH_INDEX int(11);
alter table ASSIDUOUSNESS_RECORD add index (KEY_ASSIDUOUSNESS_RECORD_MONTH_INDEX);


create table ASSIDUOUSNESS_RECORD_MONTH_INDEX (
  `ID_INTERNAL` int(11) NOT NULL auto_increment,
  `KEY_ASSIDUOUSNESS` int(11),
  `KEY_ROOT_DOMAIN_OBJECT` int(11),
  `YEAR_MONTH` text,
  primary key (ID_INTERNAL),
  index (KEY_ASSIDUOUSNESS),
  index (KEY_ROOT_DOMAIN_OBJECT)
) type=InnoDB ;

insert into ASSIDUOUSNESS_RECORD_MONTH_INDEX
select distinct null, ASSIDUOUSNESS_RECORD.KEY_ASSIDUOUSNESS, 1, left(ASSIDUOUSNESS_RECORD.DATE, 7)
from ASSIDUOUSNESS_RECORD ;

update ASSIDUOUSNESS_RECORD, ASSIDUOUSNESS_RECORD_MONTH_INDEX
set ASSIDUOUSNESS_RECORD.KEY_ASSIDUOUSNESS_RECORD_MONTH_INDEX = ASSIDUOUSNESS_RECORD_MONTH_INDEX.ID_INTERNAL
where  ASSIDUOUSNESS_RECORD_MONTH_INDEX.KEY_ASSIDUOUSNESS = ASSIDUOUSNESS_RECORD.KEY_ASSIDUOUSNESS
and left(ASSIDUOUSNESS_RECORD.DATE, 7) = ASSIDUOUSNESS_RECORD_MONTH_INDEX.YEAR_MONTH;

update ASSIDUOUSNESS_RECORD_MONTH_INDEX
set ASSIDUOUSNESS_RECORD_MONTH_INDEX.YEAR_MONTH =
concat("year=", left(ASSIDUOUSNESS_RECORD_MONTH_INDEX.YEAR_MONTH,4), ",monthOfYear=", right(ASSIDUOUSNESS_RECORD_MONTH_INDEX.YEAR_MONTH, 2));