alter table ACADEMIC_CALENDAR_ENTRY add column CREDITS_ENTITY varchar(100);

select concat('insert into ACADEMIC_CALENDAR_ENTRY (TITLE, OJB_CONCRETE_CLASS, KEY_ROOT_DOMAIN_OBJECT, BEGIN, END, KEY_PARENT_ENTRY, CREDITS_ENTITY) values ("' , concat('pt',length('Créditos Docência'),':Créditos Docência') , '","net.sourceforge.fenixedu.domain.time.calendarStructure.TeacherCreditsFillingCE",1,"' , EXECUTION_PERIOD.TEACHER_CREDITS_PERIOD_BEGIN , '","' , EXECUTION_PERIOD.TEACHER_CREDITS_PERIOD_END , '",' , SUBSTRING(EXECUTION_PERIOD.EXECUTION_INTERVAL, 75, 3) ,', "TEACHER");') as "" from EXECUTION_PERIOD where TEACHER_CREDITS_PERIOD_BEGIN is not null and TEACHER_CREDITS_PERIOD_BEGIN <> '0000-00-00 00:00:00';
select concat('insert into ACADEMIC_CALENDAR_ENTRY (TITLE, OJB_CONCRETE_CLASS, KEY_ROOT_DOMAIN_OBJECT, BEGIN, END, KEY_PARENT_ENTRY, CREDITS_ENTITY) values ("' , concat('pt',length('Créditos Docência'),':Créditos Docência') , '","net.sourceforge.fenixedu.domain.time.calendarStructure.TeacherCreditsFillingCE",1,"' , EXECUTION_PERIOD.DEPARTMENT_ADM_OFFICE_CREDITS_PERIOD_BEGIN , '","' , EXECUTION_PERIOD.DEPARTMENT_ADM_OFFICE_CREDITS_PERIOD_END , '",' , SUBSTRING(EXECUTION_PERIOD.EXECUTION_INTERVAL, 75, 3) ,', "DEPARTMENT_ADM_OFFICE");') as "" from EXECUTION_PERIOD where DEPARTMENT_ADM_OFFICE_CREDITS_PERIOD_BEGIN is not null and DEPARTMENT_ADM_OFFICE_CREDITS_PERIOD_BEGIN <> '0000-00-00 00:00:00';