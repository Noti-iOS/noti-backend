package com.noti.noti.lesson.application.port.in;

import com.noti.noti.lesson.application.port.out.OutCreatedLesson;
import lombok.Getter;

@Getter
public class InCreatedLesson {

  private String lessonName;

  public InCreatedLesson(OutCreatedLesson outCreatedLesson) {
    this.lessonName = outCreatedLesson.getLessonName();
  }

}
