package com.noti.noti.homework.adapter.in.web.dto;

import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public class FilteredHomeworkDto {

  @Schema(description = "날짜", example = "2023-02-01")
  private LocalDate date;
  @Schema(description = "해당 날짜에 대한 숙제 수", example = "4")
  private int homeworkCnt;

  public FilteredHomeworkDto(InFilteredHomeworkFrequency in) {
    this.date = in.getDate();
    this.homeworkCnt = in.getHomeworksCnt();
  }


}
