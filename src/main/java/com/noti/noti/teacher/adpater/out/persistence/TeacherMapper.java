package com.noti.noti.teacher.adpater.out.persistence;

import com.noti.noti.teacher.domain.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {

  public TeacherJpaEntity mapToJpaEntity(Teacher teacher) {
    return new TeacherJpaEntity.TeacherJpaEntityBuilder()
        .id(teacher.getId())
        .socialId(teacher.getSocial())
        .nickname(teacher.getNickname())
        .email(teacher.getEmail())
        .role(teacher.getRole())
        .profile(teacher.getProfile())
        .socialType(teacher.getSocialType())
        .build();
  }

  public Teacher mapToDomainEntity(TeacherJpaEntity teacherJpaEntity) {

    return Teacher.builder()
        .id(teacherJpaEntity.getId())
        .social(teacherJpaEntity.getSocialId())
        .nickname(teacherJpaEntity.getNickname())
        .email(teacherJpaEntity.getEmail())
        .profile(teacherJpaEntity.getProfile())
        .role(teacherJpaEntity.getRole())
        .socialType(teacherJpaEntity.getSocialType())
        .build();
  }

}
