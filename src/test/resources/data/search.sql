insert into teacher values (1,'teacher@gmail.com', 'teacher','','','TEACHER','KAKAO');
insert into teacher values (2,'teacher2@gmail.com', 'teacher2','','','TEACHER','KAKAO');
insert into teacher values (3,'teacher3@gmail.com', 'teacher3','','','TEACHER','KAKAO');


insert into lesson values (1, now(), now(), 'MONDAY,TUESDAY,WEDNESDAY,THURSDAY', '13:00:00', '수학', '12:00:00',1);
insert into lesson values (2, now(), now(), 'MONDAY,FRIDAY', '17:00:00', '수학2', '15:00:00',1);
insert into lesson values (3, now(), now(), 'FRIDAY,THURSDAY', '15:00:00', '수학3', '13:00:00',1);


insert into book
values (1, now(), now(), '수학의 정석',1);
insert into book
values (2, now(), now(), '수학의 정석2',1);
insert into book
values (3, now(), now(), '물리1',2);
insert into book
values (4, now(), now(), '물리2',2);


insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (1, now(), now(), 'p 0 ~ 10', now(), 'math1', '2023-05-01 13:10:05', 1);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (2, now(), now(), 'p 100 ~ 110', now(), 'math11', '2023-05-01 13:10:05', 1);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (3, now(), now(), 'p 0 ~ 10', now() + INTERVAL 1 DAY, 'math2', '2023-05-01 13:10:05', 1);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (4, now(), now(), 'p 100 ~ 110', now() + INTERVAL 1 DAY, 'math3', '2022-05-01 13:10:10', 1);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (5, now(), now(), 'p 0 ~ 10', now() + INTERVAL 1 DAY, 'math4', '2022-05-01 13:10:10', 1);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (6, now(), now(), 'p 0 ~ 10', now(), '1math', '2023-05-01 13:10:06', 1);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id) values (7, now(), now(), 'p 100 ~ 110', now(), '2math p 100 ~ 110', '2023-05-01 13:10:06', 2);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (8, now(), now(), 'p 100 ~ 110', now(), '5math p 100 ~ 110', '2023-05-01 13:10:07', 2);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (9, now(), now(), 'p 100 ~ 110', now(), '3math', '2023-05-01 13:11:04', 2);
insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (10, now(), now(), 'p 100 ~ 110', now(), '6math', '2023-05-01 13:11:04', 2);
