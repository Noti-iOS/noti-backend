package com.noti.noti.homework.application.port.out;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

public interface FindFilteredHomeworkPort {

  List<OutFilteredHomeworkFrequency> findFilteredHomeworks(LocalDateTime date,
      Long lessonId, Long teacherId);
}

