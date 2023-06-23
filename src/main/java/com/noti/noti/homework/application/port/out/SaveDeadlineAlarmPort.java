package com.noti.noti.homework.application.port.out;

import java.time.LocalDateTime;

public interface SaveDeadlineAlarmPort {

  void saveDeadlineAlarm(Long homeworkId, LocalDateTime deadlineAlarmTime);
}
