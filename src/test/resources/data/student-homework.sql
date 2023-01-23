insert into teacher
values (1, 'teacher@gmail.com', 'teacher', '', '', 'TEACHER', 1, 'KAKAO');
insert into teacher
values (2, 'teacher2@gmail.com', 'teacher2', '', '', 'TEACHER', 2, 'KAKAO');
insert into teacher
values (3, 'teacher3@gmail.com', 'teacher3', '', '', 'TEACHER', 3, 'KAKAO');

insert into student
values (1, now(), now(), 'stu@gmail.com', 'student', 'image', 1234);
insert into student
values (2, now(), now(), 'stu2@gmail.com', 'student2', 'image2', 12345);
insert into student
values (3, now(), now(), 'stu3@gmail.com', 'student3', 'image3', 12341);
insert into student
values (4, now(), now(), 'stu4@gmail.com', 'student4', 'image4', 12342);
insert into student
values (5, now(), now(), 'stu@gmail.com', 'student', 'image', 1239);
insert into student
values (6, now(), now(), 'stu2@gmail.com', 'student2', 'image2', 12341);
insert into student
values (7, now(), now(), 'stu3@gmail.com', 'student3', 'image3', 12355);
insert into student
values (8, now(), now(), 'stu4@gmail.com', 'student4', 'image4', 12348);

insert into lesson
values (1, now(), now(), 'MONDAY,TUESDAY,WEDNESDAY,THURSDAY', '13:00:00', '수학', '12:00:00', 1);
insert into lesson
values (2, now(), now(), 'MONDAY,FRIDAY, SATURDAY', '17:00:00', '수학2', '15:00:00', 1);
insert into lesson
values (3, now(), now(), 'FRIDAY,THURSDAY', '15:00:00', '수학3', '13:00:00', 1);
insert into lesson
values (4, now(), now(), 'FRIDAY', '10:00:00', '과학', '09:00:00', 1);
insert into lesson
values (5, now(), now(), 'FRIDAY', '12:00:00', '과학2', '11:00:00', 2);

insert into book
values (1, now(), now(), '수학의 정석');
insert into book
values (2, now(), now(), '수학의 정석2');
insert into book
values (3, now(), now(), '물리1');
insert into book
values (4, now(), now(), '물리2');

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

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, book_id, lesson_id)
values (1, now(), now(), 'p 0 ~ 10', now(), '수학 정석1', now(), 1, 1);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, book_id, lesson_id)
values (2, now(), now(), 'p 100 ~ 110', now(), '수학 정석1', now(), 1, 1);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, book_id, lesson_id)
values (3, now(), now(), 'p 0 ~ 10', now(), '수학 정석2', now(), 2, 1);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, book_id, lesson_id)
values (4, now(), now(), 'p 100 ~ 110', now(), '수학 정석2', now(), 2, 2);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, book_id, lesson_id)
values (5, now(), now(), 'p 0 ~ 10', now(), '물리 숙제', now(), 3, 2);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, book_id, lesson_id)
values (6, now(), now(), 'p 0 ~ 10', now(), '물리2 숙제', now(), 4, 3);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, book_id, lesson_id)
values (7, now(), now(), 'p 0 ~ 10', now(), '물리2 숙제', now(), 4, 4);


insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (1, now(), now(), true, 1, 1);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (2, now(), now(), true, 1, 2);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (3, now(), now(), true, 1, 3);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (4, now(), now(), true, 1, 4);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (5, now(), now(), true, 2, 1);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (6, now(), now(), true, 2, 2);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (7, now(), now(), false, 2, 3);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (8, now(), now(), false, 2, 4);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (9, now(), now(), true, 3, 2);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (10, now(), now(), false, 3, 5);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (11, now(), now(), true, 4, 5);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (12, now(), now(), false, 4, 6);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (13, now(), now(), false, 4, 7);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (14, now(), now(), false, 5, 1);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (15, now(), now(), false, 6, 1);
insert into student_homework (student_homework_id, created_at, modified_at, homework_status, homework_id, student_id)
values (16, now(), now(), false, 7, 1);