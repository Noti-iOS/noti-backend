package com.noti.noti.lesson.adapter.in.web.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FrequencyOfLessonsDto {

  private LocalDate dateOfLesson;
  private Long frequencyOfLesson;

  public FrequencyOfLessonsDto(LocalDateTime dateOfLesson, Long frequencyOfLesson) {
    this.dateOfLesson = dateOfLesson.toLocalDate();
    this.frequencyOfLesson = frequencyOfLesson;
  }
}
