package com.noti.noti.lesson.application.port.in;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import lombok.Getter;

@Getter
public class AddLessonCommand {

  private Long teacherId;
  private String lessonName;
  private Set<DayOfWeek> days;
  private LocalTime startTime;
  private LocalTime endTime;
  private List<Long> bookIds;
  private List<Long> studentIds;

  public AddLessonCommand(Long teacherId, String lessonName, Set<DayOfWeek> days,
      LocalTime startTime, LocalTime endTime, List<Long> bookIds, List<Long> studentIds) {
    this.teacherId = teacherId;
    this.lessonName = lessonName;
    this.days = days;
    this.startTime = startTime;
    this.endTime = endTime;
    this.bookIds = bookIds;
    this.studentIds = studentIds;
  }
}
