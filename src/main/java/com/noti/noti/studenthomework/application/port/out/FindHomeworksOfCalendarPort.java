package com.noti.noti.studenthomework.application.port.out;

import java.time.LocalDate;
import java.util.List;

public interface FindHomeworksOfCalendarPort {

  List<OutHomeworkOfGivenDate> findHomeworksOfCalendar(LocalDate date, Long teacherId);

}
