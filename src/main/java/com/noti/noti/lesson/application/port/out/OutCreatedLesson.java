package com.noti.noti.lesson.application.port.out;


import lombok.Getter;

@Getter
public class OutCreatedLesson {

  private Long lessonId;

  private String lessonName;


  public OutCreatedLesson(Long lessonId, String lessonName) {
    this.lessonId = lessonId;
    this.lessonName = lessonName;
  }
}
