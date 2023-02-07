insert into teacher values (1,'teacher@gmail.com', 'teacher','','','TEACHER','KAKAO');
insert into teacher values (2,'teacher2@gmail.com', 'teacher2','','','TEACHER','KAKAO');
insert into teacher values (3,'teacher3@gmail.com', 'teacher3','','','TEACHER','KAKAO');


insert into lesson values (1, now(), now(), 'MONDAY,TUESDAY,WEDNESDAY,THURSDAY', '13:00:00', '수학', '12:00:00',1);
insert into lesson values (2, now(), now(), 'MONDAY,FRIDAY', '17:00:00', '수학2', '15:00:00',1);
insert into lesson values (3, now(), now(), 'FRIDAY,THURSDAY', '15:00:00', '수학3', '13:00:00',1);
insert into lesson values (4, now(), now(), 'FRIDAY', '10:00:00', '과학', '09:00:00',2);
insert into lesson values (5, now(), now(), 'FRIDAY', '12:00:00', '과학2', '11:00:00',2);



insert into book (book_id, created_at, modified_at, title, teacher_id)
values (1, now(), now(), '수학의 정석',1);
insert into book (book_id, created_at, modified_at, title, teacher_id)
values (2, now(), now(), '수학의 정석2',2);
insert into book (book_id, created_at, modified_at, title, teacher_id)
values (3, now(), now(), '물리1',2);
insert into book (book_id, created_at, modified_at, title, teacher_id)
values (4, now(), now(), '물리2',2);



insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (7, now(), now(), 'p 0 ~ 10', now() + INTERVAL 1 MONTH, '수학 정석1', now(), 1);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (8, now(), now(), 'p 0 ~ 10', now() + INTERVAL 1 MONTH, '수학 정석2', now(), 2);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (9, now(), now(), 'p 100 ~ 110', now() + INTERVAL 1 MONTH, '수학 정석2', now(), 2);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (10, now(), now(), 'p 0 ~ 10', now() + INTERVAL 1 MONTH, '물리 숙제', now(), 4);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (11, now(), now(), 'p 0 ~ 10', now() + INTERVAL 1 MONTH, '물리2 숙제', now(), 5);

insert into homework (homework_id, created_at, modified_at, content, end_time, homework_name, start_time, lesson_id)
values (12, now(), now(), 'p 100 ~ 110', now() + INTERVAL 1 MONTH, '수학 정석1', now(), 1);


