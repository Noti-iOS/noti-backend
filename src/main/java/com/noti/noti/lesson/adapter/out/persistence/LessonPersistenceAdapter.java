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
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLessonSearchConditon;
import com.noti.noti.lesson.domain.model.Lesson;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
  public List<FrequencyOfLessons> findFrequencyOfLessons(String yearMonth, Long teacherId) {
    LocalDateTime startTime = stringToLocalDateTime(yearMonth);
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

  /**
   * 문자열을 LocalDateTime으로 변환
   *
   * @param yearMonth '년-월' 형식의 문자열
   * @return 해당 '년-월'에서 1일의 LocalDateTime
   */
  private LocalDateTime stringToLocalDateTime(String yearMonth) {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(yearMonth).append("-01 00:00:00.000");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    return LocalDateTime.parse(stringBuilder, formatter);
  }


  @Override
  public List<OutCreatedLesson> findCreatedLessons(Long teacherId) {
    return lessonQueryRepository.findCreatedLessons(teacherId);
  }

}
