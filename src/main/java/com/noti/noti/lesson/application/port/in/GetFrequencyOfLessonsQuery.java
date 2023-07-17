package com.noti.noti.lesson.application.port.in;

import java.util.List;

public interface GetFrequencyOfLessonsQuery {
  List<DateFrequencyOfLessons> findFrequencyOfLessons(int year, int month, Long teacherId);

}
