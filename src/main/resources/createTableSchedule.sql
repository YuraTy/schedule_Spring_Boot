CREATE TABLE schedule (
id SERIAL PRIMARY KEY,
group text,
teacher_first_name text,
teacher_last_name text,
course text,
classroom int,
lesson_start_time SMALLDATETIME,
lesson_end_time SMALLDATETIME);