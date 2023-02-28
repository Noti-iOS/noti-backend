package com.noti.noti.homework.application.port.in;

import com.noti.noti.homework.application.port.out.OutFilteredHomeworkFrequency;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class InFilteredHomeworkFrequency {

  private LocalDate date;
  private int homeworksCnt;

  public InFilteredHomeworkFrequency(OutFilteredHomeworkFrequency out) {
    this.date = out.getDate();
    this.homeworksCnt = out.getHomeworkCnt();
  }
}
