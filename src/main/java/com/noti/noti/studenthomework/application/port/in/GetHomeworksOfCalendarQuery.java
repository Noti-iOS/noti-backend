package com.noti.noti.studenthomework.application.port.in;

import java.time.LocalDate;
import java.util.List;

public interface GetHomeworksOfCalendarQuery {

  List<InHomeworkOfGivenDate> findHomeworksOfCalendar(Long lessonId, LocalDate date, Long teacherId);

}
