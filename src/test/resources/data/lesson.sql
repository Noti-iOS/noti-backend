insert into teacher (id, email, nickname, profile, role, social_id, social_type)
values (1, 'hojoon@gmail.com', 'hojoon', '', 'ROLE_TEACHER', '123', 'KAKAO');

insert into teacher (id, email, nickname, profile, role, social_id, social_type)
values (2, 'hojoon2@gmail.com', 'hojoon', '', 'ROLE_TEACHER', '123', 'KAKAO');

insert into teacher (id, email, nickname, profile, role, social_id, social_type)
values (3, 'hojoon3@gmail.com', 'hojoon', '', 'ROLE_TEACHER', '123', 'KAKAO');


insert into lesson
values (1, now(), now(), 'MONDAY,TUESDAY,WEDNESDAY,THURSDAY', '13:00:00', '수학', '12:00:00', 1);
insert into lesson
values (2, now(), now(), 'MONDAY,FRIDAY', '17:00:00', '수학2', '15:00:00', 1);
insert into lesson
values (3, now(), now(), 'FRIDAY,THURSDAY', '15:00:00', '수학3', '13:00:00', 1);
insert into lesson
values (4, now(), now(), 'FRIDAY', '10:00:00', '과학', '09:00:00', 2);
insert into lesson
values (5, now(), now(), 'FRIDAY', '12:00:00', '과학2', '11:00:00', 2);

insert into student
values (1, now(), now(), 'stu@gmail.com', 'student', 'image', 1234);
insert into student
values (2, now(), now(), 'stu2@gmail.com', 'student2', 'image2', 12345);
insert into student
values (3, now(), now(), 'stu3@gmail.com', 'student3', 'image3', 12341);
insert into student
values (4, now(), now(), 'stu4@gmail.com', 'student4', 'image4', 12342);

insert into student_lesson
values (1, false, 1, 1, false);
insert into student_lesson
values (2, false, 1, 2, false);
insert into student_lesson
values (3, false, 1, 3, false);
insert into student_lesson
values (4, false, 1, 4, false);

insert into student_lesson
values (5, false, 2, 1, false);
insert into student_lesson
values (6, false, 2, 2, false);
insert into student_lesson
values (7, false, 2, 3, false);
insert into student_lesson
values (8, false, 2, 4, false);