package com.noti.noti.homework.application.port.out;

import com.noti.noti.homework.adapter.in.web.dto.FrequencyOfLessonsDto;
import java.time.YearMonth;
import java.util.List;

public interface FrequencyOfLessonsPort {

  // 숙제가 있는 날의 날짜와 분반 찾기
  List<FrequencyOfLessonsDto> findFrequencyOfLessons(String yearMonth, Long teacherId);


}
