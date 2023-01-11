package com.noti.noti.homework.adapter.in.web.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FrequencyOfLessonsDto {

  public LocalDate dateOfLesson;
  public int frequencyOfLesson;

}
