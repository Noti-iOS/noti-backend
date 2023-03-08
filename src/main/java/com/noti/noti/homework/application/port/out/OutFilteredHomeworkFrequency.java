package com.noti.noti.homework.application.port.out;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class OutFilteredHomeworkFrequency {

  private LocalDate date;
  private int homeworkCnt;

  public OutFilteredHomeworkFrequency(LocalDate date, int homeworkCnt) {
    this.date = date;
    this.homeworkCnt = homeworkCnt;
  }
}
