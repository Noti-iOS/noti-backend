insert into teacher values (1,'hojoon@gmail.com', 'hojoon','','','ROLE_TEACHER',123,'KAKAO');
insert into teacher values (2,'hojoon@gmail.com', 'hojoon','','','ROLE_TEACHER',123,'KAKAO');
insert into teacher values (3,'hojoon@gmail.com', 'hojoon','','','ROLE_TEACHER',123,'KAKAO');

insert into lesson values (1, now(), now(), 'MONDAY,TUESDAY,WEDNESDAY', now(), '수학', now(),1);
insert into lesson values (2, now(), now(), 'MONDAY,FRIDAY', now(), '수학2', now(),1);
insert into lesson values (3, now(), now(), 'FRIDAY,THURSDAY', now(), '수학3', now(),1);
insert into lesson values (4, now(), now(), 'FRIDAY', now(), '수학2', now(),2);
insert into lesson values (5, now(), now(), 'FRIDAY', now(), '수학2', now(),3);

insert into book values (1,now(),now(), '수학의 정석');
insert into book values (2,now(),now(), '수학의 정석2');

insert into lesson_book values (1, now(),now(),1,1);
insert into lesson_book values (2, now(),now(),2,2);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, book_id, lesson_id) values (1,now(), now(), 'homework', now() + INTERVAL 1 DAY, 'homework', now(),1,1);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, book_id, lesson_id) values (2,now(), now(), 'homework2', now()+ INTERVAL 1 DAY, 'homework2', now(),1,1);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, book_id, lesson_id) values (3,now(), now(), 'homework3', now()+ INTERVAL 1 DAY, 'homework3', now(),1,1);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, book_id, lesson_id) values (4,now(), now(), 'homework', now()+ INTERVAL 1 DAY, 'homework', now(),2,2);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, book_id, lesson_id) values (5,now(), now(), 'homework2', now(), 'homework2', now(),2,2);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name,
                      start_time, book_id, lesson_id) values (6,now(), now(), 'homework3', now()+ INTERVAL 1 DAY, 'homework3', now(),2,2);

insert into student values (1,now(),now(), 'stu@gmail.com', 'student', 'image',1234);
insert into student values (2,now(),now(), 'stu2@gmail.com', 'student2', 'image2',12345);
insert into student values (3,now(),now(), 'stu3@gmail.com', 'student3', 'image3',12341);
insert into student values (4,now(),now(), 'stu4@gmail.com', 'student4', 'image4',12342);

insert into student_lesson values (1, false, 1,1);
insert into student_lesson values (2, false, 1,2);
insert into student_lesson values (3, false, 1,3);
insert into student_lesson values (4, false, 1,4);

insert into student_lesson values (5, false, 2,1);
insert into student_lesson values (6, false, 2,2);
insert into student_lesson values (7, false, 2,3);
insert into student_lesson values (8, false, 2,4);
insert into student_homework
values (1, now(), now(), false, 1, 1);
insert into student_homework
values (2, now(), now(), false, 1, 2);
insert into student_homework
values (3, now(), now(), false, 1, 3);
insert into student_homework
values (4, now(), now(), false, 1, 4);
insert into student_homework
values (5, now(), now(), false, 2, 1);
insert into student_homework
values (6, now(), now(), false, 2, 2);