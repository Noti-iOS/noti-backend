package com.noti.noti.studenthomework.adapter.out.persistence;

import com.noti.noti.homework.adapter.out.persistence.HomeworkMapper;
import com.noti.noti.student.adapter.out.persistence.StudentMapper;
import com.noti.noti.studenthomework.adapter.out.persistence.jpa.model.StudentHomeworkJpaEntity;
import com.noti.noti.studenthomework.domain.model.StudentHomework;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentHomeworkMapper {

  private final StudentMapper studentMapper;
  private final HomeworkMapper homeworkMapper;

  public StudentHomework mapToDomainEntity(StudentHomeworkJpaEntity studentHomeworkJpaEntity) {
    return StudentHomework.builder()
        .id(studentHomeworkJpaEntity.getId())
        .homework(homeworkMapper.mapToDomainEntity(studentHomeworkJpaEntity.getHomework()))
        .student(studentMapper.mapToDomainEntity(studentHomeworkJpaEntity.getStudent()))
        .homeworkStatus(studentHomeworkJpaEntity.isHomeworkStatus())
        .build();
  }

  public StudentHomeworkJpaEntity mapToJpaEntity(StudentHomework studentHomework) {
    return StudentHomeworkJpaEntity.builder()
        .id(studentHomework.getId())
        .student(studentMapper.mapToJpaEntity(studentHomework.getStudent()))
        .homework(homeworkMapper.mapToJpaEntity(studentHomework.getHomework()))
        .homeworkStatus(studentHomework.isHomeworkStatus())
        .build();
  }
}
