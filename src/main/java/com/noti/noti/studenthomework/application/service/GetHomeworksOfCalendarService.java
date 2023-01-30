package com.noti.noti.studenthomework.application.service;

import com.noti.noti.studenthomework.application.port.in.GetHomeworksOfCalendarQuery;
import com.noti.noti.studenthomework.application.port.in.InHomeworkOfGivenDate;
import com.noti.noti.studenthomework.application.port.out.FindHomeworksOfCalendarPort;
import com.noti.noti.studenthomework.application.port.out.OutHomeworkOfGivenDate;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetHomeworksOfCalendarService implements GetHomeworksOfCalendarQuery {

  private final FindHomeworksOfCalendarPort findHomeworksOfCalendarPort;

  @Override
  public List<InHomeworkOfGivenDate> findHomeworksOfCalendar(LocalDate date, Long teacherId) {
    List<OutHomeworkOfGivenDate> outHomeworkOfGivenDates = findHomeworksOfCalendarPort.findHomeworksOfCalendar(
        date, teacherId);
    List<InHomeworkOfGivenDate> inHomeworkOfGivenDates = new ArrayList<>();

    outHomeworkOfGivenDates.forEach(
        outHomeworkOfGivenDate -> inHomeworkOfGivenDates.add(
            new InHomeworkOfGivenDate(
                outHomeworkOfGivenDate.getLessonId(),
                outHomeworkOfGivenDate.getLessonName(),
                outHomeworkOfGivenDate.getStartTimeOfLesson(),
                outHomeworkOfGivenDate.getEndTimeOfLesson(),
                outHomeworkOfGivenDate.getHomeworks())
        )
    );

    return inHomeworkOfGivenDates;
  }
}
