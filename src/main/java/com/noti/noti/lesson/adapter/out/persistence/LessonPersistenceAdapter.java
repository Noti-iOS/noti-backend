package com.noti.noti.lesson.adapter.out.persistence;

import com.noti.noti.lesson.adapter.out.persistence.jpa.LessonJpaRepository;
import com.noti.noti.lesson.adapter.out.persistence.jpa.model.LessonJpaEntity;
import com.noti.noti.lesson.application.port.out.CheckLessonExistencePort;
import com.noti.noti.lesson.application.port.out.FindCreatedLessonsPort;
import com.noti.noti.lesson.application.port.out.FindLessonPort;
import com.noti.noti.lesson.application.port.out.FrequencyOfLessons;
import com.noti.noti.lesson.application.port.out.FrequencyOfLessonsPort;
import com.noti.noti.lesson.application.port.out.LessonDto;
import com.noti.noti.lesson.application.port.out.OutCreatedLesson;
import com.noti.noti.lesson.application.port.out.SaveLessonPort;
import com.noti.noti.lesson.application.port.out.StudentsInLesson;
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLessonSearchConditon;
import com.noti.noti.lesson.domain.model.Lesson;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class LessonPersistenceAdapter implements SaveLessonPort, FindLessonPort,
    FrequencyOfLessonsPort, FindCreatedLessonsPort, CheckLessonExistencePort {

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

  @Override
  public List<LessonDto> findAllLessonsByTeacherId(Long teacherId) {
    return lessonQueryRepository.findAllLessonsByTeacherId(teacherId);
  }

  @Override
  public Optional<StudentsInLesson> findLessonAndStudentsById(Long lessonId) {
    return lessonQueryRepository.findLessonAndStudentsById(lessonId);
  }

  @Override
  public List<FrequencyOfLessons> findFrequencyOfLessons(int year, int month, Long teacherId) {
    LocalDateTime startTime = LocalDate.of(year, month, 1).atStartOfDay();
    LocalDateTime endTime = startTime.plusMonths(1);

    return lessonQueryRepository.findFrequencyOfLesson(startTime, endTime, teacherId);
  }

  public Optional<Lesson> findById(Long id) {
    return lessonJpaRepository.findById(id).map(lessonMapper::mapToDomainEntity);
  }

  @Override
  public boolean existsById(Long lessonId) {
    return lessonJpaRepository.existsById(lessonId);
  }

  @Override
  public List<OutCreatedLesson> findCreatedLessons(Long teacherId) {
    return lessonQueryRepository.findCreatedLessons(teacherId);
  }

}
