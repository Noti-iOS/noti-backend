insert into teacher (id, email, nickname, profile, role, social_id, social_type)
values (1,'hojoon@gmail.com', 'hojoon','','ROLE_TEACHER','123','KAKAO');

insert into book (book_id, created_at, modified_at, title, teacher_id)
values (1, now(), now(), '수학의정석', 1);

insert into book (book_id, created_at, modified_at, title, teacher_id)
values (2, now(), now(), '수학의정석2', 1);

insert into book (book_id, created_at, modified_at, title, teacher_id)
values (3, now(), now(), '수학의정석3', 1);

insert into lesson (lesson_id, created_at, modified_at, days, end_time, lesson_name, start_time,
                    teacher_id)
values (1, now(), now(), 'MONDAY,TUESDAY,WEDNESDAY,THURSDAY', '13:00:00', '수학', '12:00:00',1);