package com.noti.noti.teacher.adpater.out.persistence;

import com.noti.noti.teacher.domain.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

  TeacherJpaEntity mapToJpaEntity(Teacher teacher) {
    return new TeacherJpaEntity.TeacherJpaEntityBuilder()
        .username(teacher.getUsername())
        .socialId(teacher.getSocial())
        .nickname(teacher.getNickname())
        .email(teacher.getEmail())
        .role(teacher.getRole())
        .profile(teacher.getProfile())
        .build();
  }

  // jpaEntity -> domain
  Teacher mapToDomainEntity(TeacherJpaEntity teacherJpaEntity) {

    return Teacher.builder()
        .id(teacherJpaEntity.getUsername())
        .social(teacherJpaEntity.getSocialId())
        .nickname(teacherJpaEntity.getNickname())
        .email(teacherJpaEntity.getEmail())
        .profile(teacherJpaEntity.getProfile())
        .role(teacherJpaEntity.getRole())
        .build();
  }

}
