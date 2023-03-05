package com.noti.noti.homework.application.port.in;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class AddHomeworksCommand {

  private List<String> homeworkNames;
  private LocalDateTime startTime;
  private LocalDateTime endTime;
  private Long deadlineAlarmSettingTime;
  private Long lessonId;

  public AddHomeworksCommand(List<String> homeworkNames, LocalDateTime startTime,
      LocalDateTime endTime, Long deadlineAlarmSettingTime, Long lessonId) {
    this.homeworkNames = homeworkNames;
    this.startTime = startTime;
    this.endTime = endTime;
    this.deadlineAlarmSettingTime = deadlineAlarmSettingTime;
    this.lessonId = lessonId;
  }
}
