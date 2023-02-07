package com.noti.noti.lesson.application.port.in;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DateFrequencyOfLessons {

  private LocalDate dateOfLesson;
  private Long frequencyOfLesson;


  public DateFrequencyOfLessons(LocalDate dateOfLesson, Long frequencyOfLesson) {
    this.dateOfLesson = dateOfLesson;
    this.frequencyOfLesson = frequencyOfLesson;
  }

}
