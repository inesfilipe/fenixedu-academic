select concat('update CURRICULAR_COURSE set CURRICULAR_COURSE.KEY_STUDENT_COURSE_REPORT = ',
	STUDENT_COURSE_REPORT.ID_INTERNAL, 
	' where CURRICULAR_COURSE.ID_INTERNAL = ', 
	STUDENT_COURSE_REPORT.KEY_CURRICULAR_COURSE, ';') 
as "" from STUDENT_COURSE_REPORT;