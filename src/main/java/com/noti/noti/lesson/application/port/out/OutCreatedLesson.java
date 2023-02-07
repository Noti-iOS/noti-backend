package com.noti.noti.lesson.application.port.out;


import lombok.Getter;

@Getter
public class OutCreatedLesson {

  private String lessonName;


  public OutCreatedLesson(String lessonName) {
    this.lessonName = lessonName;
  }
}
