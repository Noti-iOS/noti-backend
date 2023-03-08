package com.noti.noti.homework.application.port.in;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HomeworkContentCommand {

  private Long lessonId;
  private LocalDateTime date;

  public HomeworkContentCommand(Long lessonId, LocalDate date) {
    this.lessonId = lessonId;
    this.date = date.atStartOfDay();
  }
}
