package com.noti.noti.homework.application.port.out;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchedHomework {
  private String homeworkName;
  private String lessonName;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDateTime startDate;
  private String cursorId;

  public SearchedHomework(String homeworkName, String lessonName, LocalTime startTime, LocalTime endTime, LocalDateTime startDate, String cursorId) {
    this.homeworkName = homeworkName;
    this.lessonName = lessonName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.startDate = startDate;
    this.cursorId = cursorId;

  }
}




