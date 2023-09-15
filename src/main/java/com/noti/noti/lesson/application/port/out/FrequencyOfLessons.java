package com.noti.noti.lesson.application.port.out;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FrequencyOfLessons {

  private Integer dayOfMonth;
  private Long frequencyOfLesson;

  public FrequencyOfLessons(Integer dateOfLesson, Long frequencyOfLesson) {
    this.dayOfMonth = dateOfLesson;
    this.frequencyOfLesson = frequencyOfLesson;
  }
}
