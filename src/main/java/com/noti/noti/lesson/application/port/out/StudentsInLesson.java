package com.noti.noti.lesson.application.port.out;

import java.util.List;
import lombok.Getter;

@Getter
public class StudentsInLesson {

  private Long lessonId;
  private List<Long> studentIds;
}
