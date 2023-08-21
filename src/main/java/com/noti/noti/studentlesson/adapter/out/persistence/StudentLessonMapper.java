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
        .isDeleted(studentLessonJpaEntity.isDeleted())
        .lesson(lessonMapper.mapToDomainEntity(studentLessonJpaEntity.getLessonJpaEntity()))
        .student(studentMapper.mapToDomainEntity(studentLessonJpaEntity.getStudentJpaEntity()))
        .build();
  }

  public StudentLessonJpaEntity mapToJpaEntity(StudentLesson studentLesson) {
    return StudentLessonJpaEntity.builder()
        .id(studentLesson.getId())
        .focusStatus(studentLesson.isFocusStatus())
        .isDeleted(studentLesson.isDeleted())
        .studentJpaEntity(studentMapper.mapToJpaEntity(studentLesson.getStudent()))
        .lessonJpaEntity(lessonMapper.mapToJpaEntity(studentLesson.getLesson()))
        .build();
  }
}
