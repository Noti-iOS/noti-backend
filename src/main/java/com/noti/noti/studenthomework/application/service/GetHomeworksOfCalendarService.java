package com.noti.noti.studenthomework.application.service;

import com.noti.noti.studenthomework.adapter.in.web.dto.HomeworkOfGivenDateDto;
import com.noti.noti.studenthomework.application.port.in.GetHomeworksOfCalendarQuery;
import com.noti.noti.studenthomework.application.port.out.FindHomeworksOfCalendarPort;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHomeworksOfCalendarService implements GetHomeworksOfCalendarQuery {

  private final FindHomeworksOfCalendarPort findHomeworksOfCalendarPort;

  @Override
  public List<HomeworkOfGivenDateDto> findHomeworksOfCalendar(LocalDate date, Long teacherId) {
    return findHomeworksOfCalendarPort.findHomeworksOfCalendar(date, teacherId);
  }
}
