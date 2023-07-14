package com.noti.noti.lesson.adapter.in.web.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FrequencyOfLessonsDto {

  @Schema(description = "숙업이 있는 날짜", example = "2023-01-27")
  private LocalDate date;
  @Schema(description = "dateOfLesson 날짜에 해당하는 수업 수", example = "3")
  private Long count;

  public FrequencyOfLessonsDto(LocalDate dateOfLesson, Long frequencyOfLesson) {
    this.date = dateOfLesson;
    this.count = frequencyOfLesson;
  }
}
