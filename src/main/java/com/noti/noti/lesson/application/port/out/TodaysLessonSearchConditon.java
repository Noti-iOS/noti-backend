package com.noti.noti.lesson.application.port.out;

import lombok.Getter;

@Getter
public class TodaysLessonSearchConditon {
  private Long teacherId;

  public TodaysLessonSearchConditon(Long teacherId) {
    this.teacherId = teacherId;
  }
}
