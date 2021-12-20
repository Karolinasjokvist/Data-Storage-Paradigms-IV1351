CREATE TABLE classrooms (
 room_id INT NOT NULL,
 description VARCHAR(2000),
 zip VARCHAR(500),
 street VARCHAR(500),
 city VARCHAR(500)
);

ALTER TABLE classrooms ADD CONSTRAINT PK_classrooms PRIMARY KEY (room_id);


CREATE TABLE email (
 id INT NOT NULL,
 email VARCHAR(500) NOT NULL
);

ALTER TABLE email ADD CONSTRAINT PK_email PRIMARY KEY (id);


CREATE TABLE instructor (
 instructor_id INT NOT NULL,
 address VARCHAR(500),
 name VARCHAR(500),
 teach_ensembles VARCHAR(500),
 age INT,
 person_number VARCHAR(500) NOT NULL,
 zip VARCHAR(500),
 street VARCHAR(500),
 city VARCHAR(500)
);

ALTER TABLE instructor ADD CONSTRAINT PK_instructor PRIMARY KEY (instructor_id);


CREATE TABLE instructor_payment (
 instructor_id INT NOT NULL,
 time TIMESTAMP(10),
 total_salary INT
);

ALTER TABLE instructor_payment ADD CONSTRAINT PK_instructor_payment PRIMARY KEY (instructor_id);


CREATE TABLE instrument (
 id INT NOT NULL,
 instrument VARCHAR(500)
);

ALTER TABLE instrument ADD CONSTRAINT PK_instrument PRIMARY KEY (id);


CREATE TABLE lessons (
 id INT NOT NULL,
 lesson VARCHAR(500) NOT NULL
);

ALTER TABLE lessons ADD CONSTRAINT PK_lessons PRIMARY KEY (id);


CREATE TABLE phone (
 id INT NOT NULL,
 phone CHAR(10)
);

ALTER TABLE phone ADD CONSTRAINT PK_phone PRIMARY KEY (id);


CREATE TABLE sibling (
 id INT NOT NULL,
 sibing VARCHAR(500)
);

ALTER TABLE sibling ADD CONSTRAINT PK_sibling PRIMARY KEY (id);


CREATE TABLE student (
 student_id INT NOT NULL,
 person_number VARCHAR(12) NOT NULL,
 name VARCHAR(500),
 address VARCHAR(500),
 age INT,
 zip VARCHAR(500),
 street VARCHAR(500),
 city VARCHAR(500)
);

ALTER TABLE student ADD CONSTRAINT PK_student PRIMARY KEY (student_id);


CREATE TABLE student_email (
 student_id INT NOT NULL,
 email_id INT NOT NULL
);

ALTER TABLE student_email ADD CONSTRAINT PK_student_email PRIMARY KEY (student_id,email_id);


CREATE TABLE student_lesson (
 student_id INT NOT NULL,
 lesson_id INT NOT NULL
);

ALTER TABLE student_lesson ADD CONSTRAINT PK_student_lesson PRIMARY KEY (student_id,lesson_id);


CREATE TABLE student_parent_email (
 student_id INT NOT NULL,
 email_id INT NOT NULL
);

ALTER TABLE student_parent_email ADD CONSTRAINT PK_student_parent_email PRIMARY KEY (student_id,email_id);


CREATE TABLE student_parent_phone (
 student_id INT NOT NULL,
 phone_id INT NOT NULL
);

ALTER TABLE student_parent_phone ADD CONSTRAINT PK_student_parent_phone PRIMARY KEY (student_id,phone_id);


CREATE TABLE student_phone (
 student_id INT NOT NULL,
 phone_id INT NOT NULL
);

ALTER TABLE student_phone ADD CONSTRAINT PK_student_phone PRIMARY KEY (student_id,phone_id);


CREATE TABLE student_sibling (
 student_id INT NOT NULL,
 sibling_id INT NOT NULL
);

ALTER TABLE student_sibling ADD CONSTRAINT PK_student_sibling PRIMARY KEY (student_id,sibling_id);


CREATE TABLE teacher_email (
 instructor_id INT NOT NULL,
 email_id INT NOT NULL
);

ALTER TABLE teacher_email ADD CONSTRAINT PK_teacher_email PRIMARY KEY (instructor_id,email_id);


CREATE TABLE teacher_phone (
 instructor_id INT NOT NULL,
 phone_id INT NOT NULL
);

ALTER TABLE teacher_phone ADD CONSTRAINT PK_teacher_phone PRIMARY KEY (instructor_id,phone_id);


CREATE TABLE instruments (
 student_id INT NOT NULL,
 instructor_id INT NOT NULL,
 instrument_id INT NOT NULL
);

ALTER TABLE instruments ADD CONSTRAINT PK_instruments PRIMARY KEY (student_id,instructor_id,instrument_id);


CREATE TABLE lesson (
 lesson_id INT NOT NULL,
 time TIMESTAMP(10) NOT NULL,
 instructor_id INT NOT NULL,
 room_id INT NOT NULL
);

ALTER TABLE lesson ADD CONSTRAINT PK_lesson PRIMARY KEY (lesson_id);


CREATE TABLE renting_instruments (
 instrument_id INT NOT NULL,
 time_rented TIMESTAMP(10),
 type VARCHAR(500),
 brand VARCHAR(500),
 price INT,
 student_id INT
);

ALTER TABLE renting_instruments ADD CONSTRAINT PK_renting_instruments PRIMARY KEY (instrument_id);


CREATE TABLE student_payment (
 student_id INT NOT NULL,
 total_price INT,
 time TIMESTAMP(10),
 instrument_id INT NOT NULL
);

ALTER TABLE student_payment ADD CONSTRAINT PK_student_payment PRIMARY KEY (student_id);


CREATE TABLE ensembles (
 lesson_id INT NOT NULL,
 max_students INT,
 min_students INT,
 amount_students INT,
 genre VARCHAR(500)
);

ALTER TABLE ensembles ADD CONSTRAINT PK_ensembles PRIMARY KEY (lesson_id);


CREATE TABLE group_lesson (
 lesson_id INT NOT NULL,
 instrument_type  VARCHAR(500),
 skill_level VARCHAR(500),
 amount_students INT,
 max_students INT,
 min_students INT
);

ALTER TABLE group_lesson ADD CONSTRAINT PK_group_lesson PRIMARY KEY (lesson_id);


CREATE TABLE individual_lesson (
 lesson_id INT NOT NULL,
 instrument_type  VARCHAR(500),
 skill_level VARCHAR(500)
);

ALTER TABLE individual_lesson ADD CONSTRAINT PK_individual_lesson PRIMARY KEY (lesson_id);


ALTER TABLE instructor_payment ADD CONSTRAINT FK_instructor_payment_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);


ALTER TABLE student_email ADD CONSTRAINT FK_student_email_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE student_email ADD CONSTRAINT FK_student_email_1 FOREIGN KEY (email_id) REFERENCES email (id);


ALTER TABLE student_lesson ADD CONSTRAINT FK_student_lesson_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE student_lesson ADD CONSTRAINT FK_student_lesson_1 FOREIGN KEY (lesson_id) REFERENCES lessons (id);


ALTER TABLE student_parent_email ADD CONSTRAINT FK_student_parent_email_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE student_parent_email ADD CONSTRAINT FK_student_parent_email_1 FOREIGN KEY (email_id) REFERENCES email (id);


ALTER TABLE student_parent_phone ADD CONSTRAINT FK_student_parent_phone_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE student_parent_phone ADD CONSTRAINT FK_student_parent_phone_1 FOREIGN KEY (phone_id) REFERENCES phone (id);


ALTER TABLE student_phone ADD CONSTRAINT FK_student_phone_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE student_phone ADD CONSTRAINT FK_student_phone_1 FOREIGN KEY (phone_id) REFERENCES phone (id);


ALTER TABLE student_sibling ADD CONSTRAINT FK_student_sibling_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE student_sibling ADD CONSTRAINT FK_student_sibling_1 FOREIGN KEY (sibling_id) REFERENCES sibling (id);


ALTER TABLE teacher_email ADD CONSTRAINT FK_teacher_email_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE teacher_email ADD CONSTRAINT FK_teacher_email_1 FOREIGN KEY (email_id) REFERENCES email (id);


ALTER TABLE teacher_phone ADD CONSTRAINT FK_teacher_phone_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE teacher_phone ADD CONSTRAINT FK_teacher_phone_1 FOREIGN KEY (phone_id) REFERENCES phone (id);


ALTER TABLE instruments ADD CONSTRAINT FK_instruments_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE instruments ADD CONSTRAINT FK_instruments_1 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE instruments ADD CONSTRAINT FK_instruments_2 FOREIGN KEY (instrument_id) REFERENCES instrument (id);


ALTER TABLE lesson ADD CONSTRAINT FK_lesson_0 FOREIGN KEY (instructor_id) REFERENCES instructor (instructor_id);
ALTER TABLE lesson ADD CONSTRAINT FK_lesson_1 FOREIGN KEY (room_id) REFERENCES classrooms (room_id);


ALTER TABLE renting_instruments ADD CONSTRAINT FK_renting_instruments_0 FOREIGN KEY (student_id) REFERENCES student (student_id);


ALTER TABLE student_payment ADD CONSTRAINT FK_student_payment_0 FOREIGN KEY (student_id) REFERENCES student (student_id);
ALTER TABLE student_payment ADD CONSTRAINT FK_student_payment_1 FOREIGN KEY (instrument_id) REFERENCES renting_instruments (instrument_id);


ALTER TABLE ensembles ADD CONSTRAINT FK_ensembles_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


ALTER TABLE group_lesson ADD CONSTRAINT FK_group_lesson_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


ALTER TABLE individual_lesson ADD CONSTRAINT FK_individual_lesson_0 FOREIGN KEY (lesson_id) REFERENCES lesson (lesson_id);


