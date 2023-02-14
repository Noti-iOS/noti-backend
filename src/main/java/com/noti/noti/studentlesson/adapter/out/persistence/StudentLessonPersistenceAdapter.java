package com.noti.noti.studentlesson.adapter.out.persistence;

import com.noti.noti.studentlesson.adapter.out.persistence.jpa.StudentLessonJpaRepository;
import com.noti.noti.studentlesson.adapter.out.persistence.jpa.model.StudentLessonJpaEntity;
import com.noti.noti.studentlesson.application.port.out.SaveStudentLessonPort;
import com.noti.noti.studentlesson.domain.model.StudentLesson;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StudentLessonPersistenceAdapter implements SaveStudentLessonPort {

  private final StudentLessonJpaRepository studentLessonJpaRepository;
  private final StudentLessonMapper studentLessonMapper;


  @Override
  public List<StudentLesson> saveAllStudentLessons(List<StudentLesson> studentLessons) {
    List<StudentLessonJpaEntity> studentLessonJpaEntities =
        studentLessons.stream()
            .map(studentLessonMapper::mapToJpaEntity)
            .collect(Collectors.toList());

    return studentLessonJpaRepository.saveAll(studentLessonJpaEntities).stream()
        .map(studentLessonMapper::mapToDomainEntity)
        .collect(Collectors.toList());
  }
}
