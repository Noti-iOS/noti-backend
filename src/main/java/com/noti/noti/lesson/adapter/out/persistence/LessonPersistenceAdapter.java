package com.noti.noti.lesson.adapter.out.persistence;

import com.noti.noti.lesson.adapter.out.persistence.jpa.LessonJpaRepository;
import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import com.noti.noti.lesson.application.port.out.SaveLessonPort;
import com.noti.noti.lesson.domain.model.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LessonPersistenceAdapter implements SaveLessonPort {

  private final LessonJpaRepository lessonJpaRepository;
  private final LessonMapper lessonMapper;

  @Override
  public Lesson saveLesson(Lesson lesson) {
    LessonJpaEntity lessonJpaEntity = lessonJpaRepository.save(lessonMapper.mapToJpaEntity(lesson));
    return lessonMapper.mapToDomainEntity(lessonJpaEntity);
  }
}
