alter table EXECUTION_DEGREE add column ANOTATION longtext;
update EXECUTION_DEGREE, DEGREE_CURRICULAR_PLAN set EXECUTION_DEGREE.ANOTATION = concat('pt', length(DEGREE_CURRICULAR_PLAN.ANOTATION), ':', DEGREE_CURRICULAR_PLAN.ANOTATION) where EXECUTION_DEGREE.KEY_DEGREE_CURRICULAR_PLAN = DEGREE_CURRICULAR_PLAN.ID_INTERNAL and DEGREE_CURRICULAR_PLAN.ANOTATION is not null and DEGREE_CURRICULAR_PLAN.ANOTATION <> '' and EXECUTION_DEGREE.KEY_EXECUTION_YEAR = 44;