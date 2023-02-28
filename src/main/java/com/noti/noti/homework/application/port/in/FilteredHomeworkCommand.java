package com.noti.noti.homework.application.port.in;

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
    this.date = stringToLocalDateTime(year, month);
  }

  /**
   *
   * @param year
   * @param month
   * @return
   */
  private LocalDateTime stringToLocalDateTime(int year, int month) {

    StringBuilder stringBuilder = new StringBuilder();

    if (month > 9) {
      stringBuilder.append(year).append("-").append(month).append("-01 00:00:00.000");
    } else {
      stringBuilder.append(year).append("-0").append(month).append("-01 00:00:00.000");
    }



//    stringBuilder.append(yearMonth).append("-01 00:00:00.000");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    return LocalDateTime.parse(stringBuilder, formatter);
  }

}
