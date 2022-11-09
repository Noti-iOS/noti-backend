package com.noti.noti.lesson.domain.model;

import com.noti.noti.common.domain.model.Day;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Lesson {
  private Long id;

  //private Teacher teacher;
  private String lessonName;
  private Set<Day> days;
  private LocalTime startTime;
  private LocalTime endTime;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;

  @Builder
  public Lesson(Long id, String lessonName, Set<Day> days, LocalTime startTime, LocalTime endTime,
      LocalDateTime createdAt, LocalDateTime modifiedAt) {
    this.id = id;
    this.lessonName = lessonName;
    this.days = days;
    this.startTime = startTime;
    this.endTime = endTime;
    this.createdAt = createdAt;
    this.modifiedAt = modifiedAt;
  }
}
