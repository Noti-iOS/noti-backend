package com.noti.noti.lesson.application.port.in;

import com.noti.noti.lesson.adapter.in.web.dto.FrequencyOfLessonsDto;
import java.util.List;

public interface GetFrequencyOfLessonsQuery {
  List<FrequencyOfLessonsDto> findFrequencyOfLessons(String yearMonth, Long teacherId);

}
