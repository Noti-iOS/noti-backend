insert into teacher
values (1, 'teacher@gmail.com', 'teacher', '', '', 'TEACHER', 'KAKAO');
insert into teacher
values (2, 'teacher2@gmail.com', 'teacher2', '', '', 'TEACHER', 'KAKAO');
insert into teacher
values (3, 'teacher3@gmail.com', 'teacher3', '', '', 'TEACHER', 'KAKAO');

insert into student
values (1, now(), now(), 'stu@gmail.com', 'student', 'image', 1234);
insert into student
values (2, now(), now(), 'stu2@gmail.com', 'student2', 'image2', 12345);
insert into student
values (3, now(), now(), 'stu3@gmail.com', 'student3', 'image3', 12341);
insert into student
values (4, now(), now(), 'stu4@gmail.com', 'student4', 'image4', 12342);

insert into lesson
values (1, now(), now(), 'MONDAY,TUESDAY,WEDNESDAY,THURSDAY', '13:00:00', '수학', '12:00:00', 1);
insert into lesson
values (2, now(), now(), 'MONDAY,FRIDAY, SATURDAY', '17:00:00', '수학2', '15:00:00', 1);
insert into lesson
values (3, now(), now(), 'FRIDAY,THURSDAY', '15:00:00', '수학3', '13:00:00', 1);
insert into lesson
values (4, now(), now(), 'FRIDAY', '10:00:00', '과학', '09:00:00', 2);
insert into lesson
values (5, now(), now(), 'FRIDAY', '12:00:00', '과학2', '11:00:00', 2);

insert into book (book_id, created_at, modified_at, title, teacher_id)
values (1, now(), now(), '수학의 정석',1);
insert into book (book_id, created_at, modified_at, title, teacher_id)
values (2, now(), now(), '수학의 정석2',1);
insert into book (book_id, created_at, modified_at, title, teacher_id)
values (3, now(), now(), '물리1',2);
insert into book (book_id, created_at, modified_at, title, teacher_id)
values (4, now(), now(), '물리2',2);

insert into lesson_book (lesson_book_id, created_at, modified_at, book_id, lesson_id)
values (1, now(), now(), 1, 1);
insert into lesson_book (lesson_book_id, created_at, modified_at, book_id, lesson_id)
values (2, now(), now(), 2, 2);
insert into lesson_book (lesson_book_id, created_at, modified_at, book_id, lesson_id)
values (3, now(), now(), 1, 3);
insert into lesson_book (lesson_book_id, created_at, modified_at, book_id, lesson_id)
values (4, now(), now(), 3, 4);
insert into lesson_book (lesson_book_id, created_at, modified_at, book_id, lesson_id)
values (5, now(), now(), 4, 5);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, lesson_id)
values (1, now(), now(), 'p 0 ~ 10', now() + INTERVAL 1 DAY, '수학 정석1', now(), 1);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, lesson_id)
values (2, now(), now(), 'p 100 ~ 110', now(), '수학 정석1', now(), 1);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, lesson_id)
values (3, now(), now(), 'p 0 ~ 10', now() + INTERVAL 1 DAY, '수학 정석2', now(), 2);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, lesson_id)
values (4, now(), now(), 'p 100 ~ 110', now() + INTERVAL 1 DAY, '수학 정석2', now(), 2);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, lesson_id)
values (5, now(), now(), 'p 0 ~ 10', now(), '물리 숙제', now(), 4);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, lesson_id)
values (6, now(), now(), 'p 0 ~ 10', now() + INTERVAL 1 DAY, '물리2 숙제', now(), 5);


insert into student_homework (student_homework_id, created_at, modified_at, homework_status,
                              homework_id, student_id)
values (1, now(), now(), false, 3, 1);

insert into student_homework (student_homework_id, created_at, modified_at, homework_status,
                              homework_id, student_id)
values (2, now(), now(), false, 3, 2);

insert into student_homework (student_homework_id, created_at, modified_at, homework_status,
                              homework_id, student_id)
values (3, now(), now(), false, 3, 3);

insert into student_homework (student_homework_id, created_at, modified_at, homework_status,
                              homework_id, student_id)
values (4, now(), now(), false, 3, 4);

insert into student_homework (student_homework_id, created_at, modified_at, homework_status,
                              homework_id, student_id)
values (5, now(), now(), false, 4, 1);

insert into student_homework (student_homework_id, created_at, modified_at, homework_status,
                              homework_id, student_id)
values (6, now(), now(), false, 4, 2);

insert into student_homework (student_homework_id, created_at, modified_at, homework_status,
                              homework_id, student_id)
values (7, now(), now(), false, 4, 3);

insert into student_homework (student_homework_id, created_at, modified_at, homework_status,
                              homework_id, student_id)
values (8, now(), now(), false, 4, 4);