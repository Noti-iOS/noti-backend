package com.noti.noti.homework.application.port.in;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class FilteredHomeworkCommand {

  private long teacherId;
  private long lessonId;
  private LocalDateTime date;

  public FilteredHomeworkCommand(long teacherId, long lessonId, int year, int month) {
    this.teacherId = teacherId;
    this.lessonId = lessonId;
    this.date = LocalDate.of(year, month, 1).atStartOfDay();
  }

}

