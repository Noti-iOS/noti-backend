package com.noti.noti.homework.adapter.out.persistence;

import com.noti.noti.homework.adapter.out.persistence.jpa.HomeworkJpaRepository;
import com.noti.noti.homework.application.port.out.FindTodaysHomeworkPort;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HomeworkPersistenceAdapter implements FindTodaysHomeworkPort {

  private final HomeworkMapper homeworkMapper;
  private final HomeworkJpaRepository homeworkJpaRepository;
  private final HomeworkQueryRepository homeworkQueryRepository;

  @Override
  public List<TodaysHomework> findTodaysHomeworks(TodayHomeworkCondition condition) {
    return homeworkQueryRepository.findTodayHomeworks(condition);
  }
}
