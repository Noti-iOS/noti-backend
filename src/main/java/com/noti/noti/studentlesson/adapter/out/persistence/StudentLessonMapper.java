package com.noti.noti.studentlesson.adapter.out.persistence;

import com.noti.noti.lesson.adapter.out.persistence.LessonMapper;
import com.noti.noti.student.adapter.out.persistence.StudentMapper;
import com.noti.noti.studentlesson.adapter.out.persistence.jpa.model.StudentLessonJpaEntity;
import com.noti.noti.studentlesson.domain.model.StudentLesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentLessonMapper {

  private final LessonMapper lessonMapper;
  private final StudentMapper studentMapper;

  public StudentLesson mapToDomainEntity(StudentLessonJpaEntity studentLessonJpaEntity){
    return StudentLesson.builder()
        .id(studentLessonJpaEntity.getId())
        .focusStatus(studentLessonJpaEntity.isFocusStatus())
        .lesson(lessonMapper.mapToDomainEntity(studentLessonJpaEntity.getLesson()))
        .student(studentMapper.mapToDomainEntity(studentLessonJpaEntity.getStudent()))
        .build();
  }

  public StudentLessonJpaEntity mapToJpaEntity(StudentLesson studentLesson) {
    return StudentLessonJpaEntity.builder()
        .id(studentLesson.getId())
        .focusStatus(studentLesson.isFocusStatus())
        .student(studentMapper.mapToJpaEntity(studentLesson.getStudent()))
        .lesson(lessonMapper.mapToJpaEntity(studentLesson.getLesson()))
        .build();
  }
}
