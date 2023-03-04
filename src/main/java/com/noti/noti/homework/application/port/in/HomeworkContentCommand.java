package com.noti.noti.homework.application.port.in;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public class HomeworkContentCommand {

  private Long lessonId;
  private LocalDateTime date;

  public HomeworkContentCommand(Long lessonId, String date) {
    this.lessonId = lessonId;
    this.date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE).atStartOfDay();
  }
}
