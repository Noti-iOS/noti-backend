package com.noti.noti.lesson.domain.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Lesson {
  private Long id;

  //private Teacher teacher;
  private String lessonName;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @Builder
  public Lesson(Long id, String lessonName, LocalTime startTime, LocalTime endTime,
      LocalDateTime createdAt, LocalDateTime modifiedAt) {
    this.id = id;
    this.lessonName = lessonName;
    this.startTime = startTime;
    this.endTime = endTime;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }
}
