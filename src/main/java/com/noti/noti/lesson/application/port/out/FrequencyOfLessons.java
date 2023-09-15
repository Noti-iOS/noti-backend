package com.noti.noti.lesson.application.port.out;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FrequencyOfLessons {

  private Integer dayOfMonth;
  private Long frequencyOfLesson;

  public FrequencyOfLessons(Integer dayOfMonth, Long frequencyOfLesson) {
    this.dayOfMonth = dayOfMonth;
    this.frequencyOfLesson = frequencyOfLesson;
  }
}
