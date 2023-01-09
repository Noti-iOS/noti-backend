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
        .homework(homeworkMapper.mapToDomainEntity(studentHomeworkJpaEntity.getHomeworkJpaEntity()))
        .student(studentMapper.mapToDomainEntity(studentHomeworkJpaEntity.getStudentJpaEntity()))
        .homeworkStatus(studentHomeworkJpaEntity.isHomeworkStatus())
        .build();
  }

  public StudentHomeworkJpaEntity mapToJpaEntity(StudentHomework studentHomework) {
    return StudentHomeworkJpaEntity.builder()
        .id(studentHomework.getId())
        .studentJpaEntity(studentMapper.mapToJpaEntity(studentHomework.getStudent()))
        .homeworkJpaEntity(homeworkMapper.mapToJpaEntity(studentHomework.getHomework()))
        .homeworkStatus(studentHomework.isHomeworkStatus())
        .build();
  }
}
