package com.noti.noti.lesson.adapter.in.web.dto;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FrequencyOfLessonsDto {

  public LocalDate dateOfLesson;
  public Long frequencyOfLesson;

  public FrequencyOfLessonsDto(LocalDateTime dateOfLesson, Long frequencyOfLesson) {
    this.dateOfLesson = dateOfLesson.toLocalDate();
    this.frequencyOfLesson = frequencyOfLesson;
  }
}
