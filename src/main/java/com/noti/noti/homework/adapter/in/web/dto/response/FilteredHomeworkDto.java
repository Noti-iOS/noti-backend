package com.noti.noti.homework.adapter.in.web.dto.response;

import com.noti.noti.homework.application.port.in.InFilteredHomeworkFrequency;
import com.noti.noti.lesson.application.port.in.DateFrequencyOfLessons;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class FilteredHomeworkDto {

  @Schema(description = "날짜", example = "2023-02-01")
  private LocalDate date;
  @Schema(description = "해당 날짜에 대한 숙제 수", example = "4")
  private int count;

  public FilteredHomeworkDto(InFilteredHomeworkFrequency in) {
    this.date = in.getDate();
    this.count = in.getHomeworksCnt();
  }

  public FilteredHomeworkDto(DateFrequencyOfLessons in) {
    this.date = in.getDateOfLesson();
    this.count = in.getFrequencyOfLesson().intValue();
  }


}

