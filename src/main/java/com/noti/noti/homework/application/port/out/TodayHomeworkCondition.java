package com.noti.noti.homework.application.port.out;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class TodayHomeworkCondition {

  private Long teacherId;
  private LocalDateTime currentTime;

  public TodayHomeworkCondition(Long teacherId, LocalDateTime currentTime) {
    this.teacherId = teacherId;
    this.currentTime = currentTime;
  }
}
