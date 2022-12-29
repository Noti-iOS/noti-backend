package com.noti.noti.homework.application.port.out;

import java.util.List;

public interface FindTodaysHomeworkPort {

  List<TodaysHomework> findTodaysHomeworks(TodayHomeworkCondition condition);
}
