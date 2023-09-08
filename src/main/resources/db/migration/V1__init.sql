create table student
(
    student_id    bigint auto_increment
        primary key,
    created_at    datetime(6)  null,
    modified_at   datetime(6)  null,
    email         varchar(255) null,
    nickname      varchar(255) null,
    profile_image varchar(255) null,
    social_id     bigint       not null
);

create table teacher
(
    id          bigint auto_increment
        primary key,
    email       varchar(255) null,
    nickname    varchar(255) null,
    profile     varchar(255) null,
    role        varchar(255) null,
    social_id   varchar(255) null,
    social_type varchar(255) null
);

create table book
(
    book_id     bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    title       varchar(255) null,
    teacher_id  bigint       null,
    constraint FKmqt9byvafmcsjri33drb2lpiq
        foreign key (teacher_id) references teacher (id)
);

create table lesson
(
    lesson_id   bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    modified_at datetime(6)  null,
    days        varchar(255) null,
    end_time    time         null,
    lesson_name varchar(255) null,
    start_time  time         null,
    teacher_id  bigint       null,
    constraint FK9yhaoqrjxt5gwmn6icp1lf35n
        foreign key (teacher_id) references teacher (id)
);

create table homework
(
    homework_id   bigint auto_increment
        primary key,
    created_at    datetime(6)  null,
    modified_at   datetime(6)  null,
    content       varchar(255) null,
    end_time      datetime(6)  null,
    homework_name varchar(255) null,
    start_time    datetime(6)  null,
    lesson_id     bigint       null,
    constraint FK1hfa7auounxtrsgqvp694ixhf
        foreign key (lesson_id) references lesson (lesson_id)
);

create table lesson_book
(
    lesson_book_id bigint auto_increment
        primary key,
    created_at     datetime(6) null,
    modified_at    datetime(6) null,
    book_id        bigint      null,
    lesson_id      bigint      null,
    constraint FKbm4b5k5l7at45hlafbt23ld40
        foreign key (book_id) references book (book_id),
    constraint FKgyspp2951wa6yx0hlcwhr9qyj
        foreign key (lesson_id) references lesson (lesson_id)
);

create table student_homework
(
    student_homework_id bigint auto_increment
        primary key,
    created_at          datetime(6) null,
    modified_at         datetime(6) null,
    homework_status     bit         not null,
    homework_id         bigint      null,
    student_id          bigint      null,
    constraint FK1tg29uwqek458fvhsvb0j85nk
        foreign key (homework_id) references homework (homework_id),
    constraint FK6hklllgmofybir9ul1ye7p45y
        foreign key (student_id) references student (student_id)
);

create table student_lesson
(
    student_lesson_id bigint auto_increment
        primary key,
    focus_status      bit    null,
    lesson_id         bigint null,
    student_id        bigint null,
    constraint FK2nmxs05vgk43xy1cko182p72p
        foreign key (student_id) references student (student_id),
    constraint FKa64mwri6gq3ai7jwih91gsmf7
        foreign key (lesson_id) references lesson (lesson_id)
);