package com.noti.noti.lesson.application.port.out;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FrequencyOfLessons {

  private LocalDate dateOfLesson;
  private Long frequencyOfLesson;

  public FrequencyOfLessons(LocalDateTime dateOfLesson, Long frequencyOfLesson) {
    this.dateOfLesson = dateOfLesson.toLocalDate();
    this.frequencyOfLesson = frequencyOfLesson;
  }
}
