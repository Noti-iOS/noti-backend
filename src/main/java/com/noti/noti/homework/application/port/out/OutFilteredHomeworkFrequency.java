package com.noti.noti.homework.application.port.out;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.criteria.CriteriaBuilder.In;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OutFilteredHomeworkFrequency {

  private LocalDate date;
  private int homeworkCnt;

  public OutFilteredHomeworkFrequency(int year, int month, Integer dayOfMonth, Long homeworkCnt) {
    this.date = LocalDate.of(year, month, dayOfMonth);
    this.homeworkCnt = homeworkCnt.intValue();
  }

  public OutFilteredHomeworkFrequency(LocalDateTime date, Long homeworkCnt) {
    this.date = date.toLocalDate();
    this.homeworkCnt = homeworkCnt.intValue();
  }
}
