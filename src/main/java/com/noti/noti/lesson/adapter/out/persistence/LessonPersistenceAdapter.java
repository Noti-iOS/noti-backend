package com.noti.noti.lesson.adapter.out.persistence;

import com.noti.noti.lesson.adapter.out.persistence.jpa.LessonJpaRepository;
import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import com.noti.noti.lesson.application.port.out.FindTodaysLessonPort;
import com.noti.noti.lesson.application.port.out.SaveLessonPort;
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLessonSearchConditon;
import com.noti.noti.lesson.domain.model.Lesson;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@RequiredArgsConstructor
public class LessonPersistenceAdapter implements SaveLessonPort, FindTodaysLessonPort {

  private final LessonJpaRepository lessonJpaRepository;
  private final LessonMapper lessonMapper;
  private final LessonQueryRepository lessonQueryRepository;

  @Override
  public Lesson saveLesson(Lesson lesson) {
    LessonJpaEntity lessonJpaEntity = lessonJpaRepository.save(lessonMapper.mapToJpaEntity(lesson));
    return lessonMapper.mapToDomainEntity(lessonJpaEntity);
  }

  @Override
  public List<TodaysLesson> findTodaysLessons(TodaysLessonSearchConditon condition) {
    return lessonQueryRepository.findTodayLesson(condition);
  }
}
