CREATE TABLE IF NOT EXISTS schedule (
schedule_id SERIAL PRIMARY KEY,
group_id int REFERENCES groups (id),
teacher_id int REFERENCES teachers (id),
course_id int REFERENCES courses (id),
classroom_id int REFERENCES classrooms (id),
lesson_start_time TIMESTAMP,
lesson_end_time TIMESTAMP
);