package com.noti.noti.lesson.adapter.in.web.dto.response;

import com.noti.noti.lesson.application.port.in.InCreatedLesson;

public class CreatedLessonsDto {
  private String lessonName;

  public CreatedLessonsDto(InCreatedLesson inCreatedLesson) {
    this.lessonName = inCreatedLesson.getLessonName();
  }
}
