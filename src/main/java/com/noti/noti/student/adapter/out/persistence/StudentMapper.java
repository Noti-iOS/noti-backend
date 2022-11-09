package com.noti.noti.student.adapter.out.persistence;

import com.noti.noti.student.adapter.out.persistence.jpa.model.StudentJpaEntity;
import com.noti.noti.student.domain.model.Student;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

  public Student mapToDomainEntity(StudentJpaEntity studentJpaEntity) {
    return Student.builder()
        .id(studentJpaEntity.getId())
        .email(studentJpaEntity.getEmail())
        .nickname(studentJpaEntity.getNickname())
        .socialId(studentJpaEntity.getSocialId())
        .profileImage(studentJpaEntity.getProfileImage())
        .build();
  }

  public StudentJpaEntity mapToJpaEntity(Student student) {
    return StudentJpaEntity.builder()
        .id(student.getId())
        .email(student.getEmail())
        .nickname(student.getNickname())
        .socialId(student.getSocialId())
        .profileImage(student.getProfileImage())
        .build();
  }
}
