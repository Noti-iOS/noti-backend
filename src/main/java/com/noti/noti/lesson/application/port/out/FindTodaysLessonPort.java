package com.noti.noti.lesson.application.port.out;

import java.util.List;

public interface FindTodaysLessonPort {

  List<TodaysLesson> findTodaysLessons(TodaysLessonSearchConditon condition);
}
