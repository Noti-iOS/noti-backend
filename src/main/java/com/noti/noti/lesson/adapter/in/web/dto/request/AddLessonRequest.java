package com.noti.noti.lesson.adapter.in.web.dto.request;

import com.noti.noti.lesson.application.port.in.AddLessonCommand;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import lombok.Getter;

@Getter
public class AddLessonRequest {

  private String lessonName;
  private LocalTime startTime;
  private LocalTime endTime;
  private Set<DayOfWeek> days;
  private List<Long> bookIds;
  private List<Long> studentIds;

  public AddLessonCommand toCommand(Long teacherId) {
    return new AddLessonCommand(teacherId, lessonName, days, startTime, endTime, bookIds,
        studentIds);
  }
}
