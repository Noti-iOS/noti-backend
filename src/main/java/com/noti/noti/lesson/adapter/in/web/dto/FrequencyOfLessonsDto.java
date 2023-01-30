package com.noti.noti.lesson.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FrequencyOfLessonsDto {

  @Schema(description = "숙업이 있는 날짜", example = "2023-01-27")
  private LocalDate dateOfLesson;
  @Schema(description = "dateOfLesson 날짜에 해당하는 수업 수", example = "3")
  private Long frequencyOfLesson;

  public FrequencyOfLessonsDto(LocalDateTime dateOfLesson, Long frequencyOfLesson) {
    this.dateOfLesson = dateOfLesson.toLocalDate();
    this.frequencyOfLesson = frequencyOfLesson;
  }
}
