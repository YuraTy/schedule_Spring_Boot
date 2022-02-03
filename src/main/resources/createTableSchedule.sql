CREATE TABLE schedule (
id SERIAL PRIMARY KEY,
group text,
teacher text,
course text,
classroom int,
lesson_date SMALLDATETIME);