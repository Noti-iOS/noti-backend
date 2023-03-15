package com.noti.noti.homework.adapter.out.persistence;

import com.noti.noti.homework.adapter.out.persistence.jpa.HomeworkJpaRepository;
import com.noti.noti.homework.application.port.out.FindFilteredHomeworkPort;
import com.noti.noti.homework.application.port.out.FindSearchedHomeworkPort;
import com.noti.noti.homework.application.port.out.FindTodaysHomeworkPort;
import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import com.noti.noti.homework.application.port.out.SearchedHomework;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeworkPersistenceAdapter implements FindTodaysHomeworkPort,
    FindFilteredHomeworkPort, FindSearchedHomeworkPort {

  private final HomeworkMapper homeworkMapper;
  private final HomeworkJpaRepository homeworkJpaRepository;
  private final HomeworkQueryRepository homeworkQueryRepository;

  @Override
  public List<TodaysHomework> findTodaysHomeworks(TodayHomeworkCondition condition) {
    return homeworkQueryRepository.findTodayHomeworks(condition);
  }

  @Override
  public List<OutFilteredHomeworkFrequency> findFilteredHomeworks(LocalDateTime startOfMonth,
      Long lessonId, Long teacherId) {
    return homeworkQueryRepository.findFilteredHomeworkFrequency(
        teacherId, lessonId, startOfMonth, startOfMonth.plusMonths(1).minusSeconds(1));
  }


  @Override
  public List<SearchedHomework> findSearchedHomeworks(Long teacherId, String keyword, int size, String cursorId) {
    return homeworkQueryRepository.findSearchedHomework(teacherId, keyword, size, cursorId);
  }
}
