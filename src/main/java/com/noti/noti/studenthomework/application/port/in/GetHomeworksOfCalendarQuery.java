package com.noti.noti.studenthomework.application.port.in;

import com.noti.noti.studenthomework.adapter.in.web.dto.HomeworkOfGivenDateDto;
import java.time.LocalDate;
import java.util.List;

public interface GetHomeworksOfCalendarQuery {

  List<HomeworkOfGivenDateDto> findHomeworksOfCalendar(LocalDate date, Long teacherId);

}
