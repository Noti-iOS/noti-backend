package com.noti.noti.lesson.application.port.out;

import java.util.List;

public interface FrequencyOfLessonsPort {

  // 숙제가 있는 날의 날짜와 분반 찾기
  List<FrequencyOfLessons> findFrequencyOfLessons(int year, int month, Long teacherId);


}
