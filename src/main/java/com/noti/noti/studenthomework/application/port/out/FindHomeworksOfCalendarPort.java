package com.noti.noti.studenthomework.application.port.out;

import com.noti.noti.studenthomework.adapter.in.web.dto.HomeworkOfGivenDateDto;
import java.time.LocalDate;
import java.util.List;

public interface FindHomeworksOfCalendarPort {

  List<HomeworkOfGivenDateDto> findHomeworksOfCalendar(LocalDate date, Long teacherId);

}
