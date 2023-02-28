package com.noti.noti.homework.adapter.in.web.dto;

import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import java.time.LocalDate;

public class FilteredHomeworkDto {

  private LocalDate date;
  private int homeworkCnt;

  public FilteredHomeworkDto(InFilteredHomeworkFrequency in) {
    this.date = in.getDate();
    this.homeworkCnt = in.getHomeworksCnt();
  }


}
