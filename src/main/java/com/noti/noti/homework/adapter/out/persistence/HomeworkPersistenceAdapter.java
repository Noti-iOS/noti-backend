package com.noti.noti.homework.adapter.out.persistence;

import com.noti.noti.homework.adapter.in.web.dto.FrequencyOfLessonsDto;
import com.noti.noti.homework.adapter.out.persistence.jpa.HomeworkJpaRepository;
import com.noti.noti.homework.application.port.out.FindTodaysHomeworkPort;
import com.noti.noti.homework.application.port.out.FrequencyOfLessonsPort;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeworkPersistenceAdapter implements FindTodaysHomeworkPort, FrequencyOfLessonsPort {

  private final HomeworkMapper homeworkMapper;
  private final HomeworkJpaRepository homeworkJpaRepository;
  private final HomeworkQueryRepository homeworkQueryRepository;

  @Override
  public List<TodaysHomework> findTodaysHomeworks(TodayHomeworkCondition condition) {
    return homeworkQueryRepository.findTodayHomeworks(condition);
  }

  @Override
  public List<FrequencyOfLessonsDto> findFrequencyOfLessons(String yearMonth) {

    LocalDateTime startTime = stringToLocalDateTime(yearMonth);
    LocalDateTime endTime = startTime.plusDays(1);

    return homeworkQueryRepository.findFrequencyOfLesson(startTime, endTime);
  }

  /**
   * 문자열을 LocalDateTime으로 변환
   * @param yearMonth '년-월' 형식의 문자열
   * @return 해당 '년-월'에서 1일의 LocalDateTime
   */
  private LocalDateTime stringToLocalDateTime(String yearMonth) {

    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(yearMonth).append("-01 00:00:00.000");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    return LocalDateTime.parse(stringBuilder, formatter);
  }
}
