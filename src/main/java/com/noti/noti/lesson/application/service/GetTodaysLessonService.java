package com.noti.noti.lesson.application.service;

import com.noti.noti.homework.application.port.out.FindTodaysHomeworkPort;
import com.noti.noti.homework.application.port.out.TodayHomeworkCondition;
import com.noti.noti.homework.application.port.out.TodaysHomework;
import com.noti.noti.lesson.application.port.in.GetTodaysLessonQuery;
import com.noti.noti.lesson.application.port.in.TodaysLessonHomework;
import com.noti.noti.lesson.application.port.out.FindTodaysLessonPort;
import com.noti.noti.lesson.application.port.out.TodaysLesson;
import com.noti.noti.lesson.application.port.out.TodaysLessonSearchConditon;
import com.noti.noti.teacher.application.port.out.FindTeacherNicknamePort;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetTodaysLessonService implements GetTodaysLessonQuery {

  private final FindTodaysLessonPort findTodaysLessonPort;
  private final FindTodaysHomeworkPort findTodaysHomeworkPort;
  private final FindTeacherNicknamePort findTeacherNicknamePort;

  @Transactional(readOnly = true)
  @Override
  public TodaysLessonHomework getTodaysLessons(Long teacherId) {
    LocalDateTime now = LocalDateTime.now();
    String teacherNickname = findTeacherNicknamePort.findTeacherNickname(teacherId);
    TodaysLessonHomework todaysLessonHomework = new TodaysLessonHomework(teacherNickname, now);

    List<TodaysLesson> todaysLessons = findTodaysLessonPort.findTodaysLessons(
        new TodaysLessonSearchConditon(teacherId));

    addLesson(todaysLessonHomework, todaysLessons);

    if (checkEmpty(todaysLessonHomework.getLessons())) {
      return todaysLessonHomework;
    }

    List<TodaysHomework> todaysHomeworks = findTodaysHomeworkPort.findTodaysHomeworks(
        new TodayHomeworkCondition(teacherId, now));

    todaysHomeworks.forEach(todaysLessonHomework::addHomework);

    return todaysLessonHomework;
  }

  private boolean checkEmpty(List<?> list) {
    return list.isEmpty();
  }

  private boolean checkEmpty(Map<Long, ?> map) {
    return map.isEmpty();
  }
  private void addLesson(TodaysLessonHomework todaysLessonHomework,
      List<TodaysLesson> todaysLessons) {
    if (!checkEmpty(todaysLessons)) {
      todaysLessonHomework.changeLessonCreatedStatus();
      todaysLessons.stream()
          .filter(todaysLesson -> todaysLesson.getDays()
              .contains(todaysLessonHomework.getNow().getDayOfWeek().toString()))
          .forEach(todaysLessonHomework::addLesson);
    }
  }

}
